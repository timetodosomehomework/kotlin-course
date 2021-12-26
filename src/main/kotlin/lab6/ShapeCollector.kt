package lab6

import lab3.Circle
import lab3.Shape

object ShapeComparators {
    val areaOrderByAsc = compareBy<Shape> { it.calcArea() }
    val areaOrderByDes = compareByDescending<Shape> { it.calcArea() }
    val perimeterOrderByAsc = compareBy<Shape> { it.calcPerimeter() }
    val perimeterOrderByDes = compareByDescending<Shape> { it.calcPerimeter() }
    val radiusOrderByAsc = compareBy<Circle> { it.radius }
    val radiusOrderByDes = compareByDescending<Circle> { it.radius }
}

class ShapeCollector<T : Shape> {
    private val allShapes = mutableListOf<T>()

    fun add(new: T) {
        allShapes.add(new)
    }

    fun addAll(new: List<T>) {
        allShapes.addAll(new)
    }

    fun getAll(): List<T> {
        return allShapes.toList()
    }

    fun getAllSorted(comparator: Comparator<in T>): List<T> {
        return getAll().sortedWith(comparator)
    }
}
