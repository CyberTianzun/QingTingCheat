package com.tencent.a.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public final class k
{
  private static int a = 10000;
  private static int b = 15000;
  private static int c = 5000;
  private static int d = 20000;
  private static int e = 25000;
  private static int f = 15000;
  private static ArrayList<a> g;
  private static long h;
  private static long i;
  private static long j;
  private static long k;
  private static long l;
  private static long m;
  private static long n;
  private static long o;
  private static long p;
  private static long q;
  private static int r;
  private static int s;
  private static int t;
  private static int u;

  static
  {
    a = 12000;
    b = 20000;
    c = 8000;
    d = 20000;
    e = 25000;
    f = 15000;
    ConnectivityManager localConnectivityManager = (ConnectivityManager)l.b().getSystemService("connectivity");
    if (localConnectivityManager != null)
    {
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        int i1 = localNetworkInfo.getType();
        if ((localNetworkInfo.isConnected()) && (i1 == 0))
        {
          String str = ((TelephonyManager)l.b().getSystemService("phone")).getSubscriberId();
          if ((str != null) && (str.length() > 3) && (!str.startsWith("46000")) && (!str.startsWith("46002")))
          {
            a = 15000;
            b = 25000;
            c = 10000;
            d = 25000;
            e = 35000;
            f = 15000;
          }
        }
      }
    }
  }

  public static int a()
  {
    int i1 = a;
    if ((j > 0L) && (k > 0L));
    for (int i2 = (int)(Math.max(m, j) + k - l); ; i2 = i1)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)l.b().getSystemService("connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if (localNetworkInfo != null)
          if ((localNetworkInfo.isConnected()) || (!localNetworkInfo.isAvailable()))
            break label195;
      }
      for (i2 = b; ; i2 = c)
        label195: 
        do
        {
          int i3 = i2 + u * c;
          if (i3 <= c)
            i3 = c;
          if (i3 <= k)
            i3 = (int)(k + c);
          if (i3 >= b)
            i3 = b;
          a locala = b(Thread.currentThread().getId());
          if (locala == null)
            locala = a(Thread.currentThread().getId());
          if (i3 < locala.g + c)
            i3 = locala.g + c;
          locala.g = i3;
          return i3;
        }
        while ((k <= 0L) || (k >= c));
    }
  }

  private static a a(long paramLong)
  {
    if (g == null)
      g = new ArrayList();
    int i2;
    int i3;
    int i4;
    label259: int i6;
    int i7;
    synchronized (g)
    {
      Iterator localIterator;
      if (g.size() > 20)
      {
        int i1 = g.size();
        i2 = 0;
        i3 = 0;
        i4 = 0;
        if (i2 < i1 / 2)
          break label259;
        if (i3 != 0)
        {
          g.get(0);
          h = 0L;
          g.get(0);
          i = 0L;
          k = ((a)g.get(0)).c;
          l = ((a)g.get(0)).c;
          o = ((a)g.get(0)).d;
          p = ((a)g.get(0)).d;
          if (((a)g.get(0)).f > 0L)
            r = (int)(1000 * ((a)g.get(0)).e / ((a)g.get(0)).f);
          s = r;
          localIterator = g.iterator();
        }
      }
      int i5;
      do
      {
        a locala2;
        do
        {
          if (!localIterator.hasNext())
          {
            a locala1 = new a();
            locala1.a = paramLong;
            g.add(locala1);
            return locala1;
            if ((((a)g.get(i4)).f <= 0L) && (System.currentTimeMillis() - ((a)g.get(i4)).b <= 600000L))
              break label515;
            g.remove(i4);
            i6 = 1;
            i7 = i4;
            break;
          }
          locala2 = (a)localIterator.next();
          if (0L > h)
            h = 0L;
          if (0L < i)
            i = 0L;
          if (locala2.c > k)
            k = locala2.c;
          if (locala2.c < l)
            l = locala2.c;
          if (locala2.d > o)
            o = locala2.d;
          if (locala2.d < p)
            p = locala2.d;
        }
        while (locala2.f <= 0L);
        i5 = (int)(1000 * locala2.e / locala2.f);
        if (i5 > r)
          r = i5;
      }
      while (i5 >= s);
      s = i5;
    }
    while (true)
    {
      i2++;
      i4 = i7;
      i3 = i6;
      break;
      label515: int i8 = i4 + 1;
      int i9 = i3;
      i7 = i8;
      i6 = i9;
    }
  }

  public static void a(int paramInt)
  {
    a locala1 = b(Thread.currentThread().getId());
    if (locala1 == null)
      return;
    locala1.f = (System.currentTimeMillis() - locala1.b);
    locala1.b = System.currentTimeMillis();
    locala1.e = paramInt;
    long l1;
    if (locala1.f == 0L)
      l1 = 1L;
    while (true)
    {
      int i1 = (int)(paramInt * 1000 / l1);
      t = i1;
      int i2;
      label79: int i3;
      if (i1 > r)
      {
        i2 = t;
        r = i2;
        if (t >= s)
          break label202;
        i3 = t;
        label98: s = i3;
        if (g == null);
      }
      label202: a locala2;
      synchronized (g)
      {
        Iterator localIterator = g.iterator();
        if (!localIterator.hasNext())
        {
          if ((u > 0) && (locala1.c < c) && (locala1.d < f))
            u = -1 + u;
          locala1.g = ((int)locala1.c);
          return;
          l1 = locala1.f;
          continue;
          i2 = r;
          break label79;
          if (s == 0)
          {
            i3 = t;
            break label98;
          }
          i3 = s;
          break label98;
        }
        locala2 = (a)localIterator.next();
      }
    }
  }

  public static void a(HttpURLConnection paramHttpURLConnection)
  {
    a locala = b(Thread.currentThread().getId());
    if (locala == null)
      locala = a(Thread.currentThread().getId());
    if (locala == null)
      return;
    locala.b = System.currentTimeMillis();
  }

  public static void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      u = 1 + u;
    a locala = c(Thread.currentThread().getId());
    if (locala != null);
  }

  public static int b()
  {
    int i1 = d;
    if ((n > 0L) && (o > 0L));
    for (int i2 = (int)(Math.max(q, n) + o - p); ; i2 = i1)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)l.b().getSystemService("connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if (localNetworkInfo != null)
          if ((localNetworkInfo.isConnected()) || (!localNetworkInfo.isAvailable()))
            break label207;
      }
      for (i2 = e; ; i2 = f)
        label207: 
        do
        {
          int i3 = i2 + u * c;
          if (i3 <= f)
            i3 = f;
          if (i3 <= o)
            i3 = (int)(o + f);
          if (i3 >= e)
            i3 = e;
          a locala = b(Thread.currentThread().getId());
          if (locala != null)
          {
            if (i3 < locala.h + f)
              i3 = locala.h + f;
            if (i3 < locala.g + f)
              i3 = locala.g + f;
            locala.h = i3;
          }
          return i3;
        }
        while ((o <= 0L) || (o >= f));
    }
  }

  private static a b(long paramLong)
  {
    if (g == null)
      return null;
    synchronized (g)
    {
      Iterator localIterator = g.iterator();
      a locala;
      do
      {
        if (!localIterator.hasNext())
          return null;
        locala = (a)localIterator.next();
      }
      while (locala.a != paramLong);
      return locala;
    }
  }

  private static a c(long paramLong)
  {
    if (g != null);
    while (true)
    {
      int i2;
      synchronized (g)
      {
        int i1 = g.size();
        i2 = i1 - 1;
        if (i2 < 0)
          return null;
        if (((a)g.get(i2)).a == paramLong)
        {
          a locala = (a)g.remove(i2);
          return locala;
        }
      }
      i2--;
    }
  }

  public static void c()
  {
    a locala1 = b(Thread.currentThread().getId());
    if (locala1 == null);
    long l1;
    long l2;
    label77: 
    do
    {
      return;
      locala1.c = (System.currentTimeMillis() - locala1.b);
      locala1.b = System.currentTimeMillis();
      m = locala1.c;
      if (locala1.c <= k)
        break;
      l1 = locala1.c;
      k = l1;
      if (locala1.c >= l)
        break label150;
      l2 = locala1.c;
      l = l2;
    }
    while (g == null);
    while (true)
    {
      Iterator localIterator;
      int i1;
      synchronized (g)
      {
        localIterator = g.iterator();
        i1 = 0;
        if (localIterator.hasNext())
          break label173;
        if (i1 > 0)
          j /= i1;
        return;
      }
      l1 = k;
      break;
      label150: if (l == 0L)
      {
        l2 = locala1.c;
        break label77;
      }
      l2 = l;
      break label77;
      label173: a locala2 = (a)localIterator.next();
      if (locala2.c > 0L)
      {
        j += locala2.c;
        i1++;
      }
    }
  }

  public static void d()
  {
    a locala1 = b(Thread.currentThread().getId());
    if (locala1 == null);
    long l1;
    long l2;
    label77: 
    do
    {
      return;
      locala1.d = (System.currentTimeMillis() - locala1.b);
      locala1.b = System.currentTimeMillis();
      q = locala1.d;
      if (locala1.d <= o)
        break;
      l1 = locala1.d;
      o = l1;
      if (locala1.d >= p)
        break label150;
      l2 = locala1.d;
      p = l2;
    }
    while (g == null);
    while (true)
    {
      Iterator localIterator;
      int i1;
      synchronized (g)
      {
        localIterator = g.iterator();
        i1 = 0;
        if (localIterator.hasNext())
          break label173;
        if (i1 > 0)
          n /= i1;
        return;
      }
      l1 = o;
      break;
      label150: if (p == 0L)
      {
        l2 = locala1.d;
        break label77;
      }
      l2 = p;
      break label77;
      label173: a locala2 = (a)localIterator.next();
      if (locala2.d > 0L)
      {
        n += locala2.d;
        i1++;
      }
    }
  }

  public static final class a
  {
    public long a;
    public long b;
    public long c;
    public long d;
    public int e;
    public long f;
    public int g;
    public int h;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.k
 * JD-Core Version:    0.6.2
 */