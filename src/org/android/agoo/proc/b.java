package org.android.agoo.proc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.umeng.message.proguard.Q;
import com.umeng.message.proguard.U;
import com.umeng.message.proguard.aS;
import org.android.agoo.client.IntentHelper;

public abstract class b extends Service
  implements Handler.Callback
{
  private static final int b = 0;
  private static final String c = "cockroach";
  private static final String d = "cockroach-PPreotect";
  private static final String e = "pack";
  private static final String f = "SEService";
  private static final int g = 0;
  private static final int h = 1;
  private static final int i = 2;
  private static final int j = 3;
  private static final int k = 4;
  private static final int l = -1;
  protected volatile Context a;
  private volatile HandlerThread m = null;
  private volatile Handler n = null;
  private volatile boolean o = false;

  private static final int a(int paramInt1, int paramInt2)
  {
    return 0x0 | (paramInt1 & 0xFF | (0xFFFF & paramInt2) << 16);
  }

  private static final void a(Context paramContext)
  {
    if (paramContext != null);
    try
    {
      if (!ServiceProtect.a())
        ServiceProtect.a(paramContext);
      String str1 = paramContext.getPackageName();
      String str2 = paramContext.getClass().getName();
      int i1 = a(2, 600);
      String str3 = IntentHelper.getAgooCockroach(paramContext);
      if (ServiceProtect.a())
      {
        Q.c("SEService", "registerNotKill--->[current-thread-name:" + Thread.currentThread().getName() + "][action:" + str3 + "]");
        ServiceProtect.a(paramContext, str1, str2, str3, i1);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  private final void f()
  {
    if (this.a != null)
    {
      AlarmManager localAlarmManager = (AlarmManager)this.a.getSystemService("alarm");
      Intent localIntent = new Intent(IntentHelper.getAgooStart(this.a));
      localIntent.setPackage(this.a.getPackageName());
      localIntent.putExtra("method", "start");
      localIntent.addFlags(32);
      PendingIntent localPendingIntent = PendingIntent.getBroadcast(this.a, 0, localIntent, 134217728);
      localAlarmManager.set(1, 300000L + System.currentTimeMillis(), localPendingIntent);
    }
  }

  private static final void g()
  {
    try
    {
      Q.c("SEService", "unregisterNotKill--->[current-thread-name:" + Thread.currentThread().getName() + "]");
      if (ServiceProtect.a())
        ServiceProtect.b();
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  protected final void a()
  {
    try
    {
      this.o = false;
      if (this.n != null)
        this.n.sendEmptyMessage(1);
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  protected abstract void a(Intent paramIntent, int paramInt1, int paramInt2);

  protected abstract void b();

  protected abstract void c();

  protected abstract void d();

  protected abstract void e();

  public boolean handleMessage(Message paramMessage)
  {
    try
    {
      switch (paramMessage.what)
      {
      case 0:
        this.o = true;
        a(this.a);
        return true;
      case 1:
        g();
        stopSelf();
        return true;
      case 2:
        a(this.a);
        a((Intent)paramMessage.obj, paramMessage.arg1, paramMessage.arg2);
        return true;
      case 3:
        a(this.a);
        e();
        return true;
      case 4:
        a(this.a);
        d();
      case -1:
      }
      return true;
    }
    catch (Throwable localThrowable)
    {
    }
    return true;
  }

  public boolean hasComeFromCock(Intent paramIntent)
  {
    if (paramIntent == null);
    try
    {
      Q.c("SEService", "hasComeFromCock[intent==null]");
      return false;
      String str1 = paramIntent.getAction();
      String str2 = IntentHelper.getAgooCockroach(this.a);
      if ((TextUtils.isEmpty(str1)) || (!TextUtils.equals(str1, str2)))
      {
        Q.c("SEService", "hasComeFromCock[action==null]or[action!=" + str2 + "]");
        return false;
      }
      String str3 = paramIntent.getStringExtra("cockroach");
      if ((TextUtils.isEmpty(str3)) || (!TextUtils.equals(str3, "cockroach-PPreotect")))
      {
        Q.c("SEService", "hasComeFromCock[cockroach==null]or[cockroach!=" + str3 + "]");
        return false;
      }
      String str4 = paramIntent.getStringExtra("pack");
      if ((TextUtils.isEmpty(str4)) || (!TextUtils.equals(str4, getPackageName())))
      {
        Q.c("SEService", "hasComeFromCock[pack==null] or [" + str4 + "!=" + getPackageName() + "]");
        return false;
      }
      Q.c("SEService", "hasComeFromCock[" + str1 + "] [" + str4 + "==" + getPackageName() + "]");
    }
    catch (Throwable localThrowable)
    {
    }
    return true;
  }

  public final void onCreate()
  {
    super.onCreate();
    this.a = this;
    try
    {
      Q.a(this.a);
      Q.c("SEService", "onCreate()....");
      this.m = new HandlerThread("se-service");
      this.m.start();
      this.n = new Handler(this.m.getLooper(), this);
      b();
      return;
    }
    catch (Throwable localThrowable)
    {
      Q.d("SEService", "se_service_handlerthread", localThrowable);
    }
  }

  public final void onDestroy()
  {
    try
    {
      Q.c("SEService", "onDestroy().....");
      if (this.o)
        f();
      while (true)
      {
        U.b(this.a);
        return;
        c();
        aS.a(this.a);
      }
    }
    catch (Throwable localThrowable)
    {
    }
  }

  public final int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    try
    {
      if (this.n == null)
      {
        a();
        return 1;
      }
      if (paramIntent == null)
      {
        this.n.sendEmptyMessage(4);
        return 1;
      }
      Message localMessage = Message.obtain();
      localMessage.arg1 = paramInt1;
      localMessage.arg2 = paramInt2;
      localMessage.obj = paramIntent;
      if (hasComeFromCock(paramIntent))
      {
        localMessage.what = 3;
        this.n.sendMessage(localMessage);
        return 1;
      }
      localMessage.what = 2;
      this.n.sendMessage(localMessage);
      return 1;
    }
    catch (Throwable localThrowable)
    {
    }
    return 1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.proc.b
 * JD-Core Version:    0.6.2
 */