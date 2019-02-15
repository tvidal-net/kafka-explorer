package uk.tvidal.kafka.explorer

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

fun threadFactory(nameFormat: String): ThreadFactory = object : ThreadFactory {
    private val counter = AtomicInteger()
    override fun newThread(command: Runnable): Thread {
        val count = counter.incrementAndGet()
        val name = nameFormat.format(count)
        return Thread(command, name).apply {
            isDaemon = true
            setUncaughtExceptionHandler { _, e -> e.printStackTrace() }
        }
    }
}