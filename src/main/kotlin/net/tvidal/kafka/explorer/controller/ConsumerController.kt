package net.tvidal.kafka.explorer.controller

import net.tvidal.kafka.explorer.codec.NullCodec
import net.tvidal.kafka.explorer.model.Broker
import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import tornadofx.*

class ConsumerController : Controller() {

    val model: Broker.Model by inject()

    val broker: Broker get() = model.item

    private val codec: Serde<String> = Serdes.String()

    private var _consumer: Consumer<Nothing, String>? = null

    init {
        model.brokerProperty.addListener { _ -> close() }
    }

    val consumer get() = _consumer ?: createConsumer().also { _consumer = it }

    private fun createConsumer(): Consumer<Nothing, String> {
        val config = mapOf(
            BOOTSTRAP_SERVERS_CONFIG to broker.broker.split(','),
            CLIENT_ID_CONFIG to this::class.simpleName,
            GROUP_ID_CONFIG to this::class.simpleName,
            ENABLE_AUTO_COMMIT_CONFIG to false
        )
        return KafkaConsumer(config, NullCodec, codec.deserializer())
    }

    private fun close() {
        _consumer?.close()
        _consumer = null
    }
}
