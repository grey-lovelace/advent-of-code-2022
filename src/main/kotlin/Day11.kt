import java.io.File

class Day11: Day {
    override val resourcePath = "day11"
    override val expectedPart1Results = listOf(10605L)
    override val expectedPart2Results = listOf(2713310158L)

    override fun part1(file: File): Long {
        val monkeys = makeMonkeys(file)
        return run(monkeys, 20, {worry -> worry / 3})
    }

    override fun part2(file: File): Long {
        val monkeys = makeMonkeys(file)
        val commonFactor = monkeys.map(Monkey::divisBy).product()
        return run(monkeys, 10000, {worry -> worry % commonFactor})
    }

    fun makeMonkeys(file:File): List<Monkey> = 
        """[a-z: ]+([\d, ]+)[a-z:=\s]+([+*]) (\w+)[a-z:\s]+(\d+)[a-z:\s]+(\d+)[a-z:\s]+(\d+)"""
            .toRegex(RegexOption.IGNORE_CASE).findAll(file.readText())
            .map{ Monkey(it.destructured.toList()) }.toList()

    fun run(monkeys: List<Monkey>, rounds: Int, worryFunc: (worry: Long) -> Long): Long {
        (0 until rounds).forEach{ _ ->
            monkeys.forEach{ currMonkey ->
                while(currMonkey.items.size > 0) {
                    var item = currMonkey.items[0]
                    var change = if (currMonkey.math2=="old") item.worryLvl else currMonkey.math2.toLong()
                    item.worryLvl = when (currMonkey.mathOp) {
                        "*" ->  item.worryLvl * change
                        "+" ->  item.worryLvl + change
                        else -> throw Error()
                    }
                    item.worryLvl = worryFunc(item.worryLvl)
                    if(item.worryLvl % currMonkey.divisBy == 0.toLong()){
                        monkeys[currMonkey.trueTarget].items.add(item)
                    } else {
                        monkeys[currMonkey.falseTarget].items.add(item)
                    }
                    currMonkey.items.remove(item)
                    currMonkey.inspectionCount++
                }
            }
        }
        return monkeys.map(Monkey::inspectionCount).sortedDescending().take(2).product()
    }

    data class Item(var worryLvl: Long)

    data class Monkey(val data: List<String>) {
        val items = data[0].split(",").map(String::trim).map{ Item(it.toLong()) }.toMutableList()
        val mathOp = data[1]
        val math2 = data[2]
        val divisBy = data[3].toLong()
        val trueTarget = data[4].toInt()
        val falseTarget = data[5].toInt()
        var inspectionCount = 0L
    }
}
