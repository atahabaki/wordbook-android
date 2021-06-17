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