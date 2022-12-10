import java.io.File

class Day10: Day {
    override val resourcePath = "day10"
    override val expectedPart1Results = listOf(13140)
    override val expectedPart2Results = listOf<String>()

    override fun part1(file: File): Int {
        var x = 1
        var cycle = 1
        var result = 0
        fun progress() {
            if ((20..220 step 40).contains(cycle)) result += (x * cycle)
            cycle++
        }
        file.readLines().map{ it.split(" ") }.forEach{ line ->
            progress()
            if (line.size == 2) {
                progress()
                x += line[1].toInt()
            }
        }
        return result;
    }

    override fun part2(file: File): String {
        var x = 1
        var cycle = 0
        var pixels = (0..239).map{' '}.toMutableList()
        fun progress() {
            if ((x-1..x+1).contains(cycle % 40)) pixels[cycle] = '#'
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
