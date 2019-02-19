package uk.tvidal.kafka.explorer

import javafx.scene.image.Image
import tornadofx.*
import uk.tvidal.kafka.explorer.view.TopicsView

class KafkaExplorer : App(Image(kafkaLogo.toExternalForm()), TopicsView::class) {

    init {
        System.setProperty("log.level", "WARN")
        FX.stylesheets += styleSheet.toExternalForm()
        reloadStylesheetsOnFocus()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch<KafkaExplorer>(args)
        }
    }
}