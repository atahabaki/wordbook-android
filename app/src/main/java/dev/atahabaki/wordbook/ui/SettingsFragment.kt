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

package dev.atahabaki.wordbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.databinding.FragmentSettingsBinding
import dev.atahabaki.wordbook.ui.word.WordViewModel

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter(requireContext(),
                R.layout.swipe_setting_item,
                resources.getStringArray(R.array.swipe_operations))
        binding.settingsSwipeToRightComplete.setAdapter(adapter)
        binding.settingsSwipeToRightComplete.onItemSelectedListener = updateSwipeOption(false)
        binding.settingsSwipeToRightComplete.onItemClickListener = updateSwipeOptionClick(false)

        binding.settingsSwipeToLeftComplete.setAdapter(adapter)
        binding.settingsSwipeToLeftComplete.onItemSelectedListener = updateSwipeOption(true)
        binding.settingsSwipeToLeftComplete.onItemClickListener = updateSwipeOptionClick(true)
    }

    private fun updateSwipeOption(isLeft: Boolean) = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (isLeft) wordViewModel.updateSwipeLeft(position)
            else wordViewModel.updateSwipeRight(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) = Unit
    }
    private fun updateSwipeOptionClick(isLeft: Boolean) = object: AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if (isLeft) wordViewModel.updateSwipeLeft(p2)
            else wordViewModel.updateSwipeRight(p2)
        }
    }
}