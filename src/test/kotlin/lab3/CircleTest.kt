package lab3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.PI

internal class CircleTest {

    private val testCircle: Circle = Circle(1.0)

    @Test
    fun calcArea() {
        assertEquals(PI, testCircle.calcArea())
    }

    @Test
    fun calcPerimeter() {
        assertEquals(2 * PI, testCircle.calcPerimeter())
    }
}