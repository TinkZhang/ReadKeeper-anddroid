package github.tinkzhang.readkeeper.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.tinkzhang.readkeeper.archive.ArchiveBook
import github.tinkzhang.readkeeper.archive.ArchiveRepository
import github.tinkzhang.readkeeper.common.InjectorUtils
import github.tinkzhang.readkeeper.network.GoogleBookService
import github.tinkzhang.readkeeper.reading.ReadingBook
import github.tinkzhang.readkeeper.reading.ReadingRepository
import github.tinkzhang.readkeeper.search.model.SearchBook
import github.tinkzhang.readkeeper.search.model.Work
import github.tinkzhang.readkeeper.search.model.googlebook.Item
import github.tinkzhang.readkeeper.wish.WishBook
import github.tinkzhang.readkeeper.wish.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultViewModel(context: Context) : ViewModel() {
    var books = MutableLiveData<List<SearchBook>>()
    var isLoading = MutableLiveData<Boolean>()

    private val archiveRepository : ArchiveRepository = InjectorUtils.getArchiveRepository(context)
    private val wishRepository : WishRepository = InjectorUtils.getWishRepository(context)
    private val readingRepository : ReadingRepository = InjectorUtils.getReadingRepository(context)

    fun searchBook(keyword: String){
        viewModelScope.launch {
            try {
                isLoading.value = true
                val data = withContext(Dispatchers.IO){
//                    GoodreadsService.instance.searchBook(keyword)
                    GoogleBookService.instance.searchBook(keyword)
                }
                if (data.code() == 200) {
                    Log.d("Tink", data.body()?.totalItems.toString())
                    books.value = data.body()?.items?.map { it.convertToSearchBook() }
                    isLoading.value = false
                }
                Log.d("Tink", data.toString())
            }
            catch (e: Exception) {
                Log.d("Tink", e.toString())
                isLoading.value = false
            }
        }
    }

    fun addToReading(book: SearchBook) {
        viewModelScope.launch {
            readingRepository.insert(ReadingBook(book))
        }
    }

    fun addToWish(book: SearchBook) {
        viewModelScope.launch {
            wishRepository.insert(WishBook(book))
        }
    }

    fun addToArchive(book: SearchBook) {
        viewModelScope.launch {
            archiveRepository.insert(ArchiveBook(book))
        }
    }

}

private fun Work.convertToSearchBook(): SearchBook {
    return SearchBook(
        title = this.book.title,
        imageUrl = this.book.imageUrl,
        author = this.book.author.name,
        rating = this.averageRating.toDouble(),
        ratingsCount = this.ratingsCount,
        originalPublicationYear = this.originalPublicationYear
    )
}

private fun Item.convertToSearchBook(): SearchBook {
    return SearchBook(
        title = this.volumeInfo.title,
        imageUrl = this.volumeInfo.imageLinks?.thumbnail?.replace("http", "https") ?: "",
        author = this.volumeInfo.authors?.joinToString() ?: "",
        rating = this.volumeInfo.averageRating.toDouble(),
        ratingsCount = this.volumeInfo.ratingsCount,
        originalPublicationYear = this.volumeInfo.publishedDate.split('-').first().toInt()
    )
}
