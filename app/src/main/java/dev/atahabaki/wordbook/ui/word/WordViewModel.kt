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
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.data.word.WordRepository
import dev.atahabaki.wordbook.utils.Filter
import dev.atahabaki.wordbook.utils.Sort
import dev.atahabaki.wordbook.utils.generateQuery
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
    private val wordRepository: WordRepository
): ViewModel() {
    val query = MutableStateFlow("")
    val sort = MutableStateFlow(Sort.BY_FAV_DESC)
    val filter = MutableStateFlow(Filter.SHOW_ALL)

    @ExperimentalCoroutinesApi
    private val wordsFlow = combine(
        query,
        sort,
        filter
    ) { q, s, f ->
       Triple(q, s, f)
    }.flatMapLatest {
        wordRepository
            .getAllWords(SimpleSQLiteQuery(it.generateQuery()))
    }

    @ExperimentalCoroutinesApi
    private val words: LiveData<List<Word>> = wordsFlow.asLiveData()

    fun insert(word: Word) = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.insert(word)
    }

    fun update(word: Word) = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.update(word)
    }

    fun delete(word: Word) = CoroutineScope(Dispatchers.Main).launch {
        wordRepository.delete(word)
    }
}