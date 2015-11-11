package com.umeng.message.proguard;

import android.content.Context;
import android.provider.Settings.System;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.zip.Adler32;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class p
{
  static final String a = "dxCRMxhQkdGePGnp";
  static final String b = "mqBRboGZkQPcAkyk";
  private static final String c = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
  private static final Object e = new Object();
  private static p f = null;
  private static final String l = ".DataStorage";
  private static final String m = "ContextData";
  private static final String o = ".UTSystemConfig" + File.separator + "Global";
  private static final String p = "Alvin2";
  private Context d = null;
  private String g = null;
  private q h = null;
  private String i = "xx_utdid_key";
  private String j = "xx_utdid_domain";
  private j k = null;
  private j n = null;

  public p(Context paramContext)
  {
    this.d = paramContext;
    this.n = new j(paramContext, o, "Alvin2", false, true);
    this.k = new j(paramContext, ".DataStorage", "ContextData", false, true);
    this.h = new q();
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(f.b(this.i));
    this.i = String.format("K_%d", arrayOfObject1);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(f.b(this.j));
    this.j = String.format("D_%d", arrayOfObject2);
  }

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

  public static p a(Context paramContext)
  {
    if ((paramContext != null) && (f == null));
    synchronized (e)
    {
      if (f == null)
        f = new p(paramContext);
      return f;
    }
  }

  private static String a(byte[] paramArrayOfByte)
    throws Exception
  {
    Mac localMac = Mac.getInstance("HmacSHA1");
    localMac.init(new SecretKeySpec("d6fc3a4a06adbde89223bvefedc24fecde188aaa9161".getBytes(), localMac.getAlgorithm()));
    return b.b(localMac.doFinal(paramArrayOfByte), 2);
  }

  private void a(String paramString)
  {
    if ((paramString != null) && (paramString.endsWith("\n")))
      paramString = paramString.substring(0, -1 + paramString.length());
    if ((paramString != null) && (paramString.length() == 24) && (this.n != null))
    {
      String str1 = this.n.b("UTDID");
      String str2 = this.n.b("EI");
      if (f.a(str2))
        str2 = e.a(this.d);
      String str3 = this.n.b("SI");
      if (f.a(str3))
        str3 = e.b(this.d);
      String str4 = this.n.b("DID");
      if (f.a(str4))
        str4 = str2;
      if ((str1 == null) || (!str1.equals(paramString)))
      {
        m localm = new m();
        localm.a(str2);
        localm.b(str3);
        localm.d(paramString);
        localm.c(str4);
        localm.b(System.currentTimeMillis());
        this.n.a("UTDID", paramString);
        this.n.a("EI", localm.c());
        this.n.a("SI", localm.d());
        this.n.a("DID", localm.e());
        this.n.a("timestamp", localm.b());
        this.n.a("S", a(localm));
        this.n.c();
      }
    }
  }

  private String b()
  {
    if (this.n != null)
    {
      String str = this.n.b("UTDID");
      if ((!f.a(str)) && (this.h.a(str) != null))
        return str;
    }
    return null;
  }

  private void b(String paramString)
  {
    if ((paramString != null) && (this.k != null) && (!paramString.equals(this.k.b(this.i))))
    {
      this.k.a(this.i, paramString);
      this.k.c();
    }
  }

  private void c(String paramString)
  {
    if ((this.d.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0) && (paramString != null))
    {
      if (paramString.endsWith("\n"))
        paramString = paramString.substring(0, -1 + paramString.length());
      if ((24 == paramString.length()) && (f.a(Settings.System.getString(this.d.getContentResolver(), "mqBRboGZkQPcAkyk"))))
        Settings.System.putString(this.d.getContentResolver(), "mqBRboGZkQPcAkyk", paramString);
    }
  }

  private final byte[] c()
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i1 = (int)(System.currentTimeMillis() / 1000L);
    int i2 = new Random().nextInt();
    byte[] arrayOfByte1 = c.a(i1);
    byte[] arrayOfByte2 = c.a(i2);
    localByteArrayOutputStream.write(arrayOfByte1, 0, 4);
    localByteArrayOutputStream.write(arrayOfByte2, 0, 4);
    localByteArrayOutputStream.write(3);
    localByteArrayOutputStream.write(0);
    try
    {
      String str2 = e.a(this.d);
      str1 = str2;
      localByteArrayOutputStream.write(c.a(f.b(str1)), 0, 4);
      localByteArrayOutputStream.write(c.a(f.b(a(localByteArrayOutputStream.toByteArray()))));
      return localByteArrayOutputStream.toByteArray();
    }
    catch (Exception localException)
    {
      while (true)
        String str1 = "" + new Random().nextInt();
    }
  }

  private void d(String paramString)
  {
    if (!paramString.equals(Settings.System.getString(this.d.getContentResolver(), "dxCRMxhQkdGePGnp")))
      Settings.System.putString(this.d.getContentResolver(), "dxCRMxhQkdGePGnp", paramString);
  }

  private void e(String paramString)
  {
    if ((this.d.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0) && (paramString != null))
      d(paramString);
  }

  private boolean f(String paramString)
  {
    return (paramString != null) && ((24 == paramString.length()) || ((25 == paramString.length()) && (paramString.endsWith("\n"))));
  }

  public String a()
  {
    String str1;
    r localr;
    String str2;
    String str10;
    label154: int i1;
    while (true)
    {
      try
      {
        if (this.g != null)
        {
          str1 = this.g;
          return str1;
        }
        str1 = Settings.System.getString(this.d.getContentResolver(), "mqBRboGZkQPcAkyk");
        if ((str1 != null) && (24 == str1.length()))
          continue;
        localr = new r();
        str2 = Settings.System.getString(this.d.getContentResolver(), "dxCRMxhQkdGePGnp");
        if (f.a(str2))
          break label463;
        str1 = localr.b(str2);
        if (f(str1))
        {
          c(str1);
          continue;
        }
      }
      finally
      {
      }
      String str8 = localr.a(str2);
      if (str8 == null)
        break label456;
      String str9 = this.h.a(str8);
      if (f.a(str9))
        break label456;
      e(str9);
      str10 = Settings.System.getString(this.d.getContentResolver(), "dxCRMxhQkdGePGnp");
      String str11 = this.h.b(str10);
      boolean bool = f(str11);
      i1 = 0;
      if (!bool)
        break;
      this.g = str11;
      a(str11);
      b(str10);
      c(this.g);
      str1 = this.g;
    }
    while (true)
    {
      while (true)
      {
        str1 = b();
        if (f(str1))
        {
          String str7 = this.h.a(str1);
          if (i1 != 0)
            e(str7);
          c(str1);
          b(str7);
          this.g = str1;
          break;
        }
        String str3 = this.k.b(this.i);
        if (!f.a(str3))
        {
          String str5 = localr.a(str3);
          if (str5 == null)
            str5 = this.h.b(str3);
          if (f(str5))
          {
            String str6 = this.h.a(str5);
            if (!f.a(str5))
            {
              this.g = str5;
              if (i1 != 0)
                e(str6);
              a(this.g);
              str1 = this.g;
              break;
            }
          }
        }
        try
        {
          byte[] arrayOfByte = c();
          if (arrayOfByte != null)
          {
            this.g = b.b(arrayOfByte, 2);
            a(this.g);
            String str4 = this.h.a(arrayOfByte);
            if (str4 != null)
            {
              if (i1 != 0)
                e(str4);
              b(str4);
            }
            str1 = this.g;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          str1 = null;
        }
      }
      break;
      label456: str10 = str2;
      break label154;
      label463: i1 = 1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.p
 * JD-Core Version:    0.6.2
 */