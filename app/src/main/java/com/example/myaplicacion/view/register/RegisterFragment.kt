package com.example.myaplicacion.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myaplicacion.R
import com.example.myaplicacion.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance() // Inicializar FirebaseAuth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            val nombre = binding.nombreEditText.text.toString()
            val email = binding.EmailRegisterEditText.text.toString()
            val password = binding.PasswordRegisterEditText.text.toString()
            val passwordRepeat = binding.PasswordRepeatEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && password == passwordRepeat) {
                registerUser(email, password, nombre)
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun registerUser(email: String, password: String, nombre: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    val userId = auth.currentUser?.uid
                    val db = FirebaseFirestore.getInstance()
                    val user = hashMapOf(
                        "nombre" to nombre,
                        "email" to email
                    )

                    userId?.let {
                        db.collection("users").document(it)
                            .set(user)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al guardar los datos: $e", Toast.LENGTH_SHORT).show()
                            }
                    }
                    // Navegar a la pantalla de login o donde desees
                    findNavController().navigate(R.id.action_registerFragment2_to_loginFragment3)
                } else {
                    // Si falla el registro
                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
