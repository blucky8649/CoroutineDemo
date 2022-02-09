package com.ebookfrenzy.coroutinedomo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.ebookfrenzy.coroutinedomo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var count = 1
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    count = progress
                    binding.countText.text = "${count} coroutines"
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                }
            })
    }
    @SuppressLint("SetTextI18n")
    fun launchCoroutines(view: View) {
        (1..count).forEach {
            binding.statusText.text = "Started Coroutine $it"
            coroutineScope.launch(Dispatchers.Main) {
                binding.statusText.text = performTask(it).await()
            }
        }
    }
    suspend fun performTask(tasknumber: Int): Deferred<String> =
         coroutineScope.async(Dispatchers.Main) {
             delay(5000L)
             return@async "Finished Coroutine ${tasknumber}"
         }
}