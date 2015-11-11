package com.umeng.message.tag;

import com.umeng.common.message.Log;

public class b
  implements a
{
  private static final String a = b.class.getName();
  private static int b = 256;

  public boolean a(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString.trim())))
      return false;
    if ((paramString != null) && (paramString.length() > b))
    {
      String str = a;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(b);
      Log.b(str, String.format("The length of %s exceeds allowed max length %i", arrayOfObject));
      return false;
    }
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.tag.b
 * JD-Core Version:    0.6.2
 */