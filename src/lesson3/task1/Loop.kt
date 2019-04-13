@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun tailDigitNumber(n: Int, res: Int): Int = if (n < 10) res else tailDigitNumber(n / 10, res + 1)

fun digitNumber(n: Int): Int = tailDigitNumber(abs(n), 1)

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun tailFib(n: Int, curr: Int, prev: Int): Int = if (n == 0) curr else tailFib(n - 1, curr + prev, curr)

fun fib(n: Int): Int = tailFib(n, 0, 1)

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var a = m
    var b = n
    while (a != 0 && b != 0) {
        if (a > b)
            a %= b
        else
            b %= a
    }
    return m * n / (a + b)
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (i in 2..n / 2)
        if (n % i == 0)
            return i
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    for (i in n / 2 downTo 2)
        if (n % i == 0)
            return i
    return 1
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun getDivs(x: Int): MutableList<Int> {
    val divX = mutableListOf<Int>()
    for (i in 2..x / 2)
        if (x % i == 0)
            divX.add(i)
    if (x != 1)
        divX.add(x)
    return divX
}

fun isCoPrime(m: Int, n: Int): Boolean {
    val variables = listOf(m, n).sorted()
    val divs = getDivs(variables[0])

    for (i in divs)
        if (variables[1] % i == 0)
            return false
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val floorM = floor(sqrt(m.toDouble()))
    val ceilM = ceil(sqrt(m.toDouble()))
    val floorN = floor(sqrt(n.toDouble()))
    val ceilN = ceil(sqrt(n.toDouble()))

    return when {
        floorM == ceilM || floorN == ceilN -> true
        else -> abs(floorM - ceilN) > 1
    }
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var counter = 0
    var modifiedX = x

    while (modifiedX != 1) {
        counter++
        modifiedX = if (modifiedX % 2 == 0) modifiedX / 2 else 3 * modifiedX + 1
    }
    return counter
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun normParam(s: Double): Int = if (s < 0) 1 else 2

fun normalize(x: Double, s: Double): Double = when {
    x >= s && x <= s + PI -> x
    x > s + PI && x < s + 2 * PI -> normParam(s) * PI - x
    x >= s + 2 * PI -> normalize(x - 2 * PI, s)
    else -> normalize(x + 2 * PI, s)
}

fun elem(x: Double, n: Int, m: Int, negOne: Double = -1.0) = negOne.pow(n) * x.pow(m) / factorial(m)

fun magicSin(x: Double, eps: Double, sum: Double, n: Int, elem: Double = elem(x, n, 2 * n + 1)): Double =
        if (abs(elem) >= eps) magicSin(x, eps, sum + elem, n + 1) else sum

fun sin(x: Double, eps: Double): Double = magicSin(normalize(x, -PI / 2), eps, 0.0, 0)

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun magicCos(x: Double, eps: Double, sum: Double, n: Int, elem: Double = elem(x, n, 2 * n)): Double =
        if (abs(elem) >= eps) magicCos(x, eps, sum + elem, n + 1) else sum

fun cos(x: Double, eps: Double): Double = magicCos(normalize(x, 0.0), eps, 0.0, 0)

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun magicRevert(n: Int, res: Int): Int =
        if (n == 0) res else magicRevert(n / 10, 10 * res + n % 10)

fun revert(n: Int): Int = (if (n > 0) 1 else -1) * magicRevert(abs(n), 0)

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun find(n: Int, curr: Int): Boolean =
        if (n == 0) false else if (n % 10 == curr) find(n / 10, curr) else true

fun hasDifferentDigits(n: Int): Boolean = find(abs(n), n % 10)

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun getListOfDigits(n: Int): List<Int> {
    val res = mutableListOf<Int>()
    var num = n
    while (num > 0) {
        res.add(num % 10)
        num /= 10
    }
    return res.asReversed()
}

fun getDigit(n: Int, flag: Int): Int {
    var counter = n
    var currNum = 1
    var currNumPrepared = if (flag == 0) sqr(currNum) else fib(currNum)
    var currListOfDigits = listOf(currNumPrepared)

    while (currListOfDigits.size < counter) {
        counter -= currListOfDigits.size
        currNum += 1
        currNumPrepared = if (flag == 0) sqr(currNum) else fib(currNum)
        currListOfDigits = getListOfDigits(currNumPrepared)
    }
    return currListOfDigits[counter - 1]
}

fun squareSequenceDigit(n: Int): Int = getDigit(n, 0)

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int = getDigit(n, 1)
