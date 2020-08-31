package github.tinkzhang.readkeeper.archive

import androidx.lifecycle.LiveData
import github.tinkzhang.readkeeper.common.ArchiveBookDao

class ArchiveRepository(private val archiveBookDao: ArchiveBookDao) {

    val allBooks: LiveData<List<ArchiveBook>> = archiveBookDao.getAll()

    suspend fun insert(book: ArchiveBook) {
        archiveBookDao.insertAll(book)
    }
}