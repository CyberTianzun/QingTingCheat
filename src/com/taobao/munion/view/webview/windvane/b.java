package com.taobao.munion.view.webview.windvane;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class b
{
  private static a a;

  public static <T> c<T> a(Class<T> paramClass)
  {
    return new c(paramClass);
  }

  public static <T> c<T> a(String paramString)
    throws b.b.a
  {
    try
    {
      c localc = new c(Class.forName(paramString));
      return localc;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      b(new b.b.a(localClassNotFoundException));
    }
    return new c(null);
  }

  public static void a(a parama)
  {
    a = parama;
  }

  private static void b(b.b.a parama)
    throws b.b.a
  {
    if ((a == null) || (!a.a(parama)))
      throw parama;
  }

  public static abstract interface a
  {
    public abstract boolean a(b.b.a parama);
  }

  public static abstract class b
  {
    public static class a extends Throwable
    {
      private static final long d = 1L;
      private Class<?> a;
      private String b;
      private String c;

      public a(Exception paramException)
      {
        super();
      }

      public a(String paramString)
      {
        super();
      }

      public Class<?> a()
      {
        return this.a;
      }

      public void a(Class<?> paramClass)
      {
        this.a = paramClass;
      }

      public void a(String paramString)
      {
        this.c = paramString;
      }

      public String b()
      {
        return this.c;
      }

      public void b(String paramString)
      {
        this.b = paramString;
      }

      public String c()
      {
        return this.b;
      }

      public String toString()
      {
        if (getCause() != null)
          return getClass().getName() + ": " + getCause();
        return super.toString();
      }
    }
  }

  public static class c<C>
  {
    protected Class<C> a;

    public c(Class<C> paramClass)
    {
      this.a = paramClass;
    }

    public b.d a(Class<?>[] paramArrayOfClass)
      throws b.b.a
    {
      return new b.d(this.a, paramArrayOfClass);
    }

    public b.e<C, Object> a(String paramString)
      throws b.b.a
    {
      return new b.e(this.a, paramString, 8);
    }

    public b.f a(String paramString, Class<?>[] paramArrayOfClass)
      throws b.b.a
    {
      return new b.f(this.a, paramString, paramArrayOfClass, 8);
    }

    public Class<C> a()
    {
      return this.a;
    }

    public b.e<C, Object> b(String paramString)
      throws b.b.a
    {
      return new b.e(this.a, paramString, 0);
    }

    public b.f b(String paramString, Class<?>[] paramArrayOfClass)
      throws b.b.a
    {
      return new b.f(this.a, paramString, paramArrayOfClass, 0);
    }
  }

  public static class d
  {
    protected Constructor<?> a;

    d(Class<?> paramClass, Class<?>[] paramArrayOfClass)
      throws b.b.a
    {
      if (paramClass == null)
        return;
      try
      {
        this.a = paramClass.getDeclaredConstructor(paramArrayOfClass);
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        b.b.a locala = new b.b.a(localNoSuchMethodException);
        locala.a(paramClass);
        b.a(locala);
      }
    }

    public Object a(Object[] paramArrayOfObject)
      throws IllegalArgumentException
    {
      this.a.setAccessible(true);
      try
      {
        Object localObject = this.a.newInstance(paramArrayOfObject);
        return localObject;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }
  }

  public static class e<C, T>
  {
    private Object a;
    private final Field b;

    e(Class<C> paramClass, String paramString, int paramInt)
      throws b.b.a
    {
      if (paramClass == null)
      {
        this.b = null;
        return;
      }
      try
      {
        this.a = null;
        localField = paramClass.getDeclaredField(paramString);
        if ((paramInt > 0) && ((paramInt & localField.getModifiers()) != paramInt))
          b.a(new b.b.a(localField + " does not match modifiers: " + paramInt));
        localField.setAccessible(true);
        return;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        b.b.a locala = new b.b.a(localNoSuchFieldException);
        locala.a(paramClass);
        locala.b(paramString);
        b.a(locala);
        return;
      }
      finally
      {
        this.b = localField;
      }
    }

    public <T2> e<C, T2> a(Class<?> paramClass)
      throws b.b.a
    {
      if ((this.b != null) && (!paramClass.isAssignableFrom(this.b.getType())))
        b.a(new b.b.a(new ClassCastException(this.b + " is not of type " + paramClass)));
      return this;
    }

    public e<C, T> a(String paramString)
      throws b.b.a
    {
      try
      {
        e locale = b(Class.forName(paramString));
        return locale;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        b.a(new b.b.a(localClassNotFoundException));
      }
      return this;
    }

    public T a()
    {
      try
      {
        Object localObject = this.b.get(this.a);
        return localObject;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        localIllegalAccessException.printStackTrace();
      }
      return null;
    }

    public void a(e.b<?> paramb)
    {
      Object localObject = a();
      if (localObject == null)
        throw new IllegalStateException("Cannot hijack null");
      a(e.a(localObject, paramb, localObject.getClass().getInterfaces()));
    }

    public void a(Object paramObject)
    {
      try
      {
        this.b.set(this.a, paramObject);
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        localIllegalAccessException.printStackTrace();
      }
    }

    public <T2> e<C, T2> b(Class<T2> paramClass)
      throws b.b.a
    {
      if ((this.b != null) && (!paramClass.isAssignableFrom(this.b.getType())))
        b.a(new b.b.a(new ClassCastException(this.b + " is not of type " + paramClass)));
      return this;
    }

    public e<C, T> b(C paramC)
    {
      this.a = paramC;
      return this;
    }
  }

  public static class f
  {
    protected final Method a;

    f(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass, int paramInt)
      throws b.b.a
    {
      Method localMethod = null;
      if (paramClass == null)
      {
        this.a = null;
        return;
      }
      try
      {
        localMethod = paramClass.getDeclaredMethod(paramString, paramArrayOfClass);
        if ((paramInt > 0) && ((paramInt & localMethod.getModifiers()) != paramInt))
          b.a(new b.b.a(localMethod + " does not match modifiers: " + paramInt));
        localMethod.setAccessible(true);
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        b.b.a locala = new b.b.a(localNoSuchMethodException);
        locala.a(paramClass);
        locala.a(paramString);
        b.a(locala);
        return;
      }
      finally
      {
        this.a = localMethod;
      }
    }

    public Object a(Object paramObject, Object[] paramArrayOfObject)
      throws IllegalArgumentException
    {
      try
      {
        Object localObject = this.a.invoke(paramObject, paramArrayOfObject);
        return localObject;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }

    public Method a()
    {
      return this.a;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.b
 * JD-Core Version:    0.6.2
 */