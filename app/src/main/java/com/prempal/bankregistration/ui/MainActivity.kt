package com.prempal.bankregistration.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.prempal.bankregistration.R
import com.prempal.bankregistration.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this  //binding lifecycleOwner
        binding.viewModel = viewModel //binding MainViewModel with DataBinding viewModel

        //region TextFields validation

        // Observing PANCARD validation state, if the entered pan number is valid(eg. QWERTA1234Y) then the stroke colour of the text input field will be set to blue else red.
        viewModel.isPanValid.observe(this) { isValid ->
            val color = if (isValid) R.color.blue else R.color.red
            binding.panNumberTil.setBoxStrokeColor(getColor(color))
        }

        // Observing Birth Day validation state, if the entered number is valid(will be valid if entered number is between 1-31) then the stroke colour of the text input field will be set to blue else red.
        viewModel.isDobDayValid.observe(this) { isValid ->
            val color = if (isValid) R.color.blue else R.color.red
            binding.dobDayTil.setBoxStrokeColor(getColor(color))
        }

        // Observing Birth Month validation state, if the entered number is valid(will be valid if entered number is between 1-12) then the stroke colour of the text input field will be set to blue else red.
        viewModel.isDobMonthValid.observe(this) { isValid ->
            val color = if (isValid) R.color.blue else R.color.red
            binding.dobMonthTil.setBoxStrokeColor(getColor(color))
        }

        // Observing Birth Year validation state, if the entered number is valid(will be valid if entered number is between 1925-2024) then the stroke colour of the text input field will be set to blue else red.
        viewModel.isDobYearValid.observe(this) { isValid ->
            val color = if (isValid) R.color.blue else R.color.red
            binding.dobYearTil.setBoxStrokeColor(getColor(color))
        }
        //endregion

        //region Adding TextWatcher to textFields

        // Add TextWatcher to PANNUMBER EditText, PAN number is of 10 characters so the max length of this edit text field is set to 10 and user won't be able to insert more than 10 characters,
        // validation will be performed everytime after inserting or deleting each character.
        binding.panNumberEt.doOnTextChanged { text, _, _, _ ->
            viewModel.validatePan(text.toString())
        }

        // Add TextWatcher to Birth Day EditText, in Date a Day is of 2 characters(only numbers will be accepted by default) so the max length of this edit text field is set to 2 and user won't be able to insert more than 2 characters,
        // validation will be performed everytime after inserting or deleting each character.
        binding.dobDayEt.doOnTextChanged { text, _, _, _ ->
            viewModel.validateDobDay(text.toString())
        }

        // Add TextWatcher to Birth Month EditText, in Date a Month is of 2 characters(only numbers will be accepted by default) so the max length of this edit text field is set to 2 and user won't be able to insert more than 2 characters,
        // validation will be performed everytime after inserting or deleting each character.
        binding.dobMonthEt.doOnTextChanged { text, _, _, _ ->
            viewModel.validateDobMonth(text.toString())
        }

        // Add TextWatcher to Birth Year EditText, in Date a Year is of 4 characters(only numbers will be accepted by default) so the max length of this edit text field is set to 4 and user won't be able to insert more than 4 characters,
        // validation will be performed everytime after inserting or deleting each character.
        binding.dobYearEt.doOnTextChanged { text, _, _, _ ->
            viewModel.validateDobYear(text.toString())
        }
        //endregion

        //region NextButton OnClick
        binding.nextButton.setOnClickListener{
            Toast.makeText(this@MainActivity, "Details Submitted Successfully", Toast.LENGTH_SHORT).show() //to show Toast
            finish() //to finish the activity
        }
        //endregion

        //region No Pancard Link OnClick
        binding.noPanTv.setOnClickListener{
            finish() //to finish the activity
        }
        //endregion

    }
}