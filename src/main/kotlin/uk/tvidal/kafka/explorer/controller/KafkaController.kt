package uk.tvidal.kafka.explorer.controller

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import uk.tvidal.kafka.explorer.kafka.KafkaClientService
import uk.tvidal.kafka.explorer.kafka.KafkaService
import uk.tvidal.kafka.explorer.model.KafkaBroker
import uk.tvidal.kafka.explorer.model.KafkaMessage
import uk.tvidal.kafka.explorer.model.KafkaTopicInfo
import uk.tvidal.kafka.explorer.threadFactory
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.math.max

class KafkaController : Controller() {

    private val executor = newSingleThreadScheduledExecutor(threadFactory("KafkaController"))
    private var kafka = KafkaService.NoOp

    val broker: ObjectProperty<KafkaBroker> = SimpleObjectProperty()
    val topics = observableList<KafkaTopicInfo>()

    val topic: ObjectProperty<KafkaTopicInfo> = SimpleObjectProperty()
    val stream = observableList<KafkaMessage>()

    init {
        executor.scheduleAtFixedRate(::poll, delay, delay, MILLISECONDS)
        broker.onChange { broker ->
            broker?.let {
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
        log.info { "Connected to $broker" }
        val data = kafka.list()
        runLater { topics.setAll(data) }
    }

    private fun subscribe(topic: KafkaTopicInfo?) {
        kafka.unsubscribe()
        log.info { "subscribing to $topic" }
        topic?.run {
            val offset = max(latest - messageCount, earliest)
            kafka.subscribe(name, partition, offset)
        }
        poll()
    }

    private fun poll() {
        do {
            val messages = kafka.poll()
            if (!messages.isEmpty()) {
                runLater { stream += messages }
            }
        } while (!messages.isEmpty())
    }

    companion object {
        private const val delay = 333L
        const val messageCount = 1000
    }
}