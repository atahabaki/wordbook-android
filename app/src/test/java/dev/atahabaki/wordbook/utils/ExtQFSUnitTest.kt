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

class ExtQFSUnitTest {
    @Test
    fun `show all, order by id asc, welcome = returns correct SQL_QUERY_STRING`() {
        assertThat(Triple(
           "welcome",
           Filter.SHOW_ALL,
           Sort.BY_ID_ASC
        ).generateQuery()).isEqualTo("""
                SELECT * FROM wordbook WHERE (LOWER(title) LIKE '%welcome%' ESCAPE '\'
                OR LOWER(meaning) LIKE '%welcome%' ESCAPE '\') ORDER BY id ASC
        """.trimIndent())
    }

    @Test
    fun `show only fav, order by fav desc, well = returns correct SQL_QUERY_STRING`() {
        assertThat(Triple(
            "well",
            Filter.SHOW_ONLY_FAV,
            Sort.BY_FAV_DESC
        ).generateQuery()).isEqualTo("""
                SELECT * FROM wordbook WHERE (LOWER(title) LIKE '%well%' ESCAPE '\'
                OR LOWER(meaning) LIKE '%well%' ESCAPE '\') AND is_favorite = 1 ORDER BY is_favorite DESC
        """.trimIndent())
    }

    @Test
    fun `all, fav desc, escaping ' sql special chars on query`() {
        assertThat(Triple(
            "'",
            Filter.SHOW_ALL,
            Sort.BY_FAV_DESC
        ).generateQuery()).isEqualTo("""
                SELECT * FROM wordbook WHERE (LOWER(title) LIKE '%''%' ESCAPE '\'
                OR LOWER(meaning) LIKE '%''%' ESCAPE '\') ORDER BY is_favorite DESC
        """.trimIndent())
    }

    @Test
    fun `all, fav desc, escape (percent_sign) sql special chars on query`() {
        assertThat(Triple(
            "%",
            Filter.SHOW_ALL,
            Sort.BY_FAV_DESC
        ).generateQuery()).isEqualTo("""
                SELECT * FROM wordbook WHERE (LOWER(title) LIKE '%\%%' ESCAPE '\'
                OR LOWER(meaning) LIKE '%\%%' ESCAPE '\') ORDER BY is_favorite DESC
        """.trimIndent())
    }
}