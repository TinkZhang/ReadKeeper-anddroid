package github.tinkzhang.readkeeper.common

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import github.tinkzhang.readkeeper.archive.ArchiveBook

@Database(entities = arrayOf(ArchiveBook::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun archiveBookDao(): ArchiveBookDao

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
interface ArchiveBookDao {
    @Query("SELECT * FROM ArchiveBook ORDER BY addedTime ASC")
    fun getAll(): LiveData<List<ArchiveBook>>

    @Insert
    fun insertAll(vararg books: ArchiveBook)

    @Delete
    fun delete(book: ArchiveBook)
}
