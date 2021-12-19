package lab4

fun main(){
    var firstMatrix = Matrix(arrayOf(arrayOf(0.0, 1.0, 2.0),
                                     arrayOf(3.0, 4.0, 5.0),
                                     arrayOf(6.0, 7.0, 8.0)))
    val secondMatrix = Matrix(arrayOf(arrayOf(6.0, 7.0, 8.0),
                                      arrayOf(3.0, 4.0, 5.0),
                                      arrayOf(0.0, 1.0, 2.0)))
    println("${firstMatrix.numberOfLines()} * ${firstMatrix.numberOfColumns()}, ${secondMatrix.numberOfLines()} * ${secondMatrix.numberOfColumns()}")
    println("First matrix:\n$firstMatrix")
    println("Second matrix:\n$secondMatrix")
    val newMatrix = firstMatrix + secondMatrix
    println(newMatrix.toString())
    newMatrix += firstMatrix
    println(newMatrix.toString())
    firstMatrix = newMatrix - secondMatrix
    println(firstMatrix.toString())
    newMatrix -= firstMatrix
    println(newMatrix.toString())
    firstMatrix = newMatrix * secondMatrix
    println(firstMatrix.toString())
    newMatrix *= firstMatrix
    println(newMatrix.toString())
    firstMatrix = secondMatrix * 10.0
    println(firstMatrix.toString())
    newMatrix *= 2.0
    println(newMatrix.toString())
    firstMatrix = newMatrix / 10.0
    println(firstMatrix.toString())
    newMatrix /= 13.0
    println(newMatrix.toString())
    firstMatrix = -secondMatrix
    println(firstMatrix.toString())
    firstMatrix = +newMatrix
    println(firstMatrix.toString())
    if (firstMatrix == newMatrix)
        println(true)
    firstMatrix = secondMatrix
    if (firstMatrix != newMatrix)
        println(false)
}