package com.blacksite.scarystories.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SoundScene(@SerializedName("repeat")var repeat:Boolean,
                      @SerializedName("playTime")var playTime:Int,
                      @SerializedName("sound")var sound: Sound): Serializable {
}