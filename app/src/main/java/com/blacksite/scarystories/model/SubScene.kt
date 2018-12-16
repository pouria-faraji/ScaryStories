package com.blacksite.scarystories.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SubScene(@SerializedName("backgroundResource")var backgroundResource: String,
                    @SerializedName("paragraphSceneList")var paragraphSceneList: List<ParagraphScene>,
                    @SerializedName("soundSceneList")var soundSceneList: List<SoundScene>):Serializable{
}