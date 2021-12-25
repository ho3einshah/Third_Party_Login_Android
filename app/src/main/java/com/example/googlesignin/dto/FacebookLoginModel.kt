package com.example.googlesignin.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FacebookLoginModel(
    val displayName:String?,
    val familyName:String?,
    val email:String?
):Parcelable
