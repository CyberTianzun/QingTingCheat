package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class RefreshHeader extends ViewGroup
{
  private RotateAnimation arrowAnimation;
  private ImageView arrowImage;
  private ViewLayout arrowLayout = ViewLayout.createViewLayoutWithBoundsLT(36, 60, 480, 80, 72, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private ViewLayout contentLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 80, 480, 80, 0, 0, ViewLayout.CW);
  private AnimationDrawable loadingAnimation;
  private ImageView loadingImage;
  private ViewLayout loadingLayout = ViewLayout.createViewLayoutWithBoundsLT(40, 40, 480, 80, 70, 20, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private boolean onRefreshing = false;
  private float position = -1.0F;
  private boolean reverseArrow = false;
  private RotateAnimation reverseArrowAnimation;
  private boolean sizeChanged = false;
  private ViewLayout tipLayout = ViewLayout.createViewLayoutWithBoundsLT(280, 30, 480, 80, 100, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TextView tipText;
  private int topExtendPadding = 100;
  private ViewLayout updateLayout = ViewLayout.createViewLayoutWithBoundsLT(280, 30, 480, 80, 100, 40, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TextView updateText;
  private long updateTime = 0L;

  public RefreshHeader(Context paramContext)
  {
    super(paramContext);
    this.arrowImage = new ImageView(paramContext);
    this.arrowImage.setImageDrawable(buildLargeArrowShape());
    addView(this.arrowImage);
    this.loadingImage = new ImageView(paramContext);
    this.loadingAnimation = ProgressIndicatorView.buildAnimationDrawable(40, 150);
    this.loadingImage.setBackgroundDrawable(this.loadingAnimation);
    this.loadingImage.setVisibility(4);
    addView(this.loadingImage);
    this.tipText = new TextView(paramContext);
    this.tipText.setTextColor(-1);
    this.tipText.setGravity(17);
    addView(this.tipText);
    this.updateText = new TextView(paramContext);
    this.updateText.setTextColor(-1);
    this.updateText.setGravity(17);
    addView(this.updateText);
    this.arrowAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
    this.arrowAnimation.setInterpolator(new LinearInterpolator());
    this.arrowAnimation.setDuration(250L);
    this.arrowAnimation.setFillAfter(true);
    this.reverseArrowAnimation = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
    this.reverseArrowAnimation.setInterpolator(new LinearInterpolator());
    this.reverseArrowAnimation.setDuration(200L);
    this.reverseArrowAnimation.setFillAfter(true);
  }

  protected Drawable buildLargeArrowShape()
  {
    float f1 = 35 / 2.0F;
    float f2 = 18 / 2.0F;
    int i = 80 - 10;
    Path localPath = new Path();
    localPath.moveTo(f1, i);
    localPath.lineTo(0.0F, 52);
    localPath.lineTo(f2, 52);
    localPath.lineTo(f2, 34);
    localPath.lineTo(36 - f2, 34);
    localPath.lineTo(36 - f2, 52);
    localPath.lineTo(36, 52);
    localPath.lineTo(f1, i);
    int j = 34 - 4;
    for (int k = 0; ; k++)
    {
      if (k >= 2)
      {
        PathShape localPathShape = new PathShape(localPath, 36, 80);
        ShapeDrawable localShapeDrawable = new ShapeDrawable(localPathShape);
        localShapeDrawable.setIntrinsicWidth(36);
        localShapeDrawable.setIntrinsicHeight(80);
        localShapeDrawable.setBounds(new Rect(0, 0, 36, 80));
        localShapeDrawable.setPadding(new Rect(0, 0, 0, 0));
        Paint localPaint = localShapeDrawable.getPaint();
        localPaint.setStyle(Paint.Style.FILL);
        localPaint.setAntiAlias(true);
        localPaint.setShader(new LinearGradient(0.0F, 0.0F, 0.0F, 80, new int[] { 16777215, -1 }, new float[] { 0.0F, 1.0F }, Shader.TileMode.CLAMP));
        return localShapeDrawable;
      }
      localPath.addRect(f2, j - 4, 36 - f2, j, Path.Direction.CCW);
      j -= 8;
    }
  }

  protected Drawable buildMiddleArrowShape()
  {
    float f1 = 35 / 2.0F;
    float f2 = 18 / 2.0F;
    Path localPath1 = new Path();
    localPath1.moveTo(f1, 80);
    localPath1.lineTo(0.0F, 56);
    localPath1.lineTo(f2, 56);
    localPath1.lineTo(f2, 51);
    localPath1.lineTo(36 - f2, 51);
    localPath1.lineTo(36 - f2, 56);
    localPath1.lineTo(36, 56);
    localPath1.lineTo(f1, 80);
    PathShape localPathShape1 = new PathShape(localPath1, 36, 80);
    ShapeDrawable localShapeDrawable1 = new ShapeDrawable(localPathShape1);
    localShapeDrawable1.setIntrinsicWidth(36);
    localShapeDrawable1.setIntrinsicHeight(80);
    localShapeDrawable1.setBounds(new Rect(0, 0, 36, 80));
    localShapeDrawable1.setPadding(new Rect(0, 0, 0, 0));
    Paint localPaint1 = localShapeDrawable1.getPaint();
    localPaint1.setColor(-1);
    localPaint1.setStyle(Paint.Style.FILL);
    localPaint1.setAntiAlias(true);
    Drawable[] arrayOfDrawable = new Drawable[4];
    arrayOfDrawable[0] = localShapeDrawable1;
    int[] arrayOfInt = { -570425345, -1711276033, 1442840575 };
    int i = 51 - 6;
    for (int j = 0; ; j++)
    {
      if (j >= 3)
        return new LayerDrawable(arrayOfDrawable);
      Path localPath2 = new Path();
      localPath2.addRect(f2, i - 10, 36 - f2, i, Path.Direction.CCW);
      PathShape localPathShape2 = new PathShape(localPath2, 36, 80);
      ShapeDrawable localShapeDrawable2 = new ShapeDrawable(localPathShape2);
      localShapeDrawable2.setIntrinsicWidth(36);
      localShapeDrawable2.setIntrinsicHeight(80);
      localShapeDrawable2.setBounds(new Rect(0, 0, 36, 80));
      localShapeDrawable2.setPadding(new Rect(0, 0, 0, 0));
      Paint localPaint2 = localShapeDrawable2.getPaint();
      localPaint2.setColor(arrayOfInt[j]);
      localPaint2.setStyle(Paint.Style.FILL);
      localPaint2.setAntiAlias(true);
      arrayOfDrawable[(j + 1)] = localShapeDrawable2;
      i -= 16;
    }
  }

  public int getContentHeight()
  {
    return this.contentLayout.height;
  }

  public float getPosition()
  {
    return this.position;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingTop();
    this.arrowImage.layout(this.arrowLayout.getLeft(), i + this.arrowLayout.getTop(), this.arrowLayout.getRight(), i + this.arrowLayout.getBottom());
    this.loadingImage.layout(this.loadingLayout.getLeft(), i + this.loadingLayout.getTop(), this.loadingLayout.getRight(), i + this.loadingLayout.getBottom());
    this.tipText.layout(this.tipLayout.getLeft(), i + this.tipLayout.getTop(), this.tipLayout.getRight(), i + this.tipLayout.getBottom());
    this.updateText.layout(this.updateLayout.getLeft(), i + this.updateLayout.getTop(), this.updateLayout.getRight(), i + this.updateLayout.getBottom());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = this.contentLayout.height;
    this.contentLayout.scaleToBounds(i, j);
    this.arrowLayout.scaleToBounds(this.contentLayout);
    this.loadingLayout.scaleToBounds(this.contentLayout);
    this.tipLayout.scaleToBounds(this.contentLayout);
    this.updateLayout.scaleToBounds(this.contentLayout);
    if (k != this.contentLayout.height)
      this.sizeChanged = true;
    setPosition(this.position);
    this.arrowImage.measure(this.arrowLayout.getWidthMeasureSpec(), this.arrowLayout.getHeightMeasureSpec());
    this.tipText.setTextSize(0, 0.7F * this.tipLayout.height);
    this.tipText.measure(this.tipLayout.getWidthMeasureSpec(), this.tipLayout.getHeightMeasureSpec());
    this.updateText.setTextSize(0, 0.7F * this.updateLayout.height);
    this.updateText.measure(this.updateLayout.getWidthMeasureSpec(), this.updateLayout.getHeightMeasureSpec());
    setMeasuredDimension(this.contentLayout.width, this.contentLayout.height + getPaddingTop() + getPaddingBottom());
  }

  public void setOnRefreshing(boolean paramBoolean)
  {
    this.onRefreshing = paramBoolean;
    this.tipText.setText("正在刷新，请稍后");
    setUpdateTime();
    if (this.onRefreshing)
      this.arrowImage.clearAnimation();
    ImageView localImageView1 = this.arrowImage;
    int i;
    ImageView localImageView2;
    int j;
    if (this.onRefreshing)
    {
      i = 4;
      localImageView1.setVisibility(i);
      localImageView2 = this.loadingImage;
      boolean bool = this.onRefreshing;
      j = 0;
      if (!bool)
        break label85;
    }
    while (true)
    {
      localImageView2.setVisibility(j);
      return;
      i = 0;
      break;
      label85: j = 4;
    }
  }

  public void setPosition(float paramFloat)
  {
    setPosition(paramFloat, false);
  }

  public void setPosition(float paramFloat, boolean paramBoolean)
  {
    int i;
    if ((!this.sizeChanged) && (paramFloat == this.position) && (!paramBoolean))
    {
      i = 0;
      this.position = paramFloat;
      setPadding(0, (int)(this.position * this.contentLayout.height + (1.0F + this.position) * this.topExtendPadding), 0, 0);
      if (i != 0)
        break label71;
    }
    label71: 
    do
    {
      return;
      i = 1;
      break;
      requestLayout();
      this.sizeChanged = false;
      if (this.onRefreshing)
      {
        this.tipText.setText("正在刷新，请稍后");
        return;
      }
      if ((this.position >= 0.0F) && (!this.reverseArrow))
      {
        this.arrowImage.clearAnimation();
        this.arrowImage.startAnimation(this.arrowAnimation);
        this.reverseArrow = true;
        this.tipText.setText("松开即可刷新");
        return;
      }
    }
    while ((this.position >= 0.0F) || (!this.reverseArrow));
    this.arrowImage.clearAnimation();
    this.arrowImage.startAnimation(this.reverseArrowAnimation);
    this.reverseArrow = false;
    this.tipText.setText("下拉可以刷新");
  }

  public void setTopExtendPadding(int paramInt)
  {
    this.topExtendPadding = paramInt;
    setPosition(this.position, true);
  }

  public void setUpdateTime()
  {
    setUpdateTime(this.updateTime);
  }

  public void setUpdateTime(long paramLong)
  {
    this.updateTime = paramLong;
    long l1 = Calendar.getInstance().getTimeInMillis() - this.updateTime;
    String str;
    if (this.updateTime == 0L)
      str = "";
    while (true)
    {
      this.updateText.setText(str);
      return;
      if (l1 < 0L)
      {
        SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date localDate1 = new Date(paramLong);
        str = localSimpleDateFormat1.format(localDate1);
      }
      else if (l1 < 60000L)
      {
        int i1 = (int)(l1 / 1000L);
        Object[] arrayOfObject6 = new Object[1];
        arrayOfObject6[0] = Integer.valueOf(i1);
        str = String.format("最后更新： %d 秒前", arrayOfObject6);
      }
      else
      {
        long l2 = 60000L * 60L;
        if (l1 < l2)
        {
          int n = (int)(l1 / 60000L);
          Object[] arrayOfObject5 = new Object[1];
          arrayOfObject5[0] = Integer.valueOf(n);
          str = String.format("最后更新： %d 分钟前", arrayOfObject5);
        }
        else
        {
          long l3 = l2 * 24L;
          if (l1 < l3)
          {
            int m = (int)(l1 / 3600000L);
            Object[] arrayOfObject4 = new Object[1];
            arrayOfObject4[0] = Integer.valueOf(m);
            str = String.format("最后更新： %d 小时前", arrayOfObject4);
          }
          else
          {
            long l4 = l3 * 7L;
            if (l1 < l4)
            {
              int k = (int)(l1 / 86400000L);
              Object[] arrayOfObject3 = new Object[1];
              arrayOfObject3[0] = Integer.valueOf(k);
              str = String.format("最后更新： %d 天前", arrayOfObject3);
            }
            else
            {
              long l5 = l4 * 4L;
              if (l1 < l5)
              {
                int j = (int)(l1 / 604800000L);
                Object[] arrayOfObject2 = new Object[1];
                arrayOfObject2[0] = Integer.valueOf(j);
                str = String.format("最后更新： %d 周前", arrayOfObject2);
              }
              else if (l1 < l5 * 13L)
              {
                int i = (int)(l1 / -1875767296L);
                Object[] arrayOfObject1 = new Object[1];
                arrayOfObject1[0] = Integer.valueOf(i);
                str = String.format("最后更新： %d 个月之前", arrayOfObject1);
              }
              else
              {
                SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date localDate2 = new Date(paramLong);
                str = localSimpleDateFormat2.format(localDate2);
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.RefreshHeader
 * JD-Core Version:    0.6.2
 */