package com.ixintui.plugin;

import android.content.Context;
import java.util.List;

public abstract interface IPushSdkApi
{
  public abstract void addTags(Context paramContext, List paramList);

  public abstract void configure(Context paramContext, String paramString);

  public abstract void deleteTags(Context paramContext, List paramList);

  public abstract void enableStat(Context paramContext, boolean paramBoolean);

  public abstract void isSuspended(Context paramContext);

  public abstract void listTags(Context paramContext);

  public abstract void register(Context paramContext, int paramInt, String paramString1, String paramString2);

  public abstract void resumePush(Context paramContext);

  public abstract void suspendPush(Context paramContext);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.plugin.IPushSdkApi
 * JD-Core Version:    0.6.2
 */