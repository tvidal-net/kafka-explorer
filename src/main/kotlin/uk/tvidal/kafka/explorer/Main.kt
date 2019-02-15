package uk.tvidal.kafka.explorer

import tornadofx.*
import uk.tvidal.kafka.explorer.view.TopicsView

class KafkaExplorerApp : App(TopicsView::class, Styles::class) {

    init {
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    launch<KafkaExplorerApp>(args)
}
