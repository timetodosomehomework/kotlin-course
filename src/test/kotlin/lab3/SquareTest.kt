package lab3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SquareTest {

    private val testSquare: Square = Square(5.0)

    @Test
    fun calcArea() {
        assertEquals(25.0, testSquare.calcArea())
    }

    @Test
    fun calcPerimeter() {
        assertEquals(20.0, testSquare.calcPerimeter())
    }
}