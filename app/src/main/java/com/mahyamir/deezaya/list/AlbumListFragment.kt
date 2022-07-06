package com.mahyamir.deezaya.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahyamir.deezaya.databinding.FragmentAlbumListBinding
import com.mahyamir.deezaya.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumListFragment : Fragment() {

    private var binding by viewBinding<FragmentAlbumListBinding> {
        it.albumsRecyclerView.adapter = null
    }

    private val viewModel: AlbumListViewModel by viewModels()

    private val adapter = AlbumsAdapter(object : AlbumsAdapter.AlbumViewHolder.Callback {
        override fun onAlbumClick(id: String) {
            findNavController().navigate(AlbumListFragmentDirections.actionAlbumDetails(id))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlbumListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.albumsRecyclerView.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        binding.albumsRecyclerView.adapter = adapter

        viewModel.albumsUiState.observe(viewLifecycleOwner, ::uiStateObserver)
    }

    private fun uiStateObserver(uiState: AlbumListUiState) {
        lifecycleScope.launch {
            adapter.submitData(uiState.albums)
        }
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}