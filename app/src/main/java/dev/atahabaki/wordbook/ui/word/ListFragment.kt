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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.adapters.WordAdapter
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.databinding.FragmentListWordbookBinding
import dev.atahabaki.wordbook.utils.ItemListener

@AndroidEntryPoint
class ListFragment: Fragment(R.layout.fragment_list_wordbook) {
    private var _binding: FragmentListWordbookBinding? = null
    private val binding get() = _binding!!
    private val wAdapter = WordAdapter(object: ItemListener<Word> {
        override fun onClick(view: View, data: Word) {
            super.onClick(view, data)
            //TODO (1) implement item click...
        }
    })
    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListWordbookBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordsList.apply {
            adapter = wAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        wordViewModel.getAllWords().observe(viewLifecycleOwner) {
            wAdapter.submitList(it)
            wAdapter.notifyDataSetChanged()
        }
    }
}