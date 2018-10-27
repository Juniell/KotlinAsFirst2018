@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.isPrime
import java.io.File.separator
import java.lang.Math.pow
import java.math.BigInteger
import kotlin.math.sqrt

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
fun abs(v: List<Double>): Double {
    val res = v.map { it * it }
    return if (v.isNotEmpty()) sqrt(res.sum()) else 0.0
}

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
    val mean = mean(list)
    if (list.isNotEmpty()) list.replaceAll { it - mean }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var result = 0.0
    if (a.isNotEmpty() && b.isNotEmpty()) {
        for (i in 0 until a.size) {
            result += a[i] * b[i]
        }
    }
    return result
}


/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    val res = p.toMutableList().mapIndexed { index, a ->
        a * pow(x, index.toDouble())
    }
    return if (p.isNotEmpty()) res.sum() else 0.0
}

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
    if (list.isNotEmpty()) {
        var sum = list[0]
        for (i in 1 until list.size) {
            sum += list[i]
            list[i] = sum
        }
    }
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
    var i = 2
    var x = n
    val a = mutableListOf<Int>()
    if (isPrime(x))
        a.add(x)
    else {
        while (x > 1) {
            if (isPrime(i)) {
                while (x % i == 0) {
                    a.add(i)
                    x /= i
                }
            }
            if (i != 2) i += 2 else i++
        }
    }
    return a.sorted()
}


/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var x = n
    if (n == 0) list.add(n)
    while (x > 0) {
        list.add(x % base)
        x /= base
    }
    var b: Int
    val c = list.size - 1
    for (i in 0..(c / 2)) {
        b = list[i]
        list[i] = list[c - i]
        list[c - i] = b
    }
    return list
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
    val list = convert(n, base)
    var b: Int
    val c = list.size - 1
    var string = ""
    for (i in 0..c) {
        b = list[i]
        if (list[i] < 10)
            string += b
        else {
            when (list[i]) {
                10 -> string += 'a'
                11 -> string += 'b'
                12 -> string += 'c'
                13 -> string += 'd'
                14 -> string += 'e'
                15 -> string += 'f'
                16 -> string += 'g'
                17 -> string += 'h'
                18 -> string += 'i'
                19 -> string += 'j'
                20 -> string += 'k'
                21 -> string += 'l'
                22 -> string += 'm'
                23 -> string += 'n'
                24 -> string += 'o'
                25 -> string += 'p'
                26 -> string += 'q'
                27 -> string += 'r'
                28 -> string += 's'
                29 -> string += 't'
                30 -> string += 'u'
                31 -> string += 'v'
                32 -> string += 'w'
                33 -> string += 'x'
                34 -> string += 'y'
                35 -> string += 'z'
            }
        }
    }
    return string
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var n = 0
    for ((stage, i) in (digits.size - 1 downTo 0).withIndex()) {
        n += digits[i] * pow(base.toDouble(), stage.toDouble()).toInt()
    }
    return n
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
    val list = mutableListOf<Int>()
    for (i in 0 until str.length) {
        when (str[i]) {
            '0' -> list.add(0)
            '1' -> list.add(1)
            '2' -> list.add(2)
            '3' -> list.add(3)
            '4' -> list.add(4)
            '5' -> list.add(5)
            '6' -> list.add(6)
            '7' -> list.add(7)
            '8' -> list.add(8)
            '9' -> list.add(9)
            'a' -> list.add(10)
            'b' -> list.add(11)
            'c' -> list.add(12)
            'd' -> list.add(13)
            'e' -> list.add(14)
            'f' -> list.add(15)
            'g' -> list.add(16)
            'h' -> list.add(17)
            'i' -> list.add(18)
            'j' -> list.add(19)
            'k' -> list.add(20)
            'l' -> list.add(21)
            'm' -> list.add(22)
            'n' -> list.add(23)
            'o' -> list.add(24)
            'p' -> list.add(25)
            'q' -> list.add(26)
            'r' -> list.add(27)
            's' -> list.add(28)
            't' -> list.add(29)
            'u' -> list.add(30)
            'v' -> list.add(31)
            'w' -> list.add(32)
            'x' -> list.add(33)
            'y' -> list.add(34)
            'z' -> list.add(35)
        }
    }
    return decimal(list, base)
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
    var string = ""
    var x = n / 1000
    var digit = x % 10
    when (digit) {
        1 -> string += "M"
        2 -> string += "MM"
        3 -> string += "MMM"
    }
    x = n / 100
    digit = x % 10
    when (digit) {
        1 -> string += "C"
        2 -> string += "CC"
        3 -> string += "CCC"
        4 -> string += "CD"
        5 -> string += "D"
        6 -> string += "DC"
        7 -> string += "DCC"
        8 -> string += "DCCC"
        9 -> string += "CM"
    }
    x = n / 10
    digit = x % 10
    when (digit) {
        1 -> string += "X"
        2 -> string += "XX"
        3 -> string += "XXX"
        4 -> string += "XL"
        5 -> string += "L"
        6 -> string += "LX"
        7 -> string += "LXX"
        8 -> string += "LXXX"
        9 -> string += "XC"
    }
    x = n
    digit = x % 10
    when (digit) {
        1 -> string += "I"
        2 -> string += "II"
        3 -> string += "III"
        4 -> string += "IV"
        5 -> string += "V"
        6 -> string += "VI"
        7 -> string += "VII"
        8 -> string += "VIII"
        9 -> string += "IX"
    }
    return string
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun hundred(a: Int): String {
    var str = ""
    when (a) {
        1 -> str += "сто "
        2 -> str += "двести "
        3 -> str += "триста "
        4 -> str += "четыреста "
        5 -> str += "пятьсот "
        6 -> str += "шестьсот "
        7 -> str += "семьсот "
        8 -> str += "восемьсот "
        9 -> str += "девятьсот "
    }
    return str
}

fun ten(a: Int): String {
    var str = ""
    when (a) {
        1 -> str += "десять "
        2 -> str += "двадцать "
        3 -> str += "тридцать "
        4 -> str += "сорок "
        5 -> str += "пятьдесят "
        6 -> str += "шестьдесят "
        7 -> str += "семьдесят "
        8 -> str += "восемьдесят "
        9 -> str += "девяносто "
    }
    return str
}

fun twenty(a: Int): String {
    var str = ""
    when (a) {
        10 -> str += "десять "
        11 -> str += "одиннадцать "
        12 -> str += "двенадцать "
        13 -> str += "тринадцать "
        14 -> str += "четырнадцать "
        15 -> str += "пятнадцать "
        16 -> str += "шестнадцать "
        17 -> str += "семнадцать "
        18 -> str += "восемнадцать "
        19 -> str += "девятнадцать "
    }
    return str
}

fun russian(n: Int): String {
    var string = ""
    var x: Int
    var digit: Int
    if (n >= 1000) {
        x = n / 100000
        digit = x % 10
        string += hundred(digit)
        x = n / 1000
        if (x % 100 in 11..19) {
            digit = x % 100
            string += twenty(digit)
            string += "тысяч "
        } else {
            x = n / 10000
            digit = x % 10
            string += ten(digit)
            x = n / 1000
            digit = x % 10
            when (digit) {
                0 -> string += "тысяч "
                1 -> string += "одна тысяча "
                2 -> string += "две тысячи "
                3 -> string += "три тысячи "
                4 -> string += "четыре тысячи "
                5 -> string += "пять тысяч "
                6 -> string += "шесть тысяч "
                7 -> string += "семь тысяч "
                8 -> string += "восемь тысяч "
                9 -> string += "девять тысяч "
            }
        }
    }
    x = n / 100
    digit = x % 10
    string += hundred(digit)
    if (n % 100 in 11..19) {
        digit = n % 100
        string += twenty(digit)
    } else {
        x = n / 10
        digit = x % 10
        string += ten(digit)
        digit = n % 10
        when (digit) {
            1 -> string += "один"
            2 -> string += "два"
            3 -> string += "три"
            4 -> string += "четыре"
            5 -> string += "пять"
            6 -> string += "шесть"
            7 -> string += "семь"
            8 -> string += "восемь"
            9 -> string += "девять"
        }
    }
    return string.trim()
}