package com.prempal.bankregistration.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel(){

    //region Initialising Boolean Livedata
    private val _isPanValid = MutableLiveData<Boolean>()
    val isPanValid: LiveData<Boolean> get() = _isPanValid

    private val _isDobDayValid = MutableLiveData<Boolean>()
    val isDobDayValid: LiveData<Boolean> get() = _isDobDayValid

    private val _isDobMonthValid = MutableLiveData<Boolean>()
    val isDobMonthValid: LiveData<Boolean> get() = _isDobMonthValid

    private val _isDobYearValid = MutableLiveData<Boolean>()
    val isDobYearValid: LiveData<Boolean> get() = _isDobYearValid

    private val _isNextButtonEnabled = MutableLiveData<Boolean>()
    val isNextButtonEnabled: LiveData<Boolean> get() = _isNextButtonEnabled
    //endregion

    //region Setting values to initialised variables
    init {
        _isPanValid.value = false
        _isDobDayValid.value = false
        _isDobMonthValid.value = false
        _isDobYearValid.value = false
        _isNextButtonEnabled.value = false
    }
    //endregion

    //region Validation logic for Pancard
    fun validatePan(input: String) {
        viewModelScope.launch {
            // PAN card number regex: 5 letters, 4 digits, 1 letter (e.g., ABCDE1234F)
            val panRegex = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")
            _isPanValid.value = panRegex.matches(input)
            updateNextButtonState()
        }
    }
    //endregion

    //region Validation logic for Birth Day
    fun validateDobDay(input: String) {
        viewModelScope.launch {
            val number = input.toIntOrNull()
            _isDobDayValid.value = number != null && number in 1..31
            updateNextButtonState()
        }
    }
    //endregion

    //region Validation logic for Birth Month
    fun validateDobMonth(input: String) {
        viewModelScope.launch {
            val number = input.toIntOrNull()
            _isDobMonthValid.value = number != null && number in 1..12
            updateNextButtonState()
        }
    }
    //endregion

    //region Validation logic for Birth Year
    fun validateDobYear(input: String) {
        viewModelScope.launch {
            val number = input.toIntOrNull()
            _isDobYearValid.value = number != null && number in 1925..2024
            updateNextButtonState()
        }
    }
    //endregion

    //region Updating Next Button State
    private fun updateNextButtonState() {
        _isNextButtonEnabled.value = _isPanValid.value == true &&
                _isDobDayValid.value == true &&
                _isDobMonthValid.value == true &&
                _isDobYearValid.value == true
    }
    //endregion

}