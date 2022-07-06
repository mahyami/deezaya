package com.mahyamir.deezaya.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mahyamir.deezaya.databinding.FragmentAlbumDetailsBinding
import com.mahyamir.deezaya.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsFragment : Fragment() {

    private var binding by viewBinding<FragmentAlbumDetailsBinding>()
    private val viewModel: AlbumDetailsViewModel by viewModels()
    private val albumId by lazy { AlbumDetailsFragmentArgs.fromBundle(requireArguments()).id }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlbumDetailsBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(albumId)
    }

}