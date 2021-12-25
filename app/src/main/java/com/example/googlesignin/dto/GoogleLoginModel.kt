package com.example.googlesignin.dto

import android.os.Parcelable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoogleLoginModel(
 val displayName:String?,
 val familyName:String?,
 val email:String?
):Parcelable
