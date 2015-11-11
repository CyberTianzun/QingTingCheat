package com.alibaba.fastjson.asm;

public class ByteVector
{
  byte[] data;
  int length;

  public ByteVector()
  {
    this.data = new byte[64];
  }

  public ByteVector(int paramInt)
  {
    this.data = new byte[paramInt];
  }

  private void enlarge(int paramInt)
  {
    int i = 2 * this.data.length;
    int j = paramInt + this.length;
    if (i > j);
    while (true)
    {
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(this.data, 0, arrayOfByte, 0, this.length);
      this.data = arrayOfByte;
      return;
      i = j;
    }
  }

  ByteVector put11(int paramInt1, int paramInt2)
  {
    int i = this.length;
    if (i + 2 > this.data.length)
      enlarge(2);
    byte[] arrayOfByte = this.data;
    int j = i + 1;
    arrayOfByte[i] = ((byte)paramInt1);
    int k = j + 1;
    arrayOfByte[j] = ((byte)paramInt2);
    this.length = k;
    return this;
  }

  ByteVector put12(int paramInt1, int paramInt2)
  {
    int i = this.length;
    if (i + 3 > this.data.length)
      enlarge(3);
    byte[] arrayOfByte = this.data;
    int j = i + 1;
    arrayOfByte[i] = ((byte)paramInt1);
    int k = j + 1;
    arrayOfByte[j] = ((byte)(paramInt2 >>> 8));
    int m = k + 1;
    arrayOfByte[k] = ((byte)paramInt2);
    this.length = m;
    return this;
  }

  public ByteVector putByte(int paramInt)
  {
    int i = this.length;
    if (i + 1 > this.data.length)
      enlarge(1);
    byte[] arrayOfByte = this.data;
    int j = i + 1;
    arrayOfByte[i] = ((byte)paramInt);
    this.length = j;
    return this;
  }

  public ByteVector putByteArray(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 + this.length > this.data.length)
      enlarge(paramInt2);
    if (paramArrayOfByte != null)
      System.arraycopy(paramArrayOfByte, paramInt1, this.data, this.length, paramInt2);
    this.length = (paramInt2 + this.length);
    return this;
  }

  public ByteVector putInt(int paramInt)
  {
    int i = this.length;
    if (i + 4 > this.data.length)
      enlarge(4);
    byte[] arrayOfByte = this.data;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(paramInt >>> 24));
    int k = j + 1;
    arrayOfByte[j] = ((byte)(paramInt >>> 16));
    int m = k + 1;
    arrayOfByte[k] = ((byte)(paramInt >>> 8));
    int n = m + 1;
    arrayOfByte[m] = ((byte)paramInt);
    this.length = n;
    return this;
  }

  public ByteVector putShort(int paramInt)
  {
    int i = this.length;
    if (i + 2 > this.data.length)
      enlarge(2);
    byte[] arrayOfByte = this.data;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(paramInt >>> 8));
    int k = j + 1;
    arrayOfByte[j] = ((byte)paramInt);
    this.length = k;
    return this;
  }

  public ByteVector putUTF8(String paramString)
  {
    int i = paramString.length();
    int j = this.length;
    if (i + (j + 2) > this.data.length)
      enlarge(i + 2);
    byte[] arrayOfByte = this.data;
    int k = j + 1;
    arrayOfByte[j] = ((byte)(i >>> 8));
    int m = k + 1;
    arrayOfByte[k] = ((byte)i);
    int n = 0;
    int i1 = m;
    while (n < i)
    {
      int i2 = paramString.charAt(n);
      if ((i2 >= 1) && (i2 <= 127))
      {
        int i3 = i1 + 1;
        arrayOfByte[i1] = ((byte)i2);
        n++;
        i1 = i3;
      }
      else
      {
        throw new UnsupportedOperationException();
      }
    }
    this.length = i1;
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.asm.ByteVector
 * JD-Core Version:    0.6.2
 */