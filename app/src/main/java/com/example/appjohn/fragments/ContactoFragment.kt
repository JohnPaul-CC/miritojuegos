package com.example.appjohn.fragments

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.appjohn.R
import com.example.appjohn.databinding.FragmentContactoBinding
import com.google.android.material.snackbar.Snackbar

class ContactoFragment : Fragment() {

    private var _binding: FragmentContactoBinding? = null
    private val binding get() = _binding!!

    // Código de solicitud para el permiso de llamada telefónica
    private val CALL_PHONE_PERMISSION_REQUEST = 123

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
            makePhoneCall()
        }

        // Configurar listener para correo electrónico
        binding.email.setOnClickListener {
            sendEmail()
        }

        // Configurar listener para WhatsApp
        binding.wasap.setOnClickListener {
            sendWhatsAppMessage()
        }

        // Configurar listener para ubicación en mapa
        binding.ubi.setOnClickListener {
            openLocation()
        }
    }

//    private fun makePhoneCall() {
//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            // Si tenemos permiso, realizar la llamada
//            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:99999999999"))
//            try {
//                startActivity(intent)
//            } catch (ex: ActivityNotFoundException) {
//                Snackbar.make(binding.root, "No se pudo realizar la llamada", Snackbar.LENGTH_LONG).show()
//            }
//        } else {
//            // Si no tenemos permiso, solicitarlo
//            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CALL_PHONE)) {
//                // El usuario rechazó el permiso anteriormente, explicar por qué lo necesitamos
//                Snackbar.make(binding.root, "Para llamar directamente necesitas conceder el permiso en ajustes", Snackbar.LENGTH_LONG).show()
//            } else {
//                // Solicitar el permiso
//                requestPermissions(
//                    arrayOf(Manifest.permission.CALL_PHONE),
//                    CALL_PHONE_PERMISSION_REQUEST
//                )
//            }
//        }
//    }
    private fun makePhoneCall() {

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:999999999"))

            try{
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                Snackbar.make(binding.root, "No se pudo llamar ", Snackbar.LENGTH_LONG).show()
            }

        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CALL_PHONE)){
                Snackbar.make(binding.root, "No tienes los permisos necesarios, activalo en ajustes ", Snackbar.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),CALL_PHONE_PERMISSION_REQUEST)
            }
        }
    }

        private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Solo aplicaciones de email responderán
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@riotgames.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Consulta desde la app")
        }

        try {
            startActivity(Intent.createChooser(intent, "Enviar email con..."))
        } catch (ex: ActivityNotFoundException) {
            Snackbar.make(binding.root, "No hay aplicaciones de email instaladas", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun sendWhatsAppMessage() {
        try {
            // Intent para abrir WhatsApp con un número
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=34644732744&text=Hola%20desde%20la%20app")
            }
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Snackbar.make(binding.root, "No se pudo abrir WhatsApp", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun openLocation() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=Riot+Games+Arena+Berlin")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        try {
            startActivity(mapIntent)
        } catch (ex: ActivityNotFoundException) {
            // Si no hay app de mapas, abrir en el navegador
            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/place/Riot+Games+Arena,+Berlin"))
            startActivity(browserIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CALL_PHONE_PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permiso concedido, intentar hacer la llamada nuevamente
                    makePhoneCall()
                } else {
                    // Permiso denegado
                    Snackbar.make(binding.root, "Permiso de llamada denegado", Snackbar.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                // Ignorar otros códigos de solicitud
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}