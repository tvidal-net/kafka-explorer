package net.tvidal.kafka.explorer.message

import net.tvidal.kafka.explorer.model.KafkaBroker

data class ConnectRequest(val broker: KafkaBroker) : RequestMessage()