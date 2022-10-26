package com.example.security

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

class MainActivity : AppCompatActivity() {

    companion object {
        const val SIGN_IN_REQUEST_CODE = 1001
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchSignInFlow()
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                setContentView(R.layout.activity_main)
            } else {
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }
}