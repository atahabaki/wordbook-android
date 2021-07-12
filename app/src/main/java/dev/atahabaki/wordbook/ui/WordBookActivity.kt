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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.data.listqfs.listQFSDataStore
import dev.atahabaki.wordbook.databinding.ActivityWordbookBinding
import dev.atahabaki.wordbook.ui.word.AddFragment
import dev.atahabaki.wordbook.ui.word.ListFragmentDirections
import dev.atahabaki.wordbook.ui.word.WordViewModel
import dev.atahabaki.wordbook.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.hypot

@AndroidEntryPoint
class WordBookActivity : AppCompatActivity() {

    private var _binding: ActivityWordbookBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels()

    private lateinit var _bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private val bottomSheetBehavior get() = _bottomSheetBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WordBook)
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_wordbook)

        lifecycleScope.launch {
            applicationContext.listQFSDataStore.data.first().apply {
                when (filter) {
                    Filter.SHOW_ALL.value -> binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_all).isChecked = true
                    Filter.SHOW_ONLY_FAV.value -> binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_only_fav).isChecked = true
                    Filter.SHOW_ONLY_NOT_FAV.value -> binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_only_not_fav).isChecked = true
                    else -> binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_all).isChecked = true
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.add_framer, AddFragment())
            }
        }

        lifecycleScope.launchWhenStarted {
            wordViewModel.eventFlow.collect { e ->
                when(e) {
                    is WordViewModel.Events.ItemDeletedEvent -> Snackbar.make(
                        binding.root,
                        getString(R.string.item_deleted, e.word.title),
                        Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo) {
                            wordViewModel.insert(e.word)
                        }.setAnchorView(binding.fab).show()
                    is WordViewModel.Events.ItemSavedEvent -> {
                        val imm = this@WordBookActivity
                            .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                        shrinkFab()
                    }
                }
            }
        }

        val searchMenu = binding.bottomAppBar.menu.findItem(R.id.list_menu_search)

        searchMenu.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                binding.apply {
                    fab.hide()
                    bottomAppBar.hideOnScroll = false
                }
                return true
            }
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                binding.apply {
                    fab.show()
                    bottomAppBar.hideOnScroll = true
                }
                return true
            }
        })

        val searchView = searchMenu.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                CoroutineScope(Main).launch {
                    applicationContext.listQFSDataStore.data.first().apply {
                        newText?.toQFS(sort.getSort()).apply {
                            this?.let {
                                wordViewModel.updateQuery(it.first)
                                wordViewModel.updateFilter(it.second)
                                wordViewModel.updateSort(it.third)
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Main).launch {
                    applicationContext.listQFSDataStore.data.first().apply {
                        query?.toQFS(sort.getSort()).apply {
                            this?.let {
                                wordViewModel.updateQuery(it.first)
                                wordViewModel.updateFilter(it.second)
                                wordViewModel.updateSort(it.third)
                            }
                        }
                    }
                }
                return true
            }
        })

        _bottomSheetBehavior = BottomSheetBehavior.from(binding.scrim)
        bottomSheetBehavior.apply {
            isHideable = true
            peekHeight = binding.bottomAppBar.height
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.fab.setOnClickListener {
            explodeFab()
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.bottom_nav_view).setupWithNavController(navController)

        binding.bottomNavView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_menu_settings -> {
                    it.onNavDestinationSelected(navController)
                    true
                }
                else -> true
            }
        }

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.list_menu_search -> {
                    true
                }
                R.id.list_menu_trash -> {
                    true
                }
                // SortBy..
                R.id.list_menu_sort_id_asc -> {
                    wordViewModel.updateSort(Sort.BY_ID_ASC)
                    true
                }
                R.id.list_menu_sort_id_desc -> {
                    wordViewModel.updateSort(Sort.BY_ID_DESC)
                    true
                }
                R.id.list_menu_sort_title_asc -> {
                    wordViewModel.updateSort(Sort.BY_TITLE_ASC)
                    true
                }
                R.id.list_menu_sort_title_desc -> {
                    wordViewModel.updateSort(Sort.BY_TITLE_DESC)
                    true
                }
                R.id.list_menu_sort_meaning_asc -> {
                    wordViewModel.updateSort(Sort.BY_MEAN_ASC)
                    true
                }
                R.id.list_menu_sort_meaning_desc -> {
                    wordViewModel.updateSort(Sort.BY_MEAN_DESC)
                    true
                }
                R.id.list_menu_sort_date_asc -> {
                    wordViewModel.updateSort(Sort.BY_DATE_ASC)
                    true
                }
                R.id.list_menu_sort_date_desc -> {
                    wordViewModel.updateSort(Sort.BY_DATE_DESC)
                    true
                }
                R.id.list_menu_sort_favorite_asc -> {
                    wordViewModel.updateSort(Sort.BY_FAV_ASC)
                    true
                }
                R.id.list_menu_sort_favorite_desc -> {
                    wordViewModel.updateSort(Sort.BY_FAV_DESC)
                    true
                }
                // Filters...
                R.id.list_menu_filter_show_all -> {
                    wordViewModel.updateFilter(Filter.SHOW_ALL)
                    binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_all).isChecked = true
                    true
                }
                R.id.list_menu_filter_show_only_fav -> {
                    wordViewModel.updateFilter(Filter.SHOW_ONLY_FAV)
                    binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_only_fav).isChecked = true
                    true
                }
                R.id.list_menu_filter_show_only_not_fav -> {
                    wordViewModel.updateFilter(Filter.SHOW_ONLY_NOT_FAV)
                    binding.bottomAppBar.menu
                        .findItem(R.id.list_menu_filter_show_only_not_fav).isChecked = true
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (binding.fabExplosionArea.visibility == View.VISIBLE) shrinkFab()
        else if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED ||
                bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        else super.onBackPressed()
    }

    private fun explodeFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var cx: Int
            var cy: Int
            binding.fab.apply {
                cx = left + width / 2
                cy = top + height / 2
            }
            binding.fabExplosionArea.apply {
                val finalRadius = hypot(
                    width.toDouble(),
                    height.toDouble()).toFloat()
                val anim = ViewAnimationUtils.createCircularReveal(
                    this, cx, cy,
                    0f, finalRadius)
                binding.fabExplosionArea.visibility = View.VISIBLE
                anim.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.addFramer.visibility = View.VISIBLE
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        binding.fab.hide()
                    }
                })
                anim.start()
            }
        }
        else {
            binding.fabExplosionArea.visibility = View.VISIBLE
            binding.addFramer.visibility = View.VISIBLE
            binding.fab.hide()
        }
    }

    private fun shrinkFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var cx: Int
            var cy: Int
            binding.fab.apply {
                cx = left + width / 2
                cy = top + binding.fab.height / 2
            }
            binding.fabExplosionArea.apply {
                val initialRadius = hypot(
                    width.toDouble(),
                    height.toDouble()
                ).toFloat()
                val anim = ViewAnimationUtils.createCircularReveal(
                    this, cx, cy,
                    initialRadius, 0f)
                anim.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.fabExplosionArea.visibility = View.INVISIBLE
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        binding.addFramer.visibility = View.INVISIBLE
                        binding.fab.show()
                    }
                })
                anim.start()
            }
        }
        else {
            binding.fabExplosionArea.visibility = View.INVISIBLE
            binding.addFramer.visibility = View.INVISIBLE
            binding.fab.show()
        }
    }
}