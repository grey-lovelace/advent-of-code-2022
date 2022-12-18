import java.io.File

class Day18: Day {
    override val resourcePath = "day18"
    override val expectedPart1Results = listOf(64)
    override val expectedPart2Results = listOf(58)

    override fun part1(file: File): Int {
        val points = findPoints(file)
        return points.flatMap(Point::adjPoints).count{!points.contains(it)}
    }

    override fun part2(file: File): Int {
        val points = findPoints(file)

        fun Point.isOutOfBounds(): Boolean {
            return this.x > points.maxOf{it.x}+1 ||
                this.x < points.minOf{it.x}-1 ||
                this.y > points.maxOf{it.y}+1 ||
                this.y < points.minOf{it.y}-1 ||
                this.z > points.maxOf{it.z}+1 ||
                this.z < points.minOf{it.z}-1
        }
        val toVisit = mutableListOf(Point(points.minOf{it.x}-1, points.minOf{it.y}-1, points.minOf{it.z}-1))
        val visited = mutableSetOf<Point>()
        while(toVisit.isNotEmpty()){
            val currPoint = toVisit.removeFirst()
            visited.add(currPoint)
            currPoint.adjPoints()
                .filterNot{it.isOutOfBounds()}
                .filterNot(points::contains)
                .filterNot(visited::contains)
                .filterNot(toVisit::contains)
                .forEach(toVisit::add)
        }

        return points
            .flatMap(Point::adjPoints)
            .filterNot(points::contains)
            .count(visited::contains)
    }

    fun findPoints(file: File): Set<Point> {
        return file.readLines()
            .map{it.split(",").map(String::toInt)}
            .map{Point(it[0], it[1], it[2])}
            .toSet()
    }

    data class Point(val x: Int, val y : Int, val z: Int) {
        fun adjPoints() = setOf(
            Point(x-1,y,z),
            Point(x+1,y,z),
            Point(x,y-1,z),
            Point(x,y+1,z),
            Point(x,y,z-1),
            Point(x,y,z+1)
        )
    }
}
