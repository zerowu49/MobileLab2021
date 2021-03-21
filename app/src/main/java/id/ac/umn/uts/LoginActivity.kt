package id.ac.umn.uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginClick(view: View) {
        // get value of username & password
        val username = username.editText?.text.toString()
        val password = password.editText?.text.toString()

        // checking the username and password
        if(username == "uasmobile" && password == "uasmobilegenap"){
            val submitIntent = Intent(this, ListLaguActivity::class.java)
            submitIntent.putExtra("position","login")
            startActivity(submitIntent)
        }else{
            Toast.makeText(this, "Username and/or password is incorrect!", Toast.LENGTH_LONG).show()
        }
    }

}