package dev.jdev.katas.teaching.fp001

/**
 *                   f1            f2
 * Array<Array<Int>> => Array<Int> => Int
 * 1) [ [2,3,4][3,5,6][1,5,7] ]
 * 2) [3,4,2]
 * 3) 3
 */
fun sumaEntero(a:Int, b:Int):Int{
    return a + b
}

fun sdp(p1:Persona,p2:Persona):Int{
    return p1.edad + p2.edad
}

class Persona {
    fun masEdadDe(otro: Persona): Int {
        return this.edad + otro.edad
    }

    var edad:Int = 0
}

fun main(args: Array<String>)  {
    val pepe = Persona()
    pepe.edad = 10
    val pedro  = Persona()
    pedro.edad =25
    val sumedades = sdp(pepe,pedro)
    val sumedades2 = pepe.masEdadDe(pedro)

    val num1:Int = 5
    val num2:Int = 10

    val res1v2 = sumaEntero(num1, num2)
    //val res1v2 = a + b
    //val res1v2 = num1 + num2
    //val res1v2 = 5 + 10
    //val res1v2 = 15

    val res1 = num1.plus(5) //receiver
    // res1  = otro + this
    // res1  = 5 + num1
    // res1 = 5 + 5

    val res2 = res1.plus(8)

    val res3:Int =  num1.misumita(6)



    //Definir [ [2,3,4][3,5,6][1,5,7] ]
    val notasGrupo:Array<Array<Int>> = arrayOf(arrayOf(2,3,4), arrayOf(3,5,4), arrayOf(1,5,0))

    // Transformacion1: [3,4,2]
    val notasXestudiante: Array<Int> = calculadorNotasXEstdiante(notasGrupo)
    // Transformacion2: 3
    val notaPromedioDeGrupo:Int = promedio(notasXestudiante)

    //Imprimir
    println(notaPromedioDeGrupo)
}

private fun Int.misumita(otro: Int): Int {
    return otro + this
}


/**
 * [ 3,4,2]
 * 3
 */
fun promedio(entrada: Array<Int>): Int {
    var resultado = 0
    var i = 0
    while (i < entrada.size) {
        val tmp = entrada[i]
        resultado = resultado + tmp
        i = i + 1
    }
    return resultado
}

/**  c=0, c=1, c=2 ...
 * [ [2,3,4], [3,5,6], [1,5,7] ]
 * [ 3,         4,      4 ]
 */
fun calculadorNotasXEstdiante(entrada: Array<Array<Int>>): Array<Int> {
    var resultado:Array<Int> = arrayOf<Int>(entrada.size)
    var contador:Int = 0
    while (contador < entrada.size){
        val tmp = entrada[contador]
        val prom = promedio(tmp)
        resultado[contador] = prom
        contador = contador + 1
    }
    return resultado
}

