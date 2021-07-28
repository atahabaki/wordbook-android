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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.adapters.SocialLinkAdapter
import dev.atahabaki.wordbook.data.about.SocialLink
import dev.atahabaki.wordbook.databinding.FragmentAboutBinding
import dev.atahabaki.wordbook.utils.ItemListener
import dev.atahabaki.wordbook.utils.SOCIAL_LINKS

@AndroidEntryPoint
class AboutFragment: Fragment(R.layout.fragment_about) {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val sAdapter = SocialLinkAdapter(object: ItemListener<SocialLink> {
        override fun onClick(view: View, data: SocialLink) {
            startActivity(Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(getString(data.uri))
            })
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content: Spanned = HtmlCompat.fromHtml(
            getString(R.string.app_description), HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.aboutDescription.apply{
            text = content
            movementMethod = LinkMovementMethod.getInstance()
        }
        sAdapter.submitList(SOCIAL_LINKS)
        binding.socialLinks.apply {
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                justifyContent = JustifyContent.SPACE_EVENLY
            }
            setHasFixedSize(true)
            adapter = sAdapter
        }
        sAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}