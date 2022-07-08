package com.mahyamir.deezaya.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahyamir.core_data.AlbumDetailsDomainModel
import com.mahyamir.deezaya.R
import com.mahyamir.deezaya.databinding.FragmentAlbumDetailsBinding
import com.mahyamir.deezaya.ui.loadImage
import com.mahyamir.deezaya.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsFragment : Fragment() {

    private var binding by viewBinding<FragmentAlbumDetailsBinding>()

    private val viewModel: AlbumDetailsViewModel by viewModels()

    private val tracksAdapter = TracksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlbumDetailsBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.albumUiState.observe(viewLifecycleOwner, ::uiStateObserver)

        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksRecyclerView.adapter = tracksAdapter
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun uiStateObserver(state: AlbumDetailsUiState) {
        when (state) {
            is AlbumDetailsUiState.Loaded -> {
                setLayoutVisibility(state)
                fillView(state.details)
            }
            is AlbumDetailsUiState.Error -> {
                setLayoutVisibility(state)
            }
            is AlbumDetailsUiState.Loading -> {
                setLayoutVisibility(state)
            }
        }
    }

    private fun setLayoutVisibility(state: AlbumDetailsUiState) {
        binding.detailsLayout.isVisible = state is AlbumDetailsUiState.Loaded
        binding.errorIncluded.errorLayout.isVisible = state is AlbumDetailsUiState.Error
        binding.progressBar.isVisible = state is AlbumDetailsUiState.Loading
    }

    private fun fillView(details: AlbumDetailsDomainModel) {
        binding.apply {
            albumCoverImage.clipToOutline = true
            loadImage(
                view = albumCoverImage,
                url = details.coverUrl,
                placeholder = R.drawable.album_placeholder
            )
            titleText.text = details.title
            artistText.text = getString(R.string.by_artist).format(details.artistName)
            explicitImage.isVisible = details.isExplicit
            releasedOnText.text = getString(R.string.released_on).format(details.releaseDate)
            tracksAdapter.submitList(details.tracks)
            binding.shareButton.setOnClickListener {
                val clipboard =
                    requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(SHARE_BUTTON_LABEL, details.share)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(requireContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    companion object {
        private const val SHARE_BUTTON_LABEL = "share_label"
    }
}