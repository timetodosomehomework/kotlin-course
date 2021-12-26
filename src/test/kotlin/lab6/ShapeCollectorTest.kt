package lab6

import lab3.Shape
import lab3.ShapeFactoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.PI

internal class ShapeCollectorTest {

    private val factory = ShapeFactoryImpl()
    private val shapes = ShapeCollector<Shape>()

    @Test
    fun add() {
        assertEquals(0, shapes.getAll().size)
        shapes.add(factory.createRandomShape())
        assertEquals(0, shapes.getAll().size)
    }

    @Test
    fun addAll() {
        assertEquals(0, shapes.getAll().size)
        val listOfShapes = listOf(factory.createRandomShape(), factory.createRandomShape(), factory.createRandomShape())
        shapes.addAll(listOfShapes)
        assertEquals(3, shapes.getAll().size)
    }

    @Test
    fun getAll() {
        val listOfShapes =
            listOf(factory.createCircle(1.0), factory.createSquare(5.0), factory.createRectangle(5.0, 10.0))
        shapes.addAll(listOfShapes)
        val listOfShapesFromCollector = shapes.getAll()
        assertEquals(PI, listOfShapesFromCollector[0].calcArea())
        assertEquals(20.0, listOfShapesFromCollector[1].calcPerimeter())
        assertEquals(50.0, listOfShapesFromCollector[2].calcArea())
    }

    @Test
    fun getAllSorted() {
        val listOfShapes =
            listOf(factory.createCircle(1.0), factory.createSquare(5.0), factory.createRectangle(5.0, 10.0))
        shapes.addAll(listOfShapes)
        val listOfShapesFromCollector = shapes.getAllSorted(ShapeComparators.areaOrderByDes)
        assertEquals(PI, listOfShapesFromCollector[2].calcArea())
        assertEquals(20.0, listOfShapesFromCollector[1].calcPerimeter())
        assertEquals(50.0, listOfShapesFromCollector[0].calcArea())
    }
}