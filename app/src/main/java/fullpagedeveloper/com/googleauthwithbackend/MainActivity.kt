package fullpagedeveloper.com.googleauthwithbackend

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureGoogleSignIn()
        signInClick()

        auth = FirebaseAuth.getInstance()

        signInWithEmail()

        val videoview = findViewById<VideoView>(R.id.launchVideoView)

        //loop video
        videoview.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })

        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.river)
        videoview.setVideoURI(uri)
        videoview.resume()
        videoview.start()

    }

    private fun signInWithEmail() {

        linearLayoutSignInEmail.setOnClickListener {
            startActivity(Intent(this, SignInWithEmailActivity::class.java))
        }
    }

    companion object {
        private val RC_SIGN_IN = 9001

        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user != null) {
            startActivity(HomeActivity.getLaunchIntent(this))
            finish()
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun signInClick() {

        signInWithGoogleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }
    }

    // google login
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: Exception) {
                Log.d(TAG, "Google sign in failed", e)
                Snackbar.make(constrainMain, "Google sign in failed:(", Snackbar.LENGTH_LONG).show()
            }
        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val idToken = taskId
            Log.d(TAG, idToken.toString())

            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Snackbar.make(constrainMain, "Google sign in failed:(", Snackbar.LENGTH_LONG).show()
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle  # " + account.id!!)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    startActivity(HomeActivity.getLaunchIntent(this))
                }
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                Snackbar.make(constrainMain, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()

            }
    }

}
