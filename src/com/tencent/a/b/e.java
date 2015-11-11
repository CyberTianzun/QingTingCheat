package com.tencent.a.b;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.Iterator;

public final class e
{
  private static LocationManager b = null;
  private static float d = 0.0F;
  private Context a = null;
  private a c = null;
  private c e = null;
  private b f = null;
  private boolean g = false;
  private byte[] h = new byte[0];
  private int i = 1024;
  private long j = 0L;
  private boolean k = false;
  private int l = 0;
  private int m = 0;

  private void b()
  {
    this.m = 0;
    this.l = 0;
    GpsStatus localGpsStatus = b.getGpsStatus(null);
    if (localGpsStatus == null);
    while (true)
    {
      return;
      int n = localGpsStatus.getMaxSatellites();
      Iterator localIterator = localGpsStatus.getSatellites().iterator();
      if (localIterator != null)
        while ((localIterator.hasNext()) && (this.l <= n))
        {
          this.l = (1 + this.l);
          if (((GpsSatellite)localIterator.next()).usedInFix())
            this.m = (1 + this.m);
        }
    }
  }

  public final void a()
  {
    synchronized (this.h)
    {
      if (!this.g)
        return;
      if ((b != null) && (this.c != null))
      {
        b.removeGpsStatusListener(this.c);
        b.removeUpdates(this.c);
      }
      this.g = false;
      return;
    }
  }

  public final boolean a(c paramc, Context paramContext)
  {
    synchronized (this.h)
    {
      if (this.g)
        return true;
      if ((paramContext == null) || (paramc == null))
        return false;
      this.a = paramContext;
      this.e = paramc;
      try
      {
        b = (LocationManager)this.a.getSystemService("location");
        this.c = new a((byte)0);
        if (b != null)
        {
          a locala = this.c;
          if (locala != null);
        }
        else
        {
          return false;
        }
      }
      catch (Exception localException1)
      {
        return false;
      }
      try
      {
        b.requestLocationUpdates("gps", 1000L, 0.0F, this.c);
        b.addGpsStatusListener(this.c);
        if (b.isProviderEnabled("gps"));
        for (this.i = 4; ; this.i = 0)
        {
          this.g = true;
          return this.g;
        }
      }
      catch (Exception localException2)
      {
        return false;
      }
    }
  }

  final class a
    implements GpsStatus.Listener, LocationListener
  {
    private a()
    {
    }

    public final void onGpsStatusChanged(int paramInt)
    {
      switch (paramInt)
      {
      default:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        e.a(e.this);
        return;
        e.a(e.this, 1);
        continue;
        e.a(e.this, 0);
        continue;
        e.a(e.this, 2);
      }
    }

    public final void onLocationChanged(Location paramLocation)
    {
      double d1;
      double d2;
      int i;
      if (paramLocation != null)
      {
        d1 = paramLocation.getLatitude();
        d2 = paramLocation.getLongitude();
        boolean bool1 = d1 < 29.999998211860657D;
        i = 0;
        if (bool1)
        {
          boolean bool2 = d2 < 103.99999916553497D;
          i = 0;
          if (bool2)
            break label52;
        }
        if (i != 0)
          break label157;
      }
      label52: 
      do
      {
        return;
        boolean bool3 = Math.abs(d1) < 1.0E-08D;
        i = 0;
        if (bool3)
          break;
        boolean bool4 = Math.abs(d2) < 1.0E-08D;
        i = 0;
        if (bool4)
          break;
        boolean bool5 = d1 < -90.0D;
        i = 0;
        if (bool5)
          break;
        boolean bool6 = d1 < 90.0D;
        i = 0;
        if (bool6)
          break;
        boolean bool7 = d2 < -180.0D;
        i = 0;
        if (bool7)
          break;
        boolean bool8 = d2 < 180.0D;
        i = 0;
        if (bool8)
          break;
        i = 1;
        break;
        e.a(e.this, System.currentTimeMillis());
        e.a(e.this);
        e.a(e.this, 2);
        e.a(e.this, new e.b(e.this, paramLocation, e.b(e.this), e.c(e.this), e.d(e.this), e.e(e.this)));
      }
      while (e.f(e.this) == null);
      label157: e.f(e.this).a(e.g(e.this));
    }

    public final void onProviderDisabled(String paramString)
    {
      if (paramString != null);
      try
      {
        boolean bool = paramString.equals("gps");
        if (!bool);
        do
        {
          return;
          e.b(e.this, e.c(e.this, 0));
          e.d(e.this, 0);
        }
        while (e.f(e.this) == null);
        e.f(e.this).a(e.d(e.this));
        return;
      }
      catch (Exception localException)
      {
      }
    }

    public final void onProviderEnabled(String paramString)
    {
      if (paramString != null);
      try
      {
        boolean bool = paramString.equals("gps");
        if (!bool);
        do
        {
          return;
          e.d(e.this, 4);
        }
        while (e.f(e.this) == null);
        e.f(e.this).a(e.d(e.this));
        return;
      }
      catch (Exception localException)
      {
      }
    }

    public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }

  public final class b
    implements Cloneable
  {
    private Location a = null;
    private long b = 0L;
    private int c = 0;

    public b(Location paramInt1, int paramInt2, int paramInt3, int paramLong, long arg6)
    {
      if (paramInt1 != null)
      {
        this.a = new Location(paramInt1);
        this.c = paramInt3;
        Object localObject;
        this.b = localObject;
      }
    }

    public final boolean a()
    {
      if (this.a == null);
      while (((this.c > 0) && (this.c < 3)) || (System.currentTimeMillis() - this.b > 30000L))
        return false;
      return true;
    }

    public final Location b()
    {
      return this.a;
    }

    public final Object clone()
    {
      try
      {
        localb = (b)super.clone();
        if (this.a != null)
          localb.a = new Location(this.a);
        return localb;
      }
      catch (Exception localException)
      {
        while (true)
          b localb = null;
      }
    }
  }

  public static abstract interface c
  {
    public abstract void a(int paramInt);

    public abstract void a(e.b paramb);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.e
 * JD-Core Version:    0.6.2
 */