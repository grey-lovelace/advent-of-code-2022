import java.io.File
import org.json.*

class Day13: Day {
    override val resourcePath = "day13"
    override val expectedPart1Results = listOf(13)
    override val expectedPart2Results = listOf(140)

    override fun part1(file: File): Int {
        return file.readText()
            .split(Regex("(?:\r?\n){2}"))
            .map{ it.lines() }
            .map{ it.map{ line -> JSONArray(line) } }
            .map{ (list1, list2) -> compare(list1, list2) }
            .withIndex()
            .filter{it.value!!}
            .map{it.index + 1}
            .sum()
    }

    override fun part2(file: File): Int {
        val dividerPackets = listOf("[[2]]", "[[6]]")
        return file.readText()
            .replace(Regex("(?:\r?\n){2}"),"\n")
            .lines()
            .let{ it + dividerPackets }
            .map{ JSONArray(it) }
            .sortedWith(Comparator<JSONArray>{ a, b -> if(compare(a,b)!!) -1 else 1})
            .withIndex()
            .filter{ dividerPackets.contains(it.value.toString()) }
            .map{ it.index + 1 }
            .product()
    }

    fun compare(item1: Any, item2: Any): Boolean? {
        if(item1 is Int && item2 is Int) {
            return if (item1 != item2)  item1 < item2 else null
        }
        val item1List = if (item1 is JSONArray) item1 else JSONArray().put(item1)
        val item2List = if (item2 is JSONArray) item2 else JSONArray().put(item2)
        (0..item1List.length()-1).forEach{ i ->
            if (item1List.length() > i && item2List.length() <= i) return false
            val result = compare(item1List[i], item2List[i])
            if (result != null) return result
        }
        if(item1List.length() < item2List.length()) return true
        return null;
    }
}