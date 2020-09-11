package github.tinkzhang.readkeeper.wish

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.tinkzhang.readkeeper.R

class WishBookListAdapter(val onClickListener: OnItemClickListener) : ListAdapter<WishBook, WishItemViewHolder>(BookDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WishItemViewHolder(inflater.inflate(R.layout.item_wish_book, parent, false), onClickListener)

    }

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class WishItemViewHolder(itemView: View, val onClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("ShowToast")
    fun bind(book: WishBook) {
        itemView.findViewById<TextView>(R.id.title_textview).text = book.title

        itemView.setOnLongClickListener {
            itemView.isActivated = true
            onClickListener.onItemLongClicked(itemView, book)
            true
        }

        itemView.isActivated = false

        Glide.with(itemView).load(book.imageUrl).into(
                itemView.findViewById(R.id.book_cover_imageview)
        )

        itemView.findViewById<TextView>(R.id.author_textview).text = book.author.trim()

        if (book.originalPublicationYear > 0) {
            itemView.findViewById<TextView>(R.id.publish_time_textview).text =
                    book.originalPublicationYear.toString()
        }

        if (book.rating > 0) {
            itemView.findViewById<TextView>(R.id.rating_textview).text =
                    itemView.context.getString(R.string.rate_text, book.rating)
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<WishBook>() {

    override fun areItemsTheSame(oldItem: WishBook, newItem: WishBook): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: WishBook, newItem: WishBook): Boolean {
        return oldItem == newItem
    }
}

interface OnItemClickListener {
    fun onItemClicked(view: View, book: WishBook)
    fun onItemLongClicked(view: View, book: WishBook)
    fun onItemImageLongClicked(book: WishBook) : Boolean
    fun onAddButtonClicked(book: WishBook)
}
