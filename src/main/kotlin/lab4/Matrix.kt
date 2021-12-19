package lab4

class Matrix(matrix: Array<Array<Double>>) {

    private var original: Array<Array<Double>>

    init{
        if (matrix.isEmpty())
            throw Exception("Cannot create empty matrix")
        else
            original = matrix
    }

    fun numberOfLines(): Int{
        return original.size
    }

    fun numberOfColumns(): Int{
        return original[0].size
    }

    operator fun plus(other: Matrix): Matrix {
        if (numberOfLines() != other.numberOfLines() || numberOfColumns() != other.numberOfColumns())
            throw Exception("Dimensions doesn't match up for adding operation")
        else {
            val newMatrix = Matrix(Array(numberOfLines()) {Array(numberOfColumns()) {0.0}})
            for (i in original.indices step 1) {
                for (j in original[i].indices step 1) {
                    newMatrix.original[i][j] = original[i][j] + other.original[i][j]
                }
            }
            return newMatrix
        }
    }

    operator fun minus(other: Matrix): Matrix {
        if (numberOfLines() != other.numberOfLines() || numberOfColumns() != other.numberOfColumns())
            throw Exception("Dimensions doesn't match up for subtraction operation")
        else {
            val newMatrix = Matrix(Array(numberOfLines()) {Array(numberOfColumns()) {0.0}})
            for (i in original.indices step 1) {
                for (j in original[i].indices step 1) {
                    newMatrix.original[i][j] = original[i][j] - other.original[i][j]
                }
            }
            return newMatrix
        }
    }

    operator fun times(other: Matrix): Matrix {
        if (numberOfColumns() != other.numberOfLines())
            throw Exception("Dimensions doesn't match up for multiplication operation")
        else {
            val newMatrix = Matrix(Array(numberOfLines()) {Array(other.numberOfColumns()) {0.0}})
            for (i in original.indices step 1) {
                for (j in other.original[0].indices step 1) {
                    for (r in original[0].indices step 1)
                        newMatrix.original[i][j] += original[i][r] * other.original[r][j]
                }
            }
            return newMatrix
        }
    }

    operator fun timesAssign(other: Matrix) {
        if (numberOfColumns() != other.numberOfLines())
            throw Exception("Dimensions doesn't match up for multiplication operation")
        else
            original = (this * other).original
    }

    operator fun minusAssign(other: Matrix) {
        if (numberOfLines() != other.numberOfLines() || numberOfColumns() != other.numberOfColumns())
            throw Exception("Dimensions doesn't match up for subtraction operation")
        else
            for (i in original.indices step 1){
                for (j in original[i].indices step 1){
                    original[i][j] -= other.original[i][j]
                }
            }
    }

    operator fun plusAssign(other: Matrix) {
        if (numberOfLines() != other.numberOfLines() || numberOfColumns() != other.numberOfColumns())
            throw Exception("Dimensions doesn't match up for adding operation")
        else
            for (i in original.indices step 1){
                for (j in original[i].indices step 1){
                    original[i][j] += other.original[i][j]
                }
            }
    }

    operator fun times(scalar: Double): Matrix {
        val newMatrix = Matrix(Array(numberOfLines()) {Array(numberOfColumns()) {0.0}})
        for (i in original.indices step 1){
            for (j in original[i].indices step 1){
                newMatrix.original[i][j] = original[i][j] * scalar
            }
        }
        return newMatrix
    }

    operator fun div(scalar: Double): Matrix {
        if (scalar == 0.0)
            throw Exception("You cannot divide by 0")
        else {
            val newMatrix = Matrix(Array(numberOfLines()) { Array(numberOfColumns()) { 0.0 } })
            for (i in original.indices step 1) {
                for (j in original[i].indices step 1) {
                    newMatrix.original[i][j] = original[i][j] / scalar
                }
            }
            return newMatrix
        }
    }

    operator fun timesAssign(scalar: Double) {
        for (i in original.indices step 1){
            for (j in original[i].indices step 1){
                original[i][j] *= scalar
            }
        }
    }

    operator fun divAssign(scalar: Double) {
        if (scalar == 0.0)
            throw Exception("You cannot divide by 0")
        else {
            for (i in original.indices step 1) {
                for (j in original[i].indices step 1) {
                    original[i][j] /= scalar
                }
            }
        }
    }

    operator fun set(i: Int, j: Int, value: Double) {
        if (i < 0 || i >= numberOfLines() || j < 0 || j >= numberOfColumns())
            throw Exception("Index is out of bound")
        else
            original[i][j] = value
    }

    operator fun get(i: Int, j: Int): Double {
        if (i < 0 || i >= numberOfLines() || j < 0 || j >= numberOfColumns())
            throw Exception("Index is out of bound")
        else
            return original[i][j]
    }

    operator fun unaryMinus(): Matrix {
        val newMatrix = Matrix(Array(numberOfLines()) {Array(numberOfColumns()) {0.0}})
        for (i in original.indices step 1){
            for (j in original[i].indices step 1){
                newMatrix.original[i][j] = -original[i][j]
            }
        }
        return newMatrix
    }

    operator fun unaryPlus(): Matrix {
        return this
    }

    override fun toString(): String {
        var str = ""
        for (i in original.indices step 1){
            for (j in original[0].indices step 1)
                str += original[i][j].toString() + '\t'
            str += '\n'
        }
        return str
    }

    // The following code was inspired by answer found here:
    // https://stackoverflow.com/questions/37524422/equals-method-for-data-class-in-kotlin

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Matrix
        for (i in original.indices step 1)
            if (!original[i].contentEquals(other.original[i])) return false
        return true
    }

    override fun hashCode(): Int{
        return original.contentHashCode()
    }
}
