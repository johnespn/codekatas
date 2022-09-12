package coroutinesfn

import java.time.LocalDateTime


fun main() {

    //0. Lets define some functions
    fun times2Fn (i:Int)= i * 2
    fun squareFn (i:Int) =  i * i

    /**
     * 1. Now lets just run them and print the results in a typical scenario
     */
    println("1. ==================")
    val a = times2Fn( 10 )
    println("a is $a")
    val b = squareFn(10)
    println("b is $b")
    println( "1. Sync Executed...")

    /**
     * 2. Let's create the async version of these functions that execute the callback once they are finished
     */
    println("\n2. ==================")
    fun doubleAsync (p:Int) = executeAsyncWithCallback( 100, ::times2Fn, p) { a -> println("a is $a") }
    fun squareFnAsync (p:Int) = executeAsyncWithCallback(50, ::squareFn, p) { b -> println("b is $b") }
    // And now we will run them.
    doubleAsync(10)
    squareFnAsync(10)
    println( "2. Async Executed...")

    Thread.sleep(160) //Just so we wait for the async to be executed

    /**
     * 3. let's compose the sync ones
     */
    println("\n3. ==================")
    val times2 = times2Fn(10)
    println( "times2: $times2")
    val squareAfterTimes2 = squareFn(times2)
    println( "squareAfterTimes2: $squareAfterTimes2")

    /**
     * 4. let's compose the callback versions, now we print in the right order, but the code is quite messy
     */
    println("\n4. ==================")
    executeAsyncWithCallback(10, {a -> a*3}, 2, fun(i: Int) {
        println("1st function called at ${LocalDateTime.now()}")
        println("accumulated value is $i")
        executeAsyncWithCallback(100, { b -> b*5}, i, fun(j: Int) {
            println("2nd function called at ${LocalDateTime.now()}")
            println("accumulated value is $j")
            executeAsyncWithCallback(10, {c -> c*7}, j, fun(k: Int) {
                println("3rd function called at ${LocalDateTime.now()}")
                executeAsyncWithCallback(10, {d-> d*11}, k, fun(l: Int) {
                    println("4th function called at ${LocalDateTime.now()}")
                    println("Computation final value is $l")
                })
            })
        })
    })

    Thread.sleep(320) //Just so we wait for the async to be executed

    /**
     * 5. Can we emulate kotlin's suspend?
     */
    val p1 = 10
    val f1 = makeAsync(10)(){ it*2 }
    val f2 = makeAsync(50)(){ it*it }
    Thread.sleep(500) //Just so we wait for the async to be executed

    println("\n5.1. ==================")
    executeContinuationPassingStyle(p1,f1,f2){  println("2 fn composition result is $it") }
    Thread.sleep(580) //Just so we wait for the async to be executed

    println("\n5.2. ==================")
    executeContinuationPassingStyle(p1,f1,f2,f2){  println("3 fin composition result  is $it") }
    Thread.sleep(880) //Just so we wait for the async to be executed

    println("\n5.3. ==================")
    executeContinuationPassingStyle(p1, listOf(f1,f2,f2)){  println("L3 squareFnAfterTimes2Fn is $it") }
    Thread.sleep(1200) //Just so we wait for the async to be executed

//    println("\n100. ==================")
//    executeContinuationPassingStyle(2, listOf(
//        async10{
//            println("1st function called at ${LocalDateTime.now()}")
//            it * 3
//        },
//        async10{
//            println("2nd function called at ${LocalDateTime.now()}")
//            it * 5
//        },
//        async10{
//            println("3rd function called at ${LocalDateTime.now()}")
//            it * 7
//        },
//        async10{
//            println("4th function called at ${LocalDateTime.now()}")
//            it * 11
//        }
//    )){res ->
//        println("Computation final value is $res")
//    }
//    println("Composed function will start to execute now ${LocalDateTime.now()}")

    println("\n100. ==================")





//    Thread.sleep(1200) //Just so we wait for the async to be executed
//
//    println("\n4. ==================")
//    executeAsyncWithCallback(10, {a -> a*3}, 2, fun(i: Int) {
//        println("1st function called at ${LocalDateTime.now()}")
//        println("accumulated value is $i")
//        executeAsyncWithCallback(100, { b -> b*5}, i, fun(j: Int) {
//            println("2nd function called at ${LocalDateTime.now()}")
//            println("accumulated value is $j")
//            executeAsyncWithCallback(10, {c -> c*7}, j, fun(k: Int) {
//                println("3rd function called at ${LocalDateTime.now()}")
//                executeAsyncWithCallback(10, {d-> d*11}, k, fun(l: Int) {
//                    println("4th function called at ${LocalDateTime.now()}")
//                    println("Computation final value is $l")
//                })
//            })
//        })
//    })

}
