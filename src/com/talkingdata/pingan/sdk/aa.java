package com.talkingdata.pingan.sdk;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.InflaterInputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class aa
{
  static final boolean a = false;
  private static final String c = "UTF-8";
  private static final ExecutorService d;
  private static final byte e = 61;
  private static final String f = "US-ASCII";
  private static final byte[] g;

  static
  {
    if (!aa.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      b = bool;
      d = Executors.newSingleThreadExecutor();
      g = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
      return;
    }
  }

  public static String a(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
      localStringBuilder.append(Integer.toHexString(0xFF & paramArrayOfByte[j]));
    return localStringBuilder.toString();
  }

  public static String a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = b(paramArrayOfByte, paramInt1, paramInt2);
    try
    {
      String str = new String(arrayOfByte, "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return new String(arrayOfByte);
  }

  public static void a(Runnable paramRunnable)
  {
    d.execute(paramRunnable);
  }

  public static boolean a(Context paramContext, String paramString)
  {
    return paramContext.checkCallingOrSelfPermission(paramString) == 0;
  }

  public static final boolean a(String paramString)
  {
    return (paramString == null) || ("".equals(paramString.trim()));
  }

  private static byte[] a(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    byte[] arrayOfByte = g;
    int i;
    if (paramInt2 > 0)
    {
      i = paramArrayOfByte1[paramInt1] << 24 >>> 8;
      label20: if (paramInt2 <= 1)
        break label108;
    }
    int n;
    label108: for (int j = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16; ; j = 0)
    {
      int k = j | i;
      int m = 0;
      if (paramInt2 > 2)
        m = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
      n = m | k;
      switch (paramInt2)
      {
      default:
        return paramArrayOfByte2;
        i = 0;
        break label20;
      case 3:
      case 2:
      case 1:
      }
    }
    paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
    paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(0x3F & n >>> 6)];
    paramArrayOfByte2[(paramInt3 + 3)] = arrayOfByte[(n & 0x3F)];
    return paramArrayOfByte2;
    paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
    paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(0x3F & n >>> 6)];
    paramArrayOfByte2[(paramInt3 + 3)] = 61;
    return paramArrayOfByte2;
    paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
    paramArrayOfByte2[(paramInt3 + 2)] = 61;
    paramArrayOfByte2[(paramInt3 + 3)] = 61;
    return paramArrayOfByte2;
  }

  public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    InflaterInputStream localInflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(paramArrayOfByte2));
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    while (true)
    {
      int i = localInflaterInputStream.read();
      if (i == -1)
        break;
      localByteArrayOutputStream.write(i);
    }
    SecureRandom localSecureRandom = new SecureRandom();
    DESKeySpec localDESKeySpec = new DESKeySpec(paramArrayOfByte1);
    SecretKey localSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
    Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    localCipher.init(2, localSecretKey, new IvParameterSpec(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 }), localSecureRandom);
    return localCipher.doFinal(localByteArrayOutputStream.toByteArray());
  }

  public static byte[] a(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    for (int i = 0; i < paramArrayOfInt1.length; i++)
    {
      paramArrayOfInt1[i] = (paramArrayOfInt1[i] * paramArrayOfInt2[(-1 + paramArrayOfInt2.length - i)] - paramArrayOfInt1[(-1 + paramArrayOfInt1.length - i)] * paramArrayOfInt2[i] + "kiG9w0BAQUFADCBqjELMAkGA0JFSUpJTkcxEDAOBgNVBAcMB0JFSUpJTkcxFjAUBgNVB".charAt(i));
      paramArrayOfInt2[i] = (paramArrayOfInt2[i] * paramArrayOfInt1[(-1 + paramArrayOfInt1.length - i)] + paramArrayOfInt2[(-1 + paramArrayOfInt2.length - i)] * paramArrayOfInt1[i] - "kiG9w0BAQUFADCBqjELMAkGA0JFSUpJTkcxEDAOBgNVBAcMB0JFSUpJTkcxFjAUBgNVB".charAt(-1 + "kiG9w0BAQUFADCBqjELMAkGA0JFSUpJTkcxEDAOBgNVBAcMB0JFSUpJTkcxFjAUBgNVB".length() - i));
    }
    return (Arrays.toString(paramArrayOfInt1) + Arrays.hashCode(paramArrayOfInt2)).getBytes();
  }

  public static String b(String paramString)
  {
    try
    {
      String str = a(MessageDigest.getInstance("MD5").digest(paramString.getBytes("UTF-8")));
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String b(byte[] paramArrayOfByte)
  {
    String str1;
    try
    {
      String str2 = a(paramArrayOfByte, 0, paramArrayOfByte.length);
      str1 = str2;
      if ((!b) && (str1 == null))
        throw new AssertionError();
    }
    catch (IOException localIOException)
    {
      boolean bool;
      do
      {
        bool = b;
        str1 = null;
      }
      while (bool);
      throw new AssertionError(localIOException.getMessage());
    }
    return str1;
  }

  public static byte[] b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte == null)
      throw new NullPointerException("Cannot serialize a null array.");
    if (paramInt1 < 0)
      throw new IllegalArgumentException("Cannot have negative offset: " + paramInt1);
    if (paramInt2 < 0)
      throw new IllegalArgumentException("Cannot have length offset: " + paramInt2);
    if (paramInt1 + paramInt2 > paramArrayOfByte.length)
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      arrayOfObject[2] = Integer.valueOf(paramArrayOfByte.length);
      throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", arrayOfObject));
    }
    int i = 4 * (paramInt2 / 3);
    if (paramInt2 % 3 > 0);
    byte[] arrayOfByte1;
    int m;
    int n;
    for (int j = 4; ; j = 0)
    {
      arrayOfByte1 = new byte[j + i];
      int k = paramInt2 - 2;
      m = 0;
      n = 0;
      while (n < k)
      {
        a(paramArrayOfByte, n + paramInt1, 3, arrayOfByte1, m);
        n += 3;
        m += 4;
      }
    }
    if (n < paramInt2)
    {
      a(paramArrayOfByte, n + paramInt1, paramInt2 - n, arrayOfByte1, m);
      m += 4;
    }
    if (m <= -1 + arrayOfByte1.length)
    {
      byte[] arrayOfByte2 = new byte[m];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, m);
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.aa
 * JD-Core Version:    0.6.2
 */