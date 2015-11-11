package u.aly;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

public class bo
{
  private static final String a = bo.class.getName();
  private Map<String, String> b;

  // ERROR //
  private static String a(InputStream paramInputStream)
  {
    // Byte code:
    //   0: new 27	java/io/BufferedReader
    //   3: dup
    //   4: new 29	java/io/InputStreamReader
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 32	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: sipush 8192
    //   15: invokespecial 35	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   18: astore_1
    //   19: new 37	java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial 38	java/lang/StringBuilder:<init>	()V
    //   26: astore_2
    //   27: aload_1
    //   28: invokevirtual 41	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   31: astore 7
    //   33: aload 7
    //   35: ifnonnull +12 -> 47
    //   38: aload_0
    //   39: invokevirtual 46	java/io/InputStream:close	()V
    //   42: aload_2
    //   43: invokevirtual 49	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   46: areturn
    //   47: aload_2
    //   48: new 37	java/lang/StringBuilder
    //   51: dup
    //   52: aload 7
    //   54: invokestatic 55	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   57: invokespecial 58	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   60: ldc 60
    //   62: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: invokevirtual 49	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: pop
    //   72: goto -45 -> 27
    //   75: astore 5
    //   77: getstatic 19	u/aly/bo:a	Ljava/lang/String;
    //   80: ldc 66
    //   82: aload 5
    //   84: invokestatic 71	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   87: aload_0
    //   88: invokevirtual 46	java/io/InputStream:close	()V
    //   91: aconst_null
    //   92: areturn
    //   93: astore 6
    //   95: getstatic 19	u/aly/bo:a	Ljava/lang/String;
    //   98: ldc 66
    //   100: aload 6
    //   102: invokestatic 71	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   105: aconst_null
    //   106: areturn
    //   107: astore_3
    //   108: aload_0
    //   109: invokevirtual 46	java/io/InputStream:close	()V
    //   112: aload_3
    //   113: athrow
    //   114: astore 4
    //   116: getstatic 19	u/aly/bo:a	Ljava/lang/String;
    //   119: ldc 66
    //   121: aload 4
    //   123: invokestatic 71	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   126: aconst_null
    //   127: areturn
    //   128: astore 9
    //   130: getstatic 19	u/aly/bo:a	Ljava/lang/String;
    //   133: ldc 66
    //   135: aload 9
    //   137: invokestatic 71	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   140: aconst_null
    //   141: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   27	33	75	java/io/IOException
    //   47	72	75	java/io/IOException
    //   87	91	93	java/io/IOException
    //   27	33	107	finally
    //   47	72	107	finally
    //   77	87	107	finally
    //   108	112	114	java/io/IOException
    //   38	42	128	java/io/IOException
  }

  private JSONObject a(String paramString)
  {
    int i = new Random().nextInt(1000);
    while (true)
    {
      HttpResponse localHttpResponse;
      InputStream localInputStream;
      String str2;
      try
      {
        String str1 = System.getProperty("line.separator");
        if (paramString.length() <= 1)
        {
          bj.b(a, i + ":\tInvalid baseUrl.");
          return null;
        }
        bj.a(a, i + ":\tget: " + paramString);
        HttpGet localHttpGet = new HttpGet(paramString);
        Iterator localIterator;
        if ((this.b != null) && (this.b.size() > 0))
        {
          localIterator = this.b.keySet().iterator();
          if (localIterator.hasNext());
        }
        else
        {
          localHttpResponse = new DefaultHttpClient(b()).execute(localHttpGet);
          if (localHttpResponse.getStatusLine().getStatusCode() != 200)
            break label490;
          HttpEntity localHttpEntity = localHttpResponse.getEntity();
          if (localHttpEntity == null)
            break label540;
          localInputStream = localHttpEntity.getContent();
          localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
          if ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("gzip")))
            continue;
          bj.a(a, i + "  Use GZIPInputStream get data....");
          localObject = new GZIPInputStream(localInputStream);
          str2 = a((InputStream)localObject);
          bj.a(a, i + ":\tresponse: " + str1 + str2);
          if (str2 != null)
            break label480;
          return null;
        }
        String str3 = (String)localIterator.next();
        localHttpGet.addHeader(str3, (String)this.b.get(str3));
        continue;
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        Header localHeader;
        bj.c(a, i + ":\tClientProtocolException,Failed to send message." + paramString, localClientProtocolException);
        return null;
        if ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("deflate")))
          break label542;
        bj.a(a, i + "  Use InflaterInputStream get data....");
        localObject = new InflaterInputStream(localInputStream);
        continue;
      }
      catch (Exception localException)
      {
        bj.c(a, i + ":\tIOException,Failed to send message." + paramString, localException);
        return null;
      }
      label480: return new JSONObject(str2);
      label490: bj.c(a, i + ":\tFailed to send message. StatusCode = " + localHttpResponse.getStatusLine().getStatusCode() + bv.a + paramString);
      label540: return null;
      label542: Object localObject = localInputStream;
    }
  }

  private JSONObject a(String paramString, JSONObject paramJSONObject)
  {
    String str1 = paramJSONObject.toString();
    int i = new Random().nextInt(1000);
    bj.c(a, i + ":\trequest: " + paramString + bv.a + str1);
    HttpPost localHttpPost = new HttpPost(paramString);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(b());
    while (true)
    {
      InputStream localInputStream;
      try
      {
        if (a())
        {
          byte[] arrayOfByte = bu.a("content=" + str1, Charset.defaultCharset().toString());
          localHttpPost.addHeader("Content-Encoding", "deflate");
          localHttpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(arrayOfByte), arrayOfByte.length));
          localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
          if (localHttpResponse.getStatusLine().getStatusCode() != 200)
            continue;
          HttpEntity localHttpEntity = localHttpResponse.getEntity();
          if (localHttpEntity == null)
            break;
          localInputStream = localHttpEntity.getContent();
          Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
          if ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("deflate")))
            break label516;
          localObject = new InflaterInputStream(localInputStream);
          str2 = a((InputStream)localObject);
          bj.a(a, i + ":\tresponse: " + bv.a + str2);
          if (str2 == null)
            return null;
        }
        else
        {
          ArrayList localArrayList = new ArrayList(1);
          localArrayList.add(new BasicNameValuePair("content", str1));
          localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
          continue;
        }
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        HttpResponse localHttpResponse;
        String str2;
        bj.c(a, i + ":\tClientProtocolException,Failed to send message." + paramString, localClientProtocolException);
        return null;
        return new JSONObject(str2);
        bj.c(a, i + ":\tFailed to send message. StatusCode = " + localHttpResponse.getStatusLine().getStatusCode() + bv.a + paramString);
        return null;
      }
      catch (IOException localIOException)
      {
        bj.c(a, i + ":\tIOException,Failed to send message." + paramString, localIOException);
        return null;
      }
      catch (JSONException localJSONException)
      {
        bj.c(a, i + ":\tIOException,Failed to send message." + paramString, localJSONException);
        return null;
      }
      label516: Object localObject = localInputStream;
    }
    return null;
  }

  private HttpParams b()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
    HttpProtocolParams.setUserAgent(localBasicHttpParams, System.getProperty("http.agent"));
    return localBasicHttpParams;
  }

  private void b(String paramString)
  {
    if ((bv.d(paramString)) || (!(bp.c.equals(paramString.trim()) ^ bp.b.equals(paramString.trim()))))
      throw new RuntimeException("验证请求方式失败[" + paramString + "]");
  }

  public bo a(Map<String, String> paramMap)
  {
    this.b = paramMap;
    return this;
  }

  public <T extends bq> T a(bp parambp, Class<T> paramClass)
  {
    String str = parambp.c().trim();
    b(str);
    JSONObject localJSONObject;
    if (bp.c.equals(str))
      localJSONObject = a(parambp.b());
    while (true)
      if (localJSONObject == null)
      {
        return null;
        if (bp.b.equals(str))
          localJSONObject = a(parambp.d, parambp.a());
      }
      else
      {
        try
        {
          bq localbq = (bq)paramClass.getConstructor(new Class[] { JSONObject.class }).newInstance(new Object[] { localJSONObject });
          return localbq;
        }
        catch (SecurityException localSecurityException)
        {
          bj.b(a, "SecurityException", localSecurityException);
          return null;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          while (true)
            bj.b(a, "NoSuchMethodException", localNoSuchMethodException);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          while (true)
            bj.b(a, "IllegalArgumentException", localIllegalArgumentException);
        }
        catch (InstantiationException localInstantiationException)
        {
          while (true)
            bj.b(a, "InstantiationException", localInstantiationException);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          while (true)
            bj.b(a, "IllegalAccessException", localIllegalAccessException);
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          while (true)
            bj.b(a, "InvocationTargetException", localInvocationTargetException);
        }
        localJSONObject = null;
      }
  }

  public boolean a()
  {
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.bo
 * JD-Core Version:    0.6.2
 */