package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import javafx.scene.control.ListView
import tornadofx.*
import uk.tvidal.kafka.explorer.Styles

abstract class AbstractView(title: String) : View(title) {

    protected fun EventTarget.fadedText(text: String) = text(text) {
        addClass(Styles.faded)
    }

    protected fun EventTarget.boldText(text: String) = text(text) {
        addClass(Styles.bold)
    }

    protected fun ListView<*>.scrollToBottom() {
        if (selectionModel.isEmpty)
            scrollTo(items.size - 1)
    }
}