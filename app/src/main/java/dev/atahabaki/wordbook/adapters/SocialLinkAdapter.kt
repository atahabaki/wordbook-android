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

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.atahabaki.wordbook.data.about.SocialLink
import dev.atahabaki.wordbook.databinding.SocialLinkItemBinding
import dev.atahabaki.wordbook.utils.ItemListener

class SocialLinkAdapter(
    val listener: ItemListener<SocialLink>
): ListAdapter<SocialLink, RecyclerView.ViewHolder>(SocialLinkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class SocialLinkViewHolder(
        private val binding: SocialLinkItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: SocialLink) {
            binding.apply {
                executePendingBindings()
            }
        }
    }

    class SocialLinkDiffCallback: DiffUtil.ItemCallback<SocialLink>() {
        override fun areContentsTheSame(old: SocialLink, new: SocialLink) = old.name == new.name

        override fun areItemsTheSame(old: SocialLink, new: SocialLink) = old.name == new.name
    }
}