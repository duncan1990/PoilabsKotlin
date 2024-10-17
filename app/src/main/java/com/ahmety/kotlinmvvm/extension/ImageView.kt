package com.ahmety.kotlinmvvm.extension

import android.widget.ImageView
import com.ahmety.poilabscase.R
import com.bumptech.glide.Glide

fun ImageView.loadImagesAsThumbnail(url: String) {

    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.img_search)
        .error(R.drawable.ic_error_img)
        .into(this)

}

fun ImageView.loadImages(url: String) {

    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.img_search)
        .error(R.drawable.ic_error_img)
        .into(this)

}