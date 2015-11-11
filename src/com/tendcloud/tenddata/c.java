package com.tendcloud.tenddata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class c
  implements g, m
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
    int m = q.c(7) + q.c(this.a) + q.c(this.b) + q.c(this.c) + q.c(this.g) + q.c(this.k) + q.c(this.h.size());
    Iterator localIterator1 = this.h.iterator();
    int n = m;
    while (localIterator1.hasNext())
      n += ((ai)localIterator1.next()).a();
    int i1 = n + q.c(this.i.size());
    Iterator localIterator2 = this.i.iterator();
    int i2 = i1;
    while (localIterator2.hasNext())
      i2 += ((y)localIterator2.next()).a();
    return i2;
  }

  public void a(q paramq)
  {
    paramq.b(7);
    paramq.a(this.a);
    paramq.a(this.b);
    paramq.a(this.c);
    paramq.a(this.g);
    paramq.b(this.h.size());
    Iterator localIterator1 = this.h.iterator();
    while (localIterator1.hasNext())
      paramq.a((ai)localIterator1.next());
    paramq.b(this.i.size());
    Iterator localIterator2 = this.i.iterator();
    while (localIterator2.hasNext())
      paramq.a((y)localIterator2.next());
    paramq.a(this.k);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.c
 * JD-Core Version:    0.6.2
 */