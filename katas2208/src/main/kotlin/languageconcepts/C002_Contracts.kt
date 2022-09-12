package languageconcepts

import kotlin.contracts.ExperimentalContracts

fun executeOnce(block: () -> Unit) {
    block()
}

fun caller() {
    val value: String
    executeOnce {
        // It doesn't compile since the compiler doesn't know that the lambda
        // will be executed once and the reassignment of a val is forbidden.


        //value = "dummy-string" //TODO ==> UNCOMMENT THIS ONE
    }
}

/**
 * Contract informs the compiler that this will only happen once.
 * https://stackoverflow.com/questions/60958843/what-is-the-purpose-of-kotlin-contract
 */

//@OptIn(ExperimentalContracts::class)
//fun executeOnce(block: ()-> Unit) {
//    contract {
//        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
//    }
//    block()
//}