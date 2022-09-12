package languageconcepts

/**
 * https://stackoverflow.com/questions/44427382/what-does-mean-in-kotlin
 */
class Builder (private val multiplier: Int) {

    //Action is a lambda that receives nothing and returns nothing (unit)
    fun invokeStuff(action: Builder.() -> Unit) {
        /** this will execute the lambda,
         but the context will be the builder instance
         so if you call this within the lambda it will refered to
         the builder object where its being invoked
        */
        this.action()
    }

    fun multiply(value: Int) : Int {
        return value * multiplier
    }
}

fun main(){
    val builder = Builder(10)
    builder.invokeStuff {
        //when this block is executed "this" is the actual builder object
        var result = multiply(1)
        println(result)
        println(this)
    }
}