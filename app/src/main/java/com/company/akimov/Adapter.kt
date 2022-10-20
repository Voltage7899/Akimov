package com.company.akimov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.akimov.databinding.ElementRecItemBinding

class Adapter(val clickListener: ClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var ListInAdapter = ArrayList<Race>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_rec_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position], clickListener)
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementRecItemBinding.bind(itemView)
        fun bind(race: Race, clickListener: ClickListener) {
            binding.elTimeFrom.text=race.from
            binding.elTimeToo.text=race.to
            binding.elTimeStart.text=race.timeStart
            binding.elTimeFinish.text=race.timeFinish
            binding.elTimeFrom.text=race.from

            itemView.setOnClickListener {

                clickListener.onClick(race)
            }

        }
    }

    fun loadListToAdapter(productList: ArrayList<Race>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(race: Race) {

        }
    }

    fun deleteItem(i: Int): Race? {
        var race = ListInAdapter.get(i)

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return race
    }
}