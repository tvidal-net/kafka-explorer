package uk.tvidal.kafka.explorer.kafka

object NoOpKafkaService : KafkaService {
    override fun close() = Unit
}