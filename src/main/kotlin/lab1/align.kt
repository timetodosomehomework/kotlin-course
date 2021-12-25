@file:Suppress("NAME_SHADOWING")

package lab1

enum class Alignment {
    LEFT,
    RIGHT,
    CENTER,
    JUSTIFY
}

private val marks = setOf(
    '.',
    ',',
    '!',
    ':',
    ';',
    '\'',
    '?',
    ')',
    '»',
    '\"',
    '-',
    '–'
) // Set with punctuation marks. Used when needed to check is the next symbol is punctuation mark.

// Function for splitting the original string of text in the list of words.
private fun splitWords(
    text: String
): List<String> {
    val words: MutableList<String> = mutableListOf()
    var previous = ""
    for (i in text.indices step 1) {
        if (text[i] == '\n' || '\r' == text[i]) // Symbols of line ending
            continue
        if (text[i] == ' ') { // If we find a " " it means that the word is over
            words += previous
            previous = ""
            continue
        }
        if (i == text.length - 1)
            words += previous + text[i]
        previous += text[i]
    }
    return words
}

private fun alignLeft(
    text: List<String>,
    lineWidth: Int
): String {
    var answer = ""
    var temp = ""
    var size: Int
    for (i in text.indices step 1) { // For every word
        size = if (" " in temp) // Calculating the size of current string with the new word
            temp.length + text[i].length
        else
            temp.length + text[i].length + 1
        if (size <= lineWidth) { // If size of the string with the new word is smaller than line width
            temp += text[i]
            if (size == lineWidth) {
                temp += "\n"
                answer += temp
                temp = ""
            } else
                temp += " "
        } else { // If size of the string with the new word is bigger than line width
            temp += "\n" // Putting a symbol of ending line
            answer += temp
            temp = ""
            if (text[i].length > lineWidth) { // If the word is bigger than the line width
                var sup = ""
                for (j in text[i].indices step 1) { // For every symbol in the word
                    if (sup.length < lineWidth) { // Adding one symbol at the time until it's equal to the line width
                        if (sup.isEmpty() && text[i][j] in marks) // Checking if the punctuation symbol in the beginning of the string
                            continue
                        sup += text[i][j]
                        if (sup.length == lineWidth) { // If we hit the limit
                            if (sup.length != text[i].length) { // But word is not over yet
                                if (text[i][j + 1] in marks) { // Checking if the next symbol is the punctuation mark
                                    sup += text[i][j + 1] + "\n" // If so then we don't carry over it
                                    temp += sup
                                    sup = ""
                                } else {
                                    sup += "\n" // If not then we just carry over the end of the word
                                    temp += sup
                                    sup = ""
                                }
                            }
                        }
                    }
                }
                answer += temp
                temp = sup
                if (temp.isNotEmpty())
                    temp += " "
            } else
                temp = text[i] + " "
        }
    }
    return answer + temp
}

private fun alignRight(
    text: List<String>,
    lineWidth: Int
): String {
    var answer = ""
    var temp = ""
    var size: Int
    var sizeToPad = 0
    for (i in text.indices step 1) { // For every word
        size = if (" " in temp) // Calculating the size of current string with the new word
            temp.length + text[i].length
        else
            temp.length + text[i].length + 1
        if (size <= lineWidth) { // If size of the string with the new word is smaller than line width
            temp += text[i]
            if (size == lineWidth) {
                temp += "\n"
                answer += temp
                temp = ""
            } else
                temp += " "
        } else { // If size of the string with the new word is bigger than line width
            temp += "\n" // Putting a symbol of ending line
            if (temp.length > 1) { // Calculating the number of spaces to add at the beginning of the string
                sizeToPad = if (temp[temp.lastIndex - 1] == ' ')
                    lineWidth + 2
                else
                    lineWidth + 1
            }
            temp = temp.padStart(sizeToPad) //Adding spaces at the beginning of the string
            answer += temp
            temp = ""
            if (text[i].length > lineWidth) { // If the word is bigger than the line width
                var sup = ""
                for (j in text[i].indices step 1) { // For every symbol in the word
                    if (sup.length < lineWidth) { // Adding one symbol at the time until it's equal to the line width
                        if (sup.isEmpty() && text[i][j] in marks) // Checking if the punctuation symbol in the beginning of the string
                            continue
                        sup += text[i][j]
                        if (sup.length == lineWidth) { // If we hit the limit
                            if (sup.length != text[i].length) { // But word is not over yet
                                if (text[i][j + 1] in marks) { // Checking if the next symbol is the punctuation mark
                                    sup += text[i][j + 1] + "\n" // If so then we don't carry over it
                                    temp += sup
                                    sup = ""
                                } else { // If not then we just carry over the end of the word
                                    sup += "\n"
                                    temp += sup
                                    sup = ""
                                }
                            }
                        }
                    }
                }
                answer += temp
                temp = sup
                if (temp.isNotEmpty())
                    temp += " "
            } else
                temp = text[i] + " "
        }
    }
    if (temp.length > 1) { // Spacing the last line
        sizeToPad = if (temp[temp.lastIndex] == ' ')
            lineWidth + 1
        else
            lineWidth
    }
    temp = temp.padStart(sizeToPad)
    return answer + temp
}

private fun alignCenter(
    text: List<String>,
    lineWidth: Int
): String {
    var answer = ""
    var temp = ""
    var size: Int
    var sizeToPad = 0
    for (i in text.indices step 1) { // For every word
        size = if (" " in temp) // Calculating the size of current string with the new word
            temp.length + text[i].length
        else
            temp.length + text[i].length + 1
        if (size <= lineWidth) { // If size of the string with the new word is smaller than line width
            temp += text[i]
            if (size == lineWidth) {
                temp += "\n"
                answer += temp
                temp = ""
            } else
                temp += " "
        } else { // If size of the string with the new word is bigger than line width
            temp += "\n" // Putting a symbol of ending line
            if (temp.length > 1) { // Calculating the number of spaces to add at the beginning and ending of the string
                sizeToPad = if (temp[temp.lastIndex - 1] == ' ')
                    lineWidth + temp.length + 2
                else
                    lineWidth + temp.length + 1
            }
            temp = temp.padStart(sizeToPad / 2) //Adding spaces at the beginning of the string
            temp = temp.padEnd(sizeToPad / 2) //Adding spaces at the ending of the string
            answer += temp
            temp = ""
            if (text[i].length > lineWidth) { // If the word is bigger than the line width
                var sup = ""
                for (j in text[i].indices step 1) { // For every symbol in the word
                    if (sup.length < lineWidth) { // Adding one symbol at the time until it's equal to the line width
                        if (sup.isEmpty() && text[i][j] in marks) // Checking if the punctuation symbol in the beginning of the string
                            continue
                        sup += text[i][j]
                        if (sup.length == lineWidth) { // If we hit the limit
                            if (sup.length != text[i].length) { // But word is not over yet
                                if (text[i][j + 1] in marks) { // Checking if the next symbol is the punctuation mark
                                    sup += text[i][j + 1] + "\n" // If so then we don't carry over it
                                    temp += sup
                                    sup = ""
                                } else { // If not then we just carry over the end of the word
                                    sup += "\n"
                                    temp += sup
                                    sup = ""
                                }
                            }
                        }
                    }
                }
                answer += temp
                temp = sup
                if (temp.isNotEmpty())
                    temp += " "
            } else
                temp = text[i] + " "
        }
    }
    if (temp.length > 1) { // Spacing the last line
        sizeToPad = if (temp[temp.lastIndex] == ' ')
            lineWidth + temp.length + 1
        else
            lineWidth + temp.length
    }
    temp = temp.padStart(sizeToPad / 2)
    temp = temp.padEnd(sizeToPad / 2)
    return answer + temp
}

private fun alignJustify(
    text: List<String>,
    lineWidth: Int
): String {
    val result = StringBuilder()
    var words: List<String> = listOf()
    var answer = ""
    var temp = ""
    var size: Int
    for (i in text.indices step 1) { // For every word
        size = if (" " in temp) // Calculating the size of current string with the new word
            temp.length + text[i].length
        else
            temp.length + text[i].length + 1
        if (size <= lineWidth) { // If size of the string with the new word is smaller than line width
            temp += text[i]
            words = words + text[i]
            if (size == lineWidth) {
                temp += "\n"
                answer += temp
                temp = ""
                words = words.drop(words.size)
            } else
                temp += " "
        } else { // If size of the string with the new word is bigger than line width
            if (temp.isNotEmpty()) { // Putting a symbol of ending line and delete the space symbol at the end if it exists
                if (temp[temp.lastIndex] == ' ')
                    temp = temp.dropLast(1)
                temp += "\n"
            }
            result.append(temp)
            if (words.size > 1) { // Spacing the line with the help of StringBuilder
                var numberOfSpaces: Int =
                    (lineWidth - result.length + 2) / words.size // Calculating the number of spaces between the words
                var indexCount = 0
                if (numberOfSpaces != 0) { // Inserting the space symbol after each word in the line
                    for (k in 0..words.size - 2 step 1) {
                        for (j in 0 until numberOfSpaces step 1)
                            result.insert(indexCount + words[k].length + j, ' ')
                        indexCount += words[k].length + numberOfSpaces + 1
                    }
                }
                if (result.length - 1 != lineWidth) { // If after that it's not enough
                    val miss: Int = lineWidth - result.length + 1 // Calculating how many spaces did we miss
                    var indexCount = 0
                    var index = 0
                    for (j in 0 until miss step 1) { // Putting a space symbol after each word until we hit line width
                        result.insert(words[index].length + indexCount + numberOfSpaces + j, ' ')
                        indexCount += words[index].length + numberOfSpaces
                        if (j == 0)
                            numberOfSpaces++
                        if (words.size > 2)
                            index++
                        if (index == words.size - 2) {
                            index = 0
                            indexCount = 0
                        }
                    }
                }
            }
            answer += result
            temp = ""
            result.clear()
            words = words.drop(words.size)
            if (text[i].length > lineWidth) { // If the word is bigger than the line width
                var sup = ""
                for (j in text[i].indices step 1) { // For every symbol in the word
                    if (sup.length < lineWidth) { // Adding one symbol at the time until it's equal to the line width
                        if (sup.isEmpty() && text[i][j] in marks) // Checking if the punctuation symbol in the beginning of the string
                            continue
                        sup += text[i][j]
                        if (sup.length == lineWidth) { // If we hit the limit
                            if (sup.length != text[i].length) { // But word is not over yet
                                if (text[i][j + 1] in marks) { // Checking if the next symbol is the punctuation mark
                                    sup += text[i][j + 1] + "\n" // If so then we don't carry over it
                                    temp += sup
                                    sup = ""
                                } else { // If not then we just carry over the end of the word
                                    sup += "\n"
                                    temp += sup
                                    sup = ""
                                }
                            }
                        }
                    }
                }
                answer += temp
                temp = sup
                words = words.drop(words.size)
                words = words + temp
                if (temp.isNotEmpty())
                    temp += " "
            } else {
                temp = text[i] + " "
                words = words.drop(words.size)
                words = words + text[i]
            }
        }
    }
    if (temp.isNotEmpty()) { // Spacing last line
        if (temp[temp.lastIndex] == ' ')
            temp = temp.dropLast(1)
        temp += "\n"
    }
    result.append(temp)
    if (words.size > 1) {
        var numberOfSpaces: Int = (lineWidth - result.length + 2) / words.size
        var indexCount = 0
        if (numberOfSpaces != 0) {
            for (k in 0..words.size - 2 step 1) {
                for (j in 0 until numberOfSpaces step 1)
                    result.insert(indexCount + words[k].length + j, ' ')
                indexCount += words[k].length + numberOfSpaces + 1
            }
        }
        if (result.length - 1 != lineWidth) {
            val miss: Int = lineWidth - result.length + 1
            var indexCount = 0
            var index = 0
            for (j in 0 until miss step 1) {
                result.insert(words[index].length + indexCount + numberOfSpaces + j, ' ')
                indexCount += words[index].length + numberOfSpaces
                if (j == 0)
                    numberOfSpaces++
                if (words.size > 2)
                    index++
                if (index == words.size - 2) {
                    index = 0
                    indexCount = 0
                }
            }
        }
    }
    answer += result
    return answer
}

fun alignText(
    text: String,
    lineWidth: Int = 120,
    alignment: Alignment = Alignment.LEFT
): String {
// code here
    val textWithSplitWords: List<String> = splitWords(text)
    val answer: String = when (alignment) {
        Alignment.LEFT -> alignLeft(textWithSplitWords, lineWidth)
        Alignment.RIGHT -> alignRight(textWithSplitWords, lineWidth)
        Alignment.CENTER -> alignCenter(textWithSplitWords, lineWidth)
        Alignment.JUSTIFY -> alignJustify(textWithSplitWords, lineWidth)
    }
    return answer
}
