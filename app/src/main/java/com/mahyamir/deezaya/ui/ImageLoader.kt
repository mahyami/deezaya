package com.mahyamir.deezaya.ui

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun loadImage(view: ImageView, url: String?, @DrawableRes placeholder: Int) =
    Glide.with(view.context)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(view)