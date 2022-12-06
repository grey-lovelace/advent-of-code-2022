import java.io.File
import transpose

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

    fun moveCrates(file: File, is9001: Boolean): String {
        val (stacksString, movesString) = file.readText().split(Regex("(\r?\n){2}"))
        val stacks = prepStacks(stacksString)
        prepMoves(movesString).forEach{ (amount, from, to) ->
            var removed = (1..amount).map{ stacks[from-1].removeLast() }
            stacks[to-1].addAll(if(is9001) removed.reversed() else removed)
        }
        return stacks.map{ it.last() }.joinToString("")
    }

    fun prepStacks(stacks: String): List<MutableList<Char>> {
        return stacks.lines()
            .reversed()
            .drop(1)
            .map{it.toList()} // Needed to make it find the transpose function
            .transpose()
            .map{ it.filter{ l -> l.isLetter()}.toMutableList() }
            .filter{ it.isNotEmpty() }
    }

    fun prepMoves(moves: String): List<List<Int>> {
        return moves.lines().map{ Regex("\\d+").findAll(it).map{ f -> f.value.toInt() }.toList() }
    }
}