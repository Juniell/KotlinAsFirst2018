@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import lesson4.task1.ten

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
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
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
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val days: Int
    val month: Int
    val year: Int
    try {
        days = parts[0].toInt()
        month = when (parts[1]) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            "декабря" -> 12
            else -> return ""
        }
        year = parts[2].toInt()
        if (days > daysInMonth(month, year)) return ""
    } catch (e: NumberFormatException) {
        return ""
    }
    return String.format("%02d.%02d.%d", days, month, year)
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
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val days: Int
    val month: String
    val yaer: Int
    try {
        days = parts[0].toInt()
        month = when (parts[1].toInt()) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> return ""
        }
        yaer = parts[2].toInt()
        if (days > daysInMonth(parts[1].toInt(), yaer)) return ""
    } catch (e: NumberFormatException) {
        return ""
    }
    return String.format("%d %s %d", days, month, yaer)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String = if (Regex("""[^0-9()+\s\-]|(?<!^)\+""") in phone) ""
else Regex("""-|\s|\(|\)|^\+$""").replace(phone, "")

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
    if (Regex("""[^0-9%\-\s]""") in jumps) return -1
    var str = Regex("""[%\-]""").replace(jumps, " ")
    str = Regex(""" + +?""").replace(str, " ").trim()
    if (str == "") return -1
    val parts = str.split(Regex("""[ .]"""))
    var max = parts[0].toInt()
    for (part in parts) {
        if (part.toInt() > max) max = part.toInt()
    }
    return max
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
    if (Regex("""[^0-9%+\-\s]|-+\d""") in jumps || Regex("""\+""") !in jumps) return -1
    var str = Regex("""%|-|\s""").replace(jumps, ".")
    str = Regex("""\.+\.+?""").replace(str, ".")
    val parts = str.split(".")
    var max = -1
    for (i in 1 until parts.size) {
        if (parts[i] == "+" && parts[i - 1].toInt() > max) max = parts[i - 1].toInt()
    }
    return max
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
    if (Regex("""[^0-9+\-\s]""") in expression || expression.isEmpty() || Regex("""\d""") !in expression ||
            Regex("""^\+|^-|(\s+\s+\s+?)|([-+])+\s+(\s+?)+([-+])|\d+[-+]|[-+]+\d|\d+\s+(\s+?)\d""") in expression)
        throw IllegalArgumentException()
    val parts = expression.split(" ")
    var result = parts[0].toInt()
    for (i in 1..(parts.size - 2)) {
        if (parts[i] == "+") result += parts[i + 1].toInt()
        if (parts[i] == "-") result -= parts[i + 1].toInt()
    }
    return result
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
    val parts = str.toLowerCase().split(" ")
    var ind = 0
    for (i in 1 until parts.size) {
        if (parts[i] == parts[i - 1])
            return ind
        ind += parts[i - 1].length + 1
    }
    return -1
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
    if (description.matches(Regex(""".*+ +(\d)+""")) || description == "") return ""
    val parts = description.split("; ")
    var max = 0.0
    var word = ""
    for (part in parts) {
        val pair = part.split(" ")
        if (parts.size == 1) return pair[0]
        if (pair[1].toDouble() > max) {              // Ищем самый дорогой товар
            max = pair[1].toDouble()                 // и запоминаем его имя
            word = pair[0]
        }
    }
    return word
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
    if (Regex("""[^IVXLCDM]""") in roman || roman == "") return -1
    val units = listOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")
    val tens = listOf("X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
    val hundred = listOf("C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
    val thousand = listOf("M", "MM", "MMM")
    var str = roman
    var result = 0
    for (i in 2 downTo 0) {
        if (Regex("""^${thousand[i]}""") in str) {
            str = Regex("""^${thousand[i]}""").replace(str, "")
            result += (i + 1) * 1000
        }
    }
    for (i in 8 downTo 0) {
        if (Regex("""^${hundred[i]}""") in str) {
            str = Regex("""^${hundred[i]}""").replace(str, "")
            result += (i + 1) * 100
        }
    }
    for (i in 8 downTo 0) {
        if (Regex("""^${tens[i]}""") in str) {
            str = Regex("""^${tens[i]}""").replace(str, "")
            result += (i + 1) * 10
        }
    }
    for (i in 8 downTo 0) {
        if (Regex("""^${units[i]}""") in str) {
            str = Regex("""^${units[i]}""").replace(str, "")
            result += i + 1
        }
    }
    return result
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
    if (Regex("""[^+\-><\[\]\s]""") in commands)      // Проверяем, есть ли в строке с командами недопустимые символы
        throw IllegalArgumentException()              // Бросаем исключение, если есть
    if (Regex("""\[""").findAll(commands, 0).toList().size != Regex("""]""").findAll(commands, 0).toList().size)
        throw IllegalArgumentException()              // Проверяем парность скобок в строке с командами
    val list = mutableListOf<Int>()
    var ind: Int = cells / 2
    var count = 0                                     // Счётчик команд, чтобы не превзойти limit
    var k = 0                                         // Индекс команды из commands
    var open = 0                                      // Счётчик для открывающих скобок
    var end = 0                                       // Счётчик для закрывающих скобок
    for (com in commands) {                           // Проверяем, не идёт ли закрывающая скобка перед открывающей
        if (com == '[') open += 1
        if (com == ']') end += 1
        if (end == 1 && open == 0)                    // Если идёт, то бросаем исключение
            throw IllegalArgumentException()
    }
    open = 0
    end = 0
    for (i in commands.length - 1 downTo 0) {
        if (commands[i] == '[') open += 1
        if (commands[i] == ']') end += 1
        if (end == 0 && open == 1)                    // Если идёт, то бросаем исключение
            throw IllegalArgumentException()
    }
    for (i in 0 until cells)                          // Заполняем список нулями
        list.add(0)
    while (k < commands.length && count != limit) {
        when (commands[k]) {
            '>' -> ind++
            '<' -> ind--
            '+' -> list[ind] += 1
            '-' -> list[ind] -= 1
            ' ' -> k = k
            '[' -> {                                   // Если встречается открывающая скобка и значение под датчиком
                if (list[ind] == 0) {                  // равно нулю, то датчик должен перейти на команду, которая идёт
                    open = 1                           // после соответсвующей закрывающей скобкой.
                    end = 0                            // open = 1, потому что мы уже учли одну открывающую скобку
                    while (open != end) {              // open и end будут равны только тогда, когда мы найдём
                        k += 1                         // закрывающую скобку, соответствующую нашей
                        if (commands[k] == '[')        // поэтому мы считаем кол-во скобок и идём дальше по списку команд
                            open += 1
                        if (commands[k] == ']')
                            end += 1
                    }
                }
            }
            ']' -> {                                   // Аналогично с закрывающей скобкой
                if (list[ind] != 0) {                  // Только идём назад по списку команд и в этот раз end = 1
                    open = 0
                    end = 1
                    while (open != end) {
                        k -= 1
                        if (commands[k] == '[')
                            open += 1
                        if (commands[k] == ']')
                            end += 1
                    }
                }
            }
        }
        k++
        count++
        if (ind >= cells || ind < 0)                  // Проверка на то, чтобы не было перехода за пределы списка
            throw IllegalStateException()
    }
    return list
}