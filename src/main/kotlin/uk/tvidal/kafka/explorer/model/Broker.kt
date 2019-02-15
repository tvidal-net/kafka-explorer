package uk.tvidal.kafka.explorer.model

import tornadofx.*

data class Broker(var broker: String) {

    class Model : ItemViewModel<Broker>(Broker("localhost:9092")) {
        val brokerProperty = bind(Broker::broker)
    }
}
