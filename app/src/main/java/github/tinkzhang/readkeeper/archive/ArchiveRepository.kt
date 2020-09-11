package github.tinkzhang.readkeeper.archive

import github.tinkzhang.readkeeper.common.ArchiveBookDao
import github.tinkzhang.readkeeper.common.BookRepository

class ArchiveRepository(private val archiveBookDao: ArchiveBookDao): BookRepository<ArchiveBook>(archiveBookDao) {

    companion object {
        @Volatile private var instance: ArchiveRepository? = null

        fun getInstance(archiveBookDao: ArchiveBookDao) =
                instance ?: synchronized(this) {
                    instance ?: ArchiveRepository(archiveBookDao).also { instance = it }
                }
    }

    override suspend fun insert(book: ArchiveBook) {
        archiveBookDao.insertAll(book)
    }

    override suspend fun delete(book: ArchiveBook) {
        archiveBookDao.delete(book)
    }
}