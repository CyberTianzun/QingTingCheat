package com.talkingdata.pingan.sdk;

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
    if (PAAgent.LOG_ON)
      Log.d("pinganLog", "Send Success, Clear Data");
    e.a(PAAgent.c());
    List localList = paraman.e;
    e.b(paraman.f);
    e.c(paraman.g);
    e.d(paraman.h);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ac localac = (ac)localIterator.next();
      switch (localac.a)
      {
      default:
        break;
      case 1:
        PAAgent.b(false);
        break;
      case 2:
        c localc = localac.b;
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
      PAAgent.a();
  }

  public static void b()
  {
    try
    {
      f = false;
      q.a(new String[] { "Test", "[HTTPThread]pre send" });
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
        Context localContext = PAAgent.c();
        if (!b.b(localContext))
        {
          Log.w("pinganLog", "network is disabled.");
          PAAgent.i = true;
          return;
        }
        if ((PAAgent.f) && (!b.c(localContext)))
        {
          Log.w("pinganLog", "wifi is not connected.");
          PAAgent.i = true;
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

  static m e()
  {
    Context localContext = PAAgent.c();
    m localm = new m();
    String[] arrayOfString = j.h();
    try
    {
      localm.a = arrayOfString[0];
      localm.b = Integer.valueOf(arrayOfString[1]).intValue();
      localm.d = arrayOfString[2];
      localm.c = Float.valueOf(arrayOfString[3]).floatValue();
      label56: int[] arrayOfInt1 = j.j();
      localm.g = arrayOfInt1[0];
      localm.h = arrayOfInt1[1];
      int[] arrayOfInt2 = j.k();
      localm.i = arrayOfInt2[0];
      localm.j = arrayOfInt2[1];
      localm.k = arrayOfInt2[2];
      localm.l = arrayOfInt2[3];
      localm.m = j.l();
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)localContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      localm.n = (localDisplayMetrics.widthPixels / localDisplayMetrics.xdpi);
      localm.o = (localDisplayMetrics.heightPixels / localDisplayMetrics.ydpi);
      localm.p = localDisplayMetrics.densityDpi;
      localm.q = Build.DISPLAY;
      localm.r = "unknown";
      try
      {
        localm.r = ((String)Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[] { String.class }).invoke(null, new Object[] { "gsm.version.baseband" }));
        String str1 = a.d(localContext);
        if (str1 != null)
          localm.s = str1;
        String str2 = a.g(localContext);
        if (str2 != null)
          localm.t = str2;
        localm.y = a.f(localContext);
        localm.A = a.e(localContext);
        localm.B = a.c(localContext);
        return localm;
      }
      catch (Exception localException2)
      {
        while (true)
          q.a(new String[] { "fecth base band failed!" });
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
      d = new af(g.getLooper());
    if (e == null)
      e = new ae(g.getLooper());
  }

  private static boolean g()
  {
    if (PAAgent.c() == null)
    {
      q.a(new String[] { "TCAgent.getContext is null..." });
      return false;
    }
    an localan = h();
    if (localan == null)
    {
      q.a(new String[] { "No data to send. return true;" });
      return true;
    }
    if (PAAgent.LOG_ON)
      Log.d("pinganLog", "Post data to server...");
    boolean bool = o.a(localan);
    if (PAAgent.LOG_ON)
      Log.d("pinganLog", "server return success:" + bool);
    if (bool)
      a(localan);
    for (PAAgent.i = false; ; PAAgent.i = true)
      return bool;
  }

  private static an h()
  {
    Context localContext = PAAgent.c();
    an localan = new an();
    localan.a = a.b(localContext);
    localan.b = PAAgent.d();
    localan.c = PAAgent.m();
    localan.d = PAAgent.n();
    localan.i = PAAgent.e;
    int i = 0 + (3 + localan.a());
    if (PAAgent.e())
    {
      q.a(new String[] { "Prepare init Device profile" });
      ac localac3 = new ac();
      localac3.a = 1;
      localac3.c = e();
      localan.e.add(localac3);
      i += p.c(localac3.a) + localac3.c.a();
    }
    for (int j = 1; ; j = 0)
    {
      e.a(PAAgent.c());
      localan.h = e.e("error_report");
      List localList = e.d();
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator1 = localList.iterator();
      int k = 0;
      int m = i;
      while (true)
      {
        c localc;
        ac localac2;
        int n;
        if (localIterator1.hasNext())
        {
          localc = (c)localIterator1.next();
          k++;
          localc.h = e.a(localc.a, localan.f);
          localc.i = e.b(localc.a, localan.g);
          localac2 = new ac();
          localac2.a = 2;
          localac2.b = localc;
          n = localc.a();
          if ((n + m > 20480) && (k != 1))
            f = true;
        }
        else
        {
          String[] arrayOfString = new String[1];
          arrayOfString[0] = ("************ " + localArrayList.size() + " Session*****************");
          q.a(arrayOfString);
          localan.f = e.a(localArrayList);
          localan.g = e.b(localArrayList);
          if (localan.h <= 0L)
            break;
          Iterator localIterator2 = e.e(localan.h).iterator();
          while (localIterator2.hasNext())
          {
            ac localac1 = (ac)localIterator2.next();
            localan.e.add(localac1);
          }
        }
        m += n;
        localArrayList.add(localc);
        if ((localc.c != 2) || (localc.h.size() != 0) || (localc.i.size() != 0))
          localan.e.add(localac2);
      }
      e.b();
      if ((j == 0) && (localan.e.size() == 0))
        return null;
      return localan;
    }
  }

  public boolean d()
  {
    if (PAAgent.c() == null);
    List localList;
    do
    {
      return false;
      e.a(PAAgent.c());
      localList = e.d();
      e.b();
    }
    while (localList.size() <= 0);
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.d
 * JD-Core Version:    0.6.2
 */