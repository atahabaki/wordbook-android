package dev.atahabaki.wordbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.data.word.WordDao
import dev.atahabaki.wordbook.utils.DATABASE_NAME

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}