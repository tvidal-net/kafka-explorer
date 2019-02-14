package net.tvidal.kafka.explorer.kafka

import net.tvidal.kafka.explorer.codec.NullCodec
import net.tvidal.kafka.explorer.codec.StringCodec
import net.tvidal.kafka.explorer.model.KafkaBroker
import net.tvidal.kafka.explorer.model.KafkaMessage
import net.tvidal.kafka.explorer.model.KafkaOffset
import net.tvidal.kafka.explorer.model.KafkaService
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import java.time.Duration
import java.time.Instant

class DefaultKafkaService(broker: KafkaBroker) : KafkaService {

    companion object {
        private val timeout = Duration.ofSeconds(1)
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
        val offsets = consumer.endOffsets(topics)
        return offsets.map { (it, offset) ->
            KafkaOffset(
                topic = it.topic(),
                partition = it.partition(),
                offset = offset
            )
        }
    }

    override fun read(from: KafkaOffset, count: Int): Collection<KafkaMessage<Nothing?, String>> {
        val topicPartition = from.run { TopicPartition(topic, partition) }
        consumer.assign(listOf(topicPartition))
        return try {
            consumer.seek(topicPartition, from.offset)
            sequence {
                while (true) {
                    for (it in consumer.poll(timeout)) {
                        yield(
                            KafkaMessage(
                                value = it.value(),
                                key = it.key(),
                                topic = it.topic(),
                                partition = it.partition(),
                                offset = it.offset(),
                                timestamp = Instant.ofEpochMilli(it.timestamp())
                            )
                        )
                    }
                }
            }.take(count).toList()
        } finally {
            consumer.unsubscribe()
        }
    }
}