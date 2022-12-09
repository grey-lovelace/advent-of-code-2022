import java.io.File

class Day08: Day {
    override val resourcePath = "day08"
    override val expectedPart1Results = listOf(21)
    override val expectedPart2Results = listOf(8)

    override fun part1(file: File): Int {
        return Matrix(file).let{m -> m.points.filter(m::isVisible) }.count()
    }

    override fun part2(file: File): Int {
        return Matrix(file).let{m -> m.points.map(m::scenicScore) }.maxOrNull()!!
    }

    data class Point(val x: Int, val y: Int, val value: Int)

    data class Matrix(val file: File) {
        var cache = mutableMapOf<String, Point?>();
        val matrix = file.readLines().map{ line -> line.map{ it.toString().toInt() } }
        val points = matrix.indices.flatMap{ x -> matrix[x].indices.map{ y -> Point(x, y, matrix[x][y]).also{ cache.put("$x~$y",it) } }}
        fun findPointOrNull(x: Int, y: Int): Point? = cache.getOrPut("$x~$y") { points.firstOrNull{ it.x == x && it.y == y } }
        fun leftOf(point: Point): List<Point> = (point.x-1 downTo 0).mapNotNull{ findPointOrNull(it, point.y) }
        fun rightOf(point: Point): List<Point> = (point.x+1..matrix.lastIndex).mapNotNull{ findPointOrNull(it, point.y) }
        fun above(point: Point): List<Point> = (point.y-1 downTo 0).mapNotNull{ findPointOrNull(point.x, it) }
        fun below(point: Point): List<Point> = (point.y+1..matrix[0].lastIndex).mapNotNull{ findPointOrNull(point.x, it) }

        fun isVisible(point: Point): Boolean {
            return listOf(leftOf(point), rightOf(point), above(point), below(point))
                .any{ adj -> adj.none{ point.value <= it.value }}
        }

        fun scenicScore(point: Point): Int {
            return listOf(leftOf(point), rightOf(point), above(point), below(point)).fold(1) { acc, adj ->
                acc * adj.takeWhile{ point.value > it.value }.count().let{ count -> (if (adj.isNotEmpty() && count < adj.size) 1 else 0) + count }
            }
        }
    }
}
