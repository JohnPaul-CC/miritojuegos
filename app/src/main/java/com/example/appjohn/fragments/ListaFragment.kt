package com.example.appjohn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appjohn.clases.Campeones
import com.example.appjohn.clases.CampeonesAdapter
import com.example.appjohn.databinding.FragmentListaBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaFragment : Fragment() {

    // ViewBinding
    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!

    private  var campeones: MutableList<Campeones> = mutableListOf()
    private lateinit var adapter: CampeonesAdapter

    // Coroutines
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar el ProgressBar al iniciar
        binding.progressBar.visibility = ProgressBar.VISIBLE

        // Cargar datos con una demora simulada
        cargarDatos()
    }

        private suspend fun cargarCampeones() {
        //Cargamos los datos de la BBDD
        FirebaseApp.initializeApp(requireContext())
        val db = Firebase.firestore

        campeones.clear()

//        withContext(Dispatchers.Main)
//        {
//            //Ocultar el RecyclerView
//            binding.rvPeliculas.visibility = View.GONE
//            // Leer películas desde firebase
//            binding.progressBar.visibility = ProgressBar.VISIBLE
//        }

        db.collection("Usuarios")
            .get()
            .addOnSuccessListener{

                    result ->

                for(document in result)
                {
                    val fav = document.get("fav") as Boolean
                    val nuevoCampeon = Campeones(
                        document.get("nombre") as String,
                        document.get("descripcion") as String,
                        document.get("clase") as String,
                        document.get("fav") as Boolean)

                    campeones.add(nuevoCampeon)
                }
            }
            .addOnFailureListener{

            }

        for (i in 1..100) {
            delay(50) // Simula una tarea larga
            binding.progressBar.progress = i
        }

        withContext(Dispatchers.Main)
        {
            binding.progressBar.visibility = ProgressBar.GONE
            //Mostrar el RecyclerView
            binding.recyclerView.visibility = View.VISIBLE
        }

    }

    private fun cargarDatos() {
        // Usamos coroutines para simular una carga de datos asíncrona
        scope.launch {
            try {
                // Simulamos un trabajo haciendo una pausa
                // (similar al ejemplo del profesor)
                for (i in 0..100) {
                    delay(20) // Pausa de 20ms
                    binding.progressBar.progress = i;
                }

                // Una vez "cargados" los datos, los mostramos en el RecyclerView
                withContext(Dispatchers.Main) {
                    // Datos de prueba
                    val campeonesList = listOf(
                        Campeones("Ahri", "Mid", "Mage", false),
                        Campeones("Jinx", "ADC", "Marksman", false),
                        Campeones("Yasuo", "Mid", "Fighter", true),
                        Campeones("Leona", "Support", "Tank", false)
                    )

                    // Configurar el RecyclerView
                    val recyclerView: RecyclerView = binding.recyclerView
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = CampeonesAdapter(requireContext(), campeonesList)

                    // Ocultar ProgressBar cuando los datos están listos
                    binding.progressBar.visibility = ProgressBar.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                // Si ocurre algún error, nos aseguramos de ocultar el ProgressBar y mostrar un mensaje
                withContext(Dispatchers.Main) {
                    ocultarProgressBar()
                }
            }
        }
    }

    private fun ocultarProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job.cancel() // Cancelar el job para evitar memory leaks
    }
}