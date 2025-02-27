package com.example.appjohn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentLoginBinding
import com.example.appjohn.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

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

        //Necesario para que al usuario se le avise de los errores
        setupObservers()

        setupClickListeners()
    }

    private fun setupObservers() {
        // Observar errores de usuario
        viewModel.userError.observe(viewLifecycleOwner) { error ->
            binding.et1.error = error
        }

        // Observar errores de contraseña
        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.et2.error = error
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            // Login principal
            button2.setOnClickListener {
//                if(et1.text!!.isEmpty() || et2.text!!.isEmpty()) {
//                    if(et1.text!!.isEmpty()) {
//                        et1.error = "Se debe introducir un nombre para iniciar sesión"
//                    }
//                    if(et2.text!!.isEmpty()) {
//                        et2.error = "Se debe introducir una contraseña para iniciar sesión"
//                    }
//                } else {
//                    // Para pruebas, navegar directamente al ScaffoldFragment
//                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
//
//                    // También mostramos un mensaje de éxito
//                    Snackbar.make(root, "Inicio de sesión exitoso (simulado)", Snackbar.LENGTH_SHORT).show()
//                }

                // Usando el ViewModel para validar los campos
                if (viewModel.validateLoginForm(
                        et1.text.toString(),
                        et2.text.toString()
                    )
                ) {
                    // Navegar si es válido
                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }
            }

            // Login con Google
            iconButton2.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Google (simulado)", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()

                // Para pruebas, navegar al ScaffoldFragment después de un breve retraso
                root.postDelayed({
                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }, 1500)
            }

            // Login con Apple
            iconButton3.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Apple (simulado)", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()

                // Para pruebas, navegar al ScaffoldFragment después de un breve retraso
                root.postDelayed({
                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }, 1500)
            }

            // Login con Meta
            iconButton.setOnClickListener {
                Snackbar.make(root, "Iniciando sesión con Meta (simulado)", Snackbar.LENGTH_SHORT)
                    .setAction("Cerrar") {}
                    .show()

                // Para pruebas, navegar al ScaffoldFragment después de un breve retraso
                root.postDelayed({
                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }, 1500)
            }

            // Registro (navegar a RegisterFragment)
            textButton.setOnClickListener {
                findNavController().navigate(R.id.login_to_register)
            }

            // Olvidó contraseña
            forgotButton.setOnClickListener {
                Snackbar.make(root, "Recuperar contraseña (simulado)", Snackbar.LENGTH_SHORT)
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