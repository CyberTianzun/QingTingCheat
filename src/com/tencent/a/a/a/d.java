package com.tencent.a.a.a;

import com.tencent.a.b.h;
import java.util.ArrayList;
import java.util.Iterator;

public class d
{
  private long A = -1L;
  public int a = 1;
  public double b = 0.0D;
  public double c = 0.0D;
  public double d = -1.0D;
  public double e = 0.0D;
  public double f = 0.0D;
  public double g = 0.0D;
  public int h = 0;
  public String i = null;
  public String j = null;
  public String k = null;
  public String l = null;
  public String m = null;
  public String n = null;
  public String o = null;
  public String p = null;
  public String q = null;
  public String r = null;
  public String s = null;
  public String t = null;
  public String u = null;
  public String v = null;
  public ArrayList<c> w = null;
  public boolean x = false;
  public int y = 0;
  public int z = -1;

  public d()
  {
    this.e = 0.0D;
    this.d = 0.0D;
    this.c = 0.0D;
    this.b = 0.0D;
    this.p = null;
    this.o = null;
    this.n = null;
    this.m = null;
    this.x = false;
    this.A = System.currentTimeMillis();
    this.y = 0;
    this.z = -1;
    this.w = null;
  }

  public d(d paramd)
  {
    this.a = paramd.a;
    this.b = paramd.b;
    this.c = paramd.c;
    this.d = paramd.d;
    this.e = paramd.e;
    this.x = paramd.x;
    this.i = paramd.i;
    this.h = 0;
    this.j = paramd.j;
    this.k = paramd.k;
    this.l = paramd.l;
    this.m = paramd.m;
    this.n = paramd.n;
    this.o = paramd.o;
    this.p = paramd.p;
    this.q = paramd.q;
    this.r = paramd.r;
    this.s = paramd.s;
    this.t = paramd.t;
    this.u = paramd.u;
    this.v = paramd.v;
    this.A = paramd.a();
    this.y = paramd.y;
    this.z = paramd.z;
    this.w = null;
    if (paramd.w != null)
    {
      this.w = new ArrayList();
      Iterator localIterator = paramd.w.iterator();
      while (localIterator.hasNext())
      {
        c localc = (c)localIterator.next();
        this.w.add(localc);
      }
    }
  }

  public long a()
  {
    return this.A;
  }

  public void a(String paramString)
  {
    this.l = "Unknown";
    this.k = "Unknown";
    this.j = "Unknown";
    this.i = "Unknown";
    if (paramString == null);
    String[] arrayOfString;
    int i1;
    do
    {
      do
      {
        return;
        arrayOfString = paramString.split(",");
      }
      while (arrayOfString == null);
      i1 = arrayOfString.length;
      if (i1 > 0)
        this.i = arrayOfString[0];
      if (i1 > 1)
        this.j = arrayOfString[1];
      if (i1 == 3)
        this.k = arrayOfString[1];
      while (i1 == 3)
      {
        this.l = arrayOfString[2];
        return;
        if (i1 > 3)
          this.k = arrayOfString[2];
      }
    }
    while (i1 <= 3);
    this.l = arrayOfString[3];
  }

  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = localStringBuilder1.append(this.z).append(" ").append(this.y).append(" ");
    String str1;
    StringBuilder localStringBuilder3;
    if (this.x)
    {
      str1 = "Mars";
      localStringBuilder3 = localStringBuilder2.append(str1).append(" ");
      if (this.a != 0)
        break label424;
    }
    label424: for (String str2 = "GPS"; ; str2 = "Network")
    {
      localStringBuilder3.append(str2).append("\n");
      localStringBuilder1.append(this.b).append(" ").append(this.c).append("\n");
      localStringBuilder1.append(this.d).append(" ").append(this.e).append("\n");
      localStringBuilder1.append(this.f).append(" ").append(this.g).append("\n");
      if ((this.z == 3) || (this.z == 4))
        localStringBuilder1.append(this.i).append(" ").append(this.j).append(" ").append(this.k).append(" ").append(this.l).append(" ").append(this.m).append(" ").append(this.n).append(" ").append(this.o).append(" ").append(this.p).append("\n");
      if ((this.z != 4) || (this.w == null))
        break label431;
      localStringBuilder1.append(this.w.size()).append("\n");
      Iterator localIterator = this.w.iterator();
      while (localIterator.hasNext())
      {
        c localc = (c)localIterator.next();
        localStringBuilder1.append(localc.a).append(",").append(localc.b).append(",").append(localc.c).append(",").append(localc.d).append(",").append(localc.e).append(",").append(localc.f).append("\n");
      }
      str1 = "WGS84";
      break;
    }
    label431: if (this.z == 7)
    {
      if (this.h != 0)
        break label557;
      localStringBuilder1.append(this.i).append(" ").append(this.j).append(" ").append(this.k).append(" ").append(this.l).append(" ").append(this.m).append(" ").append(this.n).append(" ").append(this.o).append(" ").append(this.p).append("\n");
    }
    while (true)
    {
      h.a(localStringBuilder1.toString());
      return localStringBuilder1.toString();
      label557: if (this.h == 1)
        localStringBuilder1.append(this.i).append(" ").append(this.q).append(" ").append(this.r).append(" ").append(this.s).append(" ").append(this.t).append(" ").append(this.u).append(" ").append(this.v).append("\n");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.a.a.d
 * JD-Core Version:    0.6.2
 */