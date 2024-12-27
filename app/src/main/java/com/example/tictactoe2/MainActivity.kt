package com.example.tictactoe2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var statusTextView: TextView
    private lateinit var playAgainButton: Button

    private var isPlayerX = true
    private var gameActive = true
    private val board = Array(3) { Array(3) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons and views
        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )
        statusTextView = findViewById(R.id.statusTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        // Set onClickListeners for buttons
        for (button in buttons) {
            button.setOnClickListener { onCellClick(it) }
        }

        // Play again button
        playAgainButton.setOnClickListener { resetGame() }
    }

    private fun onCellClick(view: View) {
        if (!gameActive) return

        val button = view as Button
        val tag = button.tag.toString().toInt()
        val row = tag / 3
        val col = tag % 3

        if (board[row][col].isEmpty()) {
            board[row][col] = if (isPlayerX) "X" else "O"
            button.text = board[row][col]
            button.setTextColor(
                ContextCompat.getColor(
                    this,
                    if (isPlayerX) R.color.blue else R.color.red
                )
            )
            if (checkWin()) {
                gameActive = false
                statusTextView.text = "Player ${board[row][col]} Wins!"
                playAgainButton.visibility = View.VISIBLE
            } else if (isBoardFull()) {
                gameActive = false
                statusTextView.text = "It's a Draw!"
                playAgainButton.visibility = View.VISIBLE
            } else {
                isPlayerX = !isPlayerX
                statusTextView.text = "Player ${if (isPlayerX) "X" else "O"}'s Turn"
            }
        }
    }

    private fun checkWin(): Boolean {
        // Check rows
        for (row in 0..2) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0].isNotEmpty()) {
                return true
            }
        }
        // Check columns
        for (col in 0..2) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col].isNotEmpty()) {
                return true
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) {
            return true
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) {
            return true
        }

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell.isEmpty()) return false
            }
        }
        return true
    }

    private fun resetGame() {
        isPlayerX = true
        gameActive = true
        for (row in 0..2) {
            for (col in 0..2) {
                board[row][col] = ""
            }
        }
        for (button in buttons) {
            button.text = ""
        }
        statusTextView.text = "Player X's Turn"
        playAgainButton.visibility = View.GONE
    }
}
