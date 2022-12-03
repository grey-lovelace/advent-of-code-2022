import java.io.File

class Day03: Day {
    override val resourcePath = "day03"
    override val expectedPart1Results = listOf(157)
    override val expectedPart2Results = listOf(70)

    override fun part1(file: File): Int {
        return file.readLines()
            .map{ Pair(it.take(it.length/2), it.takeLast(it.length/2)) }
            .map{ it.first.find(it.second::contains) }
            .map( letters::indexOf )
            .sum()
    }

    override fun part2(file: File): Int {
        return file.readLines()
            .windowed(3,3)
            .map{ it[0].find{ l -> l in it[1] && l in it[2] } }
            .map( letters::indexOf )
            .sum()
    }

    val letters = listOf(' ') + ('a'..'z') + ('A'..'Z')
}
