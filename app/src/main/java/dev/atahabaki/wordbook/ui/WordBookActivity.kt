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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.databinding.ActivityWordbookBinding
import dev.atahabaki.wordbook.ui.word.WordViewModel
import dev.atahabaki.wordbook.utils.Filter
import dev.atahabaki.wordbook.utils.Sort

@AndroidEntryPoint
class WordBookActivity : AppCompatActivity() {

    private var _binding: ActivityWordbookBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WordBook)
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_wordbook)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.scrim)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.peekHeight = binding.bottomAppBar.height
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.bottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.scrim.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset <= 0.2f) binding.fab.show()
                else binding.fab.hide()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN ||
                        newState == BottomSheetBehavior.STATE_COLLAPSED)
                    binding.fab.show()
                else binding.fab.hide()
            }
        })

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.list_menu_search -> {
                    true
                }
                R.id.list_menu_trash -> {
                    true
                }
                // SortBy..
                R.id.list_menu_sort_title_asc -> {
                    wordViewModel.sort.value = Sort.BY_TITLE_ASC
                    true
                }
                R.id.list_menu_sort_title_desc -> {
                    wordViewModel.sort.value = Sort.BY_TITLE_DESC
                    true
                }
                R.id.list_menu_sort_meaning_asc -> {
                    wordViewModel.sort.value = Sort.BY_MEAN_ASC
                    true
                }
                R.id.list_menu_sort_meaning_desc -> {
                    wordViewModel.sort.value = Sort.BY_MEAN_DESC
                    true
                }
                R.id.list_menu_sort_date_asc -> {
                    wordViewModel.sort.value = Sort.BY_DATE_ASC
                    true
                }
                R.id.list_menu_sort_date_desc -> {
                    wordViewModel.sort.value = Sort.BY_DATE_DESC
                    true
                }
                R.id.list_menu_sort_favorite_asc -> {
                    wordViewModel.sort.value = Sort.BY_FAV_ASC
                    true
                }
                R.id.list_menu_sort_favorite_desc -> {
                    wordViewModel.sort.value = Sort.BY_FAV_DESC
                    true
                }
                // Filters...
                R.id.list_menu_filter_show_all -> {
                    wordViewModel.filter.value = Filter.SHOW_ALL
                    binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_all).isChecked = true
                    true
                }
                R.id.list_menu_filter_show_only_fav -> {
                    wordViewModel.filter.value = Filter.SHOW_ONLY_FAV
                    binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_only_fav).isChecked = true
                    true
                }
                R.id.list_menu_filter_show_only_not_fav -> {
                    wordViewModel.filter.value = Filter.SHOW_ONLY_NOT_FAV
                    binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_only_not_fav).isChecked = true
                    true
                }
                else -> false
            }
        }
    }
}