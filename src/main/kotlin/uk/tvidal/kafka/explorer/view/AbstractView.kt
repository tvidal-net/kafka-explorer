package uk.tvidal.kafka.explorer.view

import javafx.event.EventTarget
import tornadofx.*

abstract class AbstractView(title: String) : View(title) {

    protected fun EventTarget.fadedText(text: String) = text(text) {
        styleClass += "faded"
    }

    protected fun EventTarget.boldText(text: String) = text(text) {
        styleClass += "bold"
    }
}