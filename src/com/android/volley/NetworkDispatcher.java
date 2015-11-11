package com.android.volley;

import android.annotation.SuppressLint;
import java.util.concurrent.BlockingQueue;

@SuppressLint({"NewApi"})
public class NetworkDispatcher extends Thread
{
  private final Cache mCache;
  private final ResponseDelivery mDelivery;
  private final Network mNetwork;
  private final BlockingQueue<Request> mQueue;
  private volatile boolean mQuit = false;

  public NetworkDispatcher(BlockingQueue<Request> paramBlockingQueue, Network paramNetwork, Cache paramCache, ResponseDelivery paramResponseDelivery)
  {
    this.mQueue = paramBlockingQueue;
    this.mNetwork = paramNetwork;
    this.mCache = paramCache;
    this.mDelivery = paramResponseDelivery;
  }

  private void parseAndDeliverNetworkError(Request<?> paramRequest, VolleyError paramVolleyError)
  {
    VolleyError localVolleyError = paramRequest.parseNetworkError(paramVolleyError);
    this.mDelivery.postError(paramRequest, localVolleyError);
  }

  public void quit()
  {
    this.mQuit = true;
    interrupt();
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: bipush 10
    //   2: invokestatic 63	android/os/Process:setThreadPriority	(I)V
    //   5: aload_0
    //   6: getfield 27	com/android/volley/NetworkDispatcher:mQueue	Ljava/util/concurrent/BlockingQueue;
    //   9: invokeinterface 69 1 0
    //   14: checkcast 37	com/android/volley/Request
    //   17: astore_3
    //   18: aload_3
    //   19: ldc 71
    //   21: invokevirtual 75	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   24: aload_3
    //   25: invokevirtual 79	com/android/volley/Request:isCanceled	()Z
    //   28: ifeq +46 -> 74
    //   31: aload_3
    //   32: ldc 81
    //   34: invokevirtual 84	com/android/volley/Request:finish	(Ljava/lang/String;)V
    //   37: goto -32 -> 5
    //   40: astore 6
    //   42: aload_0
    //   43: aload_3
    //   44: aload 6
    //   46: invokespecial 86	com/android/volley/NetworkDispatcher:parseAndDeliverNetworkError	(Lcom/android/volley/Request;Lcom/android/volley/VolleyError;)V
    //   49: goto -44 -> 5
    //   52: astore_1
    //   53: aload_0
    //   54: getfield 25	com/android/volley/NetworkDispatcher:mQuit	Z
    //   57: ifeq +4 -> 61
    //   60: return
    //   61: ldc2_w 87
    //   64: invokestatic 92	java/lang/Thread:sleep	(J)V
    //   67: goto -62 -> 5
    //   70: astore_2
    //   71: goto -66 -> 5
    //   74: getstatic 98	android/os/Build$VERSION:SDK_INT	I
    //   77: bipush 14
    //   79: if_icmplt +10 -> 89
    //   82: aload_3
    //   83: invokevirtual 102	com/android/volley/Request:getTrafficStatsTag	()I
    //   86: invokestatic 107	android/net/TrafficStats:setThreadStatsTag	(I)V
    //   89: aload_0
    //   90: getfield 29	com/android/volley/NetworkDispatcher:mNetwork	Lcom/android/volley/Network;
    //   93: aload_3
    //   94: invokeinterface 113 2 0
    //   99: astore 7
    //   101: aload_3
    //   102: ldc 115
    //   104: invokevirtual 75	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   107: aload 7
    //   109: getfield 120	com/android/volley/NetworkResponse:notModified	Z
    //   112: ifeq +67 -> 179
    //   115: aload_3
    //   116: invokevirtual 123	com/android/volley/Request:hasHadResponseDelivered	()Z
    //   119: ifeq +60 -> 179
    //   122: aload_3
    //   123: ldc 125
    //   125: invokevirtual 84	com/android/volley/Request:finish	(Ljava/lang/String;)V
    //   128: goto -123 -> 5
    //   131: astore 4
    //   133: iconst_1
    //   134: anewarray 127	java/lang/Object
    //   137: astore 5
    //   139: aload 5
    //   141: iconst_0
    //   142: aload 4
    //   144: invokevirtual 131	java/lang/Exception:toString	()Ljava/lang/String;
    //   147: aastore
    //   148: aload 4
    //   150: ldc 133
    //   152: aload 5
    //   154: invokestatic 139	com/android/volley/VolleyLog:w	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   157: aload_0
    //   158: getfield 33	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   161: aload_3
    //   162: new 55	com/android/volley/VolleyError
    //   165: dup
    //   166: aload 4
    //   168: invokespecial 142	com/android/volley/VolleyError:<init>	(Ljava/lang/Throwable;)V
    //   171: invokeinterface 46 3 0
    //   176: goto -171 -> 5
    //   179: aload_3
    //   180: aload 7
    //   182: invokevirtual 146	com/android/volley/Request:parseNetworkResponse	(Lcom/android/volley/NetworkResponse;)Lcom/android/volley/Response;
    //   185: astore 8
    //   187: aload_3
    //   188: ldc 148
    //   190: invokevirtual 75	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   193: aload_3
    //   194: invokevirtual 151	com/android/volley/Request:shouldCache	()Z
    //   197: ifeq +35 -> 232
    //   200: aload 8
    //   202: getfield 157	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
    //   205: ifnull +27 -> 232
    //   208: aload_0
    //   209: getfield 31	com/android/volley/NetworkDispatcher:mCache	Lcom/android/volley/Cache;
    //   212: aload_3
    //   213: invokevirtual 160	com/android/volley/Request:getCacheKey	()Ljava/lang/String;
    //   216: aload 8
    //   218: getfield 157	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
    //   221: invokeinterface 166 3 0
    //   226: aload_3
    //   227: ldc 168
    //   229: invokevirtual 75	com/android/volley/Request:addMarker	(Ljava/lang/String;)V
    //   232: aload_3
    //   233: invokevirtual 171	com/android/volley/Request:markDelivered	()V
    //   236: aload_0
    //   237: getfield 33	com/android/volley/NetworkDispatcher:mDelivery	Lcom/android/volley/ResponseDelivery;
    //   240: aload_3
    //   241: aload 8
    //   243: invokeinterface 175 3 0
    //   248: goto -243 -> 5
    //
    // Exception table:
    //   from	to	target	type
    //   18	37	40	com/android/volley/VolleyError
    //   74	89	40	com/android/volley/VolleyError
    //   89	128	40	com/android/volley/VolleyError
    //   179	232	40	com/android/volley/VolleyError
    //   232	248	40	com/android/volley/VolleyError
    //   5	18	52	java/lang/InterruptedException
    //   61	67	70	java/lang/Exception
    //   18	37	131	java/lang/Exception
    //   74	89	131	java/lang/Exception
    //   89	128	131	java/lang/Exception
    //   179	232	131	java/lang/Exception
    //   232	248	131	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.NetworkDispatcher
 * JD-Core Version:    0.6.2
 */