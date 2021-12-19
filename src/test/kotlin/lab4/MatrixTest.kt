package lab4

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MatrixTest {
    var firstMatrix = Matrix(arrayOf(arrayOf(0.0, 1.0, 2.0),
        arrayOf(3.0, 4.0, 5.0),
        arrayOf(6.0, 7.0, 8.0)))
    var secondMatrix = Matrix(arrayOf(arrayOf(6.0, 7.0, 8.0),
        arrayOf(3.0, 4.0, 5.0),
        arrayOf(0.0, 1.0, 2.0)))
    @Test
    fun numberOfLines() {
        assertEquals(3, firstMatrix.numberOfLines())
    }

    @Test
    fun numberOfColumns() {
        assertEquals(3, firstMatrix.numberOfColumns())
    }

    @Test
    fun plus() {
        val newMatrix = Matrix(arrayOf(arrayOf(6.0, 8.0, 10.0),
            arrayOf(6.0, 8.0, 10.0),
            arrayOf(6.0, 8.0, 10.0)))
        assertEquals(newMatrix, firstMatrix + secondMatrix)
    }

    @Test
    fun minus() {
        val newMatrix = Matrix(arrayOf(arrayOf(-6.0, -6.0, -6.0),
            arrayOf(0.0, 0.0, 0.0),
            arrayOf(6.0, 6.0, 6.0)))
        assertEquals(newMatrix, firstMatrix - secondMatrix)
    }

    @Test
    fun times() {
        val newMatrix = Matrix(arrayOf(arrayOf(3.0, 6.0, 9.0),
            arrayOf(30.0, 42.0, 54.0),
            arrayOf(57.0, 78.0, 99.0)))
        assertEquals(newMatrix, firstMatrix * secondMatrix)
    }

    @Test
    fun testTimes() {
        val newMatrix = Matrix(arrayOf(arrayOf(0.0, 2.0, 4.0),
            arrayOf(6.0, 8.0, 10.0),
            arrayOf(12.0, 14.0, 16.0)))
        assertEquals(newMatrix, firstMatrix * 2.0)
    }

    @Test
    fun div() {
        val newMatrix = Matrix(arrayOf(arrayOf(0.0, 0.5, 1.0),
            arrayOf(1.5, 2.0, 2.5),
            arrayOf(3.0, 3.5, 4.0)))
        assertEquals(newMatrix, firstMatrix / 2.0)
    }

    @Test
    fun set() {
        firstMatrix[0,1] = 10.0
        val newMatrix = Matrix(arrayOf(arrayOf(0.0, 10.0, 2.0),
            arrayOf(3.0, 4.0, 5.0),
            arrayOf(6.0, 7.0, 8.0)))
        assertEquals(newMatrix, firstMatrix)
    }

    @Test
    fun get() {
        assertEquals(8.0, firstMatrix[2,2])
    }

    @Test
    operator fun unaryMinus() {
        val newMatrix = Matrix(arrayOf(arrayOf(-0.0, -1.0, -2.0),
            arrayOf(-3.0, -4.0, -5.0),
            arrayOf(-6.0, -7.0, -8.0)))
        assertEquals(newMatrix, -firstMatrix)
    }

    @Test
    operator fun unaryPlus() {
        val newMatrix = Matrix(arrayOf(arrayOf(0.0, 1.0, 2.0),
            arrayOf(3.0, 4.0, 5.0),
            arrayOf(6.0, 7.0, 8.0)))
        assertEquals(newMatrix, +firstMatrix)
    }

    @Test
    fun testEquals() {
        val newMatrix = Matrix(arrayOf(arrayOf(0.0, 1.0, 2.0),
            arrayOf(3.0, 4.0, 5.0),
            arrayOf(6.0, 7.0, 8.0)))
        assertEquals(newMatrix, firstMatrix)
        newMatrix[1,0]=17.0
        assertNotEquals(newMatrix, firstMatrix)
    }
}