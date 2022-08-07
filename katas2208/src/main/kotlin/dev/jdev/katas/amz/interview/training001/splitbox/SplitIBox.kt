package dev.jdev.katas.amz.interview.training

import kotlin.collections.*
import kotlin.io.*
import kotlin.text.*

/*
 * Complete the 'minimalHeaviestSetA' function below.
 *
 * The function is expected to return an INTEGER_ARRAY.
 * The function accepts INTEGER_ARRAY arr as parameter.
 *
 * <h1>Optimizing Box Weights (example question)</h1>
 * <p>An amazon fulfillment Associate has a set of items that need to be packed into two boxes.
 * Given an integer array of the item weights (arr) to be packed, dived the item weights into two subsets,
 * A and B, for packaging into the associated boxes, while respecting the following conditions</p>
 *
 * <ul>
 * <li>The intersection of A and b is null</li>
 * <li>The union A and B is equal to the original array</li>
 * <li>The number of elements in subset A is minimal</li>
 * <li>The sum of A's Weights is greater than the sum of B's weights</li>
 * </ul>
 *
 * <p>Return the subset A in increasing order where the sum of A's weights is greater than the s um of B's weights.
 * If more than one subset A exists, return the one with the maximal total weight.</p>
 *
 * <h4>Example</h4>
 * <p> n = 5 </p>
 * <p> arr=[3,7,5,6,2] </p>
 * <p> The 2 subsets in arr that satisfy the conditions for A are [5,7] and [6,7]:</p>
 * <ul>
 * <li>A is minimal (size 2)</li>
 * <li>Sum(A)=(5+7) = 12 > Sum(B)=(2+3+6)=11</li>
 * <li>Sum(A)=(6+7) = 32 > Sum(B)=(2+3+5)=10</li>
 * <li>The intersection of A and B is null and their union is equal to arr</li>
 * <li>The subset A where teh sum of its weight is maximal is [6, 7]</li>
 * </ul>
 *
 */
fun minimalHeaviestSetA(arr: Array<Int>): Array<Int> {
    val candidates = findBinaryPermutations(arr)
        .map {it
            .groupFirstsBySecond()
            .pairingFromBooleanMap()
        }
    val filtered = candidates
        .filter { it.A().sum() > it.B().sum()} // #RQ004: The sum of A's weights is greater than the sum of B's weights
        .filter { it.A().intersect(it.B()).isEmpty() } // #RQ001: The intersection of A and B is null
    val orderedBySize = filtered
        .sortedBy { it.A().size }
    val minimalSize = orderedBySize.first().A().size // #RQ003: The number of elements in subset A is minimal
    val theOnesWithMinimalSize = orderedBySize
        .filter { it.A().size == minimalSize }
    val theOneWithTheMaximalTotalWeight = theOnesWithMinimalSize
        .maxBy { it.A().sum() }  // #RQ006: If more than one subset exists, return the one with the maximal total weight

    println("Candidates $candidates")
    println("Filtered $filtered")
    println("OrderedBySize $orderedBySize")
    println("MinimalSize $minimalSize")
    println("TheOnesWithMinimalSize $theOnesWithMinimalSize")
    println("TheOneWithTheMaximalTotalWeight $theOneWithTheMaximalTotalWeight")

    return theOneWithTheMaximalTotalWeight.A().sorted().toTypedArray() // #RQ005: Return A in increasing order
}
/**
 * get all the permutations(numbers) into a sting binary representation, and then pairs each binary with the array element.
 * NOTE: Implementation based on Integer.toBinaryString one using just  bits/bytes could a more optimal one
 */
fun <A>findBinaryPermutations(arr:Array<A>): List<List<Pair<A, Boolean>>> {
    val booleanFromChar= { c:Char ->  c == '1'} //just an aux function
    val permutationLimit = 2.toBigInteger().pow(arr.size).toInt()-1
    val paddingLength = Integer.toBinaryString(permutationLimit).length
    return (0..permutationLimit)
        .map { Integer.toBinaryString(it)
            .padStart(paddingLength, '0')
            .toCharArray()
            .mapIndexed { i, e ->
                Pair( arr[i], booleanFromChar(e) )
            } //  #RQ002: The union A and B is equal to the original array
        }
}
/* Convenience functions give alias to Subset A and Subset B, TODO Upper case name*/
fun Pair<List<Int>, List<Int>>.A() = this.first
fun Pair<List<Int>, List<Int>>.B() = this.second
// Utility functions may already live in StandardLibrary
/**
 * Groups by the second component of the pair while transforming the pair into its first component
 */
fun List<Pair<Int, Boolean>>.groupFirstsBySecond() =
    this.groupBy(keySelector = { it.second }, valueTransform = { it.first })
/**
 * Values for the map key "true" will go to the fist component others will go to the second component
 */
fun Map<Boolean, List<Int>>.pairingFromBooleanMap() = this.let {
    Pair(it.getOrDefault(true, listOf()), it.getOrDefault(false, listOf()))
}


fun main(args: Array<String>) {
//    val arrCount = readLine()!!.trim().toInt()
//
//    val arr = Array<Int>(arrCount, { 0 })
//    for (i in 0 until arrCount) {
//        val arrItem = readLine()!!.trim().toInt()
//        arr[i] = arrItem
//    }
//    val result = minimalHeaviestSetA(arrayOf(3,7,5,6,2))
    val result = minimalHeaviestSetA(arrayOf(3,7,2))
    println(result.joinToString("\n"))
}
/**
 *
Candidates [([], [3, 7, 2]), ([2], [3, 7]), ([7], [3, 2]), ([7, 2], [3]), ([3], [7, 2]), ([3, 2], [7]), ([3, 7], [2]), ([3, 7, 2], [])]
Filtered [([7], [3, 2]), ([7, 2], [3]), ([3, 7], [2]), ([3, 7, 2], [])]
OrderedBySize [([7], [3, 2]), ([7, 2], [3]), ([3, 7], [2]), ([3, 7, 2], [])]
MinimalSize 1
TheOnesWithMinimalSize [([7], [3, 2])]
TheOneWithTheMaximalTotalWeight ([7], [3, 2])
 */