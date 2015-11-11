package com.umeng.message;

import android.content.Context;
import com.umeng.message.entity.UMessage;

public abstract interface UHandler
{
  public abstract void handleMessage(Context paramContext, UMessage paramUMessage);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UHandler
 * JD-Core Version:    0.6.2
 */