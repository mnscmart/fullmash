import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable


class TextDrawable(private val airQuality: Double, private val textColor: Int, private val bgColor: Int) : Drawable() {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds

        paint.color = bgColor
        canvas.drawRect(bounds, paint)

        paint.color = textColor
        val xPos = bounds.centerX().toFloat()
        val yPos = bounds.centerY() - ((paint.descent() + paint.ascent()) / 2)
        canvas.drawText(airQuality.toString(), xPos, yPos, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return android.graphics.PixelFormat.OPAQUE
    }
}