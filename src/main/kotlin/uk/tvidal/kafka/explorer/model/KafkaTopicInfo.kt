package uk.tvidal.kafka.explorer.model

import java.lang.String.CASE_INSENSITIVE_ORDER

data class KafkaTopicInfo(
    val name: String,
    val partition: Int,
    val earliest: Long,
    val latest: Long
) : Comparable<KafkaTopicInfo> {

    override fun compareTo(other: KafkaTopicInfo): Int {
        val compare = CASE_INSENSITIVE_ORDER.compare(name, other.name)
        return if (compare == 0) other.partition - partition else compare
    }
}
