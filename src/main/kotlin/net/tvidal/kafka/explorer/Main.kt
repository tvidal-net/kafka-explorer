package net.tvidal.kafka.explorer

import net.tvidal.kafka.explorer.view.ConnectView
import tornadofx.*

class KafkaExplorerApp : App(ConnectView::class, Styles::class) {

    init {
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    launch<KafkaExplorerApp>(args)
}
