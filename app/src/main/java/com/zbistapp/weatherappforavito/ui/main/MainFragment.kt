package com.zbistapp.weatherappforavito.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.zbistapp.weatherappforavito.App
import com.zbistapp.weatherappforavito.BuildConfig
import com.zbistapp.weatherappforavito.R
import com.zbistapp.weatherappforavito.databinding.FragmentMainBinding
import com.zbistapp.weatherappforavito.domain.responses.current.CurrentWeatherResponse
import com.zbistapp.weatherappforavito.domain.responses.details.DetailedWeatherResponse
import com.zbistapp.weatherappforavito.utils.Converter
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var factory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { factory }
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private val converter = Converter()
    private lateinit var hourlyAdapter: HourlyWeatherAdapter
    private lateinit var dailyAdapter: DailyWeatherAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        App.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getLocation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViews()

        with(binding) {
            searchLayout.setEndIconOnClickListener {
                viewModel.getLocationFromAddressName(inputEditText.text.toString())
                inputEditText.setText("")
                searchLayout.clearFocus()
            }

            inputEditText.setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> {
                        viewModel.getLocationFromAddressName(inputEditText.text.toString())
                        inputEditText.setText("")
                        searchLayout.clearFocus()
                        return@setOnKeyListener true
                    }
                    else -> {
                        return@setOnKeyListener false
                    }
                }
            }
        }

        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            viewModel.getCurrentWeather(it)
            viewModel.getDetailedWeather(it)
        }

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            initMainCurrentWeather(it)
        }

        viewModel.detailedWeatherLiveData.observe(viewLifecycleOwner) {
            initDetailedWeather(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    private fun initRecyclerViews() {
        hourlyAdapter = HourlyWeatherAdapter()
        binding.hourlyWeatherRecyclerView.adapter = hourlyAdapter
        dailyAdapter = DailyWeatherAdapter()
        binding.dailyWeatherRecyclerView.adapter = dailyAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initDetailedWeather(detailedWeather: DetailedWeatherResponse) {
        hourlyAdapter.listOfHourlyWeather = detailedWeather.hourlyWeather
        hourlyAdapter.notifyDataSetChanged()
        dailyAdapter.listOfDailyWeather = detailedWeather.dailyWeather
        dailyAdapter.notifyDataSetChanged()
    }

    private fun initMainCurrentWeather(currentWeather: CurrentWeatherResponse) {
        with(binding) {
            val temp = "${converter.kelvinToCelsius(currentWeather.main.temp)}??"
            temperatureTextView.text = temp
            val highTemp = "${converter.kelvinToCelsius(currentWeather.main.tempMax)}??/"
            highTemperatureTextView.text = highTemp
            val lowTemp = "${converter.kelvinToCelsius(currentWeather.main.tempMin)}??"
            lowTemperatureTextView.text = lowTemp
            weatherDescriptionTextView.text = currentWeather.weather[0].description
            val feelsLike =
                "${getString(R.string.feels_like_text)} ${
                    converter.kelvinToCelsius(
                        currentWeather.main.feelsLike
                    )
                }??"
            feelsLikeTextView.text = feelsLike
            locationTextView.text = currentWeather.name
            val lastUpdate =
                "${getString(R.string.last_update_text)} ${converter.timeToHours(currentWeather.date)}"
            lastUpdateTextView.text = lastUpdate
            Glide.with(mainPictureImageView)
                .load(
                    "${BuildConfig.IMG_URL}${currentWeather.weather[0].icon}.png"
                ).into(mainPictureImageView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_location -> {
                checkLocationPermission()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkLocationPermission() {
        when {
            (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.getLocation()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.why_i_need_permission_message),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(
                        getString(R.string.grant)
                    ) {
                        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }.show()
            }

            else -> {
                locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                viewModel.getLocation()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.if_has_not_permission_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}