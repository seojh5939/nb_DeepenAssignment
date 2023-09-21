package bootcamp.sparta.nb_deepen_assignment.model.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoData(
    val id: Long,
    @SerializedName("datetime")
    val dateTime: String,
    @SerializedName("thumbnail")
    val thumbnail: String
): Parcelable