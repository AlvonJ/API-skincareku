package com.bangkit.skincareku.view.main.CartPage

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.skincareku.networking.database.ItemCart

class ItemDiffCallback (
    private val oldList: List<ItemCart>,
    private val newList: List<ItemCart>
        ): DiffUtil.Callback() {

    override fun getOldListSize(): Int = newList.size
    override fun getNewListSize(): Int = oldList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition].id
        val latest = newList[newItemPosition].id
        return old == latest
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList == newList
}