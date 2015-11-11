package com.tencent.b.a;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class d
  implements Iterable<String>
{
  private ConcurrentLinkedQueue<String> a = null;
  private AtomicInteger b = null;

  public int a()
  {
    return this.b.get();
  }

  public int a(String paramString)
  {
    int i = paramString.length();
    this.a.add(paramString);
    return this.b.addAndGet(i);
  }

  public void a(Writer paramWriter, char[] paramArrayOfChar)
    throws IOException
  {
    if ((paramWriter == null) || (paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
      return;
    int i = paramArrayOfChar.length;
    Iterator localIterator = iterator();
    int j = 0;
    int k = i;
    while (true)
    {
      String str;
      int m;
      int n;
      if (localIterator.hasNext())
      {
        str = (String)localIterator.next();
        m = str.length();
        n = 0;
      }
      while (m > 0)
      {
        if (k > m);
        int i2;
        for (int i1 = m; ; i1 = k)
        {
          str.getChars(n, n + i1, paramArrayOfChar, j);
          k -= i1;
          j += i1;
          m -= i1;
          i2 = i1 + n;
          if (k != 0)
            break label170;
          paramWriter.write(paramArrayOfChar, 0, i);
          k = i;
          n = i2;
          j = 0;
          break;
        }
        if (j > 0)
          paramWriter.write(paramArrayOfChar, 0, j);
        paramWriter.flush();
        return;
        label170: n = i2;
      }
    }
  }

  public void b()
  {
    this.a.clear();
    this.b.set(0);
  }

  public Iterator<String> iterator()
  {
    return this.a.iterator();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.b.a.d
 * JD-Core Version:    0.6.2
 */