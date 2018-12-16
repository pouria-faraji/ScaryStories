package com.blacksite.scarystories.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sound(@SerializedName("id")var id:Int,
                 @SerializedName("name")var name:String,
                 @SerializedName("resource")var resource:String): Serializable {
}