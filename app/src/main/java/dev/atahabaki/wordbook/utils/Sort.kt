package dev.atahabaki.wordbook.utils

enum class Sort(val value: Int) {
    BY_TITLE_ASC(1),
    BY_TITLE_DESC(2),
    BY_MEAN_ASC(3),
    BY_MEAN_DESC(4),
    BY_DATE_ASC(5),
    BY_DATE_DESC(6),
    BY_FAV_ASC(7),
    BY_FAV_DESC(0),
    BY_ID_ASC(8),
    BY_ID_DESC(9)
}