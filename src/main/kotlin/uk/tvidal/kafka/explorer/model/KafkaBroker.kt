package uk.tvidal.kafka.explorer.model

data class KafkaBroker(
    val host: String,
    val port: Int
) {
    override fun toString() = "$host:$port"
}