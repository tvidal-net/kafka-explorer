package net.tvidal.kafka.explorer.model

interface KafkaService {

    fun list(): Collection<KafkaOffset> = emptyList()

    fun read(from: KafkaOffset, count: Int = Int.MAX_VALUE): Collection<KafkaMessage<Nothing?, String>> = emptyList()
}