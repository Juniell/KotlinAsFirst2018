@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

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
fun digitNumber(n: Int): Int {
    var number = n
    var count = 0
    do {
        count++
        number /= 10
    } while (abs(number) > 0)
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var result = 2
    var number = 1
    if (n <= 2) return 1
    if (n == 3) return 2
    for (i in 4..n) {
        number = result - number
        result += number
    }
    return result
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun nod(m: Int, n: Int): Int {
    var a = m
    var b = n
    while (a != b)
        if (a > b) a -= b else b -= a
    return b
}
fun lcm(m: Int, n: Int): Int {
    var nok = n * m

    nok /= nod(m, n)
    return nok
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDivisor = 1
    for (i in 2..n / 2) {
        if (n % i == 0) {
            minDivisor = i
            return minDivisor
        }
    }
    return if (isPrime(n)) n else minDivisor
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var maxDivisor = 1
    for (i in (n / 2) downTo 2) {
        if (n % i == 0) {
            maxDivisor = i
            return maxDivisor
        }
    }
    return maxDivisor
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = nod(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val l = sqrt(m.toDouble()).toInt()
    val r = sqrt(n.toDouble()).toInt()
    val count = 0
    for (i in l..r) {
        if ((m <= i * i) && (i * i <= n)) return true
    }
    return count > 0
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
    var count = 0
    var n = x
    while (n != 1) {
        if (n % 2 == 0) n /= 2 else n = 3 * n + 1
        count += 1
    }
    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    val a = x % (2 * PI)
    var number = a
    var count = 1
    var i = 1
    var result = a
    while (abs(number) >= eps) {
        count++
        i += 2
        number = (number * a * a) / (i * (i - 1))
        if (count % 2 == 1) result += number else result -= number
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    val a = x % (2 * PI)
    var number = 1.0
    var count = 1
    var i = 0
    var result = 1.0
    while (abs(number) >= eps) {
        count++
        i += 2
        number = (number * a * a) / (i * (i - 1))
        if (count % 2 == 1) result += number else result -= number
    }
    return result
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun length(n: Int): Int {
    var count = 0
    var x = n
    do {
        count += 1
        x /= 10
    } while (x > 0)
    return count
}
fun revert(n: Int): Int {
    var m = 0
    var x = n
    var lenght = length(n)
    if (lenght == 1) return n else {
        while (lenght > 0) {
            lenght -= 1
            m += (x % 10 * pow(10.0, lenght.toDouble())).toInt()
            x /= 10
        }
    }
    return m
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var k1: Int
    var k2: Int
    var a: Double
    var x = n
    val lenght = length(n)
    a = (lenght - 1).toDouble()
    for (i in 1..lenght / 2) {
        k1 = x / pow(10.0, a).toInt()
        k2 = x % 10
        if (k1 != k2) return false
        else x = (x % pow(10.0, a) / 10).toInt()
        a -= 2
    }
    return x < 10
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var x: Int
    var a = n
    while (a > 10) {
        x = a % 10
        a /= 10
        if (x != (a % 10)) return true
    }
    return (a > 9)
}


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var i = 0
    var a = 1
    var lenght = 0.0
    while (lenght < n) {
        i += 1
        a = i * i
        lenght += length(a).toDouble()
    }
    return (a / (pow(10.0, (lenght - n)).toInt()) % 10)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var i = 0
    var lenght = 0.0
    var number = 2
    while (lenght < n) {
        i++
        number = fib(i)
        lenght += length(number).toDouble()
    }
    return (number / (pow(10.0, (lenght - n)).toInt()) % 10)
}