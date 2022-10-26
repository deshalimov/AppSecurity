package com.example.security

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI

class SignOutFragment: Fragment(R.layout.sign_out) {
    private lateinit var btnDelete: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDelete = view.findViewById(R.id.btn_del_acc)

        btnDelete.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    var intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
        }
    }
}