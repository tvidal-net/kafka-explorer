package uk.tvidal.kafka.explorer

import javafx.scene.image.Image
import tornadofx.*
import uk.tvidal.kafka.explorer.view.TopicsView

private val icon = Image("/kafka.png")

class KafkaExplorer : App(icon, TopicsView::class, Styles::class) {

    init {
        reloadStylesheetsOnFocus()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch<KafkaExplorer>(args)
        }
    }
}