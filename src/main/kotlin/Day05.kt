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
        return file.readText()
            .split(Regex("(\r?\n){2}"))
            .let{ Pair(prepStacks(it[0]), it[1].lines().map{ l -> l.findNumbers() }) }
            .also{ (stacks, moves) ->
                moves.forEach{ (amount, from, to) ->
                    (1..amount).map{ stacks[from-1].removeLast() }
                        .let{ if(movesMultipleAtOnce) it.reversed() else it }
                        .also{ stacks[to-1].addAll(it) }
                }
            }
            .first
            .map{ it.last() }
            .joinToString("")
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