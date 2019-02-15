package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import javafx.scene.control.ListView
import javafx.scene.input.KeyCode
import javafx.scene.text.FontWeight.BOLD
import tornadofx.*
import uk.tvidal.kafka.explorer.Styles
import uk.tvidal.kafka.explorer.controller.KafkaController
import uk.tvidal.kafka.explorer.model.KafkaBroker

class TopicsView : View("Kafka Explorer") {

    val kafka: KafkaController by inject()

    override val root = borderpane {
        setPrefSize(1024.0, 768.0)

        left = topics()
        center = stream()
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

        items.onChange { scrollToBottom() }

        cellFormat {
            graphic = vbox {
                hbox {
                    boldText("${it.offset} ")
                    fadedText(it.timestamp.toString())
                    it.key?.let { key -> text(" (key: $key)") }
                }
                text(it.value) {
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

    private fun EventTarget.fadedText(text: String) = text(text) {
        addClass(Styles.faded)
    }

    private fun EventTarget.boldText(text: String) = text(text) {
        style {
            fontWeight = BOLD
        }
    }

    private fun ListView<*>.scrollToBottom() {
        if (selectionModel.isEmpty)
            scrollTo(items.size - 1)
    }
}