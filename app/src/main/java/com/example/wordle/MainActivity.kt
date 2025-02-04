package com.example.wordle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    var wordToGuess = ""
    var currentGuess = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        Log.v("answer", wordToGuess)


        val inputField = findViewById<EditText>(R.id.InputField)
        val button = findViewById<Button>(R.id.button)
        val guess1 = findViewById<TextView>(R.id.guess1out)
        val check1 = findViewById<TextView>(R.id.check1out)
        val guess2 = findViewById<TextView>(R.id.guess2out)
        val check2 = findViewById<TextView>(R.id.check2out)
        val guess3 = findViewById<TextView>(R.id.guess3out)
        val check3 = findViewById<TextView>(R.id.check3out)
        val answer = findViewById<TextView>(R.id.Answer)
        val answerText = findViewById<TextView>(R.id.AnswerText)
        answer.text = wordToGuess

        button.setOnClickListener {
            //make guess
            val userInput = inputField.getText().toString().uppercase()
            val check = checkGuess(userInput)

            if(currentGuess == 1){
                guess1.text = userInput
                check1.text = check
            }
            else if(currentGuess == 2){
                guess2.text = userInput
                check2.text = check
            }
            else if(currentGuess == 3){
                guess3.text = userInput
                check3.text = check
                button.setAlpha(.5f)
                button.isClickable = false
                answer.visibility = View.VISIBLE
                answerText.visibility = View.VISIBLE
            }
            if(check == "OOOO"){
                Toast.makeText(this, "You guessed correctly!", LENGTH_LONG).show()
                button.setAlpha(.5f)
                button.isClickable = false
                answer.visibility = View.VISIBLE
                answerText.visibility = View.VISIBLE
            }
            currentGuess++
        }

    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}