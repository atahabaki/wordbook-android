package dev.atahabaki.wordbook.utils

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.gotoURL(url: String) {
    val i: Intent = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}