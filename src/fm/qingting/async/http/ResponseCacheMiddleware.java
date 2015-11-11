package fm.qingting.async.http;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import fm.qingting.async.AsyncSSLSocket;
import fm.qingting.async.AsyncServer;
import fm.qingting.async.AsyncSocket;
import fm.qingting.async.ByteBufferList;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.DataEmitterBase;
import fm.qingting.async.FilteredDataEmitter;
import fm.qingting.async.Util;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.callback.ConnectCallback;
import fm.qingting.async.callback.WritableCallback;
import fm.qingting.async.http.libcore.Charsets;
import fm.qingting.async.http.libcore.DiskLruCache;
import fm.qingting.async.http.libcore.DiskLruCache.Editor;
import fm.qingting.async.http.libcore.DiskLruCache.Snapshot;
import fm.qingting.async.http.libcore.RawHeaders;
import fm.qingting.async.http.libcore.RequestHeaders;
import fm.qingting.async.http.libcore.ResponseHeaders;
import fm.qingting.async.http.libcore.StrictLineReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.SecureCacheResponse;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;

public class ResponseCacheMiddleware extends SimpleMiddleware
{
  public static final String CACHE = "cache";
  public static final String CONDITIONAL_CACHE = "conditional-cache";
  private static final int ENTRY_BODY = 1;
  private static final int ENTRY_COUNT = 2;
  private static final int ENTRY_METADATA = 0;
  private static final String LOGTAG = "AsyncHttpCache";
  public static final String SERVED_FROM = "Served-From";
  private static final int VERSION = 201105;
  private DiskLruCache cache;
  File cacheDir;
  int cacheHitCount;
  int cacheStoreCount;
  boolean caching = true;
  private AsyncHttpClient client;
  int conditionalCacheHitCount;
  int networkCount;
  long size;
  int writeAbortCount;
  int writeSuccessCount;

  public static ResponseCacheMiddleware addCache(AsyncHttpClient paramAsyncHttpClient, File paramFile, long paramLong)
    throws IOException
  {
    Iterator localIterator = paramAsyncHttpClient.getMiddleware().iterator();
    while (localIterator.hasNext())
      if (((AsyncHttpClientMiddleware)localIterator.next() instanceof ResponseCacheMiddleware))
        throw new IOException("Response cache already added to http client");
    ResponseCacheMiddleware localResponseCacheMiddleware = new ResponseCacheMiddleware();
    localResponseCacheMiddleware.size = paramLong;
    localResponseCacheMiddleware.client = paramAsyncHttpClient;
    localResponseCacheMiddleware.cacheDir = paramFile;
    localResponseCacheMiddleware.open();
    paramAsyncHttpClient.insertMiddleware(localResponseCacheMiddleware);
    return localResponseCacheMiddleware;
  }

  private static InputStream newBodyInputStream(final DiskLruCache.Snapshot paramSnapshot)
  {
    return new FilterInputStream(paramSnapshot.getInputStream(1))
    {
      public void close()
        throws IOException
      {
        paramSnapshot.close();
        super.close();
      }
    };
  }

  private void open()
    throws IOException
  {
    this.cache = DiskLruCache.open(this.cacheDir, 201105, 2, this.size);
  }

  private static String uriToKey(URI paramURI)
  {
    try
    {
      String str = new BigInteger(1, MessageDigest.getInstance("MD5").digest(paramURI.toString().getBytes())).toString(16);
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException(localNoSuchAlgorithmException);
    }
  }

  public void clear()
    throws IOException
  {
    if (this.cache != null)
    {
      this.cache.delete();
      open();
    }
  }

  public int getCacheHitCount()
  {
    return this.cacheHitCount;
  }

  public int getCacheStoreCount()
  {
    return this.cacheStoreCount;
  }

  public boolean getCaching()
  {
    return this.caching;
  }

  public int getConditionalCacheHitCount()
  {
    return this.conditionalCacheHitCount;
  }

  public int getNetworkCount()
  {
    return this.networkCount;
  }

  // ERROR //
  public fm.qingting.async.future.Cancellable getSocket(final AsyncHttpClientMiddleware.GetSocketData paramGetSocketData)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 119	fm/qingting/async/http/ResponseCacheMiddleware:cache	Lfm/qingting/async/http/libcore/DiskLruCache;
    //   4: ifnonnull +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aload_0
    //   10: getfield 47	fm/qingting/async/http/ResponseCacheMiddleware:caching	Z
    //   13: ifne +5 -> 18
    //   16: aconst_null
    //   17: areturn
    //   18: aload_1
    //   19: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   22: invokevirtual 194	fm/qingting/async/http/AsyncHttpRequest:getHeaders	()Lfm/qingting/async/http/libcore/RequestHeaders;
    //   25: invokevirtual 199	fm/qingting/async/http/libcore/RequestHeaders:isNoCache	()Z
    //   28: ifeq +5 -> 33
    //   31: aconst_null
    //   32: areturn
    //   33: aload_1
    //   34: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   37: invokevirtual 203	fm/qingting/async/http/AsyncHttpRequest:getUri	()Ljava/net/URI;
    //   40: invokestatic 205	fm/qingting/async/http/ResponseCacheMiddleware:uriToKey	(Ljava/net/URI;)Ljava/lang/String;
    //   43: astore_2
    //   44: aload_0
    //   45: getfield 119	fm/qingting/async/http/ResponseCacheMiddleware:cache	Lfm/qingting/async/http/libcore/DiskLruCache;
    //   48: aload_2
    //   49: invokevirtual 209	fm/qingting/async/http/libcore/DiskLruCache:get	(Ljava/lang/String;)Lfm/qingting/async/http/libcore/DiskLruCache$Snapshot;
    //   52: astore 4
    //   54: aload 4
    //   56: ifnonnull +15 -> 71
    //   59: aload_0
    //   60: iconst_1
    //   61: aload_0
    //   62: getfield 178	fm/qingting/async/http/ResponseCacheMiddleware:networkCount	I
    //   65: iadd
    //   66: putfield 178	fm/qingting/async/http/ResponseCacheMiddleware:networkCount	I
    //   69: aconst_null
    //   70: areturn
    //   71: new 211	fm/qingting/async/http/ResponseCacheMiddleware$Entry
    //   74: dup
    //   75: aload 4
    //   77: iconst_0
    //   78: invokevirtual 109	fm/qingting/async/http/libcore/DiskLruCache$Snapshot:getInputStream	(I)Ljava/io/InputStream;
    //   81: invokespecial 214	fm/qingting/async/http/ResponseCacheMiddleware$Entry:<init>	(Ljava/io/InputStream;)V
    //   84: astore 5
    //   86: aload 5
    //   88: aload_1
    //   89: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   92: invokevirtual 203	fm/qingting/async/http/AsyncHttpRequest:getUri	()Ljava/net/URI;
    //   95: aload_1
    //   96: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   99: invokevirtual 217	fm/qingting/async/http/AsyncHttpRequest:getMethod	()Ljava/lang/String;
    //   102: aload_1
    //   103: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   106: invokevirtual 194	fm/qingting/async/http/AsyncHttpRequest:getHeaders	()Lfm/qingting/async/http/libcore/RequestHeaders;
    //   109: invokevirtual 220	fm/qingting/async/http/libcore/RequestHeaders:getHeaders	()Lfm/qingting/async/http/libcore/RawHeaders;
    //   112: invokevirtual 226	fm/qingting/async/http/libcore/RawHeaders:toMultimap	()Ljava/util/Map;
    //   115: invokevirtual 230	fm/qingting/async/http/ResponseCacheMiddleware$Entry:matches	(Ljava/net/URI;Ljava/lang/String;Ljava/util/Map;)Z
    //   118: ifne +23 -> 141
    //   121: aload 4
    //   123: invokevirtual 233	fm/qingting/async/http/libcore/DiskLruCache$Snapshot:close	()V
    //   126: aconst_null
    //   127: areturn
    //   128: astore_3
    //   129: aload_0
    //   130: iconst_1
    //   131: aload_0
    //   132: getfield 178	fm/qingting/async/http/ResponseCacheMiddleware:networkCount	I
    //   135: iadd
    //   136: putfield 178	fm/qingting/async/http/ResponseCacheMiddleware:networkCount	I
    //   139: aconst_null
    //   140: areturn
    //   141: aload 5
    //   143: invokestatic 237	fm/qingting/async/http/ResponseCacheMiddleware$Entry:access$100	(Lfm/qingting/async/http/ResponseCacheMiddleware$Entry;)Z
    //   146: ifeq +47 -> 193
    //   149: new 239	fm/qingting/async/http/ResponseCacheMiddleware$EntrySecureCacheResponse
    //   152: dup
    //   153: aload 5
    //   155: aload 4
    //   157: invokespecial 242	fm/qingting/async/http/ResponseCacheMiddleware$EntrySecureCacheResponse:<init>	(Lfm/qingting/async/http/ResponseCacheMiddleware$Entry;Lfm/qingting/async/http/libcore/DiskLruCache$Snapshot;)V
    //   160: astore 6
    //   162: aload 6
    //   164: invokevirtual 246	java/net/CacheResponse:getHeaders	()Ljava/util/Map;
    //   167: astore 8
    //   169: aload 6
    //   171: invokevirtual 250	java/net/CacheResponse:getBody	()Ljava/io/InputStream;
    //   174: astore 9
    //   176: aload 8
    //   178: ifnull +8 -> 186
    //   181: aload 9
    //   183: ifnonnull +30 -> 213
    //   186: aload 9
    //   188: invokevirtual 253	java/io/InputStream:close	()V
    //   191: aconst_null
    //   192: areturn
    //   193: new 255	fm/qingting/async/http/ResponseCacheMiddleware$EntryCacheResponse
    //   196: dup
    //   197: aload 5
    //   199: aload 4
    //   201: invokespecial 256	fm/qingting/async/http/ResponseCacheMiddleware$EntryCacheResponse:<init>	(Lfm/qingting/async/http/ResponseCacheMiddleware$Entry;Lfm/qingting/async/http/libcore/DiskLruCache$Snapshot;)V
    //   204: astore 6
    //   206: goto -44 -> 162
    //   209: astore 7
    //   211: aconst_null
    //   212: areturn
    //   213: aload 8
    //   215: invokestatic 260	fm/qingting/async/http/libcore/RawHeaders:fromMultimap	(Ljava/util/Map;)Lfm/qingting/async/http/libcore/RawHeaders;
    //   218: astore 11
    //   220: new 262	fm/qingting/async/http/libcore/ResponseHeaders
    //   223: dup
    //   224: aload_1
    //   225: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   228: invokevirtual 203	fm/qingting/async/http/AsyncHttpRequest:getUri	()Ljava/net/URI;
    //   231: aload 11
    //   233: invokespecial 265	fm/qingting/async/http/libcore/ResponseHeaders:<init>	(Ljava/net/URI;Lfm/qingting/async/http/libcore/RawHeaders;)V
    //   236: astore 12
    //   238: aload 12
    //   240: invokestatic 271	java/lang/System:currentTimeMillis	()J
    //   243: invokestatic 271	java/lang/System:currentTimeMillis	()J
    //   246: invokevirtual 275	fm/qingting/async/http/libcore/ResponseHeaders:setLocalTimestamps	(JJ)V
    //   249: aload 12
    //   251: invokestatic 271	java/lang/System:currentTimeMillis	()J
    //   254: aload_1
    //   255: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   258: invokevirtual 194	fm/qingting/async/http/AsyncHttpRequest:getHeaders	()Lfm/qingting/async/http/libcore/RequestHeaders;
    //   261: invokevirtual 279	fm/qingting/async/http/libcore/ResponseHeaders:chooseResponseSource	(JLfm/qingting/async/http/libcore/RequestHeaders;)Lfm/qingting/async/http/libcore/ResponseSource;
    //   264: astore 13
    //   266: aload 13
    //   268: getstatic 284	fm/qingting/async/http/libcore/ResponseSource:CACHE	Lfm/qingting/async/http/libcore/ResponseSource;
    //   271: if_acmpne +146 -> 417
    //   274: aload_0
    //   275: iconst_1
    //   276: aload_0
    //   277: getfield 168	fm/qingting/async/http/ResponseCacheMiddleware:cacheHitCount	I
    //   280: iadd
    //   281: putfield 168	fm/qingting/async/http/ResponseCacheMiddleware:cacheHitCount	I
    //   284: aload_1
    //   285: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   288: ldc_w 286
    //   291: invokevirtual 289	fm/qingting/async/http/AsyncHttpRequest:logi	(Ljava/lang/String;)V
    //   294: aload 5
    //   296: invokestatic 237	fm/qingting/async/http/ResponseCacheMiddleware$Entry:access$100	(Lfm/qingting/async/http/ResponseCacheMiddleware$Entry;)Z
    //   299: ifeq +100 -> 399
    //   302: new 291	fm/qingting/async/http/ResponseCacheMiddleware$CachedSSLSocket
    //   305: dup
    //   306: aload_0
    //   307: aload 6
    //   309: checkcast 239	fm/qingting/async/http/ResponseCacheMiddleware$EntrySecureCacheResponse
    //   312: invokespecial 294	fm/qingting/async/http/ResponseCacheMiddleware$CachedSSLSocket:<init>	(Lfm/qingting/async/http/ResponseCacheMiddleware;Ljava/net/CacheResponse;)V
    //   315: astore 16
    //   317: aload 11
    //   319: ldc_w 296
    //   322: invokevirtual 299	fm/qingting/async/http/libcore/RawHeaders:removeAll	(Ljava/lang/String;)V
    //   325: aload 11
    //   327: ldc_w 301
    //   330: invokevirtual 299	fm/qingting/async/http/libcore/RawHeaders:removeAll	(Ljava/lang/String;)V
    //   333: aload 11
    //   335: ldc_w 303
    //   338: aload 4
    //   340: iconst_1
    //   341: invokevirtual 307	fm/qingting/async/http/libcore/DiskLruCache$Snapshot:getLength	(I)J
    //   344: invokestatic 311	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   347: invokevirtual 315	fm/qingting/async/http/libcore/RawHeaders:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   350: aload 16
    //   352: getfield 321	fm/qingting/async/http/ResponseCacheMiddleware$CachedSocket:pending	Lfm/qingting/async/ByteBufferList;
    //   355: aload 11
    //   357: invokevirtual 324	fm/qingting/async/http/libcore/RawHeaders:toHeaderString	()Ljava/lang/String;
    //   360: invokevirtual 145	java/lang/String:getBytes	()[B
    //   363: invokestatic 330	java/nio/ByteBuffer:wrap	([B)Ljava/nio/ByteBuffer;
    //   366: invokevirtual 336	fm/qingting/async/ByteBufferList:add	(Ljava/nio/ByteBuffer;)V
    //   369: aload_0
    //   370: getfield 51	fm/qingting/async/http/ResponseCacheMiddleware:client	Lfm/qingting/async/http/AsyncHttpClient;
    //   373: invokevirtual 340	fm/qingting/async/http/AsyncHttpClient:getServer	()Lfm/qingting/async/AsyncServer;
    //   376: new 342	fm/qingting/async/http/ResponseCacheMiddleware$1
    //   379: dup
    //   380: aload_0
    //   381: aload_1
    //   382: aload 16
    //   384: invokespecial 345	fm/qingting/async/http/ResponseCacheMiddleware$1:<init>	(Lfm/qingting/async/http/ResponseCacheMiddleware;Lfm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData;Lfm/qingting/async/http/ResponseCacheMiddleware$CachedSocket;)V
    //   387: invokevirtual 351	fm/qingting/async/AsyncServer:post	(Ljava/lang/Runnable;)Ljava/lang/Object;
    //   390: pop
    //   391: new 353	fm/qingting/async/future/SimpleCancellable
    //   394: dup
    //   395: invokespecial 354	fm/qingting/async/future/SimpleCancellable:<init>	()V
    //   398: areturn
    //   399: new 317	fm/qingting/async/http/ResponseCacheMiddleware$CachedSocket
    //   402: dup
    //   403: aload_0
    //   404: aload 6
    //   406: checkcast 255	fm/qingting/async/http/ResponseCacheMiddleware$EntryCacheResponse
    //   409: invokespecial 355	fm/qingting/async/http/ResponseCacheMiddleware$CachedSocket:<init>	(Lfm/qingting/async/http/ResponseCacheMiddleware;Ljava/net/CacheResponse;)V
    //   412: astore 16
    //   414: goto -97 -> 317
    //   417: aload 13
    //   419: getstatic 357	fm/qingting/async/http/libcore/ResponseSource:CONDITIONAL_CACHE	Lfm/qingting/async/http/libcore/ResponseSource;
    //   422: if_acmpne +50 -> 472
    //   425: aload_1
    //   426: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   429: ldc_w 359
    //   432: invokevirtual 289	fm/qingting/async/http/AsyncHttpRequest:logi	(Ljava/lang/String;)V
    //   435: new 361	fm/qingting/async/http/ResponseCacheMiddleware$CacheData
    //   438: dup
    //   439: invokespecial 362	fm/qingting/async/http/ResponseCacheMiddleware$CacheData:<init>	()V
    //   442: astore 15
    //   444: aload 15
    //   446: aload 12
    //   448: putfield 366	fm/qingting/async/http/ResponseCacheMiddleware$CacheData:cachedResponseHeaders	Lfm/qingting/async/http/libcore/ResponseHeaders;
    //   451: aload 15
    //   453: aload 6
    //   455: putfield 370	fm/qingting/async/http/ResponseCacheMiddleware$CacheData:candidate	Ljava/net/CacheResponse;
    //   458: aload_1
    //   459: getfield 374	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:state	Landroid/os/Bundle;
    //   462: ldc_w 376
    //   465: aload 15
    //   467: invokevirtual 382	android/os/Bundle:putParcelable	(Ljava/lang/String;Landroid/os/Parcelable;)V
    //   470: aconst_null
    //   471: areturn
    //   472: aload_1
    //   473: getfield 188	fm/qingting/async/http/AsyncHttpClientMiddleware$GetSocketData:request	Lfm/qingting/async/http/AsyncHttpRequest;
    //   476: ldc_w 384
    //   479: invokevirtual 387	fm/qingting/async/http/AsyncHttpRequest:logd	(Ljava/lang/String;)V
    //   482: aload 9
    //   484: invokevirtual 253	java/io/InputStream:close	()V
    //   487: aconst_null
    //   488: areturn
    //   489: astore 10
    //   491: goto -300 -> 191
    //   494: astore 14
    //   496: goto -9 -> 487
    //
    // Exception table:
    //   from	to	target	type
    //   44	54	128	java/io/IOException
    //   59	69	128	java/io/IOException
    //   71	86	128	java/io/IOException
    //   162	176	209	java/lang/Exception
    //   186	191	489	java/lang/Exception
    //   482	487	494	java/lang/Exception
  }

  public void onBodyDecoder(AsyncHttpClientMiddleware.OnBodyData paramOnBodyData)
  {
    if ((CachedSocket)Util.getWrappedSocket(paramOnBodyData.socket, CachedSocket.class) != null)
      paramOnBodyData.headers.getHeaders().set("Served-From", "cache");
    while (true)
    {
      return;
      CacheData localCacheData = (CacheData)paramOnBodyData.state.getParcelable("cache-data");
      if (localCacheData != null)
      {
        if (localCacheData.cachedResponseHeaders.validate(paramOnBodyData.headers))
        {
          paramOnBodyData.request.logi("Serving response from conditional cache");
          paramOnBodyData.headers = localCacheData.cachedResponseHeaders.combine(paramOnBodyData.headers);
          paramOnBodyData.headers.getHeaders().setStatusLine(localCacheData.cachedResponseHeaders.getHeaders().getStatusLine());
          paramOnBodyData.headers.getHeaders().set("Served-From", "conditional-cache");
          this.conditionalCacheHitCount = (1 + this.conditionalCacheHitCount);
          BodySpewer localBodySpewer = new BodySpewer(null);
          localBodySpewer.cacheResponse = localCacheData.candidate;
          localBodySpewer.setDataEmitter(paramOnBodyData.bodyEmitter);
          paramOnBodyData.bodyEmitter = localBodySpewer;
          localBodySpewer.spew();
          return;
        }
        paramOnBodyData.state.remove("cache-data");
      }
      if (this.caching)
      {
        if ((!paramOnBodyData.headers.isCacheable(paramOnBodyData.request.getHeaders())) || (!paramOnBodyData.request.getMethod().equals("GET")))
        {
          this.networkCount = (1 + this.networkCount);
          paramOnBodyData.request.logd("Response is not cacheable");
          return;
        }
        String str = uriToKey(paramOnBodyData.request.getUri());
        RawHeaders localRawHeaders = paramOnBodyData.request.getHeaders().getHeaders().getAll(paramOnBodyData.headers.getVaryFields());
        Entry localEntry = new Entry(paramOnBodyData.request.getUri(), localRawHeaders, paramOnBodyData.request, paramOnBodyData.headers);
        BodyCacher localBodyCacher = new BodyCacher(null);
        try
        {
          DiskLruCache.Editor localEditor = this.cache.edit(str);
          if (localEditor != null)
          {
            localEntry.writeTo(localEditor);
            localBodyCacher.cacheRequest = new CacheRequestImpl(localEditor);
            if (localBodyCacher.cacheRequest.getBody() != null)
            {
              localBodyCacher.setDataEmitter(paramOnBodyData.bodyEmitter);
              paramOnBodyData.bodyEmitter = localBodyCacher;
              paramOnBodyData.state.putParcelable("body-cacher", localBodyCacher);
              paramOnBodyData.request.logd("Caching response");
              this.cacheStoreCount = (1 + this.cacheStoreCount);
              return;
            }
          }
        }
        catch (Exception localException)
        {
          if (localBodyCacher.cacheRequest != null)
            localBodyCacher.cacheRequest.abort();
          localBodyCacher.cacheRequest = null;
          this.networkCount = (1 + this.networkCount);
        }
      }
    }
  }

  public void onRequestComplete(AsyncHttpClientMiddleware.OnRequestCompleteData paramOnRequestCompleteData)
  {
    BodyCacher localBodyCacher = (BodyCacher)paramOnRequestCompleteData.state.getParcelable("body-cacher");
    if (localBodyCacher == null)
      return;
    try
    {
      if (paramOnRequestCompleteData.exception != null)
      {
        localBodyCacher.abort();
        return;
      }
      localBodyCacher.commit();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void setCaching(boolean paramBoolean)
  {
    this.caching = paramBoolean;
  }

  private static class BodyCacher extends FilteredDataEmitter
    implements Parcelable
  {
    ResponseCacheMiddleware.CacheRequestImpl cacheRequest;
    ByteBufferList cached;

    public void abort()
    {
      if (this.cacheRequest != null)
      {
        this.cacheRequest.abort();
        this.cacheRequest = null;
      }
    }

    public void commit()
    {
      if (this.cacheRequest != null);
      try
      {
        this.cacheRequest.getBody().close();
        return;
      }
      catch (Exception localException)
      {
      }
    }

    public int describeContents()
    {
      return 0;
    }

    public void onDataAvailable(DataEmitter paramDataEmitter, ByteBufferList paramByteBufferList)
    {
      if (this.cached != null)
      {
        Util.emitAllData(this, this.cached);
        if (this.cached.remaining() <= 0);
      }
      while (true)
      {
        return;
        this.cached = null;
        try
        {
          if (this.cacheRequest != null)
          {
            OutputStream localOutputStream = this.cacheRequest.getBody();
            if (localOutputStream != null)
            {
              int i = paramByteBufferList.size();
              for (int j = 0; j < i; j++)
              {
                ByteBuffer localByteBuffer = paramByteBufferList.remove();
                localOutputStream.write(localByteBuffer.array(), localByteBuffer.arrayOffset() + localByteBuffer.position(), localByteBuffer.remaining());
                paramByteBufferList.add(localByteBuffer);
              }
            }
            abort();
          }
          super.onDataAvailable(paramDataEmitter, paramByteBufferList);
          if ((this.cacheRequest == null) || (paramByteBufferList.remaining() <= 0))
            continue;
          this.cached = new ByteBufferList();
          paramByteBufferList.get(this.cached);
          return;
        }
        catch (Exception localException)
        {
          while (true)
            abort();
        }
      }
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
    }
  }

  private static class BodySpewer extends FilteredDataEmitter
  {
    boolean allowEnd;
    CacheResponse cacheResponse;
    boolean paused;
    ByteBufferList pending = new ByteBufferList();

    public boolean isPaused()
    {
      return this.paused;
    }

    protected void report(Exception paramException)
    {
      if (!this.allowEnd)
        return;
      try
      {
        this.cacheResponse.getBody().close();
        label18: super.report(paramException);
        return;
      }
      catch (Exception localException)
      {
        break label18;
      }
    }

    public void resume()
    {
      this.paused = false;
      spew();
    }

    void spew()
    {
      getServer().post(new Runnable()
      {
        public void run()
        {
          ResponseCacheMiddleware.BodySpewer.this.spewInternal();
        }
      });
    }

    void spewInternal()
    {
      if (this.pending.remaining() > 0)
      {
        Util.emitAllData(this, this.pending);
        if (this.pending.remaining() <= 0);
      }
      while (true)
      {
        return;
        try
        {
          int i;
          do
          {
            localByteBuffer.limit(i);
            this.pending.add(localByteBuffer);
            Util.emitAllData(this, this.pending);
            if (this.pending.remaining() != 0)
              break;
            ByteBuffer localByteBuffer = ByteBufferList.obtain(8192);
            i = this.cacheResponse.getBody().read(localByteBuffer.array());
          }
          while (i != -1);
          this.allowEnd = true;
          report(null);
          return;
        }
        catch (IOException localIOException)
        {
          this.allowEnd = true;
          report(localIOException);
        }
      }
    }
  }

  public static class CacheData
    implements Parcelable
  {
    ResponseHeaders cachedResponseHeaders;
    CacheResponse candidate;

    public int describeContents()
    {
      return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
    }
  }

  private final class CacheRequestImpl extends CacheRequest
  {
    private OutputStream body;
    private OutputStream cacheOut;
    private boolean done;
    private final DiskLruCache.Editor editor;

    public CacheRequestImpl(DiskLruCache.Editor arg2)
      throws IOException
    {
      final DiskLruCache.Editor localEditor;
      this.editor = localEditor;
      this.cacheOut = localEditor.newOutputStream(1);
      this.body = new FilterOutputStream(this.cacheOut)
      {
        public void close()
          throws IOException
        {
          synchronized (ResponseCacheMiddleware.this)
          {
            if (ResponseCacheMiddleware.CacheRequestImpl.this.done)
              return;
            ResponseCacheMiddleware.CacheRequestImpl.access$402(ResponseCacheMiddleware.CacheRequestImpl.this, true);
            ResponseCacheMiddleware localResponseCacheMiddleware2 = ResponseCacheMiddleware.this;
            localResponseCacheMiddleware2.writeSuccessCount = (1 + localResponseCacheMiddleware2.writeSuccessCount);
            super.close();
            localEditor.commit();
            return;
          }
        }

        public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
          throws IOException
        {
          this.out.write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
        }
      };
    }

    public void abort()
    {
      synchronized (ResponseCacheMiddleware.this)
      {
        if (this.done)
          return;
        this.done = true;
        ResponseCacheMiddleware localResponseCacheMiddleware2 = ResponseCacheMiddleware.this;
        localResponseCacheMiddleware2.writeAbortCount = (1 + localResponseCacheMiddleware2.writeAbortCount);
      }
      try
      {
        this.cacheOut.close();
        try
        {
          label46: this.editor.abort();
          return;
        }
        catch (IOException localIOException2)
        {
          return;
        }
        localObject = finally;
        throw localObject;
      }
      catch (IOException localIOException1)
      {
        break label46;
      }
    }

    public OutputStream getBody()
      throws IOException
    {
      return this.body;
    }
  }

  private class CachedSSLSocket extends ResponseCacheMiddleware.CachedSocket
    implements AsyncSSLSocket
  {
    public CachedSSLSocket(CacheResponse arg2)
    {
      super(localCacheResponse);
    }

    public X509Certificate[] getPeerCertificates()
    {
      return null;
    }
  }

  private class CachedSocket extends DataEmitterBase
    implements AsyncSocket
  {
    CacheResponse cacheResponse;
    boolean closed;
    CompletedCallback closedCallback;
    boolean open;
    boolean paused;
    ByteBufferList pending = new ByteBufferList();

    public CachedSocket(CacheResponse arg2)
    {
      Object localObject;
      this.cacheResponse = localObject;
    }

    public void close()
    {
      this.open = false;
    }

    public void end()
    {
    }

    public CompletedCallback getClosedCallback()
    {
      return this.closedCallback;
    }

    public AsyncServer getServer()
    {
      return ResponseCacheMiddleware.this.client.getServer();
    }

    public WritableCallback getWriteableCallback()
    {
      return null;
    }

    public boolean isChunked()
    {
      return false;
    }

    public boolean isOpen()
    {
      return this.open;
    }

    public boolean isPaused()
    {
      return this.paused;
    }

    public void pause()
    {
      this.paused = true;
    }

    protected void report(Exception paramException)
    {
      super.report(paramException);
      try
      {
        this.cacheResponse.getBody().close();
        label15: if (this.closed);
        do
        {
          return;
          this.closed = true;
        }
        while (this.closedCallback == null);
        this.closedCallback.onCompleted(paramException);
        return;
      }
      catch (Exception localException)
      {
        break label15;
      }
    }

    public void resume()
    {
      this.paused = false;
      spew();
    }

    public void setClosedCallback(CompletedCallback paramCompletedCallback)
    {
      this.closedCallback = paramCompletedCallback;
    }

    public void setWriteableCallback(WritableCallback paramWritableCallback)
    {
    }

    void spew()
    {
      getServer().post(new Runnable()
      {
        public void run()
        {
          ResponseCacheMiddleware.CachedSocket.this.spewInternal();
        }
      });
    }

    void spewInternal()
    {
      if (this.pending.remaining() > 0)
      {
        Util.emitAllData(this, this.pending);
        if (this.pending.remaining() <= 0);
      }
      while (true)
      {
        return;
        try
        {
          int i;
          do
          {
            localByteBuffer.limit(i);
            this.pending.add(localByteBuffer);
            Util.emitAllData(this, this.pending);
            if (this.pending.remaining() != 0)
              break;
            ByteBuffer localByteBuffer = ByteBufferList.obtain(8192);
            i = this.cacheResponse.getBody().read(localByteBuffer.array());
          }
          while (i != -1);
          report(null);
          return;
        }
        catch (IOException localIOException)
        {
          report(localIOException);
        }
      }
    }

    public void write(ByteBufferList paramByteBufferList)
    {
      paramByteBufferList.clear();
    }

    public void write(ByteBuffer paramByteBuffer)
    {
      paramByteBuffer.limit(paramByteBuffer.position());
    }
  }

  private static final class Entry
  {
    private final String cipherSuite;
    private final Certificate[] localCertificates;
    private final Certificate[] peerCertificates;
    private final String requestMethod;
    private final RawHeaders responseHeaders;
    private final String uri;
    private final RawHeaders varyHeaders;

    public Entry(InputStream paramInputStream)
      throws IOException
    {
      try
      {
        StrictLineReader localStrictLineReader = new StrictLineReader(paramInputStream, Charsets.US_ASCII);
        this.uri = localStrictLineReader.readLine();
        this.requestMethod = localStrictLineReader.readLine();
        this.varyHeaders = new RawHeaders();
        int j = localStrictLineReader.readInt();
        for (int k = 0; k < j; k++)
          this.varyHeaders.addLine(localStrictLineReader.readLine());
        this.responseHeaders = new RawHeaders();
        this.responseHeaders.setStatusLine(localStrictLineReader.readLine());
        int m = localStrictLineReader.readInt();
        while (i < m)
        {
          this.responseHeaders.addLine(localStrictLineReader.readLine());
          i++;
        }
        this.cipherSuite = null;
        this.peerCertificates = null;
        this.localCertificates = null;
        return;
      }
      finally
      {
        paramInputStream.close();
      }
    }

    public Entry(URI paramURI, RawHeaders paramRawHeaders, AsyncHttpRequest paramAsyncHttpRequest, ResponseHeaders paramResponseHeaders)
    {
      this.uri = paramURI.toString();
      this.varyHeaders = paramRawHeaders;
      this.requestMethod = paramAsyncHttpRequest.getMethod();
      this.responseHeaders = paramResponseHeaders.getHeaders();
      this.cipherSuite = null;
      this.peerCertificates = null;
      this.localCertificates = null;
    }

    private boolean isHttps()
    {
      return this.uri.startsWith("https://");
    }

    private Certificate[] readCertArray(StrictLineReader paramStrictLineReader)
      throws IOException
    {
      int i = 0;
      int j = paramStrictLineReader.readInt();
      Certificate[] arrayOfCertificate;
      if (j == -1)
        arrayOfCertificate = null;
      while (true)
      {
        return arrayOfCertificate;
        try
        {
          CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
          arrayOfCertificate = new Certificate[j];
          while (i < arrayOfCertificate.length)
          {
            arrayOfCertificate[i] = localCertificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decode(paramStrictLineReader.readLine(), 0)));
            i++;
          }
        }
        catch (CertificateException localCertificateException)
        {
          throw new IOException(localCertificateException.getMessage());
        }
      }
    }

    private void writeCertArray(Writer paramWriter, Certificate[] paramArrayOfCertificate)
      throws IOException
    {
      int i = 0;
      if (paramArrayOfCertificate == null)
        paramWriter.write("-1\n");
      while (true)
      {
        return;
        try
        {
          paramWriter.write(Integer.toString(paramArrayOfCertificate.length) + '\n');
          int j = paramArrayOfCertificate.length;
          while (i < j)
          {
            String str = Base64.encodeToString(paramArrayOfCertificate[i].getEncoded(), 0);
            paramWriter.write(str + '\n');
            i++;
          }
        }
        catch (CertificateEncodingException localCertificateEncodingException)
        {
          throw new IOException(localCertificateEncodingException.getMessage());
        }
      }
    }

    public boolean matches(URI paramURI, String paramString, Map<String, List<String>> paramMap)
    {
      return (this.uri.equals(paramURI.toString())) && (this.requestMethod.equals(paramString)) && (new ResponseHeaders(paramURI, this.responseHeaders).varyMatches(this.varyHeaders.toMultimap(), paramMap));
    }

    public void writeTo(DiskLruCache.Editor paramEditor)
      throws IOException
    {
      int i = 0;
      BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(paramEditor.newOutputStream(0), Charsets.UTF_8));
      localBufferedWriter.write(this.uri + '\n');
      localBufferedWriter.write(this.requestMethod + '\n');
      localBufferedWriter.write(Integer.toString(this.varyHeaders.length()) + '\n');
      for (int j = 0; j < this.varyHeaders.length(); j++)
        localBufferedWriter.write(this.varyHeaders.getFieldName(j) + ": " + this.varyHeaders.getValue(j) + '\n');
      localBufferedWriter.write(this.responseHeaders.getStatusLine() + '\n');
      localBufferedWriter.write(Integer.toString(this.responseHeaders.length()) + '\n');
      while (i < this.responseHeaders.length())
      {
        localBufferedWriter.write(this.responseHeaders.getFieldName(i) + ": " + this.responseHeaders.getValue(i) + '\n');
        i++;
      }
      if (isHttps())
      {
        localBufferedWriter.write(10);
        localBufferedWriter.write(this.cipherSuite + '\n');
        writeCertArray(localBufferedWriter, this.peerCertificates);
        writeCertArray(localBufferedWriter, this.localCertificates);
      }
      localBufferedWriter.close();
    }
  }

  static class EntryCacheResponse extends CacheResponse
  {
    private final ResponseCacheMiddleware.Entry entry;
    private final InputStream in;
    private final DiskLruCache.Snapshot snapshot;

    public EntryCacheResponse(ResponseCacheMiddleware.Entry paramEntry, DiskLruCache.Snapshot paramSnapshot)
    {
      this.entry = paramEntry;
      this.snapshot = paramSnapshot;
      this.in = ResponseCacheMiddleware.newBodyInputStream(paramSnapshot);
    }

    public InputStream getBody()
    {
      return this.in;
    }

    public Map<String, List<String>> getHeaders()
    {
      return this.entry.responseHeaders.toMultimap();
    }
  }

  static class EntrySecureCacheResponse extends SecureCacheResponse
  {
    private final ResponseCacheMiddleware.Entry entry;
    private final InputStream in;
    private final DiskLruCache.Snapshot snapshot;

    public EntrySecureCacheResponse(ResponseCacheMiddleware.Entry paramEntry, DiskLruCache.Snapshot paramSnapshot)
    {
      this.entry = paramEntry;
      this.snapshot = paramSnapshot;
      this.in = ResponseCacheMiddleware.newBodyInputStream(paramSnapshot);
    }

    public InputStream getBody()
    {
      return this.in;
    }

    public String getCipherSuite()
    {
      return this.entry.cipherSuite;
    }

    public Map<String, List<String>> getHeaders()
    {
      return this.entry.responseHeaders.toMultimap();
    }

    public List<Certificate> getLocalCertificateChain()
    {
      if ((this.entry.localCertificates == null) || (this.entry.localCertificates.length == 0))
        return null;
      return Arrays.asList((Object[])this.entry.localCertificates.clone());
    }

    public Principal getLocalPrincipal()
    {
      if ((this.entry.localCertificates == null) || (this.entry.localCertificates.length == 0))
        return null;
      return ((X509Certificate)this.entry.localCertificates[0]).getSubjectX500Principal();
    }

    public Principal getPeerPrincipal()
      throws SSLPeerUnverifiedException
    {
      if ((this.entry.peerCertificates == null) || (this.entry.peerCertificates.length == 0))
        throw new SSLPeerUnverifiedException(null);
      return ((X509Certificate)this.entry.peerCertificates[0]).getSubjectX500Principal();
    }

    public List<Certificate> getServerCertificateChain()
      throws SSLPeerUnverifiedException
    {
      if ((this.entry.peerCertificates == null) || (this.entry.peerCertificates.length == 0))
        throw new SSLPeerUnverifiedException(null);
      return Arrays.asList((Object[])this.entry.peerCertificates.clone());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.ResponseCacheMiddleware
 * JD-Core Version:    0.6.2
 */