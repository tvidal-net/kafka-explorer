package uk.tvidal.kafka.explorer.model

data class KafkaTopicInfo(
    val name: String,
    val partition: Int,
    val earliest: Long,
    val latest: Long
)