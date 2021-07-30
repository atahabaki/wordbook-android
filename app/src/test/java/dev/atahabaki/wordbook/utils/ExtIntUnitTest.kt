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