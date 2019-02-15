package uk.tvidal.kafka.explorer.model

import java.time.Instant

data class KafkaMessage<K, V>(
    val value: V,
    val key: K,
    val topic: String,
    val partition: Int,
    val offset: Long,
    val timestamp: Instant
)