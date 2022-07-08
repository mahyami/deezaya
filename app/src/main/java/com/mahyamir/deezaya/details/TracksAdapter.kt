package com.mahyamir.deezaya.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahyamir.core_data.model.Track
import com.mahyamir.deezaya.R
import com.mahyamir.deezaya.databinding.ListItemTrackBinding
import com.mahyamir.deezaya.ui.loadImage

class TracksAdapter : ListAdapter<Track, TracksAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder =
        TrackViewHolder(
            ListItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Track -> holder.bind(item)

            else -> {
                val dataType = item?.let { item::class } ?: "NULL"
                Log.e("AlbumsAdapter", "The following type of item is not supported! $dataType")
            }
        }
    }

    class TrackViewHolder(private val binding: ListItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.coverImage.clipToOutline = true
        }

        fun bind(track: Track) {
            loadImage(binding.coverImage, track.coverUrl, R.drawable.music_placeholder)
            binding.titleText.text = track.name
        }
    }
}

private class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}