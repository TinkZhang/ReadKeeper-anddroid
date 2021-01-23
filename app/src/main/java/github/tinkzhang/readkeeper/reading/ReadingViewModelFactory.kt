package github.tinkzhang.readkeeper.reading

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ReadingViewModelFactory(var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReadingViewModel(application) as T
    }
}