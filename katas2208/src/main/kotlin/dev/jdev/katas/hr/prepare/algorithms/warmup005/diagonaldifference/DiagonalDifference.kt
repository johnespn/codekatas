package dev.jdev.katas.hr.prepare.algorithms.warmup005.diagonaldifference
import java.io.*
import java.math.*
import java.security.*
import java.text.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.regex.*
import java.util.stream.*
import kotlin.collections.*
import kotlin.comparisons.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.jvm.functions.*
import kotlin.jvm.internal.*
import kotlin.math.absoluteValue
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*

/*
 * Complete the 'diagonalDifference' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts 2D_INTEGER_ARRAY arr as parameter.
 */

fun diagonalDifference(arr: Array<Array<Int>>): Int {
    val n=arr.size
    val rlDiagonal = (0 until n)
        .map { arr[it][it] }
//        .also { println("rlDiagonal: $it") }
        .sum()
    val lrDiagonal = ( 0 until n)
        .map { arr[n-1-it][it] }
//        .also { println("lrDiagonal--: $it") }
        .sum()
    return (lrDiagonal-rlDiagonal).absoluteValue()
}

fun Int.absoluteValue():Int = if(this>0) this else -1*this

fun main(args: Array<String>) {
    val n = readLine()!!.trim().toInt()

    val arr = Array<Array<Int>>(n, { Array<Int>(n, { 0 }) })

    for (i in 0 until n) {
        arr[i] = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()
    }

    val result = diagonalDifference(arr)

    println(result)
}
