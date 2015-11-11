package com.taobao.newxp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.taobao.newxp.common.Log;
import java.util.ArrayList;
import java.util.List;

public class f
  implements e
{
  static Drawable[] a;
  static int b = 0;
  private static final String e = f.class.getSimpleName();
  int c;
  List<a> d;

  public static f a(List<a> paramList)
  {
    f localf = new f();
    localf.d = paramList;
    if ((paramList == null) && (a != null))
    {
      ArrayList localArrayList = new ArrayList();
      Drawable[] arrayOfDrawable = a;
      int i = arrayOfDrawable.length;
      for (int j = 0; j < i; j++)
        localArrayList.add(new a(null, arrayOfDrawable[j]));
    }
    return localf;
  }

  public int a()
  {
    return this.d.size();
  }

  public Drawable a(int paramInt)
  {
    return ((a)this.d.get(paramInt)).b;
  }

  public View a(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    Drawable localDrawable = a(paramInt3);
    int i = paramInt1 - 10;
    int j = localDrawable.getIntrinsicHeight();
    int k = localDrawable.getIntrinsicWidth();
    RelativeLayout localRelativeLayout = new RelativeLayout(paramContext);
    localRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    RelativeLayout.LayoutParams localLayoutParams;
    if (k > j)
    {
      localLayoutParams = new RelativeLayout.LayoutParams(i, (int)(i / k * j));
      localLayoutParams.addRule(14);
    }
    while (true)
    {
      localLayoutParams.addRule(15);
      UMScreenshot localUMScreenshot = new UMScreenshot(paramContext);
      localUMScreenshot.setScreenshotDrawable(localDrawable);
      localRelativeLayout.addView(localUMScreenshot, localLayoutParams);
      return localRelativeLayout;
      localLayoutParams = new RelativeLayout.LayoutParams((int)(paramInt2 / j * k), paramInt2);
      localLayoutParams.addRule(14);
    }
  }

  public View a(final Context paramContext, final HorizontalStrip paramHorizontalStrip, final int paramInt)
  {
    UMScreenshot localUMScreenshot = new UMScreenshot(paramContext);
    Drawable localDrawable = a(paramInt);
    int i = localDrawable.getIntrinsicHeight();
    int j = localDrawable.getIntrinsicWidth();
    int k = (int)(this.c / i * j);
    localUMScreenshot.setLayoutParams(new ViewGroup.LayoutParams(k, -1));
    Log.a(e, "getView at pos=" + paramInt + " viewWidth=" + k + "  " + "  dh=" + i + " totalH=" + this.c);
    localUMScreenshot.setBackgroundColor(-12303292);
    localUMScreenshot.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new ScreenshotDialog(paramContext, f.this)
        {
          public void dismiss()
          {
            super.dismiss();
            f.1.this.b.c();
          }
        }
        .show(paramInt);
      }
    });
    return localUMScreenshot;
  }

  public void a(int paramInt, c paramc, float paramFloat)
  {
    Drawable localDrawable = a(paramInt);
    paramc.b(localDrawable.getIntrinsicHeight());
    paramc.a(localDrawable.getIntrinsicWidth());
  }

  public void a(HorizontalStrip.a parama)
  {
  }

  public void b(int paramInt)
  {
    this.c = paramInt;
  }

  public boolean b()
  {
    return true;
  }

  public static class a
  {
    public String a;
    public Drawable b;
    public float c = 0.0F;

    public a(String paramString, Drawable paramDrawable)
    {
      this.a = paramString;
      this.b = paramDrawable;
      this.c = (100 * paramDrawable.getIntrinsicHeight() / (100 * paramDrawable.getIntrinsicWidth()));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.f
 * JD-Core Version:    0.6.2
 */