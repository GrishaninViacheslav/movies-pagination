package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import io.github.grishaninvyacheslav.theatre_movies_pagination.R
import io.github.grishaninvyacheslav.theatre_movies_pagination.databinding.ViewScoreBarBinding

class ScoreBar : FrameLayout {
    var value = 0f
        set(value) {
            field = value
            with(binding) {
                scoreBar.progress = this@ScoreBar.value
                scoreValue.text = this@ScoreBar.value.toInt().toString()
            }
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ScoreBar,
            0, 0
        )
        value = typedArray.getFloat(R.styleable.ScoreBar_value, value)
        addView(binding.root)
    }

    private val binding =
        ViewScoreBarBinding.inflate(LayoutInflater.from(context))
}