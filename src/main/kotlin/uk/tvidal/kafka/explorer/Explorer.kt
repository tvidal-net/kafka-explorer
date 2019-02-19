package uk.tvidal.kafka.explorer

import java.net.URL
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

val kafkaLogo: URL = KafkaExplorer::class.java
    .getResource("/img/kafka.png")

val styleSheet: URL = KafkaExplorer::class.java
    .getResource("/css/style.css")

fun threadFactory(nameFormat: String): ThreadFactory = object : ThreadFactory {
    private val counter = AtomicInteger()
    override fun newThread(command: Runnable): Thread {
        val count = counter.incrementAndGet()
        val name = String.format(nameFormat, count)
        return Thread(command, name).apply {
            isDaemon = true
            setUncaughtExceptionHandler { _, e -> e.printStackTrace() }
        }
    }
}