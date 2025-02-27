package com.example.appjohn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentScaffoldBinding
import com.google.android.material.snackbar.Snackbar

class ScaffoldFragment : Fragment() {

    private var _binding: FragmentScaffoldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScaffoldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Por ahora, solo mostrar un mensaje para confirmar que el fragmento se cargó
        Snackbar.make(view, "ScaffoldFragment cargado correctamente", Snackbar.LENGTH_LONG).show()

        // Configurar click en el botón de logout (temporary)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    // Navegar de vuelta al login
                    findNavController().navigate(R.id.loginFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}