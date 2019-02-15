package net.tvidal.kafka.explorer.kafka

object NoOpKafkaService : KafkaService {
    override fun close() = Unit
}