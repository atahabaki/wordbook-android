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

package dev.atahabaki.wordbook.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import dev.atahabaki.wordbook.data.AppDatabase
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.data.word.WordRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ReminderWorker(
    private val ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params) {
    companion object {
        val CHANNEL_ID = "REMINDER_ID"
    }

    override suspend fun doWork(): Result {
        val wordDao = AppDatabase.getInstance(applicationContext).wordDao()
        val wordRepo = WordRepository(wordDao)
        val word: Word = withContext(IO) {
            wordRepo.getRandomWord().first()
        }
        return try {
            Log.d("Worker", "$word")
            ListenableWorker.Result.success()
        } catch (e: Exception) {
            ListenableWorker.Result.failure()
        }
    }
}