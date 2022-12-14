import java.io.File

class Day14: Day {
    override val resourcePath = "day14"
    override val expectedPart1Results = listOf(24)
    override val expectedPart2Results = listOf(93)

    override fun part1(file: File): Int {
        return run(file) - 1
    }

    override fun part2(file: File): Int {
        // I run for 1m 20s. FIX ME.
        return run(file, true)
    }

    fun run(file: File, hasFloor: Boolean = false): Int {
        val rockPoints = buildRockPoints(file)
        val lowestYPlane = rockPoints.map{it.y}.maxOrNull()!! + if (hasFloor) 1 else 0
        val existingSand = mutableListOf<Point>()
        var shouldStop = false
        while(!shouldStop){
            val sand = Point(500,0)
            sand.fall(rockPoints + existingSand, lowestYPlane)
            val hasPassedBottomShelf = lowestYPlane == sand.y
            val hasPluggedEntrance = Point(500,0) == sand
            shouldStop = if (hasFloor) hasPluggedEntrance else hasPassedBottomShelf
            existingSand.add(sand)
        }
        return existingSand.size;
    }

    fun buildRockPoints(file: File): List<Point> {
        return file.readLines()
            .map{ it.findNumbers().chunked(2).map{ (x,y) -> Point(x,y) } }
            .flatMap{ it.fold(mutableListOf<Point>(), { acc, point ->
                acc.addAll(point.drawLineTo(acc.lastOrNull()))
                acc.add(point) // Ensure the last point is the real one so the next cycle can use it. Need to make this work better.
                acc
            })}
            .distinct()
    }

    data class Point(var x: Int, var y: Int) {
        fun drawLineTo(nextPoint: Point?): List<Point> {
            if (nextPoint == null) return listOf(this)
            return (minOf(x, nextPoint.x)..maxOf(x, nextPoint.x)).flatMap{x ->
                (minOf(y, nextPoint.y)..maxOf(y, nextPoint.y)).map{y ->
                    Point(x,y)
                }
            }
        }

        fun fall(filledPoints: List<Point>, lowestYPlane: Int){
            var canMove = true
            while(canMove && lowestYPlane != this.y) {
                val newPoint = listOf(Point(x,y+1),Point(x-1,y+1),Point(x+1,y+1))
                    .firstOrNull{ possibleNewPoint ->  !filledPoints.contains(possibleNewPoint) }
                if (newPoint != null) {
                    x = newPoint.x
                    y = newPoint.y
                } else {
                    canMove = false
                }
            }
        }
    }
}
