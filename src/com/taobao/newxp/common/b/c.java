package com.taobao.newxp.common.b;

import android.content.Context;
import com.taobao.newxp.common.Log;
import java.lang.reflect.Field;

public class c
{
  private static final String a = c.class.getName();
  private static c b;
  private static Class d = null;
  private static Class e = null;
  private static Class f = null;
  private static Class g = null;
  private static Class h = null;
  private static Class i = null;
  private static Class j = null;
  private static Class k = null;
  private static Class l = null;
  private static Class m = null;
  private static Class n = null;
  private final Context c;

  private c(Context paramContext)
  {
    this.c = paramContext.getApplicationContext();
    try
    {
      e = Class.forName(this.c.getPackageName() + ".R$drawable");
    }
    catch (ClassNotFoundException localClassNotFoundException10)
    {
      try
      {
        f = Class.forName(this.c.getPackageName() + ".R$layout");
      }
      catch (ClassNotFoundException localClassNotFoundException10)
      {
        try
        {
          d = Class.forName(this.c.getPackageName() + ".R$id");
        }
        catch (ClassNotFoundException localClassNotFoundException10)
        {
          try
          {
            g = Class.forName(this.c.getPackageName() + ".R$anim");
          }
          catch (ClassNotFoundException localClassNotFoundException10)
          {
            try
            {
              h = Class.forName(this.c.getPackageName() + ".R$style");
            }
            catch (ClassNotFoundException localClassNotFoundException10)
            {
              try
              {
                j = Class.forName(this.c.getPackageName() + ".R$string");
              }
              catch (ClassNotFoundException localClassNotFoundException10)
              {
                try
                {
                  k = Class.forName(this.c.getPackageName() + ".R$array");
                }
                catch (ClassNotFoundException localClassNotFoundException10)
                {
                  try
                  {
                    l = Class.forName(this.c.getPackageName() + ".R$attr");
                  }
                  catch (ClassNotFoundException localClassNotFoundException10)
                  {
                    try
                    {
                      m = Class.forName(this.c.getPackageName() + ".R$dimen");
                    }
                    catch (ClassNotFoundException localClassNotFoundException10)
                    {
                      try
                      {
                        i = Class.forName(this.c.getPackageName() + ".R$styleable");
                      }
                      catch (ClassNotFoundException localClassNotFoundException10)
                      {
                        try
                        {
                          while (true)
                          {
                            n = Class.forName(this.c.getPackageName() + ".R$color");
                            return;
                            localClassNotFoundException1 = localClassNotFoundException1;
                            Log.b(a, localClassNotFoundException1.getMessage());
                            continue;
                            localClassNotFoundException2 = localClassNotFoundException2;
                            Log.b(a, localClassNotFoundException2.getMessage());
                            continue;
                            localClassNotFoundException3 = localClassNotFoundException3;
                            Log.b(a, localClassNotFoundException3.getMessage());
                            continue;
                            localClassNotFoundException4 = localClassNotFoundException4;
                            Log.b(a, localClassNotFoundException4.getMessage());
                            continue;
                            localClassNotFoundException5 = localClassNotFoundException5;
                            Log.b(a, localClassNotFoundException5.getMessage());
                            continue;
                            localClassNotFoundException6 = localClassNotFoundException6;
                            Log.b(a, localClassNotFoundException6.getMessage());
                            continue;
                            localClassNotFoundException7 = localClassNotFoundException7;
                            Log.b(a, localClassNotFoundException7.getMessage());
                            continue;
                            localClassNotFoundException8 = localClassNotFoundException8;
                            Log.b(a, localClassNotFoundException8.getMessage());
                            continue;
                            localClassNotFoundException9 = localClassNotFoundException9;
                            Log.b(a, localClassNotFoundException9.getMessage());
                            continue;
                            localClassNotFoundException10 = localClassNotFoundException10;
                            Log.b(a, localClassNotFoundException10.getMessage());
                          }
                        }
                        catch (ClassNotFoundException localClassNotFoundException11)
                        {
                          Log.b(a, localClassNotFoundException11.getMessage());
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private int a(Class<?> paramClass, String paramString)
  {
    if (paramClass == null)
    {
      Log.b(a, "getRes(null," + paramString + ")");
      throw new IllegalArgumentException("ResClass is not initialized. Please make sure you have added neccessary resources. Also make sure you have " + this.c.getPackageName() + ".R$* configured in obfuscation. field=" + paramString);
    }
    try
    {
      int i1 = paramClass.getField(paramString).getInt(paramString);
      return i1;
    }
    catch (Exception localException)
    {
      Log.b(a, "getRes(" + paramClass.getName() + ", " + paramString + ")");
      Log.b(a, "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
      Log.b(a, localException.getMessage());
    }
    return -1;
  }

  public static c a(Context paramContext)
  {
    if (b == null)
      b = new c(paramContext);
    return b;
  }

  public int a(String paramString)
  {
    return a(g, paramString);
  }

  public int b(String paramString)
  {
    return a(d, paramString);
  }

  public int c(String paramString)
  {
    return a(l, paramString);
  }

  public int d(String paramString)
  {
    return a(e, paramString);
  }

  public int e(String paramString)
  {
    return a(m, paramString);
  }

  public int f(String paramString)
  {
    return a(i, paramString);
  }

  public int g(String paramString)
  {
    return a(f, paramString);
  }

  public int h(String paramString)
  {
    return a(n, paramString);
  }

  public int i(String paramString)
  {
    return a(h, paramString);
  }

  public int j(String paramString)
  {
    return a(j, paramString);
  }

  public int k(String paramString)
  {
    return a(k, paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.b.c
 * JD-Core Version:    0.6.2
 */