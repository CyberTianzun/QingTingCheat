package com.taobao.newxp.view.common.gif;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class g extends AnimationDrawable
{
  a a;
  private List<a> b;

  public g(InputStream paramInputStream, View paramView)
  {
    this.a = new a();
    this.a.a(paramInputStream);
    setCallback(paramView);
    a();
  }

  public g(List<a> paramList, View paramView)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      addFrame(new BitmapDrawable(locala.a), locala.b);
    }
    setCallback(paramView);
    setOneShot(false);
    setVisible(true, false);
  }

  public void a()
  {
    this.b = new ArrayList();
    int i = this.a.a();
    for (int j = 0; j < i; j++)
    {
      Bitmap localBitmap = this.a.b(j);
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(localBitmap);
      a locala = new a();
      locala.a = localBitmap;
      locala.b = this.a.a(j);
      this.b.add(locala);
      addFrame(localBitmapDrawable, locala.b);
    }
    setOneShot(false);
    setVisible(true, false);
  }

  public List<a> b()
  {
    return this.b;
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    start();
  }

  public class a
  {
    Bitmap a;
    int b;

    public a()
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.common.gif.g
 * JD-Core Version:    0.6.2
 */