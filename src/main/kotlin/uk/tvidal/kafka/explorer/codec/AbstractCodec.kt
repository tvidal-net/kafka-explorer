package uk.tvidal.kafka.explorer.codec

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer

abstract class AbstractCodec<T> : Serde<T>, Serializer<T>, Deserializer<T> {

    override fun configure(config: Map<String, *>, flag: Boolean) = Unit

    final override fun deserializer(): Deserializer<T> = this
    final override fun serializer(): Serializer<T> = this

    abstract override fun serialize(topic: String, data: T): ByteArray
    abstract override fun deserialize(topic: String, data: ByteArray): T

    override fun close() = Unit
}