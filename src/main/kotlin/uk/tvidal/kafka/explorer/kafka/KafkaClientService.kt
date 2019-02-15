package uk.tvidal.kafka.explorer.kafka

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import uk.tvidal.kafka.explorer.codec.NullCodec
import uk.tvidal.kafka.explorer.codec.StringCodec
import uk.tvidal.kafka.explorer.kafka.KafkaService.Companion.EMPTY_OFFSET
import uk.tvidal.kafka.explorer.model.KafkaBroker
import uk.tvidal.kafka.explorer.model.KafkaMessage
import uk.tvidal.kafka.explorer.model.KafkaTopicInfo
import java.time.Duration

class KafkaClientService(broker: KafkaBroker) : KafkaService {

    private val consumer: Consumer<Nothing?, String> = KafkaConsumer(
        mapOf(
            CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to broker.toString(),
            ConsumerConfig.GROUP_ID_CONFIG to "kafka-explorer",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 100
        ),
        NullCodec,
        StringCodec
    )

    override fun list(): List<KafkaTopicInfo> {
        val info = consumer.listTopics()?.values?.flatten() ?: emptyList()
        val externalTopics = info.filterNot { it.topic().startsWith("__") }
        val topics = externalTopics.map { TopicPartition(it.topic(), it.partition()) }
        val earliestOffsets = consumer.beginningOffsets(topics)
        val latestOffsets = consumer.endOffsets(topics)
        return topics
            .asSequence()
            .map {
                KafkaTopicInfo(
                    name = it.topic(),
                    partition = it.partition(),
                    latest = latestOffsets[it] ?: EMPTY_OFFSET,
                    earliest = earliestOffsets[it] ?: EMPTY_OFFSET
                )
            }.sorted()
            .toList()
    }

    override fun subscribe(topic: String, partition: Int, offset: Long) {
        val topicPartition = TopicPartition(topic, partition)
        consumer.assign(listOf(topicPartition))
        consumer.seek(topicPartition, offset)
    }

    override fun unsubscribe() = consumer.unsubscribe()

    override fun poll(): List<KafkaMessage> = if (consumer.assignment().isNotEmpty()) {
        consumer
            .poll(pollTimeout)
            .map(::KafkaMessage)
    } else emptyList()

    override fun close() {
        consumer.close(closeTimeout)
    }

    companion object {
        private val pollTimeout = Duration.ofMillis(100L)
        private val closeTimeout = Duration.ofSeconds(1L)
    }
}