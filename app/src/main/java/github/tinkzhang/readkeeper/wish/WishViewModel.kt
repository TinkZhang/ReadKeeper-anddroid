package github.tinkzhang.readkeeper.wish

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import github.tinkzhang.readkeeper.common.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WishRepository
    val books: LiveData<List<WishBook>>
    var isLoading = MutableLiveData<Boolean>()

    init {
        val wishBookDao = AppDatabase.getDatabase(application).wishBookDao()
        repository = WishRepository(wishBookDao)
        books = repository.allBooks
        isLoading.value = false
    }

    fun insert(book: WishBook) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(book)
    }

    fun delete(book: WishBook) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(book)
    }

}