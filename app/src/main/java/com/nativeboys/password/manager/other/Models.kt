package com.nativeboys.password.manager.other

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class FieldContentModel(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val type: String,
    val content: String
) : Parcelable
