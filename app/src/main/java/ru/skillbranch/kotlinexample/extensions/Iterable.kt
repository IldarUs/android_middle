package ru.skillbranch.kotlinexample.extensions

fun <T> Iterable<T>.dropLastUntil(predicate: (T) -> Boolean): List<T> {
    for (item in this) {
        if (predicate(item)) {
            val index = this.indexOf(item)
            return this.chunked(index).first()
        }
    }

    return emptyList()
}