import java.io.File

fun main() {
    var lines = mutableListOf<String>()
    var (x, y) = Pair(0, 0)
    val cycle = mutableListOf<Pair<Int, Int>>()
    File("src/main/kotlin/aoc10.txt").forEachLine {
        lines.add(it)
        if (it.contains("S")) {
            x = lines.size - 1
            y = it.indexOf('S')
        }
    }
    println("First star: ${lines.loop(Pair(x, y), cycle)}")
    lines = cycle.replaceS(lines)
    println("Second star: ${lines.countEnclosed(cycle.toSet())}")
}

fun List<String>.loop(pos: Pair<Int, Int>, cycle: MutableList<Pair<Int, Int>>): Int {
    var curr = pos
    var prev = Pair(-1, -1)
    var count = 0
    do {
        count++
        var temp = curr
        cycle.add(curr)
        curr = this.getNext(curr, prev)
        prev = temp
    } while (this[curr.first][curr.second] != 'S' || prev == Pair(-1, -1))
    return count / 2
}

fun List<String>.getNext(pos: Pair<Int, Int>, prev: Pair<Int, Int>): Pair<Int, Int> {
    val (x, y) = pos
    listOf(Pair(x - 1, y), Pair(x, y - 1), Pair(x + 1, y), Pair(x, y + 1)).forEach { (xx, yy) ->
        if (this.isValid(xx ,yy))
            if (Pair(xx, yy) != prev)
                if (this@getNext.isConnected(Pair(x, y), Pair(xx, yy)))
                    return Pair(xx, yy)
    }
    return Pair(-1, -1)
}

fun List<String>.isValid(x: Int, y: Int): Boolean {
    return x in indices && y in this[0].indices
}

fun List<String>.isConnected(curr: Pair<Int, Int>, next: Pair<Int, Int>): Boolean {
    val (x, y) = curr
    val (x2, y2) = next
    var r  = false
    if (x2 > x)
        r = this[x][y] in "S|F7" && this[x2][y2] in "|JLS"
    else if (x2 < x)
        r = this[x][y] in "S|JL" && this[x2][y2] in "|F7S"
    else if (y2 > y)
        r = this[x][y] in "S-FL" && this[x2][y2] in "-J7S"
    else if (y2 < y) {
        r = this[x][y] in "S-J7" && this[x2][y2] in "-FLS"
    }
    return r
}

fun List<String>.countEnclosed(cycle: Set<Pair<Int, Int>>): Int {
    var count = 0
    this.forEachIndexed { x, line ->
        line.forEachIndexed { y, ch ->
            if (Pair(x, y) !in cycle) {
                var walls = 0
                var prev = '0'
                for (i in y + 1..<this[x].length) {
                    if (Pair(x, i) in cycle && this[x][i] in "|FL7J") {
                        walls++
                        if ((prev == 'F' && this[x][i] == 'J') || (prev == 'L' && this[x][i] == '7'))
                            walls--
                        prev = this[x][i]
                    }
                }
                if (walls % 2 == 1)
                    count++
            }
        }
    }
    return count
}

fun List<Pair<Int, Int>>.replaceS(lines: List<String>): MutableList<String> {
    val lCopy = lines.toMutableList()
    val (x, y) = this.first
    val before = lines[this.last.first][this.last.second]
    val after = lines[this[1].first][this[1].second]
    if (before == after)
        lCopy[x] = lCopy[x].replace('S', after)
    else if (before in "-LF" && after in "|LJ" || after in "-LF" && before in "|LJ")
        lCopy[x] = lCopy[x].replace('S', '7')
    else if (before in "-FJ" && after in "|F7" || after in "-FJ" && before in "|F7")
        lCopy[x] = lCopy[x].replace('S', 'J')
    else if (before in "-7J" && after in "|LJ" || after in "-7J" && before in "|LJ")
        lCopy[x] = lCopy[x].replace('S', 'F')
    else if (before in "-7J" && after in "|F7" || after in "-7J" && before in "|F7")
        lCopy[x] = lCopy[x].replace('S', 'L')
    return lCopy
}