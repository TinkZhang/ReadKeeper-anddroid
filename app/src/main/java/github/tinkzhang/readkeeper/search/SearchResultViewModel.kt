package github.tinkzhang.readkeeper.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.tinkzhang.readkeeper.archive.ArchiveBook
import github.tinkzhang.readkeeper.archive.ArchiveRepository
import github.tinkzhang.readkeeper.common.InjectorUtils
import github.tinkzhang.readkeeper.network.GoodreadsService
import github.tinkzhang.readkeeper.search.model.SearchBook
import github.tinkzhang.readkeeper.search.model.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultViewModel(context: Context) : ViewModel() {
    var books = MutableLiveData<List<SearchBook>>()
    var isLoading = MutableLiveData<Boolean>()

    private val archiveRepository : ArchiveRepository = InjectorUtils.getArchiveRepository(context)

    fun searchBook(keyword: String){
        viewModelScope.launch {
            try {
                isLoading.value = true
                val data = withContext(Dispatchers.IO){
                    GoodreadsService.instance.searchBook(keyword)
                }
                if (data.code() == 200) {
                    books.value = data.body()?.search?.results?.map {
                        it.convertToSearchBook()
                    }
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
        TODO("Not yet implemented")
    }

    fun addToWish(book: SearchBook) {
        TODO("Not yet implemented")
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
