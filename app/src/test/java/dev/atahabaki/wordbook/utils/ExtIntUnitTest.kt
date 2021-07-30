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
}