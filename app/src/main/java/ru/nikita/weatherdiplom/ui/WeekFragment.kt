package ru.nikita.weatherdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.nikita.weatherdiplom.adapter.MyAdapter
import ru.nikita.weatherdiplom.adapter.OnInteractionListener
import ru.nikita.weatherdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherdiplom.dto.Week
import ru.nikita.weatherdiplom.utils.KEY_AUTH
import ru.nikita.weatherdiplom.utils.KEY_AUTH_SIGNIN
import ru.nikita.weatherdiplom.utils.KEY_AUTH_SIGNUP
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel

class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentWeekBinding.inflate(layoutInflater, container, false)

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        updateUI(userSignIn, userSignUp)


        val adapterDays = MyAdapter(object : OnInteractionListener {
            override fun onItemClicked(itemDay: Week) {
                viewModel.hoursForParse(itemDay.hours)
            }
        })
        binding.daysRecycler.adapter = adapterDays

        val adapterHours = MyAdapter(object : OnInteractionListener {
            override fun onItemClicked(itemDay: Week) {
                //TODO здесь надо прописать null
            }
        })
        binding.hoursRecycler.adapter = adapterHours

        viewModel.dataListWeek.observe(viewLifecycleOwner) {
            adapterDays.submitList(it)
        }

         viewModel.dataHours.observe(viewLifecycleOwner) {
             adapterHours.submitList(it)
         }

        return binding.root
    }

    private fun updateUI(signInPref: String, signUpPref: String) {
        if (signInPref == signUpPref) {
            binding.frameLayoutDays.visibility = View.VISIBLE
            binding.frameLayoutHours.visibility = View.VISIBLE
            binding.noAccessCardWeekFragment.visibility = View.GONE
        } else {
            binding.frameLayoutDays.visibility = View.GONE
            binding.frameLayoutHours.visibility = View.GONE
            binding.noAccessCardWeekFragment.visibility = View.VISIBLE
        }
    }
}