package com.talkingdata.pingan.sdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ah
{
  private static final ExecutorService a = Executors.newSingleThreadExecutor();

  public static void a(Runnable paramRunnable)
  {
    a.execute(paramRunnable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.ah
 * JD-Core Version:    0.6.2
 */