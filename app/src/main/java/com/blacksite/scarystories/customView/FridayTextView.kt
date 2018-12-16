package com.blacksite.scarystories.customView

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class FridayTextView:TextView {
    constructor(context: Context) : super(context) {
        this.typeface = Typeface.createFromAsset(context.assets, "fonts/Friday13v12.ttf")
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.typeface = Typeface.createFromAsset(context.assets, "fonts/Friday13v12.ttf")
    }
}