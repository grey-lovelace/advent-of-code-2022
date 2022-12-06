import java.io.File

class Day05: Day {
    override val resourcePath = "day05"
    override val expectedPart1Results = listOf("CMZ")
    override val expectedPart2Results = listOf("MCD")

    override fun part1(file: File): String {
        return moveCrates(file, false)
    }

    override fun part2(file: File): String {
        return moveCrates(file, true)
    }

    fun moveCrates(file: File, movesMultipleAtOnce: Boolean): String {
        val (stacksString, moves) = file.readText().split(Regex("(\r?\n){2}"))
        val stacks = prepStacks(stacksString)
        moves.lines()
            .map{ it.findNumbers() }
            .forEach{ (amount, from, to) ->
                var removed = (1..amount).map{ stacks[from-1].removeLast() }
                stacks[to-1].addAll(if(movesMultipleAtOnce) removed.reversed() else removed)
            }
        return stacks.map{ it.last() }.joinToString("")
    }

    fun prepStacks(stacks: String): List<MutableList<Char>> {
        return stacks.lines()
            .reversed()
            .drop(1)
            .map{it.toList()} // Needed to make it find the transpose function
            .transposed()
            .map{ it.filter{ l -> l.isLetter()}.toMutableList() }
            .filter{ it.isNotEmpty() }
    }
}