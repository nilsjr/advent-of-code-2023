fun main() {
    fun part1(input: List<String>): Int {
        val lines = input
            .map { line ->
                Regex("[0-9]+").findAll(line)
                    .map { it.value.toLong() }
                    .toList()
            }

        val races = List(lines.first().size) {
            Race(lines[0][it], lines[1][it])
        }

        return races
            .asSequence()
            .map { it.findWins() }
            .reduce { a, i -> a * i }
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { line -> line.filter { it.isDigit() }.toLong() }
        return Race(lines[0], lines[1]).findWins()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

data class Race(val duration: Long, val distance: Long)
fun Race.findWins(): Int = sequence { yieldAll(0..duration) }
    .map {
        val hold = it
        val diff = duration - it
        hold * diff
    }
    .filter { it > distance }
    .count()
