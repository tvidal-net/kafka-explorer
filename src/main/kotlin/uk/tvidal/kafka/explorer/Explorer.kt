package uk.tvidal.kafka.explorer

import java.lang.Thread.currentThread
import java.time.Instant
import java.util.Objects.toString

inline fun log(block: () -> Any?) {
    val now = Instant.now()
    val thread = currentThread().name
    val message = toString(block())
    println("$now [$thread] $message")
}