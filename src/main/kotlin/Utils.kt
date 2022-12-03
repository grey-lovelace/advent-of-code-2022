import Day
import kotlin.reflect.full.createInstance

fun <T> List<T>.peekPrint(): List<T> {
    return this.map{ it -> println(it); it; }
}
