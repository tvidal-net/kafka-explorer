package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import javafx.scene.control.ListView
import javafx.scene.text.Text
import tornadofx.*

abstract class AbstractView(title: String) : View(title) {

    protected fun EventTarget.fadedText(text: String) = text(text) {
        styleClass += "faded"
    }

    protected fun EventTarget.boldText(text: String) = text(text) {
        styleClass += "bold"
    }

    protected fun EventTarget.contentText(text: String, config: Text.() -> Unit) = text(text) {
        styleClass += "content"
        config(this)
    }

    protected fun ListView<*>.scrollToBottom() {
        if (selectionModel.selectedItem == null) {
            scrollTo(items.size - 1)
        }
    }
}