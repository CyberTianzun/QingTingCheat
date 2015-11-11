package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.security.MessageDigest;
import java.util.Locale;

public class c
{
  private final byte[] a = { 0, 0, 0, 0, 0, 0, 0, 0 };
  private String b = "1.0";
  private String c = null;
  private byte[] d = null;
  private byte[] e = null;
  private byte[] f = null;
  private int g = 0;
  private int h = 0;
  private int i = 0;
  private byte[] j = null;
  private byte[] k = null;

  private c(byte[] paramArrayOfByte1, String paramString, byte[] paramArrayOfByte2)
    throws Exception
  {
    if ((paramArrayOfByte1 == null) || (paramArrayOfByte1.length == 0))
      throw new Exception("entity is null or empty");
    this.c = paramString;
    this.i = paramArrayOfByte1.length;
    this.j = bu.a(paramArrayOfByte1);
    this.h = ((int)(System.currentTimeMillis() / 1000L));
    this.k = paramArrayOfByte2;
  }

  public static c a(Context paramContext, String paramString, byte[] paramArrayOfByte)
  {
    try
    {
      String str1 = bi.p(paramContext);
      String str2 = bi.f(paramContext);
      SharedPreferences localSharedPreferences = u.a(paramContext);
      String str3 = localSharedPreferences.getString("signature", null);
      int m = localSharedPreferences.getInt("serial", 1);
      c localc = new c(paramArrayOfByte, paramString, (str2 + str1).getBytes());
      localc.a(str3);
      localc.a(m);
      localc.b();
      localSharedPreferences.edit().putInt("serial", m + 1).putString("signature", localc.a()).commit();
      return localc;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static byte[] a(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    int m = 0;
    byte[] arrayOfByte1 = a(this.k);
    byte[] arrayOfByte2 = a(this.j);
    int n = arrayOfByte1.length;
    byte[] arrayOfByte3 = new byte[n * 2];
    for (int i1 = 0; i1 < n; i1++)
    {
      arrayOfByte3[(i1 * 2)] = arrayOfByte2[i1];
      arrayOfByte3[(1 + i1 * 2)] = arrayOfByte1[i1];
    }
    for (int i2 = 0; i2 < 2; i2++)
    {
      arrayOfByte3[i2] = paramArrayOfByte[i2];
      arrayOfByte3[(-1 + (arrayOfByte3.length - i2))] = paramArrayOfByte[(-1 + (paramArrayOfByte.length - i2))];
    }
    byte[] arrayOfByte4 = new byte[4];
    arrayOfByte4[0] = ((byte)(paramInt & 0xFF));
    arrayOfByte4[1] = ((byte)(0xFF & paramInt >> 8));
    arrayOfByte4[2] = ((byte)(0xFF & paramInt >> 16));
    arrayOfByte4[3] = ((byte)(paramInt >>> 24));
    while (m < arrayOfByte3.length)
    {
      arrayOfByte3[m] = ((byte)(arrayOfByte3[m] ^ arrayOfByte4[(m % 4)]));
      m++;
    }
    return arrayOfByte3;
  }

  public static String b(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int m = 0; m < paramArrayOfByte.length; m++)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(paramArrayOfByte[m]);
      localStringBuffer.append(String.format("%02X", arrayOfObject));
    }
    return localStringBuffer.toString().toLowerCase(Locale.US);
  }

  public static byte[] b(String paramString)
  {
    byte[] arrayOfByte = null;
    if (paramString == null);
    while (true)
    {
      return arrayOfByte;
      int m = paramString.length();
      int n = m % 2;
      arrayOfByte = null;
      if (n == 0)
      {
        arrayOfByte = new byte[m / 2];
        for (int i1 = 0; i1 < m; i1 += 2)
          arrayOfByte[(i1 / 2)] = ((byte)Integer.valueOf(paramString.substring(i1, i1 + 2), 16).intValue());
      }
    }
  }

  private byte[] d()
  {
    return a(this.a, (int)(System.currentTimeMillis() / 1000L));
  }

  private byte[] e()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(b(this.d));
    localStringBuilder.append(this.g);
    localStringBuilder.append(this.h);
    localStringBuilder.append(this.i);
    localStringBuilder.append(b(this.e));
    return a(localStringBuilder.toString().getBytes());
  }

  public String a()
  {
    return b(this.d);
  }

  public void a(int paramInt)
  {
    this.g = paramInt;
  }

  public void a(String paramString)
  {
    this.d = b(paramString);
  }

  public void b()
  {
    if (this.d == null)
      this.d = d();
    this.e = a(this.d, this.h);
    this.f = e();
  }

  public byte[] c()
  {
    bh localbh = new bh();
    localbh.a(this.b);
    localbh.b(this.c);
    localbh.c(b(this.d));
    localbh.a(this.g);
    localbh.c(this.h);
    localbh.d(this.i);
    localbh.a(this.j);
    localbh.d(b(this.e));
    localbh.e(b(this.f));
    try
    {
      byte[] arrayOfByte = new ci().a(localbh);
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = this.b;
    localStringBuilder.append(String.format("version : %s\n", arrayOfObject1));
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = this.c;
    localStringBuilder.append(String.format("address : %s\n", arrayOfObject2));
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = b(this.d);
    localStringBuilder.append(String.format("signature : %s\n", arrayOfObject3));
    Object[] arrayOfObject4 = new Object[1];
    arrayOfObject4[0] = Integer.valueOf(this.g);
    localStringBuilder.append(String.format("serial : %s\n", arrayOfObject4));
    Object[] arrayOfObject5 = new Object[1];
    arrayOfObject5[0] = Integer.valueOf(this.h);
    localStringBuilder.append(String.format("timestamp : %d\n", arrayOfObject5));
    Object[] arrayOfObject6 = new Object[1];
    arrayOfObject6[0] = Integer.valueOf(this.i);
    localStringBuilder.append(String.format("length : %d\n", arrayOfObject6));
    Object[] arrayOfObject7 = new Object[1];
    arrayOfObject7[0] = b(this.e);
    localStringBuilder.append(String.format("guid : %s\n", arrayOfObject7));
    Object[] arrayOfObject8 = new Object[1];
    arrayOfObject8[0] = b(this.f);
    localStringBuilder.append(String.format("checksum : %s ", arrayOfObject8));
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.c
 * JD-Core Version:    0.6.2
 */