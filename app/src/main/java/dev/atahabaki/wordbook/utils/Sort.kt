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

enum class Sort(val value: Int, val searchQuery: String) {
    BY_TITLE_ASC(1, "sort:title"),
    BY_TITLE_DESC(2, "sort:!title"),
    BY_MEAN_ASC(3, "sort:mean"),
    BY_MEAN_DESC(4, "sort:!mean"),
    BY_DATE_ASC(5, "sort:date"),
    BY_DATE_DESC(6, "sort:!date"),
    BY_FAV_ASC(7, "sort:fav"),
    BY_FAV_DESC(0, "sort:!fav"),
    BY_ID_ASC(8, "sort:id"),
    BY_ID_DESC(9, "sort:!id")
}