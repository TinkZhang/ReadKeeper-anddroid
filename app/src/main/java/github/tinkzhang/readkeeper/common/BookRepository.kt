package github.tinkzhang.readkeeper.common

import androidx.lifecycle.LiveData

abstract class BookRepository<T: BasicBook>(bookDao: BookDao<T>) {
    val allBooks: LiveData<List<T>> = bookDao.getAll()

    abstract suspend fun insert(book: T)

    abstract suspend fun delete(book: T)
}