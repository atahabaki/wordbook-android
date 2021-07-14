package dev.atahabaki.wordbook.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.databinding.FragmentEditBinding
import dev.atahabaki.wordbook.utils.WordValidity

class EditFragment: Fragment(R.layout.fragment_edit) {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val args: EditFragmentArgs by navArgs()
    private val viewModel: WordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
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
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            editTitle.setText(args.word.title)
            editMeaning.setText(args.word.meaning)
            editSaveFab.setOnClickListener {
                viewModel.onItemSaved(args.word.copy(
                    title = editTitle.text.toString(),
                    meaning = editMeaning.text.toString()
                ), true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}