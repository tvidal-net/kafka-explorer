package net.tvidal.kafka.explorer.message

import net.tvidal.kafka.explorer.model.KafkaOffset

data class ConnectedEvent(val data: Collection<KafkaOffset>) : EventMessage()