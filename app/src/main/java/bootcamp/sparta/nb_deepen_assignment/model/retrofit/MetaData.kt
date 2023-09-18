package bootcamp.sparta.nb_deepen_assignment.model.retrofit

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("pageable_count")
    val pageableCount: Int?,

    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("is_end")
    val isEnd: Boolean?
)