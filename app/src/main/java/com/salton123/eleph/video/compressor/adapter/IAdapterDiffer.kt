package com.salton123.eleph.video.compressor.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Time:2/9/21 3:07 PM
 * Author:
 * Description:
 */
interface IAdapterDiffer {
    fun <T> RecyclerView.Adapter<*>.onDiff(
        old: MutableList<T>,
        new: MutableList<T>,
        compareItems: (T, T) -> Boolean,
        compareContents: (T, T) -> Boolean
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compareItems(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compareContents(old[oldItemPosition], new[newItemPosition])
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })
        diff.dispatchUpdatesTo(this)
    }
}