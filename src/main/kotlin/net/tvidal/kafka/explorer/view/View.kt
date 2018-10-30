package net.tvidal.kafka.explorer.view

import javafx.util.Duration
import tornadofx.*
import tornadofx.ViewTransition.Direction.LEFT
import tornadofx.ViewTransition.Direction.RIGHT
import tornadofx.ViewTransition.Slide

val defaultDuration = Duration(333.0)

val slideLeft = Slide(defaultDuration, LEFT)
val slideRight = Slide(defaultDuration, RIGHT)

inline fun <reified T : UIComponent> UIComponent.switchTo() = switch<T>(slideLeft)
inline fun <reified T : UIComponent> UIComponent.switchBack() = switch<T>(slideRight)

inline fun <reified T : UIComponent> UIComponent.switch(transition: ViewTransition) = replaceWith<T>(transition, true, true)
