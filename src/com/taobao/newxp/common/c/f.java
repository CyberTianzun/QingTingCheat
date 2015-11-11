package com.taobao.newxp.common.c;

import com.taobao.newxp.common.Log;

public class f extends a
{
  long b = System.currentTimeMillis() / 1000L;

  protected void a(e parame)
  {
    if (a())
    {
      parame.l = this.b;
      return;
    }
    Log.e(a, "Integrity verification failed！");
  }

  protected boolean a()
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.c.f
 * JD-Core Version:    0.6.2
 */