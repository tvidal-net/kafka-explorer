package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import javafx.geometry.Pos.BASELINE_CENTER
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.Modality.APPLICATION_MODAL
import javafx.stage.Stage
import javafx.stage.StageStyle.UTILITY
import tornadofx.*
import uk.tvidal.kafka.explorer.controller.KafkaController
import uk.tvidal.kafka.explorer.model.KafkaBroker

class TopicsView : AbstractView("Kafka Explorer") {

    val kafka: KafkaController by inject()

    override val root = borderpane {
        setPrefSize(1600.0, 1200.0)

        left = borderpane {
            center = topics()
            bottom = hbox(alignment = BASELINE_CENTER) {
                padding = insets(16)
                id = "connect-bar"
                button("Connect") {
                    action {
                        replaceWith<ConnectView>()
                    }
                }
            }
        }
        center = stream()
        bottom = label(kafka.statusMessageProperty)
    }

    init {
        kafka.broker = KafkaBroker()
    }

    private fun EventTarget.topics() = listview(kafka.topics) {
        kafka.topic.bind(selectionModel.selectedItemProperty())

        cellFormat {
            graphic = borderpane {
                left = text("${it.name}:${it.partition}")
                right = fadedText("${it.earliest} .. ${it.latest}")
            }
        }
    }

    private fun EventTarget.stream() = listview(kafka.stream) {

        items.onChange {
            scrollToBottom()
        }

        cellFormat {
            graphic = vbox {
                hbox {
                    boldText("${it.offset} ")
                    it.key?.let { key -> text("(key: $key) ") }
                    fadedText(it.timestamp.toString())
                }
                contentText(it.value) {
                    wrappingWidthProperty().bind(
                        this@listview.widthProperty().minus(48)
                    )
                }
            }
        }

        setOnKeyReleased {
            if (it.code == KeyCode.ESCAPE) {
                selectionModel.clearSelection()
                scrollToBottom()
            }
        }
    }
}