package uk.tvidal.kafka.explorer.message

import uk.tvidal.kafka.explorer.model.KafkaOffset

data class ConnectedEvent(val data: Collection<KafkaOffset>) : EventMessage()