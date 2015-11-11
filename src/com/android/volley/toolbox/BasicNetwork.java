package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.cookie.DateUtils;

public class BasicNetwork
  implements Network
{
  protected static final boolean DEBUG = VolleyLog.DEBUG;
  private static int DEFAULT_POOL_SIZE = 4096;
  private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
  protected final HttpStack mHttpStack;
  protected final ByteArrayPool mPool;

  public BasicNetwork(HttpStack paramHttpStack)
  {
    this(paramHttpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
  }

  public BasicNetwork(HttpStack paramHttpStack, ByteArrayPool paramByteArrayPool)
  {
    this.mHttpStack = paramHttpStack;
    this.mPool = paramByteArrayPool;
  }

  private void addCacheHeaders(Map<String, String> paramMap, Cache.Entry paramEntry)
  {
    if (paramEntry == null);
    do
    {
      return;
      if (paramEntry.etag != null)
        paramMap.put("If-None-Match", paramEntry.etag);
    }
    while (paramEntry.serverDate <= 0L);
    paramMap.put("If-Modified-Since", DateUtils.formatDate(new Date(paramEntry.serverDate)));
  }

  private static void attemptRetryOnException(String paramString, Request<?> paramRequest, VolleyError paramVolleyError)
    throws VolleyError
  {
    RetryPolicy localRetryPolicy = paramRequest.getRetryPolicy();
    int i = paramRequest.getTimeoutMs();
    try
    {
      localRetryPolicy.retry(paramVolleyError);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramString;
      arrayOfObject2[1] = Integer.valueOf(i);
      paramRequest.addMarker(String.format("%s-retry [timeout=%s]", arrayOfObject2));
      return;
    }
    catch (VolleyError localVolleyError)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = Integer.valueOf(i);
      paramRequest.addMarker(String.format("%s-timeout-giveup [timeout=%s]", arrayOfObject1));
      throw localVolleyError;
    }
  }

  private static Map<String, String> convertHeaders(Header[] paramArrayOfHeader)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfHeader.length)
        return localHashMap;
      localHashMap.put(paramArrayOfHeader[i].getName(), paramArrayOfHeader[i].getValue());
    }
  }

  // ERROR //
  private byte[] entityToBytes(HttpEntity paramHttpEntity)
    throws IOException, ServerError, OutOfMemoryError
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 139	com/android/volley/toolbox/PoolingByteArrayOutputStream
    //   7: dup
    //   8: aload_0
    //   9: getfield 42	com/android/volley/toolbox/BasicNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
    //   12: aload_1
    //   13: invokeinterface 145 1 0
    //   18: l2i
    //   19: invokespecial 148	com/android/volley/toolbox/PoolingByteArrayOutputStream:<init>	(Lcom/android/volley/toolbox/ByteArrayPool;I)V
    //   22: astore 4
    //   24: aload_1
    //   25: invokeinterface 152 1 0
    //   30: astore 8
    //   32: aconst_null
    //   33: astore_3
    //   34: aload 8
    //   36: ifnonnull +46 -> 82
    //   39: new 135	com/android/volley/ServerError
    //   42: dup
    //   43: invokespecial 153	com/android/volley/ServerError:<init>	()V
    //   46: athrow
    //   47: astore 7
    //   49: aload 4
    //   51: astore_2
    //   52: aload 7
    //   54: athrow
    //   55: astore 5
    //   57: aload_1
    //   58: invokeinterface 156 1 0
    //   63: aload_0
    //   64: getfield 42	com/android/volley/toolbox/BasicNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
    //   67: aload_3
    //   68: invokevirtual 160	com/android/volley/toolbox/ByteArrayPool:returnBuf	([B)V
    //   71: aload_2
    //   72: ifnull +7 -> 79
    //   75: aload_2
    //   76: invokevirtual 163	com/android/volley/toolbox/PoolingByteArrayOutputStream:close	()V
    //   79: aload 5
    //   81: athrow
    //   82: aload_0
    //   83: getfield 42	com/android/volley/toolbox/BasicNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
    //   86: sipush 1024
    //   89: invokevirtual 167	com/android/volley/toolbox/ByteArrayPool:getBuf	(I)[B
    //   92: astore_3
    //   93: aload 8
    //   95: aload_3
    //   96: invokevirtual 173	java/io/InputStream:read	([B)I
    //   99: istore 9
    //   101: iload 9
    //   103: iconst_m1
    //   104: if_icmpne +37 -> 141
    //   107: aload 4
    //   109: invokevirtual 177	com/android/volley/toolbox/PoolingByteArrayOutputStream:toByteArray	()[B
    //   112: astore 10
    //   114: aload_1
    //   115: invokeinterface 156 1 0
    //   120: aload_0
    //   121: getfield 42	com/android/volley/toolbox/BasicNetwork:mPool	Lcom/android/volley/toolbox/ByteArrayPool;
    //   124: aload_3
    //   125: invokevirtual 160	com/android/volley/toolbox/ByteArrayPool:returnBuf	([B)V
    //   128: aload 4
    //   130: ifnull +8 -> 138
    //   133: aload 4
    //   135: invokevirtual 163	com/android/volley/toolbox/PoolingByteArrayOutputStream:close	()V
    //   138: aload 10
    //   140: areturn
    //   141: aload 4
    //   143: aload_3
    //   144: iconst_0
    //   145: iload 9
    //   147: invokevirtual 181	com/android/volley/toolbox/PoolingByteArrayOutputStream:write	([BII)V
    //   150: goto -57 -> 93
    //   153: astore 5
    //   155: aload 4
    //   157: astore_2
    //   158: goto -101 -> 57
    //   161: astore 11
    //   163: ldc 183
    //   165: iconst_0
    //   166: anewarray 4	java/lang/Object
    //   169: invokestatic 187	com/android/volley/VolleyLog:v	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   172: goto -52 -> 120
    //   175: astore 6
    //   177: ldc 183
    //   179: iconst_0
    //   180: anewarray 4	java/lang/Object
    //   183: invokestatic 187	com/android/volley/VolleyLog:v	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   186: goto -123 -> 63
    //   189: astore 7
    //   191: aconst_null
    //   192: astore_3
    //   193: aconst_null
    //   194: astore_2
    //   195: goto -143 -> 52
    //
    // Exception table:
    //   from	to	target	type
    //   24	32	47	java/lang/OutOfMemoryError
    //   39	47	47	java/lang/OutOfMemoryError
    //   82	93	47	java/lang/OutOfMemoryError
    //   93	101	47	java/lang/OutOfMemoryError
    //   107	114	47	java/lang/OutOfMemoryError
    //   141	150	47	java/lang/OutOfMemoryError
    //   4	24	55	finally
    //   52	55	55	finally
    //   24	32	153	finally
    //   39	47	153	finally
    //   82	93	153	finally
    //   93	101	153	finally
    //   107	114	153	finally
    //   141	150	153	finally
    //   114	120	161	java/io/IOException
    //   57	63	175	java/io/IOException
    //   4	24	189	java/lang/OutOfMemoryError
  }

  private void logSlowRequests(long paramLong, Request<?> paramRequest, byte[] paramArrayOfByte, StatusLine paramStatusLine)
  {
    Object[] arrayOfObject;
    if ((DEBUG) || (paramLong > SLOW_REQUEST_THRESHOLD_MS))
    {
      arrayOfObject = new Object[5];
      arrayOfObject[0] = paramRequest;
      arrayOfObject[1] = Long.valueOf(paramLong);
      if (paramArrayOfByte == null)
        break label91;
    }
    label91: for (Object localObject = Integer.valueOf(paramArrayOfByte.length); ; localObject = "null")
    {
      arrayOfObject[2] = localObject;
      arrayOfObject[3] = Integer.valueOf(paramStatusLine.getStatusCode());
      arrayOfObject[4] = Integer.valueOf(paramRequest.getRetryPolicy().getCurrentRetryCount());
      VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", arrayOfObject);
      return;
    }
  }

  protected void logError(String paramString1, String paramString2, long paramLong)
  {
    long l = SystemClock.elapsedRealtime();
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = Long.valueOf(l - paramLong);
    arrayOfObject[2] = paramString2;
    VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", arrayOfObject);
  }

  public NetworkResponse performRequest(Request<?> paramRequest)
    throws VolleyError
  {
    long l = SystemClock.elapsedRealtime();
    while (true)
    {
      HttpResponse localHttpResponse = null;
      byte[] arrayOfByte = null;
      Object localObject = new HashMap();
      try
      {
        HashMap localHashMap = new HashMap();
        addCacheHeaders(localHashMap, paramRequest.getCacheEntry());
        localHttpResponse = this.mHttpStack.performRequest(paramRequest, localHashMap);
        StatusLine localStatusLine = localHttpResponse.getStatusLine();
        j = localStatusLine.getStatusCode();
        localObject = convertHeaders(localHttpResponse.getAllHeaders());
        arrayOfByte = null;
        if (j == 304)
          return new NetworkResponse(304, paramRequest.getCacheEntry().data, (Map)localObject, true);
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        arrayOfByte = null;
        if (localHttpEntity != null)
        {
          arrayOfByte = entityToBytes(localHttpResponse.getEntity());
          logSlowRequests(SystemClock.elapsedRealtime() - l, paramRequest, arrayOfByte, localStatusLine);
          if ((j >= 200) && (j <= 299))
            break label209;
          throw new IOException();
        }
      }
      catch (SocketTimeoutException localSocketTimeoutException)
      {
        int j;
        while (true)
        {
          attemptRetryOnException("socket", paramRequest, new TimeoutError());
          break;
          arrayOfByte = new byte[0];
        }
        NetworkResponse localNetworkResponse2 = new NetworkResponse(j, arrayOfByte, (Map)localObject, false);
        return localNetworkResponse2;
      }
      catch (ConnectTimeoutException localConnectTimeoutException)
      {
        attemptRetryOnException("connection", paramRequest, new TimeoutError());
      }
      catch (MalformedURLException localMalformedURLException)
      {
        throw new RuntimeException("Bad URL " + paramRequest.getUrl(), localMalformedURLException);
      }
      catch (IOException localIOException)
      {
        label209: NetworkResponse localNetworkResponse1;
        if (localHttpResponse != null)
        {
          int i = localHttpResponse.getStatusLine().getStatusCode();
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(i);
          arrayOfObject[1] = paramRequest.getUrl();
          VolleyLog.e("Unexpected response code %d for %s", arrayOfObject);
          if (arrayOfByte != null)
          {
            localNetworkResponse1 = new NetworkResponse(i, arrayOfByte, (Map)localObject, false);
            if ((i == 401) || (i == 403))
              attemptRetryOnException("auth", paramRequest, new AuthFailureError(localNetworkResponse1));
          }
        }
        else
        {
          throw new NoConnectionError(localIOException);
          throw new ServerError(localNetworkResponse1);
          throw new NetworkError(null);
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
      }
    }
    throw new VolleyError();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.BasicNetwork
 * JD-Core Version:    0.6.2
 */