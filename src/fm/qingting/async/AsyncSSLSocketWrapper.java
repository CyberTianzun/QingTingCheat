package fm.qingting.async;

import android.os.Build.VERSION;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.callback.DataCallback;
import fm.qingting.async.callback.WritableCallback;
import fm.qingting.async.wrapper.AsyncSocketWrapper;
import java.nio.ByteBuffer;
import java.security.cert.X509Certificate;
import java.util.Set;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AsyncSSLSocketWrapper
  implements AsyncSocketWrapper, AsyncSSLSocket
{
  static SSLContext sslContext;
  boolean clientMode;
  SSLEngine engine;
  boolean finishedHandshake = false;
  DataCallback mDataCallback;
  BufferedDataEmitter mEmitter;
  private String mHost;
  private int mPort;
  ByteBuffer mReadTmp = ByteBufferList.obtain(8192);
  BufferedDataSink mSink;
  AsyncSocket mSocket;
  boolean mUnwrapping = false;
  private boolean mWrapping = false;
  ByteBuffer mWriteTmp = ByteBufferList.obtain(8192);
  WritableCallback mWriteableCallback;
  X509Certificate[] peerCertificates;
  TrustManager[] trustManagers;

  static
  {
    try
    {
      if (Build.VERSION.SDK_INT <= 15)
        throw new Exception();
    }
    catch (Exception localException1)
    {
      try
      {
        sslContext = SSLContext.getInstance("TLS");
        TrustManager[] arrayOfTrustManager = new TrustManager[1];
        arrayOfTrustManager[0] = new X509TrustManager()
        {
          public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
          {
          }

          public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
          {
            int i = paramAnonymousArrayOfX509Certificate.length;
            for (int j = 0; j < i; j++)
            {
              X509Certificate localX509Certificate = paramAnonymousArrayOfX509Certificate[j];
              if ((localX509Certificate != null) && (localX509Certificate.getCriticalExtensionOIDs() != null))
                localX509Certificate.getCriticalExtensionOIDs().remove("2.5.29.15");
            }
          }

          public X509Certificate[] getAcceptedIssuers()
          {
            return new X509Certificate[0];
          }
        };
        sslContext.init(null, arrayOfTrustManager, null);
        return;
        sslContext = SSLContext.getInstance("Default");
        return;
      }
      catch (Exception localException2)
      {
        localException1.printStackTrace();
        localException2.printStackTrace();
      }
    }
  }

  public AsyncSSLSocketWrapper(AsyncSocket paramAsyncSocket, String paramString, int paramInt)
  {
    this(paramAsyncSocket, paramString, paramInt, sslContext, null, true);
  }

  public AsyncSSLSocketWrapper(AsyncSocket paramAsyncSocket, String paramString, int paramInt, SSLContext paramSSLContext, TrustManager[] paramArrayOfTrustManager, boolean paramBoolean)
  {
    this.mSocket = paramAsyncSocket;
    this.clientMode = paramBoolean;
    this.trustManagers = paramArrayOfTrustManager;
    if (paramSSLContext == null)
      paramSSLContext = sslContext;
    if (paramString != null);
    for (this.engine = paramSSLContext.createSSLEngine(paramString, paramInt); ; this.engine = paramSSLContext.createSSLEngine())
    {
      this.mHost = paramString;
      this.mPort = paramInt;
      this.engine.setUseClientMode(paramBoolean);
      this.mSink = new BufferedDataSink(paramAsyncSocket);
      this.mSink.setMaxBuffer(0);
      this.mEmitter = new BufferedDataEmitter(paramAsyncSocket);
      this.mEmitter.setDataCallback(new DataCallback()
      {
        public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
        {
          if (AsyncSSLSocketWrapper.this.mUnwrapping)
            return;
          try
          {
            AsyncSSLSocketWrapper.this.mUnwrapping = true;
            ByteBufferList localByteBufferList = new ByteBufferList();
            AsyncSSLSocketWrapper.this.mReadTmp.position(0);
            AsyncSSLSocketWrapper.this.mReadTmp.limit(AsyncSSLSocketWrapper.this.mReadTmp.capacity());
            Object localObject2;
            if (paramAnonymousByteBufferList.size() > 0)
              localObject2 = paramAnonymousByteBufferList.getAll();
            while (true)
            {
              int i = ((ByteBuffer)localObject2).remaining();
              SSLEngineResult localSSLEngineResult = AsyncSSLSocketWrapper.this.engine.unwrap((ByteBuffer)localObject2, AsyncSSLSocketWrapper.this.mReadTmp);
              if (localSSLEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_OVERFLOW)
              {
                AsyncSSLSocketWrapper.this.addToPending(localByteBufferList);
                AsyncSSLSocketWrapper.this.mReadTmp = ByteBuffer.allocate(2 * AsyncSSLSocketWrapper.this.mReadTmp.remaining());
                i = -1;
              }
              AsyncSSLSocketWrapper.this.handleResult(localSSLEngineResult);
              if (((ByteBuffer)localObject2).remaining() == i)
              {
                if (((ByteBuffer)localObject2).remaining() > 0)
                  paramAnonymousByteBufferList.add(0, (ByteBuffer)localObject2);
                AsyncSSLSocketWrapper.this.addToPending(localByteBufferList);
                Util.emitAllData(AsyncSSLSocketWrapper.this, localByteBufferList);
                return;
                ByteBuffer localByteBuffer = ByteBuffer.allocate(0);
                localObject2 = localByteBuffer;
              }
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            AsyncSSLSocketWrapper.this.report(localException);
            return;
          }
          finally
          {
            AsyncSSLSocketWrapper.this.mUnwrapping = false;
          }
        }
      });
      return;
    }
  }

  // ERROR //
  private void handleResult(SSLEngineResult paramSSLEngineResult)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 165	javax/net/ssl/SSLEngineResult:getHandshakeStatus	()Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   4: getstatic 171	javax/net/ssl/SSLEngineResult$HandshakeStatus:NEED_TASK	Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   7: if_acmpne +15 -> 22
    //   10: aload_0
    //   11: getfield 107	fm/qingting/async/AsyncSSLSocketWrapper:engine	Ljavax/net/ssl/SSLEngine;
    //   14: invokevirtual 175	javax/net/ssl/SSLEngine:getDelegatedTask	()Ljava/lang/Runnable;
    //   17: invokeinterface 180 1 0
    //   22: aload_1
    //   23: invokevirtual 165	javax/net/ssl/SSLEngineResult:getHandshakeStatus	()Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   26: getstatic 183	javax/net/ssl/SSLEngineResult$HandshakeStatus:NEED_WRAP	Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   29: if_acmpne +11 -> 40
    //   32: aload_0
    //   33: iconst_0
    //   34: invokestatic 188	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   37: invokevirtual 192	fm/qingting/async/AsyncSSLSocketWrapper:write	(Ljava/nio/ByteBuffer;)V
    //   40: aload_1
    //   41: invokevirtual 165	javax/net/ssl/SSLEngineResult:getHandshakeStatus	()Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   44: getstatic 195	javax/net/ssl/SSLEngineResult$HandshakeStatus:NEED_UNWRAP	Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   47: if_acmpne +10 -> 57
    //   50: aload_0
    //   51: getfield 135	fm/qingting/async/AsyncSSLSocketWrapper:mEmitter	Lfm/qingting/async/BufferedDataEmitter;
    //   54: invokevirtual 198	fm/qingting/async/BufferedDataEmitter:onDataAvailable	()V
    //   57: aload_0
    //   58: getfield 91	fm/qingting/async/AsyncSSLSocketWrapper:finishedHandshake	Z
    //   61: ifne +217 -> 278
    //   64: aload_0
    //   65: getfield 107	fm/qingting/async/AsyncSSLSocketWrapper:engine	Ljavax/net/ssl/SSLEngine;
    //   68: invokevirtual 199	javax/net/ssl/SSLEngine:getHandshakeStatus	()Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   71: getstatic 202	javax/net/ssl/SSLEngineResult$HandshakeStatus:NOT_HANDSHAKING	Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   74: if_acmpeq +16 -> 90
    //   77: aload_0
    //   78: getfield 107	fm/qingting/async/AsyncSSLSocketWrapper:engine	Ljavax/net/ssl/SSLEngine;
    //   81: invokevirtual 199	javax/net/ssl/SSLEngine:getHandshakeStatus	()Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   84: getstatic 205	javax/net/ssl/SSLEngineResult$HandshakeStatus:FINISHED	Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
    //   87: if_acmpne +191 -> 278
    //   90: aload_0
    //   91: getfield 99	fm/qingting/async/AsyncSSLSocketWrapper:clientMode	Z
    //   94: ifeq +198 -> 292
    //   97: aload_0
    //   98: getfield 101	fm/qingting/async/AsyncSSLSocketWrapper:trustManagers	[Ljavax/net/ssl/TrustManager;
    //   101: astore_3
    //   102: aload_3
    //   103: ifnonnull +219 -> 322
    //   106: invokestatic 211	javax/net/ssl/TrustManagerFactory:getDefaultAlgorithm	()Ljava/lang/String;
    //   109: invokestatic 214	javax/net/ssl/TrustManagerFactory:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/TrustManagerFactory;
    //   112: astore 4
    //   114: aload 4
    //   116: aconst_null
    //   117: checkcast 216	java/security/KeyStore
    //   120: invokevirtual 219	javax/net/ssl/TrustManagerFactory:init	(Ljava/security/KeyStore;)V
    //   123: aload 4
    //   125: invokevirtual 223	javax/net/ssl/TrustManagerFactory:getTrustManagers	()[Ljavax/net/ssl/TrustManager;
    //   128: astore 5
    //   130: aload 5
    //   132: arraylength
    //   133: istore 6
    //   135: iconst_0
    //   136: istore 7
    //   138: iload 7
    //   140: iload 6
    //   142: if_icmpge +174 -> 316
    //   145: aload 5
    //   147: iload 7
    //   149: aaload
    //   150: astore 8
    //   152: aload 8
    //   154: checkcast 225	javax/net/ssl/X509TrustManager
    //   157: astore 10
    //   159: aload_0
    //   160: aload_0
    //   161: getfield 107	fm/qingting/async/AsyncSSLSocketWrapper:engine	Ljavax/net/ssl/SSLEngine;
    //   164: invokevirtual 229	javax/net/ssl/SSLEngine:getSession	()Ljavax/net/ssl/SSLSession;
    //   167: invokeinterface 235 1 0
    //   172: checkcast 236	[Ljava/security/cert/X509Certificate;
    //   175: checkcast 236	[Ljava/security/cert/X509Certificate;
    //   178: putfield 238	fm/qingting/async/AsyncSSLSocketWrapper:peerCertificates	[Ljava/security/cert/X509Certificate;
    //   181: aload 10
    //   183: aload_0
    //   184: getfield 238	fm/qingting/async/AsyncSSLSocketWrapper:peerCertificates	[Ljava/security/cert/X509Certificate;
    //   187: ldc 240
    //   189: invokeinterface 244 3 0
    //   194: aload_0
    //   195: getfield 109	fm/qingting/async/AsyncSSLSocketWrapper:mHost	Ljava/lang/String;
    //   198: ifnull +35 -> 233
    //   201: new 246	org/apache/http/conn/ssl/StrictHostnameVerifier
    //   204: dup
    //   205: invokespecial 247	org/apache/http/conn/ssl/StrictHostnameVerifier:<init>	()V
    //   208: aload_0
    //   209: getfield 109	fm/qingting/async/AsyncSSLSocketWrapper:mHost	Ljava/lang/String;
    //   212: aload_0
    //   213: getfield 238	fm/qingting/async/AsyncSSLSocketWrapper:peerCertificates	[Ljava/security/cert/X509Certificate;
    //   216: iconst_0
    //   217: aaload
    //   218: invokestatic 251	org/apache/http/conn/ssl/StrictHostnameVerifier:getCNs	(Ljava/security/cert/X509Certificate;)[Ljava/lang/String;
    //   221: aload_0
    //   222: getfield 238	fm/qingting/async/AsyncSSLSocketWrapper:peerCertificates	[Ljava/security/cert/X509Certificate;
    //   225: iconst_0
    //   226: aaload
    //   227: invokestatic 254	org/apache/http/conn/ssl/StrictHostnameVerifier:getDNSSubjectAlts	(Ljava/security/cert/X509Certificate;)[Ljava/lang/String;
    //   230: invokevirtual 258	org/apache/http/conn/ssl/StrictHostnameVerifier:verify	(Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V
    //   233: iconst_1
    //   234: istore 11
    //   236: aload_0
    //   237: iconst_1
    //   238: putfield 91	fm/qingting/async/AsyncSSLSocketWrapper:finishedHandshake	Z
    //   241: iload 11
    //   243: ifne +49 -> 292
    //   246: new 260	fm/qingting/async/AsyncSSLException
    //   249: dup
    //   250: invokespecial 261	fm/qingting/async/AsyncSSLException:<init>	()V
    //   253: astore 12
    //   255: aload_0
    //   256: aload 12
    //   258: invokespecial 159	fm/qingting/async/AsyncSSLSocketWrapper:report	(Ljava/lang/Exception;)V
    //   261: aload 12
    //   263: invokevirtual 265	fm/qingting/async/AsyncSSLException:getIgnore	()Z
    //   266: ifne +26 -> 292
    //   269: aload 12
    //   271: athrow
    //   272: astore_2
    //   273: aload_0
    //   274: aload_2
    //   275: invokespecial 159	fm/qingting/async/AsyncSSLSocketWrapper:report	(Ljava/lang/Exception;)V
    //   278: return
    //   279: astore 9
    //   281: aload 9
    //   283: invokevirtual 74	java/lang/Exception:printStackTrace	()V
    //   286: iinc 7 1
    //   289: goto -151 -> 138
    //   292: aload_0
    //   293: getfield 267	fm/qingting/async/AsyncSSLSocketWrapper:mWriteableCallback	Lfm/qingting/async/callback/WritableCallback;
    //   296: ifnull +12 -> 308
    //   299: aload_0
    //   300: getfield 267	fm/qingting/async/AsyncSSLSocketWrapper:mWriteableCallback	Lfm/qingting/async/callback/WritableCallback;
    //   303: invokeinterface 272 1 0
    //   308: aload_0
    //   309: getfield 135	fm/qingting/async/AsyncSSLSocketWrapper:mEmitter	Lfm/qingting/async/BufferedDataEmitter;
    //   312: invokevirtual 198	fm/qingting/async/BufferedDataEmitter:onDataAvailable	()V
    //   315: return
    //   316: iconst_0
    //   317: istore 11
    //   319: goto -83 -> 236
    //   322: aload_3
    //   323: astore 5
    //   325: goto -195 -> 130
    //
    // Exception table:
    //   from	to	target	type
    //   57	90	272	java/lang/Exception
    //   90	102	272	java/lang/Exception
    //   106	130	272	java/lang/Exception
    //   130	135	272	java/lang/Exception
    //   145	152	272	java/lang/Exception
    //   236	241	272	java/lang/Exception
    //   246	272	272	java/lang/Exception
    //   281	286	272	java/lang/Exception
    //   292	308	272	java/lang/Exception
    //   308	315	272	java/lang/Exception
    //   152	233	279	java/lang/Exception
  }

  private void report(Exception paramException)
  {
    CompletedCallback localCompletedCallback = getEndCallback();
    if (localCompletedCallback != null)
      localCompletedCallback.onCompleted(paramException);
  }

  private void writeTmp()
  {
    this.mWriteTmp.limit(this.mWriteTmp.position());
    this.mWriteTmp.position(0);
    if (this.mWriteTmp.remaining() > 0)
      this.mSink.write(this.mWriteTmp);
  }

  void addToPending(ByteBufferList paramByteBufferList)
  {
    if (this.mReadTmp.position() > 0)
    {
      this.mReadTmp.limit(this.mReadTmp.position());
      this.mReadTmp.position(0);
      paramByteBufferList.add(this.mReadTmp);
      this.mReadTmp = ByteBufferList.obtain(this.mReadTmp.capacity());
    }
  }

  boolean checkWrapResult(SSLEngineResult paramSSLEngineResult)
  {
    if (paramSSLEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_OVERFLOW)
    {
      this.mWriteTmp = ByteBuffer.allocate(2 * this.mWriteTmp.remaining());
      return false;
    }
    return true;
  }

  public void close()
  {
    this.mSocket.close();
  }

  public void end()
  {
    this.mSocket.end();
  }

  public CompletedCallback getClosedCallback()
  {
    return this.mSocket.getClosedCallback();
  }

  public DataCallback getDataCallback()
  {
    return this.mDataCallback;
  }

  public DataEmitter getDataEmitter()
  {
    return this.mSocket;
  }

  public CompletedCallback getEndCallback()
  {
    return this.mSocket.getEndCallback();
  }

  public String getHost()
  {
    return this.mHost;
  }

  public X509Certificate[] getPeerCertificates()
  {
    return this.peerCertificates;
  }

  public int getPort()
  {
    return this.mPort;
  }

  public AsyncServer getServer()
  {
    return this.mSocket.getServer();
  }

  public AsyncSocket getSocket()
  {
    return this.mSocket;
  }

  public WritableCallback getWriteableCallback()
  {
    return this.mWriteableCallback;
  }

  public boolean isChunked()
  {
    return this.mSocket.isChunked();
  }

  public boolean isOpen()
  {
    return this.mSocket.isOpen();
  }

  public boolean isPaused()
  {
    return this.mSocket.isPaused();
  }

  public void pause()
  {
    this.mSocket.pause();
  }

  public void resume()
  {
    this.mSocket.resume();
  }

  public void setClosedCallback(CompletedCallback paramCompletedCallback)
  {
    this.mSocket.setClosedCallback(paramCompletedCallback);
  }

  public void setDataCallback(DataCallback paramDataCallback)
  {
    this.mDataCallback = paramDataCallback;
  }

  public void setEndCallback(CompletedCallback paramCompletedCallback)
  {
    this.mSocket.setEndCallback(paramCompletedCallback);
  }

  public void setWriteableCallback(WritableCallback paramWritableCallback)
  {
    this.mWriteableCallback = paramWritableCallback;
  }

  public void write(ByteBufferList paramByteBufferList)
  {
    if (this.mWrapping);
    while (this.mSink.remaining() > 0)
      return;
    this.mWrapping = true;
    SSLEngineResult localSSLEngineResult = null;
    while (true)
    {
      if ((this.finishedHandshake) && (paramByteBufferList.remaining() == 0))
      {
        this.mWrapping = false;
        return;
      }
      int i = paramByteBufferList.remaining();
      this.mWriteTmp.position(0);
      this.mWriteTmp.limit(this.mWriteTmp.capacity());
      try
      {
        ByteBuffer[] arrayOfByteBuffer = paramByteBufferList.getAllArray();
        localSSLEngineResult = this.engine.wrap(arrayOfByteBuffer, this.mWriteTmp);
        paramByteBufferList.addAll(arrayOfByteBuffer);
        boolean bool = checkWrapResult(localSSLEngineResult);
        if (!bool)
          j = -1;
      }
      catch (SSLException localSSLException1)
      {
        while (true)
        {
          try
          {
            writeTmp();
            handleResult(localSSLEngineResult);
            if (((j != paramByteBufferList.remaining()) || ((localSSLEngineResult != null) && (localSSLEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP))) && (this.mSink.remaining() == 0))
              break;
            this.mWrapping = false;
            return;
            localSSLException1 = localSSLException1;
            j = i;
            SSLException localSSLException2 = localSSLException1;
            report(localSSLException2);
            continue;
          }
          catch (SSLException localSSLException3)
          {
            continue;
          }
          int j = i;
        }
      }
    }
  }

  public void write(ByteBuffer paramByteBuffer)
  {
    if (this.mWrapping);
    while (this.mSink.remaining() > 0)
      return;
    this.mWrapping = true;
    SSLEngineResult localSSLEngineResult = null;
    while (true)
    {
      if ((this.finishedHandshake) && (paramByteBuffer.remaining() == 0))
      {
        this.mWrapping = false;
        return;
      }
      int i = paramByteBuffer.remaining();
      this.mWriteTmp.position(0);
      this.mWriteTmp.limit(this.mWriteTmp.capacity());
      try
      {
        localSSLEngineResult = this.engine.wrap(paramByteBuffer, this.mWriteTmp);
        boolean bool = checkWrapResult(localSSLEngineResult);
        if (!bool)
        {
          j = -1;
          while (true)
          {
            try
            {
              writeTmp();
              handleResult(localSSLEngineResult);
              if (((j != paramByteBuffer.remaining()) || ((localSSLEngineResult != null) && (localSSLEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP))) && (this.mSink.remaining() == 0))
                break;
              this.mWrapping = false;
              return;
            }
            catch (SSLException localSSLException2)
            {
            }
            report(localSSLException2);
          }
        }
      }
      catch (SSLException localSSLException1)
      {
        while (true)
        {
          int j = i;
          Object localObject = localSSLException1;
          continue;
          j = i;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.AsyncSSLSocketWrapper
 * JD-Core Version:    0.6.2
 */