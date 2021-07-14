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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.atahabaki.wordbook.data.PreferencesRepository
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.data.word.WordRepository
import dev.atahabaki.wordbook.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    val query = MutableStateFlow("")
    private val _eventsChannel = MutableLiveData<Events>()
    val events: LiveData<Events> get() = _eventsChannel

    suspend fun updateQuery(q: String) {
        query.emit(q)
    }

    private val listQFS = preferencesRepository.readListQFS
    private val settings = preferencesRepository.readSettings

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

    fun deleteAll() = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.deleteAll()
    }

    fun updateSort(sort: Sort) = CoroutineScope(Dispatchers.IO).launch {
        preferencesRepository.updateSort(sort)
    }

    fun updateFilter(filter: Filter) = CoroutineScope(Dispatchers.IO).launch {
        preferencesRepository.updateFilter(filter)
    }

    fun updateSwipeRight(operation: Int) = CoroutineScope(Dispatchers.IO).launch {
        preferencesRepository.updateSwipeRightOperation(operation)
    }

    fun updateSwipeLeft(operation: Int) = CoroutineScope(Dispatchers.IO).launch {
        preferencesRepository.updateSwipLeftOperation(operation)
    }

    fun onItemDeleted(word: Word) {
        delete(word)
        _eventsChannel.value = Events.ItemDeletedEvent(word)
    }

    fun onItemSaved(word: Word, update: Boolean = false) {
        if (word.title.isBlank() && word.meaning.isBlank())
            _eventsChannel.value = Events.ItemInvalid(WordValidity.WORD_INVALID_TITLE_AND_MEAN_MISSING)
        else if (word.title.isBlank() || word.meaning.isBlank()) {
            if (word.title.isBlank())
                _eventsChannel.value = Events.ItemInvalid(WordValidity.WORD_INVALID_TITLE_MISSING)
            else if (word.meaning.isBlank())
                _eventsChannel.value = Events.ItemInvalid(WordValidity.WORD_INVALID_MEAN_MISSING)
        }
        else {
            if (update) update(word)
            else insert(word)
            _eventsChannel.value = Events.ItemSavedEvent
        }
    }

    fun onItemClicked(word: Word) {
        _eventsChannel.value = Events.ItemSelectedEvent(word)
    }

    sealed class Events {
        data class ItemDeletedEvent(val word: Word): Events()
        object ItemSavedEvent: Events()
        data class ItemSelectedEvent(val word: Word): Events()
        data class ItemInvalid(val reason: WordValidity): Events()
    }
}