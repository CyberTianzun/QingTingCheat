package com.google.gson.stream;

final class StringPool
{
  private final String[] pool = new String[512];

  public String get(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (int j = paramInt1; j < paramInt1 + paramInt2; j++)
      i = i * 31 + paramArrayOfChar[j];
    int k = i ^ (i >>> 20 ^ i >>> 12);
    int m = (k ^ (k >>> 7 ^ k >>> 4)) & -1 + this.pool.length;
    String str1 = this.pool[m];
    if ((str1 == null) || (str1.length() != paramInt2))
    {
      String str2 = new String(paramArrayOfChar, paramInt1, paramInt2);
      this.pool[m] = str2;
      return str2;
    }
    for (int n = 0; n < paramInt2; n++)
      if (str1.charAt(n) != paramArrayOfChar[(paramInt1 + n)])
      {
        String str3 = new String(paramArrayOfChar, paramInt1, paramInt2);
        this.pool[m] = str3;
        return str3;
      }
    return str1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.stream.StringPool
 * JD-Core Version:    0.6.2
 */