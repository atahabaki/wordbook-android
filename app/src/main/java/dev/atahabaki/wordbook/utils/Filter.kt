package dev.atahabaki.wordbook.utils

enum class Filter(val value: Int) {
    SHOW_ALL(0),
    SHOW_ONLY_FAV(1),
    SHOW_ONLY_NOT_FAV(2)
}