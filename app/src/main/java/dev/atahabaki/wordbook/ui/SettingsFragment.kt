package dev.atahabaki.wordbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.data.settings.settingsDataStore
import dev.atahabaki.wordbook.databinding.FragmentSettingsBinding
import dev.atahabaki.wordbook.ui.word.WordViewModel
import dev.atahabaki.wordbook.utils.getSwipeOperation
import kotlinx.coroutines.flow.first

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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            requireContext().settingsDataStore.data.first().apply {
                binding.settingsSwipeToLeftSpinner.setSelection(
                    swipeLeftAction.getSwipeOperation().value
                )
                binding.settingsSwipeToRightSpinner.setSelection(
                    swipeRightAction.getSwipeOperation().value
                )
            }
        }
        binding.settingsSwipeToLeftSpinner
                .onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                wordViewModel.updateSwipeLeft(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
        binding.settingsSwipeToRightSpinner
            .onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                wordViewModel.updateSwipeRight(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }
}