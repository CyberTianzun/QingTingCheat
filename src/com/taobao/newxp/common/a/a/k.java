package com.taobao.newxp.common.a.a;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

public class k
  implements l
{
  private g a = new g();
  private int b = 0;
  private int c = 0;

  public g a()
  {
    Log.i("statistics", "Pingback model touchMove num is -------------->>>>>>>>>>>> " + this.a.a);
    return this.a;
  }

  public void a(Context paramContext, MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent != null)
      this.a.a(paramContext, paramMotionEvent);
  }

  public void a(a parama)
  {
    this.b = (1 + this.b);
  }

  public void a(m paramm)
  {
    this.c = (1 + this.c);
  }

  public void b()
  {
    this.a.c();
    this.c = 0;
    this.b = 0;
  }

  public int c()
  {
    return this.b;
  }

  public int d()
  {
    return this.c;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.k
 * JD-Core Version:    0.6.2
 */