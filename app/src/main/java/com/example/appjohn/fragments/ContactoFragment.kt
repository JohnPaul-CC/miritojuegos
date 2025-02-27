package com.example.appjohn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentContactoBinding
import com.google.android.material.snackbar.Snackbar

class ContactoFragment : Fragment() {

    private var _binding: FragmentContactoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Configurar listener para llamada telefónica
        binding.call.setOnClickListener {
            Snackbar.make(binding.root, "Simulando llamada telefónica", Snackbar.LENGTH_SHORT).show()
        }

        // Configurar listener para correo electrónico
        binding.email.setOnClickListener {
            Snackbar.make(binding.root, "Simulando enviar email", Snackbar.LENGTH_SHORT).show()
        }

        // Configurar listener para WhatsApp
        binding.wasap.setOnClickListener {
            Snackbar.make(binding.root, "Simulando WhatsApp", Snackbar.LENGTH_SHORT).show()
        }

        // Configurar listener para ubicación en mapa
        binding.ubi.setOnClickListener {
            Snackbar.make(binding.root, "Simulando abrir ubicación", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}