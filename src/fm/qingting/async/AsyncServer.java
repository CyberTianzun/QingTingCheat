package fm.qingting.async;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.callback.ConnectCallback;
import fm.qingting.async.callback.ListenCallback;
import fm.qingting.async.future.Cancellable;
import fm.qingting.async.future.Future;
import fm.qingting.async.future.SimpleFuture;
import fm.qingting.async.future.TransformFuture;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AsyncServer
{
  public static final String LOGTAG = "NIO";
  private static final long QUEUE_EMPTY = 9223372036854775807L;
  static AsyncServer mInstance;
  static WeakHashMap<Thread, AsyncServer> mServers;
  private static WeakHashMap<Thread, ThreadQueue> mThreadQueues;
  Thread mAffinity;
  private boolean mAutoStart = false;
  LinkedList<Scheduled> mQueue = new LinkedList();
  private Selector mSelector;
  ExecutorService synchronousWorkers = Executors.newFixedThreadPool(4);

  static
  {
    boolean bool;
    if (!AsyncServer.class.desiredAssertionStatus())
      bool = true;
    while (true)
    {
      $assertionsDisabled = bool;
      mThreadQueues = new WeakHashMap();
      try
      {
        if (Build.VERSION.SDK_INT <= 8)
        {
          System.setProperty("java.net.preferIPv4Stack", "true");
          System.setProperty("java.net.preferIPv6Addresses", "false");
        }
        label48: mInstance = new AsyncServer()
        {
        };
        mServers = new WeakHashMap();
        return;
        bool = false;
      }
      catch (Throwable localThrowable)
      {
        break label48;
      }
    }
  }

  private boolean addMe()
  {
    synchronized (mServers)
    {
      if ((AsyncServer)mServers.get(Thread.currentThread()) != null)
        return false;
      mServers.put(this.mAffinity, this);
      return true;
    }
  }

  private ConnectFuture connectResolvedInetSocketAddress(final InetSocketAddress paramInetSocketAddress, final ConnectCallback paramConnectCallback)
  {
    final ConnectFuture localConnectFuture = new ConnectFuture(null);
    assert (!paramInetSocketAddress.isUnresolved());
    post(new Runnable()
    {
      public void run()
      {
        SelectionKey localSelectionKey = null;
        if (localConnectFuture.isCancelled())
          return;
        localConnectFuture.callback = paramConnectCallback;
        try
        {
          AsyncServer.ConnectFuture localConnectFuture = localConnectFuture;
          localSocketChannel = SocketChannel.open();
          localConnectFuture.socket = localSocketChannel;
          try
          {
            localSocketChannel.configureBlocking(false);
            localSelectionKey = localSocketChannel.register(AsyncServer.this.mSelector, 8);
            localSelectionKey.attach(localConnectFuture);
            localSocketChannel.connect(paramInetSocketAddress);
            return;
          }
          catch (Exception localException1)
          {
          }
          if (localSelectionKey != null)
            localSelectionKey.cancel();
          if (localSocketChannel != null);
          try
          {
            localSocketChannel.close();
            label96: localConnectFuture.setComplete(localException1);
            return;
          }
          catch (Exception localException3)
          {
            break label96;
          }
        }
        catch (Exception localException2)
        {
          while (true)
          {
            SocketChannel localSocketChannel = null;
            localSelectionKey = null;
          }
        }
      }
    });
    return localConnectFuture;
  }

  public static AsyncServer getCurrentThreadServer()
  {
    return (AsyncServer)mServers.get(Thread.currentThread());
  }

  public static AsyncServer getDefault()
  {
    return mInstance;
  }

  static ThreadQueue getOrCreateThreadQueue(Thread paramThread)
  {
    synchronized (mThreadQueues)
    {
      ThreadQueue localThreadQueue = (ThreadQueue)mThreadQueues.get(paramThread);
      if (localThreadQueue == null)
      {
        localThreadQueue = new ThreadQueue();
        mThreadQueues.put(paramThread, localThreadQueue);
      }
      return localThreadQueue;
    }
  }

  private void handleSocket(AsyncNetworkSocket paramAsyncNetworkSocket)
    throws ClosedChannelException
  {
    SelectionKey localSelectionKey = paramAsyncNetworkSocket.getChannel().register(this.mSelector);
    localSelectionKey.attach(paramAsyncNetworkSocket);
    paramAsyncNetworkSocket.setup(this, localSelectionKey);
  }

  private static long lockAndRunQueue(AsyncServer paramAsyncServer, LinkedList<Scheduled> paramLinkedList)
  {
    long l1 = 9223372036854775807L;
    while (true)
    {
      Object localObject2;
      long l3;
      try
      {
        long l2 = System.currentTimeMillis();
        localObject2 = null;
        l3 = l1;
        if (paramLinkedList.size() <= 0)
          break label144;
        localScheduled = (Scheduled)paramLinkedList.remove();
        if (localScheduled.time <= l2)
        {
          if (localObject2 != null)
            paramLinkedList.addAll((Collection)localObject2);
          if (localScheduled == null)
            return l3;
        }
        else
        {
          long l4 = Math.min(l3, localScheduled.time - l2);
          if (localObject2 != null)
            break label137;
          localObject3 = new LinkedList();
          ((LinkedList)localObject3).add(localScheduled);
          localObject2 = localObject3;
          l3 = l4;
          continue;
        }
      }
      finally
      {
      }
      localScheduled.runnable.run();
      l1 = l3;
      break;
      label137: Object localObject3 = localObject2;
      continue;
      label144: Scheduled localScheduled = null;
    }
  }

  public static void post(Handler paramHandler, Runnable paramRunnable)
  {
    RunnableWrapper localRunnableWrapper = new RunnableWrapper(null);
    ThreadQueue localThreadQueue = getOrCreateThreadQueue(paramHandler.getLooper().getThread());
    localRunnableWrapper.threadQueue = localThreadQueue;
    localRunnableWrapper.handler = paramHandler;
    localRunnableWrapper.runnable = paramRunnable;
    localThreadQueue.add(localRunnableWrapper);
    paramHandler.post(localRunnableWrapper);
    localThreadQueue.queueSemaphore.release();
  }

  private static void run(AsyncServer paramAsyncServer, Selector paramSelector, LinkedList<Scheduled> paramLinkedList, boolean paramBoolean)
  {
    try
    {
      while (true)
      {
        runLoop(paramAsyncServer, paramSelector, paramLinkedList, paramBoolean);
        try
        {
          label7: if ((paramSelector.isOpen()) && ((paramSelector.keys().size() > 0) || (paramBoolean) || (paramLinkedList.size() > 0)));
        }
        finally
        {
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
        Log.e("NIO", "exception?", localException);
      shutdownEverything(paramSelector);
      if (paramAsyncServer.mSelector == paramSelector)
      {
        paramAsyncServer.mQueue = new LinkedList();
        paramAsyncServer.mSelector = null;
        paramAsyncServer.mAffinity = null;
      }
      synchronized (mServers)
      {
        mServers.remove(Thread.currentThread());
        return;
      }
    }
    catch (ClosedSelectorException localClosedSelectorException)
    {
      break label7;
    }
  }

  private static void runLoop(AsyncServer paramAsyncServer, Selector paramSelector, LinkedList<Scheduled> paramLinkedList, boolean paramBoolean)
    throws IOException
  {
    int i = 1;
    long l = lockAndRunQueue(paramAsyncServer, paramLinkedList);
    label461: label469: 
    while (true)
    {
      Set localSet;
      SelectionKey localSelectionKey1;
      try
      {
        if (paramSelector.selectNow() != 0)
          break label469;
        if ((paramSelector.keys().size() == 0) && (!paramBoolean) && (l == 9223372036854775807L))
          return;
        if (i != 0)
        {
          if (l == 9223372036854775807L)
            l = 100L;
          paramSelector.select(l);
        }
        localSet = paramSelector.selectedKeys();
        Iterator localIterator = localSet.iterator();
        if (!localIterator.hasNext())
          break label461;
        localSelectionKey1 = (SelectionKey)localIterator.next();
        try
        {
          if (!localSelectionKey1.isAcceptable())
            break label242;
          SocketChannel localSocketChannel2 = ((ServerSocketChannel)localSelectionKey1.channel()).accept();
          if (localSocketChannel2 == null)
            continue;
          localSocketChannel2.configureBlocking(false);
          SelectionKey localSelectionKey2 = localSocketChannel2.register(paramSelector, 1);
          ListenCallback localListenCallback = (ListenCallback)localSelectionKey1.attachment();
          AsyncNetworkSocket localAsyncNetworkSocket2 = new AsyncNetworkSocket();
          localAsyncNetworkSocket2.attach(localSocketChannel2, (InetSocketAddress)localSocketChannel2.socket().getRemoteSocketAddress());
          localAsyncNetworkSocket2.setup(paramAsyncServer, localSelectionKey2);
          localSelectionKey2.attach(localAsyncNetworkSocket2);
          localListenCallback.onAccepted(localAsyncNetworkSocket2);
        }
        catch (Exception localException1)
        {
          Log.e("NIO", "inner loop exception", localException1);
        }
        continue;
      }
      finally
      {
      }
      label242: if (localSelectionKey1.isReadable())
      {
        paramAsyncServer.onDataTransmitted(((AsyncNetworkSocket)localSelectionKey1.attachment()).onReadable());
      }
      else if (localSelectionKey1.isWritable())
      {
        ((AsyncNetworkSocket)localSelectionKey1.attachment()).onDataWritable();
      }
      else if (localSelectionKey1.isConnectable())
      {
        ConnectFuture localConnectFuture = (ConnectFuture)localSelectionKey1.attachment();
        SocketChannel localSocketChannel1 = (SocketChannel)localSelectionKey1.channel();
        localSelectionKey1.interestOps(1);
        try
        {
          localSocketChannel1.finishConnect();
          AsyncNetworkSocket localAsyncNetworkSocket1 = new AsyncNetworkSocket();
          localAsyncNetworkSocket1.setup(paramAsyncServer, localSelectionKey1);
          localAsyncNetworkSocket1.attach(localSocketChannel1, (InetSocketAddress)localSocketChannel1.socket().getRemoteSocketAddress());
          localSelectionKey1.attach(localAsyncNetworkSocket1);
          if (!localConnectFuture.setComplete(localAsyncNetworkSocket1))
            continue;
          localConnectFuture.callback.onConnectCompleted(null, localAsyncNetworkSocket1);
        }
        catch (Exception localException2)
        {
          localSelectionKey1.cancel();
          localSocketChannel1.close();
        }
        if (localConnectFuture.setComplete(localException2))
          localConnectFuture.callback.onConnectCompleted(localException2, null);
      }
      else
      {
        Log.i("NIO", "wtf");
        if (!$assertionsDisabled)
        {
          throw new AssertionError();
          localSet.clear();
          return;
          i = 0;
        }
      }
    }
  }

  private static void shutdownEverything(Selector paramSelector)
  {
    try
    {
      Iterator localIterator = paramSelector.keys().iterator();
      if (localIterator.hasNext())
        localSelectionKey = (SelectionKey)localIterator.next();
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          SelectionKey localSelectionKey;
          localSelectionKey.channel().close();
          try
          {
            label38: localSelectionKey.cancel();
          }
          catch (Exception localException4)
          {
          }
        }
        localException1 = localException1;
        try
        {
          paramSelector.close();
          return;
        }
        catch (Exception localException2)
        {
        }
      }
      catch (Exception localException3)
      {
        break label38;
      }
    }
  }

  public AsyncDatagramSocket connectDatagram(final String paramString, final int paramInt)
    throws IOException
  {
    final DatagramChannel localDatagramChannel = DatagramChannel.open();
    final AsyncDatagramSocket localAsyncDatagramSocket = new AsyncDatagramSocket();
    localAsyncDatagramSocket.attach(localDatagramChannel);
    run(new Runnable()
    {
      public void run()
      {
        try
        {
          InetSocketAddress localInetSocketAddress = new InetSocketAddress(paramString, paramInt);
          AsyncServer.this.handleSocket(localAsyncDatagramSocket);
          localDatagramChannel.connect(localInetSocketAddress);
          return;
        }
        catch (Exception localException)
        {
          Log.e("NIO", "Datagram error", localException);
        }
      }
    });
    return localAsyncDatagramSocket;
  }

  public AsyncDatagramSocket connectDatagram(final SocketAddress paramSocketAddress)
    throws IOException
  {
    final DatagramChannel localDatagramChannel = DatagramChannel.open();
    final AsyncDatagramSocket localAsyncDatagramSocket = new AsyncDatagramSocket();
    localAsyncDatagramSocket.attach(localDatagramChannel);
    run(new Runnable()
    {
      public void run()
      {
        try
        {
          AsyncServer.this.handleSocket(localAsyncDatagramSocket);
          localDatagramChannel.connect(paramSocketAddress);
          return;
        }
        catch (Exception localException)
        {
        }
      }
    });
    return localAsyncDatagramSocket;
  }

  public Cancellable connectSocket(String paramString, int paramInt, ConnectCallback paramConnectCallback)
  {
    return connectSocket(InetSocketAddress.createUnresolved(paramString, paramInt), paramConnectCallback);
  }

  public Cancellable connectSocket(final InetSocketAddress paramInetSocketAddress, final ConnectCallback paramConnectCallback)
  {
    if (!paramInetSocketAddress.isUnresolved())
      return connectResolvedInetSocketAddress(paramInetSocketAddress, paramConnectCallback);
    return new TransformFuture()
    {
      protected void transform(InetAddress paramAnonymousInetAddress)
        throws Exception
      {
        setParent(AsyncServer.this.connectResolvedInetSocketAddress(new InetSocketAddress(paramInetSocketAddress.getHostName(), paramInetSocketAddress.getPort()), paramConnectCallback));
      }
    }
    .from(getByName(paramInetSocketAddress.getHostName()));
  }

  public void dump()
  {
    post(new Runnable()
    {
      public void run()
      {
        if (AsyncServer.this.mSelector == null)
          Log.i("NIO", "Server dump not possible. No selector?");
        while (true)
        {
          return;
          Log.i("NIO", "Key Count: " + AsyncServer.this.mSelector.keys().size());
          Iterator localIterator = AsyncServer.this.mSelector.keys().iterator();
          while (localIterator.hasNext())
          {
            SelectionKey localSelectionKey = (SelectionKey)localIterator.next();
            Log.i("NIO", "Key: " + localSelectionKey);
          }
        }
      }
    });
  }

  public Thread getAffinity()
  {
    return this.mAffinity;
  }

  public Future<InetAddress[]> getAllByName(final String paramString)
  {
    final SimpleFuture localSimpleFuture = new SimpleFuture();
    this.synchronousWorkers.execute(new Runnable()
    {
      public void run()
      {
        final InetAddress[] arrayOfInetAddress;
        try
        {
          arrayOfInetAddress = InetAddress.getAllByName(paramString);
          if ((arrayOfInetAddress == null) || (arrayOfInetAddress.length == 0))
            throw new Exception("no addresses for host");
        }
        catch (Exception localException)
        {
          AsyncServer.this.post(new Runnable()
          {
            public void run()
            {
              AsyncServer.8.this.val$ret.setComplete(localException, null);
            }
          });
          return;
        }
        AsyncServer.this.post(new Runnable()
        {
          public void run()
          {
            AsyncServer.8.this.val$ret.setComplete(null, arrayOfInetAddress);
          }
        });
      }
    });
    return localSimpleFuture;
  }

  public boolean getAutoStart()
  {
    return this.mAutoStart;
  }

  public Future<InetAddress> getByName(String paramString)
  {
    return new TransformFuture()
    {
      protected void transform(InetAddress[] paramAnonymousArrayOfInetAddress)
        throws Exception
      {
        setComplete(paramAnonymousArrayOfInetAddress[0]);
      }
    }
    .from(getAllByName(paramString));
  }

  public ExecutorService getExecutorService()
  {
    return this.synchronousWorkers;
  }

  public boolean isAffinityThread()
  {
    return this.mAffinity == Thread.currentThread();
  }

  public boolean isRunning()
  {
    return this.mSelector != null;
  }

  public void listen(final InetAddress paramInetAddress, final int paramInt, final ListenCallback paramListenCallback)
  {
    run(new Runnable()
    {
      public void run()
      {
        try
        {
          final ServerSocketChannel localServerSocketChannel = ServerSocketChannel.open();
          ServerSocketChannelWrapper localServerSocketChannelWrapper = new ServerSocketChannelWrapper(localServerSocketChannel);
          if (paramInetAddress == null);
          for (InetSocketAddress localInetSocketAddress = new InetSocketAddress(paramInt); ; localInetSocketAddress = new InetSocketAddress(paramInetAddress, paramInt))
          {
            localServerSocketChannel.socket().bind(localInetSocketAddress);
            final SelectionKey localSelectionKey = localServerSocketChannelWrapper.register(AsyncServer.this.mSelector);
            localSelectionKey.attach(paramListenCallback);
            paramListenCallback.onListening(new AsyncServerSocket()
            {
              public void stop()
              {
                try
                {
                  localServerSocketChannel.close();
                  try
                  {
                    label7: localSelectionKey.cancel();
                    return;
                  }
                  catch (Exception localException2)
                  {
                  }
                }
                catch (Exception localException1)
                {
                  break label7;
                }
              }
            });
            return;
          }
        }
        catch (Exception localException)
        {
          paramListenCallback.onCompleted(localException);
        }
      }
    });
  }

  protected void onDataTransmitted(int paramInt)
  {
  }

  public AsyncDatagramSocket openDatagram()
    throws IOException
  {
    final DatagramChannel localDatagramChannel = DatagramChannel.open();
    final AsyncDatagramSocket localAsyncDatagramSocket = new AsyncDatagramSocket();
    localAsyncDatagramSocket.attach(localDatagramChannel);
    run(new Runnable()
    {
      public void run()
      {
        try
        {
          localDatagramChannel.socket().bind(null);
          AsyncServer.this.handleSocket(localAsyncDatagramSocket);
          return;
        }
        catch (Exception localException)
        {
          Log.e("NIO", "Datagram error", localException);
        }
      }
    });
    return localAsyncDatagramSocket;
  }

  public Object post(final CompletedCallback paramCompletedCallback, final Exception paramException)
  {
    return post(new Runnable()
    {
      public void run()
      {
        paramCompletedCallback.onCompleted(paramException);
      }
    });
  }

  public Object post(Runnable paramRunnable)
  {
    return postDelayed(paramRunnable, 0L);
  }

  public Object postDelayed(Runnable paramRunnable, long paramLong)
  {
    if (paramLong != 0L);
    try
    {
      paramLong += System.currentTimeMillis();
      LinkedList localLinkedList = this.mQueue;
      Scheduled localScheduled = new Scheduled(paramRunnable, paramLong);
      localLinkedList.add(localScheduled);
      if (this.mSelector == null)
        run(false, true);
      if ((Thread.currentThread() != this.mAffinity) && (this.mSelector != null))
        this.mSelector.wakeup();
      return localScheduled;
    }
    finally
    {
    }
  }

  public void removeAllCallbacks(Object paramObject)
  {
    try
    {
      this.mQueue.remove(paramObject);
      return;
    }
    finally
    {
    }
  }

  public void run()
  {
    run(false, false);
  }

  public void run(final Runnable paramRunnable)
  {
    if (Thread.currentThread() == this.mAffinity)
    {
      post(paramRunnable);
      lockAndRunQueue(this, this.mQueue);
      return;
    }
    final Semaphore localSemaphore = new Semaphore(0);
    post(new Runnable()
    {
      public void run()
      {
        paramRunnable.run();
        localSemaphore.release();
      }
    });
    try
    {
      localSemaphore.acquire();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Log.e("NIO", "run", localInterruptedException);
    }
  }

  // ERROR //
  public void run(final boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   6: ifnull +73 -> 79
    //   9: ldc 11
    //   11: ldc_w 579
    //   14: invokestatic 433	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   17: pop
    //   18: getstatic 44	fm/qingting/async/AsyncServer:$assertionsDisabled	Z
    //   21: ifne +26 -> 47
    //   24: invokestatic 131	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   27: aload_0
    //   28: getfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   31: if_acmpeq +16 -> 47
    //   34: new 153	java/lang/AssertionError
    //   37: dup
    //   38: invokespecial 154	java/lang/AssertionError:<init>	()V
    //   41: athrow
    //   42: astore_3
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_3
    //   46: athrow
    //   47: iconst_1
    //   48: istore 8
    //   50: aload_0
    //   51: getfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   54: astore 5
    //   56: aload_0
    //   57: getfield 86	fm/qingting/async/AsyncServer:mQueue	Ljava/util/LinkedList;
    //   60: astore 6
    //   62: aload_0
    //   63: monitorexit
    //   64: iload 8
    //   66: ifeq +131 -> 197
    //   69: aload_0
    //   70: aload 5
    //   72: aload 6
    //   74: iconst_0
    //   75: invokestatic 282	fm/qingting/async/AsyncServer:runLoop	(Lfm/qingting/async/AsyncServer;Ljava/nio/channels/Selector;Ljava/util/LinkedList;Z)V
    //   78: return
    //   79: invokestatic 585	java/nio/channels/spi/SelectorProvider:provider	()Ljava/nio/channels/spi/SelectorProvider;
    //   82: invokevirtual 589	java/nio/channels/spi/SelectorProvider:openSelector	()Ljava/nio/channels/spi/AbstractSelector;
    //   85: astore 5
    //   87: aload_0
    //   88: aload 5
    //   90: putfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   93: aload_0
    //   94: getfield 86	fm/qingting/async/AsyncServer:mQueue	Ljava/util/LinkedList;
    //   97: astore 6
    //   99: iload_2
    //   100: ifeq +59 -> 159
    //   103: aload_0
    //   104: new 591	fm/qingting/async/AsyncServer$13
    //   107: dup
    //   108: aload_0
    //   109: ldc_w 593
    //   112: aload 5
    //   114: aload 6
    //   116: iload_1
    //   117: invokespecial 596	fm/qingting/async/AsyncServer$13:<init>	(Lfm/qingting/async/AsyncServer;Ljava/lang/String;Ljava/nio/channels/Selector;Ljava/util/LinkedList;Z)V
    //   120: putfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   123: aload_0
    //   124: invokespecial 598	fm/qingting/async/AsyncServer:addMe	()Z
    //   127: istore 7
    //   129: iload 7
    //   131: ifne +38 -> 169
    //   134: aload_0
    //   135: getfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   138: invokevirtual 440	java/nio/channels/Selector:close	()V
    //   141: aload_0
    //   142: aconst_null
    //   143: putfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   146: aload_0
    //   147: aconst_null
    //   148: putfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   151: aload_0
    //   152: monitorexit
    //   153: return
    //   154: astore 4
    //   156: aload_0
    //   157: monitorexit
    //   158: return
    //   159: aload_0
    //   160: invokestatic 131	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   163: putfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   166: goto -43 -> 123
    //   169: iload_2
    //   170: ifeq +42 -> 212
    //   173: aload_0
    //   174: getfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   177: invokevirtual 601	java/lang/Thread:start	()V
    //   180: aload_0
    //   181: monitorexit
    //   182: return
    //   183: astore 9
    //   185: ldc 11
    //   187: ldc_w 296
    //   190: aload 9
    //   192: invokestatic 302	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   195: pop
    //   196: return
    //   197: aload_0
    //   198: aload 5
    //   200: aload 6
    //   202: iload_1
    //   203: invokestatic 124	fm/qingting/async/AsyncServer:run	(Lfm/qingting/async/AsyncServer;Ljava/nio/channels/Selector;Ljava/util/LinkedList;Z)V
    //   206: return
    //   207: astore 11
    //   209: goto -68 -> 141
    //   212: iconst_0
    //   213: istore 8
    //   215: goto -153 -> 62
    //
    // Exception table:
    //   from	to	target	type
    //   2	42	42	finally
    //   43	45	42	finally
    //   50	62	42	finally
    //   62	64	42	finally
    //   79	99	42	finally
    //   103	123	42	finally
    //   123	129	42	finally
    //   134	141	42	finally
    //   141	153	42	finally
    //   156	158	42	finally
    //   159	166	42	finally
    //   173	182	42	finally
    //   79	99	154	java/io/IOException
    //   69	78	183	java/lang/Exception
    //   134	141	207	java/lang/Exception
  }

  public void setAutostart(boolean paramBoolean)
  {
    this.mAutoStart = paramBoolean;
  }

  // ERROR //
  public void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   6: ifnonnull +6 -> 12
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: aload_0
    //   13: new 606	fm/qingting/async/AsyncServer$4
    //   16: dup
    //   17: aload_0
    //   18: aload_0
    //   19: getfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   22: invokespecial 609	fm/qingting/async/AsyncServer$4:<init>	(Lfm/qingting/async/AsyncServer;Ljava/nio/channels/Selector;)V
    //   25: invokevirtual 163	fm/qingting/async/AsyncServer:post	(Ljava/lang/Runnable;)Ljava/lang/Object;
    //   28: pop
    //   29: getstatic 78	fm/qingting/async/AsyncServer:mServers	Ljava/util/WeakHashMap;
    //   32: astore_3
    //   33: aload_3
    //   34: monitorenter
    //   35: getstatic 78	fm/qingting/async/AsyncServer:mServers	Ljava/util/WeakHashMap;
    //   38: aload_0
    //   39: getfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   42: invokevirtual 304	java/util/WeakHashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   45: pop
    //   46: aload_3
    //   47: monitorexit
    //   48: aload_0
    //   49: new 83	java/util/LinkedList
    //   52: dup
    //   53: invokespecial 84	java/util/LinkedList:<init>	()V
    //   56: putfield 86	fm/qingting/async/AsyncServer:mQueue	Ljava/util/LinkedList;
    //   59: aload_0
    //   60: aconst_null
    //   61: putfield 105	fm/qingting/async/AsyncServer:mSelector	Ljava/nio/channels/Selector;
    //   64: aload_0
    //   65: aconst_null
    //   66: putfield 137	fm/qingting/async/AsyncServer:mAffinity	Ljava/lang/Thread;
    //   69: aload_0
    //   70: monitorexit
    //   71: return
    //   72: astore_1
    //   73: aload_0
    //   74: monitorexit
    //   75: aload_1
    //   76: athrow
    //   77: astore 4
    //   79: aload_3
    //   80: monitorexit
    //   81: aload 4
    //   83: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	72	finally
    //   12	35	72	finally
    //   48	71	72	finally
    //   73	75	72	finally
    //   81	84	72	finally
    //   35	48	77	finally
    //   79	81	77	finally
  }

  public static class AsyncSemaphore
  {
    Semaphore semaphore = new Semaphore(0);

    public void acquire()
      throws InterruptedException
    {
      AsyncServer.ThreadQueue localThreadQueue = AsyncServer.getOrCreateThreadQueue(Thread.currentThread());
      AsyncSemaphore localAsyncSemaphore = localThreadQueue.waiter;
      localThreadQueue.waiter = this;
      Semaphore localSemaphore = localThreadQueue.queueSemaphore;
      try
      {
        boolean bool1 = this.semaphore.tryAcquire();
        Runnable localRunnable;
        if (bool1)
        {
          return;
          localRunnable.run();
        }
        boolean bool2;
        do
        {
          localRunnable = localThreadQueue.remove();
          if (localRunnable != null)
            break;
          localSemaphore.acquire(Math.max(1, localSemaphore.availablePermits()));
          bool2 = this.semaphore.tryAcquire();
        }
        while (!bool2);
        return;
      }
      finally
      {
        localThreadQueue.waiter = localAsyncSemaphore;
      }
    }

    public void release()
    {
      this.semaphore.release();
      synchronized (AsyncServer.mThreadQueues)
      {
        Iterator localIterator = AsyncServer.mThreadQueues.values().iterator();
        while (localIterator.hasNext())
        {
          AsyncServer.ThreadQueue localThreadQueue = (AsyncServer.ThreadQueue)localIterator.next();
          if (localThreadQueue.waiter == this)
            localThreadQueue.queueSemaphore.release();
        }
      }
    }

    public boolean tryAcquire(long paramLong, TimeUnit paramTimeUnit)
      throws InterruptedException
    {
      long l1 = TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit);
      AsyncServer.ThreadQueue localThreadQueue = AsyncServer.getOrCreateThreadQueue(Thread.currentThread());
      AsyncSemaphore localAsyncSemaphore = localThreadQueue.waiter;
      localThreadQueue.waiter = this;
      Semaphore localSemaphore = localThreadQueue.queueSemaphore;
      long l2;
      long l3;
      do
      {
        try
        {
          boolean bool1 = this.semaphore.tryAcquire();
          if (bool1)
            return true;
          l2 = System.currentTimeMillis();
          while (true)
          {
            Runnable localRunnable = localThreadQueue.remove();
            if (localRunnable == null)
            {
              boolean bool2 = localSemaphore.tryAcquire(Math.max(1, localSemaphore.availablePermits()), l1, TimeUnit.MILLISECONDS);
              if (bool2)
                break;
              return false;
            }
            localRunnable.run();
          }
        }
        finally
        {
          localThreadQueue.waiter = localAsyncSemaphore;
        }
        boolean bool3 = this.semaphore.tryAcquire();
        if (bool3)
        {
          localThreadQueue.waiter = localAsyncSemaphore;
          return true;
        }
        l3 = System.currentTimeMillis();
      }
      while (l3 - l2 < l1);
      localThreadQueue.waiter = localAsyncSemaphore;
      return false;
    }
  }

  private class ConnectFuture extends SimpleFuture<AsyncNetworkSocket>
  {
    ConnectCallback callback;
    SocketChannel socket;

    private ConnectFuture()
    {
    }

    protected void cancelCleanup()
    {
      super.cancelCleanup();
      try
      {
        if (this.socket != null)
          this.socket.close();
        return;
      }
      catch (IOException localIOException)
      {
      }
    }
  }

  static class Reclaimer
    implements Comparator<ByteBuffer>
  {
    public int compare(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    {
      if (paramByteBuffer1.capacity() > paramByteBuffer2.capacity())
        return 1;
      return -1;
    }
  }

  private static class RunnableWrapper
    implements Runnable
  {
    Handler handler;
    boolean hasRun;
    Runnable runnable;
    AsyncServer.ThreadQueue threadQueue;

    // ERROR //
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 23	fm/qingting/async/AsyncServer$RunnableWrapper:hasRun	Z
      //   6: ifeq +6 -> 12
      //   9: aload_0
      //   10: monitorexit
      //   11: return
      //   12: aload_0
      //   13: iconst_1
      //   14: putfield 23	fm/qingting/async/AsyncServer$RunnableWrapper:hasRun	Z
      //   17: aload_0
      //   18: monitorexit
      //   19: aload_0
      //   20: getfield 25	fm/qingting/async/AsyncServer$RunnableWrapper:runnable	Ljava/lang/Runnable;
      //   23: ifnull +12 -> 35
      //   26: aload_0
      //   27: getfield 25	fm/qingting/async/AsyncServer$RunnableWrapper:runnable	Ljava/lang/Runnable;
      //   30: invokeinterface 27 1 0
      //   35: aload_0
      //   36: getfield 29	fm/qingting/async/AsyncServer$RunnableWrapper:threadQueue	Lfm/qingting/async/AsyncServer$ThreadQueue;
      //   39: aload_0
      //   40: invokevirtual 35	fm/qingting/async/AsyncServer$ThreadQueue:remove	(Ljava/lang/Object;)Z
      //   43: pop
      //   44: aload_0
      //   45: getfield 37	fm/qingting/async/AsyncServer$RunnableWrapper:handler	Landroid/os/Handler;
      //   48: aload_0
      //   49: invokevirtual 43	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
      //   52: aload_0
      //   53: aconst_null
      //   54: putfield 29	fm/qingting/async/AsyncServer$RunnableWrapper:threadQueue	Lfm/qingting/async/AsyncServer$ThreadQueue;
      //   57: aload_0
      //   58: aconst_null
      //   59: putfield 37	fm/qingting/async/AsyncServer$RunnableWrapper:handler	Landroid/os/Handler;
      //   62: aload_0
      //   63: aconst_null
      //   64: putfield 25	fm/qingting/async/AsyncServer$RunnableWrapper:runnable	Ljava/lang/Runnable;
      //   67: return
      //   68: astore_1
      //   69: aload_0
      //   70: monitorexit
      //   71: aload_1
      //   72: athrow
      //   73: astore_2
      //   74: aload_0
      //   75: getfield 29	fm/qingting/async/AsyncServer$RunnableWrapper:threadQueue	Lfm/qingting/async/AsyncServer$ThreadQueue;
      //   78: aload_0
      //   79: invokevirtual 35	fm/qingting/async/AsyncServer$ThreadQueue:remove	(Ljava/lang/Object;)Z
      //   82: pop
      //   83: aload_0
      //   84: getfield 37	fm/qingting/async/AsyncServer$RunnableWrapper:handler	Landroid/os/Handler;
      //   87: aload_0
      //   88: invokevirtual 43	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
      //   91: aload_0
      //   92: aconst_null
      //   93: putfield 29	fm/qingting/async/AsyncServer$RunnableWrapper:threadQueue	Lfm/qingting/async/AsyncServer$ThreadQueue;
      //   96: aload_0
      //   97: aconst_null
      //   98: putfield 37	fm/qingting/async/AsyncServer$RunnableWrapper:handler	Landroid/os/Handler;
      //   101: aload_0
      //   102: aconst_null
      //   103: putfield 25	fm/qingting/async/AsyncServer$RunnableWrapper:runnable	Ljava/lang/Runnable;
      //   106: aload_2
      //   107: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   2	11	68	finally
      //   12	19	68	finally
      //   69	71	68	finally
      //   19	35	73	finally
    }
  }

  private static class Scheduled
  {
    public Runnable runnable;
    public long time;

    public Scheduled(Runnable paramRunnable, long paramLong)
    {
      this.runnable = paramRunnable;
      this.time = paramLong;
    }
  }

  public static class ThreadQueue extends LinkedList<Runnable>
  {
    Semaphore queueSemaphore = new Semaphore(0);
    AsyncServer.AsyncSemaphore waiter;

    public boolean add(Runnable paramRunnable)
    {
      try
      {
        boolean bool = super.add(paramRunnable);
        return bool;
      }
      finally
      {
      }
    }

    public Runnable remove()
    {
      try
      {
        if (isEmpty())
          return null;
        Runnable localRunnable = (Runnable)super.remove();
        return localRunnable;
      }
      finally
      {
      }
    }

    public boolean remove(Object paramObject)
    {
      try
      {
        boolean bool = super.remove(paramObject);
        return bool;
      }
      finally
      {
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.AsyncServer
 * JD-Core Version:    0.6.2
 */