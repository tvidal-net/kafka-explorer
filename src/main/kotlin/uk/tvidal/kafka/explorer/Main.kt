package uk.tvidal.kafka.explorer

import tornadofx.*
import uk.tvidal.kafka.explorer.view.ConnectView

class KafkaExplorerApp : App(ConnectView::class, Styles::class) {

    init {
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    launch<KafkaExplorerApp>(args)
}
