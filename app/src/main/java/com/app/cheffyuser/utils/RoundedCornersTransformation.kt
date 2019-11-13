package com.app.cheffyuser.utils

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import java.security.MessageDigest



class RoundedCornersTransformation @JvmOverloads constructor(private val bitmapPool: BitmapPool, private val radius: Int, private val margin: Int,
                                                             private val cornerType: CornerType = CornerType.ALL
) :
    Transformation<Bitmap> {
        private val diameter: Int = radius * 2
        private var color = Color.BLACK
        private var border: Int = 0

//    val id: String
//        get() = ("RoundedTransformation(radius=" + radius + ", margin=" + margin + ", diameter="
//                + diameter + ", cornerType=" + cornerType.name + ")")

        enum class CornerType {
            ALL,
            TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
            TOP, BOTTOM, LEFT, RIGHT,
            OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
            DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT, BORDER
        }

        constructor(context: Context, radius: Int, margin: Int, @ColorInt color: Int, border: Int) : this(context, radius, margin,
            CornerType.BORDER
        ) {
            this.color = color
            this.border = border
        }

        @JvmOverloads
        constructor(context: Context, radius: Int, margin: Int,
                    cornerType: CornerType = CornerType.ALL
        ) : this(Glide.get(context).bitmapPool, radius, margin, cornerType)

        override fun transform(context: Context, resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
            val source = resource.get()
            val width = source.width
            val height = source.height
            var bitmap: Bitmap? = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888)
            if (bitmap == null)
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap!!)
            val paint = Paint()
            paint.isAntiAlias = true
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
            return BitmapResource.obtain(bitmap, bitmapPool)!!
        }

        private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {
            val right = width - margin
            val bottom = height - margin
            when (cornerType) {
                CornerType.ALL -> canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), paint)
                CornerType.TOP_LEFT -> drawTopLeftRoundRect(canvas, paint, right, bottom)
                CornerType.TOP_RIGHT -> drawTopRightRoundRect(canvas, paint, right, bottom)
                CornerType.BOTTOM_LEFT -> drawBottomLeftRoundRect(canvas, paint, right, bottom)
                CornerType.BOTTOM_RIGHT -> drawBottomRightRoundRect(canvas, paint, right, bottom)
                CornerType.TOP -> drawTopRoundRect(canvas, paint, right, bottom)
                CornerType.BOTTOM -> drawBottomRoundRect(canvas, paint, right, bottom)
                CornerType.LEFT -> drawLeftRoundRect(canvas, paint, right, bottom)
                CornerType.RIGHT -> drawRightRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_TOP_LEFT -> drawOtherTopLeftRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_TOP_RIGHT -> drawOtherTopRightRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_BOTTOM_LEFT -> drawOtherBottomLeftRoundRect(canvas, paint, right, bottom)
                CornerType.OTHER_BOTTOM_RIGHT -> drawOtherBottomRightRoundRect(canvas, paint, right, bottom)
                CornerType.DIAGONAL_FROM_TOP_LEFT -> drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom)
                CornerType.DIAGONAL_FROM_TOP_RIGHT -> drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom)
                CornerType.BORDER -> drawBorder(canvas, paint, right, bottom)
//            else -> canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), paint)
            }
        }

        private fun drawTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), (margin + diameter).toFloat()),
                radius.toFloat(), radius.toFloat(), paint)
            canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), (margin + radius).toFloat(), bottom), paint)
            canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom), paint)
        }

        private fun drawTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - diameter, margin.toFloat(), right, (margin + diameter).toFloat()), radius.toFloat(),
                radius.toFloat(), paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom), paint)
            canvas.drawRect(RectF(right - radius, (margin + radius).toFloat(), right, bottom), paint)
        }

        private fun drawBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), bottom - diameter, (margin + diameter).toFloat(), bottom),
                radius.toFloat(), radius.toFloat(), paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom - radius), paint)
            canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom), paint)
        }

        private fun drawBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - diameter, bottom - diameter, right, bottom), radius.toFloat(),
                radius.toFloat(), paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom), paint)
            canvas.drawRect(RectF(right - radius, margin.toFloat(), right, bottom - radius), paint)
        }

        private fun drawTopRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, (margin + diameter).toFloat()), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), right, bottom), paint)
        }

        private fun drawBottomRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), bottom - diameter, right, bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom - radius), paint)
        }

        private fun drawLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom), paint)
        }

        private fun drawRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - diameter, margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom), paint)
        }

        private fun drawOtherTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), bottom - diameter, right, bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRoundRect(RectF(right - diameter, margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom - radius), paint)
        }

        private fun drawOtherTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRoundRect(RectF(margin.toFloat(), bottom - diameter, right, bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom - radius), paint)
        }

        private fun drawOtherBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, (margin + diameter).toFloat()), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRoundRect(RectF(right - diameter, margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), right - radius, bottom), paint)
        }

        private fun drawOtherBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, (margin + diameter).toFloat()), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom), radius.toFloat(), radius.toFloat(),
                paint)
            canvas.drawRect(RectF((margin + radius).toFloat(), (margin + radius).toFloat(), right, bottom), paint)
        }

        private fun drawDiagonalFromTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), (margin + diameter).toFloat()),
                radius.toFloat(), radius.toFloat(), paint)
            canvas.drawRoundRect(RectF(right - diameter, bottom - diameter, right, bottom), radius.toFloat(),
                radius.toFloat(), paint)
            canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), right - diameter, bottom), paint)
            canvas.drawRect(RectF((margin + diameter).toFloat(), margin.toFloat(), right, bottom - radius), paint)
        }

        private fun drawDiagonalFromTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            canvas.drawRoundRect(RectF(right - diameter, margin.toFloat(), right, (margin + diameter).toFloat()), radius.toFloat(),
                radius.toFloat(), paint)
            canvas.drawRoundRect(RectF(margin.toFloat(), bottom - diameter, (margin + diameter).toFloat(), bottom),
                radius.toFloat(), radius.toFloat(), paint)
            canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom - radius), paint)
            canvas.drawRect(RectF((margin + radius).toFloat(), (margin + radius).toFloat(), right, bottom), paint)
        }

        private fun drawBorder(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
            // stroke
            val strokePaint = Paint()
            strokePaint.style = Paint.Style.STROKE
            strokePaint.color = color
            strokePaint.strokeWidth = border.toFloat()
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), paint)
            // stroke
            canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), strokePaint)
        }


        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        }
}
