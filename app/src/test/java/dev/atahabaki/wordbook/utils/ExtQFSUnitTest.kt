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
                OR LOWER(meaning) LIKE '%welcome%' ESCAPE '\')  ORDER BY id ASC
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
                OR LOWER(meaning) LIKE '%''%' ESCAPE '\')  ORDER BY is_favorite DESC
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
                OR LOWER(meaning) LIKE '%\%%' ESCAPE '\')  ORDER BY is_favorite DESC
        """.trimIndent())
    }
}