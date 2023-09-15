package bootcamp.sparta.nb_deepen_assignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentData(
    val id: Long,
    val dateTime: String,
    val thumbnail: String
): Parcelable

@Parcelize
data class ImageData(
    val id: Long,
    val dateTime: String,
    val thumbnail: String
): Parcelable

@Parcelize
data class VideoData(
    val id: Long,
    val dateTime: String,
    val thumbnail: String
): Parcelable