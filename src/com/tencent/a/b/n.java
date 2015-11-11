package com.tencent.a.b;

public final class n
{
  public byte[] a;
  public String b = "GBK";

  public final String toString()
  {
    try
    {
      String str = new String(this.a, this.b);
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.n
 * JD-Core Version:    0.6.2
 */