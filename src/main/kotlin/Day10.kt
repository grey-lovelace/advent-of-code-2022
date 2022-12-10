import java.io.File

class Day10: Day {
    override val resourcePath = "day10"
    override val expectedPart1Results = listOf(13140)
    override val expectedPart2Results = listOf<String>()

    override fun part1(file: File): Int {
        var x = 1
        var cycle = 1
        val importantCycles = (20..220 step 40)
        var nums = 0
        fun progress() {
            if (importantCycles.contains(cycle)) nums += (x * cycle)
            cycle++
        }
        file.readLines().map{ it.split(" ") }.forEach{ line ->
            progress()
            if (line.size == 2) {
                progress()
                x += line[1].toInt()
            }
        }
        return nums;
    }

    override fun part2(file: File): String {
        var x = 1
        var cycle = 0
        val importantCycles = (0..240 step 40)
        var pixels = (0..239).map{' '}.toMutableList()
        fun progress() {
            if (importantCycles.flatMap{(it+x-1..it+x+1)}.contains(cycle)) pixels[cycle] = '#'
            cycle++
        }
        file.readLines().map{ it.split(" ") }.forEach{ line ->
            progress()
            if (line.size == 2) {
                progress()
                x += line[1].toInt()
            }
        }
        return "\n" + pixels.chunked(40).map{ it.joinToString("") }.joinToString("\n")
    }
}
