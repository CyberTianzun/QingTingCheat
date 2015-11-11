package com.alibaba.fastjson.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServiceLoader
{
  private static final String PREFIX = "META-INF/services/";
  private static final Set<String> loadedUrls = new HashSet();

  public static <T> Set<T> load(Class<T> paramClass, ClassLoader paramClassLoader)
  {
    Object localObject;
    if (paramClassLoader == null)
      localObject = Collections.emptySet();
    while (true)
    {
      return localObject;
      localObject = new HashSet();
      String str1 = paramClass.getName();
      String str2 = "META-INF/services/" + str1;
      HashSet localHashSet = new HashSet();
      Iterator localIterator;
      try
      {
        Enumeration localEnumeration = paramClassLoader.getResources(str2);
        while (localEnumeration.hasMoreElements())
        {
          URL localURL = (URL)localEnumeration.nextElement();
          if (!loadedUrls.contains(localURL.toString()))
          {
            load(localURL, localHashSet);
            loadedUrls.add(localURL.toString());
          }
        }
      }
      catch (IOException localIOException)
      {
        localIterator = localHashSet.iterator();
      }
      while (localIterator.hasNext())
      {
        String str3 = (String)localIterator.next();
        try
        {
          ((Set)localObject).add(paramClassLoader.loadClass(str3).newInstance());
        }
        catch (Exception localException)
        {
        }
      }
    }
  }

  public static void load(URL paramURL, Set<String> paramSet)
    throws IOException
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramURL.openStream();
      BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(localInputStream, "utf-8"));
      try
      {
        while (true)
        {
          String str1 = localBufferedReader2.readLine();
          String str2 = str1;
          if (str2 == null)
          {
            IOUtils.close(localBufferedReader2);
            IOUtils.close(localInputStream);
            return;
          }
          int i = str2.indexOf('#');
          if (i >= 0)
            str2 = str2.substring(0, i);
          String str3 = str2.trim();
          if (str3.length() != 0)
            paramSet.add(str3);
        }
      }
      finally
      {
        localBufferedReader1 = localBufferedReader2;
      }
      IOUtils.close(localBufferedReader1);
      IOUtils.close(localInputStream);
      throw localObject1;
    }
    finally
    {
      BufferedReader localBufferedReader1 = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.ServiceLoader
 * JD-Core Version:    0.6.2
 */