package com.example.worldnews.view.changeCountry

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.worldnews.R
import com.example.worldnews.common.PreferenceHelper
import com.example.worldnews.databinding.FragmentChangeContryBinding
import com.example.worldnews.model.Country
import com.example.worldnews.model.CountryCode

class ChangeCountryDialogFragment : DialogFragment() {

    var clickListener: ChangeCountryListener? = null

    private lateinit var binding: FragmentChangeContryBinding

    companion object {
        private const val FRAGMENT_TAG = "custom_dialog"

        fun newInstance() = ChangeCountryDialogFragment()

        fun show(fragmentManager: FragmentManager): ChangeCountryDialogFragment {
            val dialog = newInstance()
            dialog.show(fragmentManager, FRAGMENT_TAG)
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChangeContryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private val countryList = listOf (
        Country(CountryCode.UNITED_ARAB_EMIRATES, R.drawable.united_arab_emirates),
        Country(CountryCode.ARGENTINA, R.drawable.argentina),
        Country(CountryCode.AUSTRALIA, R.drawable.australia),
        Country(CountryCode.BRAZIL, R.drawable.brazil),
        Country(CountryCode.BULGARIA, R.drawable.bulgaria),
        Country(CountryCode.CANADA, R.drawable.canada),
        Country(CountryCode.CHINA, R.drawable.china),
        Country(CountryCode.FRANCE, R.drawable.france),
        Country(CountryCode.ITALY, R.drawable.italy),
        Country(CountryCode.JAPAN, R.drawable.japan),
        Country(CountryCode.RUSSIA, R.drawable.russia),
        Country(CountryCode.SINGAPORE, R.drawable.singapore),
        Country(CountryCode.SOUTH_KOREA, R.drawable.south_korea),
        Country(CountryCode.SWEDEN, R.drawable.sweden),
        Country(CountryCode.SWITZERLAND, R.drawable.switzerland),
        Country(CountryCode.THAILAND, R.drawable.thailand),
        Country(CountryCode.TURKEY, R.drawable.turkey),
        Country(CountryCode.UNITED_KINGDOM, R.drawable.united_kingdom),
        Country(CountryCode.UNITED_STATES_OF_STATE, R.drawable.united_states_of_america)
    )

    private fun init() {
        val selectedCountryCode = PreferenceHelper.countryCode

        binding.apply {
            val adapter = CountryAdapter(requireContext(), countryList, selectedCountryCode)
            gridView.adapter = adapter

            buttonChange.setOnClickListener {
                PreferenceHelper.countryCode = adapter.getSelectedCountryCode()
                clickListener?.onClickChange()
                dismiss()
            }
            buttonCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            clickListener = context as ChangeCountryListener
        } catch (e: ClassCastException) {
            Log.e(FRAGMENT_TAG,  "리스너를 상속해주세요")
        }
    }

    interface ChangeCountryListener {
        fun onClickChange()
    }
}