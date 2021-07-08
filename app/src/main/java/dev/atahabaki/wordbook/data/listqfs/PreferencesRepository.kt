package dev.atahabaki.wordbook.data.listqfs

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
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
                    .setFilter(sort.value)
                    .build()
            }
        }
    }
}