package com.example.security

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class ChangePersonalDataFragment: Fragment(R.layout.change_personal_data) {
    private lateinit var navController: NavController
    private lateinit var imageView: ImageView
    private lateinit var input: EditText
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view);
        imageView = view.findViewById(R.id.imageView3)
        input = view.findViewById(R.id.editTextPersonName)
        button = view.findViewById(R.id.btn_change)

        val user = Firebase.auth.currentUser!!

        var name = user.displayName
        var url = user.photoUrl

        input.setText(name)
        if (url != null)
            Glide.with(this).load(user.photoUrl).into(imageView)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
            imageView.setImageURI(uri)
            url = uri
        }

        imageView.setOnClickListener {
            getContent.launch("image/*")
        }

        button.setOnClickListener {
            name = input.text.toString()
            if (url != null) {
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                    photoUri = url
                }
                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->9
                        if (task.isSuccessful) {
                            Toast.makeText( context, "Данные успешно изменены.", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        else{
                            Toast.makeText( context, "Что-то пошло не так.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else {
                Toast.makeText( context, "Вы не загрузили картинку.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}