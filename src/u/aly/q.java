package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.Gender;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class q
{
  private List<p> a = new ArrayList();
  private ah b = null;
  private ai c = null;
  private ak d = null;
  private ax e = null;
  private Context f = null;

  public q(Context paramContext)
  {
    this.f = paramContext;
  }

  private void a(Context paramContext)
  {
    try
    {
      this.c.a(AnalyticsConfig.getAppkey(paramContext));
      this.c.e(AnalyticsConfig.getChannel(paramContext));
      if ((AnalyticsConfig.mWrapperType != null) && (AnalyticsConfig.mWrapperVersion != null))
      {
        this.c.f(AnalyticsConfig.mWrapperType);
        this.c.g(AnalyticsConfig.mWrapperVersion);
      }
      this.c.c(bi.u(paramContext));
      this.c.a(bc.a);
      this.c.d("5.2.4");
      this.c.b(bi.d(paramContext));
      this.c.a(Integer.parseInt(bi.c(paramContext)));
      if (AnalyticsConfig.mVerticalType == 1)
      {
        this.c.c(AnalyticsConfig.mVerticalType);
        this.c.d("5.2.4.1");
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void b(Context paramContext)
  {
    try
    {
      this.d.f(bi.a());
      this.d.a(bi.f(paramContext));
      this.d.b(bi.g(paramContext));
      this.d.c(bi.p(paramContext));
      this.d.e(Build.MODEL);
      this.d.g("Android");
      this.d.h(Build.VERSION.RELEASE);
      int[] arrayOfInt = bi.r(paramContext);
      if (arrayOfInt != null)
        this.d.a(new ba(arrayOfInt[1], arrayOfInt[0]));
      if ((AnalyticsConfig.GPU_RENDERER != null) && (AnalyticsConfig.GPU_VENDER != null));
      this.d.i(Build.BOARD);
      this.d.j(Build.BRAND);
      this.d.a(Build.TIME);
      this.d.k(Build.MANUFACTURER);
      this.d.l(Build.ID);
      this.d.m(Build.DEVICE);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void c(Context paramContext)
  {
    while (true)
    {
      try
      {
        String[] arrayOfString1 = bi.j(paramContext);
        if ("Wi-Fi".equals(arrayOfString1[0]))
        {
          this.e.a(ag.c);
          if (!"".equals(arrayOfString1[1]))
            this.e.e(arrayOfString1[1]);
          this.e.c(bi.s(paramContext));
          String[] arrayOfString2 = bi.n(paramContext);
          this.e.b(arrayOfString2[0]);
          this.e.a(arrayOfString2[1]);
          this.e.a(bi.m(paramContext));
          if ((AnalyticsConfig.sAge == 0) && (AnalyticsConfig.sGender == null) && (AnalyticsConfig.sId == null) && (AnalyticsConfig.sSource == null))
            break;
          bg localbg = new bg();
          localbg.a(AnalyticsConfig.sAge);
          localbg.a(Gender.transGender(AnalyticsConfig.sGender));
          localbg.a(AnalyticsConfig.sId);
          localbg.b(AnalyticsConfig.sSource);
          this.e.a(localbg);
          return;
        }
        if ("2G/3G".equals(arrayOfString1[0]))
        {
          this.e.a(ag.b);
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      this.e.a(ag.a);
    }
  }

  private String i()
  {
    return u.a(this.f).getString("session_id", null);
  }

  public Context a()
  {
    return this.f;
  }

  public void a(ah paramah)
  {
    try
    {
      this.b = paramah;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void a(bf parambf)
  {
    String str = i();
    if (str == null)
      return;
    try
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
        ((p)localIterator.next()).a(parambf, str);
    }
    finally
    {
    }
    this.a.clear();
    if (this.b != null)
    {
      parambf.a(this.b);
      this.b = null;
    }
    parambf.a(c());
    parambf.a(d());
    parambf.a(e());
    parambf.a(h());
    parambf.a(f());
    parambf.a(g());
  }

  public void a(p paramp)
  {
    try
    {
      this.a.add(paramp);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  protected boolean a(int paramInt)
  {
    return true;
  }

  public int b()
  {
    try
    {
      int i = this.a.size();
      ah localah = this.b;
      if (localah != null)
        i++;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public ai c()
  {
    try
    {
      if (this.c == null)
      {
        this.c = new ai();
        a(this.f);
      }
      ai localai = this.c;
      return localai;
    }
    finally
    {
    }
  }

  public ak d()
  {
    try
    {
      if (this.d == null)
      {
        this.d = new ak();
        b(this.f);
      }
      ak localak = this.d;
      return localak;
    }
    finally
    {
    }
  }

  public ax e()
  {
    try
    {
      if (this.e == null)
      {
        this.e = new ax();
        c(this.f);
      }
      ax localax = this.e;
      return localax;
    }
    finally
    {
    }
  }

  public at f()
  {
    try
    {
      at localat = h.b(this.f).a();
      return localat;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public as g()
  {
    try
    {
      as localas = h.a(this.f).b();
      return localas;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public aj h()
  {
    try
    {
      aj localaj = w.a(this.f);
      return localaj;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return new aj();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.q
 * JD-Core Version:    0.6.2
 */