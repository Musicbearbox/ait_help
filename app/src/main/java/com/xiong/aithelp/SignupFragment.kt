package com.xiong.aithelp

import android.os.Bundle
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
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val activity = requireActivity() as LoginActivity
        val submit = view.findViewById<Button>(R.id.buttonSignup)
//        submit.setOnClickListener {
//            activity.toast("Sign up successful")
////            loginActivity.finish()
//        }
        this.bindSignup(view,activity)
        return view
    }

    private fun bindSignup(view: View,activity:LoginActivity){
        val emailEd = view.findViewById<EditText>(R.id.inputEmail)
        val passwordEd = view.findViewById<EditText>(R.id.inputPassword)
        val usernameEd = view.findViewById<EditText>(R.id.inputUsername)
        val telEd = view.findViewById<EditText>(R.id.inputTel)


        val bt = view.findViewById<Button>(R.id.buttonSignup)
        bt.setOnClickListener {
            var email = emailEd.text.toString()
            var password = passwordEd.text.toString()
            var username = usernameEd.text.toString()
            var tel = telEd.text.toString()
//            d("email",email)
//            activity.toast(email)
            if(email=="" || password=="" ||username=="" ||tel==""){
                activity.toast("all inputs can not be empty")
            }else{
                activity.register(email,password,username,tel,this)
            }
        }
    }

    public fun haveDone(isSuccess:Boolean){
        val activity = requireActivity() as LoginActivity
        if(isSuccess){
            activity.toast("register success!")
            activity.finish()
        }else{
            activity.toast("user is already existed!")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}