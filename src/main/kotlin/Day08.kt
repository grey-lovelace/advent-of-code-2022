import java.io.File

class Day08: Day {
    override val resourcePath = "day08"
    override val expectedPart1Results = listOf(21)
    override val expectedPart2Results = listOf(8)

    override fun part1(file: File): Int {
        return Matrix(file).points.filter(Point::isVisible).count()
    }

    override fun part2(file: File): Int {
        return Matrix(file).points.map(Point::scenicScore).maxOrNull()!!
    }

    data class Matrix(val file: File) {
        val matrix = file.readLines().map{ line -> line.map{ it.toString().toInt() } }
        val points = matrix.indices.flatMap{ x -> matrix[x].indices.map{ y -> Point(x, y, matrix[x][y], this) }}
        val pointMap = points.associateBy{ Pair(it.x,it.y) }
    }

    data class Point(val x: Int, val y: Int, val value: Int, val matrix: Matrix) {
        val left = (this.x-1 downTo 0).map{ Pair(it, this.y) }
        val right = (this.x+1..matrix.matrix.lastIndex).map{ Pair(it, this.y) }
        val above = (this.y-1 downTo 0).map{ Pair(this.x, it) }
        val below = (this.y+1..matrix.matrix[0].lastIndex).map{ Pair(this.x, it) }

        fun allAdjPoints() = listOf(left, right, above, below).map{ it.mapNotNull(matrix.pointMap::get) }

        fun isVisible(): Boolean = allAdjPoints().any{ adj -> adj.all{ this.value > it.value }}

        fun scenicScore(): Int = allAdjPoints().fold(1) { acc, adj ->
            acc * adj.takeWhile{ this.value > it.value }.count()
                .let{ count -> (if (adj.isNotEmpty() && count < adj.size) 1 else 0) + count }
        }
    }
}
