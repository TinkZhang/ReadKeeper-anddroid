package github.tinkzhang.readkeeper.reading

import androidx.room.Entity
import androidx.room.PrimaryKey
import github.tinkzhang.readkeeper.common.BasicBook
import github.tinkzhang.readkeeper.common.BookType
import github.tinkzhang.readkeeper.search.model.SearchBook
import java.util.*

@Entity
data class ReadingBook(
        @PrimaryKey
        override var title: String = "",
        override var imageUrl: String = "",
        override var author: String = "",
        override var pages: Int = 0,
        override var addedTime: Long = 0,
        override var rating: Double = 0.0,
        var ratingsCount: Int = 0,
        override var originalPublicationYear: Int = 1900,
        var progress: Int = 0,
        var type: BookType? = null
) : BasicBook() {
    constructor(book: SearchBook) : this() {
        this.title = book.title
        this.imageUrl = book.imageUrl
        this.author = book.author
        this.pages = book.pages
        this.originalPublicationYear = book.originalPublicationYear
        this.ratingsCount = book.ratingsCount
        this.rating = book.rating
        this.addedTime = Calendar.getInstance().timeInMillis
    }
}