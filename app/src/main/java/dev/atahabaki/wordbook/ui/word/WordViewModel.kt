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

package dev.atahabaki.wordbook.ui.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.atahabaki.wordbook.data.listqfs.PreferencesRepository
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.data.word.WordRepository
import dev.atahabaki.wordbook.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    val query = MutableStateFlow("")
    private val _eventsChannel = Channel<Events>()

    suspend fun updateQuery(q: String) {
        query.emit(q)
    }

    private val listQFS = preferencesRepository.readListQFS

    @ExperimentalCoroutinesApi
    private val wordsFlow = combine(
        query,
        listQFS
    ) { q, l ->
        Pair(q, l)
    }.flatMapLatest { (q, l) ->
        val triple = Triple(q, l.filter.getFilter(), l.sort.getSort())
        wordRepository
            .getAllWords(SimpleSQLiteQuery(triple.generateQuery()))
    }

    @ExperimentalCoroutinesApi
    val words: LiveData<List<Word>> = wordsFlow.asLiveData()

    fun insert(word: Word) = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.insert(word)
    }

    fun update(word: Word) = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.update(word)
    }

    fun delete(word: Word) = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.delete(word)
    }

    fun updateSort(sort: Sort) = CoroutineScope(Dispatchers.IO).launch {
        preferencesRepository.updateSort(sort)
    }

    fun updateFilter(filter: Filter) = CoroutineScope(Dispatchers.IO).launch {
        preferencesRepository.updateFilter(filter)
    }

    val eventFlow = _eventsChannel.receiveAsFlow()

    fun onItemDeleted(word: Word) = CoroutineScope(Dispatchers.IO).launch {
        delete(word)
        _eventsChannel.send(Events.ItemDeletedEvent(word))
    }

    fun onItemSaved(word: Word) = CoroutineScope(Dispatchers.IO).launch {
        insert(word)
        _eventsChannel.send(Events.ItemSavedEvent)
    }

    sealed class Events {
        data class ItemDeletedEvent(val word: Word): Events()
        object ItemSavedEvent: Events()
    }
}