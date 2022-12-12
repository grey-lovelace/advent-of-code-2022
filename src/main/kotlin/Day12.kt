import java.io.File

class Day12: Day {
    override val resourcePath = "day12"
    override val expectedPart1Results = listOf(31L)
    override val expectedPart2Results = listOf(29L)

    override fun part1(file: File): Long {
        return findShortestPath(file, 0)
    }

    override fun part2(file: File): Long {
        return findShortestPath(file, 1)
    }

    fun findShortestPath(file: File, startingVal: Int): Long{
        val az = listOf('S') + ('a'..'z') + listOf('E')
        val data = file.readLines().map{ line -> line.map{ az.indexOf(it) } }

        // Prepare our points
        val pairsToPoints = data.indices
            .flatMap{ x -> data[x].indices.map{ y -> Pair(x,y) } }
            .associateWith { (x,y) -> Point(x,y,data[x][y]) }
        val allPoints = pairsToPoints.values
        allPoints.forEach{ point ->
            point.adjPoints = listOf(point.x-1,point.x+1,point.x,point.x).zip(listOf(point.y,point.y,point.y+1,point.y-1))
                .mapNotNull{ pair -> pairsToPoints[pair] }
                .filter{ (0..point.value+1).contains(it.value) }
        }

        val startingPoints = allPoints.filter{ it.value == startingVal }
        val endPoint = allPoints.first{ it.value == 27 } // 27 is value of E
        return startingPoints.map{ startingPoint ->
            // Reset vals. This this gross. Need to rework.
            allPoints.forEach{ it.distance = null; it.visited = false}
            startingPoint.distance = 0
            var visited = 1
            // Dijkstra time!
            while(endPoint.distance == null && allPoints.any{ !it.visited && it.distance != null }){
                val low = allPoints.filter{ !it.visited && it.distance != null }.minByOrNull{ it.distance!! }!!
                low.adjPoints.forEach{ adj ->
                    val toAdj = low.distance!! + 1
                    if(toAdj < adj.distance ?: Long.MAX_VALUE){
                        adj.distance = toAdj
                    }
                }
                low.visited = true
                visited++
            }
            endPoint.distance ?: Long.MAX_VALUE
        }.minOrNull()!!
    }
    
    data class Point(val x: Int, val y: Int, val value: Int){
        var adjPoints: List<Point> = listOf()
        var distance: Long? = null
        var visited = false
    }
}
