package dev.jdev.katas.hr.prepare.algorithms.strings001.dnahealth

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.*
import kotlin.io.*
import kotlin.text.*


/**
 * https://www.hackerrank.com/challenges/determining-dna-health
 *
 * Notes:
 * DNA:List<Gene>
 * Gene.healthValue
 * DNA.health = DNA.map(healthValue).sum()
 *
 * We represent genes and DNA as non-empty strings of lowercase English alphabetic letters, and the same gene may appear multiple times as a susbtring of a DNA.
 * Given the following:
 *  - An array of beneficial gene strings, . Note that these gene sequences are not guaranteed to be distinct.
 *  - An array of gene health values, , where each  is the health value for gene .
 *  - A set of  DNA strands where the definition of each strand has three components, start, end, and d,
 *  where string  is a DNA for which genes gstart,..., gend are healthy.
 *
 *  Find and print the respective total healths of the unhealthiest (minimum total health)
 *  and healthiest (maximum total health) strands of DNA as two space-separated values on a single line.
 */
fun main(args: Array<String>)  {

    val tpool = Executors.newCachedThreadPool()

    val n = readLine()!!.trim().toInt()
    val genes = readLine()!!.trimEnd().split(" ").toTypedArray()
    val health = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()
    val genesWithHealth = genes.zip(health)
    val s = readLine()!!.trim().toInt()
    var allHealthValues = mutableListOf<Int>()
    for (sItr in 1..s) {
        val firstMultipleInput = readLine()!!.trimEnd().split(" ")
        val first = firstMultipleInput[0].toInt()
        val last = firstMultipleInput[1].toInt()
//        println("Frist $first Last $last")
        val d = firstMultipleInput[2]
        tpool.submit(Runnable {
            val t = processStrand(first,last,d,genesWithHealth)
            allHealthValues.add(t)
            return@Runnable
        })
    }
    tpool.shutdown() //wait for everything to finish
    try {
        tpool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)
        var max = 0
        var min = 0

        allHealthValues.forEach {
            if(it != null){
                max = if(it >= max) it else max
                min = if(it <= min) it else min
            }
        }
        print("R::: ${min} ${max}")

    } catch (e: InterruptedException) {
        return
    }
    println(allHealthValues)

}

fun processStrand(first: Int, last: Int, d: String, genesWithHealth: List<Pair<String, Int>>): Int {
    val genesWithHealth = genesWithHealth.subList(first, last + 1)
    return d.geneSubsets
        .flatMap { gs -> healthValues(genesWithHealth, gs) }
        .sum()
}

/**
 * Finds the longest sequence it can find in the map
 * caaab => [caaab, caaa, caa, ca, c]
 */
fun healthValues(genesHealthSubMap: List<Pair<String, Int>>, gs: String): Sequence<Int> {
    val r = (gs.indices.reversed()).asSequence()
        .map { gs.substring(0,it+1) }
        //.also { println("==== $it") }
        .filter { g:String -> genesHealthSubMap.any{ it.first == g } }
        .flatMap { genesHealthSubMap.filter { p-> p.first == it }
            .map { p -> p.second }
        }
    return r
}

/**
 * for gene "caaab" => it returns ["caaab", "aaab", "aab", "ab", "b"]
 */
val String.geneSubsets: Sequence<String>
    get() {
        return (indices).asSequence()
            .map { this.substring(it, this.lastIndex+1) }
    }

/**
* sample input 0

6
a b c aa d b    {the genes}
1 2 3 4 5 6     {gene health values}
3               {number of strands to process}
1 5 caaab       {strand, [start end d] } => healtyGenes=Gstart ... Gend
0 4 xyz
2 4 bcdybc

6
a b c aa d b
1 2 3 4 5 6
3
1 5 caaab
0 4 xyz
2 4 bcdybc
*/


