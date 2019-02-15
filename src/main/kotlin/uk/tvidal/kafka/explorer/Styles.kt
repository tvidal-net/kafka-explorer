package uk.tvidal.kafka.explorer

import tornadofx.*

class Styles : Stylesheet() {

    companion object {

        val borderPadding by cssclass()

        val defaultPadding = box(36.px)
    }

    init {
        borderPadding {
            padding = defaultPadding
        }
    }
}
