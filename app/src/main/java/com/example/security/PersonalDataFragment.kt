package com.example.security

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PersonalDataFragment : Fragment(R.layout.personal_data) {
  private lateinit var nameText: TextView
  private lateinit var image: ImageView
  private lateinit var emailText: TextView
  private lateinit var authText: TextView


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    nameText = view.findViewById(R.id.textName)
    image = view.findViewById(R.id.imageView3)
    emailText = view.findViewById(R.id.textEmail)
    authText = view.findViewById(R.id.textAuth)

    val user = Firebase.auth.currentUser!!

    user?.let {
      if (user.photoUrl != null)
        Glide.with(this).load(user.photoUrl).into(image)
      nameText.text = user.displayName
      emailText.text = user.email
      for (profile in it.providerData) {
        authText.text = profile.providerId
      }
    }
  }
}