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

class   LoginFragment : Fragment() {

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
                        et1.error = "Se debe introducir un nombre para registrase"
                    }
                    if(et2.text!!.isEmpty()) {
                        et2.error = "Se debe introducir un email para registrase"
                    }
                } else {
                    Snackbar.make(root, "Iniciando sesión", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Cerrar") {}
                        .show()
                    // Aquí irá la navegación al ScaffoldFragment cuando el login sea exitoso
                    // findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }
            }

            // Login con Google
            iconButton2.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Google", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Login con Apple
            iconButton3.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Apple", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Login con Meta (antes navegaba a ContactoActivity)
            iconButton.setOnClickListener {
                findNavController().navigate(R.id.login_to_register)
            }

            // Registro (antes navegaba a MainActivity2)
            registerButton.setOnClickListener {
                findNavController().navigate(R.id.login_to_register)
            }

            // Olvidó contraseña
            forgotButton.setOnClickListener {
                Snackbar.make(root, "Olvidaste tu contraseña?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evitar memory leaks
    }
}