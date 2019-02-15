package uk.tvidal.kafka.explorer.model

import org.apache.kafka.clients.consumer.ConsumerRecord
import java.time.Instant

data class KafkaMessage(
    val value: String,
    val key: Any?,
    val topic: String,
    val partition: Int,
    val offset: Long,
    val timestamp: Instant
) {
    constructor(record: ConsumerRecord<*, String>) : this(
        value = record.value(),
        key = record.key(),
        topic = record.topic(),
        partition = record.partition(),
        offset = record.offset(),
        timestamp = Instant.ofEpochMilli(record.timestamp())
    )
}