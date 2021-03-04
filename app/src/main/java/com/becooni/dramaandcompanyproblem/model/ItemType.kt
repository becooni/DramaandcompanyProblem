package com.becooni.dramaandcompanyproblem.model

import androidx.annotation.LayoutRes
import com.becooni.dramaandcompanyproblem.R

sealed class ItemType(@LayoutRes val layoutId: Int) {

    class Item(val item: User) : ItemType(LAYOUT_RES_ID_ITEM)

    class Header(val initial: String) : ItemType(LAYOUT_RES_ID_HEADER)

    companion object {
        const val LAYOUT_RES_ID_ITEM = R.layout.item_user

        const val LAYOUT_RES_ID_HEADER = R.layout.item_header
    }
}
