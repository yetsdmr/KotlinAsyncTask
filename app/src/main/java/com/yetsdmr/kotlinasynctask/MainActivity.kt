package com.yetsdmr.kotlinasynctask

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task = AsyncImageDownload(this,root_layout,progress_bar,text_view)

        button_start.setOnClickListener {
            task.execute("https://images.freeimages.com/images/large-previews/310/spring-1-1405906.jpg",
                "https://images.freeimages.com/images/large-previews/8f3/white-flower-power-1403046.jpg",
                "https://images.freeimages.com/images/large-previews/81c/flower-1393311.jpg",
                "https://images.freeimages.com/images/large-previews/7f7/statice-1406388.jpg",
                "https://images.freeimages.com/images/large-previews/760/wedding-flower-1188350.jpg"
            )
            it.isEnabled = false
            button_cancel.isEnabled =true
        }

        button_cancel.setOnClickListener {
            task.cancel(true)
            it.isEnabled = false
        }

        root_layout.setOnClickListener {
            when(task.status){
                AsyncTask.Status.RUNNING -> toast("Task running")
                AsyncTask.Status.PENDING -> toast("Task pending")
                AsyncTask.Status.FINISHED -> toast("Task finished")
                else -> toast("Task status Unknown")
            }
        }
    }

    fun Context.toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}