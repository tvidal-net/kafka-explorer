package uk.tvidal.kafka.explorer

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {

    companion object {

        val border by cssclass()
        val faded by cssclass()

        val defaultPadding = box(8.px)
    }

    init {
        border {
            padding = defaultPadding
        }

        faded {
            fill = Color.GRAY
        }
    }
}