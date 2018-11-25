@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence", "NAME_SHADOWING")

package lesson5.task1

import org.omg.CORBA.ARG_IN.value
import kotlin.math.max
import kotlin.math.min

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
    val result = mutableMapOf<String, String>()
    for ((name, phone) in mapA) {
        when {
            name in mapB && phone != mapB[name] -> {
                result[name] = phone + ", " + mapB[name]
            }
            name !in mapB -> result[name] = phone

        }
    }
    for ((name, phone) in mapB) {
        if (name !in result) {
            result[name] = phone
        }
    }
    return result
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
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val result = mutableMapOf<Int, MutableList<String>>()
    for ((name, grade) in grades)
        result.getOrPut(grade) { mutableListOf() } += name
    for ((grade, _) in result)
        result[grade]!!.sortByDescending { it }
    return result
}

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
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean = a.all { (key, value) ->
    (b[key] == value)
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
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val res = mutableMapOf<String, Double>()
    val map = mutableMapOf<String, MutableList<Double>>()
    for ((stock, price) in stockPrices) {
        map.getOrPut(stock, ::mutableListOf).add(price)
        for ((key, value) in map) {
            res += key to (value.sum() / value.size)
        }
    }
    return res
}

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
    var min = Double.POSITIVE_INFINITY
    var result = ""
    for ((name, pair) in stuff) {
        if (kind == pair.first && min >= pair.second) {
            min = pair.second
            result = name
        }
    }
    return if (min != Double.POSITIVE_INFINITY)
        result
    else
        null
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
/**
 * mapOf(
 * "Marat" to setOf("Mikhail", "Sveta"),
 * "Sveta" to setOf("Mikhail"),
 * "Mikhail" to setOf()
 *
 * mapOf(
 * "Marat" to setOf("Sveta"),
 * "Sveta" to setOf("Mikhail")

 */

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val maps = mutableMapOf<String, MutableSet<String>>()
    var count = 0
    for ((name, people) in friends) {
        maps[name] = people.toMutableSet()
    }
    do {
        count++
        for ((name, people) in friends) {
            for (person in people) {
                if (person in friends) maps[name]!!.addAll(maps[person]!!.toMutableSet())
                if (person !in friends) maps[person] = mutableSetOf()
            }
            if (name in maps[name]!!) maps[name]!! -= name
        }
    } while (count <= maps.size)
    val res = mutableMapOf<String, Set<String>>()
    for ((name, _) in maps) {
        res[name] = maps[name]!!.toSet()
    }
    return res.toMap()
}

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
    for ((key, value) in b) {
        if (a[key] == value) a.remove(key)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.intersect(b).toList()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val w = word.toLowerCase().toSet()
    val c = chars.map { it.toLowerCase() }.toSet()
    return chars.isEmpty() && word == "" || chars.isNotEmpty() && c.intersect(w) == w
}

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
fun extractRepeats(list: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    for (symbol in list)
        res[symbol] = res.getOrDefault(symbol, 0) + 1
    return res.filterValues { it > 1 }
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun letter(a: String): List<Char> = a.toList()

fun hasAnagrams(words: List<String>): Boolean {
    if (words.isEmpty()) return false
    val begin = words.map { letter(it).sorted() }
    val compare = mutableSetOf(begin[0])
    for (i in 1 until begin.size) {
        if (begin[i] in compare) return true
        compare.add(begin[i])
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
    if (list.isEmpty()) return -1 to -1
    val map = mutableMapOf<Int, Int>()  // Пустой массив,в котором ключ - число из list, а значение - его индекс
    for (i in 0 until list.size) {
        val k = number - list[i]    // k - значение, которое должно быть в list, чтобы сумма была равна number
        if (k in map) return min(map[k]!!, i) to max(map[k]!!, i)   // Если такое значение есть в map, то выводим пару
        else map[list[i]] = i
    }
    return -1 to -1
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
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val res = mutableSetOf<String>()
    val names = treasures.keys.toList()                                 // список с названиями предметов
    val properties: MutableList<Pair<Int, Int>> = mutableListOf()       // список с парой (вес to цена) для каждого предмета
    val array = Array(treasures.size + 1) { Array(capacity + 1) { 0 } } // заполнемый нами двумерный массив (таблица)
    for (i in 0 until treasures.size)                                   // заполняем список с характеристикой предметов
        properties.add(treasures[names[i]]!!.first to treasures[names[i]]!!.second)
    for (j in 1..treasures.size) {
        // Определяем, входит ли предмет в искомый набор. Если предмет вмещается, то ищем максимальное значение между
        // стоимостью рюкзака вместе с этим предметом и рюкзака без него и записываем в таблицу.
        // Если предмет не вмещается, то заносим в талицу прыдущее значение
        // Максимальная возможная стоимость находится в нижней правой ячейке таблицы array[j][k]
        for (k in 1..capacity)
            if (properties[j - 1].first <= k)
                array[j][k] = max(array[j - 1][k], array[j - 1][k - properties[j - 1].first] + properties[j - 1].second)
            else
                array[j][k] = array[j - 1][k]
    }
    fun recovery(j: Int, k: Int) {              // Рекурсивная функция для восстановления набора предметов, входящих в рюкзак
        if (array[j][k] == 0)                                     // Пропускаем ячейки, равные 0
            return
        if (array[j - 1][k] == array[j][k])                       // Если соседние элементы равны,
            recovery(j - 1, k)                                    // то предмет не входит в итоговый набор.
        else {
            res.add(names[j - 1])                                 // Иначе добавляем предмет в итоговый набор
            recovery(j - 1, k - properties[j - 1].first)          // и рассматриваем следующий предмет
        }
    }
    recovery(treasures.size, capacity)          // Используем функцию для нашего двумерного массива
    return res
}