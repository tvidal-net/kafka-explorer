package net.tvidal.kafka.explorer.model

data class KafkaOffset(
    val topic: String,
    val partition: Int,
    val earliest: Long,
    val latest: Long
)