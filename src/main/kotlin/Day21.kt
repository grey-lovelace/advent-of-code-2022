import java.io.File
import kotlin.collections.emptyList

class Day21: Day {
    override val resourcePath = "day21"
    override val expectedPart1Results = listOf(152L)
    override val expectedPart2Results = listOf(301L)

    override fun part1(file: File): Long {
        val monkeyMap = file.readLines().map{it.split(": ")}.map{Monkey(it[0], it[1].split(" "))}.associateBy{ it.name }
        return run("root", monkeyMap);
    }

    override fun part2(file: File): Long {
        val monkeyMap = file.readLines().map{it.split(": ")}.map{Monkey(it[0], it[1].split(" "))}.associateBy{ it.name }
        monkeyMap.get("humn")!!.inst = listOf()
        val root = monkeyMap.get("root")!!
        val works = whichOneWorks(root, monkeyMap)
        return findMissingValInChain(works[false]!!, monkeyMap, run(works[true]!!, monkeyMap));
    }

    fun run(name: String, monkeyMap: Map<String, Monkey>): Long {
        val instr = monkeyMap.get(name)!!.inst
        if(instr.size == 1){
            return instr[0].toLong()
        } else {
            val monkey1 = run(instr[0], monkeyMap)
            val monkey2 = run(instr[2], monkeyMap)
            return when(instr[1]){
                "+" -> monkey1 + monkey2
                "-" -> monkey1 - monkey2
                "*" -> monkey1 * monkey2
                "/" -> monkey1 / monkey2
                else -> throw Error()
            }
        }
    }

    fun findMissingValInChain(name: String, monkeyMap: Map<String, Monkey>, valToMatch: Long): Long {
        val monkey = monkeyMap.get(name)!!
        val instr = monkey.inst
        if(instr.size == 0){
            return valToMatch
        } else {
            val works = whichOneWorks(monkey, monkeyMap)
            val worksVal = run(works[true]!!, monkeyMap)
            val firstMonkeyMatched = works[true]!! == instr[0]
            val newValToMatch = when(instr[1]){
                "+" -> valToMatch - worksVal
                "-" -> if(firstMonkeyMatched) (valToMatch - worksVal) * -1 else valToMatch + worksVal
                "*" -> valToMatch / worksVal
                "/" -> if(firstMonkeyMatched) 1 / (valToMatch / worksVal) else valToMatch * worksVal
                else -> throw Error()
            }
            return findMissingValInChain(works[false]!!, monkeyMap, newValToMatch)
        }
    }

    fun whichOneWorks(monkey: Monkey, monkeyMap: Map<String, Monkey>): Map<Boolean, String> {
        return listOf(monkey.inst[0], monkey.inst[2]).associateBy{ mName ->
            try{
                run(mName, monkeyMap)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    data class Monkey(val name: String, var inst: List<String>)
}
