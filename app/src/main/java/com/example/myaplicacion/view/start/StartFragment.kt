// StartFragment.kt
package com.example.myaplicacion.view.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myaplicacion.R
import com.example.myaplicacion.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navegar al fragmento de Login
        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment3)
        }

        // Navegar al fragmento de Registro
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_registerFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
