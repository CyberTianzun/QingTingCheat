package u.aly;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class ac extends am
  implements p
{
  public ac()
  {
    a(System.currentTimeMillis());
    a(an.a);
  }

  public ac(String paramString)
  {
    this();
    a(paramString);
  }

  public ac(Throwable paramThrowable)
  {
    this();
    a(a(paramThrowable));
  }

  private String a(Throwable paramThrowable)
  {
    String str = null;
    if (paramThrowable == null)
      return null;
    try
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      paramThrowable.printStackTrace(localPrintWriter);
      for (Throwable localThrowable = paramThrowable.getCause(); ; localThrowable = localThrowable.getCause())
      {
        str = null;
        if (localThrowable == null)
          break;
        localThrowable.printStackTrace(localPrintWriter);
      }
      str = localStringWriter.toString();
      localPrintWriter.close();
      localStringWriter.close();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  public ac a(boolean paramBoolean)
  {
    if (paramBoolean);
    for (an localan = an.a; ; localan = an.b)
    {
      a(localan);
      return this;
    }
  }

  public void a(bf parambf, String paramString)
  {
    av localav;
    if (parambf.s() > 0)
    {
      Iterator localIterator = parambf.u().iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        localav = (av)localIterator.next();
      }
      while (!paramString.equals(localav.c()));
    }
    while (true)
    {
      if (localav == null)
      {
        localav = new av();
        localav.a(paramString);
        parambf.a(localav);
      }
      localav.a(this);
      return;
      localav = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.ac
 * JD-Core Version:    0.6.2
 */