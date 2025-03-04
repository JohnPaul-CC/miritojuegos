package com.example.appjohn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentScaffoldBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ScaffoldFragment : Fragment() {

    private var _binding: FragmentScaffoldBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        _binding = FragmentScaffoldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        setupNavigation()

//        db
//            .with(binding.navigationView)
//            .placeholder(R.drawable.profileicon)
//            .into(binding.nav_header_user_name);

    }

    private fun setupToolbar() {
        // Configurar la toolbar como action bar
        (requireActivity() as androidx.appcompat.app.AppCompatActivity).setSupportActionBar(binding.toolbar)

        // Configurar el drawer toggle (hamburger icon)
        drawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun setupNavigation() {
        // Configurar el NavController para el fragmento anidado
        val navHostFragment = childFragmentManager.findFragmentById(R.id.scaffold_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        // Configurar el Toolbar y el DrawerLayout
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )


        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Vincular el BottomNavigationView con el NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Conectar NavigationView con NavController para los ítems de navegación
        binding.navigationView.setupWithNavController(navController)

        // Manejar el cierre del DrawerLayout cuando se selecciona un ítem
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    // Log out firebase
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(requireContext(), "Cerraste sesión", Toast.LENGTH_SHORT).show()

                    // Navegar de vuelta al login
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.let {
                        val mainNavController = androidx.navigation.Navigation.findNavController(it.requireView())
                        mainNavController.navigate(R.id.loginFragment)
                    }
                    true
                }
                R.id.listaFragment, R.id.favoritosFragment, R.id.contactoInternalFragment -> {
                    // Navegar al destino seleccionado
                    navController.navigate(menuItem.itemId)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
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