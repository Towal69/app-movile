package com.example.myaplicacion.view.bienvenido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaplicacion.R
import com.example.myaplicacion.api.RetrofitService
import com.example.myaplicacion.databinding.FragmentBienvenidoBinding
import com.example.myaplicacion.view.products.ProductAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

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
    ): View? {
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
                    if (document != null) {
                        val nombre = document.getString("nombre")
                        binding.topAppBar.title = "$nombre"
                    } else {
                        Toast.makeText(context, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Configurar el botón de cierre de sesión
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.more -> {
                    auth.signOut()
                    findNavController().navigate(R.id.loginFragment3)
                    true
                }
                else -> false
            }
        }

        // Configurar el RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Cargar los productos de la API FakeStore
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val products = RetrofitService.api.getProducts()
                binding.recyclerView.adapter = ProductAdapter(products)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar los productos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
