package coroutinesfn

import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

typealias Callback = (Int)-> Unit
typealias MathFn = (Int)-> Int

val async10  =
    {m:MathFn -> {a:Int -> {c:Callback ->
        executeAsyncWithCallback(10,m,a,c)
    } } }

val times3 = async10{
    println("1st function called at ${LocalDateTime.now()}")
    it * 3
}
val times5 = async10{
    println("2nd function called at ${LocalDateTime.now()}")
    it * 5
}
val times7 = async10{
    println("3rd function called at ${LocalDateTime.now()}")
    it * 7
}
val times11 = async10{
    println("4th function called at ${LocalDateTime.now()}")
    it * 11
}

fun executeContinuationPassingStyleA(p1: Int, vararg program: (Int) -> (Int), effect: Callback) =
    executeContinuationPassingStyle(p1, program.toList().map(async10), effect)

fun executeContinuationPassingStyle(p1: Int, vararg program: (Int) -> (Callback) -> Unit, effect: Callback) =
    executeContinuationPassingStyle(p1, program.toList(), effect)

fun executeContinuationPassingStyle(p1: Int, program: List<(Int) -> (Callback) -> Unit>, effect: Callback) {
    val composed = program.reduceRight {
        a, b ->
        { p1 -> { cb1 ->
            //cb1(p1)
            val e1 = a(p1)
            e1 { i2 -> b(i2)(effect) }
        } }
    }
    composed(p1)(effect)
}

fun executeContinuationPassingStyle(
    p1: Int,
    f1: (Int) -> (Callback) -> Unit,
    f2: (Int) -> (Callback) -> Unit,
    f3: (Int) -> (Callback) -> Unit,
    effect: Callback
) {
    f1(p1)() {  a-> f2(a)() { x -> f3(x)(effect) } }
}

fun executeContinuationPassingStyle(
    p1: Int,
    f1: (Int) -> (Callback) -> Unit,
    f2: (Int) -> (Callback) -> Unit,
    final: Callback
) {
    f1(p1)() {  a-> f2(a)(final) }
}



//Utility function to run the callback in a separate thread
fun executeAsyncWithCallback(delay:Long, asyncFn: MathFn, param:Int, callback: Callback): Unit {
    val executorService = Executors.newCachedThreadPool()
    executorService.submit {
        Thread.sleep(delay)
        callback( asyncFn (param) )
    }
    executorService.shutdown()
}

val makeAsync  =
    { i:Long -> {m:MathFn -> {a:Int -> {c:Callback ->
        executeAsyncWithCallback(i,m,a,c)
    } } } }

fun executeAsyncWithCallbackPartial (delay:Long, asyncFn: (Int)-> Int , param:Int ) =
   { callback: Callback -> executeAsyncWithCallback(delay,asyncFn, param,callback) }


fun executeAsyncWithCallbackPartialP2 (delay:Long, asyncFn: (Int)-> Int) =
    run {
        { param:Int -> executeAsyncWithCallbackPartial(delay, asyncFn, param) }
    }


//Completable used to return future value...
fun executeAsyncWithFuture( delay:Long, asyncFn: (Int)-> Int, param:Int, callback: Callback): CompletableFuture<Int> {
    val completableFuture = CompletableFuture<Int>()
    val executorService = Executors.newCachedThreadPool()
    executorService.submit {
        Thread.sleep(delay)
        completableFuture.complete( asyncFn (param) )
    }
    executorService.shutdown()
    return completableFuture
}