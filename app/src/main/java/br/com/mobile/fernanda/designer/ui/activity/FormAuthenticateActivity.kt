package br.com.mobile.fernanda.designer.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.mobile.fernanda.designer.databinding.FormAuthenticateActivityBinding
import br.com.mobile.fernanda.designer.network.ApiClient
import br.com.mobile.fernanda.designer.network.AuthService
import br.com.mobile.fernanda.designer.network.AuthenticateRequest
import br.com.mobile.fernanda.designer.network.JwtTokenService
import br.com.mobile.fernanda.designer.network.LoginRequest
import br.com.mobile.fernanda.designer.network.manager.TokenManager
import kotlinx.coroutines.launch
import kotlin.math.log

class FormAuthenticateActivity : AppCompatActivity() {

    private lateinit var binding: FormAuthenticateActivityBinding

    private lateinit var tokenManager: TokenManager
    private lateinit var jwtTokenService: JwtTokenService
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormAuthenticateActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.formAuthenticateButtonAuthenticateActivity.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        val email = binding.formAuthenticateEmailActivity.text.toString().trim()
        val password = binding.formAuthenticateSecretActivity.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "E-mail e senha são de preenchimento obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.formAuthenticateButtonAuthenticateActivity.isEnabled = false

        tokenManager = TokenManager(this)
        jwtTokenService = ApiClient.generateJwtToken(tokenManager)
        authService = ApiClient.authenticate(tokenManager)

        lifecycleScope.launch {
            try {
                tokenManager.clearToken()
                val jwtResponse = jwtTokenService.login(LoginRequest(
                    "admin",
                    "admin"
                ))
                tokenManager.saveToken(jwtResponse.token, jwtResponse.expirationTime)
                Log.d("JWT LOGIN", "doLogin: ${jwtResponse.token}")
                val payload = AuthenticateRequest(email, password)
                val response = authService.authenticate(payload)
                Log.d("AUTHENTICATE RESPONSE", "doLogin: ${response.toString()}")
                Toast.makeText(this@FormAuthenticateActivity , response.toString(), Toast.LENGTH_LONG).show()
            } catch (ex: Exception) {
                Toast.makeText(this@FormAuthenticateActivity, "Falha ao autenticar ${ex.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.formAuthenticateButtonAuthenticateActivity.isEnabled = true
            }
        }
    }

}