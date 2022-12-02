import java.io.File

interface Day {
    val resourcePath: String
    val expectedPart1Results: List<Any>
    val expectedPart2Results: List<Any>

    fun run() {
        val path = "src/main/resources/${resourcePath}"

        expectedPart1Results.forEachIndexed{ i, it ->
            val sampleFileSuffux = if (i==0) "" else i;
            val result = this.part1(File("${path}/sample${sampleFileSuffux}.txt"))
            check(it == result, { "Part 1 - sample${sampleFileSuffux}.txt Result: ${result}  Expected: ${it}" })
        }
        val part1Result = this.part1(File("${path}/input.txt"))
        println("Part1 = ${part1Result}")

        expectedPart2Results.forEachIndexed{ i, it ->
            val sampleFileSuffux = if (i==0) "" else i;
            val result = this.part2(File("${path}/sample${sampleFileSuffux}.txt"))
            check(it == result, { "Part 2 - sample${sampleFileSuffux}.txt Result: ${result}  Expected: ${it}" })
        }
        val part2Result = this.part2(File("${path}/input.txt"))
        println("Part2 = ${part2Result}")
    }

    fun part1(file: File): Any

    fun part2(file: File): Any
}
