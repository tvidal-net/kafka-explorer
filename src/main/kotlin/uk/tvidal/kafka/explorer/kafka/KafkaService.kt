package uk.tvidal.kafka.explorer.kafka

import uk.tvidal.kafka.explorer.model.KafkaOffset

interface KafkaService : AutoCloseable {

    fun list(): Collection<KafkaOffset> = emptyList()
}