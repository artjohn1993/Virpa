package com.local.virpa.virpa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.LoginEvent
import com.local.virpa.virpa.event.RegisterEvent
import org.greenrobot.eventbus.EventBus


class SignupFragment : Fragment() {

    var signin : TextView? = null
    var signup : Button? = null
    var fullname : EditText? = null
    var email : EditText? = null
    var password : EditText? = null
    var mobile : EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_signup, container, false)

        setVariable(view)
        signin?.setOnClickListener {
            hide(view)
            EventBus.getDefault().post(LoginChangeFragment("signin"))
        }
        signup?.setOnClickListener {
            EventBus.getDefault().post(RegisterEvent(
                    fullname?.text.toString(),
                    mobile?.text.toString(),
                    email?.text.toString(),
                    password?.text.toString()
            ))
        }

        return view
    }

    private fun setVariable(view: View) {
        signin = view.findViewById(R.id.signin)
        signup = view.findViewById(R.id.registerButton)
        fullname = view.findViewById(R.id.fullnameEdit)
        mobile = view.findViewById(R.id.mobileEdit)
        email = view.findViewById(R.id.emailEdit)
        password = view.findViewById(R.id.passwordEdit)
    }
    private fun hide(view : View) {
        view.visibility = View.GONE
    }
}
