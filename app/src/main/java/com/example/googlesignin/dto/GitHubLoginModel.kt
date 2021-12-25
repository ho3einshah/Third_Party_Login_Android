package com.example.googlesignin.dto

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue



@Parcelize
data class GitHubLoginModel(
    @SerializedName("login")
    @Expose
    val login: String?,
    @SerializedName("id")
    @Expose
    val id: String?,
    @SerializedName("node_id")
    @Expose
    val nodeId: String?,
    @SerializedName("avatar_url")
    @Expose
    val avatar_url: String?,

    @SerializedName("gravatar_id")
    @Expose
    val gravatar_id: String?,

    @SerializedName("url")
    @Expose
    val url: String?,

    @SerializedName("html_url")
    @Expose
    val html_url: String?,

    @SerializedName("followers_url")
    @Expose
    val followers_url: String?,

    @SerializedName("following_url")
    @Expose
    val following_url: String?,

    @SerializedName("gists_url")
    @Expose
    val gists_url: String?,

    @SerializedName("starred_url")
    @Expose
    val starred_url: String?,

    @SerializedName("subscriptions_url")
    @Expose
    val subscriptions_url: String?,

    @SerializedName("organizations_url")
    @Expose
    val organizations_url: String?,

    @SerializedName("repos_url")
    @Expose
    val repos_url: String?,

    @SerializedName("events_url")
    @Expose
    val events_url: String?,

    @SerializedName("received_events_url")
    @Expose
    val received_events_url: String?,

    @SerializedName("type")
    @Expose
    val type: String?,

    @SerializedName("site_admin")
    @Expose
    val site_admin: String?,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("company")
    @Expose
    val company: String?,

    @SerializedName("blog")
    @Expose
    val blog: String?,

    @SerializedName("location")
    @Expose
    val location: String?,


    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("hireable")
    @Expose
    val hireable: String?,
    @SerializedName("bio")
    @Expose
    val bio: String?,

    @SerializedName("twitter_username")
    @Expose
    val twitter_username: String?,

    @SerializedName("public_repos")
    @Expose
    val public_repos: String?,

    @SerializedName("public_gists")
    @Expose
    val public_gists: String?,

    @SerializedName("followers")
    @Expose
    val followers: String?,

    @SerializedName("following")
    @Expose
    val following: String?,

    @SerializedName("created_at")
    @Expose
    val created_at: String?,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String?,

    @SerializedName("private_gists")
    @Expose
    val private_gists: String?,

    @SerializedName("total_private_repos")
    @Expose
    val total_private_repos: String?,

    @SerializedName("owned_private_repos")
    @Expose
    val owned_private_repos: String?,

    @SerializedName("disk_usage")
    @Expose
    val disk_usage: String?,

    @SerializedName("collaborators")
    @Expose
    val collaborators: String?,

    @SerializedName("two_factor_authentication")
    @Expose
    val two_factor_authentication: String?,

    @SerializedName("plan")
    @Expose
    val plan: String?,


    ):Parcelable
