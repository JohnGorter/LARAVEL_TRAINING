sealed class Month {
    class January(var numberOfDays: Int) : Month()
    data class February(var displayName: String) : Month()
    object March : Month() {
        var numberOfDays: Int = 0
        var displayName: String = "March..."
    }
}

fun eval(month: Month) = when (month) {
    is Month.January -> println("Number of days in January ${month.numberOfDays}")
    is Month.February -> println("Display name of February ${month.displayName}")
    is Month.March -> println("Number of days & Display name of March ${month.numberOfDays} && ${month.displayName}")
}

fun main() {
    val jan = Month.January(31)
    val feb = Month.February("Feb")

    eval(jan)
    eval(feb)
}