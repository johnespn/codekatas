package dev.jdev.katas.hr.prepare.algorithms.warmup003.averybigsum

import java.math.*
import kotlin.collections.*
import kotlin.io.*
import kotlin.text.*

/*
 * https://www.hackerrank.com/challenges/a-very-big-sum
 * Complete the 'aVeryBigSum' function below.
 *
 * The function is expected to return a LONG_INTEGER.
 * The function accepts LONG_INTEGER_ARRAY ar as parameter.
 */

fun aVeryBigSum(ar: Array<Long>): Long {
    val res =  ar.fold(BigInteger.ZERO) { acc, next ->
        acc.add(next.toBigInteger())
    }
    return res.toLong()
}

fun main(args: Array<String>) {
    val arCount = readLine()!!.trim().toInt()

    val ar = readLine()!!.trimEnd().split(" ").map{ it.toLong() }.toTypedArray()

    val result = aVeryBigSum(ar)

    println(result)
}
