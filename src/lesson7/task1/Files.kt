@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
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
                } else {
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
    val map = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) { // Проходим по всем строкам файла
        for (element in substrings.toSet()) {               // Проходим по всем элементам в списке
            if (element !in map)             // Каждый новый элемент
                map[element] = 0             // Добавляем в map
            val el = element.toLowerCase()   // Переводим искомый элемент в нижний регистр
            if (el in line.toLowerCase()) {  // И если такой элемент есть в строке (тоже переведённой в нижний регистр)
                val count = map[element]!! + // Считаем,сколько раз уже входил и прибавляем
                        Regex(el).findAll(line.toLowerCase(), 0).toList().size   // новое число вхождений
                map[element] = count         // Изменяем
            }
        }
    }
    return map
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
    val fixes = mapOf('Ы' to 'И', 'ы' to 'и', 'Я' to 'А', 'я' to 'а', 'Ю' to 'У', 'ю' to 'у')   // Мапа исправлений
    val letters = setOf('Ж', 'ж', 'Ч', 'ч', 'Ш', 'ш', 'Щ', 'щ')     // Множество возможных согласных
    val outputStream = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {                // Проходим по всем строкам входного файла
        var str = line                                         // Создаём строку, которую будем изменять
        for (i in 0 until str.length - 1) {
            if (str[i] in letters && str[i + 1] in fixes)      // Если подряд идёт согласная из мно-ва и нужная гласная
                str = str.substring(0, i + 1) + fixes[str[i + 1]] + str.substring(i + 2, str.length) // Заменяем
        }
        outputStream.write(str)                     // Записываем полностью исправленную строку в выходной файл
        outputStream.newLine()                      // Переходим на новую строку
    }
    outputStream.close()
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
    val list = mutableListOf<String>()
    val outputStream = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines())
        list.add(line.trim())
    val maxLength = list.maxBy { it.trim().length }!!.trim().length    // Переменная с максимальной длиной строки в тексте
    for (i in 0 until list.size) {
        val size = (maxLength - list[i].length) / 2 + list[i].length   // Находим длину строки после добавления пробелов
        list[i] = list[i].padStart(size)                               // Добавляем проблелы, чтобы размер строки соответсовал size
        outputStream.write(list[i])
        outputStream.newLine()
    }
    outputStream.close()
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
    val outputStream = File(outputName).bufferedWriter()
    var max = 0
    for (line in File(inputName).readLines()) {             // Находим максимальную длину строки,
        var str = line.trim()                               // убирая лишние пробелы
        str = Regex(""" +""").replace(str, " ")
        if (str.length > max)
            max = str.length
    }
    for (line in File(inputName).readLines()) {             // Проходим по каждой строке файла
        var str = line.trim()                               // Убираем в строке лишние пробелы
        str = Regex(""" +""").replace(str, " ")
        var str2: String
        val list = str.split(" ").toMutableList()           // Список слов в строке
        if (str.length == max || list.size < 2)             // Если длина строки равна максимальной, или в строке меньше 2-х слов,
            str2 = str                                      // то заносим в файл эту же строку
        else {                                                              // Иначе
            while (list.joinToString(separator = "").length != max)         // Пока длина строки не станет равна максимальной,
                for (i in 0 until list.size - 1)                            // мы проходим по всем словам этой строки,
                    if (list.joinToString(separator = "").length < max)     // и если длина строки не равна максимальной,
                        list[i] += " "                                      // то добавляем пробел к слову
            str2 = list.joinToString(separator = "")
        }
        outputStream.write(str2)
        outputStream.newLine()
    }
    outputStream.close()
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
    var map = mutableMapOf<String, MutableList<Int>>()
    val result = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        if (line != "") {                                                      // Не рассматриваем пустые строки
            val str = Regex("""[^а-яА-ЯёЁa-zA-Z]""").replace(line, " ").trim()    // Заменяем лишние символы на пробелы
            val words = Regex(""" +""").replace(str, " ").split(Regex("""\s"""))    // Разбиваем на слова по пробелам
            for (word in words) {                                              // Добавляем в map новые слова или
                map.getOrPut(word.toLowerCase()) { mutableListOf() } += 1      // добавляем значения в уже имеющиеся
            }
        }
    }
    if (map.isEmpty()) return result        // Если в исходном тексте не было слов, то возвращаем пустой result
    var max = 0
    var name = ""
    map = (map - "").toMutableMap()         // Убираемм из map переносы строк и пустые строки, если они есть
    for (i in 1..20) {
        for ((element, list) in map) {      // Находим слово с максимальной длиной строки
            if (list.size > max) {          // (т.е. максимальным вхождением в текст)
                max = list.size             // Запоминаем слово и число его вхождений
                name = element
            }
        }
        result[name] = max                  // Переносим полученные значения в result
        max = 0
        map = (map - name).toMutableMap()
    }
    return result
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
    val dict: Map<Char, String> = dictionary.map { it.key.toLowerCase() to it.value.toLowerCase() }.toMap()
    // Тот же самый словарь, только в нижнем регистре
    val outputStream = File(outputName).bufferedWriter()
    // Проходим по каждой строке и каждому символу строки
    // Если такой символ есть в словаре и в строке он в нижнем регистре, то просто добаляем соответсвие из словаря.
    // Если такой символ есть в словаре и в стркое он в верхнем регистре, то добавляем его, делаю первый символ соответствия заглавным.
    // Если же такого символы нет в словер, то просто добавляем сам символ
    for (line in File(inputName).readLines()) {
        val str = StringBuilder()
        for (char in line) {
            when {
                char.toLowerCase() in dict && char == char.toLowerCase() -> str.append(dict[char.toLowerCase()])
                char.toLowerCase() in dict && char == char.toUpperCase() -> str.append(dict[char.toLowerCase()]!!.capitalize())
                else -> str.append(char)
            }
        }
        outputStream.write(str.toString())
        outputStream.newLine()
    }
    outputStream.close()
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
    val outputStream = File(outputName).bufferedWriter()
    val list = mutableListOf<String>()                  // Список, куда будем заносить слова с разными буквами
    var max = 0
    val res = StringBuilder()                           // Строка, в которую будем добавлять итоговые слова
    for (line in File(inputName).readLines()) {         // Проходим по всем строкам входного файла
        val char = line.toLowerCase().toSet()           // Множество всех букв слова
        if (char.size == line.length) {         // Если кол-во символов в мно-ве равно длине слову, то буквы не повторяются,
            list.add(line)                      // поэтому заносим это слово в список
            if (line.length >= max) max = line.length  // и ищем максимальную длину среди них
        }
    }
    for (word in list) {                                // Проходим по всему списку из слов с разными буквами
        if (word.length == max) res.append("$word, ")   // и записываем в строку те, что имеют длину max
    }                                                   // Вносим в выходной файл строку из подходящих слов,
    if (list.isNotEmpty())                // Если было хотя бы нужное одно слово, то после него стоят запятая и пробел,
        outputStream.write(res.toString().substring(0, res.toString().length - 2))  // поэтому убираем их.
    else                                  // Если же нужных слов не оказалось,
        outputStream.write("")            // то добавляем пустую строку
    outputStream.close()
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
    val outputStream = File(outputName).bufferedWriter()
    var count = 0       // Счётчик для пустых строк, чтобы отслеживать <p> и </p>
    var count1 = 0      // Счётчик для полужирного текста, чтобы отслеживать <b> и </b>
    var count2 = 0      // Счётчик для курсива, чтобы отслеживать <i> и </i>
    var count3 = 0      // Счётчик для зачёркнутого текста, чтобы отслеживать <s> и </s>
    if (File(inputName).readLines().isNotEmpty()) {         // Проверяем файл на непустоту
        outputStream.write("<html>")                        // Добавляем теги начала
        outputStream.write("<body>")
        outputStream.write("<p>")
        loop@ for (line in File(inputName).readLines()) {   // Проходим по всем строкам
            outputStream.newLine()
            if (line.isEmpty() && count % 2 == 0) {         // Если строка пустая и встречается чётное кол-во раз,
                outputStream.write("</p>")
                outputStream.write("<p>")                   // то добавляем тег, открывающий новый абзац,
                count++                                     // прибавляем к счётчику
                outputStream.newLine()
                continue@loop                               // и рассматриваем следующую строку
            }
            if (line.isEmpty() && count % 2 == 1) {         // Если строка пустая и встречается нечётное кол-во раз,
                outputStream.write("</p>")                  // то добавляем тег, закрывающий абзац,
                count++                                     // прибавляем к счётчику
                outputStream.newLine()
                continue@loop                               // и рассматриваем следующую строку
            }
            var str = line
            while (Regex("""[*~]""") in str) {     // Если же строка не была пустой и в ней содержатся * или ~,
                if (Regex("""\*\*\*""") in str)
                    str = Regex("""\*\*\*""").replace(str, "</b></i>")
                if (Regex("""\*\*""") in str && count1 % 2 == 0) {     // то заменяем символы
                    str = Regex("""\*\*""").replaceFirst(str, "<b>")   // на соответствующие им теги
                    count1++                                           // в разметке HTML,
                }                                                      // прибавляя к счётчикам
                if (Regex("""\*\*""") in str && count1 % 2 == 1) {
                    str = Regex("""\*\*""").replaceFirst(str, "</b>")
                    count1++
                }
                if (Regex("""\*""") in str && count2 % 2 == 0) {
                    str = Regex("""\*""").replaceFirst(str, "<i>")
                    count2++
                }
                if (Regex("""\*""") in str && count2 % 2 == 1) {
                    str = Regex("""\*""").replaceFirst(str, "</i>")
                    count2++
                }
                if (Regex("""~~""") in str && count3 % 2 == 0) {
                    str = Regex("""~~""").replaceFirst(str, "<s>")
                    count3++
                }
                if (Regex("""~~""") in str && count3 % 2 == 1) {
                    str = Regex("""~~""").replaceFirst(str, "</s>")
                    count3++
                }
            }
            outputStream.write(str)         // Записываем в выходной файл получившуюся строку
        }
        outputStream.write("</p>")          // Добавляем теги конца файла
        outputStream.write("</body>")
        outputStream.write("</html>")
    }
    outputStream.close()
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
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
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
    TODO()
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
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

