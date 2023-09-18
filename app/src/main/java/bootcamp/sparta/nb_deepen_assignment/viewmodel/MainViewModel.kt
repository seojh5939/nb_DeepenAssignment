package bootcamp.sparta.nb_deepen_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.ImageSearchResponse
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoSearchResponse
import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: ContentRepository): ViewModel(){
    val contentList: MutableLiveData<List<ContentData>> = MutableLiveData()

    fun resultImageAndVideo(query: String) {
        viewModelScope.launch {
            val list: MutableList<ContentData> = mutableListOf()
            val imageRes= repository.searchImage(query, "recency")
            val videoRes= repository.searchVideo(query, "recency")

            if(imageRes.isSuccessful && videoRes.isSuccessful) {
                imageRes.body()?.documents?.let {imageList ->
                    videoRes.body()?.documents?.let { videoList ->
                        list.addAll(videoList.toContentDataList())
                        list.addAll(imageList.toContentDataList())
                        list.sortWith(Comparator { o1, o2 -> o2.dateTime.compareTo(o1.dateTime) })
                        contentList.value = list
                    }
                }
            }
        }
    }

    @JvmName("toContentDataListForImage")
    private fun MutableList<ImageData>.toContentDataList(): MutableList<ContentData> {
        val list: MutableList<ContentData> = mutableListOf()
        for(i in this.indices) {
            val converter = ContentData(
                id = list.size,
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
                id = list.size,
                dateTime = this[i].dateTime,
                thumbnail = this[i].thumbnail
            )
            list.add(converter)
        }
        return list
    }
}

class MainViewModelFactory(private val repository: ContentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(repository) as T
    }
}