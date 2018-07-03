package com.local.virpa.virpa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.LoginEvent
import org.greenrobot.eventbus.EventBus


class SignupFragment : Fragment() {

    var signin : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_signup, container, false)

        signin = view.findViewById(R.id.signin)
        signin?.setOnClickListener {
            hide(view)
            EventBus.getDefault().post(LoginChangeFragment("signin"))
        }

        return view
    }

    private fun hide(view : View) {
        view.visibility = View.GONE
    }
}
