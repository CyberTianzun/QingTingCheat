package com.taobao.munion.base.volley;

import android.net.TrafficStats;
import android.os.Build.VERSION;
import java.util.concurrent.BlockingQueue;

public class g extends Thread
{
  private final BlockingQueue<l<?>> a;
  private final f b;
  private final b c;
  private final o d;
  private volatile boolean e = false;

  public g(BlockingQueue<l<?>> paramBlockingQueue, f paramf, b paramb, o paramo)
  {
    this.a = paramBlockingQueue;
    this.b = paramf;
    this.c = paramb;
    this.d = paramo;
  }

  private void a(l<?> paraml)
  {
    if (Build.VERSION.SDK_INT >= 14)
      TrafficStats.setThreadStatsTag(paraml.d());
  }

  private void a(l<?> paraml, s params)
  {
    s locals = paraml.a(params);
    this.d.a(paraml, locals);
  }

  public void a()
  {
    this.e = true;
    interrupt();
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: bipush 10
    //   2: invokestatic 71	android/os/Process:setThreadPriority	(I)V
    //   5: aload_0
    //   6: getfield 24	com/taobao/munion/base/volley/g:a	Ljava/util/concurrent/BlockingQueue;
    //   9: invokeinterface 77 1 0
    //   14: checkcast 39	com/taobao/munion/base/volley/l
    //   17: astore_2
    //   18: aload_2
    //   19: ldc 79
    //   21: invokevirtual 82	com/taobao/munion/base/volley/l:a	(Ljava/lang/String;)V
    //   24: aload_2
    //   25: invokevirtual 86	com/taobao/munion/base/volley/l:j	()Z
    //   28: ifeq +33 -> 61
    //   31: aload_2
    //   32: ldc 88
    //   34: invokevirtual 90	com/taobao/munion/base/volley/l:b	(Ljava/lang/String;)V
    //   37: goto -32 -> 5
    //   40: astore 5
    //   42: aload_0
    //   43: aload_2
    //   44: aload 5
    //   46: invokespecial 91	com/taobao/munion/base/volley/g:a	(Lcom/taobao/munion/base/volley/l;Lcom/taobao/munion/base/volley/s;)V
    //   49: goto -44 -> 5
    //   52: astore_1
    //   53: aload_0
    //   54: getfield 22	com/taobao/munion/base/volley/g:e	Z
    //   57: ifeq -52 -> 5
    //   60: return
    //   61: aload_0
    //   62: aload_2
    //   63: invokespecial 93	com/taobao/munion/base/volley/g:a	(Lcom/taobao/munion/base/volley/l;)V
    //   66: aload_0
    //   67: getfield 26	com/taobao/munion/base/volley/g:b	Lcom/taobao/munion/base/volley/f;
    //   70: aload_2
    //   71: invokeinterface 98 2 0
    //   76: astore 6
    //   78: aload_2
    //   79: ldc 100
    //   81: invokevirtual 82	com/taobao/munion/base/volley/l:a	(Ljava/lang/String;)V
    //   84: aload 6
    //   86: getfield 104	com/taobao/munion/base/volley/i:d	Z
    //   89: ifeq +63 -> 152
    //   92: aload_2
    //   93: invokevirtual 107	com/taobao/munion/base/volley/l:y	()Z
    //   96: ifeq +56 -> 152
    //   99: aload_2
    //   100: ldc 109
    //   102: invokevirtual 90	com/taobao/munion/base/volley/l:b	(Ljava/lang/String;)V
    //   105: goto -100 -> 5
    //   108: astore_3
    //   109: iconst_1
    //   110: anewarray 111	java/lang/Object
    //   113: astore 4
    //   115: aload 4
    //   117: iconst_0
    //   118: aload_3
    //   119: invokevirtual 115	java/lang/Exception:toString	()Ljava/lang/String;
    //   122: aastore
    //   123: aload_3
    //   124: ldc 117
    //   126: aload 4
    //   128: invokestatic 122	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   131: aload_0
    //   132: getfield 30	com/taobao/munion/base/volley/g:d	Lcom/taobao/munion/base/volley/o;
    //   135: aload_2
    //   136: new 64	com/taobao/munion/base/volley/s
    //   139: dup
    //   140: aload_3
    //   141: invokespecial 125	com/taobao/munion/base/volley/s:<init>	(Ljava/lang/Throwable;)V
    //   144: invokeinterface 56 3 0
    //   149: goto -144 -> 5
    //   152: aload_2
    //   153: aload 6
    //   155: invokevirtual 128	com/taobao/munion/base/volley/l:a	(Lcom/taobao/munion/base/volley/i;)Lcom/taobao/munion/base/volley/n;
    //   158: astore 7
    //   160: aload_2
    //   161: ldc 130
    //   163: invokevirtual 82	com/taobao/munion/base/volley/l:a	(Ljava/lang/String;)V
    //   166: aload_2
    //   167: invokevirtual 133	com/taobao/munion/base/volley/l:t	()Z
    //   170: ifeq +35 -> 205
    //   173: aload 7
    //   175: getfield 138	com/taobao/munion/base/volley/n:b	Lcom/taobao/munion/base/volley/b$a;
    //   178: ifnull +27 -> 205
    //   181: aload_0
    //   182: getfield 28	com/taobao/munion/base/volley/g:c	Lcom/taobao/munion/base/volley/b;
    //   185: aload_2
    //   186: invokevirtual 141	com/taobao/munion/base/volley/l:g	()Ljava/lang/String;
    //   189: aload 7
    //   191: getfield 138	com/taobao/munion/base/volley/n:b	Lcom/taobao/munion/base/volley/b$a;
    //   194: invokeinterface 146 3 0
    //   199: aload_2
    //   200: ldc 148
    //   202: invokevirtual 82	com/taobao/munion/base/volley/l:a	(Ljava/lang/String;)V
    //   205: aload_2
    //   206: invokevirtual 151	com/taobao/munion/base/volley/l:x	()V
    //   209: aload_0
    //   210: getfield 30	com/taobao/munion/base/volley/g:d	Lcom/taobao/munion/base/volley/o;
    //   213: aload_2
    //   214: aload 7
    //   216: invokeinterface 154 3 0
    //   221: goto -216 -> 5
    //
    // Exception table:
    //   from	to	target	type
    //   18	37	40	com/taobao/munion/base/volley/s
    //   61	105	40	com/taobao/munion/base/volley/s
    //   152	205	40	com/taobao/munion/base/volley/s
    //   205	221	40	com/taobao/munion/base/volley/s
    //   5	18	52	java/lang/InterruptedException
    //   18	37	108	java/lang/Exception
    //   61	105	108	java/lang/Exception
    //   152	205	108	java/lang/Exception
    //   205	221	108	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.g
 * JD-Core Version:    0.6.2
 */