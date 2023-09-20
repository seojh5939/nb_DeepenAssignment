package bootcamp.sparta.nb_deepen_assignment.repository

import bootcamp.sparta.nb_deepen_assignment.datasource.ContentClient
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageSearchResponse
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoSearchResponse
import retrofit2.Response
import java.util.concurrent.atomic.AtomicLong

class ContentRepository {
    private val idGenerate = AtomicLong(1L)

    private suspend fun searchImage(query: String, sort: String): Response<ImageSearchResponse> {
        return ContentClient.imageApi.searchImage(query = query, sort = sort, page= 10, size = 10)
    }

    private suspend fun searchVideo(query: String, sort: String): Response<VideoSearchResponse> {
        return ContentClient.videoApi.searchVideo(query = query, sort= sort, page = 10, size = 10)
    }

    suspend fun resultImageAndVideo(query: String, sort: String): MutableList<ContentData> {
        val list: MutableList<ContentData> = mutableListOf()
        val image = searchImage(query, sort)
        val video = searchVideo(query, sort)

        if(image.isSuccessful) {
            image.body()?.documents?.let {imageList ->
                list.addAll(imageList.toContentDataList())
            }
        }

        if(video.isSuccessful) {
            video.body()?.documents?.let { videoList ->
                list.addAll(videoList.toContentDataList())
            }
        }

        // sort
        list.sortWith { o1, o2 -> o2.dateTime.compareTo(o1.dateTime) }

        return list
    }

    @JvmName("toContentDataListForImage")
    private fun MutableList<ImageData>.toContentDataList(): MutableList<ContentData> {
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
    private fun MutableList<VideoData>.toContentDataList(): MutableList<ContentData> {
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
