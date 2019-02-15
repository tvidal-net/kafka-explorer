package uk.tvidal.kafka.explorer.controller

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import uk.tvidal.kafka.explorer.kafka.KafkaClientService
import uk.tvidal.kafka.explorer.kafka.KafkaService
import uk.tvidal.kafka.explorer.model.KafkaBroker
import uk.tvidal.kafka.explorer.model.KafkaMessage
import uk.tvidal.kafka.explorer.model.KafkaTopicInfo
import uk.tvidal.kafka.explorer.threadFactory
import java.lang.System.currentTimeMillis
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.math.max

class KafkaController : Controller() {

    private val executor = newSingleThreadScheduledExecutor(threadFactory("KafkaController"))
    private var kafka = KafkaService.NoOp

    val statusMessageProperty = SimpleStringProperty()
    var statusMessage: String? by statusMessageProperty

    val brokerProperty = SimpleObjectProperty<KafkaBroker>()
    var broker: KafkaBroker? by brokerProperty

    val topics = observableList<KafkaTopicInfo>()

    val topic = SimpleObjectProperty<KafkaTopicInfo>()
    val stream = observableList<KafkaMessage>()

    init {
        executor.scheduleAtFixedRate(::poll, delay, delay, MILLISECONDS)
        brokerProperty.onChange {
            it?.let {
                topics.clear()
                executor.execute { connect(it) }
            }
        }
        topic.onChange {
            stream.clear()
            executor.execute { subscribe(it) }
        }
    }

    private fun connect(broker: KafkaBroker) {
        kafka.close()
        kafka = KafkaClientService(broker)
        val data = kafka.list()
        runLater { topics.setAll(data) }
    }

    private fun subscribe(topic: KafkaTopicInfo?) {
        kafka.unsubscribe()
        topic?.run {
            val offset = max(latest - messageCount, earliest)
            kafka.subscribe(name, partition, offset)
        }
        poll()
    }

    private fun poll() {
        val before = currentTimeMillis()
        var count = 0
        do {
            val messages = kafka.poll()
            if (!messages.isEmpty()) {
                runLater {
                    val over = max(stream.size + messages.size - messageCount + 1, 0)
                    if (over > 0) stream.remove(0, over - 1)
                    stream += messages
                }
            }
            count += messages.size
        } while (!messages.isEmpty())

        if (count > 0) {
            val time = currentTimeMillis() - before
            runLater {
                statusMessage = "loaded $count messages in ${time}ms"
            }
        }
    }

    companion object {
        private const val delay = 333L
        const val messageCount = 1000
    }
}