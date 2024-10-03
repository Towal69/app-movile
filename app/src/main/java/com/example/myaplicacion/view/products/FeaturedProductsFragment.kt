package com.example.myaplicacion.view.products

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
import com.example.myaplicacion.databinding.FragmentFeaturedProductsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class FeaturedProductsFragment : Fragment() {

    private var _binding: FragmentFeaturedProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance() // Inicializar FirebaseAuth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView en modo horizontal
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Cargar los productos destacados (por ejemplo, los primeros 5 productos)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val allProducts = RetrofitService.api.getProducts()
                val featuredProducts = allProducts.take(5) // Tomar solo 5 productos
                binding.recyclerView.adapter = ProductAdapter(featuredProducts)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar los productos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar la barra de navegación
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.more -> {
                    // Cerrar sesión y redirigir al login
                    auth.signOut()
                    findNavController().navigate(R.id.loginFragment3)
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
