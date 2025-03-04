package com.example.appjohn.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
import com.example.appjohn.clases.Usuario
import com.example.appjohn.databinding.FragmentLoginBinding
import com.example.appjohn.viewmodels.LoginViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    // Esta propiedad solo es válida entre onCreateView y onDestroyView
    private val binding get() = _binding!!


    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

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

                auth = FirebaseAuth.getInstance()

                // Usando el ViewModel para validar los campos
                if (viewModel.validateLoginForm(
                        et1.text.toString(),
                        et2.text.toString()
                    )
                ) {
//                    auth
//                        .signInWithEmailAndPassword(et1.text.toString(), et2.text.toString())
//                        .addOnSuccessListener {
//
//                            val intent = Intent(requireContext(), LoginFragment::class.java)
//                            startActivity(intent)
//
//                        }
//                        .addOnFailureListener{
//
//                            Toast.makeText(requireContext(), "Error en el login", Toast.LENGTH_SHORT).show()
//
//                        }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(et1.text.toString(), et2.text.toString())
                        .addOnCompleteListener() {
                            if (it.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(
                                    requireContext(),
                                    "LogIn Authentication succeded.",
                                    Toast.LENGTH_SHORT,
                                ).show()

                                findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
//                                val user = auth.currentUser
//                                updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(
                                    requireContext(),
                                    "LogIn Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
//                                updateUI(null)
                            }
                        }

                    // Navegar si es válido
//                    findNavController().navigate(R.id.action_loginFragment_to_scaffoldFragment)
                }
            }

            // Login con Google
            iconButton2.setOnClickListener {
                signInWithGoogle()
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

    fun signInWithGoogle() {
        /*val ranNonce : String = UUID.randomUUID().toString()
        val bytes : ByteArray = ranNonce.toByteArray()
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val digest: ByteArray = md.digest(bytes)
        val hashedNonce : String = digest.fold(""){str, it -> str + "%02x".format(it)}*/

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
//            .setServerClientId(getString(R.string.web_client_id))
            .setServerClientId(getString(R.string.default_web_client_id))

            //.setNonce(hashedNonce)
            //.setAutoSelectEnabled(false)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                credentialManager = CredentialManager.create(context = requireContext())
                val result = credentialManager.getCredential(
                    context = requireContext(),
                    request = request)
                val credential = result.credential

                // Use googleIdTokenCredential and extract the ID to validate and
                // authenticate on your server.
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                // You can use the members of googleIdTokenCredential directly for UX
                // purposes, but don't use them to store or control access to user
                // data. For that you first need to validate the token:
                val googleIdToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                val authResult = auth.signInWithCredential(firebaseCredential).await()

                if(authResult != null)
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(requireContext(), "Login exitoso", Toast.LENGTH_SHORT).show()
                    }

                }
                else
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(requireContext(), "Error en el login", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            catch (e: GetCredentialException)
            {
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(requireContext(), e.localizedMessage , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}