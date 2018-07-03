package com.local.virpa.virpa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.LoginEvent
import org.greenrobot.eventbus.EventBus


class SignInFragment : Fragment() {

    var signUp : Button? = null
    var login : Button? = null
    var password : android.support.design.widget.TextInputEditText? = null
    var username : android.support.design.widget.TextInputEditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        signUp = view.findViewById(R.id.signUpButton)
        login = view.findViewById(R.id.signinButton)
        username = view.findViewById(R.id.usernameEdit)
        password = view.findViewById(R.id.passwordEdit)
        login?.setOnClickListener {
            EventBus.getDefault().post(LoginEvent(username?.text.toString(), password?.text.toString()))
        }
        signUp?.setOnClickListener {
            hide(view)
            EventBus.getDefault().post(LoginChangeFragment("signup"))
        }
        return view
    }

    private fun hide(view : View) {
        view.visibility = View.GONE
    }

}
