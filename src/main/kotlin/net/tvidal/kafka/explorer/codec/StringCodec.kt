package net.tvidal.kafka.explorer.codec

import java.nio.charset.Charset

object StringCodec : AbstractCodec<String>() {

    private val charset: Charset = Charset.defaultCharset()

    override fun serialize(topic: String, data: String): ByteArray = data.toByteArray(charset)
    override fun deserialize(topic: String, data: ByteArray) = String(data, charset)
}