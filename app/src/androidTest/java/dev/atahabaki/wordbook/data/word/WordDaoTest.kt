package dev.atahabaki.wordbook.data.word

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import dev.atahabaki.wordbook.data.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WordDaoTest {
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
}