import java.io.File

class Day02: Day {
    override val resourcePath = "day02"
    override val expectedPart1Results = listOf(15)
    override val expectedPart2Results = listOf(12)
    
    override fun part1(file: File): Int {
        return score(file, false)
    }
    
    override fun part2(file: File): Int {
        return score(file, true)
    }

    val wins = mapOf(1 to 3, 2 to 1, 3 to 2)
    val ties = mapOf(1 to 1, 2 to 2, 3 to 3)
    val loses = mapOf(1 to 2, 2 to 3, 3 to 1)
    // shouldDo seems reversed because it is used from the perspective of opponent
    val shouldDo = mapOf(1 to wins, 2 to ties, 3 to loses)

    fun score(file: File, newRule: Boolean): Int {
        return file.readLines()
            .map{ Pair("ABC".indexOf(it[0])+1, "XYZ".indexOf(it[2])+1) }
            .map{ Pair(it.first, if (newRule) shouldDo[it.first]!![it.second]!! else it.second) }
            .map{ when { 
                    it.second == it.first -> 3
                    wins[it.second] == it.first -> 6
                    else -> 0
                } + it.second
            }
            .sum()
    }
}
