package com.tendcloud.tenddata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class ad
{
  private static final int e = 60000;
  private static final int f = 60000;
  final String a;
  final int b;
  HttpClient c;
  String d;

  public ad(String paramString1, String paramString2, int paramInt)
  {
    this(f(paramString1), paramString2, null, paramInt);
  }

  public ad(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this(f(paramString1), paramString2, paramString3, paramInt);
  }

  public ad(X509Certificate paramX509Certificate, String paramString1, String paramString2, int paramInt)
  {
    this.a = paramString1;
    this.b = paramInt;
    this.d = paramString2;
    try
    {
      a locala = new a(paramX509Certificate);
      locala.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      Scheme localScheme = new Scheme("https", locala, paramInt);
      this.c = e(paramString1);
      this.c.getConnectionManager().getSchemeRegistry().register(localScheme);
      c(this.a);
      return;
    }
    catch (KeyManagementException localKeyManagementException)
    {
      localKeyManagementException.printStackTrace();
      return;
    }
    catch (UnrecoverableKeyException localUnrecoverableKeyException)
    {
      localUnrecoverableKeyException.printStackTrace();
      return;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
      return;
    }
    catch (KeyStoreException localKeyStoreException)
    {
      localKeyStoreException.printStackTrace();
    }
  }

  private HttpClient e(String paramString)
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 60000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 60000);
    HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
    localDefaultHttpClient.addRequestInterceptor(new t(this, paramString));
    return localDefaultHttpClient;
  }

  // ERROR //
  private static X509Certificate f(String paramString)
  {
    // Byte code:
    //   0: new 128	java/io/ByteArrayInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokevirtual 134	java/lang/String:getBytes	()[B
    //   8: invokespecial 137	java/io/ByteArrayInputStream:<init>	([B)V
    //   11: astore_1
    //   12: ldc 139
    //   14: invokestatic 145	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   17: aload_1
    //   18: invokevirtual 149	java/security/cert/CertificateFactory:generateCertificate	(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
    //   21: checkcast 151	java/security/cert/X509Certificate
    //   24: astore 4
    //   26: aload 4
    //   28: areturn
    //   29: astore_3
    //   30: aconst_null
    //   31: areturn
    //   32: astore_2
    //   33: aload_2
    //   34: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   12	26	29	java/lang/Exception
    //   12	26	32	finally
  }

  public int a(String paramString, byte[] paramArrayOfByte)
  {
    try
    {
      HttpPost localHttpPost = new HttpPost(d(paramString));
      localHttpPost.setEntity(new ByteArrayEntity(paramArrayOfByte));
      HttpResponse localHttpResponse = a().execute(localHttpPost);
      localHttpResponse.getEntity().consumeContent();
      int i = localHttpResponse.getStatusLine().getStatusCode();
      return i;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      return 600;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
    catch (URISyntaxException localURISyntaxException)
    {
      while (true)
        localURISyntaxException.printStackTrace();
    }
  }

  public final String a(String paramString)
  {
    HttpResponse localHttpResponse;
    StringBuilder localStringBuilder;
    try
    {
      HttpGet localHttpGet = new HttpGet(d(paramString));
      localHttpResponse = a().execute(localHttpGet);
      try
      {
        if (localHttpResponse.getStatusLine().getStatusCode() == 200)
        {
          InputStreamReader localInputStreamReader = new InputStreamReader(localHttpResponse.getEntity().getContent());
          localStringBuilder = new StringBuilder();
          char[] arrayOfChar = new char[64];
          while (true)
          {
            int i = localInputStreamReader.read(arrayOfChar);
            if (i == -1)
              break;
            localStringBuilder.append(arrayOfChar, 0, i);
          }
        }
      }
      finally
      {
        localHttpResponse.getEntity().consumeContent();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      return null;
      String str = localStringBuilder.toString();
      localHttpResponse.getEntity().consumeContent();
      return str;
      localHttpResponse.getEntity().consumeContent();
    }
  }

  public final String a(String paramString, File paramFile)
  {
    HttpResponse localHttpResponse;
    FileOutputStream localFileOutputStream;
    MessageDigest localMessageDigest;
    try
    {
      HttpGet localHttpGet = new HttpGet(d(paramString));
      localHttpResponse = a().execute(localHttpGet);
      try
      {
        if (localHttpResponse.getStatusLine().getStatusCode() == 200)
        {
          InputStream localInputStream = localHttpResponse.getEntity().getContent();
          localFileOutputStream = new FileOutputStream(paramFile);
          localMessageDigest = MessageDigest.getInstance("MD5");
          try
          {
            byte[] arrayOfByte = new byte[4096];
            while (true)
            {
              int i = localInputStream.read(arrayOfByte);
              if (i == -1)
                break;
              localFileOutputStream.write(arrayOfByte, 0, i);
              localMessageDigest.update(arrayOfByte, 0, i);
            }
          }
          finally
          {
            localFileOutputStream.close();
          }
        }
      }
      finally
      {
        localHttpResponse.getEntity().consumeContent();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      return null;
      localFileOutputStream.close();
      String str = x.a(localMessageDigest.digest());
      localHttpResponse.getEntity().consumeContent();
      return str;
      localHttpResponse.getEntity().consumeContent();
    }
  }

  public HttpClient a()
  {
    return this.c;
  }

  public int b(String paramString)
  {
    try
    {
      HttpPost localHttpPost = new HttpPost(d(paramString));
      HttpResponse localHttpResponse = a().execute(localHttpPost);
      localHttpResponse.getEntity().consumeContent();
      int i = localHttpResponse.getStatusLine().getStatusCode();
      return i;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      return 600;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
    catch (URISyntaxException localURISyntaxException)
    {
      while (true)
        localURISyntaxException.printStackTrace();
    }
  }

  protected void c(String paramString)
  {
    try
    {
      this.d = InetAddress.getByName(paramString).getHostAddress();
      return;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      localUnknownHostException.printStackTrace();
    }
  }

  protected URI d(String paramString)
  {
    URL localURL = new URL(paramString);
    return new URL(localURL.getProtocol(), this.d, localURL.getPort(), localURL.getFile()).toURI();
  }

  static class a extends org.apache.http.conn.ssl.SSLSocketFactory
  {
    SSLContext a = SSLContext.getInstance("TLS");

    a(X509Certificate paramX509Certificate)
    {
      super();
      ad.b localb = new ad.b(paramX509Certificate);
      this.a.init(null, new TrustManager[] { localb }, null);
    }

    public Socket createSocket()
    {
      return this.a.getSocketFactory().createSocket();
    }

    public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
    {
      return this.a.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
    }
  }

  static class b
    implements X509TrustManager
  {
    X509Certificate a;

    b(X509Certificate paramX509Certificate)
    {
      this.a = paramX509Certificate;
    }

    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    {
    }

    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    {
      int i = paramArrayOfX509Certificate.length;
      for (int j = 0; j < i; j++)
        if (paramArrayOfX509Certificate[j].equals(this.a))
          return;
      throw new CertificateException("no trusted cert");
    }

    public X509Certificate[] getAcceptedIssuers()
    {
      return null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.ad
 * JD-Core Version:    0.6.2
 */