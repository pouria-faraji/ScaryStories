package com.blacksite.scarystories.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Scene(@SerializedName("backgroundMusic")var backgroundMusic:String,
                 @SerializedName("subSceneList") var subSceneList: List<SubScene>):Serializable{

}