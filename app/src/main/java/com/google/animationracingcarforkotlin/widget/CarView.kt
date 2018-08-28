package com.google.soul.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.google.animationracingcarforkotlin.R
import kotlinx.android.synthetic.main.widget_car_view.view.*
import java.util.*
import kotlin.collections.ArrayList

/**************************
作者：FYX
日期：2018/8/27 0027
自定义赛车View
 **************************/
class CarView(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {

    private var random = ArrayList<Float>()//记录当前路径数据
    private var lastValue = 0f//局部变量，上次移动距离
    private var currentValue = 0f//当前位移
    private var objectAnimator: ObjectAnimator? = null

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.CarView)
        val carId = ta.getResourceId(R.styleable.CarView_car_resource, 0)
        LayoutInflater.from(context).inflate(R.layout.widget_car_view, this)
        iv_car.setImageResource(carId)
        getRandomList(Random().nextInt(7) + 2)
        //kotlin传递可变参数是加*
        objectAnimator = ObjectAnimator.ofFloat(this, "translationX", *random.toFloatArray())
//        objectAnimator?.repeatCount=-1
//        objectAnimator?.repeatMode=ObjectAnimator.REVERSE
        objectAnimator?.addUpdateListener { animation ->
            currentValue = animation.animatedValue as Float
            if (lastValue <= currentValue) {
                iv_wind.visibility = View.INVISIBLE
                iv_flame.visibility = View.INVISIBLE
            } else {
                iv_wind.visibility = View.VISIBLE
                iv_flame.visibility = View.VISIBLE
            }
            lastValue = currentValue
        }
        objectAnimator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                iv_wind.visibility = View.INVISIBLE
                iv_flame.visibility = View.INVISIBLE
            }
        })
        ta.recycle()
    }

    private fun getRandomList(size: Int) {
        random.clear()
        for (i in 0 until size) {
            when (i) {
                0 -> random.add(0F)
                1 -> random.add(-600F)
                else -> random.add((-Random().nextInt(800)).toFloat())
            }
        }
    }

    fun startRun(time: Long) {
        objectAnimator?.cancel()
        objectAnimator?.duration = time
        objectAnimator?.start()
    }

    fun getFinalValue(): Float {
        return random[random.size - 1]
    }

    fun getCurrentValue(): Float {
        return currentValue
    }
}