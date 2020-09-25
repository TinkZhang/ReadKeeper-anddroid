package github.tinkzhang.readkeeper.common

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromType(type: BookType?): String {
        return type?.name ?: ""
    }

    @TypeConverter
    fun toBookType(name: String) : BookType? =
        when {
            name.isNullOrEmpty() -> null
            else -> BookType.valueOf(name)
        }

}
