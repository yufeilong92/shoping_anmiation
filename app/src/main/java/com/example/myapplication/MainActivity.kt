package com.example.myapplication

import android.animation.*
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.animation.addListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
        initListener()
        initViewModel()
    }

    private fun initEvent() {

    }

    private fun initListener() {
        tv_add.setOnClickListener {
            playAnim(it)
        }
    }

    private fun initViewModel() {

    }

    // 执行动画
    private fun playAnim(view: View) {

        //创建int数组，用来接收贝塞尔起始点坐标和终点坐标值
        var startPosition = IntArray(2);
        var endPosition = IntArray(2);
        view.getLocationInWindow(startPosition);
        iv_add.getLocationInWindow(endPosition);
        var startF = PointF();        //起始点 startF
        var endF = PointF();          //终点 endF
        var controlF = PointF();      //控制点 controlF
        startF.x = (startPosition[0]).toFloat();
        startF.y = (startPosition[1] - tv_add.bottom).toFloat();

        endF.x =
        (endPosition[0] + iv_add.getWidth() / 2 - view.getWidth() / 2).toFloat();             //微调处理，确保动画执行完毕“添加”图标中心点与购物车中心点重合
        endF.y =
        (endPosition[1] + iv_add.getHeight() / 2 - view.getHeight() / 2).toFloat();
        controlF.x = endF.x;
        controlF.y = startF.y;

        // 创建执行动画的“添加”图标
        var imageView = ImageView(this);
        rootview.addView(imageView);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.getLayoutParams().width = view.getMeasuredWidth();
        imageView.getLayoutParams().height = view.getMeasuredHeight();

//        var valueAnimator = ValueAnimator.ofObject(CartEvaluator(controlF), startF, endF);
        var valueAnimator = ValueAnimator.ofObject(CartEvaluator(controlF), startF,endF);
        valueAnimator.addUpdateListener {
            val pointF = it.getAnimatedValue() as PointF
            imageView.setX(pointF.x);
            imageView.setY(pointF.y);
        }
//        valueAnimator.addUpdateListener( ValueAnimator.AnimatorUpdateListener() {
//
//            fun onAnimationUpdate(ValueAnimator animation) {
//                PointF pointF = (PointF) animation.getAnimatedValue();
//                imageView.setX(pointF.x);
//                imageView.setY(pointF.y);
//            }
//        });
        valueAnimator.addListener(onEnd = {
            // 动画执行完毕，将执行动画的“添加”图标移除掉
            rootview.removeView(imageView);

            // 执行购物车缩放动画
            var animatorSet = AnimatorSet();
            var animatorX = ObjectAnimator.ofFloat(iv_add, "scaleX", 1f, 1.2f, 1f);
            var animatorY = ObjectAnimator.ofFloat(iv_add, "scaleY", 1f, 1.2f, 1f);
            animatorSet.play(animatorX).with(animatorY);
            animatorSet.setDuration(400);
            animatorSet.start();
        })


//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                // 动画执行完毕，将执行动画的“添加”图标移除掉
//                mRootView.removeView(imageView);
//
//                // 执行购物车缩放动画
//                AnimatorSet animatorSet = new AnimatorSet();
//                ObjectAnimator animatorX = ObjectAnimator.ofFloat(mCart, "scaleX", 1f, 1.2f, 1f);
//                ObjectAnimator animatorY = ObjectAnimator.ofFloat(mCart, "scaleY", 1f, 1.2f, 1f);
//                animatorSet.play(animatorX).with(animatorY);
//                animatorSet.setDuration(400);
//                animatorSet.start();
//            }
//        });

        valueAnimator.setDuration(800);
        valueAnimator.start();
    }
}
