package com.muhammed.encryptedsharedpreferences

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.muhammed.encryptedsharedpreferences.databinding.ActivityMainBinding

/**
 * Created by Muhammed Elşami on 14.10.2024.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var sharedPreferences: EncryptedSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val masterKeyAlias = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "secret_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences


        mainBinding.saveBtn.setOnClickListener {
            val text = mainBinding.editText.text.toString()
            if (text.isNotEmpty()) {
                saveData(text)
                mainBinding.editText.text?.clear()
                loadData()
            }
        }

        loadData()

    }

    private fun saveData(text: String) {
        val editor = sharedPreferences.edit()
        editor.putString("data", text)
        editor.apply()
    }

    private fun loadData() {
        mainBinding.textView.text = sharedPreferences.getString("data", "No data found")
    }
}