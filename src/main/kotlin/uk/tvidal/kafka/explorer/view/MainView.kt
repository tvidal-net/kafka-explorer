package uk.tvidal.kafka.explorer.view

import javafx.scene.input.KeyCode.ESCAPE
import javafx.scene.input.KeyEvent
import javafx.scene.input.KeyEvent.KEY_PRESSED
import tornadofx.*
import uk.tvidal.kafka.explorer.model.Broker

class MainView : View("Kafka Explorer") {

    val broker: Broker.Model by inject()

    override val root = borderpane {
        setPrefSize(800.0, 600.0)

        center {
            label(broker.brokerProperty)
        }
    }

    private val scene get() = currentStage!!.scene!!

    private val cancelKeyHandler: (KeyEvent) -> Unit = {
        if (docked && it.code == ESCAPE) {
            switchBack<ConnectView>()
            it.consume()
        }
    }

    init {
        scene.addEventFilter(KEY_PRESSED, cancelKeyHandler)
    }

    @Volatile
    private var docked = false

    override fun onDock() {
        docked = true
    }

    override fun onUndock() {
        docked = false
    }
}
