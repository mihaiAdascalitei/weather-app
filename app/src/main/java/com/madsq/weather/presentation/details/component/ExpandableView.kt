package com.madsq.weather.presentation.details.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.madsq.weather.databinding.ExpandableViewBinding
import com.madsq.weather.presentation.details.data.model.ExpandableItem


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

class ExpandableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
    }

    private val binding = ExpandableViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(item: ExpandableItem) {
        binding.tvTitle.text = item.title
        binding.tvData.text = item.data

        binding.root.setOnClickListener {
            binding.tvData.apply {
                maxLines = if (maxLines == 2) {
                    Int.MAX_VALUE
                } else {
                    2
                }
            }
        }
    }
}