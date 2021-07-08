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

enum class Filter(val value: Int) {
    SHOW_ALL(0),
    SHOW_ONLY_FAV(1),
    SHOW_ONLY_NOT_FAV(2)
}

fun Int.getFilter(): Filter = when(this) {
    Filter.SHOW_ALL.value -> Filter.SHOW_ALL
    Filter.SHOW_ONLY_FAV.value -> Filter.SHOW_ONLY_FAV
    Filter.SHOW_ONLY_NOT_FAV.value -> Filter.SHOW_ONLY_NOT_FAV
    else -> Filter.SHOW_ALL
}

fun Triple<String, Sort, Filter>.generateQuery() : String {
    var genQuery = """
                SELECT * FROM wordbook WHERE LOWER(title) LIKE '%${this.first}%'
                OR LOWER(meaning) LIKE '%${this.first}%' 
                """
    genQuery += when(this.third) {
        Filter.SHOW_ONLY_FAV -> "AND is_favorite = 1 "
        Filter.SHOW_ONLY_NOT_FAV -> "AND is_favorite = 0 "
        else -> " "
    }
    genQuery += "ORDER BY "
    genQuery += when (this.second) {
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

fun String.toQFS(): Triple<String, Sort, Filter> {
    var query = ""
    var sort = Sort.BY_ID_ASC
    var filter = Filter.SHOW_ALL

    val list = this.split(" ").filter { it.trim().isNotEmpty() }.toMutableList()
    var countOfDeletedEntries = 0
    val toTrash: MutableList<Int> = mutableListOf()
    for ((i,word) in list.withIndex()) {
        fun addDeleteCommand() {
            toTrash.add(i-countOfDeletedEntries)
            countOfDeletedEntries+=1
        }
        when (word) {
            IS_FAV -> {
                filter = Filter.SHOW_ONLY_FAV
                addDeleteCommand()
            }
            IS_NOT_FAV -> {
                filter = Filter.SHOW_ONLY_NOT_FAV
                addDeleteCommand()
            }
            SORT_ID -> {
                sort = Sort.BY_ID_ASC
                addDeleteCommand()
            }
            SORT_ID_DESC -> {
                sort = Sort.BY_ID_DESC
                addDeleteCommand()
            }
            SORT_TITLE -> {
                sort = Sort.BY_TITLE_ASC
                addDeleteCommand()
            }
            SORT_TITLE_DESC -> {
                sort = Sort.BY_TITLE_DESC
                addDeleteCommand()
            }
            SORT_MEAN -> {
                sort = Sort.BY_MEAN_ASC
                addDeleteCommand()
            }
            SORT_MEAN_DESC -> {
                sort = Sort.BY_MEAN_DESC
                addDeleteCommand()
            }
            SORT_DATE -> {
                sort = Sort.BY_DATE_ASC
                addDeleteCommand()
            }
            SORT_DATE_DESC -> {
                sort = Sort.BY_DATE_DESC
                addDeleteCommand()
            }
            SORT_FAV -> {
                sort = Sort.BY_FAV_ASC
                addDeleteCommand()
            }
            SORT_FAV_DESC -> {
                sort = Sort.BY_FAV_DESC
                addDeleteCommand()
            }
        }
    }
    for (index in toTrash) {
        list.removeAt(index)
    }
    query = list.joinToString(" ")

    return Triple(query, sort, filter)
}