package com.becooni.roompractice.binding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object ViewBinding {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun View.isVisible(visible: Boolean) {
        isVisible = visible
    }
}