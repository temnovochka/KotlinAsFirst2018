@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.pow
import kotlin.math.*
import kotlin.text.StringBuilder

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = if (v.isEmpty()) 0.0 else sqrt((v.map { it * it }).sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isNotEmpty()) list.sum() / list.size else 0.0

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty())
        return list
    val average = list.sum() / list.size
    for (i in 0 until list.size)
        list[i] -= average
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double = a.zip(b) { x, y -> x * y }.sum()

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double =
        p.withIndex().fold(0.0) { acc, (i, elem) -> acc + elem * x.pow(i) }

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty())
        return list
    for (i in 1 until list.size)
        list[i] += list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var mutN = n
    val res = mutableListOf<Int>()
    for (i in 2..mutN / 2) {
        while (mutN % i == 0) {
            mutN /= i
            res.add(i)
        }
        if (mutN == 1)
            break
    }
    if (mutN != 1)
        res.add(n)
    return res.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString("*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val res = mutableListOf<Int>()
    var d = n
    while (d >= base) {
        res.add(d % base)
        d /= base
    }
    res.add(d)
    return res.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val nConv = convert(n, base)
    val res = StringBuilder()
    val alphabet = ('0'..'9') + ('a'..'z')

    for (elem in nConv)
        res.append("${alphabet[elem]}")
    return res.toString()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    val diReverse = digits.reversed()
    var res = 0
    for ((i, elem) in diReverse.withIndex()) {
        res += pow(base.toDouble(), i.toDouble()).toInt() * elem
    }
    return res
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val digits = mutableListOf<Int>()
    val alphabet = "abcdefghijklmnopqrstuvwxyz"

    for (elem in str)
        digits.add(if (elem !in alphabet) elem - '0' else alphabet.indexOf(elem) + 10)
    return decimal(digits, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val num = mapOf(
            1 to "I",
            5 to "V",
            10 to "X",
            50 to "L",
            100 to "C",
            500 to "D",
            1000 to "M"
    )

    var mutN = n
    var d: Int
    var i = 1000
    val res = StringBuilder()

    if (n > 3999)
        return ""

    while (mutN != 0) {
        d = mutN / i
        mutN -= d * i
        res.append(when {
            d == 0 -> ""
            d < 4 -> num[1 * i]?.repeat(d)
            d == 4 -> num[1 * i] + num[5 * i]
            d < 9 -> num[5 * i] + num[1 * i]?.repeat(d - 5)
            d == 9 -> num[1 * i] + num[10 * i]
            else -> ""
        })
        i /= 10
    }

    return "$res"
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

private val numSmall = mapOf(
        1 to "один",
        2 to "два",
        3 to "три",
        4 to "четыре",
        5 to "пять",
        6 to "шесть",
        7 to "семь",
        8 to "восемь",
        9 to "девять"
)

private val numMiddle = mapOf(
        10 to "десять",
        11 to "одиннадцать",
        12 to "двенадцать",
        13 to "тринадцать",
        14 to "четырнадцать",
        15 to "пятнадцать",
        16 to "шестнадцать",
        17 to "семнадцать",
        18 to "восемнадцать",
        19 to "девятнадцать"
)

fun beforeHundred(n: Int): String {
    if (n == 0)
        return ""
    if (n < 10)
        return numSmall[n] ?: ""
    if (n < 20)
        return numMiddle[n] ?: ""

    val div = n / 10
    val mod = n % 10
    val small = numSmall[mod] ?: ""
    return when {
        div == 4 -> "сорок $small"
        div < 4 -> numSmall[div] + "дцать " + small
        div < 9 -> numSmall[div] + "десят " + small
        else -> "девяносто $small"
    }
}

fun beforeThousand(n: Int): String {
    return when {
        n == 0 -> ""
        n == 1 -> "сто "
        n == 2 -> "двести "
        n < 5 -> numSmall[n] + "ста "
        else -> numSmall[n] + "сот "
    }
}

fun smallThousands(n: Int): String {
    if (n == 0)
        return "тысяч "
    if (n < 10)
        return when {
            n == 1 -> "одна тысяча "
            n == 2 -> "две тысячи "
            n < 5 -> numSmall[n] + " тысячи "
            else -> numSmall[n] + " тысяч "
        }
    return numMiddle[n] + " тысяч "
}

fun middleThousands(n: Int): String {
    if (n < 20)
        return smallThousands(n)

    val div = n / 10
    val mod = n % 10
    val smallPart = smallThousands(mod)
    return when {
        div == 4 -> "сорок $smallPart"
        div < 4 -> numSmall[div] + "дцать " + smallPart
        div < 9 -> numSmall[div] + "десят " + smallPart
        else -> "девяносто $smallPart"
    }
}

fun thousands(n: Int): String {
    if (n == 0)
        return ""

    if (n < 100)
        return middleThousands(n)

    val div = n / 100
    val mod = n % 100
    val smallPart = middleThousands(mod)

    return when {
        div == 0 -> smallPart
        div == 1 -> "сто $smallPart"
        div == 2 -> "двести $smallPart"
        div < 5 -> numSmall[div] + "ста " + smallPart
        else -> numSmall[div] + "сот " + smallPart
    }
}

fun russian(n: Int): String {
    val res = thousands(n / 1000) +
            beforeThousand(n / 100 - n / 1000 * 10) +
            beforeHundred(n % 100)
    return res.trim()
}