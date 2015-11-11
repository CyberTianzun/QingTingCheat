package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class Request<T>
  implements Comparable<Request<T>>
{
  private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
  private static final long SLOW_REQUEST_THRESHOLD_MS = 3000L;
  private Cache.Entry mCacheEntry;
  private boolean mCanceled;
  private final int mDefaultTrafficStatsTag;
  private final Response.ErrorListener mErrorListener;
  private final VolleyLog.MarkerLog mEventLog;
  private final int mMethod;
  private long mRequestBirthTime;
  private RequestQueue mRequestQueue;
  private boolean mResponseDelivered;
  private RetryPolicy mRetryPolicy;
  private Integer mSequence;
  private boolean mShouldCache;
  private Object mTag;
  private final String mUrl;

  public Request(int paramInt, String paramString, Response.ErrorListener paramErrorListener)
  {
    if (VolleyLog.MarkerLog.ENABLED);
    for (VolleyLog.MarkerLog localMarkerLog = new VolleyLog.MarkerLog(); ; localMarkerLog = null)
    {
      this.mEventLog = localMarkerLog;
      this.mShouldCache = true;
      this.mCanceled = false;
      this.mResponseDelivered = false;
      this.mRequestBirthTime = 0L;
      this.mCacheEntry = null;
      this.mMethod = paramInt;
      this.mUrl = paramString;
      this.mErrorListener = paramErrorListener;
      setRetryPolicy(new DefaultRetryPolicy());
      if (!TextUtils.isEmpty(paramString))
        break;
      this.mDefaultTrafficStatsTag = 0;
      return;
    }
    Uri localUri = Uri.parse(paramString);
    if (localUri == null)
    {
      this.mDefaultTrafficStatsTag = 0;
      return;
    }
    String str = localUri.getHost();
    if (str != null);
    for (int i = str.hashCode(); ; i = 0)
    {
      this.mDefaultTrafficStatsTag = i;
      return;
    }
  }

  public Request(String paramString, Response.ErrorListener paramErrorListener)
  {
    this(-1, paramString, paramErrorListener);
  }

  private byte[] encodeParameters(Map<String, String> paramMap, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return localStringBuilder.toString().getBytes(paramString);
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localStringBuilder.append(URLEncoder.encode((String)localEntry.getKey(), paramString));
        localStringBuilder.append('=');
        localStringBuilder.append(URLEncoder.encode((String)localEntry.getValue(), paramString));
        localStringBuilder.append('&');
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("Encoding not supported: " + paramString, localUnsupportedEncodingException);
    }
  }

  public void addMarker(String paramString)
  {
    if (VolleyLog.MarkerLog.ENABLED)
      this.mEventLog.add(paramString, Thread.currentThread().getId());
    while (this.mRequestBirthTime != 0L)
      return;
    this.mRequestBirthTime = SystemClock.elapsedRealtime();
  }

  public void cancel()
  {
    this.mCanceled = true;
  }

  public int compareTo(Request<T> paramRequest)
  {
    Priority localPriority1 = getPriority();
    Priority localPriority2 = paramRequest.getPriority();
    if (localPriority1 == localPriority2)
      return this.mSequence.intValue() - paramRequest.mSequence.intValue();
    return localPriority2.ordinal() - localPriority1.ordinal();
  }

  public void deliverError(VolleyError paramVolleyError)
  {
    if (this.mErrorListener != null)
      this.mErrorListener.onErrorResponse(paramVolleyError);
  }

  protected abstract void deliverResponse(T paramT);

  void finish(final String paramString)
  {
    if (this.mRequestQueue != null)
      this.mRequestQueue.finish(this);
    final long l2;
    if (VolleyLog.MarkerLog.ENABLED)
    {
      l2 = Thread.currentThread().getId();
      if (Looper.myLooper() != Looper.getMainLooper())
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public void run()
          {
            Request.this.mEventLog.add(paramString, l2);
            Request.this.mEventLog.finish(toString());
          }
        });
    }
    long l1;
    do
    {
      return;
      this.mEventLog.add(paramString, l2);
      this.mEventLog.finish(toString());
      return;
      l1 = SystemClock.elapsedRealtime() - this.mRequestBirthTime;
    }
    while (l1 < 3000L);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Long.valueOf(l1);
    arrayOfObject[1] = toString();
    VolleyLog.d("%d ms: %s", arrayOfObject);
  }

  public byte[] getBody()
    throws AuthFailureError
  {
    Map localMap = getParams();
    if ((localMap != null) && (localMap.size() > 0))
      return encodeParameters(localMap, getParamsEncoding());
    return null;
  }

  public String getBodyContentType()
  {
    return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
  }

  public Cache.Entry getCacheEntry()
  {
    return this.mCacheEntry;
  }

  public String getCacheKey()
  {
    return getUrl();
  }

  public Map<String, String> getHeaders()
    throws AuthFailureError
  {
    return Collections.emptyMap();
  }

  public int getMethod()
  {
    return this.mMethod;
  }

  protected Map<String, String> getParams()
    throws AuthFailureError
  {
    return null;
  }

  protected String getParamsEncoding()
  {
    return "UTF-8";
  }

  public byte[] getPostBody()
    throws AuthFailureError
  {
    Map localMap = getPostParams();
    if ((localMap != null) && (localMap.size() > 0))
      return encodeParameters(localMap, getPostParamsEncoding());
    return null;
  }

  public String getPostBodyContentType()
  {
    return getBodyContentType();
  }

  protected Map<String, String> getPostParams()
    throws AuthFailureError
  {
    return getParams();
  }

  protected String getPostParamsEncoding()
  {
    return getParamsEncoding();
  }

  public Priority getPriority()
  {
    return Priority.NORMAL;
  }

  public RetryPolicy getRetryPolicy()
  {
    return this.mRetryPolicy;
  }

  public final int getSequence()
  {
    if (this.mSequence == null)
      throw new IllegalStateException("getSequence called before setSequence");
    return this.mSequence.intValue();
  }

  public Object getTag()
  {
    return this.mTag;
  }

  public final int getTimeoutMs()
  {
    return this.mRetryPolicy.getCurrentTimeout();
  }

  public int getTrafficStatsTag()
  {
    return this.mDefaultTrafficStatsTag;
  }

  public String getUrl()
  {
    return this.mUrl;
  }

  public boolean hasHadResponseDelivered()
  {
    return this.mResponseDelivered;
  }

  public boolean isCanceled()
  {
    return this.mCanceled;
  }

  public void markDelivered()
  {
    this.mResponseDelivered = true;
  }

  protected VolleyError parseNetworkError(VolleyError paramVolleyError)
  {
    return paramVolleyError;
  }

  protected abstract Response<T> parseNetworkResponse(NetworkResponse paramNetworkResponse);

  public void setCacheEntry(Cache.Entry paramEntry)
  {
    this.mCacheEntry = paramEntry;
  }

  public void setRequestQueue(RequestQueue paramRequestQueue)
  {
    this.mRequestQueue = paramRequestQueue;
  }

  public void setRetryPolicy(RetryPolicy paramRetryPolicy)
  {
    this.mRetryPolicy = paramRetryPolicy;
  }

  public final void setSequence(int paramInt)
  {
    this.mSequence = Integer.valueOf(paramInt);
  }

  public final void setShouldCache(boolean paramBoolean)
  {
    this.mShouldCache = paramBoolean;
  }

  public void setTag(Object paramObject)
  {
    this.mTag = paramObject;
  }

  public final boolean shouldCache()
  {
    return this.mShouldCache;
  }

  public String toString()
  {
    String str1 = "0x" + Integer.toHexString(getTrafficStatsTag());
    if (this.mCanceled);
    for (String str2 = "[X] "; ; str2 = "[ ] ")
      return str2 + getUrl() + " " + str1 + " " + getPriority() + " " + this.mSequence;
  }

  public static abstract interface Method
  {
    public static final int DELETE = 3;
    public static final int DEPRECATED_GET_OR_POST = -1;
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
  }

  public static enum Priority
  {
    static
    {
      HIGH = new Priority("HIGH", 2);
      IMMEDIATE = new Priority("IMMEDIATE", 3);
      Priority[] arrayOfPriority = new Priority[4];
      arrayOfPriority[0] = LOW;
      arrayOfPriority[1] = NORMAL;
      arrayOfPriority[2] = HIGH;
      arrayOfPriority[3] = IMMEDIATE;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.Request
 * JD-Core Version:    0.6.2
 */