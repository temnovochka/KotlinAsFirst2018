@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.lang.Integer.max
import java.lang.StringBuilder

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                }
                else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val fromFile = File(inputName).readLines().joinToString(separator = " ").toLowerCase()

    for (str in substrings.toSet()) {
        res[str] = res.getOrDefault(str, 0)
        var fromIndex = fromFile.indexOf(str.toLowerCase(), 0)
        while (fromIndex > -1) {
            fromIndex = fromFile.indexOf(str.toLowerCase(), fromIndex + 1)
            res[str] = res.getOrDefault(str, 0) + 1
        }
    }
    return res
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val corrections = mapOf(
            'я' to 'а',
            'ы' to 'и',
            'ю' to 'у'
    )

    val check = "жчшщ".toSet()
    val checkNext = "яыю".toSet()

    File(outputName).bufferedWriter().use {
        var prevSymbol = ' '
        for (symbol in File(inputName).readText()) {
            var currentSymbol = symbol.toLowerCase()
            if (prevSymbol in check && currentSymbol in checkNext)
                currentSymbol = corrections.getOrDefault(currentSymbol, ' ')
            currentSymbol = if (symbol.isUpperCase()) currentSymbol.toUpperCase() else currentSymbol
            it.write(currentSymbol.toString())
            prevSymbol = currentSymbol.toLowerCase()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        val lines = File(inputName).readLines().map(String::trim)
        val maxLen = lines.map { line -> line.length }.max() ?: 0

        for (line in lines)
            it.write(" ".repeat((maxLen - line.length) / 2) + line + '\n')
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        val lines = File(inputName).readLines()
        val mWords = mutableListOf<List<String>>()

        var maxLen = 0
        for (line in lines) {
            val words = line.split(" ").filter { word -> word.isNotEmpty() }
            mWords.add(words)
            val numOfWords = words.size
            val wordsLen = words.sumBy { word -> word.length }

            val len = wordsLen + numOfWords - 1
            if (len > maxLen)
                maxLen = len
        }

        for (words in mWords) {
            val numOfWords = words.size

            if (numOfWords == 0) {
                it.write("\n")
                continue
            }

            if (numOfWords == 1) {
                it.write(words.first())
                it.write("\n")
                continue
            }

            val wordsLen = words.sumBy { word -> word.length }

            val numOfSpaces = (maxLen - wordsLen)
            var howManyLonger = numOfSpaces % (numOfWords - 1)
            val lenOfSpaces = numOfSpaces / (numOfWords - 1)

            val resLine = StringBuilder()

            for ((index, word) in words.withIndex()) {
                resLine.append(word)
                if (index == numOfWords - 1)
                    break
                if (howManyLonger != 0) {
                    resLine.append(" ".repeat(lenOfSpaces + 1))
                    howManyLonger -= 1
                } else
                    resLine.append(" ".repeat(lenOfSpaces))
            }

            it.write(resLine.toString())
            it.write("\n")
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val reg = Regex("[^a-zа-я ё]")

    File(inputName).readLines()
            .map { it.toLowerCase().replace(reg, " ") }
            .flatMap { it.split(" ") }
            .filter { it.isNotEmpty() }
            .forEach {
                res[it] = res.getOrDefault(it, 0) + 1
            }

    return when {
        res.size <= 20 -> res
        else -> res.toList().sortedByDescending { (_, v) -> v }.subList(0, 20).toMap()
    }
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    File(outputName).bufferedWriter().use {
        val map = dictionary.map { (k, v) -> k.toLowerCase() to v.toLowerCase() }.toMap()
        File(inputName).readText()
                .forEach { symbol ->
                    var toReplace = map.getOrDefault(symbol.toLowerCase(), symbol.toString()).toLowerCase()
                    if (symbol.isUpperCase() && toReplace.isNotEmpty())
                        toReplace = toReplace.first().toUpperCase() + toReplace.substring(1)
                    it.write(toReplace)
                }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var maxLen = 0
        val words = mutableListOf<String>()
        for (word in File(inputName).readLines()) {
            val currentWord = word.toLowerCase()
            if (currentWord.length == currentWord.toSet().size && word.length >= maxLen) {
                if (word.length > maxLen) {
                    words.clear()
                    maxLen = word.length
                }
                words += word
            }
        }
        it.write(words.joinToString())
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Фрукты
        <ol>
          <li>Бананы</li>
          <li>
            Яблоки
            <ol>
              <li>Красные</li>
              <li>Зелёные</li>
            </ol>
          </li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).printWriter().use {
        val res = lhv * rhv
        val len = "$res".length + 1

        var r = rhv
        var nextNum = lhv * (r % 10)

        it.println(" ".repeat(len - "$lhv".length) + "$lhv")
        it.println("*" + " ".repeat(len - "$rhv".length - 1) + "$rhv")
        it.println("-".repeat(len))
        it.println(" ".repeat(len - "$nextNum".length) + "$nextNum")

        var counter = 1

        while (r / 10 != 0) {
            r /= 10
            nextNum = lhv * (r % 10)
            it.println("+" + " ".repeat(len - "$nextNum".length - 1 - counter) + "$nextNum")
            counter += 1
        }

        it.println("-".repeat(len))
        it.println(" $res")
    }
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
val Int.length
    get () = "$this".length

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).printWriter().use {
        val res = lhv / rhv
        var len: Int

        if (res == 0) {
            len = if (lhv.length == 1) 1 else 0
            it.println(" ".repeat(len) + "$lhv | $rhv")
            it.println(" ".repeat(if (lhv.length - 2 >= 0) (lhv.length - 2) else 0) + "-0" + " ".repeat(3) + "$res")
            it.println("-".repeat(max(2, lhv.length)))
            it.println(" ".repeat(len) + "$lhv")
            return
        }

        val lhvString = "$lhv".map(Character::getNumericValue).iterator()
        var upper = lhvString.next()

        while (upper / rhv == 0)
            upper = upper * 10 + lhvString.next()
        var under = upper / rhv * rhv

        len = if (under.length == upper.length) 1 else 0

        it.println(" ".repeat(len) + "$lhv | $rhv")
        it.println("-$under" + " ".repeat(lhv.length + 3 - under.length) + "$res")
        it.println("-".repeat(max(under.length + 1, upper.length)))

        len += upper.length - (upper - under).length
        upper -= under
        var numOfNull = if (upper == 0) 1 else 0

        for (num in lhvString) {
            upper = upper * 10 + num
            under = upper / rhv * rhv

            val nextLine = " ".repeat(len) + (if (numOfNull == 1) "0" else "") + "$upper"
            it.println(nextLine)
            it.println(" ".repeat(nextLine.length - "-$under".length) + "-$under")
            it.println(" ".repeat(
                    nextLine.length - (if (upper.length > under.length + 1) upper.length else (under.length + 1))
            ) + "-".repeat(max(under.length + 1, upper.length)))

            len += upper.length - (upper - under).length + numOfNull
            upper -= under
            numOfNull = if (upper == 0) 1 else 0
        }

        it.println(" ".repeat(len) + "$upper")
    }
}

