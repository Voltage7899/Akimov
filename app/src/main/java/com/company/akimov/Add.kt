package com.company.akimov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.company.akimov.databinding.ActivityAddBinding
import com.google.firebase.database.*

class Add : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addAdd.setOnClickListener {
            try {
                val id = binding.addFrom.text.toString().substring(0,2) + binding.addToo.text.toString().substring(0,2)
                val race=Race(id,binding.addData.text.toString(),binding.addStartTime.text.toString(),binding.addFinishTime.text.toString(),binding.addFrom.text.toString(),binding.addToo.text.toString())
                database.child("Race").child(race.id.toString()).child(race.data.toString()).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        database.child("Race").child(race.id.toString()).child(race.data.toString()).child(race.timeStart.toString()).setValue(race)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
                finish()
            }
            catch (ex:Exception){
                Toast.makeText(this,"Проверьте введенные данные", Toast.LENGTH_LONG).show()
            }

        }
    }
}