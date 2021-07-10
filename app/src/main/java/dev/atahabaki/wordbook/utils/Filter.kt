package dev.atahabaki.wordbook.utils

enum class Filter(val value: Int, val searchQuery: String) {
    SHOW_ALL(0, ""),
    SHOW_ONLY_FAV(1, "is:fav"),
    SHOW_ONLY_NOT_FAV(2, "is:!fav")
}