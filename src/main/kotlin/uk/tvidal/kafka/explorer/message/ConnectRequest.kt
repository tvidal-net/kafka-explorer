package uk.tvidal.kafka.explorer.message

import uk.tvidal.kafka.explorer.model.KafkaBroker

data class ConnectRequest(val broker: KafkaBroker) : RequestMessage()