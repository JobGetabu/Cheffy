package com.app.cheffyuser.utils

import android.app.Activity
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.app.cheffyuser.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.android.material.snackbar.Snackbar
import com.mikhaellopez.circularimageview.CircularImageView

inline fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast = Toast
    .makeText(this, message, duration)
    .apply {
        show()
    }

fun ImageView.loadUrl(url: Int) {
    Glide.with(context)
        .load(url)
        .thumbnail(0.05f)
        .into(this)

}

fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
        .thumbnail(0.05f)
        .into(this)
}

fun ImageView.loadUrl(url: String?,tinted: Boolean) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
        .thumbnail(0.05f)
        .into(this)

    this.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.OVERLAY)
}

fun ImageView.loadUrlTransformToCircle(url: String?) {
    val borderWidth= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, context.resources.displayMetrics).toInt()
    val iconRoundedCornersRadius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, context.resources.displayMetrics).toInt()

    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions.centerCropTransform())
        .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(context, iconRoundedCornersRadius, 0, 0xff999999.toInt(), borderWidth)))
        .into(object : BitmapImageViewTarget(this) {
        })

    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0f)
    val filter = ColorMatrixColorFilter(colorMatrix)
    this.colorFilter = filter
}

fun CircularImageView.loadUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
        .thumbnail(0.05f)
        .into(this)
}

fun TextView.setFont(font: String) {
    this.typeface = Typeface.createFromAsset(context.assets, font)
}

fun TextView.setDrawable(icon: Drawable) {
    this.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun EditText.setDrawable(icon: Drawable) {
    this.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment).addToBackStack(fragment.tag) }
}

class SemiSquareLayout : RelativeLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec - 80)
    }

}

class SquareLayout : RelativeLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}

fun createSnack(ctx: Activity, v: View = ctx.findViewById<View>(android.R.id.content), txt: String) {
    Snackbar.make(v, txt, Snackbar.LENGTH_LONG).show()
}

fun createSnack(ctx: Activity, txt: String, txtAction: String, action: View.OnClickListener) {
    Snackbar.make(ctx.findViewById<View>(android.R.id.content), txt, Snackbar.LENGTH_LONG)
        .setAction(txtAction, action)
        .show()
}

fun createSnack(ctx: Activity, txt: String, txtAction: String, isDefinate: Boolean, action: View.OnClickListener) {
    Snackbar.make(ctx.findViewById<View>(android.R.id.content), txt, Snackbar.LENGTH_INDEFINITE)
        .setAction(txtAction, action)
        .show()
}

fun createShortSnack(ctx: Activity, txt: String) {
    Snackbar.make(ctx.findViewById<View>(android.R.id.content), txt, Snackbar.LENGTH_SHORT).show()
}

