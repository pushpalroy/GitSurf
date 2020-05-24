package com.gitsurfer.gitsurf.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
  this.run {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
  }
}

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
  url?.let {
    Glide.with(context)
        .load(url)
        .into(this)
  }
}

