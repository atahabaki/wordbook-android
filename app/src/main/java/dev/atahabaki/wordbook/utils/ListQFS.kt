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

fun Triple<String, Filter, Sort>.generateQuery() : String {
    var genQuery = """
                SELECT * FROM wordbook WHERE (LOWER(title) LIKE '%${this.first}%'
                OR LOWER(meaning) LIKE '%${this.first}%') 
                """
    genQuery += when(this.second) {
        Filter.SHOW_ONLY_FAV -> "AND is_favorite = 1 "
        Filter.SHOW_ONLY_NOT_FAV -> "AND is_favorite = 0 "
        else -> " "
    }
    genQuery += "ORDER BY "
    genQuery += when (this.third) {
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