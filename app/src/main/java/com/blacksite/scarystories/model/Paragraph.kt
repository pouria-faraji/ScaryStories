package com.blacksite.scarystories.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Paragraph(@SerializedName("id")var id:Int,
                     @SerializedName("text")var text:String):Serializable{
}