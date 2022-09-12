package dev.jdev.katas.amz.interview.training002.customerreviews

import java.util.*
import kotlin.collections.*
import kotlin.io.*
import kotlin.text.*

//search for reviews
//minimum of 2 characthers => system at most 3 keyworkds alphabetical order
//reviews will update automatically after changeson the search
//keyworkd must start with the types character
//compare case insnensitive
/**
 * @param repo list with all system reviews
 * @param customerQuery the typed commands
 * @return 2d String array => List of suggestions => suggestions :LIst<String>
 */
fun searchSuggestions(repo: List<String>, customerQuery: String): List<List<String>> {
    if (customerQuery.length < 2 || repo.isEmpty()) return listOf(listOf())
    val sortedRepo = repo.sorted().map { it.lowercase(Locale.getDefault()) }
    val criteria = { s: String -> s.startsWith(customerQuery) }
    return customerQuery.indices
        .drop(2)
        .map { customerQuery.substring(0, it) }
        .map { sortedRepo.filter(criteria) }
        .take(3)
}


fun calculateKeyworkds (repo:List<String>, query:String):List<List<String>>{
    if(repo.isEmpty() || query.length<2) return emptyList()
    val lcQuery = query.toLowerCase()
    val indices = lcQuery.indices.drop(1)
    println(indices)
    val queries = lcQuery.indices.drop(1).map{ lcQuery.subSequence(0,it+1) }
    println(queries)
    val lcRepo = repo.map{it.toLowerCase()}.sorted()
    val res = queries.map{ q -> lcRepo.filter{ it.startsWith(q)}.take(3) }
    return res
}

fun calculateKeyworkds2 (repo:List<String>, query:String):List<List<String>>{
    if(repo.isEmpty() || query.length<2) return emptyList()
    return query.toLowerCase().indices.drop(1)
        .map{ i:Int -> query.toLowerCase().subSequence(0, i+1) }
        .map{ subQuery -> repo
            .map( String::toLowerCase )
            .sorted()
            .filter { keyword -> keyword.startsWith(subQuery) }
        }
}

fun calculateKeyworkds3 (repo:List<String>, query:String):List<List<String>> =
    if(repo.isEmpty() || query.length<2) emptyList() else
        query.indices.drop(1)
            .map{ index -> query.toLowerCase().subSequence(0,index+1) }
            .map{ sq -> repo.sorted()
                .map(String::toLowerCase)
                .filter{it.startsWith(sq)}
                .take(3)
            }

fun subqueries(minimumLenght:Int, query:String):List<String> = //O(n)
    query
        .indices.drop(minimumLenght)
        .map{index -> query.toLowerCase().substring(0,index+1)} //O(n)

fun find(subQuery:String, keyWordRepo:List<String>, maxResults:Int):List<String> = //O(n)
    keyWordRepo
        .map(String::toLowerCase) //O(n)
        .sorted() //O(log n)
        .filter{ it.startsWith(subQuery) } //O(n)
        .take(maxResults)

fun calculateKeyworkds4 (repo:List<String>, query:String):List<List<String>> = //O(n2)
    if(repo.isEmpty() || query.length<2) emptyList() else
        subqueries(1,query)
            .map{ subquery -> find(subquery,repo,3) }

fun main() {

    val repo = listOf("mobile", "mouse", "moneypot", "monitor", "mousepad", "abml")
    val query = "mouse"

    calculateKeyworkds4(repo,query).forEach(::println)
}

//NOtes
//Ie: for  person
//[
//    [pep, petroleum, person]
//    [perth, person, personal]
//    [person, personalized, personalization]
//    [person, personalized, personalization]
//    [person, personalized, personalization]
//]