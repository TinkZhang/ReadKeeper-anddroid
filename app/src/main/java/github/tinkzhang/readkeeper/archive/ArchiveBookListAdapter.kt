package github.tinkzhang.readkeeper.archive

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
import github.tinkzhang.readkeeper.search.OnItemClickListener

class ArchiveBookListAdapter(val onClickListener: OnItemClickListener) : ListAdapter<ArchiveBook, ArchiveItemViewHolder>(github.tinkzhang.readkeeper.archive.BookDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArchiveItemViewHolder(inflater.inflate(R.layout.item_archive_book, parent, false), onClickListener)

    }

    override fun onBindViewHolder(holder: ArchiveItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ArchiveItemViewHolder(itemView: View, val onClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("ShowToast")
    fun bind(book: ArchiveBook) {
        itemView.findViewById<TextView>(R.id.title_textview).text = book.title

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

class BookDiffCallback : DiffUtil.ItemCallback<ArchiveBook>() {

    override fun areItemsTheSame(oldItem: ArchiveBook, newItem: ArchiveBook): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ArchiveBook, newItem: ArchiveBook): Boolean {
        return oldItem == newItem
    }
}
