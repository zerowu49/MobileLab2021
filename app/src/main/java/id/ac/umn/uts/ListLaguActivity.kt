package id.ac.umn.uts

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_list_lagu.*


class ListLaguActivity : AppCompatActivity() {
    companion object {
        lateinit var musicFiles: ArrayList<MusicFiles>
    }

    val REQUEST_CODE = 1
    private val permissionStorage = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lagu)

        dialogSelamatDatang()
        permission()

        recyclerView.adapter = ListLaguAdapter(this, musicFiles)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.profilOption) {
            startActivity(Intent(this, ProfileActivity::class.java))
        } else if (item.getItemId() == R.id.logoutOption) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        return true
    }

    fun dialogSelamatDatang(){
        MaterialAlertDialogBuilder(this)
                .setTitle("Selamat Datang")
                .setMessage("Jonathan \n00000030182")
                .setPositiveButton("OK") { dialog, i -> dialog.cancel();
                }
                .show()

    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionStorage,
                    REQUEST_CODE
            )
        } else {
            musicFiles = getAllAudio(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                musicFiles = getAllAudio(this)
            }else{
                ActivityCompat.requestPermissions(this, permissionStorage, REQUEST_CODE);
            }
        }
    }

    fun getAllAudio(context: Context): ArrayList<MusicFiles> {
        val tempAudioList: ArrayList<MusicFiles> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val album: String = cursor.getString(0)
                val title: String = cursor.getString(1)
                val duration: String = cursor.getString(2)
                val path: String = cursor.getString(3)
                val musicFiles = MusicFiles(path, title, album, duration)
                Log.e("Path:  $path", " Duration : $duration")
                tempAudioList.add(musicFiles)
            }
            cursor.close()
        }
        return tempAudioList
    }
}