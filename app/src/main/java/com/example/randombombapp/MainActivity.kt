package com.example.randombombapp

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.randombombapp.bomb.RVBombsAdapter
import com.example.randombombapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val min = 1
    val max = 100
    private lateinit var rvBombAdapter: RVBombsAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //binding variables
        val btnChoose = binding.btnChoose
        val sbBombNumber = binding.sbBombNumber
        val tvSbBombNumber = binding.tvSbBombNumber
        val btnClick = binding.btnClick

        val imagesString = mutableListOf<String>()

        sbBombNumber.min = min;
        sbBombNumber.max = max;

        sbBombNumber.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                setSeekBarChange(progress, tvSbBombNumber)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })



        btnChoose.setOnClickListener {
            val toast =
                Toast.makeText(this, "폭탄 ${tvSbBombNumber.text}개 제조 완료.", Toast.LENGTH_SHORT)
            toast.show()

            val handler = Handler()
            handler.postDelayed(Runnable { toast.cancel() }, 1000)


            btnChoose.visibility = View.GONE
            sbBombNumber.visibility = View.GONE
            btnClick.visibility = View.VISIBLE

            rvBombAdapter = RVBombsAdapter(imagesString)
            val rvBombs = binding.rvBombs
            for (i in 0 until tvSbBombNumber.text.toString().toInt()) {
                imagesString.add("/drawable/bomb")
            }

            rvBombs.adapter = rvBombAdapter
            rvBombs.layoutManager = GridLayoutManager(this, 10)

            rvBombAdapter.notifyDataSetChanged()
        }


        btnClick.setOnClickListener {
            val numberOfBombs = imagesString.size
            val destroyedBombs = Random.nextInt(0, numberOfBombs + 1)
            val survivedBombs = numberOfBombs - destroyedBombs
            tvSbBombNumber.text = survivedBombs.toString()
            val toast =
                Toast.makeText(this, "폭탄 ${survivedBombs}개 남았습니다.", Toast.LENGTH_SHORT)
            toast.show()

            val handler = Handler()
            handler.postDelayed(Runnable { toast.cancel() }, 500)

            for (i in 1..destroyedBombs) {
                imagesString.removeAt(0)
                rvBombAdapter.notifyItemRemoved(0);
            }

            if(tvSbBombNumber.text == "0") {
                tvSbBombNumber.text = "폭탄이 전부 폭파되었습니다"
                Toast.makeText(this, "폭탄이 전부 폭파되었습니다", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun setSeekBarChange(value: Int, tvSeekBarValue: TextView) {
        var seekBarValue = value
        tvSeekBarValue.text = seekBarValue.toString()
    }
}




