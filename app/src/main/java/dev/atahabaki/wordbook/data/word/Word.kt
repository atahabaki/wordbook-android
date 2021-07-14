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

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.atahabaki.wordbook.utils.DICTIONARY_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = DICTIONARY_TABLE_NAME)
@Parcelize
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val wordId: Int = 0,
    val title: String,
    val meaning: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable