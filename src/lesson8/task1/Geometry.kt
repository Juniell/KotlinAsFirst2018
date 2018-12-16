@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
            if (center.distance(other.center) >= radius + other.radius) {
                center.distance(other.center) - (radius + other.radius)
            } else 0.0


    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = p.distance(center) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    var max = 0.0
    var result = Segment(Point(0.0, 0.0), Point(0.0, 0.0))
    for (point in points) {
        for (otherPoint in points) {
            if (point.distance(otherPoint) >= max) {
                max = point.distance(otherPoint)
                result = Segment(point, otherPoint)
            }
        }
    }
    return result
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val center = Point((diameter.begin.x + diameter.end.x) / 2, (diameter.begin.y + diameter.end.y) / 2)
    val rad = maxOf(center.distance(diameter.begin), center.distance(diameter.end))
    return Circle(center, rad)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x = (other.b * cos(angle) - b * cos(other.angle)) / sin(angle - other.angle)
        // Для нахождения y нужно разделить на cos(an). Поэтому выбираем ур-ие без деления на 0
        return if (angle == PI / 2)
            Point(x, (x * sin(other.angle) + other.b) / cos(other.angle))
        else Point(x, (x * sin(angle) + b) / cos(angle))
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val a = s.begin
    val b = s.end
    var angle = if (a.x.toDouble() == b.x.toDouble()) PI / 2 else atan((a.y.toDouble() - b.y.toDouble()) / (a.x.toDouble() - b.x.toDouble()))
    // Угол, найденный через треугольник (s.begin, s.end, point)
    when {                              // "Приводим" угол в нужный диапазон
        angle < 0 -> angle += PI
        angle >= PI -> angle -= PI
    }
    return Line(s.begin, angle % PI)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная1
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    var angle = if (a.x == b.x) 0.0 else atan((a.y - b.y) / (a.x - b.x)) + PI / 2    // Проверка на то, чтобы не было деления на 0
    when {                              // "Приводим" угол в нужный диапазон
        angle < 0 -> angle += PI
        angle >= PI -> angle -= PI
    }
    return Line(Point((a.x + b.x) / 2, (a.y + b.y) / 2), angle % PI)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var min = Double.POSITIVE_INFINITY
    var pair = Pair(circles[0], circles[1])     // Для возможности сравнения берём самую первую пару
    for (i in 0 until circles.size)             // Рассматриваем все пары окружностей
        for (k in 0 until circles.size) {       // и ищем пару с минимальным расстоянием
            val dist = circles[i].distance(circles[k])
            if (dist < min && i != k) {
                min = dist
                pair = Pair(circles[i], circles[k])
            }
        }
    return pair
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val normalAB = bisectorByPoints(a, b)          // Серединный перпендикуляр отрезка AB
    val normalBC = bisectorByPoints(b, c)          // Серединный перпендикуляр отрезка BC
    val center = normalAB.crossPoint(normalBC)     // Центр окружности
    val radius = maxOf(center.distance(a), center.distance(b), center.distance(c))
    return Circle(center, radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()      // Проверка нужного кол-ва точек
    if (points.size == 1) return Circle(points[0], 0.0)
    var max = 0.0                                               // Почему-то не работает функция diameter
    // Сначала ищем по варианту №2 (две самые удалённые точки)
    var diam = Segment(Point(0.0, 0.0), Point(0.0, 0.0))        // Ищем 2 максимально удалённые точки (как функция diameter),
    for (point in points)                                       // которые станут диаметром
        for (otherPoint in points)
            if (point.distance(otherPoint) >= max) {
                max = point.distance(otherPoint)
                diam = Segment(point, otherPoint)
            }
    var circle = circleByDiameter(diam)          // Строим окружность по найденному диаметру
    var count = 0
    for (point in points)                        // Проверяем, чтобы все точки входили в окружность или лежали на ней
        if (!circle.contains(point)) {           // Если хотя бы одна не лежит, прибавляем к счётчику и выходим из цикла
            count++
            break
        }
    var res = circle
    if (count == 0) return res                // Если все точки лежат внутри окружности или на ней, то выводим окружность,
    else {                                    // иначе ищем по варинату №1 (по трём точкам)
        var radius = Double.POSITIVE_INFINITY
        for (point1 in points)                                              // рассматриваем все варианты точек
            for (point2 in points)
                for (point3 in points) {
                    circle = circleByThreePoints(point1, point2, point3)    // находим окружность по трём точкам
                    count = 0
                    for (point in points)
                        if (circle.contains(point)) count++
                    if (count == points.size && circle.radius < radius) {   // если все точки в окружности и радиус меньше
                        radius = circle.radius                              // то заменяем радиус на новый
                        res = circle                                        // и в результат заносим эту окружность
                    }
                }
    }
    return res
}

