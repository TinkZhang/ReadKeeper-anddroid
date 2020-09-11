package github.tinkzhang.readkeeper.common.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar
import github.tinkzhang.readkeeper.common.BasicBook

interface BookCardInteraction<T: BasicBook> {
    fun onItemClicked(view: View, book: T) {
        Snackbar.make(view, book.title, Snackbar.LENGTH_LONG).show()
    }
    fun onItemLongClicked(view: View, book: T) {
        Snackbar.make(view, book.title, Snackbar.LENGTH_LONG).show()
    }
    fun onImageLongClicked(view: View, book: T) {
        Snackbar.make(view, book.title, Snackbar.LENGTH_LONG).show()
    }
    fun onImageClicked(view: View, book: T) {
        Snackbar.make(view, book.title, Snackbar.LENGTH_LONG).show()
    }
}