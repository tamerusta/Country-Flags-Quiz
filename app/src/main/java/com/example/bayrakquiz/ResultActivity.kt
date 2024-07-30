package com.example.bayrakquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bayrakquiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dogruSayac = intent.getIntExtra("dogruSayac",0)

        binding.textViewSonuc.text = "$dogruSayac DOĞRU ${5-dogruSayac} YANLIŞ"

        binding.textViewYuzdeSonuc.text = "%${(dogruSayac)*100/5} BAŞARI ORANI"

        binding.buttonTekrar.setOnClickListener {

            startActivity(Intent(this,QuizActivity::class.java))
            finish()
        }
    }
}
