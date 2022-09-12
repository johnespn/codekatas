package coroutinesfn

import java.time.LocalDateTime

fun main() {

    val square = {a:Int -> a*a}
    val squareAsync :(Int) -> (Callback) -> Unit = square.aasync()

    //3. With suspend  emulator, no stairway, the composing function takes care of orchestration
    executeContinuationPassingStyleA(2,
        {
            println("step1");
            3*it
        },
        {
            println("step2");
            3*it
        },
        {
            println("step3");
            3*it
        },
        {
            println("step4");
            3*it
        },

    ){res ->
        println("Computation final value is $res")
    }
    println("\nComposed function will start to execute now ${LocalDateTime.now()}")

}

private fun <R> Function<R>.aasync(): (Int) -> ((Int) -> Unit) -> Unit {
    val t = this as (Int) -> Int
    return async10(t)
}


