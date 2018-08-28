package com.google.animationracingcarforkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.animationracingcarforkotlin.entity.CarEntity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null
    private var duration: Long = 30000//动画时间
    private var list = ArrayList<CarEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        btn_start.setOnClickListener(View.OnClickListener {
            if (countDownTimer != null) {
                countDownTimer?.cancel()
            }
            mScrollPlayView.startPlay()
            cv_car1.startRun(duration)
            cv_car2.startRun(duration)
            cv_car3.startRun(duration)
            cv_car4.startRun(duration)
            cv_car5.startRun(duration)
            cv_car6.startRun(duration)
            cv_car7.startRun(duration)
            cv_car8.startRun(duration)
            cv_car9.startRun(duration)
            cv_car10.startRun(duration)
            countDownTimer = object : CountDownTimer(duration, 500) {

                override fun onTick(millisUntilFinished: Long) {
                    btn_start.text = (millisUntilFinished / 1000).toString() + "s"
                    list.clear()
                    list.add(CarEntity("①", cv_car1.getCurrentValue()))
                    list.add(CarEntity("②", cv_car2.getCurrentValue()))
                    list.add(CarEntity("③", cv_car3.getCurrentValue()))
                    list.add(CarEntity("④", cv_car4.getCurrentValue()))
                    list.add(CarEntity("⑤", cv_car5.getCurrentValue()))
                    list.add(CarEntity("⑥", cv_car6.getCurrentValue()))
                    list.add(CarEntity("⑦", cv_car7.getCurrentValue()))
                    list.add(CarEntity("⑧", cv_car8.getCurrentValue()))
                    list.add(CarEntity("⑨", cv_car9.getCurrentValue()))
                    list.add(CarEntity("⑩", cv_car10.getCurrentValue()))
                    list.sort()
                    val stringBuffer = StringBuffer()
                    for (i in list.indices) {
                        stringBuffer.append(list[i].carNumber)
                    }
                    tv_no.text = "当前排名：" + stringBuffer.toString()
                    if (millisUntilFinished > duration - 2000) {
                        mScrollPlayView.setSpeed(((duration - millisUntilFinished) / 500).toInt())//模拟2秒起步加速过程
                    } else {
                        mScrollPlayView.setSpeed(5)
                    }
                }

                override fun onFinish() {
                    btn_start.text = "比赛结束"
                    mScrollPlayView.stopPlay()
                }
            }.start()
        })
    }
}
