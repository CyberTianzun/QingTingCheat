package com.sina.weibo.sdk.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection
{
  public static Object getByArray(Object paramObject, int paramInt)
  {
    return Array.get(paramObject, paramInt);
  }

  public static Object getProperty(Object paramObject, String paramString)
    throws Exception
  {
    return paramObject.getClass().getField(paramString).get(paramObject);
  }

  public static Object getStaticProperty(String paramString1, String paramString2)
    throws Exception
  {
    Class localClass = Class.forName(paramString1);
    return localClass.getField(paramString2).get(localClass);
  }

  public static Object invokeMethod(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
  {
    try
    {
      Object localObject = paramObject.getClass().getMethod(paramString, paramArrayOfClass).invoke(paramObject, paramArrayOfObject);
      return localObject;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        localIllegalArgumentException.printStackTrace();
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

  public static Object invokeMethod(Object paramObject, String paramString, Object[] paramArrayOfObject)
    throws Exception
  {
    Class localClass = paramObject.getClass();
    Class[] arrayOfClass = new Class[paramArrayOfObject.length];
    int i = 0;
    int j = paramArrayOfObject.length;
    while (true)
    {
      if (i >= j)
        return localClass.getMethod(paramString, arrayOfClass).invoke(paramObject, paramArrayOfObject);
      arrayOfClass[i] = paramArrayOfObject[i].getClass();
      i++;
    }
  }

  public static Object invokeParamsMethod(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
    throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    Method localMethod = paramObject.getClass().getMethod(paramString, paramArrayOfClass);
    localMethod.setAccessible(true);
    return localMethod.invoke(paramObject, paramArrayOfObject);
  }

  public static Object invokeStaticMethod(Class paramClass, String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
  {
    try
    {
      Object localObject = paramClass.getMethod(paramString, paramArrayOfClass).invoke(null, paramArrayOfObject);
      return localObject;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return null;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return null;
  }

  public static Object invokeStaticMethod(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
  {
    try
    {
      Object localObject = Class.forName(paramString1).getMethod(paramString2, paramArrayOfClass).invoke(null, paramArrayOfObject);
      return localObject;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      return null;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return null;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return null;
  }

  public static Object invokeStaticMethod(String paramString1, String paramString2, Object[] paramArrayOfObject)
    throws Exception
  {
    Class localClass = Class.forName(paramString1);
    Class[] arrayOfClass = new Class[paramArrayOfObject.length];
    int i = 0;
    int j = paramArrayOfObject.length;
    while (true)
    {
      if (i >= j)
        return localClass.getMethod(paramString2, arrayOfClass).invoke(null, paramArrayOfObject);
      arrayOfClass[i] = paramArrayOfObject[i].getClass();
      i++;
    }
  }

  public static void invokeVoidMethod(Object paramObject, String paramString, boolean paramBoolean)
  {
    try
    {
      Class localClass = paramObject.getClass();
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Boolean.TYPE;
      Method localMethod = localClass.getMethod(paramString, arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      localMethod.invoke(paramObject, arrayOfObject);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    catch (SecurityException localSecurityException)
    {
    }
  }

  public static boolean isInstance(Object paramObject, Class paramClass)
  {
    return paramClass.isInstance(paramObject);
  }

  public static Object newInstance(String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
    throws Exception
  {
    return Class.forName(paramString).getConstructor(paramArrayOfClass).newInstance(paramArrayOfObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.Reflection
 * JD-Core Version:    0.6.2
 */