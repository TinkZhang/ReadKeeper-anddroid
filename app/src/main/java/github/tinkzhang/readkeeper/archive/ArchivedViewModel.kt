package github.tinkzhang.readkeeper.archive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import github.tinkzhang.readkeeper.common.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchivedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArchiveRepository
    val books: LiveData<List<ArchiveBook>>
    var isLoading = MutableLiveData<Boolean>()

    init {
        val archiveBookDao = AppDatabase.getDatabase(application).archiveBookDao()
        repository = ArchiveRepository(archiveBookDao)
        books = repository.allBooks
        isLoading.value = false
    }

    fun insert(book: ArchiveBook) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(book)
    }

    fun delete(book: ArchiveBook) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(book)
    }

}