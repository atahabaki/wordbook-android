package dev.atahabaki.wordbook.data.word

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepository @Inject constructor(
    private val wordDao: WordDao
) {
    fun getAllWords() = wordDao.getAllWords()

    suspend fun insert(word: Word) = wordDao.insert(word)

    suspend fun update(word: Word) = wordDao.update(word)

    suspend fun delete(word: Word) = wordDao.delete(word)
}