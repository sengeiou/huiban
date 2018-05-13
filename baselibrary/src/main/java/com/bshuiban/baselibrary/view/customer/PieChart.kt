package com.bshuiban.baselibrary.view.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.bshuiban.baselibrary.utils.TextUtils
import java.util.*

/**
 * Created by xinheng on 2018/5/3.<br/>
 * describe：饼状图
 */
class PieChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mPaint:Paint = Paint()
    private var mColors: IntArray= intArrayOf(Color.GREEN,Color.RED,Color.BLUE,Color.YELLOW)
    private var textSizeBottom:Float=20f
    private var textColorBottom:Int=Color.GRAY
    /**底部图案边长*/
    private var sideBottom:Int=40
    /**圆半径*/
    private var radius:Int=100
    /**圆距离底部说明距离*/
    private var paddingExplainBottom:Int=60
    /**底部说明中图案到文字的距离*/
    private var paddingImgText:Int=20

    init {
//        context.obtainStyledAttributes(R.styleA,,attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mPaint.textSize= textSizeBottom
        var textHeight=TextUtils.getTextHeightR("答对",mPaint);
        var height=paddingTop+paddingBottom+radius+paddingExplainBottom+sideBottom+paddingImgText+textHeight
        setMeasuredDimension(widthMeasureSpec,height)
    }
    override fun onDraw(canvas: Canvas) {
        //var radiusX = (measuredWidth-paddingLeft-paddingRight) / 2f + paddingLeft
        //var radiusY = radius+paddingTop
        //-45度为起始点
        var rectF = RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (paddingLeft+radius).toFloat(), (paddingTop+radius).toFloat())
        var random=Random()
        var array= IntArray(mColors.size);
        var max=0
        for (i in mColors.indices) {
            array[i] = random.nextInt(100)
            max+=array[i]
        }
        var statAngle=0f
        for (i in mColors.indices) {
            mPaint.color=mColors[i]
            var angle=array[i]*1f/max*360
            canvas.drawArc(rectF, statAngle, angle,false,mPaint)
            statAngle += angle
        }
    }
}