package net.tvidal.kafka.explorer.kafka

import net.tvidal.kafka.explorer.model.KafkaOffset

interface KafkaService : AutoCloseable {

    fun list(): Collection<KafkaOffset> = emptyList()
}