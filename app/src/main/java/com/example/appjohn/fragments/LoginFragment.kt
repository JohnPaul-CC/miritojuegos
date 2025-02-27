package com.example.appjohn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    // Esta propiedad solo es válida entre onCreateView y onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            // Login principal
            button2.setOnClickListener {
                if(et1.text!!.isEmpty() || et2.text!!.isEmpty()) {
                    if(et1.text!!.isEmpty()) {
                        et1.error = "Se debe introducir un nombre para iniciar sesión"
                    }
                    if(et2.text!!.isEmpty()) {
                        et2.error = "Se debe introducir una contraseña para iniciar sesión"
                    }
                } else {
                    // Para pruebas, navegar directamente al ScaffoldFragment
                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }
            }

            // Login con Google
            iconButton2.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Google", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Login con Apple
            iconButton3.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Apple", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Login con Meta
            iconButton.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Meta", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Registro (navegar a RegisterFragment)
            registerButton.setOnClickListener {
                findNavController().navigate(R.id.login_to_register)
            }

            // Olvidó contraseña
            forgotButton.setOnClickListener {
                Snackbar.make(root, "Recuperar contraseña", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Botón temporal para probar la navegación a ContactoFragment
            val tempButton = button2
            tempButton.setOnLongClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_contactoFragment)
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evitar memory leaks
    }
}