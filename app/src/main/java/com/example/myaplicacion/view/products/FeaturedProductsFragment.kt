package com.example.myaplicacion.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaplicacion.api.RetrofitService
import com.example.myaplicacion.databinding.FragmentFeaturedProductsBinding
import kotlinx.coroutines.launch

class FeaturedProductsFragment : Fragment() {

    private var _binding: FragmentFeaturedProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeaturedProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView en modo horizontal
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Cargar solo 5 productos destacados de la API
        lifecycleScope.launch {
            try {
                val allProducts = RetrofitService.api.getProducts()
                val featuredProducts = allProducts.take(5) // Tomar solo 5 productos
                binding.recyclerView.adapter = ProductAdapter(featuredProducts)
            } catch (e: Exception) {
                // Manejar error al cargar los productos
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
