package github.tinkzhang.readkeeper.archive

import androidx.lifecycle.LiveData
import github.tinkzhang.readkeeper.common.ArchiveBookDao

class ArchiveRepository(private val archiveBookDao: ArchiveBookDao) {

    val allBooks: LiveData<List<ArchiveBook>> = archiveBookDao.getAll()

    suspend fun insert(book: ArchiveBook) {
        archiveBookDao.insertAll(book)
    }

    suspend fun delete(book: ArchiveBook) {
        archiveBookDao.delete(book)
    }

    companion object {
        @Volatile private var instance: ArchiveRepository? = null

        fun getInstance(archiveBookDao: ArchiveBookDao) =
                instance ?: synchronized(this) {
                    instance ?: ArchiveRepository(archiveBookDao).also { instance = it }
                }
    }
}