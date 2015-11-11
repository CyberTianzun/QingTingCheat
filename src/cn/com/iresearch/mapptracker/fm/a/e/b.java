package cn.com.iresearch.mapptracker.fm.a.e;

import java.text.SimpleDateFormat;

public final class b
{
  private static SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private String a;
  private Object b;

  public b()
  {
  }

  public b(String paramString, Object paramObject)
  {
    this.a = paramString;
    this.b = paramObject;
  }

  public final String a()
  {
    return this.a;
  }

  public final Object b()
  {
    if (((this.b instanceof java.util.Date)) || ((this.b instanceof java.sql.Date)))
      return c.format(this.b);
    return this.b;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.a.e.b
 * JD-Core Version:    0.6.2
 */