package com.example.worldnews.view.changeCountry

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.worldnews.databinding.ItemCountyBinding
import com.example.worldnews.model.Country

class CountryAdapter(
    private val context: Context,
    private val countryList: List<Country>,
    private var selectedCountryCode: String
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Any {
        return countryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return countryList.size
    }

    fun getSelectedCountryCode(): String {
        return selectedCountryCode
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val country = countryList[position]

        val view: View
        val binding: ItemCountyBinding

        if (convertView == null) {
            binding = ItemCountyBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemCountyBinding
        }

        binding.apply {
            textCounty.text = country.countryCode.code
            imageCountry.setImageDrawable(ContextCompat.getDrawable(context, country.countryImage))
            imageCountry.setBackgroundColor(if (country.countryCode.code == selectedCountryCode) {
                Color.LTGRAY
            } else {
                Color.TRANSPARENT
            })

            imageCountry.setOnClickListener {
                selectedCountryCode = country.countryCode.code
                notifyDataSetChanged()
            }
        }
        return view
    }
}
