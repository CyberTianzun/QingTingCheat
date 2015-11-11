package org.android.agoo.client;

import android.content.Context;
import android.text.TextUtils;

public class IppFacade
{
  private static String a = IppFacade.class.getSimpleName();
  private static final String b = "org.agoo.android.intent.action.PING2";

  public static void performProtectOnlyOnce(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return;
      try
      {
        if (!TextUtils.isEmpty(paramContext.getPackageName()))
        {
          new Thread(new IppFacade.1(paramContext)).start();
          return;
        }
      }
      catch (Throwable localThrowable)
      {
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.client.IppFacade
 * JD-Core Version:    0.6.2
 */