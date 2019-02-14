package net.tvidal.kafka.explorer.codec

object NullCodec : AbstractCodec<Nothing?>() {

    private val empty = ByteArray(0)

    override fun serialize(topic: String, data: Nothing?): ByteArray = empty
    override fun deserialize(topic: String, data: ByteArray): Nothing? = null
}