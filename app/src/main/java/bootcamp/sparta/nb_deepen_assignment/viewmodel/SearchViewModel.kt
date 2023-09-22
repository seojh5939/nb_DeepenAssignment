package bootcamp.sparta.nb_deepen_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ContentRepository) : ViewModel() {
    private val _list: MutableLiveData<List<ContentData>> = MutableLiveData()
    val list get() = _list
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var query: MutableLiveData<String> = MutableLiveData()

    val searchedList = mutableListOf<ContentData>()
    private var pageableCountImage: Int = 1
    private var pageableCountVideo: Int = 1
    private var imgSearchRes = false
    private var videoSearchRes = false

    fun resultImageAndVideo(query: String, page: Int = 1) {
        imgSearchRes = false
        videoSearchRes = false
        loading.value = true

        // Image
        viewModelScope.launch {
            with(repository) {
                if (page <= pageableCountImage) {
                    val image = searchImage(query, "recency", page, 40)
                    with(image) {
                        if (isSuccessful) {
                            body()?.let { body ->
                                body.documents?.let { images -> searchedList.addAll(images.toContentDataList()) }
                                body.metaData?.pageableCount?.let { count ->
                                    pageableCountImage = count
                                }
                            }
                        }
                        imgSearchRes = true
                        checkResponse()
                    }
                }
            }
        }

        // Video
        viewModelScope.launch {
            with(repository) {
                val video = searchVideo(query, "recency", page, 40)
                with(video) {
                    if (isSuccessful) {
                        body()?.let { body ->
                            body.documents?.let { video -> searchedList.addAll(video.toContentDataList()) }
                            body.metaDAta?.pageableCount?.let { count ->
                                pageableCountVideo = count
                            }
                        }
                    }
                }
                videoSearchRes = true
                checkResponse()
            }
        }
    }


    fun modifySearchItem(position: Int? = null, item: ContentData) {
        fun searchIndex(item: ContentData): Int {
            val currentList = list.value.orEmpty().toMutableList()
            val selectedItem = currentList.find { it.id == item.id }

            return selectedItem?.let { currentList.indexOf(it) } ?: return -1
        }

        val currentList = list.value.orEmpty().toMutableList()
        val currentPosition = position ?: searchIndex(item)

        if (currentPosition == -1) {
            return
        }

        currentList[currentPosition] = item
        _list.value = currentList
    }

    private fun checkResponse() {
        if (imgSearchRes && videoSearchRes) {
            // sort
            searchedList.sortWith { o1, o2 -> o2.dateTime.compareTo(o1.dateTime) }

            _list.value = searchedList
            loading.value = false
        }
    }
}

class MainViewModelFactory(private val repository: ContentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(repository) as T
    }
}