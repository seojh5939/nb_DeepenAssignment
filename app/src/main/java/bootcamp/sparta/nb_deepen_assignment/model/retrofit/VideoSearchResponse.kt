package bootcamp.sparta.nb_deepen_assignment.model.retrofit

import com.google.gson.annotations.SerializedName

data class VideoSearchResponse(
    @SerializedName("meta")
    val metaDAta: MetaData?,

    @SerializedName("documents")
    val documents: MutableList<VideoData>?
)
