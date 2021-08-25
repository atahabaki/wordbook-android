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

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExtIntUnitTest {

    @Test
    fun `getSort(0) returns BY_FAV_DESC`() {
        assertThat(0.getSort()).isEqualTo(Sort.BY_FAV_DESC)
    }

    @Test
    fun `getSort(not in range) returns BY_FAV_DESC`() {
        assertThat(300.getSort()).isEqualTo(Sort.BY_FAV_DESC)
    }

    @Test
    fun `getFilter(0) returns SHOW_ALL`() {
        assertThat(0.getFilter()).isEqualTo(Filter.SHOW_ALL)
    }

    @Test
    fun `getFilter(not in range) returns SHOW_ALL`() {
        assertThat(300.getFilter()).isEqualTo(Filter.SHOW_ALL)
    }

    @Test
    fun `getSwipeOperation(0) returns DELETE`() {
        assertThat(0.getSwipeOperation()).isEqualTo(SwipeOperation.DELETE)
    }

    @Test
    fun `getSwipeOperation(1) returns STAR`() {
        assertThat(0.getSwipeOperation()).isEqualTo(SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE)
    }

    @Test
    fun `getSwipeOperation(not in range) returns DELETE`() {
        assertThat(300.getSwipeOperation()).isEqualTo(SwipeOperation.DELETE)
    }
}