import java.io.File

class Day07: Day {
    override val resourcePath = "day07"
    override val expectedPart1Results = listOf(95437)
    override val expectedPart2Results = listOf(24933642)

    override fun part1(file: File): Int {
        return file
            .let(::createSizeMap)
            .values
            .filter{ it <= 100000 }
            .sum()
    }

    override fun part2(file: File): Int {
        return file
            .let(::createSizeMap)
            .values
            .filter{ it >= 30000000 - (70000000 - file.readText().findNumbers().sum()) }
            .minOrNull()!!
    }

    fun createSizeMap(file: File): Map<List<String>,Int> {
        return file
            .readLines()
            .fold(Tracker(), { (currentDirStack, sizeMap), line ->
                when {
                    line.equals("$ cd ..") -> currentDirStack.removeLast()
                    line.startsWith("$ cd") ->  currentDirStack.add(line.split(" ").last())
                    Regex("\\d+ .*") matches line -> {
                        val fileSize = line.split(" ")[0].toInt()
                        currentDirStack.indices.forEach{ i ->
                            val dir = currentDirStack.slice(0..i)
                            sizeMap.put(dir, sizeMap.getOrDefault(dir, 0) + fileSize);
                        }
                    }
                }
                Tracker(currentDirStack, sizeMap)
            })
            .sizeMap
    }

    data class Tracker (
        val currentDirStack: MutableList<String> = mutableListOf<String>(),
        var sizeMap: MutableMap<List<String>,Int> = mutableMapOf<List<String>,Int>()
    )
}
