package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import javafx.geometry.Pos.CENTER
import tornadofx.*
import uk.tvidal.kafka.explorer.Styles.Companion.borderPadding
import uk.tvidal.kafka.explorer.model.Broker

class ConnectView : View("Kafka Connection") {

    val model: Broker.Model by inject()

    override val root = borderpane {
        addClass(borderPadding)
        prefWidth = 480.0

        center {
            form {
                fieldset("Kafka Brokers:") {
                    textfield(model.brokerProperty)
                }

                buttonBar()
            }
        }
    }

    private fun EventTarget.buttonBar() {
        hbox(36.0, CENTER) {

            button("Close") {
                isCancelButton = true
                setOnAction { close() }
            }

            button("Connect") {
                isDefaultButton = true
                setOnAction {
                    model.commit()
                    switchTo<MainView>()
                }
            }
        }
    }
}
