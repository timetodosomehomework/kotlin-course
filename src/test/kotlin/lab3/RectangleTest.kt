package lab3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RectangleTest {

    private val testRectangle: Rectangle = Rectangle(5.0, 10.0)

    @Test
    fun calcArea() {
        assertEquals(50.0, testRectangle.calcArea())
    }

    @Test
    fun calcPerimeter() {
        assertEquals(30.0, testRectangle.calcPerimeter())
    }
}