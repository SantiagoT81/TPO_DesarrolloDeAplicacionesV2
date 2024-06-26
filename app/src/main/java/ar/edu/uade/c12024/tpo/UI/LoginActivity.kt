package ar.edu.uade.c12024.tpo.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ar.edu.uade.c12024.tpo.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var registrarse: TextView
    private lateinit var iniciarGoogle: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    //------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bind()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //checkear strings.xml por la clave de API web de firebase
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()

        setListeners()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch(e: Exception){
                Log.e("GOOGLE", "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {authResult ->
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                if(authResult.additionalUserInfo!!.isNewUser){
                    Toast.makeText(this@LoginActivity, "Cuenta creada... ", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@LoginActivity, "Cuenta existente... ", Toast.LENGTH_LONG).show()
                }
                startActivity(Intent(this@LoginActivity, NavegadorActivity::class.java))
                finish()
            }
            .addOnFailureListener{e ->
                Toast.makeText(this@LoginActivity, "Login fallido... ", Toast.LENGTH_LONG).show()
            }
    }

    private fun bind(){
        iniciarGoogle = findViewById(R.id.btnLoginGoogle)
        registrarse = findViewById(R.id.txtRegistrarse)
        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        login = findViewById(R.id.btnLogin)
    }

    private fun setListeners(){
        //LOGIN
        login.setOnClickListener {
            //val intent = Intent(this, NavegadorActivity::class.java)
            //startActivity(intent)
            val correo = email.text.toString()
            val contrasenia = password.text.toString()

            if(correo.isNotEmpty() && contrasenia.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(correo, contrasenia).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, NavegadorActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Ingresar correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        registrarse.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }



        iniciarGoogle.setOnClickListener{
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
        }
    }




}