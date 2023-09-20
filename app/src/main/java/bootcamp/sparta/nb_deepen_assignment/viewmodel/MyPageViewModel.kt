package bootcamp.sparta.nb_deepen_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bootcamp.sparta.nb_deepen_assignment.model.ContentData

class MyPageViewModel: ViewModel() {
    private var _list = MutableLiveData<List<ContentData>>()
    val list get() = _list

    fun addItem(item: ContentData) {
        val currentList = list.value.orEmpty().toMutableList()
        currentList.add(item)
        _list.value = currentList
    }

    fun removeItem(position: Int? =null, item: ContentData) {
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

        currentList.removeAt(currentPosition)
        _list.value = currentList
    }

    fun updateMyPageItems(items: List<ContentData>) {
        _list.value = items
    }
}