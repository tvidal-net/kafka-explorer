package uk.tvidal.kafka.explorer.kafka

import uk.tvidal.kafka.explorer.model.KafkaMessage
import uk.tvidal.kafka.explorer.model.KafkaTopicInfo

interface KafkaService : AutoCloseable {

    fun list(): List<KafkaTopicInfo> = emptyList()

    fun subscribe(topic: String, partition: Int, offset: Long = EMPTY_OFFSET) = Unit
    fun unsubscribe() = Unit

    fun poll(): List<KafkaMessage> = emptyList()

    companion object {
        const val EMPTY_OFFSET = -1L
        const val KAFKA_PORT = 9092

        val NoOp = object : KafkaService {
            override fun close() = Unit
        }
    }
}