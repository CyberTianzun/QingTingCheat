package cn.com.iresearch.mapptracker.fm.a.e;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class e
{
  private static SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private String a;
  private String b;
  private Class<?> c;
  private Field d;
  private Method e;
  private Method f;

  public static void b()
  {
  }

  private static java.util.Date c(String paramString)
  {
    if (paramString != null)
      try
      {
        java.util.Date localDate = g.parse(paramString);
        return localDate;
      }
      catch (ParseException localParseException)
      {
        localParseException.printStackTrace();
      }
    return null;
  }

  public final <T> T a(Object paramObject)
  {
    if ((paramObject != null) && (this.e != null));
    try
    {
      Object localObject = this.e.invoke(paramObject, new Object[0]);
      return localObject;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
  }

  public final void a(Class<?> paramClass)
  {
    this.c = paramClass;
  }

  public final void a(Object paramObject1, Object paramObject2)
  {
    if ((this.f != null) && (paramObject2 != null))
    {
      while (true)
      {
        try
        {
          if (this.c == String.class)
          {
            Method localMethod7 = this.f;
            Object[] arrayOfObject7 = new Object[1];
            arrayOfObject7[0] = paramObject2.toString();
            localMethod7.invoke(paramObject1, arrayOfObject7);
            return;
          }
          if ((this.c != Integer.TYPE) && (this.c != Integer.class))
            break;
          Method localMethod1 = this.f;
          Object[] arrayOfObject1 = new Object[1];
          if (paramObject2 == null)
          {
            i = null.intValue();
            arrayOfObject1[0] = Integer.valueOf(i);
            localMethod1.invoke(paramObject1, arrayOfObject1);
            return;
          }
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          return;
        }
        int i = Integer.parseInt(paramObject2.toString());
      }
      if ((this.c == Float.TYPE) || (this.c == Float.class))
      {
        Method localMethod2 = this.f;
        Object[] arrayOfObject2 = new Object[1];
        if (paramObject2 == null);
        for (float f1 = null.floatValue(); ; f1 = Float.parseFloat(paramObject2.toString()))
        {
          arrayOfObject2[0] = Float.valueOf(f1);
          localMethod2.invoke(paramObject1, arrayOfObject2);
          return;
        }
      }
      if ((this.c == Double.TYPE) || (this.c == Double.class))
      {
        Method localMethod3 = this.f;
        Object[] arrayOfObject3 = new Object[1];
        if (paramObject2 == null);
        for (double d1 = null.doubleValue(); ; d1 = Double.parseDouble(paramObject2.toString()))
        {
          arrayOfObject3[0] = Double.valueOf(d1);
          localMethod3.invoke(paramObject1, arrayOfObject3);
          return;
        }
      }
      if ((this.c == Long.TYPE) || (this.c == Long.class))
      {
        Method localMethod4 = this.f;
        Object[] arrayOfObject4 = new Object[1];
        if (paramObject2 == null);
        for (long l = null.longValue(); ; l = Long.parseLong(paramObject2.toString()))
        {
          arrayOfObject4[0] = Long.valueOf(l);
          localMethod4.invoke(paramObject1, arrayOfObject4);
          return;
        }
      }
      if ((this.c == java.util.Date.class) || (this.c == java.sql.Date.class))
      {
        Method localMethod5 = this.f;
        Object[] arrayOfObject5 = new Object[1];
        java.util.Date localDate = null;
        if (paramObject2 == null);
        while (true)
        {
          arrayOfObject5[0] = localDate;
          localMethod5.invoke(paramObject1, arrayOfObject5);
          return;
          localDate = c(paramObject2.toString());
        }
      }
      if ((this.c == Boolean.TYPE) || (this.c == Boolean.class))
      {
        Method localMethod6 = this.f;
        Object[] arrayOfObject6 = new Object[1];
        if (paramObject2 == null);
        for (boolean bool = null.booleanValue(); ; bool = "1".equals(paramObject2.toString()))
        {
          arrayOfObject6[0] = Boolean.valueOf(bool);
          localMethod6.invoke(paramObject1, arrayOfObject6);
          return;
        }
      }
      this.f.invoke(paramObject1, new Object[] { paramObject2 });
      return;
    }
    try
    {
      this.d.setAccessible(true);
      this.d.set(paramObject1, paramObject2);
      return;
    }
    catch (Exception localException1)
    {
    }
  }

  public final void a(String paramString)
  {
    this.a = paramString;
  }

  public final void a(Field paramField)
  {
    this.d = paramField;
  }

  public final void a(Method paramMethod)
  {
    this.e = paramMethod;
  }

  public final void b(String paramString)
  {
    this.b = paramString;
  }

  public final void b(Method paramMethod)
  {
    this.f = paramMethod;
  }

  public final String c()
  {
    return this.a;
  }

  public final String d()
  {
    return this.b;
  }

  public final Class<?> e()
  {
    return this.c;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.a.e.e
 * JD-Core Version:    0.6.2
 */