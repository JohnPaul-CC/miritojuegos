package com.example.appjohn.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val pswLength = 6

    // LiveData para manejar los errores de validación
    private val _userError = MutableLiveData<String?>()
    val userError: LiveData<String?> = _userError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    // LiveData para informar sobre el estado de la validación
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    // Función para validar el formulario de login
    fun validateLoginForm(username: String, password: String): Boolean {
        var isValid = true

        // Validar que el campo de usuario no esté vacío
        if (username.length <= 3) {
            _userError.value = "El nombre debe tener más de 3 carácteres"
            isValid = false
        } else {
            _userError.value = null
        }

        // Validar que el campo de contraseña no esté vacío
        if (password.length <= pswLength) {
            _passwordError.value = "La contraseña debe tener al menos $pswLength caracteres"
            isValid = false
        } else {
            _passwordError.value = null
        }

        // Actualizar el estado de validación del formulario
        _isFormValid.value = isValid

        return isValid
    }

    // Función para limpiar errores
    fun clearErrors() {
        _userError.value = null
        _passwordError.value = null
    }
}