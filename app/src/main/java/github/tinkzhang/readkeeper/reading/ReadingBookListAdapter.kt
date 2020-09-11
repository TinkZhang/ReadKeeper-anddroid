package github.tinkzhang.readkeeper.reading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.BasicBook
import github.tinkzhang.readkeeper.common.ui.BookCardInteraction
import github.tinkzhang.readkeeper.common.ui.BookDiffCallback
import github.tinkzhang.readkeeper.common.ui.BookViewHolder

class ReadingBookListAdapter(private val onClickListener: ReadingCardInteraction)
    : ListAdapter<ReadingBook, ReadingItemViewHolder>(ReadingBookDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReadingItemViewHolder(
                inflater.inflate(R.layout.item_reading_book, parent, false),
                onClickListener)
    }

    override fun onBindViewHolder(holder: ReadingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ReadingItemViewHolder(itemView: View, private val onClickListener: ReadingCardInteraction)
    : BookViewHolder(itemView) {
    fun bind(book: ReadingBook) {
        super.bind(book, onClickListener as BookCardInteraction<BasicBook>)
    }
}

class ReadingBookDiffCallback : BookDiffCallback<ReadingBook>() {
    override fun areContentsTheSame(oldItem: ReadingBook, newItem: ReadingBook): Boolean {
        return oldItem == newItem
    }
}