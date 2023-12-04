import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        val (_, numbers) = line.split(":")
        val (winning, own) = numbers.split("|")
        val winningNumbers = winning.mapNumbers()
        val winCount = own.mapNumbers().count { it in winningNumbers }
        val result = if (winCount > 1) {
            2.0.pow(winCount - 1).toInt()
        } else {
            winCount
        }

        return@sumOf result
    }

    fun part2(input: List<String>): Int {
        val wins = input.map { line ->
            val (_, numbers) = line.split(":")
            val (winning, own) = numbers.split("|")
            val winningNumbers = winning.mapNumbers()
            own.mapNumbers().count { it in winningNumbers }
        }
        val m = buildMap { repeat(wins.size) { put(it, 1) } }.toMutableMap()

        wins.forEachIndexed { i, won ->
            val cV = m.getValue(i)
            val n = i + 1
            repeat(won) {
                val c = n + it
                if (c < m.size) {
                    val v = m.getValue(c)
                    m[c] = v + cV
                }
            }
        }

        return m.map { it.value }.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun String.mapNumbers(): List<Int> = split(" ")
    .filter { it.isNotEmpty() }
    .map { it.trim().toInt() }