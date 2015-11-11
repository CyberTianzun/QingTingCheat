package com.tendcloud.tenddata;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class d
{
  static long a = 0L;
  static final int b = 60000;
  private static final int c = 20480;
  private static Handler d = null;
  private static Handler e = null;
  private static boolean f = false;
  private static final HandlerThread g = new HandlerThread("ProcessingThread");

  static
  {
    g.start();
  }

  static Handler a()
  {
    try
    {
      if (e == null)
        f();
      Handler localHandler = e;
      return localHandler;
    }
    finally
    {
    }
  }

  private static void a(an paraman)
  {
    e.a(j.h());
    List localList = paraman.e;
    e.b(paraman.f);
    e.c(paraman.g);
    e.d(paraman.h);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      z localz = (z)localIterator.next();
      switch (localz.a)
      {
      default:
        break;
      case 1:
        j.b(false);
        break;
      case 2:
        c localc = localz.b;
        if (localc.c == 1)
        {
          e.a(localc.a);
        }
        else if (localc.c == 3)
        {
          e.b(localc.a);
          e.c(localc.a);
          e.d(localc.a);
        }
        break;
      }
    }
    e.b();
    if (paraman.i != null)
      j.f();
  }

  public static void b()
  {
    try
    {
      f = false;
      g();
      if (f)
        d.sendEmptyMessageDelayed(0, 100L);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  static void c()
  {
    long l1 = 0L;
    while (true)
    {
      try
      {
        long l2 = SystemClock.elapsedRealtime() - a;
        if ((a > l1) && (l2 < 60000L))
          l1 = 60000L - l2;
        Context localContext = j.h();
        if (!b.b(localContext))
        {
          Log.w("TDLog", "network is disabled.");
          j.j = true;
          return;
        }
        if ((j.f) && (!b.c(localContext)))
        {
          Log.w("TDLog", "wifi is not connected.");
          j.j = true;
          continue;
        }
      }
      finally
      {
      }
      d.sendEmptyMessageDelayed(0, l1);
      a = l1 + SystemClock.elapsedRealtime();
    }
  }

  static n e()
  {
    Context localContext = j.h();
    n localn = new n();
    String[] arrayOfString = k.h();
    try
    {
      localn.a = arrayOfString[0];
      localn.b = Integer.valueOf(arrayOfString[1]).intValue();
      localn.d = arrayOfString[2];
      localn.c = Float.valueOf(arrayOfString[3]).floatValue();
      label56: int[] arrayOfInt1 = k.j();
      localn.g = arrayOfInt1[0];
      localn.h = arrayOfInt1[1];
      int[] arrayOfInt2 = k.k();
      localn.i = arrayOfInt2[0];
      localn.j = arrayOfInt2[1];
      localn.k = arrayOfInt2[2];
      localn.l = arrayOfInt2[3];
      localn.m = k.l();
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)localContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      localn.n = (localDisplayMetrics.widthPixels / localDisplayMetrics.xdpi);
      localn.o = (localDisplayMetrics.heightPixels / localDisplayMetrics.ydpi);
      localn.p = localDisplayMetrics.densityDpi;
      localn.q = Build.DISPLAY;
      localn.r = "unknown";
      try
      {
        localn.r = ((String)Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[] { String.class }).invoke(null, new Object[] { "gsm.version.baseband" }));
        label250: String str1 = a.d(localContext);
        if (str1 != null)
          localn.s = str1;
        String str2 = a.g(localContext);
        if (str2 != null)
          localn.t = str2;
        localn.y = a.f(localContext);
        localn.z = ae.d(localContext).toString();
        localn.A = a.e(localContext);
        localn.B = a.c(localContext);
        return localn;
      }
      catch (Exception localException2)
      {
        break label250;
      }
    }
    catch (Exception localException1)
    {
      break label56;
    }
  }

  private static void f()
  {
    if (d == null)
      d = new ac(g.getLooper());
    if (e == null)
      e = new ab(g.getLooper());
  }

  private static boolean g()
  {
    if (j.h() == null)
      return false;
    an localan = h();
    if (localan == null)
      return true;
    if (TCAgent.LOG_ON)
      Log.i("TDLog", "Post data to server...");
    boolean bool = p.a(localan);
    if (TCAgent.LOG_ON)
      Log.i("TDLog", "server return success:" + bool);
    if (bool)
      a(localan);
    for (j.j = false; ; j.j = true)
      return bool;
  }

  private static an h()
  {
    Context localContext = j.h();
    an localan = new an();
    localan.a = a.b(localContext);
    localan.b = j.i();
    localan.c = j.r();
    localan.d = j.s();
    localan.i = j.e;
    int i = 0 + (3 + localan.a());
    int j;
    if (j.j())
    {
      z localz1 = new z();
      localz1.a = 1;
      localz1.c = e();
      localan.e.add(localz1);
      j = i + (q.c(localz1.a) + localz1.c.a());
    }
    for (int k = 1; ; k = 0)
    {
      e.a(j.h());
      localan.h = e.e("error_report");
      List localList = e.d();
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator1 = localList.iterator();
      int m = j;
      int n = 0;
      while (true)
      {
        c localc;
        z localz3;
        int i1;
        if (localIterator1.hasNext())
        {
          localc = (c)localIterator1.next();
          n++;
          localc.h = e.a(localc.a, localan.f);
          localc.i = e.b(localc.a, localan.g);
          localz3 = new z();
          localz3.a = 2;
          localz3.b = localc;
          i1 = localc.a();
          if ((i1 + m > 20480) && (n != 1))
            f = true;
        }
        else
        {
          localan.f = e.a(localArrayList);
          localan.g = e.b(localArrayList);
          if (localan.h <= 0L)
            break;
          Iterator localIterator2 = e.e(localan.h).iterator();
          while (localIterator2.hasNext())
          {
            z localz2 = (z)localIterator2.next();
            localan.e.add(localz2);
          }
        }
        m += i1;
        localArrayList.add(localc);
        if ((localc.c != 2) || (localc.h.size() != 0) || (localc.i.size() != 0))
          localan.e.add(localz3);
      }
      e.b();
      if ((k == 0) && (localan.e.size() == 0))
        return null;
      return localan;
      j = i;
    }
  }

  public boolean d()
  {
    if (j.h() == null);
    List localList;
    do
    {
      return false;
      e.a(j.h());
      localList = e.d();
      e.b();
    }
    while (localList.size() <= 0);
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.d
 * JD-Core Version:    0.6.2
 */