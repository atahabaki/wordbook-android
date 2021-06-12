package dev.atahabaki.wordbook.utils

import android.view.View

interface ItemListener<T> {
    fun onClick(view: View, data: T) {}
}