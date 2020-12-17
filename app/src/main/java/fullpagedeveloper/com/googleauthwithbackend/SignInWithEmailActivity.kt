package fullpagedeveloper.com.googleauthwithbackend

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_sign_in_with_email.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignInWithEmailActivity : AppCompatActivity() {

    /*fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z]).{5,6}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }*/

    fun isValid(s: String) : Boolean {
        val regex = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z]).{5,6}$")
        return regex.matcher(s).find() && s.length < 6
    }

    lateinit var textInputPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_with_email)

        val emailAddress = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        val PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*?[A-Z])" +
                    "(?=.*?[a-z])" +
                    ".{1,}\$"
        ).toRegex()

//         val  boolean = isValid(s: String) {
//                    val regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
//
//                    if (regex.matcher(s).find() && s.length() > 6) {
//                        return true
//                    }
//                    if (isValid(s.toString())) {
//                        textInputPassword.setEndIconDrawable(R.drawable.ic_success)
//                    } else {
//                        textInputPassword.setEndIconDrawable(R.drawable.ic_canceles)
//                    }
//                }



        textInputPassword = findViewById(R.id.textInputPassword)

        textInputPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.toString().length < 6) {
//                    textInputPassword.setErrorIconDrawable(R.drawable.ic_canceles)
//                    textInputPassword.focusable
//                    textInputPassword.setErrorEnabled(true)
//                }
//
//                if (textInputPassword == PASSWORD_PATTERN) {
//                    textInputPassword.setErrorIconDrawable(R.drawable.ic_canceles)
//                    textInputPassword.focusable
//                    textInputPassword.setErrorEnabled(true)
//
//                } else {
//                    textInputPassword.setErrorIconDrawable(R.drawable.ic_success)
//                    textInputPassword.focusable
//                    textInputPassword.setErrorEnabled(true)
//                }

                /*if (s.length < 7 && isValidPassword(editTextPassword.text.toString())) {
                    textInputPassword.endIconMode = TextInputLayout.END_ICON_CUSTOM
                    textInputPassword.endIconDrawable = ContextCompat.getDrawable(
                        this@SignInWithEmailActivity, R.drawable.ic_success)
                    textInputPassword.isErrorEnabled
                    textInputPassword.isEnabled = true

                    if (s.length > 7) {
                        textInputPassword.endIconMode = TextInputLayout.END_ICON_CUSTOM
                        textInputPassword.endIconDrawable = ContextCompat.getDrawable(
                            this@SignInWithEmailActivity, R.drawable.ic_canceles)
                        textInputPassword.isErrorEnabled
                        textInputPassword.isEnabled = true
                    }

                } else {
                    textInputPassword.endIconMode = TextInputLayout.END_ICON_CUSTOM
                    textInputPassword.endIconDrawable = ContextCompat.getDrawable(
                        this@SignInWithEmailActivity, R.drawable.ic_canceles)
                }*/

                if (isValid(s.toString())) {
                    textInputPassword.endIconMode = TextInputLayout.END_ICON_CUSTOM;
                    textInputPassword.endIconDrawable = ContextCompat.getDrawable(this@SignInWithEmailActivity, R.drawable.ic_success)
                } else {
                    textInputPassword.endIconMode = TextInputLayout.END_ICON_CUSTOM;
                    textInputPassword.endIconDrawable = ContextCompat.getDrawable(this@SignInWithEmailActivity, R.drawable.ic_canceles)
                    textInputPassword.isErrorEnabled
                    textInputPassword.isEnabled = true
                    //textInputPassword.error = "Password maxmium contain 6 characters and must at least have a character"
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })

        submitButtom.setOnClickListener {


//            if (editTextPassword.text.toString().length < 6) {
//                editTextPassword.setError("password minimum contain 6 character")
//                editTextPassword.requestFocus()
//                editTextPassword.isEnabled = true
//                val toast = Toast.makeText(applicationContext, "email dan password tidak boleh kosong", LENGTH_LONG)
//                toast.show()
//            }
//            if (editTextPassword.text.toString().length > 6) {
//                editTextPassword.setError("password maximum contain 6 character")
//                editTextPassword.requestFocus()
//            }
//            if (!PASSWORD_PATTERN.matches(password)) {
//                editTextPassword.setError("harus ada huruf besar dan huruf kecil")
//                editTextPassword.requestFocus()
//            }
//            if (editTextPassword.text.toString().equals("")) {
//                editTextPassword.setError("please enter password")
//                editTextPassword.requestFocus();
//            }


        }
    }


}
