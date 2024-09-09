package com.example.analytics1.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemDemoModel(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("image") var image: Int = 0,
    @SerializedName("type") var type: String = ""
) : Serializable