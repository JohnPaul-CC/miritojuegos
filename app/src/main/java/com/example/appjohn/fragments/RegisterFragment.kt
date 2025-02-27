package com.example.appjohn.fragments

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentRegisterBinding
import com.example.appjohn.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupDatePicker()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observar errores de nombre
        viewModel.nameError.observe(viewLifecycleOwner) { error ->
            binding.et1.error = error
        }

        // Observar errores de email
        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            binding.et2.error = error
        }

        // Observar errores de contraseña
        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.et3.error = error
        }

        // Observar errores de fecha
        viewModel.dateError.observe(viewLifecycleOwner)
        { error ->
            binding.et4.error = error
        }
    }

    private fun setupDatePicker() {
        binding.et4.keyListener = null
        binding.et4.setOnClickListener {
            val calen = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calen.set(Calendar.YEAR, year)
                calen.set(Calendar.MONTH, month)
                calen.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.et4.setText(buildString {
                    append(calen.get(Calendar.DAY_OF_MONTH))
                    append("/${calen.get(Calendar.MONTH)}")
                    append("/${calen.get(Calendar.YEAR)}")
                })
            }

            DatePickerDialog(
                requireContext(),
                dateSetListener,
                calen.get(Calendar.YEAR),
                calen.get(Calendar.MONTH),
                calen.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            // Registro con Google
            iconButton2.setOnClickListener {
                Snackbar.make(root, "Registrandose con Google", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Registro con Meta
            iconButton.setOnClickListener {
                Snackbar.make(root, "Registrandose con Meta", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Botón de registro principal
            button2.setOnClickListener {
                if (viewModel.validateRegisterForm(
                    et1.text.toString(),
                    et2.text.toString(),
                    et3.text.toString(),
                    et4.text.toString()
                )) {
                    // Si la validación es exitosa, navegar al LoginFragment
                    findNavController().navigate(R.id.register_to_login)
                }
            }
        }
    }

    //Validacion sin ViewModel
    private fun validateFields(): Boolean {
        with(binding) {

            var isValid = true

            if (et4.text!!.isEmpty()) {
                et4.error = "Se debe introducir una fecha de nacimiento para registrase"
                isValid = false
            }
            if (et1.text!!.isEmpty()) {
                et1.error = "Se debe introducir un nombre para registrase"
                isValid = false
            }
            if (et2.text!!.isEmpty()) {
                et2.error = "Se debe introducir un email para registrase"
                isValid = false
            }
            if (et3.text!!.isEmpty()) {
                et3.error = "Se debe introducir una contraseña para registrase"
                isValid = false
            }

            return isValid
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}