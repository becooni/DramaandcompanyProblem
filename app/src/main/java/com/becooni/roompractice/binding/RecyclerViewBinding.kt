package com.becooni.roompractice.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becooni.roompractice.model.ItemType
import com.becooni.roompractice.ui.MainViewModel
import com.becooni.roompractice.ui.UserAdapter

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("setUserAdapter", "setViewModel", requireAll = false)
    fun RecyclerView.setUserAdapter(items: List<ItemType>?, viewModel: MainViewModel?) {
        val adapter = adapter as? UserAdapter? ?: UserAdapter().also {
            adapter = it
            it.viewModel = viewModel
        }
        adapter.submitList(items)
    }
}