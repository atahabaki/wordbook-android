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

package dev.atahabaki.wordbook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.databinding.WordItemBinding

class WordAdapter: ListAdapter<Word, RecyclerView.ViewHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordViewHolder(
        WordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WordViewHolder).bind(getItem(position))
    }

    inner class WordViewHolder(
        private val binding: WordItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Word) {
            binding.apply {
                word = entry
                executePendingBindings()
            }
        }
    }

    class WordDiffCallback: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word) = oldItem.wordId == newItem.wordId

        override fun areContentsTheSame(oldItem: Word, newItem: Word) = oldItem == newItem
    }
}