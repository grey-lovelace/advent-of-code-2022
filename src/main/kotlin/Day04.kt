import java.io.File

class Day04: Day {
    override val resourcePath = "day04"
    override val expectedPart1Results = listOf(2)
    override val expectedPart2Results = listOf(4)

    override fun part1(file: File): Int {
        return file.findRanges()
            .filter{ (range1, range2) -> range1.all(range2::contains) || range2.all(range1::contains) }
            .count()
    }

    override fun part2(file: File): Int {
        return file.findRanges()
            .filter{ (range1, range2) -> range1.any(range2::contains) }
            .count()
    }

    fun File.findRanges(): List<Pair<IntRange,IntRange>> {
        return this.readLines()
            .map{ Regex("\\d+").findAll(it).map{ f -> f.value.toInt() }.toList() }
            .map{ Pair((it[0]..it[1]),(it[2]..it[3])) }
    }
}
