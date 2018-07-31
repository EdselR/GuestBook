package com.narindo.guestbook.PhotoId

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.android.gms.vision.text.TextBlock
import com.narindo.guestbook.PhotoId.PhotoIdActivity

class OcrGraphic internal constructor(overlay: GraphicOverlay<*>, val textBlock: TextBlock?) : GraphicOverlay.Graphic(overlay) {

    var id: Int = 0

    init {

        if (sRectPaint == null) {
            sRectPaint = Paint()
            sRectPaint!!.color = TEXT_COLOR
            sRectPaint!!.style = Paint.Style.STROKE
            sRectPaint!!.strokeWidth = 4.0f
        }

        if (sTextPaint == null) {
            sTextPaint = Paint()
            sTextPaint!!.color = TEXT_COLOR
            sTextPaint!!.textSize = 54.0f
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.

     * @param x An x parameter in the relative context of the canvas.
     * *
     * @param y A y parameter in the relative context of the canvas.
     * *
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    override fun contains(x: Float, y: Float): Boolean {
        // TODO: Check if this graphic's text contains this point.
        if (textBlock == null) {
            return false
        }

        val rect = RectF(textBlock.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        return rect.left < x && rect.right > x && rect.top < y && rect.bottom > y
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas, context: Context) {
        // TODO: Draw the text onto the canvas.

        val act = context as PhotoIdActivity
        if (textBlock == null) {
            return
        }

        act.showButton(true)

        // Draws the bounding box around the TextBlock.
        val rect = RectF(textBlock.boundingBox)
        val left:Int = canvas.width/6
        val right:Int = canvas.width - (canvas.width/6)
        val top:Int = canvas.height/4
        val bottom:Int = canvas.height - (canvas.height/4)
//        Log.e("Height", ":"+ canvas.height)
//        Log.e("width", ":"+ canvas.width)
        rect.left = left.toFloat()
        rect.top = top.toFloat()
        rect.right = right.toFloat()
        rect.bottom = bottom.toFloat()
        canvas.drawRect(rect, sRectPaint!!)

        // Render the text at the bottom of the box.
        //canvas.drawText(textBlock.value, rect.left, rect.bottom, sTextPaint!!)
        //canvas.drawRect(, top, right, bottom, sTextPaint)
        //canvas.drawRect(141.53563f, 1123.5483f, 345.97595f, 1424.0931f, sTextPaint)

    }

    /**

     */

    companion object {

        private val TEXT_COLOR = Color.WHITE

        private var sRectPaint: Paint? = null
        private var sTextPaint: Paint? = null

        private val BOX_STROKE_WIDTH = 5.0f
        private val COLOR_CHOICES = intArrayOf(Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW)
        private var mCurrentColorIndex = 0
    }
}
