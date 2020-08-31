package github.tinkzhang.readkeeper.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bumptech.glide.Glide
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.archive.ArchiveBook
import github.tinkzhang.readkeeper.common.AppDatabase
import github.tinkzhang.readkeeper.search.model.SearchBook
import kotlinx.coroutines.Dispatchers

class SearchBookListAdapter : ListAdapter<SearchBook, SearchItemViewHolder>(SearchItemViewHolder.BookDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchItemViewHolder(inflater.inflate(R.layout.item_search_book, parent, false))
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("ShowToast")
    fun bind(book: SearchBook) {
        itemView.findViewById<TextView>(R.id.title_textview).text = book.title

        Glide.with(itemView).load(book.imageUrl).into(
                itemView.findViewById<ImageView>(R.id.book_cover_imageview)
        )

        itemView.findViewById<TextView>(R.id.author_textview).text = book.author

        if (book.originalPublicationYear > 0) {
            itemView.findViewById<TextView>(R.id.publish_time_textview).text =
                book.originalPublicationYear.toString()
        }

        if (book.rating > 0) {
            itemView.findViewById<TextView>(R.id.rating_textview).text =
                itemView.context.getString(R.string.rate_text, book.rating)
        }

        itemView.findViewById<ImageButton>(R.id.add_button).setOnClickListener {
            MaterialDialog(itemView.context).show {
                listItemsSingleChoice(R.array.add_destinations) { dialog, index, text ->
                    val toastText = itemView.context.getString(R.string.add_to, book.title, text)
                    when(index) {
                        // TODO: add selected book to corresponding list
                        0 -> addedToArchive(itemView.context, ArchiveBook(book))
                    }
                    Toast.makeText(itemView.context, toastText, Toast.LENGTH_LONG).show()
                }
                positiveButton(R.string.add)
            }
        }
    }

    private fun addedToArchive(context: Context, book: ArchiveBook) = viewModelScope.launch(
        Dispatchers.IO) {
        AppDatabase.getDatabase(context).archiveBookDao().insertAll(book)
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