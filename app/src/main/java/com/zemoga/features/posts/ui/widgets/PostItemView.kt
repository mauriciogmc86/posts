package com.zemoga.features.posts.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.zemoga.R
import com.zemoga.databinding.PostItemLayoutBinding
import com.zemoga.features.posts.data.models.PostModel
import java.util.Locale

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PostItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = PostItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    private lateinit var data: PostModel

    @ModelProp
    fun setData(data: PostModel) {
        this.data = data
    }

    @CallbackProp
    fun setListener(listener: Listener?) {
        binding.root.setOnClickListener {
            listener?.onPostClicked(data)
        }
    }

    @AfterPropsSet
    fun bindData() {
        if (data.isFavorite) {
            binding.imageButtonIcon.setImageResource(R.drawable.ic_star)
        } else {
            binding.imageButtonIcon.setImageResource(R.drawable.ic_arrow_forward)
        }

        binding.imageButtonRead.visibility = if (data.isRead) View.GONE else View.VISIBLE
        binding.textViewPostTitle.text = data.title.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    interface Listener {
        fun onPostClicked(data: PostModel)
    }
}
