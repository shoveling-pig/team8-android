package com.android.example.podomarket.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.example.podomarket.R
import com.android.example.podomarket.databinding.FragmentHiddenProductBinding

class HiddenProductFragment : Fragment() {

    lateinit var binding: FragmentHiddenProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hidden_product, container, false)

        return binding.root
    }

}