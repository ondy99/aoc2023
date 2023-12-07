import java.io.File

fun main() {
    val fives = mutableListOf<Pair<String, Int>>()
    val fours = mutableListOf<Pair<String, Int>>()
    val fulls = mutableListOf<Pair<String, Int>>()
    val threes = mutableListOf<Pair<String, Int>>()
    val twoPairs = mutableListOf<Pair<String, Int>>()
    val pairs = mutableListOf<Pair<String, Int>>()
    val highs = mutableListOf<Pair<String, Int>>()

    File("src/main/kotlin/aoc07.txt").forEachLine {
        val (a, b) = it.split(" ")
        //val c = a   //first star
        val c = findBest(a) //second star
        when {
            isFiveOfAKind(c) -> fives.add(Pair(a, b.toInt()))
            isFourOfAKind(c) -> fours.add(Pair(a, b.toInt()))
            isFullHouse(c) -> fulls.add(Pair(a, b.toInt()))
            isThreeOfAKind(c) -> threes.add(Pair(a, b.toInt()))
            isTwoPair(c) -> twoPairs.add(Pair(a, b.toInt()))
            isOnePair(c) -> pairs.add(Pair(a, b.toInt()))
            else -> highs.add(Pair(a, b.toInt()))
        }
    }
    val l = listOf(highs, pairs, twoPairs, threes, fulls, fours, fives).map {
        it.sortedBy { plsHelp(it.first) }
    }.flatten()
    var sum1 = 0
    l.forEachIndexed { i, (_, bid) ->
        sum1 += (i + 1) * bid
    }
    println(sum1)
}

fun isFiveOfAKind(card: String) : Boolean {
    return card.count { it == card[0] } == 5
}

fun isFourOfAKind(card: String) : Boolean {
    return card.count { it == card[0] } == 4 || card.count { it == card[1] } == 4
}

fun isFullHouse(card: String) : Boolean {
    val sortedCard = card.toCharArray().sorted()
    val count1 = sortedCard.count { it == sortedCard.first }
    val count2 = sortedCard.count { it == sortedCard.last }
    return (count1 == 2 && count2 == 3) || (count1 == 3 && count2 == 2)
}

fun isThreeOfAKind(card: String) : Boolean {
    val sortedCard = card.toCharArray().sorted()
    val count1 = sortedCard.count { it == sortedCard.first }
    val count2 = sortedCard.count { it == sortedCard.last }
    val count3 = sortedCard.count { it == sortedCard[2] }
    return count1 == 3 || count2 == 3 || count3 == 3
}

fun isTwoPair(card: String) : Boolean {
    val sortedCard = card.toCharArray().sorted()
    val counts = listOf(sortedCard.count { it == sortedCard.first },
                sortedCard.count { it == sortedCard.last },
                sortedCard.count { it == sortedCard[2] })
    return counts.filter { it == 2 }.size == 2
}

fun isOnePair(card: String) : Boolean {
    val sortedCard = card.toCharArray().sorted()
    val counts = listOf(sortedCard.count { it == sortedCard.first },
        sortedCard.count { it == sortedCard.last },
        sortedCard.count { it == sortedCard[2] })
    return counts.filter { it == 2 }.size == 1
}

fun plsHelp(card: String) : String {
    var copy = card
    copy = copy.replace("T", "B")
    //copy = copy.replace("J", "C")   //first star
    copy = copy.replace("J", "1")   //second star
    copy = copy.replace("Q", "D")
    copy = copy.replace("K", "E")
    copy = copy.replace("A", "F")
    return copy
}

fun findBest(card: String) : String {
    var best = card
    var bestTypes = listOf(isFiveOfAKind(best), isFourOfAKind(best), isFullHouse(best), isThreeOfAKind(best), isTwoPair(best), isOnePair(best), true)

    "23456789TQKA".toList().forEach {
        val candidate = card.replace('J', it)
        val candidateTypes = listOf(isFiveOfAKind(candidate), isFourOfAKind(candidate), isFullHouse(candidate), isThreeOfAKind(candidate), isTwoPair(candidate), isOnePair(candidate), true)

        if (candidateTypes.indexOf(true) < bestTypes.indexOf(true)) {
            best = candidate
            bestTypes = listOf(isFiveOfAKind(best), isFourOfAKind(best), isFullHouse(best), isThreeOfAKind(best), isTwoPair(best), isOnePair(best), true)
        }
    }
    return best
}