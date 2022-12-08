fun <T> List<T>.peekPrint(): List<T> {
    return this.map{ it.also(::println) }
}

fun String.findNumbers(): List<Int> {
    return Regex("\\d+").findAll(this).map{ f -> f.value.toInt() }.toList()
}

fun <T> List<List<T>>.transposed(): List<List<T>> {
    return this.fold(mutableListOf<MutableList<T>>(), { acc, it ->
        it.forEachIndexed{ i, l -> 
            if (acc.size <= i) acc.add(mutableListOf())
            acc[i].add(l)
        }
        acc;
    })
}