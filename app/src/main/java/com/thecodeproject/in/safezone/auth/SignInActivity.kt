package com.thecodeproject.`in`.safezone.auth

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thecodeproject.`in`.safezone.MainActivity
import com.thecodeproject.`in`.safezone.R
import com.thecodeproject.`in`.safezone.databinding.ActivitySignInBinding
import com.thecodeproject.`in`.safezone.sharedPref.AuthSharedPref

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var authSharedPref: AuthSharedPref
    private lateinit var pd: ProgressDialog // Changed to lateinit

    //login
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Hide the status bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        actionBar?.hide()
        setContentView(binding.root)
        authSharedPref = AuthSharedPref(this)

        // Initialize ProgressDialog here
        pd = ProgressDialog(this).apply {
            setMessage("Please wait..")
            setCancelable(false)
        }

        //login
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        /*Sign In Btn*/
        binding.btnSignIn.setOnClickListener {
            if (authSharedPref.isSignedIn()) {
                Snackbar.make(
                    binding.main,
                    "Welcome ${authSharedPref.userName()!!} 👋👋!!",
                    Snackbar.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
                exit()
            } else {
                signIn()
            }
        }

        binding.btnContinue.setOnClickListener { setupProfile() }
    }

    private fun signIn() {
        pd.show()
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                // Save onboarding status and login type in SharedPreferences
                authSharedPref.setAuthStatus(true)
                authSharedPref.setUserName(auth.currentUser?.displayName!!)
                authSharedPref.setUID(auth.currentUser!!.uid)
                Snackbar.make(
                    binding.main,
                    "Welcome ${authSharedPref.userName()!!} 👋👋!!",
                    Snackbar.LENGTH_SHORT
                ).show()
                pd.dismiss()
                //Changing visibility
                binding.btnSignIn.visibility = View.GONE
                binding.etUserName.visibility = View.VISIBLE
                binding.countryCode.visibility = View.VISIBLE
                binding.btnContinue.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupProfile() {
        authSharedPref.setUserName(binding.etUserName.text.toString())
        authSharedPref.setCountry(binding.countryCode.selectedCountryName.toString())
        pd.show()
        exit()
    }

    private fun exit() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        pd.dismiss()
        finishAffinity()
    }

}