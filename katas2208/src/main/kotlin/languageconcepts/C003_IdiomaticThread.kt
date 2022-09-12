package languageconcepts

import kotlin.concurrent.thread

fun main(){
    thread {
        Thread.sleep(1000)
        println("Hello from a different thread")
    }
    println("Hello from the main thread")
}