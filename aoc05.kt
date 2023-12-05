import java.io.File

fun main() {
    var seeds = mutableListOf<Long>()
    var seeds2 = mutableListOf<Pair<Long, Long>>()
    val seedToSoil = mutableListOf<List<Long>>()
    val soilToFertilizer = mutableListOf<List<Long>>()
    val fertilizerToWater = mutableListOf<List<Long>>()
    val waterToLight = mutableListOf<List<Long>>()
    val lightToTemperature = mutableListOf<List<Long>>()
    val temperatureToHumidity = mutableListOf<List<Long>>()
    val humidityToLocation = mutableListOf<List<Long>>()
    var index = -1
    val lists = listOf(seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature,
                temperatureToHumidity, humidityToLocation)
    File("src/main/kotlin/aoc05.txt").forEachLine {
        if (seeds.isEmpty()) {
            seeds = it.removePrefix("seeds: ").split(" ").map { it.toLong() }.toMutableList()
            seeds2 = it.removePrefix("seeds: ").split(" ").chunked(2)
                     .map { it[0].toLong() to it[1].toLong() }.toMutableList()
        }

        else if (it.isEmpty())
            index++
        else if (it[0].isDigit())
            lists[index].add(it.split(" ").map { it.toLong() })
    }

    lists.forEach {
        seeds.myMap(it)
    }
    println("First star: ${seeds.min()}")

    lists.forEach {
        seeds2.myMap2(it)
    }
    println("Second star: ${seeds2.minOf { it.first }}")
}

fun MutableList<Long>.myMap(data: MutableList<List<Long>>) {
    this.forEachIndexed { i, seed ->
        val interval = data.find { (dest, src, range) ->
            seed >= src && seed < src + range}
        this[i] = seed - (interval?.get(1) ?: 0) + (interval?.get(0) ?: 0)
    }
}

fun MutableList<Pair<Long, Long>>.myMap2(data: MutableList<List<Long>>) {
    val toAdd = mutableListOf<Pair<Long, Long>>()
    this.forEachIndexed { i, (seed, sRange) ->
        val interval = data.find { (dest, src, range) ->
            seed >= src && seed < src + range
        }
        if (!interval.isNullOrEmpty())
            if (seed + sRange <= interval[1] + interval[2])
                this[i] = Pair(seed - interval[1] + interval[0], sRange)
            else {
                val rest = Pair(interval[1] + interval[2], sRange - (interval[1] + interval[2] - seed))
                this[i] = Pair(seed - interval[1] + interval[0], interval[1] + interval[2] - seed)
                toAdd.add(rest)
            }
    }
    if (toAdd.isNotEmpty()) {
        toAdd.myMap2(data)
        this.addAll(toAdd)
    }
}