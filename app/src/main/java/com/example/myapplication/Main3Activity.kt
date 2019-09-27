package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main2.*
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.ViewGroup
import android.view.View
import android.view.animation.*
import android.view.View.MeasureSpec
import kotlinx.android.synthetic.main.activity_main.*


/**
 * @Author : YFL  is Creating a porject in DELL
 * @Package com.example.myapplication
 * @Email : yufeilong92@163.com
 * @Time :2019/9/27 13:32
 * @Purpose :
 */
class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        initEvent()
        initListener()
        initViewModel()
    }

    private fun initEvent() {
        val data = mutableListOf<String>()
        for (item in 0..10) {
            data.add("$item")
        }
        val adapte = RlvAdapter(this, data)
        val m = GridLayoutManager(this, 1)
        rlv.layoutManager = m
        rlv.adapter = adapte
        var carLoc = IntArray(2)
        car_iv.getLocationInWindow(carLoc)
        carLoc[0] = carLoc[0] + car_iv.getWidth() / 2 - ViewUtils.dip2px(this, 10.0)

        adapte.setRecyclerListener(object : RlvAdapter.RecyclerItemListener {
            override fun itemClickListener(btn: Button) {
                val start = IntArray(2)
                btn.getLocationInWindow(start)
                val img=ImageView(this@Main3Activity)
                img.setImageBitmap(getAddDrawBitMap())
                setAnim(img,start,car_iv)
            }
        })
    }

    private fun initListener() {

    }

    private fun initViewModel() {

    }

    fun getAddDrawBitMap(): Bitmap {
        val text = ImageView(this)
        // 运动的控件，样式可以自定义
        text.setBackgroundResource(R.drawable.circle_blue)
        return convertViewToBitmap(text)
    }

    private fun createAnimLayout(): ViewGroup {
        val rootView = window.decorView as ViewGroup
        val animLayout = LinearLayout(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        animLayout.layoutParams = lp
        animLayout.id = Integer.MAX_VALUE
        animLayout.setBackgroundResource(android.R.color.transparent)
        rootView.addView(animLayout)
        return animLayout
    }

    private fun addViewToAnimLayout(vg: ViewGroup, view: View, location: IntArray): View {
        val x = location[0]
        val y = location[1]
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.leftMargin = x
        lp.topMargin = y
        view.setLayoutParams(lp)
        return view
    }

    private fun setAnim(v: View, start_location: IntArray, ent: View) {
        var anim_mask_layout: ViewGroup? = null
        anim_mask_layout = createAnimLayout()
        // 把动画小球添加到动画层
        anim_mask_layout.addView(v)
        val view = addViewToAnimLayout(anim_mask_layout, v, start_location)
        // 这是用来存储动画结束位置的X、Y坐标
        val end_location = IntArray(2)
        // rl_gouwuche是小球运动的终点 一般是购物车图标
        ent.getLocationInWindow(end_location)

        // 计算位移
//        val endX = 0 - start_location[0] + 40// 动画位移的X坐标
        val endX =0-end_location[0]+ent.width/2 // 动画位移的X坐标
        val endY = end_location[1] - start_location[1]// 动画位移的y坐标
        val translateAnimationX = TranslateAnimation(0f, endX.toFloat(), 0f, 0f)
        translateAnimationX.interpolator = LinearInterpolator()
        translateAnimationX.repeatCount = 0// 动画重复执行的次数
        translateAnimationX.fillAfter = true

        val translateAnimationY = TranslateAnimation(0f, 0f, 0f, endY.toFloat())
        translateAnimationY.interpolator = AccelerateInterpolator()
        translateAnimationY.repeatCount = 0// 动画重复执行的次数
        translateAnimationX.fillAfter = true

        val set = AnimationSet(false)
        set.fillAfter = false
        set.addAnimation(translateAnimationY)
        set.addAnimation(translateAnimationX)
        set.duration = 800// 动画的执行时间
        view.startAnimation(set)
        // 动画监听事件
        set.setAnimationListener(object : Animation.AnimationListener {
            // 动画的开始
            override fun onAnimationStart(animation: Animation) {
                v.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            override fun onAnimationEnd(animation: Animation) {
                v.visibility = View.GONE
            }
        })

    }

    fun convertViewToBitmap(view: View): Bitmap {
        view.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }
}
