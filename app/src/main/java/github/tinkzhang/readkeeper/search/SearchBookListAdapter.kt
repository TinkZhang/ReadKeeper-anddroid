package github.tinkzhang.readkeeper.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.ListAdapter
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.BasicBook
import github.tinkzhang.readkeeper.common.ui.BookCardInteraction
import github.tinkzhang.readkeeper.common.ui.BookDiffCallback
import github.tinkzhang.readkeeper.common.ui.BookViewHolder
import github.tinkzhang.readkeeper.search.model.SearchBook

class SearchBookListAdapter(private val onClickListener: SearchCardInteraction)
    : ListAdapter<SearchBook, SearchItemViewHolder>(SearchBookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchItemViewHolder(inflater.inflate(R.layout.item_search_book, parent, false), onClickListener)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchItemViewHolder(itemView: View, private val onClickListener: SearchCardInteraction)
    : BookViewHolder(itemView) {
    fun bind(book: SearchBook) {
        super.bind(book, onClickListener as BookCardInteraction<BasicBook>)

        itemView.findViewById<ImageButton>(R.id.add_button).setOnClickListener {
            onClickListener.onAddButtonClicked(book)
        }
    }
}

class SearchBookDiffCallback : BookDiffCallback<SearchBook>() {
    override fun areContentsTheSame(oldItem: SearchBook, newItem: SearchBook): Boolean {
        return oldItem == newItem
    }
}