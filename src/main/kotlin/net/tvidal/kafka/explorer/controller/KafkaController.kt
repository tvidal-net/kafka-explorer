package net.tvidal.kafka.explorer.controller

import net.tvidal.kafka.explorer.kafka.KafkaClientService
import net.tvidal.kafka.explorer.kafka.KafkaService
import net.tvidal.kafka.explorer.kafka.NoOpKafkaService
import net.tvidal.kafka.explorer.message.ConnectRequest
import net.tvidal.kafka.explorer.message.ConnectedEvent
import net.tvidal.kafka.explorer.model.KafkaBroker
import tornadofx.*

class KafkaController : Controller() {

    private var kafka: KafkaService = NoOpKafkaService

    init {
        subscribe<ConnectRequest> { connect(it.broker) }
    }

    private fun connect(broker: KafkaBroker) {
        kafka.close()
        kafka = KafkaClientService(broker)
        val data = kafka.list()
        fire(ConnectedEvent(data))
    }
}