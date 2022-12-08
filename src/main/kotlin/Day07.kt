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
            .fold(Tracker(), { (currentDirStack, sizeMap), it ->
                when {
                    it.equals("$ cd ..") -> currentDirStack.removeLast()
                    it.startsWith("$ cd") ->  currentDirStack.add(it.split(" ").last())
                    Regex("\\d+ .*") matches it -> {
                        val fileSize = it.split(" ")[0].toInt()
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
