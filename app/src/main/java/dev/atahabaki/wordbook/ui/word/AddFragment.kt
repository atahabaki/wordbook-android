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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.databinding.FragmentAddBinding
import dev.atahabaki.wordbook.utils.WordValidity

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        viewModel.events.observe(viewLifecycleOwner, {
            if (it is WordViewModel.Events.ItemInvalid) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.item_invalid)
                    .setMessage(when(it.reason) {
                        WordValidity.WORD_INVALID_TITLE_AND_MEAN_MISSING ->
                            R.string.title_meaning_missing
                        WordValidity.WORD_INVALID_TITLE_MISSING ->
                            R.string.title_missing
                        WordValidity.WORD_INVALID_MEAN_MISSING ->
                            R.string.meaning_missing
                    })
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
            if (it is WordViewModel.Events.ItemSavedEvent) {
                binding.apply {
                    addTitle.setText("")
                    addMeaning.setText("")
                }
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addSaveFab.setOnClickListener {
            viewModel.onItemSaved(
                Word(
                        title = binding.addTitle.text.toString(),
                        meaning = binding.addMeaning.text.toString()
                    )
                )
        }
    }
}