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
import androidx.core.view.isVisible


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
//            btnResult.setOnClickListener {
//                transitionBetweenBtnAndLayout(btnSkip, layoutResult)
//            }
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

        element1.isVisible = !element1.isVisible
        element2.isVisible = !element1.isVisible
    }

}


// findViewById
// ViewBinding - генерирует привязку для каждого файла в разметке в проекте
// что позволяет получить доступ к элементам разметки через этот объект
// создается только один раз при создрании Activity

// findViewById
//        val tvQuestionWord: TextView = findViewById(R.id.tvQuestionWord)
//        tvQuestionWord.text = "52"
//        tvQuestionWord.setTextColor(Color.BLUE)
//        tvQuestionWord.setTextColor(Color.parseColor("#000000"))
//        tvQuestionWord.setTextColor(ContextCompat.getColor(this, R.color.questionWordTitle))
// this - контекст текущей Activity

// ViewBinding


//        with (binding) {
//            tvQuestionWord.text = "AndroidSprint.ru"
//            tvQuestionWord.setTextColor(Color.GRAY)
//            imageButton.isVisible = false
//        }