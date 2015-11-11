package com.tencent.mm.a;

import android.util.Base64;
import javax.crypto.Cipher;

public final class a
{
  private Cipher a;

  public final String a(String paramString)
  {
    try
    {
      byte[] arrayOfByte = Base64.decode(paramString, 0);
      String str = new String(this.a.doFinal(arrayOfByte), "UTF8");
      return str;
    }
    catch (Exception localException)
    {
      return "[des]" + paramString + "|" + localException.toString();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.a.a
 * JD-Core Version:    0.6.2
 */