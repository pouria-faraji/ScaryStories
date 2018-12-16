package com.blacksite.scarystories.customView

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class FaceYourFearTextView:TextView{
    constructor(context: Context) : super(context) {
        this.typeface = Typeface.createFromAsset(context.assets, "fonts/DK Face Your Fears.ttf")
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.typeface = Typeface.createFromAsset(context.assets, "fonts/DK Face Your Fears.ttf")
    }
}