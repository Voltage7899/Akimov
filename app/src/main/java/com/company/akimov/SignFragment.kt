package com.company.akimov

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.company.akimov.databinding.ActivityMainBinding
import com.company.akimov.databinding.FragmentSignBinding
import com.google.firebase.database.*


class SignFragment : Fragment() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    lateinit var binding: FragmentSignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signSign.setOnClickListener {
            val phone = binding.phoneSign.text.toString()
            val pass = binding.passSign.text.toString()
            Log.d(TAG,"DATABASE    "+database)
            database.child("User").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(phone).exists()) {
                        val userCurrentData: User? = snapshot.child(phone).getValue(
                            User::class.java
                        )

                        if (userCurrentData?.phone.equals(phone) && userCurrentData?.pass.equals(pass)) {
                            Toast.makeText(activity,"Вы вошли",Toast.LENGTH_LONG).show()
                            User.currentUser=userCurrentData
                            activity?.supportFragmentManager?.beginTransaction()?.remove(this@SignFragment)?.commit()
                        } else {
                            Toast.makeText(activity,"Неверные данные",Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(activity,"Такого номера телефона не существует",Toast.LENGTH_LONG).show()

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SignFragment()
    }
}