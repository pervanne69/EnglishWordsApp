package com.example.englishwordsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.englishwordsapp.databinding.ActivityLearnWordBinding
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.Button
import androidx.core.view.accessibility.AccessibilityEventCompat.ContentChangeType
import androidx.core.view.isVisible


// Подкорректировать layouts


class MainActivity : AppCompatActivity() {
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

        // нейтральный ответ
        // корректный ответ
        // некорректный ответ

        with(binding) {
            layoutAnswer3.setOnClickListener {
                markAnswerCorrect()
            }
            layoutAnswer1.setOnClickListener {
                markAnswerWrong()
            }
            btnResult.setOnClickListener {
                makeAnswerNeutral()
            }
        }


    }

    private fun makeAnswerNeutral() {
        with (binding) {
            for (layout in listOf(layoutAnswer3, layoutAnswer1)) {
                 layout.background = ContextCompat.getDrawable(
                     this@MainActivity,
                     R.drawable.shape_rounded_containers
                 )
            }
            for (textView in listOf(tvVariantValue1, tvVariantValue3)) {
                textView.setTextColor(ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                ))
            }

            for (textView in listOf(tvVariantNumber1, tvVariantNumber3)) {
                textView.apply{
                    background = ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.shape_rounded_variants
                    )
                    setTextColor(ContextCompat.getColor(
                        this@MainActivity,
                        R.color.textVariantsColor
                    ))
                }
            }
            transitionBetweenButtons(btnResult, btnSkip)
        }
    }

    private fun markAnswerWrong() {
        with(binding) {
            layoutAnswer1.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_containers_wrong
            )
            tvVariantNumber1.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants_wrong
            )
            tvVariantNumber1.setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            ))
            tvVariantValue1.setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            ))
            layoutResult.setBackgroundColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            ))

            ivResultIcon.setImageDrawable(ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.ic_wrong
            ))
            tvResultTitle.text = resources.getString(R.string.title_wrong)
            btnResult.setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            ))
            transitionBetweenBtnAndLayout(btnSkip, layoutResult)
        }
    }

    private fun markAnswerCorrect() {
        with(binding) {
            layoutAnswer3.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_containers_correct
            )
            tvVariantNumber3.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants_correct
            )
            tvVariantNumber3.setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            ))
            tvVariantValue3.setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            ))

            layoutResult.setBackgroundColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            ))

            ivResultIcon.setImageDrawable(ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.ic_correct
            ))
            tvResultTitle.text = resources.getString(R.string.title_correct)
            btnResult.setTextColor(ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            ))


            transitionBetweenBtnAndLayout(btnSkip, layoutResult)
        }
    }

    private fun transitionBetweenBtnAndLayout(element1: Button, element2: View) {
        val transition = Slide(Gravity.START)

        TransitionManager.beginDelayedTransition(binding.root, transition)

        element1.isVisible = false
        element2.isVisible = true
    }

    private fun transitionBetweenButtons(element1: Button, element2: Button) {
        val transition = Slide(Gravity.START)

        TransitionManager.beginDelayedTransition(binding.root, transition)

        element1.isVisible = false
        element2.isVisible = true
    }

}