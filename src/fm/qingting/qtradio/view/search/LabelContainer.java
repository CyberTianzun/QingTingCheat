package fm.qingting.qtradio.view.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class LabelContainer extends ViewGroup
{
  public LabelContainer(Context paramContext)
  {
    super(paramContext);
  }

  public LabelContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramView.layout(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = getMeasuredWidth();
    int k = 0;
    int m = 0;
    int i3;
    for (int n = 0; k < i; n = i3)
    {
      View localView = getChildAt(k);
      int i1 = localView.getMeasuredWidth();
      int i2 = localView.getMeasuredHeight();
      i3 = n + i1;
      if (i3 > j)
      {
        m += i2;
        i3 = 0 + i1;
        n = 0;
      }
      setChildFrame(localView, n, m, i3, i2 + m);
      k++;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = 0;
    int m = 0;
    int n = 0;
    if (k < i)
    {
      View localView = getChildAt(k);
      int i2;
      if (localView != null)
      {
        localView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        int i1 = localView.getMeasuredWidth();
        i2 = localView.getMeasuredHeight();
        n += i1;
        if (n <= j)
          break label101;
        n = 0 + i1;
        m += i2;
      }
      while (true)
      {
        k++;
        break;
        label101: if (m == 0)
          m += i2;
      }
    }
    setMeasuredDimension(j, m);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.LabelContainer
 * JD-Core Version:    0.6.2
 */