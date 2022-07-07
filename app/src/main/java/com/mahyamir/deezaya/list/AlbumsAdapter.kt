package com.mahyamir.deezaya.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahyamir.deezaya.R
import com.mahyamir.deezaya.databinding.ListItemAlbumBinding
import com.mahyamir.deezaya.ui.loadImage

class AlbumsAdapter(private val callback: AlbumViewHolder.Callback) :
    PagingDataAdapter<Album, AlbumsAdapter.AlbumViewHolder>(AlbumsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            callback = callback,
            binding = ListItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Album -> holder.bind(item)

            else -> {
                val dataType = item?.let { item::class } ?: "NULL"
                // TODO Timber
                Log.e("AlbumsAdapter", "The following type of item is not supported! $dataType")
            }
        }
    }


    class AlbumViewHolder(
        private val callback: Callback,
        private val binding: ListItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            itemView.setOnClickListener { callback.onAlbumClick(album.id) }
            binding.apply {
                artistText.text = "By ${album.artistName}"
                titleText.text = album.name
            }
            binding.coverImage.clipToOutline = true
            loadImage(
                binding.coverImage,
                url = album.coverUrl,
                placeholder = R.drawable.album_placeholder
            )
        }

        interface Callback {
            fun onAlbumClick(id: String)
        }
    }
}

private class AlbumsDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}