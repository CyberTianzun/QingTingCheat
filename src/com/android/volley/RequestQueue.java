package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue
{
  private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
  private final Cache mCache;
  private CacheDispatcher mCacheDispatcher;
  private final PriorityBlockingQueue<Request> mCacheQueue = new PriorityBlockingQueue();
  private final Set<Request> mCurrentRequests = new HashSet();
  private final ResponseDelivery mDelivery;
  private NetworkDispatcher[] mDispatchers;
  private final Network mNetwork;
  private final PriorityBlockingQueue<Request> mNetworkQueue = new PriorityBlockingQueue();
  private AtomicInteger mSequenceGenerator = new AtomicInteger();
  private final Map<String, Queue<Request>> mWaitingRequests = new HashMap();

  public RequestQueue(Cache paramCache, Network paramNetwork)
  {
    this(paramCache, paramNetwork, 4);
  }

  public RequestQueue(Cache paramCache, Network paramNetwork, int paramInt)
  {
    this(paramCache, paramNetwork, paramInt, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
  }

  public RequestQueue(Cache paramCache, Network paramNetwork, int paramInt, ResponseDelivery paramResponseDelivery)
  {
    this.mCache = paramCache;
    this.mNetwork = paramNetwork;
    this.mDispatchers = new NetworkDispatcher[paramInt];
    this.mDelivery = paramResponseDelivery;
  }

  public Request add(Request paramRequest)
  {
    paramRequest.setRequestQueue(this);
    synchronized (this.mCurrentRequests)
    {
      this.mCurrentRequests.add(paramRequest);
      paramRequest.setSequence(getSequenceNumber());
      paramRequest.addMarker("add-to-queue");
      if (!paramRequest.shouldCache())
      {
        this.mNetworkQueue.add(paramRequest);
        return paramRequest;
      }
    }
    while (true)
    {
      String str;
      synchronized (this.mWaitingRequests)
      {
        str = paramRequest.getCacheKey();
        if (this.mWaitingRequests.containsKey(str))
        {
          Object localObject3 = (Queue)this.mWaitingRequests.get(str);
          if (localObject3 == null)
            localObject3 = new LinkedList();
          ((Queue)localObject3).add(paramRequest);
          this.mWaitingRequests.put(str, localObject3);
          if (VolleyLog.DEBUG)
            VolleyLog.v("Request for cacheKey=%s is in flight, putting on hold.", new Object[] { str });
          return paramRequest;
        }
      }
      this.mWaitingRequests.put(str, null);
      this.mCacheQueue.add(paramRequest);
    }
  }

  public void cancelAll(RequestFilter paramRequestFilter)
  {
    synchronized (this.mCurrentRequests)
    {
      Iterator localIterator = this.mCurrentRequests.iterator();
      Request localRequest;
      do
      {
        if (!localIterator.hasNext())
          return;
        localRequest = (Request)localIterator.next();
      }
      while (!paramRequestFilter.apply(localRequest));
      localRequest.cancel();
    }
  }

  public void cancelAll(final Object paramObject)
  {
    if (paramObject == null)
      throw new IllegalArgumentException("Cannot cancelAll with a null tag");
    cancelAll(new RequestFilter()
    {
      public boolean apply(Request<?> paramAnonymousRequest)
      {
        return paramAnonymousRequest.getTag() == paramObject;
      }
    });
  }

  void finish(Request paramRequest)
  {
    synchronized (this.mCurrentRequests)
    {
      this.mCurrentRequests.remove(paramRequest);
      if (!paramRequest.shouldCache());
    }
    synchronized (this.mWaitingRequests)
    {
      String str = paramRequest.getCacheKey();
      Queue localQueue = (Queue)this.mWaitingRequests.remove(str);
      if (localQueue != null)
      {
        if (VolleyLog.DEBUG)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(localQueue.size());
          arrayOfObject[1] = str;
          VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", arrayOfObject);
        }
        this.mCacheQueue.addAll(localQueue);
      }
      return;
      localObject1 = finally;
      throw localObject1;
    }
  }

  public Cache getCache()
  {
    return this.mCache;
  }

  public int getSequenceNumber()
  {
    return this.mSequenceGenerator.incrementAndGet();
  }

  public void start()
  {
    stop();
    this.mCacheDispatcher = new CacheDispatcher(this.mCacheQueue, this.mNetworkQueue, this.mCache, this.mDelivery);
    this.mCacheDispatcher.start();
    for (int i = 0; ; i++)
    {
      if (i >= this.mDispatchers.length)
        return;
      NetworkDispatcher localNetworkDispatcher = new NetworkDispatcher(this.mNetworkQueue, this.mNetwork, this.mCache, this.mDelivery);
      this.mDispatchers[i] = localNetworkDispatcher;
      localNetworkDispatcher.start();
    }
  }

  public void stop()
  {
    if (this.mCacheDispatcher != null)
      this.mCacheDispatcher.quit();
    for (int i = 0; ; i++)
    {
      if (i >= this.mDispatchers.length)
        return;
      if (this.mDispatchers[i] != null)
        this.mDispatchers[i].quit();
    }
  }

  public static abstract interface RequestFilter
  {
    public abstract boolean apply(Request<?> paramRequest);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.RequestQueue
 * JD-Core Version:    0.6.2
 */