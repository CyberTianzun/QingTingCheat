package com.taobao.munion.base.utdid.c;

import android.content.Context;
import android.provider.Settings.System;
import com.taobao.munion.base.utdid.a.a.b;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.zip.Adler32;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class d
{
  static final String a = "dxCRMxhQkdGePGnp";
  static final String b = "mqBRboGZkQPcAkyk";
  private static final String c = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
  private static final Object e = new Object();
  private static d f = null;
  private static final String l = ".DataStorage";
  private static final String m = "ContextData";
  private static final String o = ".UTSystemConfig" + File.separator + "Global";
  private static final String p = "Alvin2";
  private Context d = null;
  private String g = null;
  private e h = null;
  private String i = "xx_utdid_key";
  private String j = "xx_utdid_domain";
  private com.taobao.munion.base.utdid.b.a.c k = null;
  private com.taobao.munion.base.utdid.b.a.c n = null;

  public d(Context paramContext)
  {
    this.d = paramContext;
    this.n = new com.taobao.munion.base.utdid.b.a.c(paramContext, o, "Alvin2", false, true);
    this.k = new com.taobao.munion.base.utdid.b.a.c(paramContext, ".DataStorage", "ContextData", false, true);
    this.h = new e();
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(com.taobao.munion.base.utdid.a.a.f.b(this.i));
    this.i = String.format("K_%d", arrayOfObject1);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(com.taobao.munion.base.utdid.a.a.f.b(this.j));
    this.j = String.format("D_%d", arrayOfObject2);
  }

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
      if (!com.taobao.munion.base.utdid.a.a.f.a(str))
      {
        Adler32 localAdler32 = new Adler32();
        localAdler32.reset();
        localAdler32.update(str.getBytes());
        return localAdler32.getValue();
      }
    }
    return 0L;
  }

  public static d a(Context paramContext)
  {
    if ((paramContext != null) && (f == null));
    synchronized (e)
    {
      if (f == null)
        f = new d(paramContext);
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
      if (com.taobao.munion.base.utdid.a.a.f.a(str2))
        str2 = com.taobao.munion.base.utdid.a.a.e.a(this.d);
      String str3 = this.n.b("SI");
      if (com.taobao.munion.base.utdid.a.a.f.a(str3))
        str3 = com.taobao.munion.base.utdid.a.a.e.b(this.d);
      String str4 = this.n.b("DID");
      if (com.taobao.munion.base.utdid.a.a.f.a(str4))
        str4 = str2;
      if ((str1 == null) || (!str1.equals(paramString)))
      {
        a locala = new a();
        locala.a(str2);
        locala.b(str3);
        locala.d(paramString);
        locala.c(str4);
        locala.b(System.currentTimeMillis());
        this.n.a("UTDID", paramString);
        this.n.a("EI", locala.c());
        this.n.a("SI", locala.d());
        this.n.a("DID", locala.e());
        this.n.a("timestamp", locala.b());
        this.n.a("S", a(locala));
        this.n.c();
      }
    }
  }

  private String b()
  {
    if (this.n != null)
    {
      String str = this.n.b("UTDID");
      if ((!com.taobao.munion.base.utdid.a.a.f.a(str)) && (this.h.a(str) != null))
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
      if ((24 == paramString.length()) && (com.taobao.munion.base.utdid.a.a.f.a(Settings.System.getString(this.d.getContentResolver(), "mqBRboGZkQPcAkyk"))))
        Settings.System.putString(this.d.getContentResolver(), "mqBRboGZkQPcAkyk", paramString);
    }
  }

  private final byte[] c()
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i1 = (int)(System.currentTimeMillis() / 1000L);
    int i2 = new Random().nextInt();
    byte[] arrayOfByte1 = com.taobao.munion.base.utdid.a.a.c.a(i1);
    byte[] arrayOfByte2 = com.taobao.munion.base.utdid.a.a.c.a(i2);
    localByteArrayOutputStream.write(arrayOfByte1, 0, 4);
    localByteArrayOutputStream.write(arrayOfByte2, 0, 4);
    localByteArrayOutputStream.write(3);
    localByteArrayOutputStream.write(0);
    try
    {
      String str2 = com.taobao.munion.base.utdid.a.a.e.a(this.d);
      str1 = str2;
      localByteArrayOutputStream.write(com.taobao.munion.base.utdid.a.a.c.a(com.taobao.munion.base.utdid.a.a.f.b(str1)), 0, 4);
      localByteArrayOutputStream.write(com.taobao.munion.base.utdid.a.a.c.a(com.taobao.munion.base.utdid.a.a.f.b(a(localByteArrayOutputStream.toByteArray()))));
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
    f localf;
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
        localf = new f();
        str2 = Settings.System.getString(this.d.getContentResolver(), "dxCRMxhQkdGePGnp");
        if (com.taobao.munion.base.utdid.a.a.f.a(str2))
          break label463;
        str1 = localf.b(str2);
        if (f(str1))
        {
          c(str1);
          continue;
        }
      }
      finally
      {
      }
      String str8 = localf.a(str2);
      if (str8 == null)
        break label456;
      String str9 = this.h.a(str8);
      if (com.taobao.munion.base.utdid.a.a.f.a(str9))
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
        if (!com.taobao.munion.base.utdid.a.a.f.a(str3))
        {
          String str5 = localf.a(str3);
          if (str5 == null)
            str5 = this.h.b(str3);
          if (f(str5))
          {
            String str6 = this.h.a(str5);
            if (!com.taobao.munion.base.utdid.a.a.f.a(str5))
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
 * Qualified Name:     com.taobao.munion.base.utdid.c.d
 * JD-Core Version:    0.6.2
 */