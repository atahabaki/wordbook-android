package dev.atahabaki.wordbook.utils

fun Int.getSort(): Sort = when (this) {
    Sort.BY_TITLE_ASC.value -> Sort.BY_TITLE_ASC
    Sort.BY_TITLE_DESC.value -> Sort.BY_TITLE_DESC
    Sort.BY_MEAN_ASC.value -> Sort.BY_MEAN_ASC
    Sort.BY_MEAN_DESC.value -> Sort.BY_MEAN_DESC
    Sort.BY_DATE_ASC.value -> Sort.BY_DATE_ASC
    Sort.BY_DATE_DESC.value -> Sort.BY_DATE_DESC
    Sort.BY_FAV_ASC.value -> Sort.BY_FAV_ASC
    Sort.BY_FAV_DESC.value -> Sort.BY_FAV_DESC
    Sort.BY_ID_ASC.value -> Sort.BY_ID_ASC
    Sort.BY_ID_DESC.value -> Sort.BY_ID_DESC
    else -> Sort.BY_FAV_DESC
}

fun Int.getFilter(): Filter = when(this) {
    Filter.SHOW_ALL.value -> Filter.SHOW_ALL
    Filter.SHOW_ONLY_FAV.value -> Filter.SHOW_ONLY_FAV
    Filter.SHOW_ONLY_NOT_FAV.value -> Filter.SHOW_ONLY_NOT_FAV
    else -> Filter.SHOW_ALL
}