import java.io.File

class Day1: Day {
    override val resourcePath = "day01"
    override val expectedPart1Results = listOf(24000L)
    override val expectedPart2Results = listOf(45000L)

    override fun part1(file: File): Long {
        return getCaloriesPerElf(file).maxOrNull() as Long;
    }

    override fun part2(file: File): Long {
        return getCaloriesPerElf(file).sortedDescending().take(3).sum()
    }

    fun getCaloriesPerElf(file: File): MutableList<Long> {
        return file.readLines().fold(mutableListOf(0L)) { acc, line -> 
            when {
                line.isNotBlank() -> acc[acc.lastIndex] += line.toLong()
                else -> acc += 0
            }
            acc
        }
    }
}
