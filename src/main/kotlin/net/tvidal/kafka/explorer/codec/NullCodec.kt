package net.tvidal.kafka.explorer.codec

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer

object NullCodec : Deserializer<Nothing>, Serializer<Nothing> {

    private val empty = ByteArray(0)

    override fun configure(config: MutableMap<String, *>?, flag: Boolean) = Unit
    override fun close() = Unit

    override fun deserialize(topic: String, data: ByteArray?) = null

    override fun serialize(topic: String, data: Nothing?) = empty
}
