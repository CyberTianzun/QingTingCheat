package com.ixintui.push;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.ixintui.plugin.IMediateService;

public class MediateService extends Service
{
  private IMediateService a;
  private volatile Looper b;
  private volatile MediateService.ServiceHandler c;

  private static boolean a(Intent paramIntent)
  {
    boolean bool1;
    if (paramIntent != null)
    {
      boolean bool2 = paramIntent.getBooleanExtra("st", false);
      bool1 = false;
      if (!bool2);
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    HandlerThread localHandlerThread = new HandlerThread("IntentService[MediateService]");
    localHandlerThread.start();
    this.b = localHandlerThread.getLooper();
    this.c = new MediateService.ServiceHandler(this, this.b);
  }

  public void onDestroy()
  {
    this.b.quit();
    super.onDestroy();
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Message localMessage = this.c.obtainMessage();
    localMessage.arg1 = paramInt2;
    localMessage.obj = paramIntent;
    this.c.sendMessage(localMessage);
    if (a(paramIntent))
      return 1;
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.push.MediateService
 * JD-Core Version:    0.6.2
 */