package com.zemoga.features.posts.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.zemoga.databinding.CommentItemLayoutBinding
import com.zemoga.features.posts.data.models.PostCommentModel
import java.util.Locale

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CommentItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = CommentItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    private lateinit var data: PostCommentModel

    @ModelProp
    fun setData(data: PostCommentModel) {
        this.data = data
    }

    @AfterPropsSet
    fun bindData() {
        binding.textViewCommentTitle.text = data.body.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}
