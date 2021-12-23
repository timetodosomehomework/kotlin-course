package lab2

import kotlin.math.*

const val numbers = "0123456789"
const val maxNumberLength = 50

enum class Character {
    PLUS, MINUS, MULT, DIV, POW, MUNARY, PUNARY,
    COS, SIN, TG, CTG, LN, LG, PI, E, O_BRACKET, C_BRACKET,
    NUMBER, UNKNOWN
}

enum class Priority {
    UNKNOWN, BRACKETS, PLUS, MINUS, MULT_DIV, POW,
    FUNCTION, NUMBER
}

class Calculator {
    class Entity(
        var data: String,
        var character: Character = Character.UNKNOWN,
        var priority: Priority = Priority.UNKNOWN,
        var length: Int = 1
    )

    var expression: MutableList<Entity> = mutableListOf(Entity(""))

    private fun fillEntity(data: String, index: Int) {
        expression[expression.lastIndex].data = data[index].toString()
        expression[expression.lastIndex].length = data[index].toString().length
    }

    private fun givePriority( // Function that fills object of character class with information
        data: String,
        index: Int
    ) {
        when (data[index]) {
            '+' -> {
                if (index == 0) {
                    expression[expression.lastIndex].character = Character.PUNARY
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    fillEntity(data, index)
                } else {
                    if (data[index - 1] in ")0123456789ie") {
                        expression[expression.lastIndex].character = Character.PLUS
                        expression[expression.lastIndex].priority = Priority.PLUS
                        fillEntity(data, index)
                    } else {
                        expression[expression.lastIndex].character = Character.PUNARY
                        expression[expression.lastIndex].priority = Priority.FUNCTION
                        fillEntity(data, index)
                    }
                }
            }
            '-' -> {
                if (index == 0) {
                    expression[expression.lastIndex].character = Character.MUNARY
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    fillEntity(data, index)
                } else {
                    if (data[index - 1] in ")0123456789ie") {
                        expression[expression.lastIndex].character = Character.MINUS
                        expression[expression.lastIndex].priority = Priority.MINUS
                        fillEntity(data, index)
                    } else {
                        expression[expression.lastIndex].character = Character.MUNARY
                        expression[expression.lastIndex].priority = Priority.FUNCTION
                        fillEntity(data, index)
                    }
                }
            }
            '*' -> {
                expression[expression.lastIndex].character = Character.MULT
                expression[expression.lastIndex].priority = Priority.MULT_DIV
                fillEntity(data, index)
            }
            '/' -> {
                expression[expression.lastIndex].character = Character.DIV
                expression[expression.lastIndex].priority = Priority.MULT_DIV
                fillEntity(data, index)
            }
            '^' -> {
                expression[expression.lastIndex].character = Character.POW
                expression[expression.lastIndex].priority = Priority.POW
                fillEntity(data, index)
            }
            'c' -> {
                if (data[index + 1] == 'o' && data[index + 2] == 's') {
                    expression[expression.lastIndex].character = Character.COS
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    expression[expression.lastIndex].data = "cos"
                    expression[expression.lastIndex].length = 3
                }
                if (data[index + 1] == 't' && data[index + 2] == 'g') {
                    expression[expression.lastIndex].character = Character.CTG
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    expression[expression.lastIndex].data = "ctg"
                    expression[expression.lastIndex].length = 3
                }
            }
            's' -> {
                if (data[index + 1] == 'i' && data[index + 2] == 'n') {
                    expression[expression.lastIndex].character = Character.SIN
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    expression[expression.lastIndex].data = "sin"
                    expression[expression.lastIndex].length = 3
                }
            }
            't' -> {
                if (data[index + 1] == 'g') {
                    expression[expression.lastIndex].character = Character.TG
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    expression[expression.lastIndex].data = "tg"
                    expression[expression.lastIndex].length = 2
                }
            }
            'l' -> {
                if (data[index + 1] == 'g') {
                    expression[expression.lastIndex].character = Character.LG
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    expression[expression.lastIndex].data = "lg"
                    expression[expression.lastIndex].length = 2
                }
                if (data[index + 1] == 'n') {
                    expression[expression.lastIndex].character = Character.LN
                    expression[expression.lastIndex].priority = Priority.FUNCTION
                    expression[expression.lastIndex].data = "ln"
                    expression[expression.lastIndex].length = 2
                }
            }
            'p' -> {
                if (data[index + 1] == 'i') {
                    expression[expression.lastIndex].character = Character.PI
                    expression[expression.lastIndex].priority = Priority.NUMBER
                    expression[expression.lastIndex].data = "pi"
                    expression[expression.lastIndex].length = 2
                }
            }
            'e' -> {
                expression[expression.lastIndex].character = Character.E
                expression[expression.lastIndex].priority = Priority.NUMBER
                fillEntity(data, index)
            }
            '(' -> {
                expression[expression.lastIndex].character = Character.O_BRACKET
                expression[expression.lastIndex].priority = Priority.BRACKETS
                fillEntity(data, index)
            }
            ')' -> {
                expression[expression.lastIndex].character = Character.C_BRACKET
                expression[expression.lastIndex].priority = Priority.BRACKETS
                fillEntity(data, index)
            }
            in numbers -> { //If character is a number
                var number = ""
                number += data[index]
                var correctInput = true
                var countDots = 0
                var currentLength = 1
                if (data.length - 1 != index)
                    while (data[index + currentLength] in numbers || data[index + currentLength] == '.') {
                        if (data[index + currentLength] == '.')
                            countDots++
                        if ((data[index] == '0' && data[index + 1] != '.') || (currentLength > maxNumberLength) || (countDots > 1)) {
                            correctInput = false
                            break
                        }
                        number += data[index + currentLength]
                        if (index + currentLength != data.length - 1)
                            currentLength++
                        else
                            break
                    }
                if (correctInput) {
                    expression[expression.lastIndex].character = Character.NUMBER
                    expression[expression.lastIndex].priority = Priority.NUMBER
                    expression[expression.lastIndex].data = number
                    expression[expression.lastIndex].length = number.length
                } else {
                    currentLength++
                    throw Exception("Incorrect input at position $currentLength.")
                }
            }
        }
    }

    private fun isCorrect(): Boolean { // Function that checks the expressions correctness
        var bracketsCount = 0
        if (expression[0].priority >= Priority.PLUS && expression[0].priority <= Priority.POW) { // If expression starts with wrong operator
            throw Exception("Incorrect input at position 0.")
        }
        for (i in expression.indices step 1) {
            if (expression[i].character == Character.UNKNOWN) { // If character isn't in the enumeration of available characters
                throw Exception("Expression has an unknown character.")
            }
            if (expression[i].character == Character.C_BRACKET && bracketsCount == 0) { // If expression has an additional close bracket without open one
                throw Exception("Missing open bracket.")
            }
            if (expression[i].character == Character.O_BRACKET)
                bracketsCount++
            if (expression[i].character == Character.C_BRACKET)
                bracketsCount--
            if (i != 0)
            // Checks that wrong pair of operators not in a row
                if ((expression[i - 1].priority >= Priority.PLUS && expression[i - 1].priority <= Priority.POW && expression[i].priority >= Priority.PLUS && expression[i].priority <= Priority.POW) ||
                    (expression[i - 1].character == Character.MUNARY && expression[i].character == Character.O_BRACKET) ||
                    (expression[i - 1].character == Character.C_BRACKET && (expression[i].character == Character.O_BRACKET || expression[i].priority == Priority.NUMBER || expression[i].priority == Priority.FUNCTION)) ||
                    (expression[i].character == Character.C_BRACKET && !(expression[i - 1].priority == Priority.NUMBER || expression[i - 1].character == Character.C_BRACKET)) ||
                    (expression[i - 1].character == Character.O_BRACKET && !(expression[i].priority == Priority.NUMBER || expression[i].priority == Priority.FUNCTION || expression[i].character == Character.O_BRACKET)) ||
                    (expression[i - 1].character == Character.NUMBER && (expression[i].priority == Priority.FUNCTION || expression[i].priority == Priority.NUMBER || expression[i].character == Character.O_BRACKET))
                ) {
                    throw Exception("Two mismatched operators in a row.")
                }
        }
        if (bracketsCount != 0) {
            throw Exception("Not all brackets have a pair.")
        }
        return true
    }

    fun read( // Function that reads the expression and fills the list of characters
        data: String
    ): Boolean {
        var i = 0
        var correct = true
        while (i < data.length) {
            if (!correct)
                break
            if (i != 0)
                expression.add(Entity(""))
            givePriority(data, i)
            if (expression[expression.lastIndex].character == Character.UNKNOWN)
                correct = false
            i += expression[expression.lastIndex].length
        }
        if (!isCorrect()) {
            return false
        }
        return true
    }

    fun toPrefix() { // Function that converts list of characters to prefix form
        val stack: MutableList<Entity> = mutableListOf()
        val prefix: MutableList<Entity> = mutableListOf()
        var i = expression.size - 1
        while (i >= 0) {
            when (expression[i].character) {
                Character.C_BRACKET -> stack.add(
                    Entity(
                        expression[i].data,
                        expression[i].character,
                        expression[i].priority
                    )
                )
                Character.NUMBER -> prefix.add(
                    0,
                    Entity(expression[i].data, expression[i].character, expression[i].priority)
                )
                Character.O_BRACKET ->
                    while (stack[stack.lastIndex].character != Character.C_BRACKET) {
                        prefix.add(
                            0,
                            Entity(
                                stack[stack.lastIndex].data,
                                stack[stack.lastIndex].character,
                                stack[stack.lastIndex].priority
                            )
                        )
                        stack.removeAt(stack.lastIndex)
                    }
                else -> {
                    if (stack.size == 0)
                        stack.add(Entity(expression[i].data, expression[i].character, expression[i].priority))
                    else {
                        while (stack[stack.lastIndex].priority >= expression[i].priority) {
                            prefix.add(
                                0,
                                Entity(
                                    stack[stack.lastIndex].data,
                                    stack[stack.lastIndex].character,
                                    stack[stack.lastIndex].priority
                                )
                            )
                            stack.removeAt(stack.lastIndex)
                            if (stack.size == 0)
                                break
                        }
                        stack.add(Entity(expression[i].data, expression[i].character, expression[i].priority))
                    }
                }
            }
            i--
        }
        while (stack.size != 0) {
            if (stack[stack.lastIndex].character != Character.C_BRACKET)
                prefix.add(
                    0,
                    Entity(
                        stack[stack.lastIndex].data,
                        stack[stack.lastIndex].character,
                        stack[stack.lastIndex].priority
                    )
                )
            stack.removeAt(stack.lastIndex)
        }
        expression = prefix
    }

    fun calculate(): Double { // Function that calculates value of the expression
        val stack: MutableList<Double> = mutableListOf()
        var a: Double
        var b: Double
        var i = expression.size - 1
        while (i >= 0) {
            when (expression[i].character) {
                Character.PLUS -> {
                    a = stack.removeLast()
                    b = stack.removeLast()
                    stack.add(a + b)
                }
                Character.MINUS -> {
                    a = stack.removeLast()
                    b = stack.removeLast()
                    stack.add(a - b)
                }
                Character.MULT -> {
                    a = stack.removeLast()
                    b = stack.removeLast()
                    stack.add(a * b)
                }
                Character.DIV -> {
                    a = stack.removeLast()
                    b = stack.removeLast()
                    stack.add(a / b)
                }
                Character.POW -> {
                    a = stack.removeLast()
                    b = stack.removeLast()
                    stack.add(a.pow(b))
                }
                Character.MUNARY -> {
                    a = stack.removeLast()
                    stack.add(-a)
                }
                Character.PUNARY -> {
                    a = stack.removeLast()
                    stack.add(a)
                }
                Character.COS -> {
                    a = stack.removeLast()
                    stack.add(cos(a))
                }
                Character.SIN -> {
                    a = stack.removeLast()
                    stack.add(sin(a))
                }
                Character.TG -> {
                    a = stack.removeLast()
                    stack.add(tan(a))
                }
                Character.CTG -> {
                    a = stack.removeLast()
                    stack.add(1 / tan(a))
                }
                Character.LN -> {
                    a = stack.removeLast()
                    stack.add(ln(a))
                }
                Character.LG -> {
                    a = stack.removeLast()
                    stack.add(log10(a))
                }
                Character.PI -> {
                    stack.add(PI)
                }
                Character.E -> {
                    stack.add(E)
                }
                Character.NUMBER -> {
                    stack.add(expression[i].data.toDouble())
                }
                else -> {
                }
            }
            i--
        }
        return stack.removeLast()
    }
}