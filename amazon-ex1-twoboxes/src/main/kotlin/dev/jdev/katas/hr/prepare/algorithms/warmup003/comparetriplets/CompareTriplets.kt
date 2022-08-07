package dev.jdev.katas.hr.prepare.algorithms.warmup003.comparetriplets

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
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*

/*
 * https://www.hackerrank.com/challenges/compare-the-triplets
 * Complete the 'compareTriplets' function below.
 *
 * The function is expected to return an INTEGER_ARRAY.
 * The function accepts following parameters:
 *  1. INTEGER_ARRAY a
 *  2. INTEGER_ARRAY b
 */

fun compareTriplets(a: Array<Int>, b: Array<Int>): Array<Int> {
    val zip = a.zip(b)
    val emptyScore = Pair(0, 0)
    val awardPintsFn = { acc: Pair<Int, Int>, next: Pair<Int, Int> ->
        val comparison = next.first.compareTo(next.second)
        when {
            comparison > 0 -> Pair(acc.first.inc(), acc.second)
            comparison < 0 -> Pair(acc.first, acc.second.inc())
            else -> acc
        }
    }
    val results = zip.fold(emptyScore, awardPintsFn)
    return arrayOf(results.first, results.second)
}

fun main(args: Array<String>) {

    val a = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()

    val b = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()

    val result = compareTriplets(a, b)

    println(result.joinToString(" "))
}
