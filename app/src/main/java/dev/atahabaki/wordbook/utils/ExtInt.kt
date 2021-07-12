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

fun Int.getSwipeOperation(): SwipeOperation = when(this) {
    SwipeOperation.DELETE.value -> SwipeOperation.DELETE
    SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE.value -> SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE
    else -> SwipeOperation.DELETE
}