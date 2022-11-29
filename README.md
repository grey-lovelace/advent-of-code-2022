# AOC Kotlin 2022

2022 Advent of code using the [Kotlin AOC Template](https://github.com/grey-lovelace/advent-of-code-template-kotlin)

# From Template
Using Kotlin scripts (.kts) files are a little less verbose, but it makes it much harder to integrate with IDE intellisense. This setup makes that work.

To run a day, simply run: `./gradlew run --args="{spaceDelimittedDayNumbers}"`

Example: `./gradlew run --args="5 6"` would run the Day5 and Day6 classes.

To run all days in order, simply run: `./gradlew run --args="ALL"`

## On testing with sample inputs

Each Day class has optional lists of expeced results you may set for part1 and part2. For each list, it will run the first expected result against the corrosponding `sample.txt` contents. Any additional test values after the first try to find file `sample{expectedValueIndex}.txt` and use that as the input instead.

Example: Setting 
```
    override val expectedPart1Results = listOf(123, 456)
    override val expectedPart2Results = listOf(789)
```
will:
- verify 123 is the output of `part1` with file `sample.txt`
- verify 456 is the output of `part1` with file `sample1.txt`
- run `part1` with file `input.txt` and output the value to console
- verify 789 is the output of `part2` with file `sample.txt`
- run `part2` with file `input.txt` and output the value to console

This allows for flexibility if wanting to test multiple scenarios before the actual input runs.