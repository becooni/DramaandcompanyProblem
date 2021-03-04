package com.becooni.dramaandcompanyproblem.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becooni.dramaandcompanyproblem.model.User
import com.becooni.dramaandcompanyproblem.ui.MainViewModel
import com.becooni.dramaandcompanyproblem.ui.UserAdapter

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("setUserAdapter", "setViewModel", requireAll = false)
    fun RecyclerView.setUserAdapter(items: List<User>?, viewModel: MainViewModel?) {
        val adapter = adapter as? UserAdapter? ?: UserAdapter().also {
            adapter = it
            it.viewModel = viewModel
        }
        adapter.submitList(items)
    }
}