package ru.nikita.weatherdiplom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nikita.weatherdiplom.databinding.ItemWeekTempBinding
import ru.nikita.weatherdiplom.dto.Week

class MyAdapter() : ListAdapter<Week, WeekViewHolder>(WeekDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
       val binding = ItemWeekTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val week = getItem(position)
        holder.bind(week)
    }
}

class WeekViewHolder(
  private val  binding: ItemWeekTempBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(week: Week) {
        binding.tempItemWeek.text = week.currentTemp
        binding.dataItemWeek.text = week.date
        binding.conditionItemWeek.text = week.condition
        //TODO Вставка картинки
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
