package com.example.morningapp.ui.models

data class DayWish(val text: String)

val dayWishes = listOf(
    DayWish("Пусть этот день пройдёт отлично!"),
    DayWish("Замечательного тебе дня!"),
    DayWish("Пусть у тебя всё сегодня получится!"),
    DayWish("Пусть это будет отличный день!"),
    DayWish("Пусть этот день тебя порадует!"),
    DayWish("Самого плодотворного тебе дня!"),
    DayWish("Пусть всё пройдёт так, как ты хочешь!"),
    DayWish("Удачного тебе дня!"),
    DayWish("Пусть этот день будет радостным!"),
    DayWish("Пусть этот день принесёт тебе удачу!")
)

fun getDayWish(): DayWish {
    return dayWishes.random()
}

