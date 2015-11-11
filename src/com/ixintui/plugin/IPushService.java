package com.ixintui.plugin;

import android.app.Service;
import android.content.Intent;

public abstract interface IPushService
{
  public abstract void init(Service paramService);

  public abstract void onCreate();

  public abstract void onDestroy();

  public abstract int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.plugin.IPushService
 * JD-Core Version:    0.6.2
 */