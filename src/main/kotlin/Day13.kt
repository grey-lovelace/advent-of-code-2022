import java.io.File

class Day13: Day {
    override val resourcePath = "day13"
    override val expectedPart1Results = listOf(13)
    override val expectedPart2Results = listOf(140)

    override fun part1(file: File): Int {
        return file.readText()
            .split(Regex("(?:\r?\n){2}"))
            .map{ it.lines() }
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
            .sortedWith(Comparator<String>{ a, b -> if(compare(a,b)!!) -1 else 1})
            .withIndex()
            .filter{ dividerPackets.contains(it.value) }
            .map{ it.index + 1 }
            .product()
    }

    fun compare(item1: String, item2: String): Boolean? {
        if(item1.isNum() && item2.isNum()){
            if (item1.toInt() < item2.toInt()) return true
            if (item1.toInt() > item2.toInt()) return false
            return null
        }
        val item1List = item1.let{if (it.isList()) it.drop(1).dropLast(1) else it}.splitToList()
        val item2List = item2.let{if (it.isList()) it.drop(1).dropLast(1) else it}.splitToList()
        item1List.indices.forEach{ i ->
            if(item1List.size > i && item2List.size <= i) return false
            val result = compare(item1List[i], item2List[i])
            if (result != null) return result
        }
        if(item1List.size < item2List.size) return true
        return null;
    }
    
    fun String.isNum() = Regex("\\d+").matches(this)
    fun String.isList() = this.startsWith("[")
    fun String.splitToList(): List<String> {
        val list = mutableListOf<String>()
        if(this.isBlank()) return list
        var bracketCount = 0
        var currentString = ""
        this.forEach{
            when (it) {
                ',' -> {
                    if (bracketCount == 0) {
                        list.add(currentString)
                        currentString = ""
                    } else {
                        currentString += it
                    }
                }
                '[' -> {
                    bracketCount++
                    currentString += it
                }
                ']' -> {
                    bracketCount--
                    currentString += it
                }
                else -> currentString += it
            }
        }
        list.add(currentString)
        return list.toList()
    }
}
