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

package dev.atahabaki.wordbook.data.word

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import dev.atahabaki.wordbook.data.AppDatabase
import dev.atahabaki.wordbook.utils.Filter
import dev.atahabaki.wordbook.utils.Sort
import dev.atahabaki.wordbook.utils.generateQuery
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WordDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: WordDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.wordDao()
    }

    @After
    fun closeDB() = db.close()

    @Test
    fun testInsertWord() = runBlockingTest {
        val word = Word(1, "Salut", "Hi", true)
        dao.insert(word)
        assertThat(dao.getAllWords(
                    SimpleSQLiteQuery(
                        Triple("", Filter.SHOW_ALL, Sort.BY_ID_ASC).generateQuery()
                    )
                ).first()[0]).isEqualTo(word)
    }

    @Test
    fun testDeleteWord()= runBlockingTest {
        val word = Word(1, "Salut", "Hi", true,
                System.currentTimeMillis())
        dao.insert(word)
        dao.delete(word)
        assertThat(dao.getAllWords(
                SimpleSQLiteQuery(
                    Triple("", Filter.SHOW_ALL, Sort.BY_ID_ASC).generateQuery()
                )
            ).first()).doesNotContain(word)
    }

    @Test
    fun testUpdate() = runBlockingTest {
        val word = Word(1, "Salut", "Hi", true,
            System.currentTimeMillis())
        dao.insert(word)
        val update = word.copy(meaning="greetings")
        dao.update(update)
        assertThat(dao.getAllWords(
            SimpleSQLiteQuery(
                Triple("", Filter.SHOW_ALL, Sort.BY_ID_ASC).generateQuery()
            )
        ).first()).apply {
            contains(update)
            doesNotContain(word)
        }
    }

    @Test
    fun testDeleteAll() = runBlockingTest {
        val word1 = Word(1, "Salut", "Hi", true,
            System.currentTimeMillis())
        val word2 = Word(2, "Bonjour", "Hello", true,
            System.currentTimeMillis())
        val word3 = Word(3, "Bonsoire", "Good night", true,
            System.currentTimeMillis())
        dao.apply {
            insert(word1)
            insert(word2)
            insert(word3)
            deleteAll()
            assertThat(getAllWords(
                        SimpleSQLiteQuery(
                            Triple("", Filter.SHOW_ALL, Sort.BY_ID_ASC).generateQuery()
                        )
                    ).first()).apply {
                        doesNotContain(word1)
                        doesNotContain(word2)
                        doesNotContain(word3)
                    }
        }
    }
}