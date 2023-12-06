import java.io.File

fun main() {
    var time = listOf<Int>()
    var dist = listOf<Int>()
    var t1 = 0L
    var d1 = 0L
    val counters = mutableListOf<Int>()

    File("src/main/kotlin/aoc06.txt").forEachLine {
        if (time.isEmpty()) {
            time = it.removePrefix("Time:").split(" ")
                .filter { it.isNotEmpty() }.map { it.toInt() }
            t1 = it.removePrefix("Time:").filter { it != ' ' }.toLong()
        }
        else {
            dist = it.removePrefix("Distance:").split(" ")
                .filter { it.isNotEmpty() }.map { it.toInt() }
            d1 = it.removePrefix("Distance:").filter { it != ' ' }.toLong()
        }
    }

    time.forEachIndexed { i, t ->
        counters.add(0)
        for (hold in 0..t) {
            if (hold * (t - hold) > dist[i])
                counters[i]++
        }
    }

    var c = 0
    for (hold in 0..t1) {
        if (hold * (t1 - hold) > d1)
            c++
    }

    println("First star: ${counters.reduce(Int::times)}")
    println("Second star: $c")
}