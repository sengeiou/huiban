package com.bshuiban.baselibrary.view.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by xinheng on 2018/5/3.<br></br>
 * describe：环形进度条，起始点45度，向两边扩散（水平向右0度）
 */
class CircleProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mPaint: Paint = Paint()
    private val paint1: Paint
    private val paint2: Paint
    private val rect: RectF = RectF()
    private var progressValue: Int =80
    private val mColors = intArrayOf(Color.GREEN, Color.RED)

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.YELLOW
        paint1 = Paint(mPaint)
        paint2 = Paint(mPaint)
        paint1.strokeCap = Paint.Cap.ROUND
        paint2.strokeCap = Paint.Cap.ROUND
        paint1.color = mColors[0]
        paint2.color = mColors[1]
    }

    override fun onDraw(canvas: Canvas) {
        var mWidth = measuredWidth
        var mHeight = measuredHeight
        mWidth = Math.min(mWidth, mHeight)
        mHeight = mWidth
        val widthPaint = mWidth / 10f
        mPaint.strokeWidth = widthPaint
        paint1.strokeWidth = widthPaint
        paint2.strokeWidth = widthPaint

        val left = paddingLeft + widthPaint
        val top = paddingTop + widthPaint
        rect.set(left, top, mWidth - left, mHeight - top)
        canvas.drawArc(rect, 0f, 360f, false, mPaint)
        val section = progressValue.toFloat() / 100
        //        int count = mColors.length;
        //        int[] colors = new int[count];
        //        System.arraycopy(mColors, 0, colors, 0, count);
        val y0 = mWidth / 4
        val shader = LinearGradient((mWidth - y0).toFloat(), y0.toFloat(), y0.toFloat(), (mHeight - y0).toFloat(), mColors, null,
                Shader.TileMode.CLAMP)
        mPaint.shader = shader
        val section_ = section * 360
        val ANGLE =170f
        if (section_ > ANGLE) {
            val v = section_ - ANGLE
            val v1 = v / 2
            Log.e("TAG", "onDraw: $v1, $section_")
            var angle = ANGLE / 2
            canvas.drawArc(rect, 45- angle -v1, v1, false, paint1)
            canvas.drawArc(rect, 45+ angle, v1, false, paint2)
            canvas.drawArc(rect,45- angle, ANGLE, false, mPaint)
        } else {
            mPaint.strokeCap = Paint.Cap.ROUND
            val v = section_ / 2
            canvas.drawArc(rect, 45 - v, 45 + v, false, mPaint)
        }
    }
}
