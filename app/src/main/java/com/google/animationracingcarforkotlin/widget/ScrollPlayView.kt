package com.google.soul.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.google.animationracingcarforkotlin.R

/**************************
作者：FYX
日期：2018/8/22 0022
可以无限滚动的背景
 **************************/

class ScrollPlayView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var speed: Int//滚动速度
    private var direction: Int//1往左滚动，2往右滚动，3往上滚动，4往下滚动
    private val drawable: Drawable
    private val drawableOther: Drawable
    private val drawableWidth: Int
    private val drawableHeight: Int
    private lateinit var valueAnimator: ValueAnimator

    //init模块中的内容可以直接使用构造函数的参数。
    //多个构造函数不能用init模块，用关键字constructor(context: Context) : super(context) {}
    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollPlayView)
        speed = ta.getInteger(R.styleable.ScrollPlayView_speed, 1)
        drawable = ta.getDrawable(R.styleable.ScrollPlayView_resource)
        drawableOther = ta.getDrawable(R.styleable.ScrollPlayView_resource)
        direction = ta.getInteger(R.styleable.ScrollPlayView_direction, 1)
        drawableWidth = drawable.minimumWidth
        drawableHeight = drawable.minimumHeight
        ta.recycle()
        setDirection()
    }

    private fun setDirection() {
        drawable.bounds.set(0, 0, drawableWidth, drawableHeight)
        when (direction) {
            1 -> {
                drawableOther.bounds.set(drawableWidth, 0, drawableWidth * 2, drawableHeight)//在drawable右边
                valueAnimator = ValueAnimator.ofInt(0, drawableWidth)
                valueAnimator.addUpdateListener { animation ->
                    var value = animation.animatedValue as Int * speed//设置速度
                    if (value > drawableWidth) {//过渡值大于宽度，直接重新开始动画
                        valueAnimator.cancel()
                        valueAnimator.start()
                        scrollTo(drawableWidth, 0)
                    } else {
                        scrollTo(value, 0)
                    }
                }
            }
            2 -> {
                drawableOther.bounds.set(-drawableWidth, 0, 0, drawableHeight)//在drawable左边
                valueAnimator = ValueAnimator.ofInt(0, drawableWidth)
                valueAnimator.addUpdateListener { animation ->
                    var value = animation.animatedValue as Int * speed//设置速度
                    if (value > drawableWidth) {//过渡值大于宽度，直接重新开始动画
                        valueAnimator.cancel()
                        valueAnimator.start()
                        scrollTo(-drawableWidth, 0)
                    } else {
                        scrollTo(-value, 0)
                    }
                }
            }
            3 -> {
                drawableOther.bounds.set(0, drawableHeight, drawableWidth, drawableHeight * 2)//在drawable下边
                valueAnimator = ValueAnimator.ofInt(0, drawableHeight)
                valueAnimator.addUpdateListener { animation ->
                    var value = animation.animatedValue as Int * speed//设置速度
                    if (value > drawableHeight) {
                        valueAnimator.cancel()
                        valueAnimator.start()
                        scrollTo(0, drawableHeight)
                    } else {
                        scrollTo(0, value)
                    }
                }
            }
            4 -> {
                drawableOther.bounds.set(0, -drawableHeight, drawableWidth, 0)//在drawable上边
                valueAnimator = ValueAnimator.ofInt(0, drawableHeight)
                valueAnimator.addUpdateListener { animation ->
                    var value = animation.animatedValue as Int * speed//设置速度
                    if (value > drawableHeight) {//过渡值大于高度，直接重新开始动画
                        valueAnimator.cancel()
                        valueAnimator.start()
                        scrollTo(0, -drawableHeight)
                    } else {
                        scrollTo(0, -value)
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), drawableHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawable.draw(canvas)
        drawableOther.draw(canvas)
    }

    fun startPlay() {
        if (valueAnimator.isRunning && valueAnimator.isStarted) {
            valueAnimator.cancel()
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.repeatCount = -1
        valueAnimator.duration = 2000
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.start()
    }

    fun stopPlay() {
        if (valueAnimator.isRunning && valueAnimator.isStarted) {
            valueAnimator.cancel()
        }
    }

    fun setSpeed(speed: Int) {
        this.speed = speed
    }
}