package lab3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TriangleTest {

    private val testTriangle: Triangle = Triangle(5.0, 12.0, 13.0)

    @Test
    fun calcArea() {
        assertEquals(30.0, testTriangle.calcArea())
    }

    @Test
    fun calcPerimeter() {
        assertEquals(30.0, testTriangle.calcPerimeter())
    }
}