package com.example.fixheightview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.example.fixheightview.R;

public class FixHeiImageView extends ImageView
{
	private double wh_ratio=0.0;

	public FixHeiImageView(Context context)
	{
		super(context);
		wh_ratio = 2.0;
	}
	
	public FixHeiImageView(Context context, double mWh_ratio)
	{
		super(context);

		this.wh_ratio = mWh_ratio;
	}

	public FixHeiImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixHeiImageView);
		wh_ratio = typedArray.getFloat(R.styleable.FixHeiImageView_whratio, (float) 1.0);
		typedArray.recycle();
	}
	
	public FixHeiImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixHeiImageView);
		wh_ratio = typedArray.getFloat(R.styleable.FixHeiImageView_whratio, (float) 1.0);
		typedArray.recycle();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// 父容器传过来的宽度方向上的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 父容器传过来的高度方向上的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 父容器传过来的宽度的值
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
                - getPaddingRight();
        // 父容器传过来的高度的值
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom()
                - getPaddingTop();

            height = (int) (width / wh_ratio + 0.5f);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
