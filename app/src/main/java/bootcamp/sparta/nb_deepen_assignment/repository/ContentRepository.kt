package bootcamp.sparta.nb_deepen_assignment.repository

import bootcamp.sparta.nb_deepen_assignment.datasource.ContentClient
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageSearchResponse
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoSearchResponse
import retrofit2.Response
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

class ContentRepository @Inject constructor() {
    private val idGenerate = AtomicLong(1L)

    suspend fun searchImage(query: String, sort: String, page: Int, size: Int): Response<ImageSearchResponse> {
        return ContentClient.imageApi.searchImage(query = query, sort = sort, page= page, size = size)
    }

    suspend fun searchVideo(query: String, sort: String, page: Int, size: Int): Response<VideoSearchResponse> {
        return ContentClient.videoApi.searchVideo(query = query, sort= sort, page = page, size = size)
    }

    @JvmName("toContentDataListForImage")
    fun MutableList<ImageData>.toContentDataList(): MutableList<ContentData> {
        val list: MutableList<ContentData> = mutableListOf()
        for(i in this.indices) {
            val converter = ContentData(
                id = idGenerate.getAndIncrement(),
                dateTime = this[i].dateTime,
                thumbnail = this[i].thumbnail
            )
            list.add(converter)
        }
        return list
    }

    @JvmName("toContentDataListForVideo")
    fun MutableList<VideoData>.toContentDataList(): MutableList<ContentData> {
        val list: MutableList<ContentData> = mutableListOf()
        for(i in this.indices) {
            val converter = ContentData(
                id = idGenerate.getAndIncrement(),
                dateTime = this[i].dateTime,
                thumbnail = this[i].thumbnail
            )
            list.add(converter)
        }
        return list
    }
}
