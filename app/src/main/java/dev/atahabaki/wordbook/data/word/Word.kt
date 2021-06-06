package dev.atahabaki.wordbook.data.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.atahabaki.wordbook.utils.DICTIONARY_TABLE_NAME

@Entity(tableName = DICTIONARY_TABLE_NAME)
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val wordId: Int = 0,
    val title: String,
    val meaning: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    val createdAt: Long
)
