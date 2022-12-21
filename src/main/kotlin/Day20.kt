import java.io.File

class Day20: Day {
    override val resourcePath = "day20"
    override val expectedPart1Results = listOf(3L)
    override val expectedPart2Results = listOf(1623178306L)

    override fun part1(file: File): Long {
        val nums = file.readLines().map(String::toLong).mapIndexed { i, it -> Wrapper(it, i, it) }
        val newNums = nums.fold(nums.toMutableList()) { acc, num ->
            if(num.value != 0L){
                val currIndex = acc.indexOf(num)
                acc.remove(num)
                val newIndex = acc.sanitizeIndex(currIndex + num.value)
                if (newIndex==0) {
                    acc.add(num)
                } else {
                    acc.add(newIndex, num)
                }
            }
            acc
        }
        println(newNums)
        return listOf(1000,2000,3000)
            .map{ it + newNums.withIndex().first{ it.value.value == 0L }.index }
            .map{ newNums.sanitizeIndex(it.toLong()) }
            .map(newNums::get)
            .map(Wrapper::value)
            .sum()
    }

    override fun part2(file: File): Long {
        val numsBig = file.readLines().map(String::toLong).map{it*811589153L}
        val nums = numsBig.mapIndexed{ i, it -> Wrapper(it, i, it) }
        var mixed = nums.toMutableList()
        (0 until 10).forEach{ i ->
            println(i)
            // println(mixed.map(Wrapper::originalValue))
            mixed = nums.fold(mixed) { acc, num ->
                // println(num)
                // println(acc)
                if(num.value != 0L){
                    val currIndex = acc.indexOf(num)
                    acc.remove(num)
                    val newIndex = acc.sanitizeIndex(currIndex + num.value)
                    if (newIndex==0) {
                        acc.add(num)
                    } else {
                        acc.add(newIndex, num)
                    }
                }
                acc
            }
        }
        
        // println(mixed)
        return listOf(1000,2000,3000)
            // .also(::println)
            .map{ it + mixed.withIndex().first{ it.value.value == 0L }.index }
            // .also(::println)
            .map{ mixed.sanitizeIndex(it.toLong()) }
            // .also(::println)
            .map(mixed::get)
            // .also(::println)
            .map(Wrapper::originalValue)
            .sum()
    }

    fun <T> List<T>.sanitizeIndex(newIndex: Long): Int {
        var returnVal = newIndex
        while(returnVal <= 0) {
            returnVal += this.size
        }
        return (returnVal % this.size.toLong()).toInt()
    }

    data class Wrapper(val value: Long, val originalIndex: Int, val originalValue: Long)
}
