package com.example.appjohn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
//import com.example.appjohn.

import androidx.navigation.fragment.findNavController
import com.example.appjohn.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Manejo de eventos
        binding.button2.setOnClickListener {
            if (binding.et1.text!!.isEmpty() || binding.et2.text!!.isEmpty()) {
                if (binding.et1.text!!.isEmpty()) {
                    binding.et1.error = "Se debe introducir un nombre para registrarse"
                }
                if (binding.et2.text!!.isEmpty()) {
                    binding.et2.error = "Se debe introducir un email para registrarse"
                }
            } else {
                Snackbar.make(binding.root, "Iniciando sesión", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }
        }

        binding.textButton.setOnClickListener {
            // Navegar al RegistroFragment usando Navigation Component
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        binding.iconButton2.setOnClickListener {
            Snackbar.make(binding.root, "Iniciando sesión con Google", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {}
                .show()
        }

        binding.iconButton3.setOnClickListener {
            Snackbar.make(binding.root, "Iniciando sesión con Apple", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {}
                .show()
        }

        binding.iconButton.setOnClickListener {
            // Navegar a otro fragmento en lugar de usar Intent
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        binding.forgotButton.setOnClickListener {
            Snackbar.make(binding.root, "Olvidaste tu contraseña?", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cerrar") {}
                .show()
        }
    }
//    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            LoginFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}