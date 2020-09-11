package github.tinkzhang.readkeeper.common.ui

import androidx.recyclerview.widget.DiffUtil
import github.tinkzhang.readkeeper.common.BasicBook

abstract class BookDiffCallback<T: BasicBook> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.title == newItem.title && oldItem.author == newItem.author
    }
}