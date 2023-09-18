package bootcamp.sparta.nb_deepen_assignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentData(
    val id: Int,
    val dateTime: String,
    val thumbnail: String
): Parcelable