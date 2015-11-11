package com.taobao.munion.base.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.taobao.munion.base.Log;

public class c
{
  private static final String b = c.class.getName();
  final Messenger a = new Messenger(new b());
  private Context c;
  private g d;
  private Messenger e;
  private String f = "none";
  private String g = "";
  private String h;
  private String i;
  private String j;
  private String k;
  private String[] l;
  private String[] m;
  private String[] n;
  private String[] o;
  private String[] p;
  private String[] q;
  private boolean r = false;
  private boolean s = false;
  private boolean t = false;
  private ServiceConnection u = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Log.d("ServiceConnection.onServiceConnected", new Object[0]);
      c.a(c.this, new Messenger(paramAnonymousIBinder));
      try
      {
        Message localMessage = Message.obtain(null, 4);
        c.a locala = new c.a(c.d(c.this), c.e(c.this), c.f(c.this));
        locala.e = c.g(c.this);
        locala.f = c.h(c.this);
        locala.a = c.i(c.this);
        locala.g = c.j(c.this);
        locala.i = c.k(c.this);
        locala.j = c.l(c.this);
        locala.k = c.m(c.this);
        locala.l = c.n(c.this);
        locala.h = c.o(c.this);
        locala.m = c.p(c.this);
        locala.n = c.q(c.this);
        locala.o = c.r(c.this);
        localMessage.setData(locala.a());
        localMessage.replyTo = c.this.a;
        c.s(c.this).send(localMessage);
        return;
      }
      catch (RemoteException localRemoteException)
      {
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      Log.d("ServiceConnection.onServiceDisconnected", new Object[0]);
      c.a(c.this, null);
    }
  };

  public c(Context paramContext, String paramString1, String paramString2)
  {
    this.c = paramContext.getApplicationContext();
    this.f = paramString1;
    this.h = paramString2;
  }

  public void a()
  {
    Intent localIntent = new Intent(this.c, DownloadingService.class);
    this.c.bindService(localIntent, this.u, 1);
    this.c.startService(new Intent(this.c, DownloadingService.class));
  }

  public void a(g paramg)
  {
    this.d = paramg;
  }

  public void a(String paramString)
  {
    this.i = paramString;
  }

  public void a(boolean paramBoolean)
  {
    this.r = paramBoolean;
  }

  public void a(String[] paramArrayOfString)
  {
    this.l = paramArrayOfString;
  }

  public void b(String paramString)
  {
    this.j = paramString;
  }

  public void b(boolean paramBoolean)
  {
    this.s = paramBoolean;
  }

  public void b(String[] paramArrayOfString)
  {
    this.p = paramArrayOfString;
  }

  public void c(String paramString)
  {
    this.k = paramString;
  }

  public void c(boolean paramBoolean)
  {
    this.t = paramBoolean;
  }

  public void c(String[] paramArrayOfString)
  {
    this.m = paramArrayOfString;
  }

  public c d(String paramString)
  {
    this.g = paramString;
    return this;
  }

  public void d(String[] paramArrayOfString)
  {
    this.n = paramArrayOfString;
  }

  public void e(String[] paramArrayOfString)
  {
    this.o = paramArrayOfString;
  }

  public void f(String[] paramArrayOfString)
  {
    this.q = paramArrayOfString;
  }

  static class a
  {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String[] g = null;
    public String[] h = null;
    public String[] i = null;
    public String[] j = null;
    public String[] k = null;
    public String[] l = null;
    public boolean m = false;
    public boolean n = false;
    public boolean o = false;

    public a(String paramString1, String paramString2, String paramString3)
    {
      this.b = paramString1;
      this.c = paramString2;
      this.d = paramString3;
    }

    public static a a(Bundle paramBundle)
    {
      a locala = new a(paramBundle.getString("mComponentName"), paramBundle.getString("mTitle"), paramBundle.getString("mUrl"));
      locala.e = paramBundle.getString("mMd5");
      locala.f = paramBundle.getString("mTargetMd5");
      locala.a = paramBundle.getString("mReqClz");
      locala.g = paramBundle.getStringArray("succUrls");
      locala.i = paramBundle.getStringArray("faiUrls");
      locala.j = paramBundle.getStringArray("startUrls");
      locala.k = paramBundle.getStringArray("pauseUrls");
      locala.l = paramBundle.getStringArray("cancelUrls");
      locala.h = paramBundle.getStringArray("carryonUrls");
      locala.m = paramBundle.getBoolean("rich_notification");
      locala.n = paramBundle.getBoolean("mSilent");
      locala.o = paramBundle.getBoolean("mWifiOnly");
      return locala;
    }

    public Bundle a()
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("mComponentName", this.b);
      localBundle.putString("mTitle", this.c);
      localBundle.putString("mUrl", this.d);
      localBundle.putString("mMd5", this.e);
      localBundle.putString("mTargetMd5", this.f);
      localBundle.putString("mReqClz", this.a);
      localBundle.putStringArray("succUrls", this.g);
      localBundle.putStringArray("faiUrls", this.i);
      localBundle.putStringArray("startUrls", this.j);
      localBundle.putStringArray("pauseUrls", this.k);
      localBundle.putStringArray("cancelUrls", this.l);
      localBundle.putStringArray("carryonUrls", this.h);
      localBundle.putBoolean("rich_notification", this.m);
      localBundle.putBoolean("mSilent", this.n);
      localBundle.putBoolean("mWifiOnly", this.o);
      return localBundle;
    }
  }

  class b extends Handler
  {
    b()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      try
      {
        Log.d("DownloadAgent.handleMessage(" + paramMessage.what + "): ", new Object[0]);
        switch (paramMessage.what)
        {
        case 4:
        default:
          super.handleMessage(paramMessage);
          return;
        case 1:
          if (c.a(c.this) == null)
            return;
          c.a(c.this).a();
          return;
        case 3:
        case 5:
        case 2:
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.d("DownloadAgent.handleMessage(" + paramMessage.what + "): " + localException.getMessage(), new Object[0]);
        return;
      }
      if (c.a(c.this) != null)
      {
        c.a(c.this).a(paramMessage.arg1);
        return;
        c.c(c.this).unbindService(c.b(c.this));
        if (c.a(c.this) != null)
        {
          if ((paramMessage.arg1 == 1) || (paramMessage.arg1 == 3) || (paramMessage.arg1 == 5))
          {
            c.a(c.this).a(paramMessage.arg1, paramMessage.arg2, paramMessage.getData().getString("filename"));
            return;
          }
          c.a(c.this).a(0, 0, null);
          Log.d("DownloadAgent.handleMessage(DownloadingService.DOWNLOAD_COMPLETE_FAIL): ", new Object[0]);
          return;
          c.a(c.this).b(paramMessage.arg1);
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.download.c
 * JD-Core Version:    0.6.2
 */