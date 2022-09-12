package coroutineguide.cg001

/*
 * Copyright 2016-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */


import kotlinx.coroutines.*

suspend fun getRemoteValue() = 5 //just a demo fn

fun main() {
    println("Starting main...!")
    runBlocking { // this: CoroutineScope
        //This is a courutineBlock, you can call suspend functions
        val valueFromSuspendFn  = getRemoteValue()
        delay(1000L) // You can use delay

        //And you can call create other coroutines including the one created with
        //the launch coroutine buildeer
        val job = launch { // launch a new coroutine and continue
            delay(3000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("a second later, value from suspendFn is: $valueFromSuspendFn")
        job.cancel("I want to cancel this coroutine") //"World wont be printed as a result"

        val job2 = launch { // launch a new coroutine and continue
            delay(2000L) // non-blocking delay for 1 second (default time unit is ms)
            println("Job 2 excecuted!") // print after delay
        }
        job2.join() // wait until child coroutine completes
        println("I waited until job completed")
    }
    println("im outside of the coroutine")

    // Lets try another one:
}


/**
 * This one blocks and returns the expected computation, we usally use runBlocking in the edges of the system
 * (where the 'nonPure' runtime gets injected)
 * https://kotlinlang.org/docs/coroutines-basics.html#scope-builder
 * ... runBlocking and coroutineScope builders may look similar because they both wait for their body and all its children to complete.
 * The main difference is that the runBlocking method blocks the current thread for waiting,
 * while coroutineScope just suspends, releasing the underlying thread for other usages.
 * Because of that difference, runBlocking is a regular function and coroutineScope is a suspending function.
 */
fun blockingProgram() =
    runBlocking { getRemoteValue() }

/**
 * This is how to excecute in a non fashion way
 * we get the Future/Promise version of kotlin called Deferred
 * Basically async wraps the return value in a Deferred<Int>
 * ... TODO could we Bind to those using regular kotlin coRoutines, or do we need Arrow for that?
 */
suspend fun nonBlockingProgramAsync(): Deferred<Int> =
    coroutineScope { async { getRemoteValue() } }

/**
 * With this one we loose the async nature is still sequential (continuation executed)
 * See coroutines intro 2017 kotlin conf by Roman Elizarov https://youtu.be/_hfBv0a09Jc?t=1507
 */
suspend fun nonBlockingProgram(): Int = coroutineScope { getRemoteValue() }

fun blockingProgram2(): Int = runBlocking { nonBlockingProgram() }

suspend fun launchBuilderInsideRunBlocking(): Job = runBlocking { launch { getRemoteValue() } }
//This one doesnt compile launch is undefined as we are not within CoroutineScope, so we cant execute the fn CoroutineScope.launch
// suspend fun launchBuilderOutside = launch { getRemoteValue() } //TODO Remove to see compilation error
