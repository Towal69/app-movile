package com.example.myaplicacion.view.bienvenido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myaplicacion.R
import com.example.myaplicacion.databinding.FragmentBienvenidoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

        val userId = auth.currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        userId?.let {
            db.collection("users").document(it)
                .get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        val nombre = document.getString("nombre")
                        binding.topAppBar.title = "$nombre"
                    }else{
                        Toast.makeText(context, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        binding.topAppBar.setNavigationOnClickListener {
            showPopupMenu(it)
        }
        // Configurar el botón de cierre de sesión
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.search -> {
                    Toast.makeText(context, "Search clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more -> {
                    // Cerrar sesión y navegar al fragmento de inicio de sesión
                    auth.signOut()
                    findNavController().navigate(R.id.loginFragment3)
                    true
                }
                R.id.menu -> {
                    showPopupMenu(binding.topAppBar)
                    true
                }
                else -> false
            }
        }
    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.popup_menu) // Inflar el menú de categorías
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.categoria1 -> {
                    Toast.makeText(context, "Categoría 1 seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.categoria2 -> {
                    Toast.makeText(context, "Categoría 2 seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.categoria3 -> {
                    Toast.makeText(context, "Categoría 3 seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
