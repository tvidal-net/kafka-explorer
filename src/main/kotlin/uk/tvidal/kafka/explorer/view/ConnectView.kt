package uk.tvidal.kafka.explorer.view

import javafx.geometry.Pos.CENTER
import javafx.geometry.Pos.CENTER_RIGHT
import javafx.scene.paint.Color
import tornadofx.*
import uk.tvidal.kafka.explorer.controller.KafkaController
import uk.tvidal.kafka.explorer.model.KafkaBroker

class ConnectView : AbstractView("Connect to Kafka") {

    private val kafka: KafkaController by inject()

    private val broker = textfield(KafkaBroker().toString())

    override val root = stackpane {
        style {
            backgroundColor = multi(Color.BLACK)
        }

        vbox(8, CENTER) {
            padding = insets(24)
            maxWidth = 480.0

            label("Kafka Host:Port")
            add(broker)

            hbox(8, CENTER_RIGHT) {

                button("Cancel") {
                    isCancelButton = true
                    action { back() }
                }

                button("Connect") {
                    isDefaultButton = true
                    action {
                        val broker = KafkaBroker.of(broker.text)
                        kafka.broker = broker
                        back()
                    }
                }
            }
        }
    }

    private fun back() {
        replaceWith<TopicsView>()
    }
}