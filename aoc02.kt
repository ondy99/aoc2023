import java.io.File

fun main() {
    var sum1 = 0
    var sum2 = 0
    val lines = mutableListOf<String>()
    val rgb = mapOf("red" to 12, "green" to 13, "blue" to 14)

    // process file
    File("src/main/kotlin/aoc02.txt").forEachLine { lines.add(it) }
    lines.map {
        it.split(":")[1].split(";").map {
            it.split(",").map { it.trim() }.map {
                it.split(" ")
            }.associateBy({it.last}, {it.first.toInt()})
        }

    }.onEachIndexed game@ { i, game ->
        // second star
        val comb = game[0].toMutableMap()
        game.forEach {round ->
            round.forEach { (key, value) ->
                comb.merge(key, value) {oldVal, newVal -> maxOf(oldVal, newVal)}
            }
        }
        sum2 += comb.values.reduce { acc, next -> acc * next }

        // first star
        game.forEach { round ->
            round.forEach  {
                if (it.value > rgb[it.key]!!)
                    return@game
            }
        }
        sum1 += i + 1
    }

    println("first star: $sum1\nsecond star: $sum2")
}
