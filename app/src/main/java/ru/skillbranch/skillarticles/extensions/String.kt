package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(
    substr: String,
    ignoreCase: Boolean = true
): List<Int> {
    if (substr.isEmpty()) return emptyList()
    val list = mutableListOf<Int>()
    var startChar = 0
    while (true) {
        startChar = this?.indexOf(substr, startChar, ignoreCase)!!
        if (startChar == -1) break
        list.add(startChar)
        startChar++
    }
    return list
}