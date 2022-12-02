import java.io.File

class Day1: Day {
    override val resourcePath = "day01"
    override val expectedPart1Results = listOf(24000L)
    override val expectedPart2Results = listOf(45000L)

    override fun part1(file: File): Long {
        return getCaloriesPerElf(file).maxOrNull()!!;
    }

    override fun part2(file: File): Long {
        return getCaloriesPerElf(file).sortedDescending().take(3).sum()
    }

    fun getCaloriesPerElf(file: File): List<Long> {
        return file.readText().split(Regex("(?:\r?\n){2}")).map{
            it.split(Regex("\r?\n")).map{ line -> line.toLong() }.sum()
        }
    }
}
