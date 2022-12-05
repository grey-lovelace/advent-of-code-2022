import java.io.File

class Day05: Day {
    override val resourcePath = "day05"
    override val expectedPart1Results = listOf("CMZ")
    override val expectedPart2Results = listOf("MCD")

    override fun part1(file: File): String {
        val (stacks, instructions) = getStacksAndInstructions(file)
        instructions.forEach{ (amount, from, to) ->
            (1..amount).forEach{
                stacks[to-1].add(stacks[from-1].removeLast())
            }
        }
        return stacks.map{ it.last() }.joinToString("")
    }

    override fun part2(file: File): String {
        val (stacks, instructions) = getStacksAndInstructions(file)
        instructions.forEach{ (amount, from, to) ->
            stacks[to-1].addAll(stacks[from-1].takeLast(amount))
            (1..amount).forEach{ stacks[from-1].removeLast() }
        }
        return stacks.map{ it.last() }.joinToString("")
    }

    fun getStacksAndInstructions(file: File): Pair<List<MutableList<Char>>, List<List<Int>>> {
        var (stacksString, instructions) = file.readText().split(Regex("(\r?\n){2}"))
        return Pair(prepStacks(stacksString), prepInstructions(instructions))
    }

    fun prepStacks(stacks: String): List<MutableList<Char>> {
        return stacks.lines()
            .subList(0, stacks.lines().lastIndex)
            .map{ it.substring(1)}
            .map{ (0..it.lastIndex step 4).map(it::get) }
            .reversed()
            .fold(mutableListOf<MutableList<Char>>(), { acc, it ->
                it.indices.forEach{ i -> if (acc.elementAtOrNull(i) == null) acc.add(mutableListOf()) };
                it.forEachIndexed{ i, l -> acc[i].add(l) };
                acc;
            })
            .map{ it.joinToString("").trim().toMutableList() }
    }

    fun prepInstructions(instructions: String): List<List<Int>> {
        return instructions
            .lines()
            .map{ Regex("\\d+").findAll(it).map{ f -> f.value.toInt() }.toList() }
    }


}
