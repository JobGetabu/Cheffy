package com.app.cheffyuser.utils

import android.app.ActionBar
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

class ViewAnimations {
    companion object {
        fun expand(view: View, animListener: AnimListener) {
            val a = expandAction(view)

            a.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    animListener.onFinish()
                }

                override fun onAnimationStart(p0: Animation?) {
                }
            })

            view.startAnimation(a)
        }

        private fun expandAction(v: View): Animation {
            v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            val targtetHeight = v.measuredHeight

            v.layoutParams.height = 0
            v.visibility = View.VISIBLE
            val a = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    v.layoutParams.height = if (interpolatedTime == 1f)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else
                        (targtetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            a.duration = ((targtetHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
            v.startAnimation(a)

            return a
        }

        fun collapse(v: View) {
            val initialHeight = v.measuredHeight

            val animation = object: Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1F) {
                        v.hideView()
                    } else {
                        v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            animation.duration = (initialHeight / v.context.resources.displayMetrics.density).toLong()
            v.startAnimation(animation)
        }

        fun toggleArrow(view: View): Boolean {
            return if (view.rotation == 0F) {
                view.animate().setDuration(200).rotation(180F)
                true
            } else {
                view.animate().setDuration(200).rotation(0F)
                false
            }
        }
    }

    interface AnimListener {
        fun onFinish()
    }
}