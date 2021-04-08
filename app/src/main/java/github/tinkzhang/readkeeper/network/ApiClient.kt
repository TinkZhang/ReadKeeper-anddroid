package github.tinkzhang.readkeeper.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import github.tinkzhang.readkeeper.search.model.GoodreadsResponse
import github.tinkzhang.readkeeper.search.model.googlebook.GoogleBookDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val KEY = "H9ZQW1lAJm4SP8HUkhy2g"
private const val GOOGLE_BOOK_KEY = "AIzaSyDntV4wugC2oEewMD4RZ5tCDZ75ysTf300"

interface GoodreadsService {

    @GET("/search/index.xml")
    suspend fun searchBook(
        @Query("q") keyword: String,
        @Query("key") key: String = KEY)
            : Response<GoodreadsResponse>

    companion object {
        val instance: GoodreadsService by lazy {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.goodreads.com")
                .addConverterFactory(
                    TikXmlConverterFactory.create(
                        TikXml.Builder().exceptionOnUnreadXml(false).build()
                    )
                )
                .build()

            retrofit.create<GoodreadsService>()
        }
    }
}

interface GoogleBookService {
    @GET("books/v1/volumes")
    suspend fun searchBook(
        @Query("q") keyword: String,
        @Query("key") key: String = GOOGLE_BOOK_KEY) : Response<GoogleBookDto>

    companion object {
        val instance: GoogleBookService by lazy {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create()
        }
    }
}

