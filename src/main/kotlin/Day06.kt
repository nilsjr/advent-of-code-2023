fun main() {
    fun part1(input: List<String>): Int = input.size

    fun part2(input: List<String>): Int = input.size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day0X_test")
    check(part1(testInput) == 1)

    val input = readInput("Day0X")
    part1(input).println()
    part2(input).println()
}
