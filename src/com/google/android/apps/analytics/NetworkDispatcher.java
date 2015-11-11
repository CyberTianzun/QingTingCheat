package com.google.android.apps.analytics;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

class NetworkDispatcher
  implements Dispatcher
{
  private static final String GOOGLE_ANALYTICS_HOST_NAME = "www.google-analytics.com";
  private static final int GOOGLE_ANALYTICS_HOST_PORT = 80;
  private static final int MAX_EVENTS_PER_PIPELINE = 30;
  private static final int MAX_GET_LENGTH = 2036;
  private static final int MAX_POST_LENGTH = 8192;
  private static final int MAX_SEQUENTIAL_REQUESTS = 5;
  private static final long MIN_RETRY_INTERVAL = 2L;
  private static final String USER_AGENT_TEMPLATE = "%s/%s (Linux; U; Android %s; %s-%s; %s Build/%s)";
  private DispatcherThread dispatcherThread;
  private boolean dryRun = false;
  private final HttpHost googleAnalyticsHost;
  private final String userAgent;

  public NetworkDispatcher()
  {
    this("GoogleAnalytics", "1.4.2");
  }

  public NetworkDispatcher(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, "www.google-analytics.com", 80);
  }

  NetworkDispatcher(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.googleAnalyticsHost = new HttpHost(paramString3, paramInt);
    Locale localLocale = Locale.getDefault();
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = paramString2;
    arrayOfObject[2] = Build.VERSION.RELEASE;
    String str1;
    if (localLocale.getLanguage() != null)
    {
      str1 = localLocale.getLanguage().toLowerCase();
      arrayOfObject[3] = str1;
      if (localLocale.getCountry() == null)
        break label134;
    }
    label134: for (String str2 = localLocale.getCountry().toLowerCase(); ; str2 = "")
    {
      arrayOfObject[4] = str2;
      arrayOfObject[5] = Build.MODEL;
      arrayOfObject[6] = Build.ID;
      this.userAgent = String.format("%s/%s (Linux; U; Android %s; %s-%s; %s Build/%s)", arrayOfObject);
      return;
      str1 = "en";
      break;
    }
  }

  public void dispatchHits(Hit[] paramArrayOfHit)
  {
    if (this.dispatcherThread == null)
      return;
    this.dispatcherThread.dispatchHits(paramArrayOfHit);
  }

  String getUserAgent()
  {
    return this.userAgent;
  }

  public void init(Dispatcher.Callbacks paramCallbacks)
  {
    stop();
    this.dispatcherThread = new DispatcherThread(paramCallbacks, this.userAgent, this, null);
    this.dispatcherThread.start();
  }

  public void init(Dispatcher.Callbacks paramCallbacks, PipelinedRequester paramPipelinedRequester, HitStore paramHitStore)
  {
    stop();
    this.dispatcherThread = new DispatcherThread(paramCallbacks, paramPipelinedRequester, this.userAgent, this, null);
    this.dispatcherThread.start();
  }

  public boolean isDryRun()
  {
    return this.dryRun;
  }

  public void setDryRun(boolean paramBoolean)
  {
    this.dryRun = paramBoolean;
  }

  public void stop()
  {
    if ((this.dispatcherThread != null) && (this.dispatcherThread.getLooper() != null))
    {
      this.dispatcherThread.getLooper().quit();
      this.dispatcherThread = null;
    }
  }

  void waitForThreadLooper()
  {
    this.dispatcherThread.getLooper();
    while (this.dispatcherThread.handlerExecuteOnDispatcherThread == null)
      Thread.yield();
  }

  private static class DispatcherThread extends HandlerThread
  {
    private final Dispatcher.Callbacks callbacks;
    private AsyncDispatchTask currentTask = null;
    volatile Handler handlerExecuteOnDispatcherThread;
    private int lastStatusCode;
    private int maxEventsPerRequest = 30;
    private final NetworkDispatcher parent;
    private final PipelinedRequester pipelinedRequester;
    private final RequesterCallbacks requesterCallBacks;
    private long retryInterval;
    private final String userAgent;

    private DispatcherThread(Dispatcher.Callbacks paramCallbacks, PipelinedRequester paramPipelinedRequester, String paramString, NetworkDispatcher paramNetworkDispatcher)
    {
      super();
      this.callbacks = paramCallbacks;
      this.userAgent = paramString;
      this.pipelinedRequester = paramPipelinedRequester;
      this.requesterCallBacks = new RequesterCallbacks(null);
      this.pipelinedRequester.installCallbacks(this.requesterCallBacks);
      this.parent = paramNetworkDispatcher;
    }

    private DispatcherThread(Dispatcher.Callbacks paramCallbacks, String paramString, NetworkDispatcher paramNetworkDispatcher)
    {
      this(paramCallbacks, new PipelinedRequester(paramNetworkDispatcher.googleAnalyticsHost), paramString, paramNetworkDispatcher);
    }

    public void dispatchHits(Hit[] paramArrayOfHit)
    {
      if (this.handlerExecuteOnDispatcherThread == null)
        return;
      this.handlerExecuteOnDispatcherThread.post(new AsyncDispatchTask(paramArrayOfHit));
    }

    protected void onLooperPrepared()
    {
      this.handlerExecuteOnDispatcherThread = new Handler();
    }

    private class AsyncDispatchTask
      implements Runnable
    {
      private final LinkedList<Hit> hits = new LinkedList();

      public AsyncDispatchTask(Hit[] arg2)
      {
        Object[] arrayOfObject;
        Collections.addAll(this.hits, arrayOfObject);
      }

      private void dispatchSomePendingHits(boolean paramBoolean)
        throws IOException, ParseException, HttpException
      {
        if ((GoogleAnalyticsTracker.getInstance().getDebug()) && (paramBoolean))
          Log.v("GoogleAnalyticsTracker", "dispatching hits in dry run mode");
        int i = 0;
        if ((i < this.hits.size()) && (i < NetworkDispatcher.DispatcherThread.this.maxEventsPerRequest))
        {
          String str1 = Utils.addQueueTimeParameter(((Hit)this.hits.get(i)).hitString, System.currentTimeMillis());
          int j = str1.indexOf('?');
          Object localObject2;
          Object localObject1;
          if (j < 0)
          {
            localObject2 = "";
            localObject1 = str1;
            if (((String)localObject2).length() >= 2036)
              break label339;
          }
          StringBuffer localStringBuffer;
          label328: label339: BasicHttpEntityEnclosingRequest localBasicHttpEntityEnclosingRequest;
          for (Object localObject3 = new BasicHttpEntityEnclosingRequest("GET", str1); ; localObject3 = localBasicHttpEntityEnclosingRequest)
          {
            String str3 = NetworkDispatcher.this.googleAnalyticsHost.getHostName();
            if (NetworkDispatcher.this.googleAnalyticsHost.getPort() != 80)
              str3 = str3 + ":" + NetworkDispatcher.this.googleAnalyticsHost.getPort();
            ((HttpEntityEnclosingRequest)localObject3).addHeader("Host", str3);
            ((HttpEntityEnclosingRequest)localObject3).addHeader("User-Agent", NetworkDispatcher.DispatcherThread.this.userAgent);
            if (!GoogleAnalyticsTracker.getInstance().getDebug())
              break label453;
            localStringBuffer = new StringBuffer();
            Header[] arrayOfHeader = ((HttpEntityEnclosingRequest)localObject3).getAllHeaders();
            int k = arrayOfHeader.length;
            for (int m = 0; m < k; m++)
              localStringBuffer.append(arrayOfHeader[m].toString()).append("\n");
            if (j > 0);
            for (String str2 = str1.substring(0, j); ; str2 = "")
            {
              if (j >= -2 + str1.length())
                break label328;
              String str4 = str1.substring(j + 1);
              localObject1 = str2;
              localObject2 = str4;
              break;
            }
            localObject1 = str2;
            localObject2 = "";
            break;
            localBasicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("POST", "/p" + (String)localObject1);
            localBasicHttpEntityEnclosingRequest.addHeader("Content-Length", Integer.toString(((String)localObject2).length()));
            localBasicHttpEntityEnclosingRequest.addHeader("Content-Type", "text/plain");
            localBasicHttpEntityEnclosingRequest.setEntity(new StringEntity((String)localObject2));
          }
          localStringBuffer.append(((HttpEntityEnclosingRequest)localObject3).getRequestLine().toString()).append("\n");
          Log.i("GoogleAnalyticsTracker", localStringBuffer.toString());
          label453: if (((String)localObject2).length() > 8192)
          {
            Log.w("GoogleAnalyticsTracker", "Hit too long (> 8192 bytes)--not sent");
            NetworkDispatcher.DispatcherThread.this.requesterCallBacks.requestSent();
          }
          while (true)
          {
            i++;
            break;
            if (paramBoolean)
              NetworkDispatcher.DispatcherThread.this.requesterCallBacks.requestSent();
            else
              NetworkDispatcher.DispatcherThread.this.pipelinedRequester.addRequest((HttpEntityEnclosingRequest)localObject3);
          }
        }
        if (!paramBoolean)
          NetworkDispatcher.DispatcherThread.this.pipelinedRequester.sendRequests();
      }

      public Hit removeNextHit()
      {
        return (Hit)this.hits.poll();
      }

      public void run()
      {
        NetworkDispatcher.DispatcherThread.access$402(NetworkDispatcher.DispatcherThread.this, this);
        int i = 0;
        while (true)
        {
          long l;
          if ((i < 5) && (this.hits.size() > 0))
            l = 0L;
          try
          {
            if ((NetworkDispatcher.DispatcherThread.this.lastStatusCode == 500) || (NetworkDispatcher.DispatcherThread.this.lastStatusCode == 503))
            {
              l = ()(Math.random() * NetworkDispatcher.DispatcherThread.this.retryInterval);
              if (NetworkDispatcher.DispatcherThread.this.retryInterval < 256L)
                NetworkDispatcher.DispatcherThread.access$630(NetworkDispatcher.DispatcherThread.this, 2L);
            }
            while (true)
            {
              Thread.sleep(l * 1000L);
              dispatchSomePendingHits(NetworkDispatcher.this.isDryRun());
              i++;
              break;
              NetworkDispatcher.DispatcherThread.access$602(NetworkDispatcher.DispatcherThread.this, 2L);
            }
          }
          catch (InterruptedException localInterruptedException)
          {
            Log.w("GoogleAnalyticsTracker", "Couldn't sleep.", localInterruptedException);
            NetworkDispatcher.DispatcherThread.this.pipelinedRequester.finishedCurrentRequests();
            NetworkDispatcher.DispatcherThread.this.callbacks.dispatchFinished();
            NetworkDispatcher.DispatcherThread.access$402(NetworkDispatcher.DispatcherThread.this, null);
            return;
          }
          catch (IOException localIOException)
          {
            while (true)
              Log.w("GoogleAnalyticsTracker", "Problem with socket or streams.", localIOException);
          }
          catch (HttpException localHttpException)
          {
            while (true)
              Log.w("GoogleAnalyticsTracker", "Problem with http streams.", localHttpException);
          }
        }
      }
    }

    private class RequesterCallbacks
      implements PipelinedRequester.Callbacks
    {
      private RequesterCallbacks()
      {
      }

      public void pipelineModeChanged(boolean paramBoolean)
      {
        if (paramBoolean)
        {
          NetworkDispatcher.DispatcherThread.access$1002(NetworkDispatcher.DispatcherThread.this, 30);
          return;
        }
        NetworkDispatcher.DispatcherThread.access$1002(NetworkDispatcher.DispatcherThread.this, 1);
      }

      public void requestSent()
      {
        if (NetworkDispatcher.DispatcherThread.this.currentTask == null);
        Hit localHit;
        do
        {
          return;
          localHit = NetworkDispatcher.DispatcherThread.this.currentTask.removeNextHit();
        }
        while (localHit == null);
        NetworkDispatcher.DispatcherThread.this.callbacks.hitDispatched(localHit.hitId);
      }

      public void serverError(int paramInt)
      {
        NetworkDispatcher.DispatcherThread.access$502(NetworkDispatcher.DispatcherThread.this, paramInt);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.analytics.NetworkDispatcher
 * JD-Core Version:    0.6.2
 */