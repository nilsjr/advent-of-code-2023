fun main() {
    fun part1(input: List<String>): Int {
        val parts = input.partsPerRow()
        val symbols = input.symbolsPerRow()

        return parts
            .mapIndexed { index, partList ->
                val upper = index - 1
                val lower = index + 1
                partList.filter { part ->
                    val first = part.range.first - 1
                    val last = part.range.last + 1
                    val range = first..last

                    val upperValid = symbols.getOrNull(upper).orEmpty().any { it in range }
                    val rightLeftValid = symbols.getOrNull(index).orEmpty().any { it in range }
                    val lowerValid = symbols.getOrNull(lower).orEmpty().any { it in range }

                    upperValid || lowerValid || rightLeftValid
                }
            }
            .filter { it.isNotEmpty() }
            .flatten()
            .sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val parts = input.partsPerRow()
        val symbols = input
            .mapIndexed { index, line ->
                Regex("[^.\\d]+").findAll(line)
                    .filter { it.value == "*" }
                    .map { index to it.range.first }
                    .toList()
            }
            .filter { it.isNotEmpty() }
            .flatten()

        return symbols.sumOf { (row, col) ->
            val upperParts = parts.getOrNull(row - 1).orEmpty().filteredParts(col)
            val lowerParts = parts.getOrNull(row + 1).orEmpty().filteredParts(col)
            val adjacentParts = parts.getOrNull(row).orEmpty().filteredParts(col)
            val combine = upperParts + lowerParts + adjacentParts
            if (combine.size != 2) return@sumOf 0
            combine
                .map { it.value }
                .reduce { acc, i -> acc * i }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class Part(val value: Int, val range: IntRange)

private fun List<String>.partsPerRow(): List<List<Part>> = map { line ->
    Regex("[0-9]+").findAll(line)
        .map { Part(it.value.toInt(), it.range) }
        .toList()
}

private fun List<String>.symbolsPerRow(): List<List<Int>> = map { line ->
    Regex("[^.\\d]+").findAll(line)
        .map { it.range.first }
        .toList()
}

private fun List<Part>.filteredParts(index: Int): List<Part> = filter {
    val before = index - 1
    val after = index + 1
    index in it.range || before in it.range || after in it.range
}