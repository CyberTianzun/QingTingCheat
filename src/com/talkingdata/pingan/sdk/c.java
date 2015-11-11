package com.talkingdata.pingan.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class c
  implements g, l
{
  public static final int d = 1;
  public static final int e = 2;
  public static final int f = 3;
  public String a = "";
  public long b = 0L;
  public int c = 0;
  public int g = 0;
  public List h = new ArrayList();
  public List i = new ArrayList();
  public int j = 0;
  public int k = 0;

  public int a()
  {
    int m = p.c(7) + p.c(this.a) + p.c(this.b) + p.c(this.c) + p.c(this.g) + p.c(this.k) + p.c(this.h.size());
    Iterator localIterator1 = this.h.iterator();
    int n = m;
    while (localIterator1.hasNext())
      n += ((am)localIterator1.next()).a();
    int i1 = n + p.c(this.i.size());
    Iterator localIterator2 = this.i.iterator();
    int i2 = i1;
    while (localIterator2.hasNext())
      i2 += ((ab)localIterator2.next()).a();
    return i2;
  }

  public void a(p paramp)
  {
    paramp.b(7);
    paramp.a(this.a);
    paramp.a(this.b);
    paramp.a(this.c);
    paramp.a(this.g);
    paramp.b(this.h.size());
    Iterator localIterator1 = this.h.iterator();
    while (localIterator1.hasNext())
      paramp.a((am)localIterator1.next());
    paramp.b(this.i.size());
    Iterator localIterator2 = this.i.iterator();
    while (localIterator2.hasNext())
      paramp.a((ab)localIterator2.next());
    paramp.a(this.k);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.c
 * JD-Core Version:    0.6.2
 */