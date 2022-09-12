package coroutinesfn

import java.time.LocalDateTime

fun main() {

    //1. With anonymous functions "callback hell"
    times3(2)(fun(v1){
        times5(v1)(fun(v2){
            times7(v2)(fun(v3){
                times11(v3)(fun (v4){
                    println("Computation final value is $v4")
                })
            })
        })
    })
    println("\nCallback version will start to execute now ${LocalDateTime.now()}\n")

    Thread.sleep(1000) //Just so we wait for the async to be executed

    //2. With lambdas, pretty much the same problem
    times3(2)(){v1 ->
        times5(v1)(){v2 ->
            times7(v2)(){v3 ->
                times11(v3)(){v4 ->
                    println("Computation final value is $v4")
                }
            }
        }
    }
    println("\nCallback stairway to hell function will start to execute now ${LocalDateTime.now()}\n")

    Thread.sleep(1000) //Just so we wait for the async to be executed

    //3. With suspend  emulator, no stairway, the composing function takes care of orchestration
    executeContinuationPassingStyle(2,
        times3,
        times5,
        times7,
        times11
    ){res ->
        println("Computation final value is $res")
    }
    println("\nComposed function will start to execute now ${LocalDateTime.now()}")
}