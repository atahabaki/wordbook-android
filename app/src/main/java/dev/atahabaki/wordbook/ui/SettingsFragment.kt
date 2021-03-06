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
import android.widget.AutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.adapters.FilterFreeAdapter
import dev.atahabaki.wordbook.data.settings.settingsDataStore
import dev.atahabaki.wordbook.databinding.FragmentSettingsBinding
import dev.atahabaki.wordbook.ui.word.WordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
        val adapter = FilterFreeAdapter<String>(requireContext(),
                R.layout.swipe_setting_item,
                resources.getStringArray(R.array.swipe_operations))
        val dropDownBackground =
            ResourcesCompat.getDrawable(resources, R.drawable.dropdown_background, null)

        binding.settingsSwipeToRightComplete.setDropDownBackgroundDrawable(dropDownBackground)
        binding.settingsSwipeToRightComplete.setAdapter(adapter)
        binding.settingsSwipeToRightComplete
                .onItemClickListener = updateSwipeOptionClick(
                    false,
                    binding.settingsSwipeToRightComplete)

        binding.settingsSwipeToLeftComplete.setDropDownBackgroundDrawable(dropDownBackground)
        binding.settingsSwipeToLeftComplete.setAdapter(adapter)
        binding.settingsSwipeToLeftComplete
                .onItemClickListener = updateSwipeOptionClick(
                    true,
                    binding.settingsSwipeToLeftComplete)

        binding.settingsNotificationsPeriodComplete.apply {
            setDropDownBackgroundDrawable(dropDownBackground)
            val adapter = FilterFreeAdapter<String>(requireContext(),
                        R.layout.swipe_setting_item,
                        resources.getStringArray(R.array.notifications_periods))
            setAdapter(adapter)
            onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
                this.clearFocus()
                wordViewModel.updateNotificationsPeriod(i)
            }
        }
        
        binding.settingsNotificationsState.setOnCheckedChangeListener { _, isChecked ->
            wordViewModel.updateIsNotificationsDisabled(!isChecked)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            requireContext().settingsDataStore.data.collect {
                binding.settings = it
            }
        }
    }

    private fun updateSwipeOptionClick(isLeft: Boolean, focused: AutoCompleteTextView) =
        AdapterView.OnItemClickListener { _, _, i, _ ->
            focused.clearFocus()
            if (isLeft) wordViewModel.updateSwipeLeft(i)
            else wordViewModel.updateSwipeRight(i)
        }
}