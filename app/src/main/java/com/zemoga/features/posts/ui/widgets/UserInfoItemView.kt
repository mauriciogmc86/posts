package com.zemoga.features.posts.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.zemoga.databinding.UserInfoItemLayoutBinding
import androidx.constraintlayout.widget.ConstraintLayout

class UserInfoItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = UserInfoItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    fun setData(title: String, value: String) {
        binding.textViewTitle.text = title
        binding.textViewValue.text = value
    }
}