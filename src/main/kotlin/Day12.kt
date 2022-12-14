import java.io.File

class Day12: Day {
    override val resourcePath = "day12"
    override val expectedPart1Results = listOf(31L)
    override val expectedPart2Results = listOf(29L)

    override fun part1(file: File): Long {
        return run(file, 27, 0)
    }

    override fun part2(file: File): Long {
        return run(file, 27, 1)
    }

    fun run(file: File, startingVal: Int, endingVal: Int): Long {
        val az = listOf('S') + ('a'..'z') + listOf('E')
        val data = file.readLines().map{ line -> line.map{ az.indexOf(it) } }
        return Maze(data, { maxOf(1, minOf(it,26)) }).findShortestPath(
            isStartingPoint = { it.originalValue == startingVal },
            isEndingPoint = { it.originalValue == endingVal },
            adjPointFilter = {point, adjCandidate ->
                point.value -1 <= adjCandidate.value 
            }
        )
    }
}
