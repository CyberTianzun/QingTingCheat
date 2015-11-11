package u.aly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class a
{
  private final int a = 10;
  private final int b = 20;
  private final String c;
  private List<aq> d;
  private ar e;

  public a(String paramString)
  {
    this.c = paramString;
  }

  private boolean g()
  {
    ar localar = this.e;
    Object localObject;
    if (localar == null)
    {
      localObject = null;
      if (localar != null)
        break label205;
    }
    label205: for (int i = 0; ; i = localar.j())
    {
      String str = a(f());
      boolean bool1 = false;
      if (str != null)
      {
        boolean bool2 = str.equals(localObject);
        bool1 = false;
        if (!bool2)
        {
          if (localar == null)
            localar = new ar();
          localar.a(str);
          localar.a(System.currentTimeMillis());
          localar.a(i + 1);
          aq localaq = new aq();
          localaq.a(this.c);
          localaq.c(str);
          localaq.b((String)localObject);
          localaq.a(localar.f());
          if (this.d == null)
            this.d = new ArrayList(2);
          this.d.add(localaq);
          if (this.d.size() > 10)
            this.d.remove(0);
          this.e = localar;
          bool1 = true;
        }
      }
      return bool1;
      localObject = localar.c();
      break;
    }
  }

  public String a(String paramString)
  {
    if (paramString == null);
    String str;
    do
    {
      return null;
      str = paramString.trim();
    }
    while ((str.length() == 0) || ("0".equals(str)) || ("unknown".equals(str.toLowerCase(Locale.US))));
    return str;
  }

  public void a(List<aq> paramList)
  {
    this.d = paramList;
  }

  public void a(ar paramar)
  {
    this.e = paramar;
  }

  public void a(as paramas)
  {
    this.e = ((ar)paramas.d().get("mName"));
    List localList = paramas.j();
    if ((localList != null) && (localList.size() > 0))
    {
      if (this.d == null)
        this.d = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        aq localaq = (aq)localIterator.next();
        if (this.c.equals(localaq.a))
          this.d.add(localaq);
      }
    }
  }

  public boolean a()
  {
    return g();
  }

  public String b()
  {
    return this.c;
  }

  public boolean c()
  {
    return (this.e == null) || (this.e.j() <= 20);
  }

  public ar d()
  {
    return this.e;
  }

  public List<aq> e()
  {
    return this.d;
  }

  public abstract String f();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.a
 * JD-Core Version:    0.6.2
 */