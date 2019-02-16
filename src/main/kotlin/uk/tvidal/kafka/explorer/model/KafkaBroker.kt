package uk.tvidal.kafka.explorer.model

import uk.tvidal.kafka.explorer.kafka.KafkaService.Companion.KAFKA_PORT

data class KafkaBroker(
    val host: String = "127.0.0.1",
    val port: Int = KAFKA_PORT
) {
    override fun toString() = "$host:$port"

    companion object {
        fun of(hostPort: String): KafkaBroker {
            val split = hostPort.split(':')
            return if (split.size > 1) KafkaBroker(split[0], split[1].toInt())
            else KafkaBroker(split[0])
        }
    }
}