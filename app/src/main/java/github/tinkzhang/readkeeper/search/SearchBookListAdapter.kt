package github.tinkzhang.readkeeper.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.search.model.SearchBook

class SearchBookListAdapter : ListAdapter<SearchBook, SearchItemViewHolder>(BookDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchItemViewHolder(inflater.inflate(R.layout.item_search_book, parent, false))
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(book: SearchBook) {
        itemView.findViewById<TextView>(R.id.title_textview).text = book.title
        Glide.with(itemView).load(book.imageUrl).into(
                itemView.findViewById<ImageView>(R.id.book_cover_imageview)
        )
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<SearchBook>() {

    override fun areItemsTheSame(oldItem: SearchBook, newItem: SearchBook): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: SearchBook, newItem: SearchBook): Boolean {
        return oldItem == newItem
    }
}