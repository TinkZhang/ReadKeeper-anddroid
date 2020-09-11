package github.tinkzhang.readkeeper.archive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.BasicBook
import github.tinkzhang.readkeeper.common.ui.BookCardInteraction
import github.tinkzhang.readkeeper.common.ui.BookDiffCallback
import github.tinkzhang.readkeeper.common.ui.BookViewHolder

class ArchiveBookListAdapter(private val onClickListener: ArchiveCardInteraction)
    : ListAdapter<ArchiveBook, ArchiveItemViewHolder>(ArchiveBookDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArchiveItemViewHolder(
                inflater.inflate(R.layout.item_archive_book, parent, false),
                onClickListener)
    }

    override fun onBindViewHolder(holder: ArchiveItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ArchiveItemViewHolder(itemView: View, private val onClickListener: ArchiveCardInteraction)
    : BookViewHolder(itemView) {
    fun bind(book: ArchiveBook) {
        super.bind(book, onClickListener as BookCardInteraction<BasicBook>)
    }
}

class ArchiveBookDiffCallback : BookDiffCallback<ArchiveBook>() {
    override fun areContentsTheSame(oldItem: ArchiveBook, newItem: ArchiveBook): Boolean {
        return oldItem == newItem
    }
}
