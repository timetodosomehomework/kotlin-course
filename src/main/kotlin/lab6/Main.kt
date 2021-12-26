package lab6

import lab3.Circle
import lab3.Shape
import lab3.ShapeFactoryImpl

fun main() {
    val factory = ShapeFactoryImpl()
    val shapes = ShapeCollector<Shape>()
    val circles = ShapeCollector<Circle>()
    shapes.add(factory.createRandomShape())
    shapes.add(factory.createRandomCircle())
    shapes.add(factory.createRandomTriangle())
    shapes.add(factory.createRandomRectangle())
    shapes.add(factory.createRandomSquare())
    circles.add(factory.createRandomCircle())
    circles.add(factory.createRandomCircle())
    circles.add(factory.createRandomCircle())
    circles.add(factory.createRandomCircle())
    circles.add(factory.createRandomCircle())
    println("Площадь по возрастанию:")
    for (i in shapes.getAllSorted(ShapeComparators.areaOrderByAsc))
        println(i.toString() + '\t' + i.calcArea())
    println("\nПлощадь по убыванию:")
    for (i in shapes.getAllSorted(ShapeComparators.areaOrderByDes))
        println(i.toString() + '\t' + i.calcArea())
    println("\nПериметр по возрастанию:")
    for (i in shapes.getAllSorted(ShapeComparators.perimeterOrderByAsc))
        println(i.toString() + '\t' + i.calcPerimeter())
    println("\nПериметр по убыванию:")
    for (i in shapes.getAllSorted(ShapeComparators.perimeterOrderByDes))
        println(i.toString() + '\t' + i.calcPerimeter())
    println("\nРадиус по возрастанию:")
    for (i in circles.getAllSorted(ShapeComparators.radiusOrderByAsc))
        println(i.toString() + '\t' + i.radius)
    println("\nРадиус по убыванию:")
    for (i in circles.getAllSorted(ShapeComparators.radiusOrderByDes))
        println(i.toString() + '\t' + i.radius)
}