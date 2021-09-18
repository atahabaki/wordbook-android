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

package dev.atahabaki.wordbook.data

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.atahabaki.wordbook.data.listqfs.ListQFS
import dev.atahabaki.wordbook.data.listqfs.listQFSDataStore
import dev.atahabaki.wordbook.data.settings.Settings
import dev.atahabaki.wordbook.data.settings.settingsDataStore
import dev.atahabaki.wordbook.utils.Filter
import dev.atahabaki.wordbook.utils.Sort
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val readListQFS: Flow<ListQFS> = context.listQFSDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e("${context.packageName}.readListQFS",exception.message.toString())
                    emit(ListQFS.getDefaultInstance())
                }
                else {
                    throw exception
                }
            }

    suspend fun updateFilter(filter: Filter)  {
        withContext(IO) {
            context.listQFSDataStore.updateData { curr ->
                curr.toBuilder()
                    .setFilter(filter.value)
                    .build()
            }
        }
    }

    suspend fun updateSort(sort: Sort)  {
        withContext(IO) {
            context.listQFSDataStore.updateData { curr ->
                curr.toBuilder()
                    .setSort(sort.value)
                    .build()
            }
        }
    }

    val readSettings: Flow<Settings>  = context.settingsDataStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e("${context.packageName}.readSettings", exception.message.toString())
            emit(Settings.getDefaultInstance())
        }
        else {
            throw  exception
        }
    }

    suspend fun updateSwipeRightOperation(operation: Int) {
        withContext(IO) {
            context.settingsDataStore.updateData { curr ->
                curr.toBuilder()
                    .setSwipeRightAction(operation)
                    .build()
            }
        }
    }

    suspend fun updateSwipLeftOperation(operation: Int) {
        withContext(IO) {
            context.settingsDataStore.updateData { curr ->
                curr.toBuilder()
                    .setSwipeLeftAction(operation)
                    .build()
            }
        }
    }

    suspend fun updateNotificationsPeriod(period: Int) = withContext(IO) {
        context.settingsDataStore.updateData { curr ->
            curr.toBuilder()
                .setNotificationsPeriod(period)
                .build()
        }
    }
}