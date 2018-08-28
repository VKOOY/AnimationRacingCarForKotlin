package com.google.animationracingcarforkotlin.entity

/**************************
作者：FYX
日期：2018/8/27 0027
 **************************/
class CarEntity(val carNumber: String, val carValue: Float) : Comparable<CarEntity> {

    override fun compareTo(other: CarEntity): Int {
        //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
        return if (this.carValue >= other.carValue) 1
        else -1
    }
}