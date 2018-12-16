package com.blacksite.scarystories.application

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView


@BindingAdapter("app:layoutMarginRight")
fun setRightMargin(view: View, rightMargin: Float?) {
    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
            Math.round(rightMargin!!), layoutParams.bottomMargin)
    view.layoutParams = layoutParams
}
@BindingAdapter("app:layoutMarginLeft")
fun setLeftMargin(view: View, leftMargin: Float?) {
    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(Math.round(leftMargin!!), layoutParams.topMargin,
            layoutParams.rightMargin, layoutParams.bottomMargin)
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_width")
fun setWidth(view: View,width:Float?) {
    view.layoutParams.width = width!!.toInt()
}
@BindingAdapter("android:layout_height")
fun setHeight(view: View,height:Float?) {
    view.layoutParams.height = height!!.toInt()
}

@BindingAdapter("android:layout_width")
fun setImageWidth(imageView: ImageView,width:Float?) {
    imageView.layoutParams.width = width!!.toInt()
}

@BindingAdapter("android:layout_height")
fun setImageHeight(imageView: ImageView,height:Float?) {
    imageView.layoutParams.height = height!!.toInt()
}
@BindingAdapter("app:imagePadding")
fun setImagePadding(imageView: ImageView,padding:Float?) {
    imageView.setPadding(padding!!.toInt(),padding!!.toInt(),padding!!.toInt(),padding!!.toInt())
}

@BindingAdapter("android:layout_height")
fun setRelativeLayoutHeight(layout: RelativeLayout,height:Float?) {
    layout.layoutParams.height = height!!.toInt()
}

@BindingAdapter("app:setBitmap")
fun setImageByBitmap(imageview: ImageView, bitmap: Bitmap?) {
    imageview.setImageBitmap(bitmap)
}

@BindingAdapter("app:textSize")
fun setRightMargin(textView: TextView, size: Float?) {
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size!!)
}