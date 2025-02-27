package com.example.appjohn.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    // LiveData para manejar los errores de validación
    private val _nameError = MutableLiveData<String?>()
    val nameError: LiveData<String?> = _nameError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _dateError = MutableLiveData<String?>()
    val dateError: LiveData<String?> = _dateError

    // LiveData para informar sobre el estado de la validación
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    // Función para validar el formulario de registro
    fun validateRegisterForm(name: String, email: String, password: String, date: String): Boolean {
        var isValid = true

        // Validar que el campo de nombre no esté vacío
        if (name.isEmpty()) {
            _nameError.value = "Se debe introducir un nombre para registrarse"
            isValid = false
        } else {
            _nameError.value = null
        }

        // Validar que el campo de email no esté vacío
        if (email.isEmpty()) {
            _emailError.value = "Se debe introducir un email para registrarse"
            isValid = false
        } else {
            _emailError.value = null
        }

        // Validar que el campo de contraseña no esté vacío
        if (password.isEmpty()) {
            _passwordError.value = "Se debe introducir una contraseña para registrarse"
            isValid = false
        } else {
            _passwordError.value = null
        }

        // Validar que el campo de fecha no esté vacío
        if (date.isEmpty()) {
            _dateError.value = "Se debe introducir una fecha de nacimiento para registrarse"
            isValid = false
        } else {
            _dateError.value = null
        }

        // Actualizar el estado de validación del formulario
        _isFormValid.value = isValid

        return isValid
    }

    // Función para limpiar errores
    fun clearErrors() {
        _nameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _dateError.value = null
    }
}