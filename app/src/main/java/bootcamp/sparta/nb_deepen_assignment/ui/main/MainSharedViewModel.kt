package bootcamp.sparta.nb_deepen_assignment.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bootcamp.sparta.nb_deepen_assignment.model.ContentData

class MainSharedViewModel: ViewModel(){
    private val _searchedEvent: MutableLiveData<MainSharedEventForSearch> = MutableLiveData()
    val searchedEvent get() = _searchedEvent

    private val _myPageEvent: MutableLiveData<MainSharedEventForMyPage> = MutableLiveData()
    val myPageEvent get() = _myPageEvent

    fun updateSearchedItem(item: ContentData) {
        _searchedEvent.value = MainSharedEventForSearch.UpdateSearcedContent(item)
    }

    fun updateMyPageItems(items: List<ContentData>) {
        _myPageEvent.value = MainSharedEventForMyPage.UpdateMyPageContent(items)
    }

}

sealed interface MainSharedEventForSearch {
    data class UpdateSearcedContent(
        val item: ContentData
    ): MainSharedEventForSearch
}

sealed interface MainSharedEventForMyPage {
    data class UpdateMyPageContent(
        val items: List<ContentData>
    ): MainSharedEventForMyPage
}