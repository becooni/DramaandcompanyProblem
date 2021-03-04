package com.becooni.dramaandcompanyproblem.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becooni.dramaandcompanyproblem.databinding.ItemHeaderBinding
import com.becooni.dramaandcompanyproblem.databinding.ItemUserBinding
import com.becooni.dramaandcompanyproblem.model.ItemType
import com.becooni.dramaandcompanyproblem.model.User

class UserAdapter : ListAdapter<ItemType, RecyclerView.ViewHolder>(Companion) {

    internal var viewModel: MainViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.LAYOUT_RES_ID_ITEM -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            ItemType.LAYOUT_RES_ID_HEADER -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            else -> throw IllegalArgumentException("Unsupported type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ItemType.Item -> (holder as ItemViewHolder).bind(item.item)
            is ItemType.Header -> (holder as HeaderViewHolder).bind(item.initial)
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).layoutId

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserBinding.bind(itemView)

        fun bind(item: User) {
            binding.item = item
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemHeaderBinding.bind(itemView)

        fun bind(item: String) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object : DiffUtil.ItemCallback<ItemType>() {

        override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType) =
            if (oldItem is ItemType.Item && newItem is ItemType.Item) {
                oldItem.item.id == newItem.item.id
            } else if (oldItem is ItemType.Header && newItem is ItemType.Header) {
                oldItem.initial == newItem.initial
            } else {
                false
            }

        override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType) =
            if (oldItem is ItemType.Item && newItem is ItemType.Item) {
                oldItem == newItem
            } else if (oldItem is ItemType.Header && newItem is ItemType.Header) {
                oldItem == newItem
            } else {
                false
            }
    }
}