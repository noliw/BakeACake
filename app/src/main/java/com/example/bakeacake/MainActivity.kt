package com.example.bakeacake

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


val wetList = mutableListOf<String>()
val dryList = mutableListOf<String>()


class MainActivity : AppCompatActivity() {
    lateinit var wetBtn: Button
    lateinit var dryBtn: Button
    lateinit var mixBtn: Button
    lateinit var dryInput: EditText
    lateinit var wetInput: EditText
    lateinit var dryNum: TextView
    lateinit var wetNum: TextView
    lateinit var ingredientList: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wetBtn = findViewById(R.id.wet_ingredients_btn)
        dryBtn = findViewById(R.id.dry_ingredients_btn)
        mixBtn = findViewById(R.id.mixIt_btn)
        dryInput = findViewById(R.id.dry_ingredients_et)
        wetInput = findViewById(R.id.wet_ingredients_et)
        dryNum = findViewById(R.id.dry_num_tv)
        wetNum = findViewById(R.id.wet_num_tv)
        ingredientList = findViewById(R.id.ingredient_list_tv)

        dryBtn.setOnClickListener {
            val ingredient = dryInput.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                dryList.add(ingredient)
                dryNum.text = "You have ${dryList.count()} dry ingredients."
                dryInput.text.clear() // clear the EditText content after adding the ingredient
            } else {
                hideKeyboard(this)
                Toast.makeText(this, "Please enter a valid ingredient.", Toast.LENGTH_SHORT).show()
            }
        }

        wetBtn.setOnClickListener {
            val ingredient = wetInput.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                wetList.add(ingredient)
                wetNum.text = "You have ${wetList.count()} wet ingredients."
                wetInput.text.clear() // clear the EditText content after adding the ingredient
            } else {
                hideKeyboard(this)
                Toast.makeText(this, "Please enter a valid ingredient.", Toast.LENGTH_SHORT).show()
            }
        }


        mixBtn.setOnClickListener {
            hideKeyboard(this)
            wetNum.text = ""
            dryNum.text = ""
            if (wetList.isNotEmpty() || dryList.isNotEmpty()) {
                ingredientList.text = """Your wet ingredients are:
           |-> ${wetList.joinToString("\n -> ")}
           |
           |Your dry ingredients are:
           |-> ${dryList.joinToString("\n -> ")}
       """.trimMargin()

                val dialogBuilder = AlertDialog.Builder(this)

                if (wetList.count() == 3 && dryList.count() == 3) {
                    dialogBuilder.setMessage("You have baked a delicious cake")
                        .setTitle("CONGRATULATIONS")
                        .setCancelable(true)
                        .setPositiveButton("Go back") { dialog, _ ->
                            dialog.cancel()
                        }
                        .setNegativeButton("Reset") { _, _ ->
                            reset()
                        }
                } else {
                    dialogBuilder.setMessage("To bake a cake, You need at least \n3 wet ingredients and 3 dry ingredients\nGo back and add more ingredients")
                        .setTitle("Bad Job!")
                        .setCancelable(false)
                        .setPositiveButton("Go Back!") { dialog, _ ->
                            dialog.cancel()
                        }
                        .setNegativeButton("Reset") { _, _ ->
                            reset()
                        }
                }

                dialogBuilder.show()
            } else {
                Toast.makeText(this, "You have no ingredients. \nPlease enter ingredients to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun reset() {
        wetList.clear()
        dryList.clear()
        wetNum.text = ""
        dryNum.text = ""
        ingredientList.text = ""
    }
    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        // Check if no view has focus:
        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.windowToken, 0)
        }
    }

}
