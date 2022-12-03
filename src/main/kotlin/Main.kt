import Day
import kotlin.reflect.full.createInstance

fun main(args: Array<String>) {
    when{
        args[0].equals("ALL", ignoreCase = true) -> (1..25).forEach{ runDay(it.toString()) }
        args.all { it.toIntOrNull() in (1..25) } -> args.forEach { runDay(it) }
        else -> println("Only accepts args of \"ALL\" or a list of integers 1-25")
    }
}

fun runDay(dayString: String) {
    val dayClassString = "Day${dayString.padStart(2, '0')}";
    println("Running ${dayClassString}...")
    val day: Day = Class.forName(dayClassString).kotlin.createInstance() as Day
    day.run()
}
