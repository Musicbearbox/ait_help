package com.xiong.aithelp

import android.app.Activity
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var toSignup: Button
    private lateinit var activity:LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val activity =  requireActivity() as LoginActivity
        this.toSignup = view.findViewById<Button>(R.id.buttonCreateAccount)
        toSignup.setOnClickListener {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.login_page_frame, SignupFragment())
                .addToBackStack(null)
                .commit()
        }
        this.bindLogin(view,activity)
        return view
    }

    private fun bindLogin(view: View,activity:LoginActivity){
        val emailEd = view.findViewById<EditText>(R.id.loginInputEmail)
        val passwordEd = view.findViewById<EditText>(R.id.inputPassword)
//        val username = "elliot@ait.asia"
//        val password = "123456"
        val bt = view.findViewById<Button>(R.id.buttonLogin)
        bt.setOnClickListener {
            var email = emailEd.text.toString()
            var password = passwordEd.text.toString()
//            d("email",email)
//            activity.toast(email)
            if(email=="" || password==""){
                activity.toast("email and password can not be empty")
            }else{
                activity.login(email,password,this)
            }
        }
    }

    public fun haveDone(isSuccess:Boolean){
        val activity = requireActivity() as LoginActivity
        if(isSuccess){
            activity.toast("login success!")
            activity.finish()
        }else{
            activity.toast("user does not exist or password incorrect")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}