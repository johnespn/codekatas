package languageconcepts

import arrow.core.compose
import arrow.core.reduceOrNull

// 5     =>  4     =>    3 =>     2 =>     1 =>    0
// [0,1] => [1,1] => [1,2] => [2,3] => [3,5] => [5,8] => ...
fun fibWithFold2 (n:Int): Int? = (1..n).reduceOrNull(
    initial = { Pair(0,1) },
    operation = { p:Pair<Int,Int>, _ -> Pair(p.second, p.first+p.second) }
)?.second

fun fibWithFold(n: Int): Int =
    if (n == 0) 0 else (1..n)
        .reduceOrNull(
            initial = { Pair(0, 1) },
            operation = { p, _ -> Pair(p.second, p.first + p.second) }
        )!!.second

fun <A,B>foldPairNTimes(times:Int, initial:Pair<A,B>, operation:(Pair<A,B>) -> Pair<A,B>):B{
    var current = initial
    for (i in 1..times){
        current = operation(current)
    }
    return current.second
}

fun fibWithFoldPairNtimes(n: Int):Int =
    if (0 == n) 0 else
    foldPairNTimes(n, Pair(0, 1)) {
        Pair(it.second, it.first + it.second)
    }

//TODO doesent seem to work...chunck may not be the tool.
fun fibWithChunk(n: Int):Int =
    if (0 == n) 0 else
        (1..n).chunked(2).fold(Pair(0, 1)){
                a, _ -> Pair(a.second, a.first + a.second)
        }.second

fun <A>repeatComposing ( n:Int, initial: A, operation:(A) -> A):A {
    var current = initial
    for (i in 1..n){
        current = operation(current)
    }
    return current
}

fun fibWithRepeatComposing(n: Int):Int {
    return if (0 == n) 0 else
        repeatComposing( n, Pair(1,0)){
                a:Pair<Int,Int> -> Pair(a.second, a.first + a.second)
        }.second
}

//TODO can this be made a tailrec fn? should this be in place?
typealias ComposableFn<T> = (T) -> T
fun <T>ComposableFn<T>.runTimes(times:Int, initial:T):T{
    var current = initial
    for (i in 1..times){
        current = this(current)
    }
    return current
}

//Note that the originator pair is not (0,1) but (1,0), that works for n<= 0
fun fibWithRunTimes(n: Int):Int =
    { p:Pair<Int,Int> ->
        Pair(p.second, p.first + p.second)
    }.runTimes(n, Pair(1,0) ).second

fun <T>ComposableFn<T>.composeTimes(times:Int): ComposableFn<T> {
    var composed = this
    for (i in 1..times){
        composed = this.compose(composed)
    }
    return composed
}

fun fibWithComposeTimes(n: Int):Int =
    { p:Pair<Int,Int> ->
        Pair(p.second, p.first + p.second)
    }.composeTimes(n)( Pair(1,0) ).second



fun main (){
    run {  }
    println("fibWithFold(0) =  ${fibWithFold(0 )}")
    println("fibWithFold(1) =  ${fibWithFold(1 )}")
    println("fibWithFold(2) =  ${fibWithFold(2 )}")
    println("fibWithFold(3) =  ${fibWithFold(3 )}")
    println("fibWithFold(4) =  ${fibWithFold(4 )}")
    println("fibWithFold(5) =  ${fibWithFold(5 )}")
    println("fibWithFold(6) =  ${fibWithFold(6 )}")

    //TODO HAS SOME BUGS
    println("fibWithFoldPairNtimes(0) =  ${fibWithFoldPairNtimes(0 )}")
    println("fibWithFoldPairNtimes(1) =  ${fibWithFoldPairNtimes(1 )}")
    println("fibWithFoldPairNtimes(2) =  ${fibWithFoldPairNtimes(2 )}")
    println("fibWithFoldPairNtimes(3) =  ${fibWithFoldPairNtimes(3 )}")
    println("fibWithFoldPairNtimes(4) =  ${fibWithFoldPairNtimes(4 )}")
    println("fibWithFoldPairNtimes(5) =  ${fibWithFoldPairNtimes(5 )}")
    println("fibWithFoldPairNtimes(6) =  ${fibWithFoldPairNtimes(6 )}")

    println("fibWithRepeatComposing(0) =  ${fibWithRepeatComposing(0 )}")
    println("fibWithRepeatComposing(1) =  ${fibWithRepeatComposing(1 )}")
    println("fibWithRepeatComposing(2) =  ${fibWithRepeatComposing(2 )}")
    println("fibWithRepeatComposing(3) =  ${fibWithRepeatComposing(3 )}")
    println("fibWithRepeatComposing(4) =  ${fibWithRepeatComposing(4 )}")
    println("fibWithRepeatComposing(5) =  ${fibWithRepeatComposing(5 )}")
    println("fibWithRepeatComposing(6) =  ${fibWithRepeatComposing(6 )}")

    println("fibWithRunTimes(0) =  ${fibWithRunTimes(0 )}")
    println("fibWithRunTimes(1) =  ${fibWithRunTimes(1 )}")
    println("fibWithRunTimes(2) =  ${fibWithRunTimes(2 )}")
    println("fibWithRunTimes(3) =  ${fibWithRunTimes(3 )}")
    println("fibWithRunTimes(4) =  ${fibWithRunTimes(4 )}")
    println("fibWithRunTimes(5) =  ${fibWithRunTimes(5 )}")
    println("fibWithRunTimes(6) =  ${fibWithRunTimes(6 )}")

    println("fibWithComposeTimes(0) =  ${fibWithComposeTimes(0 )}")
    println("fibWithComposeTimes(1) =  ${fibWithComposeTimes(1 )}")
    println("fibWithComposeTimes(2) =  ${fibWithComposeTimes(2 )}")
    println("fibWithComposeTimes(3) =  ${fibWithComposeTimes(3 )}")
    println("fibWithComposeTimes(4) =  ${fibWithComposeTimes(4 )}")
    println("fibWithComposeTimes(5) =  ${fibWithComposeTimes(5 )}")
    println("fibWithComposeTimes(6) =  ${fibWithComposeTimes(6 )}")
}