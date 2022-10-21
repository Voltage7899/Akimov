package com.company.akimov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.akimov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.find.setOnClickListener {
            try {
                if( !binding.seOt.text.isEmpty() && !binding.seData.text.isEmpty() && !binding.seKu.text.isEmpty() ){
                    val id = binding.seOt.text.toString().substring(0,2) + binding.seKu.text.toString().substring(0,2)
                    val data = binding.seData.text.toString()

                    val intent = Intent(this,ListTime::class.java)
                    intent.putExtra("id",id)
                    intent.putExtra("data",data)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Введите все данные в поля", Toast.LENGTH_LONG).show()
                }


            }catch (ex:Exception){
                Toast.makeText(this,"Проверьте поля", Toast.LENGTH_LONG).show()
            }
        }
        binding.createRace.setOnClickListener {
            startActivity(Intent(this,Sign::class.java))
        }
    }
}