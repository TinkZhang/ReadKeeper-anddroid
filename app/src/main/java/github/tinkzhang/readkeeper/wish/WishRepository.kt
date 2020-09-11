package github.tinkzhang.readkeeper.wish

import androidx.lifecycle.LiveData
import github.tinkzhang.readkeeper.common.WishBookDao

class WishRepository(private val wishBookDao: WishBookDao) {

    val allBooks: LiveData<List<WishBook>> = wishBookDao.getAll()

    suspend fun insert(book: WishBook) {
        wishBookDao.insertAll(book)
    }

    suspend fun delete(book: WishBook) {
        wishBookDao.delete(book)
    }

    companion object {
        @Volatile private var instance: WishRepository? = null

        fun getInstance(wishBookDao: WishBookDao) =
            instance ?: synchronized(this) {
                instance ?: WishRepository(wishBookDao).also { instance = it }
            }
    }
}