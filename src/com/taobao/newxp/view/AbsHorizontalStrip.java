package com.taobao.newxp.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class AbsHorizontalStrip extends ViewGroup
{
  private static String g = AbsHorizontalStrip.class.getSimpleName();
  protected Context a;
  protected final float b;
  protected int c = 10;
  protected float d = 0.0F;
  protected float e;
  HashMap<Integer, Float[]> f = new HashMap();
  private boolean h;
  private float i;
  private a j;
  private final int k;
  private VelocityTracker l;
  private float m;
  private b n;

  public AbsHorizontalStrip(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    this.a = paramContext;
    this.k = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    (1158.2634F * (160.0F * paramContext.getResources().getDisplayMetrics().density) * ViewConfiguration.getScrollFriction());
    this.b = 5.0F;
    setWillNotDraw(false);
  }

  private void a(float paramFloat1, float paramFloat2)
  {
    this.i = paramFloat1;
    this.m = 0.0F;
  }

  private void a(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
  }

  private void b(float paramFloat1, float paramFloat2)
  {
    float f1 = this.i - paramFloat1;
    this.i = paramFloat1;
    Log.i(g, "onTouchEventMove action= touchX=" + paramFloat1 + "  getScrollPosition=" + a() + " offsetX=" + f1);
    int i1 = getWidth();
    if (this.e > i1)
      e(a() - f1);
  }

  private void c()
  {
    if (this.l == null)
      this.l = VelocityTracker.obtain();
    this.l.clear();
  }

  private void c(float paramFloat1, float paramFloat2)
  {
    float f1 = Math.abs(a());
    Iterator localIterator = this.f.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i4 = ((Integer)localIterator.next()).intValue();
      Float[] arrayOfFloat = (Float[])this.f.get(Integer.valueOf(i4));
      if ((arrayOfFloat[0].floatValue() <= f1) && (arrayOfFloat[1].floatValue() >= f1))
      {
        float f2 = arrayOfFloat[1].floatValue() - arrayOfFloat[0].floatValue();
        if (f1 - arrayOfFloat[0].floatValue() <= f2 / 2.0F)
          onChildAcquiredFocus(i4);
        else if (this.f.containsKey(Integer.valueOf(i4 + 1)))
          onChildAcquiredFocus(i4 + 1);
      }
    }
    List localList = getVisibleChild();
    if (this.n != null)
      if (localList.size() <= 0)
        break label253;
    label253: for (int i1 = ((Integer)localList.get(0)).intValue(); ; i1 = 0)
    {
      int i2 = localList.size();
      int i3 = 0;
      if (i2 > 0)
        i3 = ((Integer)localList.get(-1 + localList.size())).intValue();
      this.n.onChildVisibleChanged(i1, i3);
      return;
    }
  }

  private void d()
  {
    if (this.l == null)
      this.l = VelocityTracker.obtain();
  }

  private void e()
  {
    if (this.l != null)
    {
      this.l.recycle();
      this.l = null;
    }
  }

  private void e(float paramFloat)
  {
    int i1 = -(int)d(paramFloat);
    Log.i(g, "updateScrollPosition toX=" + i1 + "  total=" + this.e);
    scrollTo(i1, 0);
    invalidate();
  }

  private void f()
  {
    ViewParent localViewParent = getParent();
    if (localViewParent != null)
      localViewParent.requestDisallowInterceptTouchEvent(true);
  }

  protected float a()
  {
    return -getScrollX();
  }

  protected float a(float paramFloat)
  {
    if (this.e == 0.0F);
    do
    {
      return paramFloat;
      if (paramFloat < 0.0F)
        paramFloat += this.e;
    }
    while (paramFloat < this.e);
    return paramFloat - this.e;
  }

  abstract float a(int paramInt);

  protected a a(float paramFloat, long paramLong)
  {
    this.d = a();
    return new a(paramFloat, paramLong);
  }

  abstract float b(float paramFloat);

  abstract float c(float paramFloat);

  protected float d(float paramFloat)
  {
    if (paramFloat > 0.0F)
      paramFloat = 0.0F;
    int i1 = (int)(this.e - getWidth());
    Log.i(g, "exSize " + i1 + "   " + paramFloat);
    if (-paramFloat > i1)
      paramFloat = -i1;
    return paramFloat;
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
  }

  public int getChildByX(float paramFloat)
    throws IllegalArgumentException
  {
    Iterator localIterator = this.f.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i1 = ((Integer)localIterator.next()).intValue();
      Float[] arrayOfFloat = (Float[])this.f.get(Integer.valueOf(i1));
      if ((arrayOfFloat[0].floatValue() <= paramFloat) && (arrayOfFloat[1].floatValue() >= paramFloat))
        return i1;
    }
    throw new IllegalArgumentException();
  }

  public int getLayoutMargin()
  {
    return this.c;
  }

  public List<Integer> getVisibleChild()
  {
    float f1 = a();
    ArrayList localArrayList = new ArrayList();
    float f2 = Math.abs(f1);
    float f3 = f2 + getWidth();
    Iterator localIterator = this.f.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i1 = ((Integer)localIterator.next()).intValue();
      Float[] arrayOfFloat = (Float[])this.f.get(Integer.valueOf(i1));
      if (((arrayOfFloat[0].floatValue() >= f2) && (arrayOfFloat[0].floatValue() < f3)) || ((arrayOfFloat[1].floatValue() >= f2) && (arrayOfFloat[1].floatValue() < f3)))
        localArrayList.add(Integer.valueOf(i1));
    }
    return localArrayList;
  }

  public void onChildAcquiredFocus(int paramInt)
  {
    runScrollAnimation(-(a(paramInt) + a()) / 0.1F, 0.1F);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getY();
    float f2 = paramMotionEvent.getX();
    int i1 = 0xFF & paramMotionEvent.getAction();
    Log.i(g, "onInterceptTouchEvent action=" + i1 + " touchX=" + f2 + " touchY" + f1);
    boolean bool2;
    switch (i1)
    {
    default:
      bool2 = super.onInterceptTouchEvent(paramMotionEvent);
    case 0:
    case 2:
    case 1:
    }
    boolean bool1;
    do
    {
      boolean bool3;
      do
      {
        return bool2;
        this.i = f2;
        this.m = 0.0F;
        a(f2, f1);
        return false;
        this.h = false;
        this.m += Math.abs(this.i - f2);
        this.i = f2;
        if (this.m > this.k)
          this.h = true;
        bool3 = this.h;
        bool2 = false;
      }
      while (!bool3);
      onTouchEvent(paramMotionEvent);
      return true;
      bool1 = this.h;
      bool2 = false;
    }
    while (!bool1);
    this.h = false;
    onTouchEvent(paramMotionEvent);
    return true;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i1 = paramMotionEvent.getAction();
    float f1 = paramMotionEvent.getY();
    float f2 = paramMotionEvent.getX();
    Log.i(g, "onTouch action=" + i1 + "   " + (i1 & 0xFF) + " touchX=" + f2 + " touchY=" + f1);
    d();
    this.l.addMovement(paramMotionEvent);
    switch (i1 & 0xFF)
    {
    default:
    case 2:
    case 0:
    case 1:
    }
    while (true)
    {
      return true;
      b(f2, f1);
      continue;
      a(f2, f1);
      continue;
      c(f2, f1);
    }
  }

  public void runScrollAnimation(float paramFloat1, float paramFloat2)
  {
    this.j = a(paramFloat1, ()Math.abs(1000.0F * paramFloat2));
    this.j.a();
  }

  public void setChildVisibleChanged(b paramb)
  {
    this.n = paramb;
  }

  public void setLayoutMargin(int paramInt)
  {
    this.c = paramInt;
  }

  class a
    implements Runnable
  {
    private float b;
    private long c = System.nanoTime();
    private float d;

    public a(float paramLong, long arg3)
    {
      Object localObject;
      this.b = ((float)localObject / 1000.0F);
      this.d = paramLong;
    }

    private void b()
    {
      AbsHorizontalStrip.this.post(this);
    }

    public void a()
    {
      b();
    }

    public void run()
    {
      float f1 = (float)(System.nanoTime() - this.c) / 1.0E+09F;
      if (f1 > this.b)
        f1 = this.b;
      float f2 = f1 * this.d;
      float f3 = AbsHorizontalStrip.this.d + Math.round(f2);
      Log.d(AbsHorizontalStrip.b(), "do animation " + f3 + "  " + f1);
      AbsHorizontalStrip.a(AbsHorizontalStrip.this, f3);
      if (f1 < this.b)
        b();
    }
  }

  public static abstract interface b
  {
    public abstract void onChildVisibleChanged(int paramInt1, int paramInt2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.AbsHorizontalStrip
 * JD-Core Version:    0.6.2
 */