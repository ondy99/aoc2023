import java.io.File

fun main() {
    val pattern = mutableListOf<String>()
    var sum1 = 0
    var sum2 = 0
    File("src/main/kotlin/aoc13.txt").forEachLine {
        if (it.isBlank()) {
            sum1 += findReflection1(pattern)
            sum2 += findReflection2(pattern)
            sum1 += findReflection1(pattern.transpose()) * 100
            sum2 += findReflection2(pattern.transpose()) * 100
            pattern.clear()
        }
        else
            pattern.add(it)
    }
    sum1 += findReflection1(pattern)
    sum2 += findReflection2(pattern)
    sum1 += findReflection1(pattern.transpose()) * 100
    sum2 += findReflection2(pattern.transpose()) * 100
    println("First star: $sum1\nSecond star: $sum2")
}

fun findReflection1(pattern: List<String>): Int {
    pattern[0].forEachIndexed { i, c ->
        val l = minOf(i + 1, pattern[0].length - i - 1)
        if (pattern[0].slice(i - l + 1..i) == pattern[0].slice(i + 1..i + l).reversed()) {
            var reflectsAll = true
                pattern.drop(1).forEach { line ->
                    if (line.slice(i - l + 1..i) != line.slice(i + 1..i + l).reversed())
                        reflectsAll = false
                }
            if (reflectsAll)
                return (i + 1) % pattern[0].length
        }
    }
    return 0
}

fun findReflection2(pattern: List<String>): Int {
    var changed = false
    pattern[0].forEachIndexed { i, c ->
        val l = minOf(i + 1, pattern[0].length - i - 1)
        val p1 = pattern[0].slice(i - l + 1..i)
        val p2 = pattern[0].slice(i + 1..i + l).reversed()
        var diff1 = differsByOne(p1, p2)
        changed = diff1
        if (p1 == p2 || diff1) {
            var reflectsAll = true
                pattern.drop(1).forEach { line ->
                    val pp1 = line.slice(i - l + 1..i)
                    val pp2 = line.slice(i + 1..i + l).reversed()
                    if (!changed) {
                        diff1 = differsByOne(pp1, pp2)
                        changed = diff1
                    }
                    else diff1 = false
                    if (pp1 != pp2 && !diff1)
                        reflectsAll = false
                }
            if (reflectsAll && changed)
                return (i + 1) % pattern[0].length
        }
    }
    return 0
}

fun differsByOne(p1: String, p2: String): Boolean {
    if (p1 == p2) return false
    var canChange = true
    p1.forEachIndexed { i, c ->
        if (c != p2[i])
            if (canChange) canChange = false
            else return false
    }
    return true
}