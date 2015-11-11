package com.tencent.mm.sdk.c;

import android.net.Uri;
import android.provider.BaseColumns;

public final class a
{
  public static final class a
  {
    public static Object a(int paramInt, String paramString)
    {
      switch (paramInt)
      {
      default:
      case 1:
      case 2:
      case 4:
      case 5:
      case 6:
        try
        {
          com.tencent.mm.sdk.b.a.a("MicroMsg.SDK.PluginProvider.Resolver", "unknown type");
          break label82;
          return Integer.valueOf(paramString);
          return Long.valueOf(paramString);
          return Boolean.valueOf(paramString);
          return Float.valueOf(paramString);
          Double localDouble = Double.valueOf(paramString);
          return localDouble;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          label82: paramString = null;
        }
      case 3:
      }
      return paramString;
    }
  }

  public static final class b
    implements BaseColumns
  {
    public static final Uri CONTENT_URI = Uri.parse("content://com.tencent.mm.sdk.plugin.provider/sharedpref");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.c.a
 * JD-Core Version:    0.6.2
 */