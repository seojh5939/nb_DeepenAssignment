package bootcamp.sparta.nb_deepen_assignment.datasource

import bootcamp.sparta.nb_deepen_assignment.auth.KakaoAuth
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageSearchResponse
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageData
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageApi {
    @GET("v2/search/image")
    suspend fun searchImage(
        @Header("Authorization") apiKey: String = KakaoAuth.AUTH_HEADER,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): retrofit2.Response<ImageSearchResponse>
}