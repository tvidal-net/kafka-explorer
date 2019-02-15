package uk.tvidal.kafka.explorer

import tornadofx.*
import uk.tvidal.kafka.explorer.view.TopicsView

class KafkaExplorer : App(TopicsView::class, Styles::class) {

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