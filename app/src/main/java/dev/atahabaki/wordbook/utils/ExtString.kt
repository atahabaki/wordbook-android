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

fun String.toQFS(s: Sort = Sort.BY_ID_ASC): Triple<String, Filter, Sort> {
    var query = ""
    var sort = s
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
            Filter.SHOW_ONLY_FAV.searchQuery -> {
                filter = Filter.SHOW_ONLY_FAV
                addDeleteCommand()
            }
            Filter.SHOW_ONLY_NOT_FAV.searchQuery -> {
                filter = Filter.SHOW_ONLY_NOT_FAV
                addDeleteCommand()
            }
            Sort.BY_ID_ASC.searchQuery -> {
                sort = Sort.BY_ID_ASC
                addDeleteCommand()
            }
            Sort.BY_ID_DESC.searchQuery -> {
                sort = Sort.BY_ID_DESC
                addDeleteCommand()
            }
            Sort.BY_TITLE_ASC.searchQuery -> {
                sort = Sort.BY_TITLE_ASC
                addDeleteCommand()
            }
            Sort.BY_TITLE_DESC.searchQuery -> {
                sort = Sort.BY_TITLE_DESC
                addDeleteCommand()
            }
            Sort.BY_MEAN_ASC.searchQuery -> {
                sort = Sort.BY_MEAN_ASC
                addDeleteCommand()
            }
            Sort.BY_MEAN_DESC.searchQuery -> {
                sort = Sort.BY_MEAN_DESC
                addDeleteCommand()
            }
            Sort.BY_DATE_ASC.searchQuery -> {
                sort = Sort.BY_DATE_ASC
                addDeleteCommand()
            }
            Sort.BY_DATE_DESC.searchQuery -> {
                sort = Sort.BY_DATE_DESC
                addDeleteCommand()
            }
            Sort.BY_FAV_ASC.searchQuery -> {
                sort = Sort.BY_FAV_ASC
                addDeleteCommand()
            }
            Sort.BY_FAV_DESC.searchQuery -> {
                sort = Sort.BY_FAV_DESC
                addDeleteCommand()
            }
        }
    }
    for (index in toTrash) {
        list.removeAt(index)
    }
    query = list.joinToString(" ")

    return Triple(query, filter, sort)
}