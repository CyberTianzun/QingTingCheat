package com.taobao.newxp;

public enum a
{
  static
  {
    a[] arrayOfa = new a[3];
    arrayOfa[0] = a;
    arrayOfa[1] = b;
    arrayOfa[2] = c;
  }

  public static a a(String paramString)
  {
    for (a locala : values())
      if (locala.toString().equals(paramString))
        return locala;
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.a
 * JD-Core Version:    0.6.2
 */