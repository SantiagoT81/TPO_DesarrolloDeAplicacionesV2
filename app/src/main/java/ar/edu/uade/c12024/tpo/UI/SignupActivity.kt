package ar.edu.uade.c12024.tpo.UI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ar.edu.uade.c12024.tpo.R
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var registrar: Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registroExitosoFragment = RegistroExitosoFragment()
        val registroFallidoFragment = RegistroFallidoFragment()
        firebaseAuth = FirebaseAuth.getInstance()
        bind()
        registrar.setOnClickListener {
            //registroExitosoFragment.show(supportFragmentManager, "RegistroExitosoDialog")
            if(email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() && repassword.text.toString().isNotEmpty()){
                if(password.text.toString() == repassword.text.toString()){
                    firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnSuccessListener {
                            registroExitosoFragment.show(supportFragmentManager, "RegistroExitosoDialog")
                        }
                        .addOnFailureListener{
                            registroFallidoFragment.show(supportFragmentManager, "RegistroFallidoDialog")
                        }
                }
            }
            //AGREGAR PARÁMETRO PARA CAMBIAR EL TEXTO DEL FRAGMENTO FALLIDO (1: Error por email ya registrado o 2: Campos vacíos/inválidos)
            else{
                registroFallidoFragment.show(supportFragmentManager, "RegistroFallidoDialog")
            }
        }

    }

    private fun bind(){
        email = findViewById(R.id.txtSignupEmail)
        password = findViewById(R.id.txtSignupPassword)
        repassword = findViewById(R.id.txtSignupRePassword)
        registrar = findViewById(R.id.btnSignup)


    }
}