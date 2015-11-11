package com.taobao.munion.base.download;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import java.lang.reflect.Field;

public class h
{
  protected Context d;
  protected Notification e;
  protected Notification.Builder f;
  protected PendingIntent g;

  public h(Context paramContext)
  {
    this.d = paramContext.getApplicationContext();
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f = new Notification.Builder(paramContext);
      return;
    }
    this.e = new Notification();
  }

  public h a(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f.setSmallIcon(paramInt);
      return this;
    }
    this.e.icon = paramInt;
    return this;
  }

  public h a(long paramLong)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f.setWhen(paramLong);
      return this;
    }
    this.e.when = paramLong;
    return this;
  }

  public h a(PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f.setContentIntent(paramPendingIntent);
      return this;
    }
    this.g = paramPendingIntent;
    return this;
  }

  public h a(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f.setOngoing(paramBoolean);
      return this;
    }
    if (paramBoolean)
    {
      Notification localNotification2 = this.e;
      localNotification2.flags = (0x2 | localNotification2.flags);
      return this;
    }
    Notification localNotification1 = this.e;
    localNotification1.flags = (0xFFFFFFFD & localNotification1.flags);
    return this;
  }

  public h b(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f.setAutoCancel(paramBoolean);
      return this;
    }
    if (paramBoolean)
    {
      Notification localNotification2 = this.e;
      localNotification2.flags = (0x10 | localNotification2.flags);
      return this;
    }
    Notification localNotification1 = this.e;
    localNotification1.flags = (0xFFFFFFEF & localNotification1.flags);
    return this;
  }

  public void b()
  {
    if (Build.VERSION.SDK_INT >= 16);
    try
    {
      Field localField = Notification.Builder.class.getDeclaredField("mActions");
      localField.setAccessible(true);
      localField.set(this.f, localField.get(this.f).getClass().newInstance());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public h c(CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.f.setTicker(paramCharSequence);
      return this;
    }
    this.e.tickerText = paramCharSequence;
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.download.h
 * JD-Core Version:    0.6.2
 */