package com.taobao.newxp;

import com.taobao.newxp.common.Category.a;
import java.util.Set;

public enum b
{
  static
  {
    b[] arrayOfb = new b[4];
    arrayOfb[0] = a;
    arrayOfb[1] = b;
    arrayOfb[2] = c;
    arrayOfb[3] = d;
  }

  public static b a(String paramString)
  {
    try
    {
      String str = paramString.split("\\.")[0];
      for (b localb : values())
      {
        boolean bool = localb.toString().equals(str);
        if (bool)
          return localb;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static b a(String paramString, Set<Category.a> paramSet)
  {
    int i = 1;
    try
    {
      String[] arrayOfString = paramString.split("\\.");
      String str = arrayOfString[0];
      if ((arrayOfString.length > i) && (paramSet != null))
        while (i < arrayOfString.length)
        {
          paramSet.add(Category.a.a(arrayOfString[i]));
          i++;
        }
      for (b localb : values())
      {
        boolean bool = localb.toString().equals(str);
        if (bool)
          return localb;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.b
 * JD-Core Version:    0.6.2
 */