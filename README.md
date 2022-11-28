# AOC Kotlin Template

A template for doing Advent of Code in Kotlin!

Using Kotlin scripts (.kts) files are a little less verbose, but it makes it much harder to integrate with IDE intellisense. This setup makes that work.

To run a day, simply run: `./gradlew run --args="{spaceDelimittedDayNumbers}"`

Example: `./gradlew run --args="5 6"` would run the Day5 and Day6 classes.

To run all days in order, simply run: `./gradlew run --args="ALL"`

## On calling `run`

When calling the `run` method, you will pass in 2 lists of expected test values. For each list, it will run the first expected result against the corrosponding `sample.txt` contents. Any additional test values after the first try to find file `sample{expectedValueIndex}.txt` and use that as the input instead.

Example: Passing in `run(listOf(123, 456), listOf(789))` will:
- verify 123 is the output of `part1` with file `sample.txt`
- verify 456 is the output of `part1` with file `sample1.txt`
- run `part1` with file `input.txt` and output the value to console
- verify 789 is the output of `part2` with file `sample.txt`
- run `part2` with file `input.txt` and output the value to console

This allows for flexibility if wanting to test multiple scenarios before the actual input runs.