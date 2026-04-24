package com.example.lifehackquiz

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lifehackquiz.ui.theme.LifeHackQuizTheme

// Lindelwa Ndlovu
// ST10516574

class MainActivity : ComponentActivity() {
    // array to store all quiz questions
        private val questions = arrayOf(
        "Writing things down helps you remember better.",
        "Eating sugar make children hyperactive.",
        "Sleeping well improves your mood and focus."
    )

    //array to store correct answers
    private val answers = arrayOf (true, false, true)
    //keeps track of which question the user is on
    private var currentQuestion = 0
    //stores the user's score
    private var score = 0
    private val feedbackList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }
    private fun showWelcomeScreen() {
        setContentView(R.layout.activity_welcome)
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            currentQuestion = 0
            score = 0
            feedbackList.clear()
            showQuestionScreen()
        }
    }
    // connect this activity to its XML to kotlin code
    private fun showQuestionScreen() {
        setContentView(R.layout.activity_question)
        // link UI elements from XML to Kotlin code
        val questionText = findViewById<TextView>(R.id.questionText)
        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)
        val textFeedback = findViewById<TextView>(R.id.textFeedback)
        val nextButton = findViewById<Button>(R.id.nextButton)

        //function to declare the current question
        questionText.text = questions[currentQuestion]
        textFeedback.text = ""
        var answered = false

        //when the user clicks true
        trueButton.setOnClickListener {
            if (!answered) {
                checkAnswer( true,textFeedback) //true - Hack
                answered = true
            }
        }

        //when the user clicks false
        falseButton.setOnClickListener {
            if (!answered) {
                checkAnswer(false, textFeedback) // false - Myth
                answered = true
            }
        }

            //when the user clicks the next button
            nextButton.setOnClickListener {
                currentQuestion++ // move to next question
                //checking if there are more questions
                if (currentQuestion < questions.size) {
                    showQuestionScreen()
                } else {
                    showScoreScreen() // If no more questions, go to score screen
                }
            }
        }

        private fun checkAnswer(userAnswer: Boolean, textFeedback: TextView) {
            val correct = answers[currentQuestion]
            if (userAnswer == correct) {
                textFeedback.text = "Correct!"
                score++ // Increase score
                feedbackList.add("Q${currentQuestion + 1}:")
            } else {
                textFeedback.text = "Incorrect!"
                feedbackList.add("Q${currentQuestion + 1}:")
            }
        }

        private fun showScoreScreen() {
            setContentView(R.layout.activity_score)

            val scoreText = findViewById<TextView>(R.id.scoreText)
            val finalFeedback = findViewById<TextView>(R.id.finalFeedback)
            val reviewButton = findViewById<Button>(R.id.reviewButton)
            val exitButton = findViewById<Button>(R.id.exitButton)

            // Compare user answer with correct answer
            scoreText.text = "You scored $score out of ${questions.size}"
            finalFeedback.text = if (score >= 2) "Great Job!" else "Keep Practicing!" // show wrong message

            reviewButton.setOnClickListener {
                val answers = questions.mapIndexed { index, q ->
                    "${index + 1}. $q\nAnswer: ${answers[index]}"
                }.joinToString("\n\n")
                Toast.makeText(this, answers, Toast.LENGTH_LONG).show()
            }

            exitButton.setOnClickListener {
                finish() // close the screen
            }

        }
    }
