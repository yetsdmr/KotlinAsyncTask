package com.yetsdmr.kotlinasynctask

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build.VERSION_CODES.O
import android.widget.*
import java.lang.Exception
import java.net.URL

class AsyncImageDownload(
    val context : Context,
    val rootLayout : LinearLayout,
    val progressBar : ProgressBar,
    val textView : TextView
) : AsyncTask<String, Int, MutableList<Bitmap>>(){

    override fun onPreExecute() {
        progressBar.progress = 0
        textView.text = "Download started..."
        super.onPreExecute()
    }

    override fun doInBackground(vararg p0: String?): MutableList<Bitmap> {
        val list = ArrayList<Bitmap>()

        for(i in O until p0.size){
            val urlOfImage = URL(p0[i])
            try {
                val inputStream = urlOfImage.openStream()
                list.add(BitmapFactory.decodeStream(inputStream)!!)
            }catch (e : Exception){
                e.printStackTrace()
            }

            publishProgress(((i + 1) / p0.size.toFloat() * 100).toInt())

            if (isCancelled)break
        }
        return list
    }

    override fun onProgressUpdate(vararg values: Int?) {
        progressBar.progress = values[0]!!
        textView.text = "Download completed... ${values[0]}%"
        Toast.makeText(context,"Download ${values[0]} %",Toast.LENGTH_LONG).show()
        super.onProgressUpdate(*values)
    }

    override fun onCancelled(result: MutableList<Bitmap>?) {
        textView.text = "Task cancelled..\n ${result!!.size} file  files download success"

        for (bitmap in result){
            rootLayout.addView(newImageView(bitmap))
        }

        super.onCancelled(result)
    }

    override fun onPostExecute(result: MutableList<Bitmap>?) {
        textView.text = "Task finish...\n ${result?.size} files download success"
        for (bitmap in result!!){
            rootLayout.addView(newImageView(bitmap))
        }

        super.onPostExecute(result)
    }

    private fun newImageView(bitmap: Bitmap): ImageView {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300)
        params.bottomMargin = 15
        val imageView = ImageView(context)
        imageView.layoutParams = params
        imageView.setImageBitmap(bitmap)
        imageView.setBackgroundColor(Color.LTGRAY)
        imageView.setPadding(10,10,10,10)
        return imageView
    }
}