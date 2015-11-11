package com.tencent.weibo.sdk.android.component.sso.tools;

public class Base64
{
  public static byte[] decode(String paramString)
  {
    int i = 0;
    byte[] arrayOfByte;
    int k;
    int m;
    for (int j = -1 + paramString.length(); ; j--)
    {
      if (paramString.charAt(j) != '=')
      {
        arrayOfByte = new byte[6 * paramString.length() / 8 - i];
        k = 0;
        m = 0;
        if (m < paramString.length())
          break;
        return arrayOfByte;
      }
      i++;
    }
    int n = (getValue(paramString.charAt(m)) << 18) + (getValue(paramString.charAt(m + 1)) << 12) + (getValue(paramString.charAt(m + 2)) << 6) + getValue(paramString.charAt(m + 3));
    for (int i1 = 0; ; i1++)
    {
      if ((i1 >= 3) || (k + i1 >= arrayOfByte.length))
      {
        k += 3;
        m += 4;
        break;
      }
      arrayOfByte[(k + i1)] = ((byte)(0xFF & n >> 8 * (2 - i1)));
    }
  }

  public static String encode(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i += 3)
    {
      if (i >= paramArrayOfByte.length)
        return localStringBuffer.toString();
      localStringBuffer.append(encodeBlock(paramArrayOfByte, i));
    }
  }

  protected static char[] encodeBlock(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    int j = -1 + (paramArrayOfByte.length - paramInt);
    int k;
    int m;
    char[] arrayOfChar;
    if (j >= 2)
    {
      k = 2;
      m = 0;
      if (m <= k)
        break label72;
      arrayOfChar = new char[4];
    }
    for (int i2 = 0; ; i2++)
    {
      if (i2 >= 4)
      {
        if (j < 1)
          arrayOfChar[2] = '=';
        if (j < 2)
          arrayOfChar[3] = '=';
        return arrayOfChar;
        k = j;
        break;
        label72: int n = paramArrayOfByte[(paramInt + m)];
        if (n < 0);
        for (int i1 = n + 256; ; i1 = n)
        {
          i += (i1 << 8 * (2 - m));
          m++;
          break;
        }
      }
      arrayOfChar[i2] = getChar(0x3F & i >>> 6 * (3 - i2));
    }
  }

  protected static char getChar(int paramInt)
  {
    int i = 63;
    if ((paramInt >= 0) && (paramInt <= 25))
      i = (char)(paramInt + 65);
    do
    {
      return i;
      if ((paramInt >= 26) && (paramInt <= 51))
        return (char)(97 + (paramInt - 26));
      if ((paramInt >= 52) && (paramInt <= 61))
        return (char)(48 + (paramInt - 52));
      if (paramInt == 62)
        return '+';
    }
    while (paramInt != i);
    return '/';
  }

  protected static int getValue(char paramChar)
  {
    if ((paramChar >= 'A') && (paramChar <= 'Z'))
      return paramChar - 'A';
    if ((paramChar >= 'a') && (paramChar <= 'z'))
      return 26 + (paramChar - 'a');
    if ((paramChar >= '0') && (paramChar <= '9'))
      return 52 + (paramChar - '0');
    if (paramChar == '+')
      return 62;
    if (paramChar == '/')
      return 63;
    if (paramChar == '=')
      return 0;
    return -1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.sso.tools.Base64
 * JD-Core Version:    0.6.2
 */