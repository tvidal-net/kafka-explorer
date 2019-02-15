package uk.tvidal.kafka.explorer.model

data class KafkaBroker(
    val host: String = "127.0.0.1",
    val port: Int = 9092
) {
    override fun toString() = "$host:$port"
}