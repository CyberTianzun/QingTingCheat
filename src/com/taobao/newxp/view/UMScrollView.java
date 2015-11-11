package com.taobao.newxp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class UMScrollView extends ScrollView
{
  private float a;
  private float b;
  private float c;
  private float d;
  private int e = 10;
  private boolean f = false;

  public UMScrollView(Context paramContext)
  {
    super(paramContext);
    a(paramContext);
  }

  public UMScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a(paramContext);
  }

  public UMScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    a(paramContext);
  }

  private void a(Context paramContext)
  {
    this.e = ViewConfiguration.get(paramContext).getScaledTouchSlop();
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool2;
    switch (0xFF & paramMotionEvent.getAction())
    {
    default:
      bool2 = super.onInterceptTouchEvent(paramMotionEvent);
      return bool2;
    case 0:
      this.a = paramMotionEvent.getY();
      this.b = paramMotionEvent.getX();
      this.f = false;
      return super.onInterceptTouchEvent(paramMotionEvent);
    case 2:
      this.f = false;
      this.c += Math.abs(this.a - paramMotionEvent.getY());
      this.d += Math.abs(this.b - paramMotionEvent.getX());
      this.a = paramMotionEvent.getY();
      this.b = paramMotionEvent.getX();
      if ((this.c > this.e) && (this.c > this.d));
      for (this.f = true; ; this.f = false)
      {
        boolean bool1 = this.f;
        bool2 = false;
        if (!bool1)
          break;
        return super.onInterceptTouchEvent(paramMotionEvent);
      }
    case 1:
    }
    this.a = 0.0F;
    this.b = 0.0F;
    this.c = 0.0F;
    this.d = 0.0F;
    this.f = false;
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.UMScrollView
 * JD-Core Version:    0.6.2
 */