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

class ExtStringUnitTest {

    @Test
    fun `is(colon)fav sort(colon)id well returns the expected QFS`() {
        assertThat(
            "is:fav sort:id well".toQFS()
        ).isEqualTo(Triple("well", Filter.SHOW_ONLY_FAV, Sort.BY_ID_ASC))
    }

    @Test
    fun `care only about the last statement`() {
        assertThat(
            "is:fav is:!fav sort:id sort:fav well".toQFS()
        ).isEqualTo(Triple("well", Filter.SHOW_ONLY_NOT_FAV, Sort.BY_FAV_ASC))
    }
}