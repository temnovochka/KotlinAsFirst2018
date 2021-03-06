@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val monthNames = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val line = str.split(" ")
    try {
        if (line.size != 3)
            return ""
        val (d, m, y) = line
        if (m !in monthNames)
            return ""
        val monthNumber = monthNames.indexOf(m) + 1
        val daysNumber = daysInMonth(monthNumber, y.toInt())
        if (d.toInt() > daysNumber)
            return ""
        return String.format("%02d.%02d.%d", d.toInt(), monthNumber, y.toInt())
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val monthNames = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val line = digital.split(".")
    try {
        if (line.size != 3)
            return ""
        val (d, m, y) = line
        if (m.toInt() > 12)
            return ""
        val monthName = monthNames.elementAt(m.toInt() - 1)
        val daysNumber = daysInMonth(m.toInt(), y.toInt())
        if (d.toInt() > daysNumber)
            return ""
        return String.format("%d %s %d", d.toInt(), monthName, y.toInt())
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val canBe = "0123456789+-() ".toSet()
    val mustBe = "0123456789+".toSet()

    return when {
        phone.any { it !in canBe } -> ""
        else -> phone.filter { it in mustBe }
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val canBe = "0123456789%- ".toSet()

    return when {
        jumps.any { it !in canBe } -> -1
        else -> jumps.split(" ").filter { it !in setOf("%", "-") }.mapNotNull {
            try {
                it.toInt()
            } catch (e: NumberFormatException) {
                null
            }
        }.max() ?: -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val canBe = "0123456789+-% ".toSet()
    if (jumps.any { it !in canBe } || jumps.isEmpty())
        return -1

    var highs = -1
    var prev = 0

    for (part in jumps.split(" ")) {
        if (part.first().isDigit())
            prev = part.toInt()
        else if (part.last() == '+' && prev > highs)
            highs = prev
    }
    return highs
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val canBe = "0123456789+- ".toSet()
    if (expression.any { it !in canBe } || expression.isEmpty() || expression.all { !it.isDigit() })
        throw IllegalArgumentException("")

    val parts = expression.split(" ")
    var res = 0
    var isStart = true
    var operation = '+'

    var isPrevNum = false
    var parsed: Int

    for (part in parts) {
        try {
            if (!isPrevNum && !part.first().isDigit())
                throw IllegalArgumentException("")
            parsed = part.toInt()
            if (isPrevNum)
                throw IllegalArgumentException("")
            else {
                res = when {
                    isStart -> parsed
                    operation == '+' -> res + parsed
                    else -> res - parsed
                }
                isStart = false
                isPrevNum = true
            }
        } catch (e: NumberFormatException) {
            if (isPrevNum && !isStart) {
                operation = part.first()
                isPrevNum = false
            } else
                throw IllegalArgumentException("")
        }
    }
    return res
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parsed = str.toLowerCase().split(" ")

    if (parsed == parsed.toSet())
        return -1

    var prevWord = ""
    var len = 0

    for ((i, word) in parsed.withIndex()) {
        if (word == prevWord) {
            len -= word.length + 1
            break
        }
        if (i == parsed.size - 1)
            return -1
        prevWord = word
        len += word.length + 1
    }

    return len
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description.isEmpty())
        return ""

    val parsed = description.split("; ")

    var best = Pair("", -1.0)

    for (part in parsed) {
        val splittedPart = part.split(" ")
        if (splittedPart.size != 2)
            return ""

        val (name, price) = splittedPart

        try {
            val doublePrice = price.toDouble()
            if (doublePrice < 0.0)
                return ""
            if (doublePrice > best.second) {
                best = Pair(name, doublePrice)
            }
        } catch (e: NumberFormatException) {
            return ""
        }
    }

    return best.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val fromRoman = mapOf(
            'I' to 1,
            'V' to 5,
            'X' to 10,
            'L' to 50,
            'C' to 100,
            'D' to 500,
            'M' to 1000
    )
    val priority = fromRoman.keys.mapIndexed { index, key -> key to index }.toMap()
    val valid = fromRoman.keys.toSet()

    var res = 0
    var prev = ' '

    if (roman.isEmpty())
        return -1

    for (symb in roman) {
        if (symb !in valid) return -1
        val d = priority.getValue(symb) - priority.getOrDefault(prev, priority.size)
        when {
            d > 2 -> return -1
            d > 0 -> res -= fromRoman.getOrDefault(prev, 0)
            else -> res += fromRoman.getOrDefault(prev, 0)
        }
        prev = symb
    }
    res += fromRoman.getOrDefault(prev, 0)
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0 until cells)
        result.add(0)

    val canBe = "><+-[] ".toSet()
    var position = cells / 2
    val illegal = commands.any { it !in canBe }

    if (commands.count { it == ']' } != commands.count { it == '[' } || illegal)
        throw IllegalArgumentException("")

    val opened = mutableListOf<Int>()
    val mapOfOpenedBrackets = mutableMapOf<Int, Int>()
    for ((j, command) in commands.withIndex()) {
        if (command == '[') {
            mapOfOpenedBrackets[j] = -1
            opened.add(j)
        } else if (command == ']') {
            if (opened.isNotEmpty()) {
                mapOfOpenedBrackets[opened.last()] = j
                opened.removeAt(opened.lastIndex)
            } else
                throw IllegalArgumentException("")
        }
    }

    val mapOfClosedBrackets = mutableMapOf<Int, Int>()
    for ((open, close) in mapOfOpenedBrackets)
        mapOfClosedBrackets[close] = open

    var i = 0
    for (counter in (1..limit)) {
        val command = commands.getOrNull(i) ?: break

        when (command) {
            '+' -> result[position] += 1
            '-' -> result[position] -= 1
            '>' -> position += 1
            '<' -> position -= 1
            else -> {
                if (command == '[' && result[position] == 0) {
                    i = mapOfOpenedBrackets[i]!!
                } else if (command == ']' && result[position] != 0) {
                    i = mapOfClosedBrackets[i]!!
                }
            }
        }
        i += 1
        if (position < 0 || position >= cells)
            throw IllegalStateException("")
    }
    return result
}
