package github.tinkzhang.readkeeper.common.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.BasicBook

open class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(book: BasicBook, onClickListener: BookCardInteraction<BasicBook>) {
        configTitle(book)
        configItemView(book, onClickListener)
        configImageView(book, onClickListener)
        configAuthor(book)
        configRating(book)
        configPublicationYear(book)
    }


    private fun configPublicationYear(book: BasicBook) {
        if (book.originalPublicationYear > 0) {
            itemView.findViewById<TextView>(R.id.publish_time_textview).text =
                    book.originalPublicationYear.toString()
        }
    }

    private fun configRating(book: BasicBook) {
        if (book.rating > 0) {
            itemView.findViewById<TextView>(R.id.rating_textview).text =
                    itemView.context.getString(R.string.rate_text, book.rating)
        }
    }

    private fun configAuthor(book: BasicBook) {
        itemView.findViewById<TextView>(R.id.author_textview).text = book.author.trim()
    }

    private fun configTitle(book: BasicBook) {
        itemView.findViewById<TextView>(R.id.title_textview).text = book.title
    }

    private fun configImageView(book: BasicBook, onClickListener: BookCardInteraction<BasicBook>) {
        val imageView: ImageView = itemView.findViewById(R.id.book_cover_imageview)
        Glide.with(itemView).load(book.imageUrl).into(imageView)
        imageView.setOnLongClickListener {
            onClickListener.onImageLongClicked(itemView, book)
            true
        }
        imageView.setOnClickListener {
            onClickListener.onImageClicked(itemView, book)
        }
    }

    private fun configItemView(book: BasicBook, onClickListener: BookCardInteraction<BasicBook>) {
        itemView.setOnLongClickListener {
            itemView.isActivated = true
            onClickListener.onItemLongClicked(itemView, book)
            true
        }

        itemView.setOnClickListener {
            onClickListener.onItemClicked(itemView, book)
        }

        itemView.isActivated = false
    }
}