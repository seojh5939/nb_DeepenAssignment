package bootcamp.sparta.nb_deepen_assignment.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    // 날짜 포멧변경
    fun changeDateFormat(dttm: String): String {
        val old_format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000Z", Locale.KOREAN)
        val old_date = old_format.parse(dttm)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
        val new_date = old_date?.let { formatter.format(it) }

        return new_date ?: "invalid Format"
    }
}