package com.example.memesharingapp

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts.Intents.Insert.ACTION
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.Volley.newRequestQueue
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var currentImageUrl:String ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          loadMeme()
    }

    private fun loadMeme(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
              val currentImageUrl = response.getString("url")
                Glide.with(this)
                    .load(currentImageUrl)

                    .into(memeImageView)

            },
            Response.ErrorListener {
                Toast.makeText(this,"something went wrong" , Toast.LENGTH_LONG).show()
            }
        )

        queue.add(jsonObjectRequest)

    }
    fun shareMeme(view:View){

        val intent = Intent (Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey ,Checkout this cool meme on reddit  $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this name using ...")
        startActivity(chooser)

    }

    fun nextMeme(view:View){

        loadMeme()

    }


}