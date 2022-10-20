package com.company.akimov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.akimov.databinding.ActivityAdminListTimeBinding
import com.company.akimov.databinding.ActivityListTimeBinding
import com.google.firebase.database.*

class AdminListTime  : AppCompatActivity(),Adapter.ClickListener {
    lateinit var binding: ActivityAdminListTimeBinding
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var ListAdapter:Adapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminListTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id =intent.getStringExtra("id").toString()
        val data = intent.getStringExtra("data").toString()

        binding.recTimeAdmin.layoutManager=  LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        ListAdapter= Adapter(this)
        binding.recTimeAdmin.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData(id,data))

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val race =ListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.child("Race").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (race != null) {
                            database.child("Race").child(race.id.toString()).child(race.data.toString()).child(race.timeStart.toString()).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recTimeAdmin)


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