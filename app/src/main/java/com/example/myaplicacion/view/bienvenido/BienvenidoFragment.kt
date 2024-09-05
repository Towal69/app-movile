package com.example.myaplicacion.view.bienvenido

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myaplicacion.R
import com.example.myaplicacion.databinding.FragmentBienvenidoBinding


class BienvenidoFragment : Fragment() {
    private var _binding: FragmentBienvenidoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBienvenidoBinding.inflate(inflater, container, false)
        return binding.root
    }


}