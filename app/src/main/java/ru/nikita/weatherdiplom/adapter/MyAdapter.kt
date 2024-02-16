package ru.nikita.weatherdiplom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.nikita.weatherdiplom.databinding.ItemWeekTempBinding
import ru.nikita.weatherdiplom.dto.Week


interface OnInteractionListener {
    fun onItemClicked(itemDay: Week){}
}

class MyAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Week, WeekViewHolder>(WeekDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
       val binding = ItemWeekTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val week = getItem(position)
        holder.bind(week)
    }
}

class WeekViewHolder(
  private val  binding: ItemWeekTempBinding,
  private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(week: Week) {
        binding.tempItemWeek.text = ("${week.currentTemp} °C")
        binding.dataItemWeek.text = week.date
        binding.conditionItemWeek.text = week.condition
        Picasso.get().load("https:" + week.imageURL).into(binding.imageItemWeek)
        itemListener(week)
    }

    private fun itemListener(week: Week) {
        itemView.setOnClickListener {
            onInteractionListener.onItemClicked(week)
        }
    }


}

class WeekDiffCallback : DiffUtil.ItemCallback<Week>() {
    override fun areItemsTheSame(oldItem: Week, newItem: Week): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Week, newItem: Week): Boolean {
        return oldItem == newItem
    }

}
