import java.io.File

class Day08: Day {
    override val resourcePath = "day08"
    override val expectedPart1Results = listOf(21)
    override val expectedPart2Results = listOf(8)

    var findCache = mutableMapOf<Pair<Int,Int>, Int>();

    override fun part1(file: File): Int {
        findCache.clear()
        val matrix = file.readLines()
            .map{ line -> line.map{ it.toString().toInt() } }

        val allPoints = matrix.indices.flatMap{ x -> matrix[x].indices.map{ y -> Pair(x,y) }}
        return allPoints.filter{ isVisible(it, matrix) }
            .count()
    }

    override fun part2(file: File): Int {
        findCache.clear()
        val matrix = file.readLines()
            .map{ line -> line.map{ it.toString().toInt() } }

        val allPoints = matrix.indices.flatMap{ x -> matrix[x].indices.map{ y -> Pair(x,y) }}
        return allPoints.map{ scenicScore(it, matrix) }.maxOrNull()!!
    }

    fun List<List<Int>>.find(point: Pair<Int,Int>): Int = findCache.getOrPut(point) { (this.getOrNull(point.first) ?: listOf()).getOrNull(point.second) ?: -1 }

    fun isVisible(point: Pair<Int,Int>, input: List<List<Int>>): Boolean {
        val current = input.find(point)
        val leftx = point.first == 0 || (0..point.first-1).map{ x -> input.find(Pair(x, point.second)) }.none{ current <= it }
        val rightx = point.first == input.lastIndex || (point.first+1..input.lastIndex).map{ x -> Pair(x, point.second) }.map{ input.find(it) }.none{ current <= it }
        val lefty = point.second == 0 || (0..point.second-1).none{ y -> current <= input.find(Pair(point.first, y)) }
        val righty = point.second == input[0].lastIndex || (point.second+1..input[0].lastIndex).none{ y -> current <= input.find(Pair(point.first, y)) }
        val result = leftx ||
            rightx ||
            lefty ||
            righty
        return result
    }

    fun scenicScore(point: Pair<Int,Int>, input: List<List<Int>>): Int {
        val current = input.find(point)
        var x = point.first - 1
        var leftX = 0
        asdf@ while(input.find(Pair(x, point.second)) != -1) {
            leftX++
            if (current <= input.find(Pair(x, point.second))) break@asdf
            x--
        }
        x = point.first + 1
        var rightX = 0
        asdf@ while(input.find(Pair(x, point.second)) != -1) {
            rightX++
            if (current <= input.find(Pair(x, point.second))) break@asdf
            x++
        }

        var y = point.second - 1
        var leftY = 0
        asdf@ while(input.find(Pair(point.first, y)) != -1) {
            leftY++
            if (current <= input.find(Pair(point.first, y))) break@asdf
            y--
        }
        y = point.second + 1
        var rightY = 0
        asdf@ while(input.find(Pair(point.first, y)) != -1) {
            rightY++
            if (current <= input.find(Pair(point.first, y))) break@asdf
            y++
        }

        val result = leftX * rightX * leftY * rightY
        return result
    }
}
