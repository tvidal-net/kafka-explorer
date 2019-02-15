package uk.tvidal.kafka.explorer.message

import tornadofx.*
import tornadofx.EventBus.RunOn.ApplicationThread

abstract class EventMessage : FXEvent(ApplicationThread)