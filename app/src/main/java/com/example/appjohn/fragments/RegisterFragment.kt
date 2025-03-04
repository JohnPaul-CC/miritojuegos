package com.example.appjohn.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appjohn.R
import com.example.appjohn.Usuario
import com.example.appjohn.databinding.FragmentRegisterBinding
import com.example.appjohn.viewmodels.RegisterViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.material.snackbar.Snackbar
import com.google.api.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

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
                signInWithGoogle()

                Snackbar.make(root, "Registrandose con Google", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
            }

            // Registro con Meta
            iconButton.setOnClickListener {
                Snackbar.make(root, "Registrandose con Meta", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Cerrar") {}
                    .show()
                findNavController().navigate(R.id.register_to_login)

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

                    auth
                        .createUserWithEmailAndPassword(et1.text.toString(), et2.text.toString())
                        .addOnSuccessListener {

                            val intent = Intent(requireContext(), LoginFragment::class.java)
                            startActivity(intent)
                            Toast.makeText(requireContext(), "createUserWithEmail:success", Toast.LENGTH_SHORT).show()

                        }
                        .addOnFailureListener{

                            Toast.makeText(
                                requireContext(),
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }



                    val nuevoUser = Usuario(et1.text.toString(), et3.text.toString())
                    addUsuario(nuevoUser)
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

    fun signInWithGoogle() {
        /*val ranNonce : String = UUID.randomUUID().toString()
        val bytes : ByteArray = ranNonce.toByteArray()
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val digest: ByteArray = md.digest(bytes)
        val hashedNonce : String = digest.fold(""){str, it -> str + "%02x".format(it)}*/

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(getString(R.string.web_client_id))
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

//    private suspend fun cargarUsuarios() {
//        //Cargamos los datos de la BBDD
//        FirebaseApp.initializeApp(requireContext())
//        val db = Firebase.firestore
//
//        Usuarios.clear()
//
//        withContext(Dispatchers.Main)
//        {
//            //Ocultar el RecyclerView
//            binding.rvPeliculas.visibility = View.GONE
//            // Leer películas desde firebase
//            binding.progressBar.visibility = ProgressBar.VISIBLE
//        }
//
//        db.collection("Usuarios")
//            .get()
//            .addOnSuccessListener{
//
//                    result ->
//
//                for(document in result)
//                {
//                    val fav = document.get("fav") as Boolean
//                    val pelicula = Pelicula(document.id.toInt(),
//                        0,
//                        document.get("nombre") as String,
//                        document.get("descripcion") as String,
//                        fav)
//
//                    movies.add(pelicula)
//                }
//            }
//            .addOnFailureListener{
//
//            }
//
//        for (i in 1..100) {
//            delay(50) // Simula una tarea larga
//            binding.progressBar.progress = i
//        }
//
//        withContext(Dispatchers.Main)
//        {
//            binding.progressBar.visibility = ProgressBar.GONE
//            //Mostrar el RecyclerView
//            binding.rvPeliculas.visibility = View.VISIBLE
//        }
//
//    }

    fun addUsuario(nuevoUsuario : Usuario) {
        val db = FirebaseFirestore.getInstance()

        // Referencia a la colección "Usuarios"
        val usuariosRef = db.collection("Usuarios")
            .add(nuevoUsuario)
            .addOnSuccessListener {
                // Éxito al guardar el usuario
                Toast.makeText(requireContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Error al guardar el usuario
                Toast.makeText(requireContext(), e.localizedMessage , Toast.LENGTH_SHORT).show()
            }

    }

}