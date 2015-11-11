package com.tencent.connect.auth;

import com.tencent.tauth.IUiListener;
import java.util.HashMap;

public class AuthMap
{
  private static int b;
  public static AuthMap sInstance;
  public final String KEY_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  public HashMap<String, Auth> authMap = new HashMap();

  static
  {
    if (!AuthMap.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      a = bool;
      b = 0;
      return;
    }
  }

  private String a(String paramString1, String paramString2)
  {
    int i = 0;
    if ((!a) && (paramString1.length() % 2 != 0))
      throw new AssertionError();
    StringBuilder localStringBuilder = new StringBuilder();
    int j = paramString2.length();
    int k = paramString1.length() / 2;
    int m = 0;
    while (i < k)
    {
      localStringBuilder.append((char)(Integer.parseInt(paramString1.substring(i * 2, 2 + i * 2), 16) ^ paramString2.charAt(m)));
      m = (m + 1) % j;
      i++;
    }
    return localStringBuilder.toString();
  }

  public static AuthMap getInstance()
  {
    if (sInstance == null)
      sInstance = new AuthMap();
    return sInstance;
  }

  public static int getSerial()
  {
    int i = 1 + b;
    b = i;
    return i;
  }

  public String decode(String paramString1, String paramString2)
  {
    return a(paramString1, paramString2);
  }

  public Auth get(String paramString)
  {
    return (Auth)this.authMap.get(paramString);
  }

  public String makeKey()
  {
    int i = (int)Math.ceil(3.0D + 20.0D * Math.random());
    char[] arrayOfChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    int j = arrayOfChar.length;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int k = 0; k < i; k++)
      localStringBuffer.append(arrayOfChar[((int)(Math.random() * j))]);
    return localStringBuffer.toString();
  }

  public void remove(String paramString)
  {
    this.authMap.remove(paramString);
  }

  public String set(Auth paramAuth)
  {
    int i = getSerial();
    try
    {
      this.authMap.put("" + i, paramAuth);
      return "" + i;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        localThrowable.printStackTrace();
    }
  }

  public static class Auth
  {
    public AuthDialog dialog;
    public String key;
    public IUiListener listener;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.auth.AuthMap
 * JD-Core Version:    0.6.2
 */