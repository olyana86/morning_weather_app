package com.example.morningapp.utils

import androidx.compose.material3.CalendarLocale
import java.util.Calendar


fun getDateTodayForMorningScreen(): String {
    val calendar = Calendar.getInstance(CalendarLocale("RU"))
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    val dateToday = "$day ${monthIntToText(month)} $year г."
    return dateToday
}


fun getDayOfWeekForMorningScreen(): String {
    val dayOfWeekInt = getDayOfWeekFromDate().toInt()
    return dayOfWeekIntToText(dayOfWeekInt)
}


fun getDateToday(): String {
    val calendar = Calendar.getInstance(CalendarLocale("RU"))
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    val dateToday = "$year-$month-$day"

    return dateToday
}


fun getDayOfWeekFromDate(): String {
    val currentDate = getDateToday()
    val year: Int = currentDate.substringBefore('-').toInt()
    val month: Int = (currentDate.substringAfter('-')).substringBefore('-').toInt()
    val day = currentDate.substringAfterLast('-').toInt()

    val calendar = Calendar.getInstance(CalendarLocale("RU"))
    calendar.set(year, month, day)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    return dayOfWeek.toString()
}


fun monthIntToText(monthInt: Int): String {
    var monthText = ""
    when (monthInt) {
        0 -> monthText = "января"
        1 -> monthText = "февраля"
        2 -> monthText = "марта"
        3 -> monthText = "апреля"
        4 -> monthText = "мая"
        5 -> monthText = "июня"
        6 -> monthText = "июля"
        7 -> monthText = "августа"
        8 -> monthText = "сентября"
        9 -> monthText = "октября"
        10 -> monthText = "ноября"
        11 -> monthText = "декабря"
    }
    return monthText
}


fun dayOfWeekIntToText(dayOfWeekInt: Int): String {
    var dayOfWeekText = ""

    when (dayOfWeekInt) {
        1 -> dayOfWeekText = "воскресенье"
        2 -> dayOfWeekText = "понедельник"
        3 -> dayOfWeekText = "вторник"
        4 -> dayOfWeekText = "среда"
        5 -> dayOfWeekText = "четверг"
        6 -> dayOfWeekText = "пятница"
        7 -> dayOfWeekText = "суббота"
    }
    return dayOfWeekText
}
