package uk.tvidal.kafka.explorer.controller

import tornadofx.*
import uk.tvidal.kafka.explorer.kafka.KafkaClientService
import uk.tvidal.kafka.explorer.kafka.KafkaService
import uk.tvidal.kafka.explorer.kafka.NoOpKafkaService
import uk.tvidal.kafka.explorer.log
import uk.tvidal.kafka.explorer.message.ConnectRequest
import uk.tvidal.kafka.explorer.message.ConnectedEvent
import uk.tvidal.kafka.explorer.model.KafkaBroker

class KafkaController : Controller() {

    private var kafka: KafkaService = NoOpKafkaService

    init {
        subscribe<ConnectRequest> { connect(it.broker) }
    }

    private fun connect(broker: KafkaBroker) {
        kafka.close()
        kafka = KafkaClientService(broker)
        log { "Connected to $broker" }
        val data = kafka.list()
        fire(ConnectedEvent(data))
    }
}