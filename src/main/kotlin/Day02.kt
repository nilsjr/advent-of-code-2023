fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        val (game, sets) = line.split(":")
        val gameNumber = game.substring(5 until game.length).toInt()
        val gameSets = sets.split(";")
            .map { set ->
                set
                    .split(",")
                    .map { cubes ->
                        val (amount, color) = cubes.trim().split(" ")
                        color to amount.toInt()
                    }
            }
            .flatten()
            .groupBy { it.first }
            .mapValues { it.value.maxBy { cube -> cube.second }.second }
        val gameIsPossible = colors.all { color ->
            gameSets.getValue(color.key) <= color.value
        }
        if (gameIsPossible) gameNumber else 0
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        val (_, sets) = line.split(":")

        sets.split(";")
            .map { set ->
                set
                    .split(",")
                    .map { cubes ->
                        val (amount, color) = cubes.trim().split(" ")
                        color to amount.toInt()
                    }
            }
            .flatten()
            .groupBy { it.first }
            .mapValues { it.value.maxBy { cube -> cube.second }.second }
            .map { it.value }
            .reduce { acc, i ->
                acc * i
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private val colors = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

