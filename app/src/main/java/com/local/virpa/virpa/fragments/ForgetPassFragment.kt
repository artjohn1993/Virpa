package com.local.virpa.virpa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.enum.LoginFragment
import com.local.virpa.virpa.event.ForgetPassEvent
import com.local.virpa.virpa.event.LoginChangeFragment
import org.greenrobot.eventbus.EventBus

class ForgetPassFragment : Fragment() {
    var email : android.support.design.widget.TextInputEditText? = null
    var continueButton : Button? = null
    var back : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_forget_pass, container, false)
        setVariables(view)
        back?.setOnClickListener {
            EventBus.getDefault().post(LoginChangeFragment(LoginFragment.SIGNIN))
        }
        continueButton?.setOnClickListener {
            EventBus.getDefault().post(ForgetPassEvent(email?.text.toString()))
        }

        return view
    }

    private fun setVariables(view : View) {
        email = view.findViewById(R.id.emailEdit)
        continueButton = view.findViewById(R.id.continueButton)
        back = view.findViewById(R.id.backSignIn)
    }
}
