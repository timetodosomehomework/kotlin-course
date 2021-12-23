package lab2


fun main() {
    val calculator = Calculator()
    val answer: Double
    var expression =
        "(ln(e) +2.1245)*3 - 3^3/-2 + cos(+pi) - sin(tg(pi)) - ctg(0.3) - lg(5084.172)" //(ln(e) +2.1245)*3 - 3^3/-2 + cos(+pi) - sin(tg(pi)) - ctg(0.3) - lg(5084.172)
    expression = expression.replace(" ", "")
    if (calculator.read(expression)) {
        println("Original:\n$expression\nPrefix form:")
        calculator.toPrefix()
        for (i in calculator.expression.indices step 1)
            print(calculator.expression[i].data)
        answer = calculator.calculate()
        println("\nAnswer: $answer")
    }
}

