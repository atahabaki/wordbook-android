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

import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.adapters.WordAdapter
import dev.atahabaki.wordbook.data.settings.settingsDataStore
import dev.atahabaki.wordbook.data.word.Word
import dev.atahabaki.wordbook.databinding.FragmentListWordbookBinding
import dev.atahabaki.wordbook.utils.ItemListener
import dev.atahabaki.wordbook.utils.SwipeOperation
import dev.atahabaki.wordbook.utils.getSwipeOperation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlin.math.abs

@AndroidEntryPoint
class ListFragment: Fragment(R.layout.fragment_list_wordbook) {
    private var _binding: FragmentListWordbookBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by activityViewModels()

    private val wAdapter = WordAdapter(object: ItemListener<Word> {
        override fun onClick(view: View, data: Word) {
            super.onClick(view, data)
            if (view is AppCompatImageView)
                wordViewModel.update(data.copy(isFavorite = !data.isFavorite))
            else wordViewModel.onItemClicked(data)
            // else {} // TODO (*) Navigate to DetailFragment
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListWordbookBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordsList.apply {
            adapter = wAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        wordViewModel.words.observe(viewLifecycleOwner) {
            wAdapter.submitList(it)
            wAdapter.notifyDataSetChanged()
        }
        setupSwipeOperations()
    }

    private fun setupSwipeOperations() = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        fun delete(position: Int) {
            wordViewModel.onItemDeleted(wAdapter.currentList[position])
            wAdapter.notifyItemRemoved(position)
        }

        fun toggleFavorite(position: Int) {
            val previous = wAdapter.currentList[position]
            wordViewModel.insert(previous.copy(isFavorite = !previous.isFavorite))
            wAdapter.notifyItemChanged(position)
        }

        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                requireContext().settingsDataStore.data.first().apply {
                    // From RIGHT to LEFT...
                    if (direction == ItemTouchHelper.RIGHT) {
                        when(swipeLeftAction.getSwipeOperation()) {
                            SwipeOperation.DELETE -> delete(viewHolder.adapterPosition)
                            SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE ->
                                toggleFavorite(viewHolder.adapterPosition)
                        }
                    }
                    // From LEFT to RIGHT...
                    else if (direction == ItemTouchHelper.LEFT) {
                        when(swipeRightAction.getSwipeOperation()) {
                            SwipeOperation.DELETE -> delete(viewHolder.adapterPosition)
                            SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE ->
                                toggleFavorite(viewHolder.adapterPosition)
                        }
                    }
                }
            }
        }

        private fun getSwipeColor(compare: Int, @ColorRes default: Int): Int = when (compare) {
            SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE.value -> R.color.star_background
            SwipeOperation.DELETE.value -> R.color.trash_background
            else -> default
        }

        private fun getSwipeDrawable(compare: Int, @DrawableRes default: Int): Int = when(compare) {
            SwipeOperation.MARK_OR_UNMARK_AS_FAVORITE.value -> R.drawable.ic_star_filled
            SwipeOperation.DELETE.value -> R.drawable.ic_trash_all
            else -> default
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
               requireContext().settingsDataStore.data.first().apply {
                   if (dX > 0) {
                       val bg = ColorDrawable(ContextCompat.getColor(
                           viewHolder.itemView.context,
                           getSwipeColor(swipeLeftAction, R.color.trash_background)))
                       bg.setBounds(
                           viewHolder.itemView.left, viewHolder.itemView.top,
                           viewHolder.itemView.left + dX.toInt(), viewHolder.itemView.bottom
                       )
                       bg.draw(c)
                       val icon = ContextCompat.getDrawable(viewHolder.itemView.context,
                           getSwipeDrawable(swipeLeftAction, R.drawable.ic_trash_all))!!
                       icon.colorFilter = PorterDuffColorFilter(
                               ContextCompat.getColor(viewHolder.itemView.context, R.color.defaultThemeColor),
                               PorterDuff.Mode.SRC_IN)
                       val margin = (viewHolder.itemView.height - icon.intrinsicHeight)/2
                       icon.setBounds(
                           viewHolder.itemView.left + margin, viewHolder.itemView.top + margin,
                           viewHolder.itemView.left + margin + icon.intrinsicWidth,
                           viewHolder.itemView.bottom - margin
                       )
                       if (dX > margin + icon.intrinsicWidth)
                           icon.draw(c)
                   }
                   else if (dX < 0) {
                       val bg = ColorDrawable(ContextCompat.getColor(
                           viewHolder.itemView.context,
                           getSwipeColor(swipeRightAction, R.color.star_background)))
                       bg.setBounds(
                           viewHolder.itemView.right + dX.toInt(), viewHolder.itemView.top,
                           viewHolder.itemView.right, viewHolder.itemView.bottom
                       )
                       bg.draw(c)
                       val icon = ContextCompat.getDrawable(viewHolder.itemView.context,
                           getSwipeDrawable(swipeRightAction, R.drawable.ic_star_filled))!!
                       icon.colorFilter = PorterDuffColorFilter(
                               ContextCompat.getColor(viewHolder.itemView.context, R.color.defaultThemeColor),
                               PorterDuff.Mode.SRC_IN)
                       val margin = (viewHolder.itemView.height - icon.intrinsicHeight)/2
                       icon.setBounds(
                           viewHolder.itemView.right - margin - icon.intrinsicWidth,
                           viewHolder.itemView.top + margin,
                           viewHolder.itemView.right - margin,
                           viewHolder.itemView.bottom - margin
                       )
                       if (abs(dX) > margin + icon.intrinsicWidth)
                           icon.draw(c)
                   }
               }
            }
            super.onChildDraw(
                c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive
            )
        }
    }).attachToRecyclerView(binding.wordsList)
}