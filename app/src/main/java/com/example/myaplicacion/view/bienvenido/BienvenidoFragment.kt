package com.example.myaplicacion.view.bienvenido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myaplicacion.R
import com.example.myaplicacion.databinding.FragmentBienvenidoBinding
import com.google.firebase.auth.FirebaseAuth

class BienvenidoFragment : Fragment() {

    private var _binding: FragmentBienvenidoBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBienvenidoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el nombre del usuario desde el Bundle
        val nombreUsuario = arguments?.getString("nombre_usuario") ?: "Usuario"

        // Configurar el texto de bienvenida
        binding.welcomeTextView.text = "Bienvenido, $nombreUsuario!"

        // Configurar el botón de cierre de sesión
        binding.logoutButton.setOnClickListener {
            // Cerrar sesión
            auth.signOut()
            // Navegar de vuelta a la pantalla de inicio de sesión
            findNavController().navigate(R.id.loginFragment3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
