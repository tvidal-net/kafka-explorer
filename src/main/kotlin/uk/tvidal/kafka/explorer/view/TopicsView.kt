package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import javafx.geometry.Pos.CENTER
import tornadofx.*
import uk.tvidal.kafka.explorer.Styles.Companion.borderPadding
import uk.tvidal.kafka.explorer.controller.KafkaController
import uk.tvidal.kafka.explorer.model.KafkaBroker

class TopicsView : View("Kafka Explorer") {

    val kafka: KafkaController by inject()

    private fun EventTarget.connectButton() = button("Connect") {
        action {
            val broker = KafkaBroker("127.0.0.1", 9092)
            kafka.broker.set(broker)
        }
    }

    private fun EventTarget.topics() = vbox {
        addClass(borderPadding)
        listview(kafka.topics) {
            kafka.topic.bind(selectionModel.selectedItemProperty())
        }

        hbox(8, CENTER) {
            alignment = CENTER
            connectButton()
        }
    }

    private fun EventTarget.stream() = listview(kafka.stream) {
        items.onChange {
            scrollTo(items.size - 1)
        }
    }

    override val root = borderpane {
        left = topics()
        center = stream()
    }
}