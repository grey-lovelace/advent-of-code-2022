import Day
import kotlin.reflect.full.createInstance

fun <T> List<T>.peekPrint(): List<T> {
    return this.map{ it -> println(it); it; }
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    return this.fold(mutableListOf<MutableList<T>>(), { acc, it ->
        it.forEachIndexed{ i, l -> 
            if (acc.size <= i) acc.add(mutableListOf())
            acc[i].add(l)
        }
        acc;
    })
}