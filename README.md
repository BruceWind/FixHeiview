## fixheiview这个控件就是高度依据宽高比来自动变化的控件

	代码已经传到github：https://github.com/weizongwei5/fixheiview

做这个控件的目的 就是之前有轮播图，然后轮播图的宽度肯定是要fill_parent，高度肯定以宽度为基准和一个约定的宽高比来计算得出的。
因为如果服务器图片宽高比有问题的话，我这边界面就会很难看，所以我不能以服务器图片的宽高比来重绘。

我的需求：
> - 宽度是 fill_parent.
> - 高度是宽度 × 约定的宽高比.

这样子才是我的需求。
虽然可以再viewpager添加数据源的时候，通过set高度的方法来控制，只是我觉得这样子手动的操作，写多次会导致代码的赘余。
仔细思考下我发现还有其他的优点：

> - set高度的方式，从代码设计模式的角度来说不是很完美。如果其他地方用到类似的控件这样子就会导致代码的赘余很厉害。
> - 如果在listview，gridview的item的布局中也每次都set高度的话，一方面高度难以计算。
> - 另外一方面这样子的话，会导致计算高度和布局又重新计算一次，而不单单是这个控件重新计算一次，可能父控件也重新计算一次，导致重绘次数过多，不必要的性能损耗。


**综上：做到自定义控件里面，合适的重写父类的方法才是最好的选择。**

好了，废话说完了。上代码：


### 1.自定义属性：


``` xml
  <declare-styleable name="FixHeiImageView">
        <attr name="whratio" format="float" />
    </declare-styleable>  <declare-styleable name="FixHeiImageView">
        <attr name="whratio" format="float" />
    </declare-styleable>

```

### 2.控件

``` java


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

```

> - 我这里只是重写了ImageView，如果还有别的需求还可以再改下这个类，为你所用。
