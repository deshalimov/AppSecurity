package com.example.security

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangeEmailFragment : Fragment(R.layout.change_email) {
    private lateinit var navController: NavController
    private lateinit var btnChangeEmail: Button
    private lateinit var textEmail: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        val user = Firebase.auth.currentUser!!

        btnChangeEmail = view.findViewById(R.id.btn_change)
        textEmail = view.findViewById(R.id.editTextEmail)

        textEmail.setText(user.email)

        btnChangeEmail.setOnClickListener {
            val email = textEmail.text.toString()

            if (email != "") {
                user.updateEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText( context, "Email успешно обновлён", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText( context, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText( context, "Вы не ввели Email", Toast.LENGTH_SHORT).show()
            }

        }
    }
}