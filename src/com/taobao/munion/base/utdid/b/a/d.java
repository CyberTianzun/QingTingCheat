package com.taobao.munion.base.utdid.b.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

public class d
{
  public static final int a = 2;
  public static final int b = 1;
  public static final int c;
  private static final Object f = new Object();
  private final Object d = new Object();
  private File e;
  private HashMap<File, a> g = new HashMap();

  public d(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      this.e = new File(paramString);
      return;
    }
    throw new RuntimeException("Directory can not be empty");
  }

  private File a(File paramFile, String paramString)
  {
    if (paramString.indexOf(File.separatorChar) < 0)
      return new File(paramFile, paramString);
    throw new IllegalArgumentException("File " + paramString + " contains a path separator");
  }

  private File a(String paramString)
  {
    return a(b(), paramString + ".xml");
  }

  private File b()
  {
    synchronized (this.d)
    {
      File localFile = this.e;
      return localFile;
    }
  }

  private static File b(File paramFile)
  {
    return new File(paramFile.getPath() + ".bak");
  }

  public b a(String paramString, int paramInt)
  {
    File localFile1 = a(paramString);
    a locala;
    HashMap localHashMap;
    synchronized (f)
    {
      locala = (a)this.g.get(localFile1);
      if ((locala != null) && (!locala.d()))
        return locala;
      File localFile2 = b(localFile1);
      if (localFile2.exists())
      {
        localFile1.delete();
        localFile2.renameTo(localFile1);
      }
      if ((localFile1.exists()) && (!localFile1.canRead()));
      boolean bool1 = localFile1.exists();
      localHashMap = null;
      if (bool1)
      {
        boolean bool2 = localFile1.canRead();
        localHashMap = null;
        if (!bool2);
      }
    }
    try
    {
      FileInputStream localFileInputStream1 = new FileInputStream(localFile1);
      localHashMap = e.a(localFileInputStream1);
      localFileInputStream1.close();
      label139: Object localObject3 = f;
      if (locala != null)
      {
        try
        {
          locala.a(localHashMap);
          return locala;
        }
        finally
        {
        }
        localObject2 = finally;
        throw localObject2;
      }
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      while (true)
      {
        try
        {
          FileInputStream localFileInputStream2 = new FileInputStream(localFile1);
          byte[] arrayOfByte = new byte[localFileInputStream2.available()];
          localFileInputStream2.read(arrayOfByte);
          new String(arrayOfByte, 0, arrayOfByte.length, "UTF-8");
        }
        catch (FileNotFoundException localFileNotFoundException2)
        {
          localFileNotFoundException2.printStackTrace();
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
        }
        continue;
        locala = (a)this.g.get(localFile1);
        if (locala == null)
        {
          locala = new a(localFile1, paramInt, localHashMap);
          this.g.put(localFile1, locala);
        }
      }
    }
    catch (IOException localIOException1)
    {
      break label139;
    }
    catch (FileNotFoundException localFileNotFoundException1)
    {
      break label139;
    }
  }

  private static final class a
    implements b
  {
    private static final Object f = new Object();
    private final File a;
    private final File b;
    private final int c;
    private Map d;
    private boolean e = false;
    private WeakHashMap<b.b, Object> g;

    a(File paramFile, int paramInt, Map paramMap)
    {
      this.a = paramFile;
      this.b = d.a(paramFile);
      this.c = paramInt;
      if (paramMap != null);
      while (true)
      {
        this.d = paramMap;
        this.g = new WeakHashMap();
        return;
        paramMap = new HashMap();
      }
    }

    private FileOutputStream a(File paramFile)
    {
      try
      {
        FileOutputStream localFileOutputStream1 = new FileOutputStream(paramFile);
        return localFileOutputStream1;
      }
      catch (FileNotFoundException localFileNotFoundException1)
      {
        if (!paramFile.getParentFile().mkdir())
          return null;
        try
        {
          FileOutputStream localFileOutputStream2 = new FileOutputStream(paramFile);
          return localFileOutputStream2;
        }
        catch (FileNotFoundException localFileNotFoundException2)
        {
        }
      }
      return null;
    }

    private boolean e()
    {
      if (this.a.exists())
        if (!this.b.exists())
          if (this.a.renameTo(this.b))
            break label44;
      while (true)
      {
        return false;
        this.a.delete();
        try
        {
          label44: FileOutputStream localFileOutputStream = a(this.a);
          if (localFileOutputStream == null)
            continue;
          e.a(this.d, localFileOutputStream);
          localFileOutputStream.close();
          this.b.delete();
          return true;
        }
        catch (IOException localIOException)
        {
          if ((!this.a.exists()) || (this.a.delete()))
            continue;
          return false;
        }
        catch (XmlPullParserException localXmlPullParserException)
        {
          label80: break label80;
        }
      }
    }

    public float a(String paramString, float paramFloat)
    {
      try
      {
        Float localFloat = (Float)this.d.get(paramString);
        if (localFloat != null)
          paramFloat = localFloat.floatValue();
        return paramFloat;
      }
      finally
      {
      }
    }

    public int a(String paramString, int paramInt)
    {
      try
      {
        Integer localInteger = (Integer)this.d.get(paramString);
        if (localInteger != null)
          paramInt = localInteger.intValue();
        return paramInt;
      }
      finally
      {
      }
    }

    public long a(String paramString, long paramLong)
    {
      try
      {
        Long localLong = (Long)this.d.get(paramString);
        if (localLong != null)
          paramLong = localLong.longValue();
        return paramLong;
      }
      finally
      {
      }
    }

    public String a(String paramString1, String paramString2)
    {
      while (true)
      {
        try
        {
          str = (String)this.d.get(paramString1);
          if (str != null)
            return str;
        }
        finally
        {
        }
        String str = paramString2;
      }
    }

    public void a(b.b paramb)
    {
      try
      {
        this.g.put(paramb, f);
        return;
      }
      finally
      {
      }
    }

    public void a(Map paramMap)
    {
      if (paramMap != null)
        try
        {
          this.d = paramMap;
          return;
        }
        finally
        {
        }
    }

    public void a(boolean paramBoolean)
    {
      try
      {
        this.e = paramBoolean;
        return;
      }
      finally
      {
      }
    }

    public boolean a()
    {
      return (this.a != null) && (new File(this.a.getAbsolutePath()).exists());
    }

    public boolean a(String paramString)
    {
      try
      {
        boolean bool = this.d.containsKey(paramString);
        return bool;
      }
      finally
      {
      }
    }

    public boolean a(String paramString, boolean paramBoolean)
    {
      try
      {
        Boolean localBoolean = (Boolean)this.d.get(paramString);
        if (localBoolean != null)
          paramBoolean = localBoolean.booleanValue();
        return paramBoolean;
      }
      finally
      {
      }
    }

    public Map<String, ?> b()
    {
      try
      {
        HashMap localHashMap = new HashMap(this.d);
        return localHashMap;
      }
      finally
      {
      }
    }

    public void b(b.b paramb)
    {
      try
      {
        this.g.remove(paramb);
        return;
      }
      finally
      {
      }
    }

    public b.a c()
    {
      return new a();
    }

    public boolean d()
    {
      try
      {
        boolean bool = this.e;
        return bool;
      }
      finally
      {
      }
    }

    public final class a
      implements b.a
    {
      private final Map<String, Object> b = new HashMap();
      private boolean c = false;

      public a()
      {
      }

      public b.a a()
      {
        try
        {
          this.c = true;
          return this;
        }
        finally
        {
        }
      }

      public b.a a(String paramString)
      {
        try
        {
          this.b.put(paramString, this);
          return this;
        }
        finally
        {
        }
      }

      public b.a a(String paramString, float paramFloat)
      {
        try
        {
          this.b.put(paramString, Float.valueOf(paramFloat));
          return this;
        }
        finally
        {
        }
      }

      public b.a a(String paramString, int paramInt)
      {
        try
        {
          this.b.put(paramString, Integer.valueOf(paramInt));
          return this;
        }
        finally
        {
        }
      }

      public b.a a(String paramString, long paramLong)
      {
        try
        {
          this.b.put(paramString, Long.valueOf(paramLong));
          return this;
        }
        finally
        {
        }
      }

      public b.a a(String paramString1, String paramString2)
      {
        try
        {
          this.b.put(paramString1, paramString2);
          return this;
        }
        finally
        {
        }
      }

      public b.a a(String paramString, boolean paramBoolean)
      {
        try
        {
          this.b.put(paramString, Boolean.valueOf(paramBoolean));
          return this;
        }
        finally
        {
        }
      }

      public boolean b()
      {
        while (true)
        {
          int j;
          String str2;
          Object localObject4;
          synchronized (d.a())
          {
            int i = d.a.a(d.a.this).size();
            j = 0;
            if (i > 0)
              j = 1;
            if (j == 0)
              break label351;
            ArrayList localArrayList1 = new ArrayList();
            localHashSet = new HashSet(d.a.a(d.a.this).keySet());
            localArrayList2 = localArrayList1;
            try
            {
              if (this.c)
              {
                d.a.b(d.a.this).clear();
                this.c = false;
              }
              Iterator localIterator1 = this.b.entrySet().iterator();
              if (!localIterator1.hasNext())
                break label220;
              Map.Entry localEntry = (Map.Entry)localIterator1.next();
              str2 = (String)localEntry.getKey();
              localObject4 = localEntry.getValue();
              if (localObject4 == this)
              {
                d.a.b(d.a.this).remove(str2);
                if (j == 0)
                  continue;
                localArrayList2.add(str2);
                continue;
              }
            }
            finally
            {
            }
          }
          d.a.b(d.a.this).put(str2, localObject4);
          continue;
          label220: this.b.clear();
          boolean bool = d.a.c(d.a.this);
          if (bool)
            d.a.this.a(true);
          if (j != 0)
            for (int k = -1 + localArrayList2.size(); k >= 0; k--)
            {
              String str1 = (String)localArrayList2.get(k);
              Iterator localIterator2 = localHashSet.iterator();
              while (localIterator2.hasNext())
              {
                b.b localb = (b.b)localIterator2.next();
                if (localb != null)
                  localb.a(d.a.this, str1);
              }
            }
          return bool;
          label351: HashSet localHashSet = null;
          ArrayList localArrayList2 = null;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.utdid.b.a.d
 * JD-Core Version:    0.6.2
 */