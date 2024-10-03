package com.example.myaplicacion.view.bienvenido.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaplicacion.api.Product
import com.example.myaplicacion.api.RetrofitService
import com.example.myaplicacion.databinding.FragmentProductsBinding
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private var allProducts = listOf<Product>()
    private var filteredProducts = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                allProducts = RetrofitService.api.getProducts()
                filteredProducts = allProducts.toMutableList() // Inicialmente muestra todos los productos
                binding.recyclerView.adapter = ProductAdapter(filteredProducts)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar los productos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText ?: "")
                return true
            }
        })

    }
    private fun filterProducts(query: String) {
        val filteredList = if (query.isEmpty()) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.title.contains(query, ignoreCase = true)
            }
        }

        filteredProducts.clear()
        filteredProducts.addAll(filteredList)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
