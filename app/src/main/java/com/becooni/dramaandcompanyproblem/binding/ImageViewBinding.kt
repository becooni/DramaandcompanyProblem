package com.becooni.dramaandcompanyproblem.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object ImageViewBinding {

    @JvmStatic
    @BindingAdapter("setImage")
    fun ImageView.setImage(imageUrl: String) {
        load(imageUrl)
    }
}