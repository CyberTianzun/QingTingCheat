package com.umeng.fb.net;

import android.text.TextUtils;
import com.umeng.fb.util.DeflaterHelper;
import com.umeng.fb.util.Helper;
import com.umeng.fb.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class UClient
{
  private static final String TAG = UClient.class.getName();
  private Map<String, String> headers;

  private JSONObject HttpRequestGet(String paramString)
  {
    int i = new Random().nextInt(1000);
    HttpResponse localHttpResponse;
    try
    {
      str1 = System.getProperty("line.separator");
      if (paramString.length() <= 1)
      {
        Log.e(TAG, i + ":\tInvalid baseUrl.");
        return null;
      }
      Log.i(TAG, i + ":\tget: " + paramString);
      localHttpGet = new HttpGet(paramString);
      if ((this.headers != null) && (this.headers.size() > 0))
      {
        Iterator localIterator = this.headers.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str3 = (String)localIterator.next();
          localHttpGet.addHeader(str3, (String)this.headers.get(str3));
        }
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      String str1;
      HttpGet localHttpGet;
      Log.d(TAG, i + ":\tClientProtocolException,Failed to send message." + paramString, localClientProtocolException);
      return null;
      localHttpResponse = new DefaultHttpClient(getHttpParams()).execute(localHttpGet);
      if (localHttpResponse.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break label544;
        Object localObject = localHttpEntity.getContent();
        Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
        if ((localHeader != null) && (localHeader.getValue().equalsIgnoreCase("gzip")))
          Log.i(TAG, i + "  Use GZIPInputStream get data....");
        String str2;
        for (localObject = new GZIPInputStream((InputStream)localObject); ; localObject = new InflaterInputStream((InputStream)localObject))
        {
          do
          {
            str2 = convertStreamToString((InputStream)localObject);
            Log.i(TAG, i + ":\tresponse: " + str1 + str2);
            if (str2 != null)
              break;
            return null;
          }
          while ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("deflate")));
          Log.i(TAG, i + "  Use InflaterInputStream get data....");
        }
        JSONObject localJSONObject = new JSONObject(str2);
        return localJSONObject;
      }
    }
    catch (Exception localException)
    {
      Log.d(TAG, i + ":\tIOException,Failed to send message." + paramString, localException);
      return null;
    }
    Log.d(TAG, i + ":\tFailed to send message. StatusCode = " + localHttpResponse.getStatusLine().getStatusCode() + Helper.LINE_SEPARATOR + paramString);
    label544: return null;
  }

  private JSONObject HttpRequestPost(String paramString, JSONObject paramJSONObject)
  {
    String str1 = paramJSONObject.toString();
    int i = new Random().nextInt(1000);
    Log.d(TAG, i + ":\trequest: " + paramString + Helper.LINE_SEPARATOR + str1);
    HttpPost localHttpPost = new HttpPost(paramString);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(getHttpParams());
    try
    {
      if (shouldCompressData())
      {
        byte[] arrayOfByte = DeflaterHelper.deflaterCompress("content=" + str1, Charset.defaultCharset().toString());
        localHttpPost.addHeader("Content-Encoding", "deflate");
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
        localHttpPost.setEntity(new InputStreamEntity(localByteArrayInputStream, arrayOfByte.length));
      }
      while (true)
      {
        localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
        if (localHttpResponse.getStatusLine().getStatusCode() != 200)
          break label448;
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break label446;
        Object localObject = localHttpEntity.getContent();
        Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
        if ((localHeader != null) && (localHeader.getValue().equalsIgnoreCase("deflate")))
          localObject = new InflaterInputStream((InputStream)localObject);
        str2 = convertStreamToString((InputStream)localObject);
        Log.i(TAG, i + ":\tresponse: " + Helper.LINE_SEPARATOR + str2);
        if (str2 != null)
          break;
        return null;
        ArrayList localArrayList = new ArrayList(1);
        BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair("content", str1);
        localArrayList.add(localBasicNameValuePair);
        UrlEncodedFormEntity localUrlEncodedFormEntity = new UrlEncodedFormEntity(localArrayList, "UTF-8");
        localHttpPost.setEntity(localUrlEncodedFormEntity);
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      String str2;
      Log.d(TAG, i + ":\tClientProtocolException,Failed to send message." + paramString, localClientProtocolException);
      return null;
      JSONObject localJSONObject = new JSONObject(str2);
      return localJSONObject;
    }
    catch (IOException localIOException)
    {
      HttpResponse localHttpResponse;
      Log.d(TAG, i + ":\tIOException,Failed to send message." + paramString, localIOException);
      return null;
      return null;
      Log.d(TAG, i + ":\tFailed to send message. StatusCode = " + localHttpResponse.getStatusLine().getStatusCode() + Helper.LINE_SEPARATOR + paramString);
      return null;
    }
    catch (JSONException localJSONException)
    {
      label446: label448: Log.d(TAG, i + ":\tIOException,Failed to send message." + paramString, localJSONException);
    }
    return null;
  }

  private static String convertStreamToString(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream), 8192);
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuilder.append(str + "\n");
      }
    }
    catch (IOException localIOException2)
    {
      Log.e(TAG, "Caught IOException in convertStreamToString()", localIOException2);
      try
      {
        paramInputStream.close();
        return null;
        try
        {
          paramInputStream.close();
          return localStringBuilder.toString();
        }
        catch (IOException localIOException4)
        {
          Log.e(TAG, "Caught IOException in convertStreamToString()", localIOException4);
          return null;
        }
      }
      catch (IOException localIOException3)
      {
        Log.e(TAG, "Caught IOException in convertStreamToString()", localIOException3);
        return null;
      }
    }
    finally
    {
      try
      {
        paramInputStream.close();
        throw localObject;
      }
      catch (IOException localIOException1)
      {
        Log.e(TAG, "Caught IOException in convertStreamToString()", localIOException1);
      }
    }
    return null;
  }

  private HttpParams getHttpParams()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
    HttpProtocolParams.setUserAgent(localBasicHttpParams, System.getProperty("http.agent"));
    return localBasicHttpParams;
  }

  private void verifyMethod(String paramString)
  {
    if ((TextUtils.isEmpty(paramString)) || (!(URequest.GET.equals(paramString.trim()) ^ URequest.POST.equals(paramString.trim()))))
      throw new RuntimeException("验证请求方式失败[" + paramString + "]");
  }

  public <T extends UResponse> T execute(URequest paramURequest, Class<T> paramClass)
  {
    String str = paramURequest.getHttpMethod().trim();
    verifyMethod(str);
    JSONObject localJSONObject;
    if (URequest.GET.equals(str))
      localJSONObject = HttpRequestGet(paramURequest.toGetUrl());
    while (localJSONObject == null)
    {
      return null;
      boolean bool = URequest.POST.equals(str);
      localJSONObject = null;
      if (bool)
        localJSONObject = HttpRequestPost(paramURequest.baseUrl, paramURequest.toJson());
    }
    try
    {
      UResponse localUResponse = (UResponse)paramClass.getConstructor(new Class[] { JSONObject.class }).newInstance(new Object[] { localJSONObject });
      return localUResponse;
    }
    catch (SecurityException localSecurityException)
    {
      Log.e(TAG, "SecurityException", localSecurityException);
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        Log.e(TAG, "NoSuchMethodException", localNoSuchMethodException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        Log.e(TAG, "IllegalArgumentException", localIllegalArgumentException);
    }
    catch (InstantiationException localInstantiationException)
    {
      while (true)
        Log.e(TAG, "InstantiationException", localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        Log.e(TAG, "IllegalAccessException", localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        Log.e(TAG, "InvocationTargetException", localInvocationTargetException);
    }
  }

  public UClient setHeader(Map<String, String> paramMap)
  {
    this.headers = paramMap;
    return this;
  }

  public boolean shouldCompressData()
  {
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.net.UClient
 * JD-Core Version:    0.6.2
 */