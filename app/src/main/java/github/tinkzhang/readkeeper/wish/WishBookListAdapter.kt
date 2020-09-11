package github.tinkzhang.readkeeper.wish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.BasicBook
import github.tinkzhang.readkeeper.common.ui.BookCardInteraction
import github.tinkzhang.readkeeper.common.ui.BookDiffCallback
import github.tinkzhang.readkeeper.common.ui.BookViewHolder

class WishBookListAdapter(private val onClickListener: WishCardInteraction)
    : ListAdapter<WishBook, WishItemViewHolder>(WishBookDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WishItemViewHolder(
                inflater.inflate(R.layout.item_wish_book, parent, false),
                onClickListener)
    }

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class WishItemViewHolder(itemView: View, private val onClickListener: WishCardInteraction)
    : BookViewHolder(itemView) {
    fun bind(book: WishBook) {
        super.bind(book, onClickListener as BookCardInteraction<BasicBook>)
    }
}

class WishBookDiffCallback : BookDiffCallback<WishBook>() {
    override fun areContentsTheSame(oldItem: WishBook, newItem: WishBook): Boolean {
        return oldItem == newItem
    }
}
