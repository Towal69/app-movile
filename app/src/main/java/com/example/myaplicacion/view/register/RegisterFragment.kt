package com.example.myaplicacion.view.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.util.regex.Pattern

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

        // Validación de coincidencia de contraseñas en tiempo real
        val passwordTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = binding.PasswordRegisterEditText.text.toString()
                val passwordRepeat = binding.PasswordRepeatEditText.text.toString()

                // Validar coincidencia de contraseñas
                if (passwordRepeat.isNotEmpty() && password != passwordRepeat) {
                    binding.textViewPasswordRepeatRegister.error = "Las contraseñas no coinciden"
                } else {
                    binding.textViewPasswordRepeatRegister.error = null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        // Agregar el TextWatcher a ambos campos
        binding.PasswordRegisterEditText.addTextChangedListener(passwordTextWatcher)
        binding.PasswordRepeatEditText.addTextChangedListener(passwordTextWatcher)

        // Listener para el botón de registro
        binding.buttonRegister.setOnClickListener {
            val nombre = binding.nombreEditText.text.toString()
            val email = binding.EmailRegisterEditText.text.toString()
            val password = binding.PasswordRegisterEditText.text.toString()
            val passwordRepeat = binding.PasswordRepeatEditText.text.toString()

            // Limpiar los errores previos
            clearErrors()

            // Validaciones personalizadas
            var valid = true
            if (!isNotEmpty(nombre)) {
                binding.textNombre.error = "El nombre no puede estar vacío"
                valid = false
            }
            if (!isEmailValid(email)) {
                binding.EmailRegisterTextInputLayout.error = "Formato de correo inválido"
                valid = false
            }
            if (password.length < 6) {
                binding.textViewPasswordRegister.error = "La contraseña debe tener al menos 6 caracteres"
                valid = false
            }
            if (password != passwordRepeat) {
                binding.textViewPasswordRepeatRegister.error = "Las contraseñas no coinciden"
                valid = false
            }

            // Si todos los campos son válidos, continuar con el registro
            if (valid) {
                registerUser(email, password, nombre)
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    // Funciones de validación
    private fun isNotEmpty(text: String): Boolean {
        return text.trim().isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return Pattern.compile(emailPattern).matcher(email).matches()
    }

    private fun clearErrors() {
        binding.textNombre.error = null
        binding.EmailRegisterTextInputLayout.error = null
        binding.textViewPasswordRegister.error = null
        binding.textViewPasswordRepeatRegister.error = null
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
