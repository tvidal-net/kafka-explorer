package uk.tvidal.kafka.explorer.view

import tornadofx.*
import uk.tvidal.kafka.explorer.Styles.Companion.borderPadding

class TopicsView : View() {

    override val root = vbox {
        addClass(borderPadding)
    }
}
