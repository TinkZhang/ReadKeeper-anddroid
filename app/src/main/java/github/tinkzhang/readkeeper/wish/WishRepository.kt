package github.tinkzhang.readkeeper.wish

import github.tinkzhang.readkeeper.common.BookRepository
import github.tinkzhang.readkeeper.common.WishBookDao

class WishRepository(private val wishBookDao: WishBookDao): BookRepository<WishBook>(wishBookDao) {

    override suspend fun insert(book: WishBook) {
        wishBookDao.insertAll(book)
    }

    override suspend fun delete(book: WishBook) {
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