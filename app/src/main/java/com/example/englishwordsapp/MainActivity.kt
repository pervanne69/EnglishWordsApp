package com.example.englishwordsapp

import android.os.Bundle
import android.text.Layout
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with (binding) {
            btnResult.setOnClickListener {
                layoutResult.isVisible = false
                makeAnswerNeutral(layoutAnswer1, tvVariantValue1, tvVariantNumber1)
                makeAnswerNeutral(layoutAnswer2, tvVariantValue2, tvVariantNumber2)
                makeAnswerNeutral(layoutAnswer3, tvVariantValue3, tvVariantNumber3)
                makeAnswerNeutral(layoutAnswer4, tvVariantValue4, tvVariantNumber4)
                showNextQuestion(trainer)
                isAnswerSelected = false

            }
            btnSkip.setOnClickListener {
                isAnswerSelected = false
                showNextQuestion(trainer)
            }
        }
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with (binding) {
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS) {
                tvQuestionWord.isVisible = false
                layoutAnswers.isVisible = false
                btnSkip.text = "Complete"
            } else {
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.original

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    if (trainer.checkAnswer(0)) {
                        markAnswerCorrect(layoutAnswer1, tvVariantValue1, tvVariantNumber1)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer1, tvVariantValue1, tvVariantNumber1)
                        showResultMessage(false)
                    }
                }

                layoutAnswer2.setOnClickListener {
                    if (trainer.checkAnswer(1)) {
                        markAnswerCorrect(layoutAnswer2, tvVariantValue2, tvVariantNumber2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer2, tvVariantValue2, tvVariantNumber2)
                        showResultMessage(false)
                    }
                }

                layoutAnswer3.setOnClickListener {
                    if (trainer.checkAnswer(2)) {
                        markAnswerCorrect(layoutAnswer3, tvVariantValue3, tvVariantNumber3)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer3, tvVariantValue3, tvVariantNumber3)
                        showResultMessage(false)
                    }
                }

                layoutAnswer4.setOnClickListener {
                    if (trainer.checkAnswer(3)) {
                        markAnswerCorrect(layoutAnswer4, tvVariantValue4, tvVariantNumber4)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer4, tvVariantValue4, tvVariantNumber4)
                        showResultMessage(false)
                    }
                }

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

    private fun showResultMessage(isCorrect: Boolean) {
        val color: Int
        val messageText: String
        val resultIconResource: Int
        if (isCorrect) {
            color = ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            )
            messageText = resources.getString(R.string.title_correct)
            resultIconResource = R.drawable.ic_correct
        } else {
            color = ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            )
            messageText = resources.getString(R.string.title_wrong)
            resultIconResource = R.drawable.ic_wrong
        }
        with (binding) {
            layoutResult.setBackgroundColor(color)
            tvResultTitle.text = messageText
            btnResult.setTextColor(color)
            ivResultIcon.setImageResource(resultIconResource)
            isAnswerSelected = true
            val parentLayout = findViewById<ViewGroup>(R.id.layoutAnswers)
            val layoutAnswers = getInnerLayouts(parentLayout)
            disableAnswerClicks(layoutAnswers)
            transitionBetweenLayouts(btnSkip, layoutResult)
        }
    }
}