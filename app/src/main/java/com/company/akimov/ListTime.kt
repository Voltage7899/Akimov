package com.company.akimov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.akimov.databinding.ActivityListTimeBinding
import com.google.firebase.database.*

class ListTime : AppCompatActivity(),Adapter.ClickListener {
    lateinit var binding: ActivityListTimeBinding
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var ListAdapter:Adapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id =intent.getStringExtra("id").toString()
        val data = intent.getStringExtra("data").toString()

        binding.recItem.layoutManager=  LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        ListAdapter= Adapter(this)
        binding.recItem.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData(id,data))

    }

    fun getData(id:String,data:String) :ArrayList<Race>{
        val commonList=ArrayList<Race>()

        database.child("Race").child(id).child(data).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (item in snapshot.children){
                    var race=item.getValue(Race::class.java)
                    if (race != null) {
                        commonList.add(race)
                    }
                }
                ListAdapter?.loadListToAdapter(commonList)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return  commonList
    }
}