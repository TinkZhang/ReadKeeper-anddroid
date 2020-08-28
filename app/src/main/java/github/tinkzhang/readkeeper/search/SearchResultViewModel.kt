package github.tinkzhang.readkeeper.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.tinkzhang.readkeeper.network.GoodreadsService
import github.tinkzhang.readkeeper.search.model.SearchBook
import github.tinkzhang.readkeeper.search.model.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var books = MutableLiveData<List<SearchBook>>()


    fun searchBook(keyword: String){
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO){
                    GoodreadsService.instance.searchBook(keyword)
                }
                if (data.code() == 200) {
                    books.value = data.body()?.search?.results?.map {
                        it.convertToSearchBook()
                    }
                }
                Log.d("Tink", data.toString())
            }
            catch (e: Exception) {
                Log.d("Tink", e.toString())
            }
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
