import java.io.File

class Day07: Day {
    override val resourcePath = "day07"
    override val expectedPart1Results = listOf(95437)
    override val expectedPart2Results = listOf(24933642)

    override fun part1(file: File): Int {
        return createSizeMap(file).values.filter{ it <= 100000 }.sum()
    }

    override fun part2(file: File): Int {
        val sizeNeeded = 30000000 - (70000000 - file.readText().findNumbers().sum())
        return createSizeMap(file).values.filter{ it >= sizeNeeded }.minOrNull()!!
    }

    fun createSizeMap(file: File): Map<List<String>,Int> {
        var sizeMap = mutableMapOf<List<String>,Int>();
        var currentDirStack = mutableListOf<String>();
        file.readLines().forEach{
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
        }
        return sizeMap
    }
}
