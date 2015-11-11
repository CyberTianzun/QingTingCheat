package org.apache.commons.httpclient;

public class HttpVersion
  implements Comparable
{
  public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);
  public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);
  public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);
  private int major = 0;
  private int minor = 0;

  public HttpVersion(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
      throw new IllegalArgumentException("HTTP major version number may not be negative");
    this.major = paramInt1;
    if (paramInt2 < 0)
      throw new IllegalArgumentException("HTTP minor version number may not be negative");
    this.minor = paramInt2;
  }

  // ERROR //
  public static HttpVersion parse(String paramString)
    throws ProtocolException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +13 -> 14
    //   4: new 33	java/lang/IllegalArgumentException
    //   7: dup
    //   8: ldc 48
    //   10: invokespecial 38	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aload_0
    //   15: ldc 50
    //   17: invokevirtual 56	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   20: ifne +30 -> 50
    //   23: new 44	org/apache/commons/httpclient/ProtocolException
    //   26: dup
    //   27: new 58	java/lang/StringBuffer
    //   30: dup
    //   31: invokespecial 59	java/lang/StringBuffer:<init>	()V
    //   34: ldc 61
    //   36: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   39: aload_0
    //   40: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   43: invokevirtual 69	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   46: invokespecial 70	org/apache/commons/httpclient/ProtocolException:<init>	(Ljava/lang/String;)V
    //   49: athrow
    //   50: ldc 50
    //   52: invokevirtual 74	java/lang/String:length	()I
    //   55: istore_1
    //   56: aload_0
    //   57: ldc 76
    //   59: iload_1
    //   60: invokevirtual 80	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   63: istore_2
    //   64: iload_2
    //   65: iconst_m1
    //   66: if_icmpne +30 -> 96
    //   69: new 44	org/apache/commons/httpclient/ProtocolException
    //   72: dup
    //   73: new 58	java/lang/StringBuffer
    //   76: dup
    //   77: invokespecial 59	java/lang/StringBuffer:<init>	()V
    //   80: ldc 82
    //   82: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   85: aload_0
    //   86: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   89: invokevirtual 69	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   92: invokespecial 70	org/apache/commons/httpclient/ProtocolException:<init>	(Ljava/lang/String;)V
    //   95: athrow
    //   96: aload_0
    //   97: iload_1
    //   98: iload_2
    //   99: invokevirtual 86	java/lang/String:substring	(II)Ljava/lang/String;
    //   102: invokestatic 92	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   105: istore 4
    //   107: iload_2
    //   108: iconst_1
    //   109: iadd
    //   110: istore 5
    //   112: aload_0
    //   113: invokevirtual 74	java/lang/String:length	()I
    //   116: istore 6
    //   118: aload_0
    //   119: iload 5
    //   121: iload 6
    //   123: invokevirtual 86	java/lang/String:substring	(II)Ljava/lang/String;
    //   126: invokestatic 92	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   129: istore 8
    //   131: new 2	org/apache/commons/httpclient/HttpVersion
    //   134: dup
    //   135: iload 4
    //   137: iload 8
    //   139: invokespecial 19	org/apache/commons/httpclient/HttpVersion:<init>	(II)V
    //   142: areturn
    //   143: astore_3
    //   144: new 44	org/apache/commons/httpclient/ProtocolException
    //   147: dup
    //   148: new 58	java/lang/StringBuffer
    //   151: dup
    //   152: invokespecial 59	java/lang/StringBuffer:<init>	()V
    //   155: ldc 94
    //   157: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   160: aload_0
    //   161: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   164: invokevirtual 69	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   167: invokespecial 70	org/apache/commons/httpclient/ProtocolException:<init>	(Ljava/lang/String;)V
    //   170: athrow
    //   171: astore 7
    //   173: new 44	org/apache/commons/httpclient/ProtocolException
    //   176: dup
    //   177: new 58	java/lang/StringBuffer
    //   180: dup
    //   181: invokespecial 59	java/lang/StringBuffer:<init>	()V
    //   184: ldc 96
    //   186: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   189: aload_0
    //   190: invokevirtual 65	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   193: invokevirtual 69	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   196: invokespecial 70	org/apache/commons/httpclient/ProtocolException:<init>	(Ljava/lang/String;)V
    //   199: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   96	107	143	java/lang/NumberFormatException
    //   118	131	171	java/lang/NumberFormatException
  }

  public int compareTo(Object paramObject)
  {
    return compareTo((HttpVersion)paramObject);
  }

  public int compareTo(HttpVersion paramHttpVersion)
  {
    if (paramHttpVersion == null)
      throw new IllegalArgumentException("Version parameter may not be null");
    int i = getMajor() - paramHttpVersion.getMajor();
    if (i == 0)
      i = getMinor() - paramHttpVersion.getMinor();
    return i;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof HttpVersion))
      return false;
    return equals((HttpVersion)paramObject);
  }

  public boolean equals(HttpVersion paramHttpVersion)
  {
    return compareTo(paramHttpVersion) == 0;
  }

  public int getMajor()
  {
    return this.major;
  }

  public int getMinor()
  {
    return this.minor;
  }

  public boolean greaterEquals(HttpVersion paramHttpVersion)
  {
    return compareTo(paramHttpVersion) >= 0;
  }

  public int hashCode()
  {
    return 100000 * this.major + this.minor;
  }

  public boolean lessEquals(HttpVersion paramHttpVersion)
  {
    return compareTo(paramHttpVersion) <= 0;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("HTTP/");
    localStringBuffer.append(this.major);
    localStringBuffer.append('.');
    localStringBuffer.append(this.minor);
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpVersion
 * JD-Core Version:    0.6.2
 */