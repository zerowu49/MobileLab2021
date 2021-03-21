package id.ac.umn.uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(intent.getStringExtra("position").equals("listlagu",true)){
            if(item.itemId == android.R.id.home){
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}