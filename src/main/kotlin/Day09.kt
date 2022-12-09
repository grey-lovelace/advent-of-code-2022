import java.io.File
import kotlin.math.sign

class Day09: Day {
    override val resourcePath = "day09"
    override val expectedPart1Results = listOf(13)
    override val expectedPart2Results = listOf(1, 36)

    override fun part1(file: File): Int {
        return run(file, 1);
    }

    override fun part2(file: File): Int {
        return run(file, 9);
    }

    fun run(file: File, numOfTails: Int ): Int {
        var knots = (0..numOfTails).map{Pair(0,0)}.toMutableList()
        var visited = mutableSetOf(Pair(0,0))
        file.readLines().map{ it.split(" ") }.forEach{ (dir, amount) ->
            (1..amount.toInt()).forEach{
                knots[0] = when (dir) {
                    "R" -> Pair(knots[0].first+1, knots[0].second)
                    "L" -> Pair(knots[0].first-1, knots[0].second)
                    "U" -> Pair(knots[0].first, knots[0].second+1)
                    "D" -> Pair(knots[0].first, knots[0].second-1)
                    else -> throw Error("Should not get here")
                }
                (1..numOfTails).forEach{ i ->
                    val prev = knots[i-1]
                    if(!knots[i].adjPoints(true).contains(prev)) {
                        val xDist = (prev.first - knots[i].first).sign
                        val yDist = (prev.second - knots[i].second).sign
                        knots[i] = Pair(knots[i].first + xDist, knots[i].second + yDist)
                    }
                }
                visited.add(knots.last())
            }
        }
        return visited.count();
    }
}
