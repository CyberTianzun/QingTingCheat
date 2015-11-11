package com.umeng.message.proguard;

import android.content.Context;
import java.util.zip.Adler32;

public class n
{
  static String a = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
  static final Object b = new Object();
  static final byte c = 1;
  private static m d = null;

  static long a(m paramm)
  {
    if (paramm != null)
    {
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = paramm.f();
      arrayOfObject[1] = paramm.e();
      arrayOfObject[2] = Long.valueOf(paramm.b());
      arrayOfObject[3] = paramm.d();
      arrayOfObject[4] = paramm.c();
      String str = String.format("%s%s%s%s%s", arrayOfObject);
      if (!f.a(str))
      {
        Adler32 localAdler32 = new Adler32();
        localAdler32.reset();
        localAdler32.update(str.getBytes());
        return localAdler32.getValue();
      }
    }
    return 0L;
  }

  public static m a(Context paramContext)
  {
    while (true)
    {
      try
      {
        if (d != null)
        {
          localm = d;
          return localm;
        }
        if (paramContext != null)
        {
          localm = b(paramContext);
          d = localm;
          continue;
        }
      }
      finally
      {
      }
      m localm = null;
    }
  }

  private static m b(Context paramContext)
  {
    if (paramContext != null)
    {
      new m();
      while (true)
      {
        String str1;
        synchronized (b)
        {
          str1 = p.a(paramContext).a();
          if (!f.a(str1))
          {
            if (!str1.endsWith("\n"))
              break label146;
            str2 = str1.substring(0, -1 + str1.length());
            m localm = new m();
            long l = System.currentTimeMillis();
            String str3 = e.a(paramContext);
            String str4 = e.b(paramContext);
            localm.c(str3);
            localm.a(str3);
            localm.b(l);
            localm.b(str4);
            localm.d(str2);
            localm.a(a(localm));
            return localm;
          }
        }
        label146: String str2 = str1;
      }
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.n
 * JD-Core Version:    0.6.2
 */