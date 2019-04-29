@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val numbers = mapA.toMutableMap()

    for ((key, value) in mapB)
        numbers[key] = when (numbers[key]) {
            value, null -> value
            else -> numbers[key] + ", " + value
        }

    return numbers
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> =
        grades.entries.groupBy({ (_, v) -> v }, { (k, _) -> k }).mapValues { (_, v) -> v.sortedDescending() }

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((k, v) in a)
        if (b[k] != v)
            return false
    return true
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> =
        stockPrices.groupBy({ (k, _) -> k }, { (_, v) -> v }).mapValues { (_, v) -> v.average() }

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minPrice = Double.MAX_VALUE
    var foundName = ""
    var flag = false

    for ((name, typePrice) in stuff) {
        val (type, price) = typePrice
        if (type == kind && price <= minPrice) {
            flag = true
            minPrice = price
            foundName = name
        }
    }

    return when (flag) {
        false -> null
        else -> foundName
    }
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */

fun getFriends(person: String, currentFriends: MutableSet<String>, friends: Map<String, Set<String>>): Set<String> {
    var setOfMaybeFriends = emptyList<String>()

    do {
        currentFriends += setOfMaybeFriends
        setOfMaybeFriends = currentFriends.flatMap { friends.getOrDefault(it, emptySet()) }
    } while (!currentFriends.containsAll(setOfMaybeFriends))

    return currentFriends
}

fun propagateHandshakes(friends: Map<String, Set<String>>) =
        (friends.keys + friends.values.flatten()).map { person ->
            person to (friends[person]?.let { getFriends(person, it.toMutableSet(), friends) - person } ?: emptySet())
        }.toMap()

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((k, v) in b)
        a.remove(k, v)
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().intersect(b.toSet()).toList()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
        (word.map { it.toLowerCase() }.toSet() - chars.map { it.toLowerCase() }).isEmpty()

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> =
        list.groupBy { it }.mapValues { (_, v) -> v.size }.filterValues { it > 1 }

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun countLetters(word: String) = word.toList().groupBy { it }.mapValues { (_, v) -> v.size }

fun hasAnagrams(words: List<String>): Boolean {
    for ((i, currentWord) in words.withIndex()) {
        val countedCurrentWord = countLetters(currentWord)
        val sameLenWords = words.filterIndexed { index, _ -> index > i }.filter { it.length == currentWord.length }
        for (word in sameLenWords)
            if (countedCurrentWord == countLetters(word))
                return true
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val numbers = list.filter { it <= number / 2 }.toSet()
    val allNumbers = list.toMutableSet()
    val indexesOfNumbers =
            list.withIndex().map { (i, num) -> num to i }.groupBy({ (k, _) -> k }, { (_, v) -> v }).toMap()

    if (number / 2 == number - number / 2 && number / 2 in allNumbers) {
        var l = indexesOfNumbers.getValue(number / 2)
        if (l.size > 1) {
            l = l.sorted()
            return Pair(l[0], l[1])
        }
    }

    for (elem in numbers) {
        allNumbers.remove(elem)
        if (number - elem in allNumbers) {
            val first = indexesOfNumbers.getValue(elem).first()
            val second = indexesOfNumbers.getValue(number - elem).first()
            return if (first < second) Pair(first, second) else Pair(second, first)
        }
    }
    return Pair(-1, -1)
}

fun main(args: Array<String>) {
    val list = listOf(
            0,
            0,
            40699,
            1,
            25761,
            0,
            0,
            26195,
            30021,
            1,
            21259,
            43474,
            30568,
            1195,
            40700,
            40699,
            40700,
            36823,
            5842,
            18803,
            40700,
            16018,
            40700,
            8700,
            0,
            0,
            1,
            40699,
            39573,
            0,
            0,
            40699,
            0,
            43129,
            0,
            40699,
            40699,
            33520,
            32707,
            32226,
            40700,
            17390,
            0,
            0,
            40700,
            18990,
            40699,
            47567,
            1,
            0,
            3431,
            0,
            34237,
            40700,
            5899,
            40700,
            1,
            40700,
            0,
            1,
            1,
            1,
            936,
            49531,
            8673,
            40700,
            0,
            30353,
            0,
            1,
            40700,
            32401,
            0,
            0,
            48639,
            3449,
            1,
            0,
            1,
            40700,
            20458,
            1,
            1,
            40699,
            36979,
            40700,
            41190,
            0,
            1,
            40699,
            1,
            34505,
            40700,
            40699,
            21356,
            14329,
            24464,
            38023,
            48836,
            14545,
            1,
            12320,
            0,
            1,
            35957,
            40699,
            12993,
            35082,
            12137,
            40700,
            40700,
            32687,
            40699,
            1,
            4252,
            40699,
            37094,
            40699,
            0,
            40699,
            40700,
            0,
            1,
            1,
            40255,
            25648,
            40699,
            11775,
            36787,
            0,
            0,
            1,
            43303,
            1,
            40700,
            32649,
            18872,
            24028,
            40700,
            25145,
            0,
            40700,
            1,
            0,
            12474,
            40700,
            40700,
            16827,
            25689,
            1,
            6095,
            33294,
            40699,
            40699,
            26732,
            4433,
            45739,
            0,
            9657,
            1,
            40700,
            1,
            38608,
            23775,
            0,
            40699,
            0,
            0,
            48230,
            40699,
            0,
            41740,
            467,
            122,
            14584,
            1,
            0,
            47889,
            24152,
            40699,
            1,
            1,
            40699,
            38767,
            40699,
            40700,
            40700,
            40700,
            48833,
            40700,
            8114,
            40699,
            45253,
            0,
            40699,
            40700,
            0,
            40700,
            43470,
            4987,
            7374,
            1,
            1038,
            40700,
            23296,
            40699,
            35923,
            45310,
            40834,
            1,
            40700,
            39465,
            0,
            40700,
            40700,
            40699,
            1,
            24938,
            40699,
            18887,
            19620,
            0,
            45242,
            1,
            40700,
            40699,
            0,
            26524,
            1,
            0,
            0,
            17786,
            1,
            1
    )
    println(findSumOfTwo(list, 69708))
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()
