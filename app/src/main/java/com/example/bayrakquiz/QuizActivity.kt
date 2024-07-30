package com.example.bayrakquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bayrakquiz.databinding.ActivityQuizBinding
import java.util.HashSet


class QuizActivity : AppCompatActivity() {
    private lateinit var sorular: ArrayList<Bayraklar>
    private lateinit var yanlisSecenekler: ArrayList<Bayraklar>
    private lateinit var dogruSoru: Bayraklar
    private lateinit var tumSecenekler: HashSet<Bayraklar>
    private lateinit var binding: ActivityQuizBinding
    private lateinit var vt: VeritabaniYardimcisi

    private var soruSayac = 0
    private var dogruSayac = 0
    private var yanlisSayac = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vt = VeritabaniYardimcisi(this)

        sorular = Bayraklardao().rastgele5BayrakGetir(vt)

        soruYukle()

            binding.button1.setOnClickListener {
                dogruKontrol(binding.button1)
                soruSayacKontrol()
            }

            binding.button2.setOnClickListener {
                dogruKontrol(binding.button2)
                soruSayacKontrol()
            }

            binding.button3.setOnClickListener {
                dogruKontrol(binding.button3)
                soruSayacKontrol()
            }

            binding.button4.setOnClickListener {
                dogruKontrol(binding.button4)
                soruSayacKontrol()
            }
        }

    fun soruYukle() {

            binding.textViewSoruSayi.text = "${soruSayac + 1}. Soru"

            dogruSoru = sorular.get(soruSayac)

            binding.imageViewBayrak.setImageResource(
                resources.getIdentifier(dogruSoru.bayrak_resim, "drawable", packageName)
            )

            yanlisSecenekler = Bayraklardao().rastgele3YanlisSecenekGetir(vt, dogruSoru.bayrak_id)

        tumSecenekler = HashSet()
        tumSecenekler.add(dogruSoru)
        tumSecenekler.add(yanlisSecenekler.get(0))
        tumSecenekler.add(yanlisSecenekler.get(1))
        tumSecenekler.add(yanlisSecenekler.get(2))

        binding.button1.text = tumSecenekler.elementAt(0).bayrak_ad
        binding.button2.text = tumSecenekler.elementAt(1).bayrak_ad
        binding.button3.text = tumSecenekler.elementAt(2).bayrak_ad
        binding.button4.text = tumSecenekler.elementAt(3).bayrak_ad

    }

    fun dogruKontrol(button: Button) {
        val buttonYazi = button.text.toString()
        val dogruCevap = dogruSoru.bayrak_ad

        if (buttonYazi == dogruCevap) {
            dogruSayac++
        } else {
            yanlisSayac++
        }

        binding.textViewDogruSayi.text = "Doğru $dogruSayac"
        binding.textViewYanlisSayi.text = "Yanlış $yanlisSayac"
    }

    fun soruSayacKontrol() {
        soruSayac++

        if (soruSayac != 5) {
            soruYukle()
        } else {
            val intent = Intent(this@QuizActivity, ResultActivity::class.java)
            intent.putExtra("dogruSayac", dogruSayac)
            startActivity(intent)
            finish()
        }
    }
}
