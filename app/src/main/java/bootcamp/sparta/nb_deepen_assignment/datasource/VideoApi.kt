package bootcamp.sparta.nb_deepen_assignment.datasource

import bootcamp.sparta.nb_deepen_assignment.auth.KakaoAuth
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageSearchResponse
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// Retrofit
interface VideoApi {
    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Header("Authorization") apiKey: String = KakaoAuth.AUTH_HEADER,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<VideoSearchResponse>
}