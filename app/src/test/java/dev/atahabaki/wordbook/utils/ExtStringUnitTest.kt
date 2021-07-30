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