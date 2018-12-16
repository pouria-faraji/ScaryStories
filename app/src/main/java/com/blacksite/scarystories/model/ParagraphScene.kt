package com.blacksite.scarystories.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ParagraphScene(@SerializedName("position")var position:Int,
                          @SerializedName("playTime")var playTime:Int,
                          @SerializedName("duration")var duration:Int,
                          @SerializedName("paragraph")var paragraph: Paragraph): Serializable {
}