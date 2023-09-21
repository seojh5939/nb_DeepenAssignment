package bootcamp.sparta.nb_deepen_assignment.utils

import android.app.Activity
import android.content.Context
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    private const val MY_PAGE_PREF = "my_page_pref"

    // 날짜 포멧변경
    fun changeDateFormat(dttm: String): String {
        val old_format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000Z", Locale.KOREAN)
        val old_date = old_format.parse(dttm)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
        val new_date = old_date?.let { formatter.format(it) }

        return new_date ?: "invalid Format"
    }

    fun saveMyPageContent(context: Context, item: ContentData) {
        val pref = context.getSharedPreferences(MY_PAGE_PREF, Activity.MODE_PRIVATE)
        val edit = pref.edit()
        val jsonString = Gson().toJson(item)
        edit.putString(item.id.toString(), jsonString)
        edit.apply()
    }

    fun loadMyPageContents(context: Context): ArrayList<ContentData> {
        val pref = context.getSharedPreferences(MY_PAGE_PREF, Activity.MODE_PRIVATE)
        val list = ArrayList<ContentData>()
        val getAllData: Map<String, *> = pref.all
        val gson = Gson()
        getAllData.forEach() { (key, value) ->
            val item = gson.fromJson(value as String, ContentData::class.java)
            list.add(item)
        }
        return list
    }

    fun removeMyPageContent(context: Context, item: ContentData) {
        val pref = context.getSharedPreferences(MY_PAGE_PREF, Activity.MODE_PRIVATE)
        val edit = pref.edit()
        edit.remove(item.id.toString())
        edit.apply()
    }
}