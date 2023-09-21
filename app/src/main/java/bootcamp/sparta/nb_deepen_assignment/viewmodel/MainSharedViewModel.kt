package bootcamp.sparta.nb_deepen_assignment.viewmodel

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

    fun addMyPageItem(item: ContentData){
        _myPageEvent.value = MainSharedEventForMyPage.AddMyPageContent(item)
    }

    fun removeMyPageItem(item: ContentData){
        _myPageEvent.value = MainSharedEventForMyPage.RemoveMyPageContent(item)
    }

}

sealed interface MainSharedEventForSearch {
    data class UpdateSearcedContent(
        val item: ContentData
    ): MainSharedEventForSearch
}

sealed interface MainSharedEventForMyPage {
    data class AddMyPageContent(
        val item: ContentData
    ): MainSharedEventForMyPage

    data class RemoveMyPageContent(
        val item: ContentData
    ): MainSharedEventForMyPage
}