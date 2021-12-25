package com.example.googlesignin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import androidx.core.app.ActivityCompat.startActivityForResult
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.googlesignin.api.ApiService
import com.example.googlesignin.R
import com.example.googlesignin.repo.Repository
import com.example.googlesignin.databinding.ActivityMainBinding
import com.example.googlesignin.dto.FacebookLoginModel
import com.example.googlesignin.dto.GitHubLoginModel
import com.example.googlesignin.dto.GoogleLoginModel
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.facebook.login.LoginResult


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.facebook.GraphRequest
import com.facebook.FacebookException


import com.facebook.FacebookCallback
import okhttp3.internal.wait
import java.util.*


class MainActivity : AppCompatActivity() {

    val clientid = " 636447286499-3mgtrs8qg465mhnmf7k3fh6ccqnoaaau.apps.googleusercontent.com"

    val githubClientId = "8cafd6acb0b1220a5b82"

    val githubSecretId = "37685d845f8ddb0849a12decc3a8c858b59c3afd"

    var githubCode = ""

    lateinit var retrofit1: Retrofit

    var githubAccessToken = ""

    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 125


    lateinit var binding: ActivityMainBinding

    lateinit var move: Intent

    var loginResult: LoginResult?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        AppEventsLogger.activateApp(application)

        val callbackManager = CallbackManager.Factory.create()

        binding.facebbokSignIn.setPermissions(listOf("public_profile", "email"))


        binding.facebbokSignIn.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "user cancelled auth", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@MainActivity, "an error occured", Toast.LENGTH_SHORT).show()
                    Log.e(MainActivity::class.java.toString(), error.message.toString())
                }

                override fun onSuccess(result: LoginResult?) {
                    loginResult= result

                }
            })
        //login manager for facebook


        //github login


        binding.githubSignIn.setOnClickListener {
            val uri =
                Uri.parse("https://github.com/login/oauth/authorize?client_id=$githubClientId&redirect_uri=samplesignin://callback")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

          /*  while (githubCode.isNullOrBlank())
            {
                Toast.makeText(this,"please wait",Toast.LENGTH_SHORT).show()
            }*/



        }


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.googleSignIn.setOnClickListener { signIn() }

    }

    //on redirected callback github
    override fun onResume() {
        super.onResume()

        //facebook
        if (loginResult?.accessToken?.token!=null){
            val parameters = Bundle()
            parameters.putString("fields", "id,name,email,first_name,last_name")
            val request = GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me", null,
                HttpMethod.GET,
                {
                    move = Intent(this@MainActivity, ShowUserInformation::class.java)
                    move.putExtra(
                        "FacebookLogin", FacebookLoginModel(
                            it.jsonObject?.get("first_name").toString(),
                            it.jsonObject?.get("last_name").toString(),
                            it.jsonObject?.get("email").toString()
                        )
                    )
                    startActivity(move)
                    Log.i("facebookResponse", it.jsonObject.toString())

                }
            )
            request.parameters = parameters
            request.executeAsync()
            loginResult=null
        }







        val uri = intent.data
        if (uri != null) {
            Log.i("callback", uri.toString())
            githubCode = uri.getQueryParameter("code").toString()
            val retrofit = Retrofit.Builder().baseUrl("https://github.com/login/oauth/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val apiService = retrofit.create(ApiService::class.java)
            val getAccess = apiService.getAccessToken(githubClientId, githubSecretId, githubCode)
            getAccess.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    try {
                        val stringResponse = response.body()?.string()?.split("=")
                        githubAccessToken = stringResponse?.get(1)?.substring(0, 40).toString()
                        Log.i("access_token", githubAccessToken)
                        retrofit1 = Repository().createRetrofit(
                            "https://api.github.com/",
                            accessToken = githubAccessToken
                        )

                        val getParameter = retrofit1.create(ApiService::class.java)
                        val parameters = getParameter.getParameter()
                        parameters.enqueue(object : Callback<GitHubLoginModel> {
                            override fun onResponse(
                                call: Call<GitHubLoginModel>,
                                response: Response<GitHubLoginModel>
                            ) {
                                move = Intent(this@MainActivity, ShowUserInformation::class.java)
                                val responseModel = response.body()
                                move.putExtra(
                                    "githubmodel",
                                    GitHubLoginModel(
                                        responseModel?.login,
                                        responseModel?.id,
                                        responseModel?.nodeId,
                                        responseModel?.avatar_url,
                                        responseModel?.gravatar_id,
                                        responseModel?.url,
                                        responseModel?.html_url,
                                        responseModel?.followers_url,
                                        responseModel?.following_url,
                                        responseModel?.gists_url,
                                        responseModel?.starred_url,
                                        responseModel?.subscriptions_url,
                                        responseModel?.organizations_url,
                                        responseModel?.repos_url,
                                        responseModel?.events_url,
                                        responseModel?.received_events_url,
                                        responseModel?.type,
                                        responseModel?.site_admin,
                                        responseModel?.name,
                                        responseModel?.company,
                                        responseModel?.blog,
                                        responseModel?.location,
                                        responseModel?.email,
                                        responseModel?.hireable,
                                        responseModel?.bio,
                                        responseModel?.twitter_username,
                                        responseModel?.public_repos,
                                        responseModel?.public_gists,
                                        responseModel?.followers,
                                        responseModel?.following,
                                        responseModel?.created_at,
                                        responseModel?.updated_at,
                                        responseModel?.private_gists,
                                        responseModel?.total_private_repos,
                                        responseModel?.owned_private_repos,
                                        responseModel?.disk_usage,
                                        responseModel?.collaborators,
                                        responseModel?.two_factor_authentication,
                                        responseModel?.plan
                                    )
                                )
                                startActivity(move)
                                intent.data = null


                            }

                            override fun onFailure(call: Call<GitHubLoginModel>, t: Throwable) {

                            }

                        })
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()

                }
            })



        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(this, signInIntent, RC_SIGN_IN, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            move = Intent(this, ShowUserInformation::class.java)
            move.putExtra(
                "googleLogin",
                GoogleLoginModel(account.displayName, account.familyName, account.email)
            )
            startActivity(move)

            // Toast.makeText(this, "successfully signed in ${account}", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {

            Log.w("${MainActivity::class.java}", "signInResult:failed code=" + e.statusCode)
        }
    }

}