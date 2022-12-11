fun <T> List<T>.peekPrint(): List<T> {
    return this.map{ it.also(::println) }
}

fun String.findNumbers(): List<Int> {
    return Regex("\\d+").findAll(this).map{ f -> f.value.toInt() }.toList()
}

fun List<Int>.product(): Int {
    return this.fold(1){acc: Int, num: Int -> acc * num}
}

fun List<Long>.product(): Long {
    return this.fold(1L){acc: Long, num: Long -> acc * num}
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

fun Pair<Int,Int>.adjPoints(includeDiagnals: Boolean = false): List<Pair<Int,Int>> {
    val orth = listOf(
        Pair(this.first-1,this.second),
        Pair(this.first+1,this.second),
        Pair(this.first,this.second-1),
        Pair(this.first,this.second+1)
    )
    val diag = listOf(
        Pair(this.first-1,this.second-1),
        Pair(this.first+1,this.second+1),
        Pair(this.first+1,this.second-1),
        Pair(this.first-1,this.second+1)
    )
    return if (includeDiagnals) orth + diag else orth
}