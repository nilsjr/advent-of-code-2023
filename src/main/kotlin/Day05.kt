fun main() {
    fun part1(input: List<String>): Int {
        val seeds = input.seeds()
        val eL = input.emptyLines()
        val cat = input.categories(eL)
        val result = seeds.mapFromSeedToLocation(cat)
        return result.min().toInt()
    }

    fun part2(input: List<String>): Long {
        val seeds = input.seeds()
            .windowed(2, 2)
            .map { (start, range) -> (start until (start + range)) }
        val cat = input.categories(input.emptyLines())

        return seeds.minOf { range ->
            range.minOfOrNull { value ->
                cat.fold(value) { c, m ->
                    m.firstNotNullOfOrNull { it.map(c) } ?: c
                }
            } ?: Long.MAX_VALUE
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class Mapping(
    val source: LongRange,
    val offset: Long,
) {
    fun map(input: Long): Long? {
        return when (input) {
            in source -> input + offset
            else -> null
        }
    }
}

private fun List<String>.seeds(): List<Long> {
    return first().split(":")
        .asSequence()
        .drop(1)
        .map { it.trim().split(" ") }
        .flatten()
        .map { it.toLong() }
        .toList()
}

private fun List<String>.emptyLines(): List<Int> {
    return mapIndexed { index, s -> index to s }
        .filter { it.second.isEmpty() }
        .map { it.first } + listOf(this.size)
}

private fun List<String>.categories(eL: List<Int>): List<List<Mapping>> {
    return eL.windowed(2, 1)
        .map { e ->
            val range = e.first() + 2 until e[1]
            slice(range)
                .map { it.split(" ") }
                .filter { it.isNotEmpty() }
                .map { it.map { n -> n.trim().toLong() } }
        }
        .map { category ->
            category.map { row ->
                val (destStart, sourceStart, l) = row
                val source = sourceStart until (sourceStart + l)
                Mapping(source, destStart - sourceStart)
            }
        }

}

private fun List<Long>.mapFromSeedToLocation(cat: List<List<Mapping>>): Sequence<Long> {
    return asSequence().map { seed -> seed.mapFromSeedToLocation(cat) }
}

private fun Long.mapFromSeedToLocation(mapping: List<List<Mapping>>): Long {
    return mapping.scan(this) { acc, m ->
        m.firstOrNull { acc in it.source }?.map(acc) ?: acc
    }.last()
}