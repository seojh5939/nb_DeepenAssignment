package bootcamp.sparta.nb_deepen_assignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.utils.Utils

class MyPageViewModel(application: Application) : AndroidViewModel(application) {
    private var _list = MutableLiveData<List<ContentData>>()
    val list get() = _list

    fun addItem(item: ContentData) {
        val current = list.value.orEmpty().toMutableList()
        val check = current.find { it.id == item.id }
        if(check?.isLike == item.isLike) {
            return
        }
        current.add(item)
        _list.value = current
    }

    fun removeItem(position: Int? = null, item: ContentData) {
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

        currentList.removeAt(currentPosition)
        _list.value = currentList
    }

    fun saveSharedPrefs(item: ContentData) {
        val appContext = getApplication<Application>().applicationContext
        Utils.saveMyPageContent(appContext, item)
    }

    fun deleteSharedPrefs(item: ContentData) {
        val appContext = getApplication<Application>().applicationContext
        Utils.removeMyPageContent(appContext, item)
    }
}