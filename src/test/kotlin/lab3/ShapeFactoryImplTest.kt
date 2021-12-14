package lab3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ShapeFactoryImplTest {

    private val testFactory: ShapeFactory = ShapeFactoryImpl()

    @Test
    fun createCircle() {
        val testCircle = Circle(12.76)
        val factoryCircle: Circle = testFactory.createCircle(12.76)
        assertEquals(testCircle.calcArea(), factoryCircle.calcArea())
        assertEquals(testCircle.calcPerimeter(), factoryCircle.calcPerimeter())
    }

    @Test
    fun createCircleError() {
        assertThrows(Exception::class.java) {
            testFactory.createCircle(-5.0)
        }
    }

    @Test
    fun createSquare() {
        val testSquare = Square(12.76)
        val factorySquare: Square = testFactory.createSquare(12.76)
        assertEquals(testSquare.calcArea(), factorySquare.calcArea())
        assertEquals(testSquare.calcPerimeter(), factorySquare.calcPerimeter())
    }

    @Test
    fun createSquareError() {
        assertThrows(Exception::class.java) {
            testFactory.createSquare(-5.0)
        }
    }

    @Test
    fun createRectangle() {
        val testRectangle = Rectangle(12.76, 5.3)
        val factoryRectangle: Rectangle = testFactory.createRectangle(12.76, 5.3)
        assertEquals(testRectangle.calcArea(), factoryRectangle.calcArea())
        assertEquals(testRectangle.calcPerimeter(), factoryRectangle.calcPerimeter())
    }

    @Test
    fun createRectangleError() {
        assertThrows(Exception::class.java) {
            testFactory.createRectangle(-5.0, 5.3)
        }
    }

    @Test
    fun createTriangle() {
        val testTriangle = Triangle(12.76, 5.3, 13.44)
        val factoryTriangle: Triangle = testFactory.createTriangle(12.76, 5.3, 13.44)
        assertEquals(testTriangle.calcArea(), factoryTriangle.calcArea())
        assertEquals(testTriangle.calcPerimeter(), factoryTriangle.calcPerimeter())
    }

    @Test
    fun createTriangleError() {
        assertThrows(Exception::class.java) {
            testFactory.createTriangle(5.0, 5.3, 70.0)
        }
    }

    @Test
    fun createRandomCircle() {
        val factorySquare1: Square = testFactory.createRandomSquare()
        val factorySquare2: Square = testFactory.createRandomSquare()
        assertNotEquals(factorySquare1.calcArea(), factorySquare2.calcArea())
        assertNotEquals(factorySquare1.calcPerimeter(), factorySquare2.calcPerimeter())
    }

    @Test
    fun createRandomSquare() {
        val factorySquare1: Square = testFactory.createRandomSquare()
        val factorySquare2: Square = testFactory.createRandomSquare()
        assertNotEquals(factorySquare1.calcArea(), factorySquare2.calcArea())
        assertNotEquals(factorySquare1.calcPerimeter(), factorySquare2.calcPerimeter())
    }

    @Test
    fun createRandomRectangle() {
        val factoryRectangle1: Rectangle = testFactory.createRandomRectangle()
        val factoryRectangle2: Rectangle = testFactory.createRandomRectangle()
        assertNotEquals(factoryRectangle1.calcArea(), factoryRectangle2.calcArea())
        assertNotEquals(factoryRectangle1.calcPerimeter(), factoryRectangle2.calcPerimeter())
    }

    @Test
    fun createRandomTriangle() {
        val factoryTriangle1: Triangle = testFactory.createRandomTriangle()
        val factoryTriangle2: Triangle = testFactory.createRandomTriangle()
        assertNotEquals(factoryTriangle1.calcArea(), factoryTriangle2.calcArea())
        assertNotEquals(factoryTriangle1.calcPerimeter(), factoryTriangle2.calcPerimeter())
    }
}