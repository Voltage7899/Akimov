package com.company.akimov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.akimov.databinding.ActivityMainBinding
import com.company.akimov.databinding.ActivitySignBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sign : AppCompatActivity() {
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    lateinit var binding: ActivitySignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.signFragment.id,SignFragment.newInstance()).commit()

        binding.Adminfind.setOnClickListener {
            try {
                if( !binding.AdminseOt.text.isEmpty() && !binding.AdminseData.text.isEmpty() && !binding.AdminseKu.text.isEmpty() ) {
                    val id = binding.AdminseOt.text.toString()
                        .substring(0, 2) + binding.AdminseKu.text.toString().substring(0, 2)
                    val data = binding.AdminseData.text.toString()

                    val intent = Intent(this, AdminListTime::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("data", data)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Введите все данные в поля", Toast.LENGTH_LONG).show()
                }

            }catch (ex:Exception){
                Toast.makeText(this,"Проверьте поля", Toast.LENGTH_LONG).show()
            }
        }
        binding.AdmincreateRace.setOnClickListener {
            startActivity(Intent(this,Add::class.java))
        }
    }
}