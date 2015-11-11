package com.tencent.connect.avatar;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class a
{
  public static int a(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.avatar.a
 * JD-Core Version:    0.6.2
 */