package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

/**
 * Created by xinheng on 2018/3/8.<br/>
 * describe：换行
 */

public class EnterRadioGroup extends ViewGroup {
    /**子元素的宽度*/
    private int widthChild;
    /**横向子元素的个数(最多)*/
    private int countH=3;
    /**子元素之间横向间隔*/
    private int spaceH=(int)getResources().getDimension(R.dimen.dp_4);
    /**标题距子元素距离*/
    private int spaceTitle=30;
    /**子元素之间纵向间隔*/
    private int spaceV=20;
    private int sizeTitle=(int) getResources().getDimension(R.dimen.dp_12);
    private int sizeChild= (int) getResources().getDimension(R.dimen.dp_10);
    private int colorTitle=getResources().getColor(R.color.gray1);
    private int colorSelected = getResources().getColor(R.color.guide_start_btn);
    private int colorUnSelected = getResources().getColor(R.color.grg_text);
    private int colorSelectedBG=Color.parseColor("#1A28C7E9");//28C7E9
    private int colorUnSelectedBG=Color.WHITE;
    private int dp1;
    private String[] textArray;
    private String title;
    private final String TAG="TAG";
    /**
     * 子元素第几个默认选中
     */
    private int indexSelected;
    private SparseArray sparseArray;

    public EnterRadioGroup(Context context) {
        this(context,null);
    }

    public EnterRadioGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EnterRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp1= (int) TypedValue.applyDimension(1,1,getResources().getDisplayMetrics());
    }
    private boolean hasAddView;
    private boolean widthSure;

    public void setWidthSure(boolean widthSure) {
        this.widthSure = widthSure;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int maxWidthChild=width-getPaddingLeft()-getPaddingRight();
        if(!hasAddView) {
            widthChild = (int) ((width - getPaddingLeft() - getPaddingRight() - (countH - 1f) * spaceH) / countH);
            addView(getTextView(title, sizeTitle, colorTitle));
            for (int i = 0; i < textArray.length; i++) {
                TextView textViewChild = getTextViewChild(textArray[i], sizeChild, colorUnSelected, widthChild);
                textViewChild.setOnClickListener(onClickListener);
                textViewChild.setTag(i);
                addView(textViewChild);
                if(i==indexSelected){
                    setSelect(textViewChild);
                }
            }
            hasAddView=true;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidthChild, MeasureSpec.AT_MOST);
        measureChildren(makeMeasureSpec, heightMeasureSpec);

        int height=getPaddingBottom()+getPaddingTop();
        int measuredHeight;
        int widthTag = 0;//一行，已占用的宽度
        int widthTemporary;//临时
        int heightLast = 0;//上一个元素的高度
        sparseArray=new SparseArray<>(getChildCount()-1);
        for (int i=0;i<getChildCount();i++){
            View childAt = getChildAt(i);
            //measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            int measuredChildWidth = childAt.getMeasuredWidth();
            measuredHeight = childAt.getMeasuredHeight();
            widthTemporary = widthTag + measuredChildWidth;
            if(i==0){//标题
                height+=measuredHeight+spaceTitle;
            }else{//子元素列表
                if(widthTemporary==maxWidthChild){//一行满
                    height+=measuredHeight+spaceV;
                    widthTag=0;
                    sparseArray.append(i,i);
                }else if(widthTemporary<maxWidthChild){//未满
                    widthTag=widthTemporary+spaceH;
                    heightLast=measuredHeight;
                    if(i==getChildCount()-1){
                        height+=measuredHeight+spaceV;
                    }
                }else{//溢出，转下一行
                    height+=heightLast+spaceV;
                    sparseArray.append(i-1,i-1);
                    widthTag=0;
                    i--;
                }
            }
        }
        setMeasuredDimension(width,height-spaceV);
    }
    private int getMaxHeight(int []array){
        int max=0;
        for (int i=0;i<array.length;i++){
            if(max<array[i]){
                max=array[i];
            }
        }
        return max;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int left=getPaddingLeft();
        int startY=getPaddingTop();
        int startX=getPaddingLeft();
        int right;
        int bottom;
        for (int i=0;i<getChildCount();i++){
            View childAt = getChildAt(i);
            if(i==0) {
                right=childAt.getMeasuredWidth()+startX;
                bottom=childAt.getMeasuredHeight()+startY;
                childAt.layout(startX,startY,right,bottom);
                startY=bottom+spaceTitle;
                continue;
            }
            int key = sparseArray.indexOfKey(i);
            if(key>=0){//行，最后一个
                right=childAt.getMeasuredWidth()+startX;
                bottom=childAt.getMeasuredHeight()+startY;
                childAt.layout(startX,startY,right,bottom);
                startX=left;
                startY=bottom+spaceV;
            }else{
                right=childAt.getMeasuredWidth()+startX;
                bottom=childAt.getMeasuredHeight()+startY;
                childAt.layout(startX,startY,right,bottom);
                startX=right+spaceH;
            }
        }
    }

    public void setTextArray(String title,String[] textArray,int indexSelected) {
        this.textArray = textArray;
        this.title=title;
        this.indexSelected=indexSelected;
    }
    private TextView getTextViewChild(String s,int size, int color,int width) {
        TextView textView = getTextView(s, size, color);
        LayoutParams params;
        int space1=0;
        int space= (int) getResources().getDimension(R.dimen.dp_6);
        if(widthSure){
            params = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
            space1=0;
        }else{
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            //space1=space;
            textView.setMinWidth(width);
        }
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setPadding(space1,space,space1,space);
        GradientDrawable drawable = getGradientDrawable(colorUnSelectedBG,color);
        textView.setBackground(drawable);
        return textView;
    }

    private GradientDrawable getGradientDrawable(int colorBG,int colorStroke) {
        GradientDrawable drawable=new GradientDrawable();
        drawable.setColor(colorBG);
        drawable.setStroke(dp1,colorStroke);
        return drawable;
    }

    private TextView getTextView(String s,int size, int color){
        TextView textView = new TextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        textView.setTextColor(color);
        textView.setText(s==null?"":s);
        //textView.setTypeface(new Typeface());
        return textView;
    }
    private OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v instanceof TextView){
                updateTextViewUI((TextView) v);
                if(l!=null){
                    l.itemClick((int) v.getTag());
                }
            }
        }
    };
    private void updateTextViewUI(TextView tv){
        for (int i=1;i<getChildCount();i++){
            View childAt = getChildAt(i);
            if(childAt instanceof TextView&&childAt!=tv){
                childAt.setBackground(getGradientDrawable(colorUnSelectedBG,colorUnSelected));
                ((TextView) childAt).setTextColor(colorUnSelected);
            }
        }
        setSelect(tv);
    }

    private void setSelect(TextView tv) {
        tv.setBackground(getGradientDrawable(colorSelectedBG,colorSelected));
        tv.setTextColor(colorSelected);
    }

    private OnItemSelectClickListener l;
    public void setOnItemSelectClickListener(OnItemSelectClickListener l){
        this.l=l;
    }
    public interface OnItemSelectClickListener {
        void itemClick(int position);
    }
}
