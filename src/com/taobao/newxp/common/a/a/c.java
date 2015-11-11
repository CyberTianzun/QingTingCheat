package com.taobao.newxp.common.a.a;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import com.taobao.newxp.common.a.a;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class c
{
  private static final String F = c.class.getName();
  private static int G = 0;
  public double A;
  public double B;
  public boolean C;
  public String D;
  public String E;
  private byte[] H;
  private Context I;
  private g J;
  public String a;
  public int b;
  public int c;
  public String d;
  public String e;
  public String f;
  public String g;
  public byte h;
  public String i;
  public int j;
  public int k;
  public int l;
  public int m;
  public int n;
  public int o;
  public boolean p;
  public byte q;
  public boolean r;
  public long s;
  public int t;
  public int u;
  public int v;
  public int w;
  public int x;
  public int y;
  public String z;

  private c()
  {
  }

  private c(Context paramContext, Bundle paramBundle)
  {
    this.I = paramContext;
    d locald = a.a().b();
    if (locald != null)
    {
      this.t = locald.f;
      this.u = locald.g;
      this.A = locald.i;
      this.B = locald.j;
    }
    d();
  }

  public static int a(InputStream paramInputStream)
    throws IOException
  {
    int i1 = 0x0 | 0xFF00 & paramInputStream.read() << 8 | 0xFF & paramInputStream.read();
    if (i1 != 65535)
      return i1;
    return -1;
  }

  public static c a(Context paramContext, Bundle paramBundle)
  {
    return new c(paramContext, paramBundle);
  }

  public static c a(String paramString)
    throws NullPointerException, IOException, SecurityException
  {
    return a(paramString.getBytes());
  }

  private static c a(byte[] paramArrayOfByte)
    throws NullPointerException, IOException, SecurityException
  {
    int i1 = 1;
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 7))
    {
      byte[] arrayOfByte1 = Base64.decode(paramArrayOfByte, 0);
      byte[] arrayOfByte2 = new byte[4];
      byte[] arrayOfByte3 = new byte[-7 + arrayOfByte1.length];
      c localc = new c();
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte1);
      localc.a = a((byte)localByteArrayInputStream.read(), (byte)localByteArrayInputStream.read());
      localc.H = a((byte)localByteArrayInputStream.read(), (byte)localByteArrayInputStream.read(), (byte)localByteArrayInputStream.read(), (byte)localByteArrayInputStream.read());
      localc.b = localByteArrayInputStream.read();
      System.arraycopy(arrayOfByte1, 7, arrayOfByte3, 0, arrayOfByte3.length);
      a(com.taobao.newxp.common.b.b.a(arrayOfByte3), arrayOfByte2, 0);
      int i2;
      if ((localc.H != null) && (arrayOfByte2 != null) && (com.taobao.munion.base.anticheat.b.a(localc.H, arrayOfByte2)))
      {
        localc.d = c(localByteArrayInputStream);
        localc.c = a(localByteArrayInputStream);
        localc.e = a(localByteArrayInputStream, 17);
        localc.f = c(localByteArrayInputStream);
        localc.g = c(localByteArrayInputStream);
        localc.h = ((byte)localByteArrayInputStream.read());
        localc.i = c(localByteArrayInputStream);
        localc.j = a(localByteArrayInputStream);
        localc.k = a(localByteArrayInputStream);
        localc.l = localByteArrayInputStream.read();
        localc.m = localByteArrayInputStream.read();
        localc.n = localByteArrayInputStream.read();
        localc.o = localByteArrayInputStream.read();
        localc.s = b(localByteArrayInputStream);
        if ((byte)localByteArrayInputStream.read() == i1)
          i2 = i1;
      }
      while (true)
      {
        localc.p = i2;
        localc.q = ((byte)localByteArrayInputStream.read());
        if ((byte)localByteArrayInputStream.read() == i1)
        {
          label344: localc.r = i1;
          localc.t = a(localByteArrayInputStream);
          localc.u = a(localByteArrayInputStream);
          localc.J.a = a(localByteArrayInputStream);
          localc.J.e = a(localByteArrayInputStream);
          localc.J.f = a(localByteArrayInputStream);
          localc.J.c = a(localByteArrayInputStream);
          localc.J.d = a(localByteArrayInputStream);
          localc.J.g = a(localByteArrayInputStream);
          localc.J.h = a(localByteArrayInputStream);
          localc.J.i = a(localByteArrayInputStream);
          localc.v = ((byte)localByteArrayInputStream.read());
          localc.w = a(localByteArrayInputStream);
          localc.x = a(localByteArrayInputStream);
          localc.y = a(localByteArrayInputStream);
          localc.z = c(localByteArrayInputStream);
          localc.A = d(localByteArrayInputStream);
          localc.B = d(localByteArrayInputStream);
          localc.C = false;
          localc.D = c(localByteArrayInputStream);
          localc.E = c(localByteArrayInputStream);
        }
        try
        {
          localByteArrayInputStream.close();
          return localc;
          i2 = 0;
          continue;
          i1 = 0;
          break label344;
          throw new SecurityException();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          return localc;
        }
        finally
        {
          if (localByteArrayInputStream == null);
        }
      }
    }
    throw new NullPointerException();
  }

  private static String a(byte paramByte1, byte paramByte2)
    throws NullPointerException
  {
    return new Integer(paramByte1).toString() + "." + new Integer(paramByte2).toString();
  }

  public static String a(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    paramInputStream.read(arrayOfByte);
    if (arrayOfByte[0] != -1)
      return new String(arrayOfByte);
    return null;
  }

  private static void a(byte paramByte, byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > paramInt))
      paramArrayOfByte[paramInt] = paramByte;
  }

  public static void a(OutputStream paramOutputStream, double paramDouble, int paramInt)
    throws IOException
  {
    int i1 = -1;
    int i2;
    int i3;
    if (paramDouble != 0.0D)
    {
      i2 = (int)Math.floor(Math.abs(paramDouble));
      i3 = (int)Math.floor(60.0D * com.taobao.munion.base.anticheat.b.a(Math.abs(paramDouble)));
      if (paramDouble <= 0.0D)
        break label76;
    }
    label76: for (int i4 = i2 + 180; ; i4 = 180 - i2)
    {
      i1 = 0x0 | i3 & 0x3F | 0x7FC0 & i4 << 6;
      a(paramOutputStream, i1);
      return;
    }
  }

  public static void a(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write((byte)(paramInt >> 8));
    paramOutputStream.write((byte)(paramInt >> 0));
  }

  public static void a(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    if (paramString != null)
    {
      if (paramString.trim().length() > 0)
      {
        paramOutputStream.write(paramString.getBytes());
        return;
      }
      paramOutputStream.write((byte)paramString.trim().length());
      return;
    }
    paramOutputStream.write(b.b());
  }

  private static void a(String paramString, byte[] paramArrayOfByte, int paramInt)
    throws NullPointerException, NumberFormatException
  {
    if ((paramString != null) && (paramString.trim().length() > 0) && (paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      String[] arrayOfString = paramString.split("\\.");
      int i1 = paramInt + 1;
      paramArrayOfByte[paramInt] = Integer.valueOf(arrayOfString[0]).byteValue();
      paramArrayOfByte[i1] = Integer.valueOf(arrayOfString[1]).byteValue();
    }
  }

  private static void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte1.length >= paramInt + 4) && (paramArrayOfByte2 != null) && (paramArrayOfByte2.length >= 16))
    {
      int i1 = paramInt + 1;
      paramArrayOfByte2[paramInt] = paramArrayOfByte1[0];
      int i2 = i1 + 1;
      paramArrayOfByte2[i1] = paramArrayOfByte1[5];
      int i3 = i2 + 1;
      paramArrayOfByte2[i2] = paramArrayOfByte1[10];
      paramArrayOfByte2[i3] = paramArrayOfByte1[15];
    }
  }

  private static byte[] a(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4)
  {
    return new byte[] { paramByte1, paramByte2, paramByte3, paramByte4 };
  }

  public static int b(InputStream paramInputStream)
    throws IOException
  {
    int i1 = 0x0 | 0xFF000000 & paramInputStream.read() << 24 | 0xFF0000 & paramInputStream.read() << 16 | 0xFF00 & paramInputStream.read() << 8 | 0xFF & paramInputStream.read();
    if (i1 != -1)
      return i1;
    return -1;
  }

  private String b()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("emulator:" + localStringBuilder);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("osVersion:" + this.c);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("MAC:" + this.e);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("IMSI:" + this.f);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("deviceId:" + this.g);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("deviceType:" + this.h);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("manufacturer:" + this.i);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("appWidth:" + this.j);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("appHight:" + this.k);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("screenDensity:" + this.l);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("screenBright:" + this.m);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("netType:" + this.n);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("netProtocol:" + this.o);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("appRunTime:" + G);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("isConvered:-1");
    localStringBuilder.append("\r\n");
    localStringBuilder.append("adOpenness:" + this.q);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("inVisio:-1");
    localStringBuilder.append("\r\n");
    localStringBuilder.append("adWidth:" + this.t);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("adHeight:" + this.u);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchNum:" + this.J.a);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchDownX:" + this.J.e);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchDownY:" + this.J.f);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchUpX:" + this.J.c);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchUpY:" + this.J.d);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchMoveX:" + this.J.a());
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchMoveY:" + this.J.b());
    localStringBuilder.append("\r\n");
    localStringBuilder.append("touchTime:" + this.J.i);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("availPower:" + this.v);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("totalMemory:" + this.w);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("availMemory:" + this.x);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("netTraffic:" + this.y);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("packName:" + this.z);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("longitude:" + this.A);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("latitude:" + this.B);
    localStringBuilder.append("\r\n");
    localStringBuilder.append("isRoot:-1");
    localStringBuilder.append("\r\n");
    return localStringBuilder.toString();
  }

  public static void b(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write((byte)(paramInt >> 24));
    paramOutputStream.write((byte)(paramInt >> 16));
    paramOutputStream.write((byte)(paramInt >> 8));
    paramOutputStream.write((byte)(paramInt >> 0));
  }

  public static void b(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    if (paramString != null)
    {
      if (paramString.trim().length() > 0)
      {
        paramOutputStream.write((byte)paramString.length());
        paramOutputStream.write(paramString.getBytes());
        return;
      }
      paramOutputStream.write((byte)paramString.trim().length());
      return;
    }
    paramOutputStream.write(b.c());
  }

  public static String c(InputStream paramInputStream)
    throws IOException
  {
    int i1 = (byte)paramInputStream.read();
    String str;
    if (i1 > 0)
    {
      byte[] arrayOfByte = new byte[i1];
      paramInputStream.read(arrayOfByte);
      str = new String(arrayOfByte);
    }
    do
    {
      return str;
      str = null;
    }
    while (i1 != 0);
    return "";
  }

  private byte[] c()
    throws IOException
  {
    int i1 = 1;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    b(localByteArrayOutputStream, this.d);
    a(localByteArrayOutputStream, this.c);
    a(localByteArrayOutputStream, this.e);
    b(localByteArrayOutputStream, this.f);
    b(localByteArrayOutputStream, this.g);
    localByteArrayOutputStream.write(this.h);
    b(localByteArrayOutputStream, this.i);
    a(localByteArrayOutputStream, this.j);
    a(localByteArrayOutputStream, this.k);
    localByteArrayOutputStream.write((byte)this.l);
    localByteArrayOutputStream.write((byte)this.m);
    localByteArrayOutputStream.write((byte)this.n);
    localByteArrayOutputStream.write((byte)this.o);
    b(localByteArrayOutputStream, G);
    int i2;
    if (this.p)
      i2 = i1;
    while (true)
    {
      localByteArrayOutputStream.write(i2);
      localByteArrayOutputStream.write(this.q);
      label154: byte[] arrayOfByte2;
      if (this.r)
      {
        localByteArrayOutputStream.write(i1);
        a(localByteArrayOutputStream, this.t);
        a(localByteArrayOutputStream, this.u);
        a(localByteArrayOutputStream, this.J.a);
        a(localByteArrayOutputStream, this.J.e);
        a(localByteArrayOutputStream, this.J.f);
        a(localByteArrayOutputStream, this.J.c);
        a(localByteArrayOutputStream, this.J.d);
        a(localByteArrayOutputStream, this.J.a());
        a(localByteArrayOutputStream, this.J.b());
        a(localByteArrayOutputStream, (int)this.J.i);
        localByteArrayOutputStream.write((byte)this.v);
        a(localByteArrayOutputStream, this.w);
        a(localByteArrayOutputStream, this.x);
        a(localByteArrayOutputStream, this.y);
        b(localByteArrayOutputStream, this.z);
        a(localByteArrayOutputStream, this.A, 180);
        a(localByteArrayOutputStream, this.B, 180);
        localByteArrayOutputStream.write(b.a());
        b(localByteArrayOutputStream, this.D);
        b(localByteArrayOutputStream, this.E);
        byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
        arrayOfByte2 = new byte[7 + arrayOfByte1.length];
        this.H = com.taobao.newxp.common.b.b.a(arrayOfByte1);
        a(this.a, arrayOfByte2, 0);
        a(this.H, arrayOfByte2, 2);
        a((byte)this.b, arrayOfByte2, 6);
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 7, arrayOfByte1.length);
      }
      try
      {
        localByteArrayOutputStream.close();
        return Base64.encode(arrayOfByte2, 0);
        i2 = 0;
        continue;
        i1 = 0;
        break label154;
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          localIOException.printStackTrace();
          if (localByteArrayOutputStream == null);
        }
      }
      finally
      {
        if (localByteArrayOutputStream == null);
      }
    }
  }

  public static double d(InputStream paramInputStream)
    throws IOException
  {
    double d1 = 0.0D;
    int i1 = a(paramInputStream);
    double d2;
    double d3;
    if (i1 != -1)
    {
      d2 = i1 & 0x3F;
      d3 = ((i1 & 0x7FC0) >> 6) - 180.0D;
      if (d3 < d1)
        d1 = -(Math.abs(d3) + Math.abs(d2) / 60.0D);
    }
    else
    {
      return d1;
    }
    return Math.abs(d3) + Math.abs(d2) / 60.0D;
  }

  private void d()
  {
    this.a = "1.2";
    if (G == 0)
      G = com.taobao.munion.base.anticheat.b.c();
    this.b = 1;
    this.c = com.taobao.munion.base.anticheat.b.b();
    this.d = com.taobao.munion.base.anticheat.b.a();
    this.e = com.taobao.munion.base.anticheat.b.d(this.I);
    this.f = com.taobao.munion.base.anticheat.b.b(this.I);
    this.g = com.taobao.munion.base.anticheat.b.c(this.I);
    this.h = 0;
    this.i = com.taobao.munion.base.anticheat.b.d();
    this.j = com.taobao.munion.base.anticheat.b.k(this.I);
    this.k = com.taobao.munion.base.anticheat.b.l(this.I);
    this.p = false;
    this.r = false;
    this.q = -1;
    this.v = -1;
    this.l = com.taobao.munion.base.anticheat.b.e(this.I);
    this.m = com.taobao.munion.base.anticheat.b.f(this.I);
    this.n = com.taobao.munion.base.anticheat.b.n(this.I);
    this.o = com.taobao.munion.base.anticheat.b.o(this.I);
    this.w = com.taobao.munion.base.anticheat.b.g(this.I);
    this.x = com.taobao.munion.base.anticheat.b.h(this.I);
    this.y = -1;
    this.z = com.taobao.munion.base.anticheat.b.i(this.I);
    this.C = false;
    this.D = com.taobao.munion.base.anticheat.b.p(this.I);
    this.E = "";
  }

  public String a()
    throws IOException
  {
    this.J = ((b)a.a().b(0)).a();
    return com.taobao.munion.base.anticheat.b.a(new String(c()));
  }

  public static class a
  {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 0;
    public static final int f = 1;
    public static final int g = 2;
    public static final int h = 0;
    public static final int i = 1;
    public static final int j = 2;
    public static final int k = 3;
  }

  public static class b
  {
    public static final int a = 0;
    public static final int b = -1;
    public static final double c = 0.0D;
    private static final byte d = 1;
    private static final byte e = 0;
    private static final byte[] f = { 0 };
    private static final byte[] g = { -1 };
    private static final int h = 180;
    private static final String i = "1.2";
    private static final String j = "\r\n";
    private static final byte[] k = { -1, -1, -1, -1, -1, -1, -1 };
    private static final byte[] l = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.c
 * JD-Core Version:    0.6.2
 */