package github.tinkzhang.readkeeper.reading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import github.tinkzhang.readkeeper.common.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReadingRepository
    val books: LiveData<List<ReadingBook>>
    var isLoading = MutableLiveData<Boolean>()

    init {
        val readingBookDao = AppDatabase.getDatabase(application).readingBookDao()
        repository = ReadingRepository(readingBookDao)
        books = repository.allBooks
        isLoading.value = false
    }

    fun insert(book: ReadingBook) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(book)
    }

    fun delete(book: ReadingBook) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(book)
    }

}