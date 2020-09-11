package github.tinkzhang.readkeeper.reading

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import github.tinkzhang.readkeeper.common.BookDao

@Dao
interface ReadingBookDao : BookDao<ReadingBook> {
    @Query("SELECT * FROM ReadingBook ORDER BY addedTime ASC")
    override fun getAll(): LiveData<List<ReadingBook>>

    @Insert
    override suspend fun insertAll(vararg books: ReadingBook)

    @Delete
    override suspend fun delete(book: ReadingBook)

}