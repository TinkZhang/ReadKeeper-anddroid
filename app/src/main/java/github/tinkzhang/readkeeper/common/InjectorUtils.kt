package github.tinkzhang.readkeeper.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import github.tinkzhang.readkeeper.archive.ArchiveRepository
import github.tinkzhang.readkeeper.search.SearchResultViewModel

object InjectorUtils {

    fun getArchiveRepository(context: Context) : ArchiveRepository {
        return ArchiveRepository.getInstance(AppDatabase.getDatabase(context).archiveBookDao())
    }

    fun provideSearchViewModelFactory(context: Context) : SearchViewModelFactory {
        return SearchViewModelFactory(context)
    }
}

class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchResultViewModel(context) as T
    }
}