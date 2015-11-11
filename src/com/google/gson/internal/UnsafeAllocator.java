package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator
{
  public static UnsafeAllocator create()
  {
    try
    {
      Class localClass = Class.forName("sun.misc.Unsafe");
      Field localField = localClass.getDeclaredField("theUnsafe");
      localField.setAccessible(true);
      final Object localObject = localField.get(null);
      UnsafeAllocator local1 = new UnsafeAllocator()
      {
        public <T> T newInstance(Class<T> paramAnonymousClass)
          throws Exception
        {
          return this.val$allocateInstance.invoke(localObject, new Object[] { paramAnonymousClass });
        }
      };
      return local1;
    }
    catch (Exception localException1)
    {
      try
      {
        Method localMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Class.class });
        localMethod3.setAccessible(true);
        UnsafeAllocator local2 = new UnsafeAllocator()
        {
          public <T> T newInstance(Class<T> paramAnonymousClass)
            throws Exception
          {
            return this.val$newInstance.invoke(null, new Object[] { paramAnonymousClass, Object.class });
          }
        };
        return local2;
      }
      catch (Exception localException2)
      {
        try
        {
          Method localMethod1 = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[] { Class.class });
          localMethod1.setAccessible(true);
          final int i = ((Integer)localMethod1.invoke(null, new Object[] { Object.class })).intValue();
          Class[] arrayOfClass = new Class[2];
          arrayOfClass[0] = Class.class;
          arrayOfClass[1] = Integer.TYPE;
          Method localMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", arrayOfClass);
          localMethod2.setAccessible(true);
          UnsafeAllocator local3 = new UnsafeAllocator()
          {
            public <T> T newInstance(Class<T> paramAnonymousClass)
              throws Exception
            {
              Method localMethod = this.val$newInstance;
              Object[] arrayOfObject = new Object[2];
              arrayOfObject[0] = paramAnonymousClass;
              arrayOfObject[1] = Integer.valueOf(i);
              return localMethod.invoke(null, arrayOfObject);
            }
          };
          return local3;
        }
        catch (Exception localException3)
        {
        }
      }
    }
    return new UnsafeAllocator()
    {
      public <T> T newInstance(Class<T> paramAnonymousClass)
      {
        throw new UnsupportedOperationException("Cannot allocate " + paramAnonymousClass);
      }
    };
  }

  public abstract <T> T newInstance(Class<T> paramClass)
    throws Exception;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.UnsafeAllocator
 * JD-Core Version:    0.6.2
 */