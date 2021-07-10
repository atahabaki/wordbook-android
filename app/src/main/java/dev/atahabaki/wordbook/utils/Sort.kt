package dev.atahabaki.wordbook.utils

enum class Sort(val value: Int, val searchQuery: String) {
    BY_TITLE_ASC(1, "sort:title"),
    BY_TITLE_DESC(2, "sort:!title"),
    BY_MEAN_ASC(3, "sort:mean"),
    BY_MEAN_DESC(4, "sort:!mean"),
    BY_DATE_ASC(5, "sort:date"),
    BY_DATE_DESC(6, "sort:!date"),
    BY_FAV_ASC(7, "sort:fav"),
    BY_FAV_DESC(0, "sort:!fav"),
    BY_ID_ASC(8, "sort:id"),
    BY_ID_DESC(9, "sort:!id")
}