package github.tinkzhang.readkeeper.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.search.model.SearchBook

class SearchBookListAdapter(val onClickListener: OnItemClickListener) : ListAdapter<SearchBook, SearchItemViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchItemViewHolder(inflater.inflate(R.layout.item_search_book, parent, false), onClickListener)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchItemViewHolder(itemView: View, val onClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("ShowToast")
    fun bind(book: SearchBook) {
        itemView.findViewById<TextView>(R.id.title_textview).text = book.title

        Glide.with(itemView).load(book.imageUrl).into(
                itemView.findViewById(R.id.book_cover_imageview)
        )
        itemView.findViewById<ImageView>(R.id.book_cover_imageview).apply {
            setOnLongClickListener {
                onClickListener.onItemImageLongClicked(book)
            }
        }

        itemView.findViewById<TextView>(R.id.author_textview).text = book.author.trim()

        if (book.originalPublicationYear > 0) {
            itemView.findViewById<TextView>(R.id.publish_time_textview).text =
                book.originalPublicationYear.toString()
        }

        if (book.rating > 0) {
            itemView.findViewById<TextView>(R.id.rating_textview).text =
                itemView.context.getString(R.string.rate_text, book.rating)
        }

        itemView.findViewById<ImageButton>(R.id.add_button).setOnClickListener {
            onClickListener.onAddButtonClicked(book)
        }
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


interface OnItemClickListener {
    fun onItemClicked(book: SearchBook)
    fun onItemImageLongClicked(book: SearchBook) : Boolean
    fun onAddButtonClicked(book: SearchBook)
}