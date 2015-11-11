package com.taobao.munion.view.webview.windvane;

import java.util.regex.Pattern;

public class k
{
  private static final String[] a = { "wv_hybrid:", "mraid:", "ssp:" };
  private static final Pattern b = Pattern.compile("hybrid://(.+?):(.+?)/(.+?)(\\?(.*?))?");
  private static final Pattern c = Pattern.compile("mraid://(.+?):(.+?)/(.+?)(\\?(.*?))?");
  private static final Pattern d = Pattern.compile("ssp://(.+?):(.+?)/(.+?)(\\?(.*?))?");

  public static boolean a(String paramString)
  {
    String[] arrayOfString = a;
    int i = arrayOfString.length;
    for (int j = 0; ; j++)
    {
      boolean bool = false;
      if (j < i)
      {
        if (arrayOfString[j].equals(paramString))
          bool = true;
      }
      else
        return bool;
    }
  }

  public static Pattern b(String paramString)
  {
    if ("wv_hybrid:".equals(paramString))
      return b;
    if ("mraid:".equals(paramString))
      return c;
    if ("ssp:".equals(paramString))
      return d;
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.k
 * JD-Core Version:    0.6.2
 */