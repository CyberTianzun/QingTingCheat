package cn.com.iresearch.mapptracker.fm.a.c;

import cn.com.iresearch.mapptracker.fm.a.a.a.b;
import cn.com.iresearch.mapptracker.fm.a.a.a.c;
import cn.com.iresearch.mapptracker.fm.a.a.a.d;
import cn.com.iresearch.mapptracker.fm.a.a.a.f;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public final class a
{
  static
  {
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  public static String a(Field paramField)
  {
    d locald = (d)paramField.getAnnotation(d.class);
    if ((locald != null) && (locald.a().trim().length() != 0))
      return locald.a();
    b localb = (b)paramField.getAnnotation(b.class);
    if ((localb != null) && (localb.a().trim().length() != 0))
      return localb.a();
    c localc = (c)paramField.getAnnotation(c.class);
    if ((localc != null) && (localc.a() != null) && (localc.a().trim().length() != 0))
      return localc.a();
    cn.com.iresearch.mapptracker.fm.a.a.a.a locala = (cn.com.iresearch.mapptracker.fm.a.a.a.a)paramField.getAnnotation(cn.com.iresearch.mapptracker.fm.a.a.a.a.class);
    if ((locala != null) && (locala.a().trim().length() != 0))
      return locala.a();
    return paramField.getName();
  }

  private static Method a(Class<?> paramClass, String paramString)
  {
    String str = "is" + paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
    if (a(paramString));
    while (true)
    {
      try
      {
        Method localMethod = paramClass.getDeclaredMethod(paramString, new Class[0]);
        return localMethod;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        return null;
      }
      paramString = str;
    }
  }

  public static Method a(Class<?> paramClass, Field paramField)
  {
    String str = paramField.getName();
    Class localClass1 = paramField.getType();
    Class localClass2 = Boolean.TYPE;
    Method localMethod = null;
    if (localClass1 == localClass2)
      localMethod = a(paramClass, str);
    if (localMethod == null)
      localMethod = b(paramClass, str);
    return localMethod;
  }

  private static boolean a(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0));
    while ((!paramString.startsWith("is")) || (Character.isLowerCase(paramString.charAt(2))))
      return false;
    return true;
  }

  public static String b(Field paramField)
  {
    d locald = (d)paramField.getAnnotation(d.class);
    if ((locald != null) && (locald.b().trim().length() != 0))
      return locald.b();
    return null;
  }

  private static Method b(Class<?> paramClass, String paramString)
  {
    String str = "get" + paramString.substring(0, 1).toUpperCase() + paramString.substring(1);
    try
    {
      Method localMethod = paramClass.getDeclaredMethod(str, new Class[0]);
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    return null;
  }

  public static Method b(Class<?> paramClass, Field paramField)
  {
    String str1 = paramField.getName();
    String str2 = "set" + str1.substring(0, 1).toUpperCase() + str1.substring(1);
    try
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = paramField.getType();
      Method localMethod = paramClass.getDeclaredMethod(str2, arrayOfClass);
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      if (paramField.getType() == Boolean.TYPE)
        return c(paramClass, paramField);
    }
    return null;
  }

  private static Method c(Class<?> paramClass, Field paramField)
  {
    String str1 = paramField.getName();
    String str2 = "set" + str1.substring(0, 1).toUpperCase() + str1.substring(1);
    if (a(paramField.getName()))
      str2 = "set" + str1.substring(2, 3).toUpperCase() + str1.substring(3);
    try
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = paramField.getType();
      Method localMethod = paramClass.getDeclaredMethod(str2, arrayOfClass);
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    return null;
  }

  public static boolean c(Field paramField)
  {
    return paramField.getAnnotation(f.class) != null;
  }

  public static boolean d(Field paramField)
  {
    return paramField.getAnnotation(b.class) != null;
  }

  public static boolean e(Field paramField)
  {
    return paramField.getAnnotation(c.class) != null;
  }

  public static boolean f(Field paramField)
  {
    Class localClass = paramField.getType();
    return (localClass.equals(String.class)) || (localClass.equals(Integer.class)) || (localClass.equals(Byte.class)) || (localClass.equals(Long.class)) || (localClass.equals(Double.class)) || (localClass.equals(Float.class)) || (localClass.equals(Character.class)) || (localClass.equals(Short.class)) || (localClass.equals(Boolean.class)) || (localClass.equals(java.util.Date.class)) || (localClass.equals(java.util.Date.class)) || (localClass.equals(java.sql.Date.class)) || (localClass.isPrimitive());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.a.c.a
 * JD-Core Version:    0.6.2
 */