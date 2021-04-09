package github.tinkzhang.readkeeper

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.text.TextPaint
import java.util.*

class AvatarGenerator {
    companion object {
        lateinit var uiContext: Context
        var texSize = 0F

        fun avatarImage(context: Context, size: Int, name: String): BitmapDrawable {
            uiContext = context
            val width = size
            val hieght = size

            texSize = calTextSize(size)
            val label = firstCharacter(name)
            val textPaint = textPainter()
            val painter = painter()
            val areaRect = Rect(0, 0, width, width)
            painter.color = 0x3F51B5

            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawRect(areaRect, painter)

            val bounds = RectF(areaRect)
            bounds.right = textPaint.measureText(label, 0, 1)
            bounds.bottom = textPaint.descent() - textPaint.ascent()

            bounds.left += (areaRect.width() - bounds.right) / 2.0f
            bounds.top += (areaRect.height() - bounds.bottom) / 2.0f

            canvas.drawCircle(width.toFloat() / 2, hieght.toFloat() / 2, width.toFloat() / 2, painter)
            canvas.drawText(label, bounds.left, bounds.top - textPaint.ascent(), textPaint)
            return BitmapDrawable(uiContext.resources, bitmap)
        }

        private fun firstCharacter(name: String): String {
            return name.first().toString().toUpperCase(Locale.ROOT)
        }

        private fun textPainter(): TextPaint {
            val textPaint = TextPaint()
            textPaint.textSize = texSize * uiContext.resources.displayMetrics.scaledDensity
            textPaint.color = Color.WHITE
            return textPaint
        }

        private fun painter(): Paint {
            return Paint()
        }

        private fun calTextSize(size: Int): Float {
            return (size / 3.125).toFloat()
        }
    }

}