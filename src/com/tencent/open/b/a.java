package com.tencent.open.b;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class a extends RelativeLayout
{
  private static final String a = a.class.getName();
  private Rect b = null;
  private boolean c = false;
  private a d = null;

  public a(Context paramContext)
  {
    super(paramContext);
    if (this.b == null)
      this.b = new Rect();
  }

  public void a(a parama)
  {
    this.d = parama;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt2);
    Activity localActivity = (Activity)getContext();
    localActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.b);
    int j = this.b.top;
    int k = localActivity.getWindowManager().getDefaultDisplay().getHeight() - j - i;
    if ((this.d != null) && (i != 0))
    {
      if (k <= 100)
        break label112;
      this.d.onKeyboardShown(Math.abs(this.b.height()) - getPaddingBottom() - getPaddingTop());
    }
    while (true)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
      label112: this.d.onKeyboardHidden();
    }
  }

  public static abstract interface a
  {
    public abstract void onKeyboardHidden();

    public abstract void onKeyboardShown(int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.b.a
 * JD-Core Version:    0.6.2
 */