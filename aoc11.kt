import java.io.File
import kotlin.math.abs

fun main() {
    val N = 999_999     // change to 1 for first star result
    var sum = 0L
    val xx = mutableListOf<Int>()
    val yy = mutableListOf<Int>()
    var extI = 0
    var extJ = 0
    var lines = File("src/main/kotlin/aoc11.txt").readLines()
    lines.forEachIndexed { i, line ->
        if (line.any { it == '#' }) line.forEachIndexed { _, c -> if (c == '#') yy.add(i + extJ) }
        else extJ += N
    }
    lines = lines.transpose()
    lines.forEachIndexed { i, line ->
        if (line.any { it == '#' }) line.forEachIndexed { _, c -> if (c == '#') xx.add(i + extI) }
        else extI += N
    }
    val coords = xx zip yy
    coords.forEachIndexed { i, (x, y) ->
        coords.drop(i + 1).forEach { (x2, y2) ->
            sum += abs(x2 - x) + abs(y2 - y)
        }
    }
    println(sum)
}

fun List<String>.transpose(): List<String> =
    Array(this[0].length) { i -> String(CharArray(this.size) {j -> this[j][i] }) }.toList()