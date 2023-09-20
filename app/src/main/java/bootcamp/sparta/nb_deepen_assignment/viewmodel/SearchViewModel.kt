package bootcamp.sparta.nb_deepen_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.model.retrofit.VideoData
import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong

class SearchViewModel(private val repository: ContentRepository): ViewModel(){
    private val _list: MutableLiveData<List<ContentData>> = MutableLiveData()
    val list get() = _list

    fun resultImageAndVideo(query: String) {
        viewModelScope.launch {
            val result = repository.resultImageAndVideo(query, "recency")
            _list.value = result
        }
    }

    fun modifySearchItem(position: Int?= null, item: ContentData) {
        fun searchIndex(item: ContentData): Int {
            val currentList = list.value.orEmpty().toMutableList()
            val selectedItem = currentList.find { it.id == item.id }

            return selectedItem?.let { currentList.indexOf(it) } ?: return -1
        }

        val currentList = list.value.orEmpty().toMutableList()
        val currentPosition = position ?: searchIndex(item)

        if(currentPosition == -1) {
            return
        }

        currentList[currentPosition] = item
        _list.value = currentList
    }
}

class MainViewModelFactory(private val repository: ContentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(repository) as T
    }
}