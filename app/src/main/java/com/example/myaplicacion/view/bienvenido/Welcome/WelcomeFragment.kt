package com.example.myaplicacion.view.bienvenido.Welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaplicacion.api.Product
import com.example.myaplicacion.api.RetrofitService
import com.example.myaplicacion.databinding.FragmentWelcomeBinding
import com.example.myaplicacion.view.bienvenido.Welcome.ColorAdapter
import com.example.myaplicacion.view.bienvenido.products.ProductAdapter
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private var allProducts = listOf<Product>()
    private var filteredProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                allProducts = RetrofitService.api.getProducts()
                filteredProducts = allProducts.take(5).toMutableList() // Inicialmente muestra todos los productos
                binding.recyclerView.adapter = ProductAdapter(filteredProducts)
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
