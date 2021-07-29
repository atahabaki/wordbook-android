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
}