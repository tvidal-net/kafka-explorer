package uk.tvidal.kafka.explorer

import javafx.scene.image.Image
import tornadofx.*
import uk.tvidal.javafx.darkTheme
import uk.tvidal.kafka.explorer.view.TopicsView

class KafkaExplorer : App(Image("/kafka.png"), TopicsView::class) {

    init {
        FX.stylesheets += darkTheme.toExternalForm()
        FX.stylesheets += style.toExternalForm()
        reloadStylesheetsOnFocus()
    }

    companion object {

        private val style = KafkaExplorer::class.java
            .getResource("/style.css")

        @JvmStatic
        fun main(args: Array<String>) {
            launch<KafkaExplorer>(args)
        }
    }
}