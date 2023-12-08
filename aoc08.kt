import java.io.File

fun main() {
    var lines = File("src/main/kotlin/aoc08.txt").readLines()
    val dirs = lines[0]
    lines = lines.drop(2)
    val nodes = lines.map {
        it.filter{it in 'A'..'Z' || it in '0'..'9' || it == ' '}.split("\\s+".toRegex()).let{(n,l,r) -> n to Pair(l, r)}
    }.associateBy({it.first}, {it.second})
    println("First star: ${nodes.travel(dirs, "AAA", "ZZZ")}\nSecond star: ${nodes.ghost(dirs)}")
}

fun Map<String, Pair<String, String>>.travel(dirs: String, start: String, end: String) : Int {
    var pos = start
    var count = 0
    while (!(pos).endsWith(end))
        pos = if (dirs[count++ % dirs.length] == 'L') this[pos]!!.first else this[pos]!!.second
    return count
}

fun Map<String, Pair<String, String>>.ghost(dirs: String) : Long =
    this.keys.filter{it.endsWith("A")}.map{this.travel(dirs, it, "Z").toLong()}.reduce{acc, v -> lcm(acc, v)}

fun gcd(a: Long, b: Long) : Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long) : Long = a * b / gcd(a, b)