data class Maze(val data: List<List<Int>>, val mapToValue: (originalValue: Int) -> Int = {it}){
    val points: List<Point> = data.indices.flatMap{ x -> data[x].indices.map{ y -> Point(x,y,data[x][y],mapToValue(data[x][y]))} }

    data class Point(val x: Int, val y: Int, val originalValue: Int, val value: Int){
        val orthoganalPairs = listOf(x-1,x+1,x,x).zip(listOf(y,y,y+1,y-1))
        val diagonalPairs = listOf(x-1,x-1,x+1,x+1).zip(listOf(y-1,y+1,y-1,y+1))
        var adjPoints: List<Point> = listOf()
        var distance: Long? = null
        var visited = false
    }

    fun findShortestPath(isStartingPoint: (point: Point) -> Boolean, isEndingPoint: (point: Point) -> Boolean, includeDiagnals: Boolean = false, addValueAsDistance: Boolean = false, adjPointFilter: (point: Point, adjCandidate: Point) -> Boolean): Long {
        val allPoints = points.map{ Point(it.x, it.y ,it.originalValue, it.value) }
        val allPointsByPair = allPoints.associateBy{ Pair(it.x, it.y) }
        allPoints.forEach{ point ->
            point.adjPoints = point.orthoganalPairs
                .let{ if (includeDiagnals) it + point.diagonalPairs else it}
                .mapNotNull{ pair -> allPointsByPair[pair] }
                .filter{ adjPointFilter(point, it) }
        }
        allPoints.filter(isStartingPoint).forEach{ it.distance = 0 }
        val endPoints = allPoints.filter(isEndingPoint)
        // Dijkstra time!
        while(endPoints.all{ it.distance == null }) {
            val low = allPoints.filter{ !it.visited && it.distance != null }.minByOrNull{ it.distance!! }!!
            low.adjPoints.forEach{ adj ->
                val toAdj = low.distance!! + if (addValueAsDistance) adj.value else 1
                if(toAdj < adj.distance ?: Long.MAX_VALUE){ 
                    adj.distance = toAdj
                } 
            }
            low.visited = true
        }
        return endPoints.first{ it.distance != null}.distance!!;
    }
}