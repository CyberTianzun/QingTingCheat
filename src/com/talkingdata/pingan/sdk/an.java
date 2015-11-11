package com.talkingdata.pingan.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class an
  implements g, r
{
  public String a = "";
  public String b = "";
  public h c = new h();
  public u d = new u();
  public List e = new ArrayList();
  public long f = 0L;
  public long g = 0L;
  public long h = 0L;
  public Long[][] i;

  public int a()
  {
    return p.c(5) + p.c(this.a) + p.c(this.b) + this.c.a() + this.d.a();
  }

  public void a(p paramp)
  {
    paramp.b(6);
    paramp.a(this.a);
    paramp.a(this.b);
    paramp.a(this.c);
    paramp.a(this.d);
    paramp.b(this.e.size());
    Iterator localIterator = this.e.iterator();
    while (localIterator.hasNext())
      paramp.a((ac)localIterator.next());
    if (this.i == null)
      paramp.b();
    while (true)
    {
      return;
      paramp.b(this.i.length);
      Long[][] arrayOfLong = this.i;
      int j = arrayOfLong.length;
      for (int k = 0; k < j; k++)
        paramp.a(arrayOfLong[k]);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.an
 * JD-Core Version:    0.6.2
 */