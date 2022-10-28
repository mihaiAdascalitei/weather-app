package com.madsq.weather.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.madsq.weather.databinding.FragmentSettingsBinding
import com.madsq.weather.presentation.darktheme.DarkThemeSelectionDialogFragment


/**
 * Created by mihai.adascalitei on 26.10.2022.
 */

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentSettingsBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDarkMode.setOnClickListener {
            DarkThemeSelectionDialogFragment.newInstance().show(childFragmentManager)
        }

        binding.tvTextMagic.setOnClickListener {
            binding.groupMagicText.isVisible = true
        }

        binding.icClose.setOnClickListener {
            binding.groupMagicText.isVisible = false
        }
    }

}