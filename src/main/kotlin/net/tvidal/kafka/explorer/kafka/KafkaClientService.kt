package net.tvidal.kafka.explorer.kafka

import net.tvidal.kafka.explorer.codec.NullCodec
import net.tvidal.kafka.explorer.codec.StringCodec
import net.tvidal.kafka.explorer.model.KafkaBroker
import net.tvidal.kafka.explorer.model.KafkaOffset
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import java.time.Duration

class KafkaClientService(broker: KafkaBroker) : KafkaService {

    companion object {
        const val EMPTY_OFFSET = -1L
        private val timeout = Duration.ofSeconds(1L)
    }

    private val consumer: Consumer<Nothing?, String> = KafkaConsumer(
        mapOf(
            CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to broker.toString(),
            ConsumerConfig.GROUP_ID_CONFIG to "kafka-explorer",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 1
        ),
        NullCodec,
        StringCodec
    )

    override fun list(): Collection<KafkaOffset> {
        val info = consumer.listTopics()?.values?.flatten() ?: emptyList()
        val externalTopics = info.filterNot { it.topic().startsWith("__") }
        val topics = externalTopics.map { TopicPartition(it.topic(), it.partition()) }
        val earliestOffsets = consumer.beginningOffsets(topics)
        val latestOffsets = consumer.endOffsets(topics)
        return topics.map {
            KafkaOffset(
                topic = it.topic(),
                partition = it.partition(),
                latest = latestOffsets[it] ?: EMPTY_OFFSET,
                earliest = earliestOffsets[it] ?: EMPTY_OFFSET
            )
        }
    }

    override fun close() {
        consumer.close(timeout)
    }
}