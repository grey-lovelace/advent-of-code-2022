import java.io.File

class Day06: Day {
    override val resourcePath = "day06"
    override val expectedPart1Results = listOf(7)
    override val expectedPart2Results = listOf(19)

    override fun part1(file: File): Int {
        return findPacketMarker(file, 4);
    }

    override fun part2(file: File): Int {
        return findPacketMarker(file, 14);
    }

    fun findPacketMarker(file: File, numOfUniqueChars: Int): Int {
        return file.readText()
            .withIndex()
            .windowed(numOfUniqueChars)
            .first{ window -> window.distinctBy{ it.value }.size == numOfUniqueChars }
            .last()
            .index + 1;
    }
}
