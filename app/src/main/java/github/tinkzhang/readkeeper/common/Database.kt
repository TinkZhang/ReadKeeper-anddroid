package github.tinkzhang.readkeeper.common

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import github.tinkzhang.readkeeper.archive.ArchiveBook
import github.tinkzhang.readkeeper.wish.WishBook

@Database(entities = arrayOf(ArchiveBook::class, WishBook::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun archiveBookDao(): ArchiveBookDao
    abstract fun wishBookDao(): WishBookDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

@Dao
interface ArchiveBookDao : BookDao<ArchiveBook> {
    @Query("SELECT * FROM ArchiveBook ORDER BY addedTime ASC")
    override fun getAll(): LiveData<List<ArchiveBook>>

    @Insert
    override suspend fun insertAll(vararg books: ArchiveBook)

    @Delete
    override suspend fun delete(book: ArchiveBook)
}

@Dao
interface BookDao<T:BasicBook> {
    fun getAll(): LiveData<List<T>>

    @Insert
    suspend fun insertAll(vararg books: T)

    @Delete
    suspend fun delete(book: T)
}

@Dao
interface WishBookDao : BookDao<WishBook> {
    @Query("SELECT * FROM WishBook ORDER BY addedTime ASC")
    override fun getAll(): LiveData<List<WishBook>>

    @Insert
    override suspend fun insertAll(vararg books: WishBook)

    @Delete
    override suspend fun delete(book: WishBook)

}
