package miseguridad.com.ui_

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apisecurityapp.api.ApiClient
import com.example.apisecurityapp.model.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import miseguridad.com.R

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(username, password)
            Log.d("LoginActivity", "Intentando iniciar sesión con: $username y $password")
            loginUser(loginRequest)
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        lifecycleScope.launch {
            try {
                Log.d("LoginActivity", "Enviando solicitud a la API...")

                val loginResponse = ApiClient.apiService.login(loginRequest)

                // Si la respuesta es exitosa, navegar a la siguiente actividad
                Log.d("LoginActivity", "Login exitoso: ${loginResponse.message}")
                Toast.makeText(this@LoginActivity, "Login exitoso", Toast.LENGTH_SHORT).show()

                // Navegar a HomeActivity
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            } catch (e: HttpException) {
                Log.e("LoginActivity", "Error HTTP: ${e.message()}")
                Toast.makeText(this@LoginActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error de conexión o servidor: ${e.message}")
                Toast.makeText(this@LoginActivity, "Error de conexión o servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
