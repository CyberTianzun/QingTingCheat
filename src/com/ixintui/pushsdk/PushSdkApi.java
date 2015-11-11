package com.ixintui.pushsdk;

import android.content.Context;
import com.ixintui.plugin.IPushSdkApi;
import com.ixintui.pushsdk.a.a;
import java.util.List;

public class PushSdkApi
{
  private static IPushSdkApi api;

  public static void addTags(Context paramContext, List paramList)
  {
    if (check(paramContext))
      api.addTags(paramContext, paramList);
  }

  private static boolean check(Context paramContext)
  {
    if (api == null)
      api = (IPushSdkApi)a.a(paramContext, "com.ixintui.pushsdk.PushSdkApiImpl");
    return api != null;
  }

  public static void configure(Context paramContext, String paramString)
  {
    if (check(paramContext))
      api.configure(paramContext, paramString);
  }

  public static void deleteTags(Context paramContext, List paramList)
  {
    if (check(paramContext))
      api.deleteTags(paramContext, paramList);
  }

  public static void enableStat(Context paramContext, boolean paramBoolean)
  {
    if (check(paramContext))
      api.enableStat(paramContext, paramBoolean);
  }

  public static void isSuspended(Context paramContext)
  {
    if (check(paramContext))
      api.isSuspended(paramContext);
  }

  public static void listTags(Context paramContext)
  {
    if (check(paramContext))
      api.listTags(paramContext);
  }

  public static void register(Context paramContext, int paramInt, String paramString1, String paramString2)
  {
    if (check(paramContext))
      api.register(paramContext, paramInt, paramString1, paramString2);
  }

  public static void resumePush(Context paramContext)
  {
    if (check(paramContext))
      api.resumePush(paramContext);
  }

  public static void suspendPush(Context paramContext)
  {
    if (check(paramContext))
      api.suspendPush(paramContext);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.pushsdk.PushSdkApi
 * JD-Core Version:    0.6.2
 */