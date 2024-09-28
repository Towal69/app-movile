package com.example.myaplicacion.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myaplicacion.R
import com.example.myaplicacion.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth // Inicializar FirebaseAuth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Si el usuario está autenticado
            Toast.makeText(context, "Usuario autenticado: ${user.email}", Toast.LENGTH_SHORT).show()
            // Aquí podrías navegar a otra pantalla si lo deseas
            findNavController().navigate(R.id.action_loginFragment3_to_bienvenidoFragment)
        } else {
            // Si no hay usuario autenticado
            Toast.makeText(context, "Por favor, inicie sesión.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Manejar el botón de inicio de sesión
        binding.loginButton.setOnClickListener {
            val email = binding.EmailEditText.text.toString()
            val password = binding.PasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Manejar el botón de registro
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment3_to_registerFragment2)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    // Navegar a la pantalla principal o donde desees
                    findNavController().navigate(R.id.action_loginFragment3_to_bienvenidoFragment)
                } else {
                    // Si falla el inicio de sesión
                    handleLoginError(task.exception)
                }
            }
    }

    private fun handleLoginError(exception: Exception?) {
        exception?.let {
            val errorMessage = when (it) {
                is FirebaseAuthInvalidUserException -> "El usuario no existe o ha sido deshabilitado."
                is FirebaseAuthInvalidCredentialsException -> "Correo electrónico o contraseña incorrectos."
                is FirebaseAuthUserCollisionException -> "El usuario ya está registrado."
                else -> "Error al iniciar sesión. Por favor, intenta de nuevo."
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(context, "Error desconocido al iniciar sesión.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
