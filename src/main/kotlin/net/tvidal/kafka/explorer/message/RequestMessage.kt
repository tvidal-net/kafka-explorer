package net.tvidal.kafka.explorer.message

import tornadofx.*
import tornadofx.EventBus.RunOn.BackgroundThread

abstract class RequestMessage : FXEvent(BackgroundThread)