package lab3

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

interface Shape {
    fun calcArea(): Double
    fun calcPerimeter(): Double
}

class Circle(
    var radius: Double = 0.0
) : Shape {

    init {
        if (radius <= 0)
            throw IllegalArgumentException("Circle cannot have radius <= 0")
    }

    override fun calcArea(): Double {
        return PI * radius.pow(2)
    }

    override fun calcPerimeter(): Double {
        return 2 * PI * radius
    }
}

class Square(
    var side: Double = 0.0
) : Shape {

    init {
        if (side <= 0)
            throw IllegalArgumentException("Square cannot have side <= 0")
    }

    override fun calcArea(): Double {
        return side.pow(2)
    }

    override fun calcPerimeter(): Double {
        return 4 * side
    }
}

class Rectangle(
    var sideA: Double = 0.0,
    var sideB: Double = 0.0
) : Shape {
    init {
        if (sideA <= 0 || sideB <= 0)
            throw IllegalArgumentException("Rectangle cannot have side <= 0")
    }

    override fun calcArea(): Double {
        return sideA * sideB
    }

    override fun calcPerimeter(): Double {
        return 2 * (sideA + sideB)
    }
}

class Triangle(
    var sideA: Double = 0.0,
    var sideB: Double = 0.0,
    var sideC: Double = 0.0
) : Shape {

    init {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0 || (sideA + sideB) < sideC || (sideA + sideC) < sideB || (sideB + sideC) < sideA)
            throw IllegalArgumentException("Triangle with this exact parameters doesn't exist")
    }

    override fun calcArea(): Double {
        val halfPerimeter: Double = (sideA + sideB + sideC) / 2
        return sqrt(halfPerimeter * (halfPerimeter - sideA) * (halfPerimeter - sideB) * (halfPerimeter - sideC))
    }

    override fun calcPerimeter(): Double {
        return sideA + sideB + sideC
    }
}

interface ShapeFactory {
    fun createCircle(r: Double): Circle
    fun createSquare(a: Double): Square
    fun createRectangle(a: Double, b: Double): Rectangle
    fun createTriangle(a: Double, b: Double, c: Double): Triangle

    fun createRandomCircle(): Circle
    fun createRandomSquare(): Square
    fun createRandomRectangle(): Rectangle
    fun createRandomTriangle(): Triangle

    fun createRandomShape(): Shape
}

class ShapeFactoryImpl : ShapeFactory {
    override fun createCircle(r: Double): Circle {
        return Circle(r)
    }

    override fun createSquare(a: Double): Square {
        return Square(a)
    }

    override fun createRectangle(a: Double, b: Double): Rectangle {
        return Rectangle(a, b)
    }

    override fun createTriangle(a: Double, b: Double, c: Double): Triangle {
        return Triangle(a, b, c)
    }

    override fun createRandomCircle(): Circle {
        return Circle(Random.nextDouble(0.1, 100.0))
    }

    override fun createRandomSquare(): Square {
        return Square(Random.nextDouble(0.1, 100.0))
    }

    override fun createRandomRectangle(): Rectangle {
        return Rectangle(Random.nextDouble(0.1, 100.0), Random.nextDouble(0.1, 100.0))
    }

    override fun createRandomTriangle(): Triangle {
        val sideA: Double = Random.nextDouble(0.1, 100.0)
        val sideB: Double = Random.nextDouble(0.1, 100.0)
        return Triangle(sideA, sideB, Random.nextDouble(abs(sideA - sideB) + 0.01, sideA + sideB))
    }

    override fun createRandomShape(): Shape {
        when (Random.nextInt(0, 4)) {
            0 -> return createRandomCircle()
            1 -> return createRandomSquare()
            2 -> return createRandomRectangle()
            3 -> return createRandomTriangle()
        }
        return createRandomCircle()
    }
}
