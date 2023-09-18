package bootcamp.sparta.nb_deepen_assignment.datasource

import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContentClient {
    private const val BASE_URL = "https://dapi.kakao.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val imageApi by lazy {
        retrofit.create(ImageApi::class.java)
    }

    val videoApi by lazy {
        retrofit.create(VideoApi::class.java)
    }
}