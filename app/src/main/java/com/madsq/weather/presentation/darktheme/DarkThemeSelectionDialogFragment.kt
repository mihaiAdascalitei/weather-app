package com.madsq.weather.presentation.darktheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.madsq.weather.databinding.FragmentDarkThemeSelectionBinding


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

class DarkThemeSelectionDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDarkThemeSelectionBinding? = null
    private val binding: FragmentDarkThemeSelectionBinding
        get() = _binding!!

    companion object {
        private const val fragmentTag = "DarkThemeSelectionDialogFragment"
        fun newInstance() = DarkThemeSelectionDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDarkThemeSelectionBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icClose.setOnClickListener { dismiss() }
        binding.rgThemes.apply {
            check(currentThemeId)
            setOnCheckedChangeListener { _, id ->
                val themeId = findThemeForId(id)
                DarkThemeUtil.updateDarkThemeMode(themeId)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun show(fragmentManager: FragmentManager) {
        showNow(fragmentManager, fragmentTag)
    }

    private val currentThemeId: Int
        get() = when (DarkThemeUtil.darkThemeMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.rbLight.id
            AppCompatDelegate.MODE_NIGHT_YES -> binding.rbDark.id
            else -> binding.rbDefault.id
        }

    private fun findThemeForId(id: Int) = when (id) {
        binding.rbDark.id -> AppCompatDelegate.MODE_NIGHT_YES
        binding.rbLight.id -> AppCompatDelegate.MODE_NIGHT_NO
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

}