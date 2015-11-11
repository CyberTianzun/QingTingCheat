package cn.com.iresearch.mapptracker.fm.a.e;

import cn.com.iresearch.mapptracker.fm.a.b.b;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public final class f
{
  private static final HashMap<String, f> g = new HashMap();
  public final LinkedHashMap<String, e> a = new LinkedHashMap();
  public final HashMap<String, c> b = new HashMap();
  private String c;
  private a d;
  private HashMap<String, d> e = new HashMap();
  private boolean f;

  public static f a(Class<?> paramClass)
  {
    if (paramClass == null)
      throw new b("table info get error,because the clazz is null");
    Object localObject = (f)g.get(paramClass.getName());
    f localf;
    cn.com.iresearch.mapptracker.fm.a.a.a.e locale;
    String str;
    Iterator localIterator3;
    label190: Iterator localIterator2;
    label220: Iterator localIterator1;
    if (localObject == null)
    {
      localf = new f();
      locale = (cn.com.iresearch.mapptracker.fm.a.a.a.e)paramClass.getAnnotation(cn.com.iresearch.mapptracker.fm.a.a.a.e.class);
      if ((locale != null) && (locale.a().trim().length() != 0))
        break label307;
      str = paramClass.getName().replace('.', '_');
      localf.c = str;
      paramClass.getName();
      Field localField = cn.com.iresearch.mapptracker.fm.dao.a.getPrimaryKeyField(paramClass);
      if (localField == null)
        break label318;
      a locala = new a();
      locala.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(localField));
      localField.getName();
      a.b();
      locala.b(cn.com.iresearch.mapptracker.fm.a.c.a.b(paramClass, localField));
      locala.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(paramClass, localField));
      locala.a(localField.getType());
      localf.d = locala;
      List localList1 = cn.com.iresearch.mapptracker.fm.dao.a.getPropertyList(paramClass);
      if (localList1 != null)
      {
        localIterator3 = localList1.iterator();
        if (localIterator3.hasNext())
          break label347;
      }
      List localList2 = cn.com.iresearch.mapptracker.fm.dao.a.getManyToOneList(paramClass);
      if (localList2 != null)
      {
        localIterator2 = localList2.iterator();
        if (localIterator2.hasNext())
          break label382;
      }
      List localList3 = cn.com.iresearch.mapptracker.fm.dao.a.getOneToManyList(paramClass);
      if (localList3 != null)
        localIterator1 = localList3.iterator();
    }
    while (true)
    {
      if (!localIterator1.hasNext())
      {
        g.put(paramClass.getName(), localf);
        localObject = localf;
        if (localObject != null)
          break label452;
        throw new b("the class[" + paramClass + "]'s table is null");
        label307: str = locale.a();
        break;
        label318: throw new b("the class[" + paramClass + "]'s idField is null");
        label347: e locale1 = (e)localIterator3.next();
        if (locale1 == null)
          break label190;
        localf.a.put(locale1.c(), locale1);
        break label190;
        label382: c localc = (c)localIterator2.next();
        if (localc == null)
          break label220;
        localf.b.put(localc.c(), localc);
        break label220;
      }
      d locald = (d)localIterator1.next();
      if (locald != null)
        localf.e.put(locald.c(), locald);
    }
    label452: return localObject;
  }

  public final String a()
  {
    return this.c;
  }

  public final a b()
  {
    return this.d;
  }

  public final boolean c()
  {
    return this.f;
  }

  public final void d()
  {
    this.f = true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.a.e.f
 * JD-Core Version:    0.6.2
 */