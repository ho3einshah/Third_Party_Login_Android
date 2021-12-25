package com.example.googlesignin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.googlesignin.R
import com.example.googlesignin.databinding.ActivityShowUserInformationBinding
import com.example.googlesignin.dto.FacebookLoginModel
import com.example.googlesignin.dto.GitHubLoginModel
import com.example.googlesignin.dto.GoogleLoginModel

class ShowUserInformation : AppCompatActivity() {

    lateinit var  gitHubLoginModel:GitHubLoginModel
    lateinit var googleLoginModel: GoogleLoginModel
    lateinit var facebookLoginModel: FacebookLoginModel

    lateinit var binding: ActivityShowUserInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_show_user_information)

        if (intent.extras?.get("githubmodel") != null) {
             gitHubLoginModel = intent.extras?.get("githubmodel") as GitHubLoginModel
            binding.nameValue.text= gitHubLoginModel.name
            binding.familyValue.text= gitHubLoginModel.login
            binding.emailValue.text=gitHubLoginModel.email
        }
        if (intent.extras?.get("googleLogin") != null) {
            googleLoginModel = intent.extras?.get("googleLogin") as GoogleLoginModel
            binding.nameValue.text= googleLoginModel.displayName
            binding.familyValue.text= googleLoginModel.familyName
            binding.emailValue.text=googleLoginModel.email
        }
        if (intent.extras?.get("FacebookLogin") != null) {
            facebookLoginModel = intent.extras?.get("FacebookLogin") as FacebookLoginModel
            binding.nameValue.text= facebookLoginModel.displayName
            binding.familyValue.text= facebookLoginModel.familyName
            binding.emailValue.text=facebookLoginModel.email
        }
    }
}