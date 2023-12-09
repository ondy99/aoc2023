import java.io.File

fun main() {
    var sum1 = 0
    var sum2 = 0
    File("src/main/kotlin/aoc09.txt").forEachLine {
        sum1 += extrapolate(it.split(" ").map { it.toInt() }).last
        sum2 += extrapolate(it.split(" ").map { it.toInt() }.reversed()).last
    }
    println("First star: $sum1\nSecond star: $sum2")
}

fun extrapolate(line: List<Int>) : List<Int> =
    if (line.all { it == 0 }) line else line + (line.last + extrapolate(line.zipWithNext { a, b -> b - a }).last)
