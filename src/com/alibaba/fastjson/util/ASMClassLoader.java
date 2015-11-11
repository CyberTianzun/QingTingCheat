package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

public class ASMClassLoader extends ClassLoader
{
  private static ProtectionDomain DOMAIN = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction()
  {
    public Object run()
    {
      return ASMClassLoader.class.getProtectionDomain();
    }
  });

  public ASMClassLoader()
  {
    super(getParentClassLoader());
  }

  static ClassLoader getParentClassLoader()
  {
    ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
    if (localClassLoader != null)
      try
      {
        localClassLoader.loadClass(JSON.class.getName());
        return localClassLoader;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
      }
    return JSON.class.getClassLoader();
  }

  public Class<?> defineClassPublic(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ClassFormatError
  {
    return defineClass(paramString, paramArrayOfByte, paramInt1, paramInt2, DOMAIN);
  }

  public boolean isExternalClass(Class<?> paramClass)
  {
    ClassLoader localClassLoader = paramClass.getClassLoader();
    if (localClassLoader == null)
      return false;
    for (Object localObject = this; ; localObject = ((ClassLoader)localObject).getParent())
    {
      if (localObject == null)
        break label30;
      if (localObject == localClassLoader)
        break;
    }
    label30: return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.ASMClassLoader
 * JD-Core Version:    0.6.2
 */