package com.example.randombombapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.VISIBLE
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.randombombapp.bomb.RVBombsAdapter
import com.example.randombombapp.databinding.ActivityMainBinding
import java.lang.Integer.min
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val min = 1
    val step = 25
    val max = 500 / step

    private lateinit var rvBombAdapter: RVBombsAdapter
    var buttonClickClicked = 1
    var flag = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //binding variables
        val tvSelectBombNumber = binding.tvSelectBombNumber
        val btnChoose = binding.btnChoose
        val sbBombNumber = binding.sbBombNumber
        val tvSbBombNumber = binding.tvSbBombNumber
        val btnClick = binding.btnClick
        btnClick.text = "${buttonClickClicked}번째 폭파"

        val imagesString = mutableListOf<String>()

        sbBombNumber.min = min;
        sbBombNumber.max = max;

        sbBombNumber.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                setSeekBarChange(progress * 25, tvSbBombNumber)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })



        btnChoose.setOnClickListener {
            tvSelectBombNumber.text = "남은 폭탄 개수"
            val toast =
                Toast.makeText(this, "폭탄 ${tvSbBombNumber.text}개 제조 완료.", Toast.LENGTH_SHORT)
            toast.show()

            val handler = Handler(Looper.getMainLooper())

            handler.postDelayed(Runnable { toast.cancel() }, 1000)


            btnChoose.visibility = View.GONE
            sbBombNumber.visibility = View.GONE
            tvSelectBombNumber.text = "남은 폭탄 개수"

            btnClick.visibility = View.VISIBLE

            rvBombAdapter = RVBombsAdapter(imagesString)
            val rvBombs = binding.rvBombs
            for (i in 0 until tvSbBombNumber.text.toString().toInt()) {
                imagesString.add("/drawable/bomb")
            }

            rvBombs.adapter = rvBombAdapter
            if(tvSbBombNumber.text.toString().toInt() > 100) {
                rvBombs.layoutManager = GridLayoutManager(this, 25)
            }else {
                rvBombs.layoutManager = GridLayoutManager(this, 10)
            }


            rvBombAdapter.notifyDataSetChanged()
        }


        btnClick.setOnClickListener {
            buttonClickClicked++
            btnClick.text = "${buttonClickClicked}번째 폭파"
            val numberOfBombs = imagesString.size
            val destroyedBombs = min(Random.nextInt(0, numberOfBombs + 1), Random.nextInt(0, numberOfBombs + 1))
            val survivedBombs = numberOfBombs - destroyedBombs
            tvSbBombNumber.text = survivedBombs.toString()
            val toast =
                Toast.makeText(this, "폭탄 ${survivedBombs}개 남았습니다.", Toast.LENGTH_SHORT)
            toast.show()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(Runnable { toast.cancel() }, 500)

            for (i in 1..destroyedBombs) {
                imagesString.removeAt(0)
                rvBombAdapter.notifyItemRemoved(0);
            }


            if(tvSbBombNumber.text.toString().toInt() <= 100 && flag) {
                binding.rvBombs.layoutManager = GridLayoutManager(this, 10)
                flag = false;
            }

            if(tvSbBombNumber.text == "0") {
                tvSbBombNumber.text = "폭탄이 전부 폭파되었습니다"
                Toast.makeText(this, "폭탄이 전부 폭파되었습니다", Toast.LENGTH_SHORT).show()
                btnClick.isEnabled = false
                binding.btnRestart.visibility = VISIBLE
            }
        }

        binding.btnRestart.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finishAffinity()
        }

    }

    fun setSeekBarChange(value: Int, tvSeekBarValue: TextView) {
        var seekBarValue = value
        tvSeekBarValue.text = seekBarValue.toString()
    }


}




