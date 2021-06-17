/*
 *  WordBook - An android application for those who aims to learn a new language.
 *  Copyright (C) 2021 A. Taha Baki
 *
 *  This file is part of WordBook.
 *
 *  WordBook is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WordBook is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WordBook.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.atahabaki.wordbook.utils

const val IS_FAV = "is:fav"
const val IS_NOT_FAV = "is:!fav"
const val SORT_ID = "sort:id"
const val SORT_ID_DESC = "sort:!id"
const val SORT_TITLE = "sort:title"
const val SORT_TITLE_DESC = "sort:!title"
const val SORT_MEAN = "sort:mean"
const val SORT_MEAN_DESC = "sort:!mean"
const val SORT_DATE = "sort:date"
const val SORT_DATE_DESC = "sort:!date"
const val SORT_FAV = "sort:fav"
const val SORT_FAV_DESC = "sort:!fav"

enum class Sort {
    BY_TITLE_ASC,
    BY_TITLE_DESC,
    BY_MEAN_ASC,
    BY_MEAN_DESC,
    BY_DATE_ASC,
    BY_DATE_DESC,
    BY_FAV_ASC,
    BY_FAV_DESC,
    BY_ID_ASC,
    BY_ID_DESC,
}

enum class Filter {
    SHOW_ONLY_FAV,
    SHOW_ONLY_NOT_FAV,
    SHOW_ALL
}

data class ListSFS(
    val query: String,
    val sorting: Sort,
    val filter: Filter
)

fun ListSFS.generateQuery(
        query: String = "",
        sorting: Sort = Sort.BY_FAV_DESC,
        filter: Filter = Filter.SHOW_ALL): String {
    var genQuery = """
                SELECT * FROM wordbook WHERE LOWER(title) LIKE '%${query}%'
                OR LOWER(meaning) LIKE '%${query}%' 
                """
    genQuery += when(filter) {
        Filter.SHOW_ONLY_FAV -> "AND is_favorite = 1 "
        Filter.SHOW_ONLY_NOT_FAV -> "AND is_favorite = 0 "
        else -> " "
    }
    genQuery += "ORDER BY "
    genQuery += when (sorting) {
        Sort.BY_FAV_DESC -> "is_favorite DESC"
        Sort.BY_FAV_ASC -> "is_favorite ASC"
        Sort.BY_TITLE_ASC -> "title ASC"
        Sort.BY_TITLE_DESC -> "title DESC"
        Sort.BY_MEAN_ASC -> "meaning ASC"
        Sort.BY_MEAN_DESC -> "meaning DESC"
        Sort.BY_DATE_ASC -> "createdAt ASC"
        Sort.BY_DATE_DESC -> "createdAt DESC"
        Sort.BY_ID_ASC -> "id ASC"
        Sort.BY_ID_DESC -> "id DESC"
    }
    return genQuery
}