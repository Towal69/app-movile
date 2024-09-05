# Implementacion de Firebase Authentication en Android

1. [Introducción](#introducción)
2. [Descripción General](#descripción-general)
   - [Características principales](#características-principales)
3. [Funcionalidades de la Aplicación](#funcionalidades-de-la-aplicación)
   1. [Pantalla de Login](#pantalla-de-login)
   2. [Pantalla de Registro](#pantalla-de-registro)
   3. [Pantalla de Bienvenida](#pantalla-de-bienvenida)
4. [Arquitectura de Navegación](#arquitectura-de-navegación)
5. [Bibliotecas Utilizadas](#bibliotecas-utilizadas)
6. [Requisitos Técnicos](#requisitos-técnicos)
7. [Implementación de Firebase](#implementación-de-firebase)
   1. [Pasos para la configuración de Firebase](#pasos-para-la-configuración-de-firebase)
   2. [Dependencias de Firebase](#dependencias-de-firebase)
   3. [Inicialización de FirebaseAuth](#inicialización-de-firebaseauth)
   4. [Autenticación de Usuarios](#autenticación-de-usuarios)
      - [Registro de usuario](#registro-de-usuario)
      - [Inicio de sesión](#inicio-de-sesión)

# Introducción

Se nos ha encargado implementar un sistema de autenticación de usuarios en una aplicación Android. La tecnología escogida por el equipo es Firebase Authentication. La aplicación debe permitir a los usuarios registrarse con un correo electrónico y contraseña, iniciar sesión y navegar entre las pantallas de bienvenida, login y registro.

## Descripción General

Esta aplicación Android es un sistema de autenticación de usuarios utilizando Firebase para el registro, inicio de sesión y autenticación. La aplicación consta de tres pantallas principales: Login, Registro y Bienvenida.

### Características principales:
- Autenticación de usuarios mediante Firebase Authentication.
- Registro de nuevos usuarios y verificación de credenciales.
- Navegación entre las pantallas de Login, Registro y Bienvenida.
- Uso de Firebase Firestore para almacenamiento de datos (si se implementa más adelante).
- Uso de la arquitectura de navegación de Android para gestionar la navegación entre fragmentos.

---

## Funcionalidades de la Aplicación

1. **Pantalla de Login**:
   - Permite a los usuarios iniciar sesión con su correo electrónico y contraseña.
   - Navegación a la pantalla de registro si el usuario no tiene una cuenta.

2. **Pantalla de Registro**:
   - Permite registrar a nuevos usuarios con su correo electrónico y contraseña.
   - Validación de que la contraseña y la repetición de la contraseña sean iguales.

3. **Pantalla de Bienvenida**:
   - Muestra un mensaje de bienvenida una vez que el usuario ha iniciado sesión correctamente.

## Arquitectura de Navegación

Se utilizó el componente de navegación de Android para manejar la navegación entre fragmentos. Los archivos de fragmentos están conectados a través de `nav_graph.xml`, lo que permite una navegación fluida entre las pantallas de inicio de sesión, registro y bienvenida.

## Bibliotecas Utilizadas

- **Firebase Authentication**: Para manejar la autenticación de usuarios.
- **Firebase Firestore**: (opcional para almacenamiento de datos).
- **Android Jetpack Navigation**: Para la navegación entre fragmentos.
- **Material Components**: Para diseño moderno y elementos de la interfaz de usuario.

## Requisitos Técnicos

- **SDK Mínimo**: 30
- **SDK de Objetivo**: 34
- **Firebase SDK**: 33.2.0
- **Lenguaje**: Kotlin



## Implementación de Firebase

### Pasos para la configuración de Firebase:

1. **Agregar Firebase al Proyecto**:
   - Se configuró Firebase en el proyecto Android. Esto incluye añadir el archivo `google-services.json` al directorio `app` y configurar el SDK de Firebase en el archivo `build.gradle`.
   - En el archivo `build.gradle.kts` del nivel de la aplicación, se agregó el plugin de servicios de Google.

2. **Dependencias de Firebase**:
   En el archivo `build.gradle.kts`, se agregaron las siguientes dependencias para Firebase:
   ```kotlin
   dependencies {
       implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
       implementation("com.google.firebase:firebase-analytics")
       implementation("com.google.firebase:firebase-auth-ktx")
       implementation("com.google.firebase:firebase-firestore-ktx")
   }
   ```
3. **Inicialización de FirebaseAuth**: 

FirebaseAuth se inicializa en los fragmentos que manejan el login y el registro de usuarios. La instancia de FirebaseAuth es necesaria para autenticar usuarios.

```kotlin
private lateinit var auth: FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    auth = FirebaseAuth.getInstance()
}
```
4. **Autenticación de Usuarios**: 

Se utiliza FirebaseAuth para registrar e iniciar sesión a los usuarios con las siguientes funciones:

4.1. Registro de usuario

```kotlin
private fun registerUser(email: String, password: String) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registro exitoso
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                // Navegar a la pantalla de login
            } else {
                // Error en el registro
                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}
```

4.2. Inicio de sesión

```kotlin
private fun loginUser(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Inicio de sesión exitoso
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                // Navegar a la pantalla principal
            } else {
                // Error en el inicio de sesión
                handleLoginError(task.exception)
            }
        }
}
```

