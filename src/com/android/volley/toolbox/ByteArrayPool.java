package com.android.volley.toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ByteArrayPool
{
  protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator()
  {
    public int compare(byte[] paramAnonymousArrayOfByte1, byte[] paramAnonymousArrayOfByte2)
    {
      return paramAnonymousArrayOfByte1.length - paramAnonymousArrayOfByte2.length;
    }
  };
  private List<byte[]> mBuffersByLastUse = new LinkedList();
  private List<byte[]> mBuffersBySize = new ArrayList(64);
  private int mCurrentSize = 0;
  private final int mSizeLimit;

  public ByteArrayPool(int paramInt)
  {
    this.mSizeLimit = paramInt;
  }

  private void trim()
  {
    try
    {
      while (true)
      {
        int i = this.mCurrentSize;
        int j = this.mSizeLimit;
        if (i <= j)
          return;
        byte[] arrayOfByte = (byte[])this.mBuffersByLastUse.remove(0);
        this.mBuffersBySize.remove(arrayOfByte);
        this.mCurrentSize -= arrayOfByte.length;
      }
    }
    finally
    {
    }
  }

  public byte[] getBuf(int paramInt)
  {
    for (int i = 0; ; i++)
      try
      {
        byte[] arrayOfByte;
        if (i >= this.mBuffersBySize.size())
          arrayOfByte = new byte[paramInt];
        while (true)
        {
          return arrayOfByte;
          arrayOfByte = (byte[])this.mBuffersBySize.get(i);
          if (arrayOfByte.length < paramInt)
            break;
          this.mCurrentSize -= arrayOfByte.length;
          this.mBuffersBySize.remove(i);
          this.mBuffersByLastUse.remove(arrayOfByte);
        }
      }
      finally
      {
      }
  }

  public void returnBuf(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null);
    try
    {
      int i = paramArrayOfByte.length;
      int j = this.mSizeLimit;
      if (i > j);
      while (true)
      {
        return;
        this.mBuffersByLastUse.add(paramArrayOfByte);
        int k = Collections.binarySearch(this.mBuffersBySize, paramArrayOfByte, BUF_COMPARATOR);
        if (k < 0)
          k = -1 + -k;
        this.mBuffersBySize.add(k, paramArrayOfByte);
        this.mCurrentSize += paramArrayOfByte.length;
        trim();
      }
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.ByteArrayPool
 * JD-Core Version:    0.6.2
 */