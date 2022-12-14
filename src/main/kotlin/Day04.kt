import java.io.File

class Day04: Day {
    override val resourcePath = "day04"
    override val expectedPart1Results = listOf(2)
    override val expectedPart2Results = listOf(4)

    override fun part1(file: File): Int {
        return file
            .let(::findRanges)
            .filter{ (range1, range2) -> range1.all(range2::contains) || range2.all(range1::contains) }
            .count()
    }

    override fun part2(file: File): Int {
        return file
            .let(::findRanges)
            .filter{ (range1, range2) -> range1.any(range2::contains) }
            .count()
    }

    fun findRanges(file: File): List<Pair<IntRange,IntRange>> {
        return file.readLines()
            .map{ it.findNumbers() }
            .map{ Pair((it[0]..it[1]),(it[2]..it[3])) }
    }
}
