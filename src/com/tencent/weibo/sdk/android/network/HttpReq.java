package com.tencent.weibo.sdk.android.network;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public abstract class HttpReq extends AsyncTask<Void, Integer, Object>
{
  private final String GET = "GET";
  private final String POST = "POST";
  protected HttpCallback mCallBack = null;
  protected String mHost = null;
  protected String mMethod = null;
  protected ReqParam mParam = new ReqParam();
  protected int mPort = 8088;
  private int mServiceTag = -1;
  protected String mUrl = null;

  public static String decode(String paramString)
  {
    if (paramString == null)
      return "";
    try
    {
      String str = URLDecoder.decode(paramString, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
    }
  }

  private InputStream picMethod()
  {
    HttpClient localHttpClient = new HttpClient();
    PostMethod localPostMethod = new PostMethod(this.mUrl);
    String str1 = this.mParam.toString();
    try
    {
      localArrayList = new ArrayList();
      if ((str1 != null) && (!str1.equals("")))
      {
        arrayOfString1 = str1.split("&");
        int j = arrayOfString1.length;
        k = 0;
        localObject1 = null;
        if (k < j)
          break label205;
      }
    }
    catch (IOException localIOException2)
    {
      try
      {
        arrayOfChar = ((String)this.mParam.getmParams().get("pic")).toCharArray();
        arrayOfByte = new byte[arrayOfChar.length];
        m = 0;
        if (m >= arrayOfChar.length)
        {
          ByteArrayPartSource localByteArrayPartSource = new ByteArrayPartSource("123456.jpg", arrayOfByte);
          localFilePart = new FilePart("pic", localByteArrayPartSource, "image/jpeg", "utf-8");
        }
      }
      catch (IOException localIOException2)
      {
        try
        {
          while (true)
          {
            ArrayList localArrayList;
            String[] arrayOfString1;
            int k;
            char[] arrayOfChar;
            byte[] arrayOfByte;
            int m;
            FilePart localFilePart;
            localArrayList.add(localFilePart);
            localPostMethod.setRequestEntity(new MultipartRequestEntity((Part[])localArrayList.toArray(new Part[localArrayList.size()]), localPostMethod.getParams()));
            int i = localHttpClient.executeMethod(localPostMethod);
            if (i == 200)
              break;
            return null;
            label205: String str2 = arrayOfString1[k];
            if ((str2 == null) || (str2.equals("")) || (str2.indexOf("=") <= -1))
              break label368;
            String[] arrayOfString2 = str2.split("=");
            if (arrayOfString2.length == 2);
            for (String str3 = decode(arrayOfString2[1]); ; str3 = "")
            {
              String str4 = arrayOfString2[0];
              localObject2 = new StringPart(str4, str3, "utf-8");
              localArrayList.add(localObject2);
              k++;
              localObject1 = localObject2;
              break;
            }
            arrayOfByte[m] = ((byte)arrayOfChar[m]);
            m++;
          }
          InputStream localInputStream2 = localPostMethod.getResponseBodyAsStream();
          InputStream localInputStream1 = localInputStream2;
          return localInputStream1;
          for (localIOException1 = localIOException1; ; localIOException2 = localIOException2)
          {
            localIOException1.printStackTrace();
            localInputStream1 = null;
            break;
          }
        }
        catch (IOException localIOException3)
        {
          while (true)
          {
            Object localObject1;
            continue;
            label368: Object localObject2 = localObject1;
          }
        }
      }
    }
  }

  private static InputStream readHttpResponse(HttpResponse paramHttpResponse)
  {
    HttpEntity localHttpEntity = paramHttpResponse.getEntity();
    Object localObject = null;
    try
    {
      localObject = localHttpEntity.getContent();
      Header localHeader = paramHttpResponse.getFirstHeader("Content-Encoding");
      if ((localHeader != null) && (localHeader.getValue().toLowerCase().indexOf("gzip") > -1))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream((InputStream)localObject);
        localObject = localGZIPInputStream;
      }
      return localObject;
    }
    catch (IOException localIOException)
    {
      return localObject;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      label67: break label67;
    }
  }

  public void addParam(String paramString, Object paramObject)
  {
    this.mParam.addParam(paramString, paramObject);
  }

  public void addParam(String paramString1, String paramString2)
  {
    this.mParam.addParam(paramString1, paramString2);
  }

  protected Object doInBackground(Void[] paramArrayOfVoid)
  {
    try
    {
      Object localObject = runReq();
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  protected HttpCallback getCallBack()
  {
    return this.mCallBack;
  }

  public int getServiceTag()
  {
    return this.mServiceTag;
  }

  protected void onCancelled()
  {
    if (this.mCallBack != null)
      this.mCallBack.onResult(null);
    HttpService.getInstance().onReqFinish(this);
  }

  protected void onPostExecute(Object paramObject)
  {
    if (this.mCallBack != null)
      this.mCallBack.onResult(paramObject);
    HttpService.getInstance().onReqFinish(this);
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
  }

  protected abstract Object processResponse(InputStream paramInputStream)
    throws Exception;

  public Object runReq()
    throws Exception
  {
    HttpClient localHttpClient = new HttpClient();
    if (this.mMethod.equals("GET"))
      this.mUrl = (this.mUrl + "?" + this.mParam.toString().substring(0, -1 + this.mParam.toString().length()));
    for (Object localObject = new GetMethod(this.mUrl); ; localObject = new UTF8PostMethod(this.mUrl))
    {
      localHttpClient.getHostConfiguration().setHost(this.mHost, this.mPort, "http");
      ((HttpMethod)localObject).setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      setReq((HttpMethod)localObject);
      if (localHttpClient.executeMethod((HttpMethod)localObject) == 200)
        break label197;
      return null;
      if (!this.mMethod.equals("POST"))
        break;
      if (this.mParam.getmParams().get("pic") != null)
        return processResponse(picMethod());
    }
    throw new Exception("unrecognized http method");
    label197: return processResponse(((HttpMethod)localObject).getResponseBodyAsStream());
  }

  public void setParam(ReqParam paramReqParam)
  {
    this.mParam = paramReqParam;
  }

  protected abstract void setReq(HttpMethod paramHttpMethod)
    throws Exception;

  public void setServiceTag(int paramInt)
  {
    this.mServiceTag = paramInt;
  }

  public static class UTF8PostMethod extends PostMethod
  {
    public UTF8PostMethod(String paramString)
    {
      super();
    }

    public String getRequestCharSet()
    {
      return "UTF-8";
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.network.HttpReq
 * JD-Core Version:    0.6.2
 */