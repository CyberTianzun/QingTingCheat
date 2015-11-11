package fm.qingting.framework.utils;

public class HTTPStream
{
  // ERROR //
  public java.io.InputStream getStream(java.lang.String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +12 -> 13
    //   4: aload_1
    //   5: ldc 22
    //   7: invokevirtual 28	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   10: ifeq +5 -> 15
    //   13: aconst_null
    //   14: areturn
    //   15: new 30	org/apache/http/client/methods/HttpGet
    //   18: dup
    //   19: aload_1
    //   20: invokespecial 33	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   23: astore_2
    //   24: new 35	org/apache/http/params/BasicHttpParams
    //   27: dup
    //   28: invokespecial 36	org/apache/http/params/BasicHttpParams:<init>	()V
    //   31: astore_3
    //   32: aload_3
    //   33: sipush 5000
    //   36: invokestatic 42	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   39: aload_3
    //   40: sipush 5000
    //   43: invokestatic 45	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   46: new 47	org/apache/http/impl/client/DefaultHttpClient
    //   49: dup
    //   50: aload_3
    //   51: invokespecial 50	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
    //   54: astore 4
    //   56: aload 4
    //   58: aload_2
    //   59: invokevirtual 54	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   62: astore 14
    //   64: aload 14
    //   66: astore 6
    //   68: aload 6
    //   70: ifnull -57 -> 13
    //   73: aload 6
    //   75: invokeinterface 60 1 0
    //   80: astore 7
    //   82: aload 7
    //   84: invokeinterface 66 1 0
    //   89: astore 10
    //   91: aload 10
    //   93: areturn
    //   94: astore 13
    //   96: aload 13
    //   98: invokevirtual 69	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   101: aconst_null
    //   102: astore 6
    //   104: goto -36 -> 68
    //   107: astore 12
    //   109: aload 12
    //   111: invokevirtual 70	java/net/SocketTimeoutException:printStackTrace	()V
    //   114: aconst_null
    //   115: astore 6
    //   117: goto -49 -> 68
    //   120: astore 11
    //   122: aload 11
    //   124: invokevirtual 71	java/io/IOException:printStackTrace	()V
    //   127: aconst_null
    //   128: astore 6
    //   130: goto -62 -> 68
    //   133: astore 9
    //   135: aload 9
    //   137: invokevirtual 72	java/lang/IllegalStateException:printStackTrace	()V
    //   140: aconst_null
    //   141: areturn
    //   142: astore 8
    //   144: aload 8
    //   146: invokevirtual 71	java/io/IOException:printStackTrace	()V
    //   149: aconst_null
    //   150: areturn
    //   151: astore 5
    //   153: aconst_null
    //   154: astore 6
    //   156: goto -88 -> 68
    //
    // Exception table:
    //   from	to	target	type
    //   56	64	94	org/apache/http/client/ClientProtocolException
    //   56	64	107	java/net/SocketTimeoutException
    //   56	64	120	java/io/IOException
    //   82	91	133	java/lang/IllegalStateException
    //   82	91	142	java/io/IOException
    //   56	64	151	java/lang/OutOfMemoryError
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.HTTPStream
 * JD-Core Version:    0.6.2
 */