package com.sina.weibo.sdk.component;

import android.content.Context;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import java.util.HashMap;
import java.util.Map;

public class WeiboCallbackManager
{
  private static WeiboCallbackManager sInstance;
  private Context mContext;
  private Map<String, WeiboAuthListener> mWeiboAuthListenerMap;
  private Map<String, WidgetRequestParam.WidgetRequestCallback> mWidgetRequestCallbackMap;

  private WeiboCallbackManager(Context paramContext)
  {
    this.mContext = paramContext;
    this.mWeiboAuthListenerMap = new HashMap();
    this.mWidgetRequestCallbackMap = new HashMap();
  }

  public static WeiboCallbackManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new WeiboCallbackManager(paramContext);
      WeiboCallbackManager localWeiboCallbackManager = sInstance;
      return localWeiboCallbackManager;
    }
    finally
    {
    }
  }

  public String genCallbackKey()
  {
    return String.valueOf(System.currentTimeMillis());
  }

  public WeiboAuthListener getWeiboAuthListener(String paramString)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool);
      for (WeiboAuthListener localWeiboAuthListener = null; ; localWeiboAuthListener = (WeiboAuthListener)this.mWeiboAuthListenerMap.get(paramString))
        return localWeiboAuthListener;
    }
    finally
    {
    }
  }

  public WidgetRequestParam.WidgetRequestCallback getWidgetRequestCallback(String paramString)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool);
      for (WidgetRequestParam.WidgetRequestCallback localWidgetRequestCallback = null; ; localWidgetRequestCallback = (WidgetRequestParam.WidgetRequestCallback)this.mWidgetRequestCallbackMap.get(paramString))
        return localWidgetRequestCallback;
    }
    finally
    {
    }
  }

  public void removeWeiboAuthListener(String paramString)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool);
      while (true)
      {
        return;
        this.mWeiboAuthListenerMap.remove(paramString);
      }
    }
    finally
    {
    }
  }

  public void removeWidgetRequestCallback(String paramString)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool);
      while (true)
      {
        return;
        this.mWidgetRequestCallbackMap.remove(paramString);
      }
    }
    finally
    {
    }
  }

  public void setWeiboAuthListener(String paramString, WeiboAuthListener paramWeiboAuthListener)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if ((bool) || (paramWeiboAuthListener == null));
      while (true)
      {
        return;
        this.mWeiboAuthListenerMap.put(paramString, paramWeiboAuthListener);
      }
    }
    finally
    {
    }
  }

  public void setWidgetRequestCallback(String paramString, WidgetRequestParam.WidgetRequestCallback paramWidgetRequestCallback)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if ((bool) || (paramWidgetRequestCallback == null));
      while (true)
      {
        return;
        this.mWidgetRequestCallbackMap.put(paramString, paramWidgetRequestCallback);
      }
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.component.WeiboCallbackManager
 * JD-Core Version:    0.6.2
 */