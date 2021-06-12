package dev.atahabaki.wordbook.utils

interface ItemListener<T> {
    fun onClick(data: T) {}
}