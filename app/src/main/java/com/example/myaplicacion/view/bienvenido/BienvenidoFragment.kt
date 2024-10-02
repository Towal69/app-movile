package com.example.myaplicacion.view.bienvenido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaplicacion.R
import com.example.myaplicacion.api.Product
import com.example.myaplicacion.api.RetrofitService
import com.example.myaplicacion.databinding.FragmentBienvenidoBinding
import com.example.myaplicacion.view.bienvenido.products.ProductAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class BienvenidoFragment : Fragment() {

    private var _binding: FragmentBienvenidoBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var allProducts: List<Product>
    private lateinit var filteredProducts: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBienvenidoBinding.inflate(inflater, container, false)
        val navHostFragment= childFragmentManager.findFragmentById(R.id.bienvenido_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
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

        // Configurar el RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Cargar los productos de la API FakeStore
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                allProducts = RetrofitService.api.getProducts()
                filteredProducts = allProducts.toMutableList() // Inicialmente muestra todos los productos
                binding.recyclerView.adapter = ProductAdapter(filteredProducts)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar los productos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el SearchView para buscar productos
        val searchItem = binding.topAppBar.menu.findItem(R.id.search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText ?: "")
                return true
            }
        })

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
    }
    private fun filterProducts(query: String) {
        val filteredList = if (query.isEmpty()) {
            allProducts // Si no hay texto, muestra todos los productos
        } else {
            allProducts.filter { product ->
                product.title.contains(query, ignoreCase = true) // Filtra por título
            }
        }

        filteredProducts.clear()
        filteredProducts.addAll(filteredList)
        binding.recyclerView.adapter?.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
