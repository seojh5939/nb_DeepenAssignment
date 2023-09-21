package bootcamp.sparta.nb_deepen_assignment.model.retrofit

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var documents: MutableList<ImageData>?
)
