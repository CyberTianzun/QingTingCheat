package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

public class b extends View
{
  private Rect a;
  private Paint b;

  public b(Context paramContext)
  {
    super(paramContext);
    b();
  }

  private void b()
  {
    this.b = new Paint();
  }

  public Rect a()
  {
    if (this.a == null)
    {
      this.a = new Rect();
      int i = getMeasuredWidth();
      int j = getMeasuredHeight();
      int k = Math.min(Math.min(-80 + (j - 60), i), 640);
      int m = (i - k) / 2;
      int n = (j - k) / 2;
      int i1 = m + k;
      int i2 = k + n;
      this.a.set(m, n, i1, i2);
    }
    return this.a;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Rect localRect = a();
    int i = getMeasuredWidth();
    int j = getMeasuredHeight();
    this.b.setStyle(Paint.Style.FILL);
    this.b.setColor(Color.argb(100, 0, 0, 0));
    paramCanvas.drawRect(0.0F, 0.0F, i, localRect.top, this.b);
    paramCanvas.drawRect(0.0F, localRect.bottom, i, j, this.b);
    paramCanvas.drawRect(0.0F, localRect.top, localRect.left, localRect.bottom, this.b);
    paramCanvas.drawRect(localRect.right, localRect.top, i, localRect.bottom, this.b);
    paramCanvas.drawColor(Color.argb(100, 0, 0, 0));
    this.b.setStyle(Paint.Style.STROKE);
    this.b.setColor(-1);
    paramCanvas.drawRect(localRect.left, localRect.top, -1 + localRect.right, localRect.bottom, this.b);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.avatar.b
 * JD-Core Version:    0.6.2
 */