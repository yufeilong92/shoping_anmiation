package com.example.myapplication

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication
 * @Email : yufeilong92@163.com
 * @Time :2019/9/27 9:03
 * @Purpose :插值器
 */
class CartEvaluator( mControlPoint: PointF? ) :TypeEvaluator<PointF> {
    private var pointCur: PointF?=null
    private var mControlPoint: PointF?=null
    init {
        this.mControlPoint=mControlPoint
        pointCur=PointF()
    }

    override fun evaluate(fraction:Float, startValue:PointF , endValue:PointF ): PointF? {
        // 将二阶贝塞尔曲线的计算公式直接代入即可
        pointCur!!.x = (1 - fraction) * (1 - fraction) * startValue.x
        + 2 * fraction * (1 - fraction) * mControlPoint!!.x + fraction * fraction * endValue.x;
        pointCur!!.y = (1 - fraction) * (1 - fraction) * startValue.y
        + 2 * fraction * (1 - fraction) * mControlPoint!!.y + fraction * fraction * endValue.y;
        return pointCur;
    }
}