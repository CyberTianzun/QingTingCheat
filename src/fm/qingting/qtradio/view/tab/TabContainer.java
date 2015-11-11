package fm.qingting.qtradio.view.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.utils.ScreenType;

class TabContainer extends ViewGroup
{
  private SlideView mSlideLayout;
  private LinearLayout mTabLayout;

  public TabContainer(Context paramContext)
  {
    super(paramContext);
    this.mTabLayout = new LinearLayout(paramContext);
    addView(this.mTabLayout);
    this.mSlideLayout = new SlideView(paramContext);
    addView(this.mSlideLayout);
  }

  LinearLayout getTabLayout()
  {
    return this.mTabLayout;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mTabLayout.layout(0, 0, this.mTabLayout.getMeasuredWidth(), ScreenType.getSubTabHeight());
    this.mSlideLayout.layout(0, ScreenType.getSubTabHeight() - ScreenType.getSubTabSlideHeight(), this.mSlideLayout.getMeasuredWidth(), ScreenType.getSubTabHeight());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mTabLayout.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(ScreenType.getSubTabHeight(), 1073741824));
    this.mSlideLayout.measure(View.MeasureSpec.makeMeasureSpec(this.mTabLayout.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ScreenType.getSubTabSlideHeight(), 1073741824));
    setMeasuredDimension(this.mTabLayout.getMeasuredWidth(), ScreenType.getSubTabHeight());
  }

  void shiftSlide(int paramInt1, int paramInt2)
  {
    this.mSlideLayout.shiftSlide(paramInt1, paramInt2);
  }

  private class SlideView extends ViewImpl
  {
    private ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 0, 0, ViewLayout.SLT | ViewLayout.CH);
    private final Paint mLinePaint = new Paint();
    int mOffset = 0;
    int mPosition = 0;
    private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 4, 720, 4, 0, 0, ViewLayout.SLT | ViewLayout.CH);

    public SlideView(Context arg2)
    {
      super();
      this.mLinePaint.setColor(SkinManager.getDividerColor());
    }

    private void drawLine(Canvas paramCanvas)
    {
      float f = this.standardLayout.height - this.lineLayout.height / 2.0F;
      paramCanvas.drawLine(0.0F, f, getWidth(), f, this.mLinePaint);
    }

    private void drawSlide(Canvas paramCanvas)
    {
      View localView1 = TabContainer.this.mTabLayout.getChildAt(this.mPosition);
      if (localView1 == null)
        return;
      int i = ScreenType.getTabPadding() / 2;
      View localView2 = TabContainer.this.mTabLayout.getChildAt(1 + this.mPosition);
      if (localView2 == null)
      {
        int n = paramCanvas.save();
        paramCanvas.clipRect(i + localView1.getLeft(), 0, localView1.getRight() - i, getHeight());
        paramCanvas.drawColor(SkinManager.getTextColorHighlight());
        paramCanvas.restoreToCount(n);
        return;
      }
      float f = this.mOffset / ScreenType.getWidth();
      int j = (int)((i + localView1.getLeft()) * (1.0F - f) + f * (i + localView2.getLeft()));
      int k = (int)((localView1.getRight() - i) * (1.0F - f) + f * (localView2.getRight() - i));
      int m = paramCanvas.save();
      paramCanvas.clipRect(j, 0, k, getHeight());
      paramCanvas.drawColor(SkinManager.getTextColorHighlight());
      paramCanvas.restoreToCount(m);
    }

    private void shiftSlide(int paramInt1, int paramInt2)
    {
      if ((this.mPosition != paramInt1) || (this.mOffset != paramInt2))
      {
        this.mPosition = paramInt1;
        this.mOffset = paramInt2;
        invalidate();
      }
    }

    protected void onDraw(Canvas paramCanvas)
    {
      paramCanvas.save();
      drawLine(paramCanvas);
      drawSlide(paramCanvas);
      paramCanvas.restore();
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
      this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
      this.lineLayout.scaleToBounds(this.standardLayout);
      this.mLinePaint.setStrokeWidth(this.lineLayout.height);
      super.onMeasure(paramInt1, paramInt2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.tab.TabContainer
 * JD-Core Version:    0.6.2
 */