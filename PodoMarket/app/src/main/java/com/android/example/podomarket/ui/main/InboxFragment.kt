package com.android.example.podomarket.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.example.podomarket.R
import com.android.example.podomarket.databinding.FragmentInboxBinding


class InboxFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentInboxBinding by lazy {
            DataBindingUtil.inflate(inflater, R.layout.fragment_inbox, container, false)
        }
        binding.run {
            toolBar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
                inflateMenu(R.menu.app_bar_fragment_inbox)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit_button -> {
                            Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT)
                                .show()
                            true
                        }
                        R.id.delete_button -> {
                            Toast.makeText(activity, "Delete", Toast.LENGTH_SHORT)
                                .show()
                            true
                        }
                        else -> false
                    }
                }
            }
        }
        return binding.root
    }


}