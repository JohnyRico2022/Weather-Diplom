package ru.nikita.weatherdiplom.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.nikita.weatherdiplom.R
import ru.nikita.weatherdiplom.databinding.FragmentDayBinding
import ru.nikita.weatherdiplom.utils.AndroidUtils
import ru.nikita.weatherdiplom.utils.KEY_DATA
import ru.nikita.weatherdiplom.utils.KEY_DATA_CITY
import ru.nikita.weatherdiplom.utils.KEY_DATA_LANGUAGE
import ru.nikita.weatherdiplom.utils.dialogManager.AccessDialog
import ru.nikita.weatherdiplom.utils.dialogManager.DialogClickListener
import ru.nikita.weatherdiplom.utils.dialogManager.InfoDialog
import ru.nikita.weatherdiplom.utils.dialogManager.LocationDialog
import ru.nikita.weatherdiplom.utils.isPermissionGranted
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DayFragment : Fragment() {
    private lateinit var binding: FragmentDayBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var launcher: ActivityResultLauncher<String>

    @Inject
    lateinit var hideKeyboard: AndroidUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding = FragmentDayBinding.inflate(inflater, container, false)
        checkPermission()
        val pref = this.requireActivity()
            .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)

        val city = pref.getString(KEY_DATA_CITY, "Moscow").toString()
        val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

        lifecycleScope.launch {
            viewModel.getWeather(city, language)
        }



        binding.location.setOnClickListener {
            getLocation(viewModel)
        }

        binding.searchImage.setOnClickListener {
            val textCity = binding.searchCity.text.trim().toString()

            if (textCity.length > 2) {
                pref.edit()
                    .putString(KEY_DATA_CITY, textCity)
                    .apply()
                hideKeyboard.hideKeyboard(requireView())
                lifecycleScope.launch {
                    viewModel.getWeather(textCity, language)
                }

                binding.searchCity.setText("")
            } else {
                InfoDialog.mainCardInfoDialog(requireContext())
            }
        }


        viewModel.dataDay.observe(viewLifecycleOwner) {
            with(binding) {
                cityName.text = it.city
                currentTemp.text = "${it.currentTemp} °C"
                condition.text = it.condition
                Picasso.get().load("https:" + it.imageURL).into(imageWeather)
            }
        }

        binding.mainCard.setOnClickListener {
            findNavController().navigate(R.id.action_dayFragment_to_fullCurrentWeatherFragment)
        }

        binding.info.setOnClickListener {
            InfoDialog.mainCardInfoDialog(requireContext())
        }

        return binding.root
    }

    private fun isLocationEnabled(): Boolean {   //проверка, включен ли GPS
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getLocation(viewModel: WeatherViewModel) {

        if (!isLocationEnabled()) {
            LocationDialog.settingsLocation(requireContext(), object : DialogClickListener {
                override fun onClick() {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })

            return
        } else {

            val pref = this.requireActivity()
                .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)
            val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

            val token = CancellationTokenSource()

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                AccessDialog.accessLocationLimited(requireContext(), object : DialogClickListener {
                    override fun onClick() {
                        startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
                    }
                })
                return
            }
            fusedLocationClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token)
                .addOnCompleteListener {
                    val currentCity = "${it.result.latitude},${it.result.longitude}"

                    lifecycleScope.launch {
                        viewModel.getWeather(currentCity, language)
                    }

                    pref.edit()
                        .putString(KEY_DATA_CITY, currentCity)
                        .apply()
                }
        }
    }

    private fun permissionListener() {
        launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

}