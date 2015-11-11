package com.tencent.weibo.sdk.android.component.sso.tools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Cryptor
{
  public static final int QUOTIENT = 79764919;
  public static final int SALT_LEN = 2;
  public static final int ZERO_LEN = 7;
  private int contextStart;
  private int crypt;
  private boolean header = true;
  private byte[] key;
  private byte[] out;
  private int padding;
  private byte[] plain;
  private int pos;
  private int preCrypt;
  private byte[] prePlain;
  private Random random = new Random();

  public static int CRC32Hash(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    int j = -1;
    int m;
    int n;
    for (int k = 0; ; k++)
    {
      if (k >= i)
        return j ^ 0xFFFFFFFF;
      m = paramArrayOfByte[k];
      n = 0;
      if (n < 8)
        break;
    }
    if ((m ^ j) >>> 31 == 1)
      j = 0x4C11DB7 ^ j << 1;
    while (true)
    {
      m = (byte)(m << 1);
      n++;
      break;
      j <<= 1;
    }
  }

  public static byte[] MD5Hash(byte[] paramArrayOfByte, int paramInt)
  {
    return new byte[2];
  }

  public static int _4bytesDecryptAFrame(long paramLong, byte[] paramArrayOfByte)
  {
    short[] arrayOfShort1 = new short[2];
    arrayOfShort1[0] = ((short)(int)(0xFFFF & paramLong));
    arrayOfShort1[1] = ((short)(int)(paramLong >> 16));
    short[] arrayOfShort2 = new short[4];
    arrayOfShort2[0] = ((short)(paramArrayOfByte[1] << 8 | paramArrayOfByte[0]));
    arrayOfShort2[1] = ((short)(paramArrayOfByte[3] << 8 | paramArrayOfByte[2]));
    arrayOfShort2[2] = ((short)(paramArrayOfByte[5] << 8 | paramArrayOfByte[4]));
    arrayOfShort2[3] = ((short)(paramArrayOfByte[7] << 8 | paramArrayOfByte[6]));
    int i = arrayOfShort1[0];
    int j = arrayOfShort1[1];
    int k = (short)412640;
    int n;
    for (int m = 32; ; m = n)
    {
      n = (short)(m - 1);
      if (m <= 0)
      {
        arrayOfShort1[0] = i;
        arrayOfShort1[1] = j;
        return arrayOfShort1[1] << 16 | 0xFFFF & arrayOfShort1[0];
      }
      j = (short)(j - ((i << 4) + arrayOfShort2[2] ^ i + k ^ (i >> 5) + arrayOfShort2[3]));
      i = (short)(i - ((j << 4) + arrayOfShort2[0] ^ j + k ^ (j >> 5) + arrayOfShort2[1]));
      k = (short)(k - 12895);
    }
  }

  public static byte[] _4bytesEncryptAFrame(int paramInt, byte[] paramArrayOfByte)
  {
    short[] arrayOfShort1 = new short[2];
    arrayOfShort1[0] = ((short)(0xFFFF & paramInt));
    arrayOfShort1[1] = ((short)(paramInt >>> 16));
    short[] arrayOfShort2 = new short[4];
    arrayOfShort2[0] = ((short)(paramArrayOfByte[1] << 8 | paramArrayOfByte[0]));
    arrayOfShort2[1] = ((short)(paramArrayOfByte[3] << 8 | paramArrayOfByte[2]));
    arrayOfShort2[2] = ((short)(paramArrayOfByte[5] << 8 | paramArrayOfByte[4]));
    arrayOfShort2[3] = ((short)(paramArrayOfByte[7] << 8 | paramArrayOfByte[6]));
    int i = arrayOfShort1[0];
    int j = arrayOfShort1[1];
    int k = 0;
    int n;
    for (int m = 32; ; m = n)
    {
      n = (short)(m - 1);
      if (m <= 0)
      {
        byte[] arrayOfByte = new byte[4];
        arrayOfByte[0] = ((byte)(j >> 8));
        arrayOfByte[1] = ((byte)(j & 0xFF));
        arrayOfByte[2] = ((byte)(i >> 8));
        arrayOfByte[3] = ((byte)(i & 0xFF));
        return arrayOfByte;
      }
      k = (short)(k + 12895);
      i = (short)(i + ((j << 4) + arrayOfShort2[0] ^ j + k ^ (j >> 5) + arrayOfShort2[1]));
      j = (short)(j + ((i << 4) + arrayOfShort2[2] ^ i + k ^ (i >> 5) + arrayOfShort2[3]));
    }
  }

  private byte[] decipher(byte[] paramArrayOfByte)
  {
    return decipher(paramArrayOfByte, 0);
  }

  private byte[] decipher(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      long l1 = getUnsignedInt(paramArrayOfByte, paramInt, 4);
      long l2 = getUnsignedInt(paramArrayOfByte, paramInt + 4, 4);
      long l3 = getUnsignedInt(this.key, 0, 4);
      long l4 = getUnsignedInt(this.key, 4, 4);
      long l5 = getUnsignedInt(this.key, 8, 4);
      long l6 = getUnsignedInt(this.key, 12, 4);
      long l7 = 0xE3779B90 & 0xFFFFFFFF;
      long l8 = 0x9E3779B9 & 0xFFFFFFFF;
      int j;
      for (int i = 16; ; i = j)
      {
        j = i - 1;
        if (i <= 0)
        {
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(8);
          DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
          localDataOutputStream.writeInt((int)l1);
          localDataOutputStream.writeInt((int)l2);
          localDataOutputStream.close();
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          return arrayOfByte;
        }
        l2 = 0xFFFFFFFF & l2 - (l5 + (l1 << 4) ^ l1 + l7 ^ l6 + (l1 >>> 5));
        l1 = 0xFFFFFFFF & l1 - (l3 + (l2 << 4) ^ l2 + l7 ^ l4 + (l2 >>> 5));
        l7 = 0xFFFFFFFF & l7 - l8;
      }
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private boolean decrypt8Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    boolean bool = true;
    for (this.pos = 0; ; this.pos = (1 + this.pos))
    {
      if (this.pos >= 8)
      {
        this.prePlain = decipher(this.prePlain);
        if (this.prePlain != null)
          break;
        bool = false;
      }
      while (this.contextStart + this.pos >= paramInt2)
        return bool;
      byte[] arrayOfByte = this.prePlain;
      int i = this.pos;
      arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ paramArrayOfByte[(paramInt1 + this.crypt + this.pos)]));
    }
    this.contextStart = (8 + this.contextStart);
    this.crypt = (8 + this.crypt);
    this.pos = 0;
    return bool;
  }

  private byte[] encipher(byte[] paramArrayOfByte)
  {
    try
    {
      long l1 = getUnsignedInt(paramArrayOfByte, 0, 4);
      long l2 = getUnsignedInt(paramArrayOfByte, 4, 4);
      long l3 = getUnsignedInt(this.key, 0, 4);
      long l4 = getUnsignedInt(this.key, 4, 4);
      long l5 = getUnsignedInt(this.key, 8, 4);
      long l6 = getUnsignedInt(this.key, 12, 4);
      long l7 = 0L;
      long l8 = 0x9E3779B9 & 0xFFFFFFFF;
      int j;
      for (int i = 16; ; i = j)
      {
        j = i - 1;
        if (i <= 0)
        {
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(8);
          DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
          localDataOutputStream.writeInt((int)l1);
          localDataOutputStream.writeInt((int)l2);
          localDataOutputStream.close();
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          return arrayOfByte;
        }
        l7 = 0xFFFFFFFF & l7 + l8;
        l1 = 0xFFFFFFFF & l1 + (l3 + (l2 << 4) ^ l2 + l7 ^ l4 + (l2 >>> 5));
        l2 = 0xFFFFFFFF & l2 + (l5 + (l1 << 4) ^ l1 + l7 ^ l6 + (l1 >>> 5));
      }
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private void encrypt8Bytes()
  {
    this.pos = 0;
    if (this.pos >= 8)
      System.arraycopy(encipher(this.plain), 0, this.out, this.crypt, 8);
    for (this.pos = 0; ; this.pos = (1 + this.pos))
    {
      if (this.pos >= 8)
      {
        System.arraycopy(this.plain, 0, this.prePlain, 0, 8);
        this.preCrypt = this.crypt;
        this.crypt = (8 + this.crypt);
        this.pos = 0;
        this.header = false;
        return;
        if (this.header)
        {
          byte[] arrayOfByte2 = this.plain;
          int j = this.pos;
          arrayOfByte2[j] = ((byte)(arrayOfByte2[j] ^ this.prePlain[this.pos]));
        }
        while (true)
        {
          this.pos = (1 + this.pos);
          break;
          byte[] arrayOfByte1 = this.plain;
          int i = this.pos;
          arrayOfByte1[i] = ((byte)(arrayOfByte1[i] ^ this.out[(this.preCrypt + this.pos)]));
        }
      }
      byte[] arrayOfByte3 = this.out;
      int k = this.crypt + this.pos;
      arrayOfByte3[k] = ((byte)(arrayOfByte3[k] ^ this.prePlain[this.pos]));
    }
  }

  private byte[] getRandomByte(int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    this.random.nextBytes(arrayOfByte);
    return arrayOfByte;
  }

  public static long getUnsignedInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l = 0L;
    int i;
    if (paramInt2 > 8)
      i = paramInt1 + 8;
    for (int j = paramInt1; ; j++)
    {
      if (j >= i)
      {
        return 0xFFFFFFFF & l | l >>> 32;
        i = paramInt1 + paramInt2;
        break;
      }
      l = l << 8 | 0xFF & paramArrayOfByte[j];
    }
  }

  private int rand()
  {
    return this.random.nextInt();
  }

  public byte[] decrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    this.preCrypt = 0;
    this.crypt = 0;
    this.key = paramArrayOfByte2;
    byte[] arrayOfByte = new byte[paramInt1 + 8];
    if ((paramInt2 % 8 != 0) || (paramInt2 < 16));
    int i;
    do
    {
      return null;
      this.prePlain = decipher(paramArrayOfByte1, paramInt1);
      this.pos = (0x7 & this.prePlain[0]);
      i = -10 + (paramInt2 - this.pos);
    }
    while (i < 0);
    int j = paramInt1;
    label81: int k;
    if (j >= arrayOfByte.length)
    {
      this.out = new byte[i];
      this.preCrypt = 0;
      this.crypt = 8;
      this.contextStart = 8;
      this.pos = (1 + this.pos);
      this.padding = 1;
      label129: if (this.padding <= 2)
        break label176;
      k = 0;
      label140: if (i != 0)
        break label229;
    }
    for (this.padding = 1; ; this.padding = (1 + this.padding))
    {
      if (this.padding >= 8)
      {
        return this.out;
        arrayOfByte[j] = 0;
        j++;
        break label81;
        label176: if (this.pos < 8)
        {
          this.pos = (1 + this.pos);
          this.padding = (1 + this.padding);
        }
        if (this.pos != 8)
          break label129;
        arrayOfByte = paramArrayOfByte1;
        if (decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2))
          break label129;
        return null;
        label229: if (this.pos < 8)
        {
          this.out[k] = ((byte)(arrayOfByte[(paramInt1 + this.preCrypt + this.pos)] ^ this.prePlain[this.pos]));
          k++;
          i--;
          this.pos = (1 + this.pos);
        }
        if (this.pos != 8)
          break label140;
        arrayOfByte = paramArrayOfByte1;
        this.preCrypt = (-8 + this.crypt);
        if (decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2))
          break label140;
        return null;
      }
      if (this.pos < 8)
      {
        if ((arrayOfByte[(paramInt1 + this.preCrypt + this.pos)] ^ this.prePlain[this.pos]) != 0)
          break;
        this.pos = (1 + this.pos);
      }
      if (this.pos == 8)
      {
        arrayOfByte = paramArrayOfByte1;
        this.preCrypt = this.crypt;
        if (!decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2))
          break;
      }
    }
  }

  public byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return decrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }

  public byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    byte[] arrayOfByte = decrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
    if (arrayOfByte == null)
      arrayOfByte = getRandomByte(paramInt);
    return arrayOfByte;
  }

  public byte[] encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    this.plain = new byte[8];
    this.prePlain = new byte[8];
    this.pos = 1;
    this.padding = 0;
    this.preCrypt = 0;
    this.crypt = 0;
    this.key = paramArrayOfByte2;
    this.header = true;
    this.pos = ((paramInt2 + 10) % 8);
    if (this.pos != 0)
      this.pos = (8 - this.pos);
    this.out = new byte[10 + (paramInt2 + this.pos)];
    this.plain[0] = ((byte)(0xF8 & rand() | this.pos));
    int i = 1;
    int j;
    if (i > this.pos)
    {
      this.pos = (1 + this.pos);
      j = 0;
      label136: if (j < 8)
        break label204;
      this.padding = 1;
    }
    int m;
    while (true)
    {
      if (this.padding > 2)
      {
        m = paramInt1;
        if (paramInt2 > 0)
          break label287;
        this.padding = 1;
        label168: if (this.padding <= 7)
          break label354;
        return this.out;
        this.plain[i] = ((byte)(0xFF & rand()));
        i++;
        break;
        label204: this.prePlain[j] = 0;
        j++;
        break label136;
      }
      if (this.pos < 8)
      {
        byte[] arrayOfByte1 = this.plain;
        int k = this.pos;
        this.pos = (k + 1);
        arrayOfByte1[k] = ((byte)(0xFF & rand()));
        this.padding = (1 + this.padding);
      }
      if (this.pos == 8)
        encrypt8Bytes();
    }
    label287: int n;
    if (this.pos < 8)
    {
      byte[] arrayOfByte2 = this.plain;
      int i1 = this.pos;
      this.pos = (i1 + 1);
      n = m + 1;
      arrayOfByte2[i1] = paramArrayOfByte1[m];
      paramInt2--;
    }
    while (true)
    {
      if (this.pos == 8)
        encrypt8Bytes();
      m = n;
      break;
      label354: if (this.pos < 8)
      {
        byte[] arrayOfByte3 = this.plain;
        int i2 = this.pos;
        this.pos = (i2 + 1);
        arrayOfByte3[i2] = 0;
        this.padding = (1 + this.padding);
      }
      if (this.pos != 8)
        break label168;
      encrypt8Bytes();
      break label168;
      n = m;
    }
  }

  public byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return encrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.sso.tools.Cryptor
 * JD-Core Version:    0.6.2
 */