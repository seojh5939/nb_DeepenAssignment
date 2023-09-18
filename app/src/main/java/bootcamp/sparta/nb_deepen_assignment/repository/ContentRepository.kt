package bootcamp.sparta.nb_deepen_assignment.repository

import bootcamp.sparta.nb_deepen_assignment.datasource.ContentClient
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageSearchResponse
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoSearchResponse
import retrofit2.Response

class ContentRepository {
    suspend fun searchImage(query: String, sort: String): Response<ImageSearchResponse> {
        return ContentClient.imageApi.searchImage(query = query, sort = sort, page= 10, size = 10)
    }

    suspend fun searchVideo(query: String, sort: String): Response<VideoSearchResponse> {
        return ContentClient.videoApi.searchVideo(query = query, sort= sort, page = 10, size = 10)
    }
}