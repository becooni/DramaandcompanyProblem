package com.becooni.roompractice.model

import androidx.annotation.LayoutRes
import com.becooni.roompractice.R

sealed class ItemType(@LayoutRes val layoutId: Int) {

    class Item(val item: User) : ItemType(LAYOUT_RES_ID_ITEM)

    class Header(val initial: String) : ItemType(LAYOUT_RES_ID_HEADER)

    companion object {
        const val LAYOUT_RES_ID_ITEM = R.layout.item_user

        const val LAYOUT_RES_ID_HEADER = R.layout.item_header
    }
}
