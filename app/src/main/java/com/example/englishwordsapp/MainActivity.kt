package com.example.englishwordsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.englishwordsapp.databinding.ActivityLearnWordBinding
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.LinearLayout
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private var isAnswerSelected = false
    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")


    //    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // результат вызова этого метода экз
        //   емпляр класса ActivityMainBinding
        // у которой есть ссылки на все TextView

        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        with(binding) {
            btnResult.setOnClickListener {
                makeAnswerNeutral(
                    layoutAnswer1,
                    tvVariantValue1,
                    tvVariantNumber1
                )
                makeAnswerNeutral(
                    layoutAnswer3,
                    tvVariantValue3,
                    tvVariantNumber3
                )
            }
            layoutAnswer3.setOnClickListener {
                markAnswerCorrect(
                    layoutAnswer3,
                    tvVariantValue3,
                    tvVariantNumber3
                )
            }
            layoutAnswer1.setOnClickListener {
                markAnswerWrong(
                    layoutAnswer1,
                    tvVariantValue1,
                    tvVariantNumber1
                )
            }
        }


    }

    private fun makeAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantValue: TextView,
        tvVariantNumber: TextView
    ) {
        Log.d("MainActivity", "makeAnswerNeutral called")
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers,
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.textVariantsColor
            )
        )

        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants
            )
            setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                )
            )
        }
        with(binding) {
            isAnswerSelected = false
            val parentLayout = findViewById<ViewGroup>(R.id.layoutAnswers)
            val layoutAnswers = getInnerLayouts(parentLayout)
            enableAnswerClicks(layoutAnswers)
            transitionBetweenLayouts(layoutResult, btnSkip)
        }
    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantValue: TextView,
        tvVariantNumber: TextView
    ) {
        Log.d("MainActivity", "makeAnswerWrong called")
        if (isAnswerSelected) return
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong
        )
        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )
        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            )
        )
        with(binding) {
            layoutResult.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.wrongAnswerColor
                )
            )
            ivResultIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_wrong
                )
            )
            tvResultTitle.text = resources.getString(R.string.title_wrong)
            btnResult.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.wrongAnswerColor
                )
            )
            isAnswerSelected = true
            val parentLayout = findViewById<ViewGroup>(R.id.layoutAnswers)
            val layoutAnswers = getInnerLayouts(parentLayout)
            disableAnswerClicks(layoutAnswers)
            transitionBetweenLayouts(btnSkip, layoutResult)
        }
    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantValue: TextView,
        tvVariantNumber: TextView
    ) {
        Log.d("MainActivity", "makeAnswerCorrect called")
        if (isAnswerSelected) return
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct
        )
        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )
        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            )
        )
        with(binding) {
            layoutResult.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.correctAnswerColor
                )
            )

            ivResultIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_correct
                )
            )
            tvResultTitle.text = resources.getString(R.string.title_correct)
            btnResult.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.correctAnswerColor
                )
            )
            isAnswerSelected = true
            val parentLayout = findViewById<ViewGroup>(R.id.layoutAnswers)
            val layoutAnswers = getInnerLayouts(parentLayout)
            disableAnswerClicks(layoutAnswers)
            transitionBetweenLayouts(btnSkip, layoutResult)

        }
    }

    private fun transitionBetweenLayouts(element1: View, element2: View) {
        val transition = Slide(Gravity.START)

        TransitionManager.beginDelayedTransition(binding.root, transition)

        if (element1.isVisible && !element2.isVisible) {
            element1.isVisible = false
            element2.isVisible = true
        } else if (!element1.isVisible && element2.isVisible) {
            element1.isVisible = true
            element2.isVisible = false
        }

        println(element1.isVisible)
        println(element2.isVisible)

    }

    private fun getInnerLayouts(parentLayout: ViewGroup): List<LinearLayout> {
        val innerLayouts = mutableListOf<LinearLayout>()
        for (i in 0 until parentLayout.childCount) {
            val childView = parentLayout.getChildAt(i)
            if (childView is LinearLayout) {
                innerLayouts.add(childView)
            }
        }
        return innerLayouts
    }

    private fun disableAnswerClicks(layouts: List<LinearLayout>) {
        for (layout in layouts) {
            layout.isClickable = false
        }
    }

    private fun enableAnswerClicks(layouts: List<LinearLayout>) {
        for (layout in layouts) {
            layout.isClickable = true
        }
    }
}