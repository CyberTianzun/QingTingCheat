package com.taobao.munion.base.utdid.c;

import android.content.Context;
import com.taobao.munion.base.utdid.a.a.e;
import com.taobao.munion.base.utdid.a.a.f;
import java.util.zip.Adler32;

public class b
{
  static String a = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
  static final Object b = new Object();
  static final byte c = 1;
  private static a d = null;

  static long a(a parama)
  {
    if (parama != null)
    {
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = parama.f();
      arrayOfObject[1] = parama.e();
      arrayOfObject[2] = Long.valueOf(parama.b());
      arrayOfObject[3] = parama.d();
      arrayOfObject[4] = parama.c();
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

  public static a a(Context paramContext)
  {
    while (true)
    {
      try
      {
        if (d != null)
        {
          locala = d;
          return locala;
        }
        if (paramContext != null)
        {
          locala = b(paramContext);
          d = locala;
          continue;
        }
      }
      finally
      {
      }
      a locala = null;
    }
  }

  private static a b(Context paramContext)
  {
    if (paramContext != null)
    {
      new a();
      while (true)
      {
        String str1;
        synchronized (b)
        {
          str1 = d.a(paramContext).a();
          if (!f.a(str1))
          {
            if (!str1.endsWith("\n"))
              break label146;
            str2 = str1.substring(0, -1 + str1.length());
            a locala = new a();
            long l = System.currentTimeMillis();
            String str3 = e.a(paramContext);
            String str4 = e.b(paramContext);
            locala.c(str3);
            locala.a(str3);
            locala.b(l);
            locala.b(str4);
            locala.d(str2);
            locala.a(a(locala));
            return locala;
          }
        }
        label146: String str2 = str1;
      }
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.utdid.c.b
 * JD-Core Version:    0.6.2
 */