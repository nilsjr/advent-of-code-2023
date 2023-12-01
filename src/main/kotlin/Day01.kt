fun main() {
    fun part1(input: List<String>): Int = input
        .sumOf { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            "$firstDigit$lastDigit".toInt()
        }

    fun part2(input: List<String>): Int = input
        .sumOf { line ->
            val first = mappedDigits.getValue(line.findAnyOf(mappedDigits.keys)!!.second)
            val last = mappedDigits.getValue(line.findLastAnyOf(mappedDigits.keys)!!.second)
            "$first$last".toInt()
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private val digits = (1..9)
private val spelledDigits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
private val mappedDigits = digits.associateBy { it.toString() } +
        (digits.mapIndexed { i, d -> spelledDigits[i] to d }).toMap()