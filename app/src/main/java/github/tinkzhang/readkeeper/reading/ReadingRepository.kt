package github.tinkzhang.readkeeper.reading

import github.tinkzhang.readkeeper.common.BookRepository

class ReadingRepository(private val readingBookDao: ReadingBookDao)
    : BookRepository<ReadingBook>(readingBookDao) {

    companion object {
        @Volatile private var instance: ReadingRepository? = null

        fun getInstance(readingBookDao: ReadingBookDao) =
                instance ?: synchronized(this) {
                    instance ?: ReadingRepository(readingBookDao).also { instance = it }
                }
    }

    override suspend fun insert(book: ReadingBook) {
        readingBookDao.insertAll(book)
    }

    override suspend fun delete(book: ReadingBook) {
        readingBookDao.delete(book)
    }
}