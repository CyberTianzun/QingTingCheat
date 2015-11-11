package com.taobao.munion.base.download;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.SparseArray;
import android.widget.Toast;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.a.m;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DownloadingService extends Service
{
  private static final long B = 8000L;
  private static final long C = 500L;
  private static Map<c.a, Messenger> D = new HashMap();
  private static SparseArray<f.c> E = new SparseArray();
  private static Boolean H = Boolean.valueOf(false);
  public static boolean a = false;
  static final int b = 1;
  static final int c = 2;
  static final int d = 3;
  static final int e = 4;
  static final int f = 5;
  static final int g = 6;
  public static final int h = 0;
  public static final int i = 1;
  public static final int j = 2;
  public static final int k = 3;
  public static final int l = 4;
  public static final int m = 5;
  public static final int n = 6;
  public static final int o = 7;
  static final int p = 100;
  static final String q = "filename";
  private static final long v = 104857600L;
  private static final long w = 10485760L;
  private static final long x = 259200000L;
  private static final int y = 3;
  private Handler A;
  private i F;
  private boolean G = true;
  a r;
  final Messenger s = new Messenger(new c());
  private NotificationManager t;
  private f u;
  private Context z;

  private void a(c.a parama)
  {
    Log.d("startDownload([mComponentName:" + parama.b + " mTitle:" + parama.c + " mUrl:" + parama.d + "])", new Object[0]);
    this.u.c(parama);
    int i1 = this.u.a(parama);
    b localb = new b(getApplicationContext(), parama, i1, 0, this.r);
    f.c localc1 = new f.c(parama, i1);
    this.F.a(i1);
    localc1.a(E);
    localc1.a = localb;
    localb.start();
    d();
    if (a)
      for (int i2 = 0; i2 < E.size(); i2++)
      {
        f.c localc2 = (f.c)E.valueAt(i2);
        Log.d("Running task " + localc2.e.c, new Object[0]);
      }
  }

  private void a(final String paramString)
  {
    synchronized (H)
    {
      if (!H.booleanValue())
      {
        Log.d("show single toast.[" + paramString + "]", new Object[0]);
        H = Boolean.valueOf(true);
        this.A.post(new Runnable()
        {
          public void run()
          {
            Toast.makeText(DownloadingService.b(DownloadingService.this), paramString, 0).show();
          }
        });
        this.A.postDelayed(new Runnable()
        {
          public void run()
          {
            DownloadingService.a(Boolean.valueOf(false));
          }
        }
        , 1200L);
      }
      return;
    }
  }

  private void c()
  {
    Iterator localIterator = this.F.a().iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      this.t.cancel(localInteger.intValue());
    }
  }

  private void d()
  {
    if (a)
    {
      int i1 = D.size();
      int i2 = E.size();
      Log.i("Client size =" + i1 + "   cacheSize = " + i2, new Object[0]);
      if (i1 != i2)
        throw new RuntimeException("Client size =" + i1 + "   cacheSize = " + i2);
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    Log.d("onBind ", new Object[0]);
    return this.s.getBinder();
  }

  public void onCreate()
  {
    super.onCreate();
    if (a)
      Debug.waitForDebugger();
    this.t = ((NotificationManager)getSystemService("notification"));
    this.z = this;
    this.F = new i(this.z);
    this.u = new f(E, D, this.F);
    this.u.a(m.a(getApplicationContext()));
    this.A = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default:
          return;
        case 5:
          c.a locala2 = (c.a)paramAnonymousMessage.obj;
          int j = paramAnonymousMessage.arg2;
          while (true)
          {
            PendingIntent localPendingIntent2;
            try
            {
              String str4 = paramAnonymousMessage.getData().getString("filename");
              Log.d("Cancel old notification....", new Object[0]);
              Intent localIntent = new Intent("android.intent.action.VIEW");
              localIntent.addFlags(268435456);
              localIntent.setDataAndType(Uri.fromFile(new File(str4)), "application/vnd.android.package-archive");
              localPendingIntent2 = PendingIntent.getActivity(DownloadingService.b(DownloadingService.this), 0, localIntent, 134217728);
              if (locala2.n)
              {
                Notification localNotification2 = new Notification(17301634, k.k, System.currentTimeMillis());
                localNotification2.setLatestEventInfo(DownloadingService.b(DownloadingService.this), locala2.c, k.k, localPendingIntent2);
                localObject = localNotification2;
                ((Notification)localObject).flags = 16;
                DownloadingService.a(DownloadingService.this, (NotificationManager)DownloadingService.this.getSystemService("notification"));
                DownloadingService.c(DownloadingService.this).notify(j + 1, (Notification)localObject);
                Log.d("Show new  notification....", new Object[0]);
                boolean bool = DownloadingService.a(DownloadingService.this).a(DownloadingService.b(DownloadingService.this));
                Object[] arrayOfObject1 = new Object[1];
                arrayOfObject1[0] = Boolean.valueOf(bool);
                Log.d(String.format("isAppOnForeground = %1$B", arrayOfObject1), new Object[0]);
                if ((bool) && (!locala2.n))
                {
                  DownloadingService.c(DownloadingService.this).cancel(j + 1);
                  DownloadingService.b(DownloadingService.this).startActivity(localIntent);
                }
                Object[] arrayOfObject2 = new Object[2];
                arrayOfObject2[0] = locala2.c;
                arrayOfObject2[1] = str4;
                Log.i(String.format("%1$10s downloaded. Saved to: %2$s", arrayOfObject2), new Object[0]);
                return;
              }
            }
            catch (Exception localException)
            {
              Log.e("can not install. " + localException.getMessage(), new Object[0]);
              DownloadingService.c(DownloadingService.this).cancel(j + 1);
              return;
            }
            Notification localNotification3 = new Notification(17301634, k.j, System.currentTimeMillis());
            localNotification3.setLatestEventInfo(DownloadingService.b(DownloadingService.this), locala2.c, k.j, localPendingIntent2);
            Object localObject = localNotification3;
          }
        case 6:
        }
        c.a locala1 = (c.a)paramAnonymousMessage.obj;
        int i = paramAnonymousMessage.arg2;
        String str1 = paramAnonymousMessage.getData().getString("filename");
        DownloadingService.c(DownloadingService.this).cancel(i);
        Notification localNotification1 = new Notification(17301633, k.l, System.currentTimeMillis());
        PendingIntent localPendingIntent1 = PendingIntent.getActivity(DownloadingService.b(DownloadingService.this), 0, new Intent(), 134217728);
        localNotification1.setLatestEventInfo(DownloadingService.b(DownloadingService.this), f.c(DownloadingService.b(DownloadingService.this)), k.l, localPendingIntent1);
        DownloadingService.c(DownloadingService.this).notify(i + 1, localNotification1);
        String str2 = str1.replace(".patch", ".apk");
        String str3 = DeltaUpdate.a(DownloadingService.this);
        f localf = DownloadingService.a(DownloadingService.this);
        localf.getClass();
        new f.d(localf, DownloadingService.b(DownloadingService.this), i, locala1, str2).execute(new String[] { str3, str2, str1 });
      }
    };
    this.r = new a()
    {
      SparseArray<Long> a = new SparseArray();

      public void a(int paramAnonymousInt)
      {
        if (DownloadingService.a().indexOfKey(paramAnonymousInt) >= 0)
        {
          f.c localc = (f.c)DownloadingService.a().get(paramAnonymousInt);
          long[] arrayOfLong = localc.f;
          int i = 0;
          if (arrayOfLong != null)
          {
            boolean bool = arrayOfLong[1] < 0L;
            i = 0;
            if (bool)
            {
              i = (int)(100.0F * ((float)arrayOfLong[0] / (float)arrayOfLong[1]));
              if (i > 100)
                i = 99;
            }
          }
          if (!localc.e.n)
          {
            this.a.put(paramAnonymousInt, Long.valueOf(-1L));
            f.a locala = DownloadingService.a(DownloadingService.this).a(DownloadingService.this, localc.e, paramAnonymousInt, i);
            localc.b = locala;
            DownloadingService.c(DownloadingService.this).notify(paramAnonymousInt, locala.a());
          }
        }
      }

      public void a(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (DownloadingService.a().indexOfKey(paramAnonymousInt1) >= 0)
        {
          f.c localc = (f.c)DownloadingService.a().get(paramAnonymousInt1);
          c.a locala = localc.e;
          long l = System.currentTimeMillis();
          if ((!locala.n) && (l - ((Long)this.a.get(paramAnonymousInt1)).longValue() > 500L))
          {
            this.a.put(paramAnonymousInt1, Long.valueOf(l));
            f.a locala1 = localc.b;
            locala1.a(100, paramAnonymousInt2, false).a(String.valueOf(paramAnonymousInt2) + "%");
            DownloadingService.c(DownloadingService.this).notify(paramAnonymousInt1, locala1.a());
          }
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = Integer.valueOf(paramAnonymousInt1);
          arrayOfObject[1] = Integer.valueOf(paramAnonymousInt2);
          arrayOfObject[2] = locala.c;
          Log.d(String.format("%3$10s Notification: mNotificationId = %1$15s\t|\tprogress = %2$15s", arrayOfObject), new Object[0]);
        }
      }

      public void a(int paramAnonymousInt, Exception paramAnonymousException)
      {
        if (DownloadingService.a().indexOfKey(paramAnonymousInt) >= 0)
          DownloadingService.a(DownloadingService.this).b(DownloadingService.b(DownloadingService.this), paramAnonymousInt);
      }

      public void a(int paramAnonymousInt, String paramAnonymousString)
      {
        c.a locala;
        Bundle localBundle;
        if (DownloadingService.a().indexOfKey(paramAnonymousInt) >= 0)
        {
          f.c localc = (f.c)DownloadingService.a().get(paramAnonymousInt);
          if (localc != null)
          {
            locala = localc.e;
            e.a(DownloadingService.b(DownloadingService.this)).a(locala.b, locala.d, 100);
            localBundle = new Bundle();
            localBundle.putString("filename", paramAnonymousString);
            if (!locala.b.equalsIgnoreCase("delta_update"))
              break label138;
            Message localMessage3 = Message.obtain();
            localMessage3.what = 6;
            localMessage3.arg1 = 1;
            localMessage3.obj = locala;
            localMessage3.arg2 = paramAnonymousInt;
            localMessage3.setData(localBundle);
            DownloadingService.d(DownloadingService.this).sendMessage(localMessage3);
          }
        }
        return;
        label138: Message localMessage1 = Message.obtain();
        localMessage1.what = 5;
        localMessage1.arg1 = 1;
        localMessage1.obj = locala;
        localMessage1.arg2 = paramAnonymousInt;
        localMessage1.setData(localBundle);
        DownloadingService.d(DownloadingService.this).sendMessage(localMessage1);
        Message localMessage2 = Message.obtain();
        localMessage2.what = 5;
        localMessage2.arg1 = 1;
        localMessage2.arg2 = paramAnonymousInt;
        localMessage2.setData(localBundle);
        try
        {
          if (DownloadingService.b().get(locala) != null)
            ((Messenger)DownloadingService.b().get(locala)).send(localMessage2);
          DownloadingService.a(DownloadingService.this).b(DownloadingService.b(DownloadingService.this), paramAnonymousInt);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          DownloadingService.a(DownloadingService.this).b(DownloadingService.b(DownloadingService.this), paramAnonymousInt);
        }
      }
    };
  }

  public void onDestroy()
  {
    try
    {
      e.a(getApplicationContext()).a(259200);
      e.a(getApplicationContext()).finalize();
      super.onDestroy();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.e(localException.getMessage(), new Object[0]);
    }
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if ((paramIntent != null) && (paramIntent.getExtras() != null))
      this.u.a(this, paramIntent);
    if ((Build.VERSION.SDK_INT >= 19) && ((this.F.b()) || (this.G)));
    try
    {
      Intent localIntent = new Intent(getApplicationContext(), getClass());
      localIntent.setPackage(getPackageName());
      PendingIntent localPendingIntent = PendingIntent.getService(getApplicationContext(), 1, localIntent, 1073741824);
      ((AlarmManager)getApplicationContext().getSystemService("alarm")).set(3, 5000L + SystemClock.elapsedRealtime(), localPendingIntent);
      label114: if (this.G)
      {
        c();
        this.G = false;
      }
      return 1;
    }
    catch (Exception localException)
    {
      break label114;
    }
  }

  static abstract interface a
  {
    public abstract void a(int paramInt);

    public abstract void a(int paramInt1, int paramInt2);

    public abstract void a(int paramInt, Exception paramException);

    public abstract void a(int paramInt, String paramString);
  }

  class b extends Thread
  {
    private Context b;
    private boolean c;
    private File d;
    private int e = 0;
    private long f = -1L;
    private long g = -1L;
    private int h = -1;
    private int i;
    private DownloadingService.a j;
    private c.a k;

    public b(Context parama, c.a paramInt1, int paramInt2, int parama1, DownloadingService.a arg6)
    {
      try
      {
        this.b = parama;
        this.k = paramInt1;
        this.e = parama1;
        if (DownloadingService.a().indexOfKey(paramInt2) >= 0)
        {
          long[] arrayOfLong = ((f.c)DownloadingService.a().get(paramInt2)).f;
          if ((arrayOfLong != null) && (arrayOfLong.length > 1))
          {
            this.f = arrayOfLong[0];
            this.g = arrayOfLong[1];
          }
        }
        Object localObject;
        this.j = localObject;
        this.i = paramInt2;
        boolean[] arrayOfBoolean = new boolean[1];
        this.d = f.a("/apk", parama, arrayOfBoolean);
        this.c = arrayOfBoolean[0];
        if (this.c);
        String str = a(this.k);
        this.d = new File(this.d, str);
        return;
      }
      catch (Exception localException)
      {
        Log.d(localException.getMessage(), new Object[] { localException });
        this.j.a(this.i, localException);
      }
    }

    private String a(c.a parama)
    {
      if (parama.f != null);
      for (String str = parama.f + ".apk.tmp"; ; str = f.a(parama.d) + ".apk.tmp")
      {
        if (parama.b.equalsIgnoreCase("delta_update"))
          str = str.replace(".apk", ".patch");
        return str;
      }
    }

    private HttpURLConnection a(URL paramURL, File paramFile)
      throws IOException
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection();
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setRequestProperty("Accept-Encoding", "identity");
      localHttpURLConnection.addRequestProperty("Connection", "keep-alive");
      localHttpURLConnection.setConnectTimeout(5000);
      localHttpURLConnection.setReadTimeout(10000);
      if ((paramFile.exists()) && (paramFile.length() > 0L))
      {
        String str = this.k.c + " getFileLength: %1$15s";
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(paramFile.length());
        Log.d(String.format(str, arrayOfObject), new Object[0]);
        localHttpURLConnection.setRequestProperty("Range", "bytes=" + paramFile.length() + "-");
      }
      return localHttpURLConnection;
    }

    private void a()
    {
      Log.d("wait for repeating Test network repeat count=" + this.e, new Object[0]);
      try
      {
        if (!this.k.m)
        {
          Thread.sleep(8000L);
          if (this.g < 1L)
          {
            a(false);
            return;
          }
          a(true);
          return;
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        a(localInterruptedException);
        DownloadingService.a(DownloadingService.this).b(this.b, this.i);
        return;
      }
      f.c localc = (f.c)DownloadingService.a().get(this.i);
      localc.f[0] = this.f;
      localc.f[1] = this.g;
      localc.f[2] = this.e;
      String str = j.a(this.i, "continue");
      Intent localIntent = new Intent(this.b, DownloadingService.class);
      localIntent.putExtra("com.taobao.broadcast.download.msg", str);
      DownloadingService.a(DownloadingService.this).a(DownloadingService.this, localIntent);
      DownloadingService.a(DownloadingService.this, k.n);
      Log.d("changed play state button on op-notification.", new Object[0]);
    }

    private void a(File paramFile, String paramString)
      throws RemoteException
    {
      Log.d("itemMd5 " + this.k.e, new Object[0]);
      Log.d("fileMd5 " + f.a(paramFile), new Object[0]);
      Message localMessage;
      if ((this.k.e != null) && (!this.k.e.equalsIgnoreCase(f.a(paramFile))))
      {
        if (!this.k.b.equalsIgnoreCase("delta_update"))
          break label245;
        DownloadingService.c(DownloadingService.this).cancel(this.i);
        Bundle localBundle = new Bundle();
        localBundle.putString("filename", paramString);
        localMessage = Message.obtain();
        localMessage.what = 5;
        localMessage.arg1 = 3;
        localMessage.arg2 = this.i;
        localMessage.setData(localBundle);
      }
      label245: 
      do
      {
        try
        {
          if (DownloadingService.b().get(this.k) != null)
            ((Messenger)DownloadingService.b().get(this.k)).send(localMessage);
          DownloadingService.a(DownloadingService.this).b(this.b, this.i);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          DownloadingService.a(DownloadingService.this).b(this.b, this.i);
          return;
        }
        ((Messenger)DownloadingService.b().get(this.k)).send(Message.obtain(null, 5, 0, 0));
      }
      while (this.k.n);
      DownloadingService.a(DownloadingService.this).b(this.b, this.i);
      Notification localNotification = new Notification(17301634, k.m, System.currentTimeMillis());
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.b, 0, new Intent(), 0);
      localNotification.setLatestEventInfo(this.b, f.c(this.b), k.m, localPendingIntent);
      localNotification.flags = (0x10 | localNotification.flags);
      DownloadingService.c(DownloadingService.this).notify(this.i, localNotification);
    }

    private void a(Exception paramException)
    {
      Log.e("can not install. " + paramException.getMessage(), new Object[0]);
      if (this.j != null)
        this.j.a(this.i, paramException);
      DownloadingService.a(DownloadingService.this).a(this.f, this.g, this.e, this.k);
    }

    // ERROR //
    private void a(boolean paramBoolean)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   4: invokevirtual 385	java/io/File:getName	()Ljava/lang/String;
      //   7: astore_2
      //   8: new 387	java/io/FileOutputStream
      //   11: dup
      //   12: aload_0
      //   13: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   16: iconst_1
      //   17: invokespecial 390	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
      //   20: astore_3
      //   21: aload_0
      //   22: getfield 81	com/taobao/munion/base/download/DownloadingService$b:c	Z
      //   25: ifne +1845 -> 1870
      //   28: aload_0
      //   29: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   32: invokevirtual 393	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   35: iconst_3
      //   36: invokestatic 396	com/taobao/munion/base/download/f:a	(Ljava/lang/String;I)Z
      //   39: ifne +1831 -> 1870
      //   42: aload_3
      //   43: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   46: aload_0
      //   47: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   50: aload_2
      //   51: ldc_w 400
      //   54: invokevirtual 406	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
      //   57: astore 73
      //   59: aload_0
      //   60: aload_0
      //   61: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   64: aload_2
      //   65: invokevirtual 410	android/content/Context:getFileStreamPath	(Ljava/lang/String;)Ljava/io/File;
      //   68: putfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   71: aload 73
      //   73: astore 9
      //   75: iconst_2
      //   76: anewarray 95	java/lang/Object
      //   79: astore 35
      //   81: aload 35
      //   83: iconst_0
      //   84: aload_0
      //   85: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   88: getfield 142	com/taobao/munion/base/download/c$a:d	Ljava/lang/String;
      //   91: aastore
      //   92: aload 35
      //   94: iconst_1
      //   95: aload_0
      //   96: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   99: invokevirtual 393	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   102: aastore
      //   103: ldc_w 412
      //   106: aload 35
      //   108: invokestatic 206	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   111: iconst_0
      //   112: anewarray 95	java/lang/Object
      //   115: invokestatic 100	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   118: aload_0
      //   119: new 150	java/net/URL
      //   122: dup
      //   123: aload_0
      //   124: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   127: getfield 142	com/taobao/munion/base/download/c$a:d	Ljava/lang/String;
      //   130: invokespecial 414	java/net/URL:<init>	(Ljava/lang/String;)V
      //   133: aload_0
      //   134: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   137: invokespecial 416	com/taobao/munion/base/download/DownloadingService$b:a	(Ljava/net/URL;Ljava/io/File;)Ljava/net/HttpURLConnection;
      //   140: astore 36
      //   142: aload 36
      //   144: ldc 158
      //   146: invokevirtual 162	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
      //   149: aload 36
      //   151: invokevirtual 419	java/net/HttpURLConnection:connect	()V
      //   154: aload 36
      //   156: invokevirtual 423	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
      //   159: astore 39
      //   161: aload 39
      //   163: astore 10
      //   165: iload_1
      //   166: ifne +156 -> 322
      //   169: lconst_0
      //   170: lstore 68
      //   172: aload_0
      //   173: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   176: invokevirtual 188	java/io/File:exists	()Z
      //   179: ifeq +27 -> 206
      //   182: aload_0
      //   183: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   186: invokevirtual 192	java/io/File:length	()J
      //   189: lconst_0
      //   190: lcmp
      //   191: ifle +15 -> 206
      //   194: lload 68
      //   196: aload_0
      //   197: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   200: invokevirtual 192	java/io/File:length	()J
      //   203: ladd
      //   204: lstore 68
      //   206: aload_0
      //   207: lload 68
      //   209: putfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   212: aload_0
      //   213: lload 68
      //   215: aload 36
      //   217: invokevirtual 427	java/net/HttpURLConnection:getContentLength	()I
      //   220: i2l
      //   221: ladd
      //   222: putfield 40	com/taobao/munion/base/download/DownloadingService$b:g	J
      //   225: iconst_1
      //   226: anewarray 95	java/lang/Object
      //   229: astore 70
      //   231: aload 70
      //   233: iconst_0
      //   234: aload_0
      //   235: getfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   238: invokestatic 202	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   241: aastore
      //   242: ldc_w 429
      //   245: aload 70
      //   247: invokestatic 206	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   250: iconst_0
      //   251: anewarray 95	java/lang/Object
      //   254: invokestatic 100	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   257: iconst_1
      //   258: anewarray 95	java/lang/Object
      //   261: astore 71
      //   263: aload 71
      //   265: iconst_0
      //   266: aload 36
      //   268: invokevirtual 427	java/net/HttpURLConnection:getContentLength	()I
      //   271: invokestatic 434	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   274: aastore
      //   275: ldc_w 436
      //   278: aload 71
      //   280: invokestatic 206	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   283: iconst_0
      //   284: anewarray 95	java/lang/Object
      //   287: invokestatic 100	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   290: iconst_1
      //   291: anewarray 95	java/lang/Object
      //   294: astore 72
      //   296: aload 72
      //   298: iconst_0
      //   299: aload_0
      //   300: getfield 40	com/taobao/munion/base/download/DownloadingService$b:g	J
      //   303: invokestatic 202	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   306: aastore
      //   307: ldc_w 438
      //   310: aload 72
      //   312: invokestatic 206	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   315: iconst_0
      //   316: anewarray 95	java/lang/Object
      //   319: invokestatic 100	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   322: sipush 4096
      //   325: newarray byte
      //   327: astore 41
      //   329: new 112	java/lang/StringBuilder
      //   332: dup
      //   333: invokespecial 113	java/lang/StringBuilder:<init>	()V
      //   336: aload_0
      //   337: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   340: getfield 194	com/taobao/munion/base/download/c$a:c	Ljava/lang/String;
      //   343: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   346: ldc_w 440
      //   349: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   352: aload_0
      //   353: getfield 40	com/taobao/munion/base/download/DownloadingService$b:g	J
      //   356: invokestatic 443	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   359: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   362: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   365: iconst_0
      //   366: anewarray 95	java/lang/Object
      //   369: invokestatic 100	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   372: aload_0
      //   373: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   376: invokestatic 448	com/taobao/munion/base/download/e:a	(Landroid/content/Context;)Lcom/taobao/munion/base/download/e;
      //   379: aload_0
      //   380: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   383: getfield 124	com/taobao/munion/base/download/c$a:b	Ljava/lang/String;
      //   386: aload_0
      //   387: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   390: getfield 142	com/taobao/munion/base/download/c$a:d	Ljava/lang/String;
      //   393: invokevirtual 451	com/taobao/munion/base/download/e:a	(Ljava/lang/String;Ljava/lang/String;)Z
      //   396: pop
      //   397: iconst_0
      //   398: istore 43
      //   400: aload_0
      //   401: getfield 42	com/taobao/munion/base/download/DownloadingService$b:h	I
      //   404: ifge +1460 -> 1864
      //   407: aload 10
      //   409: aload 41
      //   411: invokevirtual 457	java/io/InputStream:read	([B)I
      //   414: istore 64
      //   416: iload 64
      //   418: ifle +1446 -> 1864
      //   421: aload 9
      //   423: aload 41
      //   425: iconst_0
      //   426: iload 64
      //   428: invokevirtual 461	java/io/FileOutputStream:write	([BII)V
      //   431: aload_0
      //   432: aload_0
      //   433: getfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   436: iload 64
      //   438: i2l
      //   439: ladd
      //   440: putfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   443: iload 43
      //   445: iconst_1
      //   446: iadd
      //   447: istore 65
      //   449: iload 43
      //   451: bipush 50
      //   453: irem
      //   454: ifne +1403 -> 1857
      //   457: aload_0
      //   458: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   461: invokestatic 464	com/taobao/munion/base/download/f:b	(Landroid/content/Context;)Z
      //   464: istore 66
      //   466: iconst_0
      //   467: istore 44
      //   469: iload 66
      //   471: ifne +275 -> 746
      //   474: aload 10
      //   476: invokevirtual 465	java/io/InputStream:close	()V
      //   479: aload 9
      //   481: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   484: aload_0
      //   485: getfield 42	com/taobao/munion/base/download/DownloadingService$b:h	I
      //   488: iconst_1
      //   489: if_icmpne +447 -> 936
      //   492: invokestatic 51	com/taobao/munion/base/download/DownloadingService:a	()Landroid/util/SparseArray;
      //   495: aload_0
      //   496: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   499: invokevirtual 61	android/util/SparseArray:get	(I)Ljava/lang/Object;
      //   502: checkcast 63	com/taobao/munion/base/download/f$c
      //   505: astore 58
      //   507: aload 58
      //   509: ifnull +37 -> 546
      //   512: aload 58
      //   514: getfield 66	com/taobao/munion/base/download/f$c:f	[J
      //   517: iconst_0
      //   518: aload_0
      //   519: getfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   522: lastore
      //   523: aload 58
      //   525: getfield 66	com/taobao/munion/base/download/f$c:f	[J
      //   528: iconst_1
      //   529: aload_0
      //   530: getfield 40	com/taobao/munion/base/download/DownloadingService$b:g	J
      //   533: lastore
      //   534: aload 58
      //   536: getfield 66	com/taobao/munion/base/download/f$c:f	[J
      //   539: iconst_2
      //   540: aload_0
      //   541: getfield 34	com/taobao/munion/base/download/DownloadingService$b:e	I
      //   544: i2l
      //   545: lastore
      //   546: aload 10
      //   548: ifnull +8 -> 556
      //   551: aload 10
      //   553: invokevirtual 465	java/io/InputStream:close	()V
      //   556: aload 9
      //   558: ifnull +8 -> 566
      //   561: aload 9
      //   563: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   566: return
      //   567: astore 37
      //   569: aload_0
      //   570: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   573: invokevirtual 468	java/io/File:delete	()Z
      //   576: pop
      //   577: goto -423 -> 154
      //   580: astore 34
      //   582: aload 34
      //   584: astore 6
      //   586: aconst_null
      //   587: astore 7
      //   589: aload 9
      //   591: astore 5
      //   593: aload 6
      //   595: ldc_w 470
      //   598: iconst_0
      //   599: anewarray 95	java/lang/Object
      //   602: invokestatic 473	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
      //   605: iconst_1
      //   606: aload_0
      //   607: getfield 34	com/taobao/munion/base/download/DownloadingService$b:e	I
      //   610: iadd
      //   611: istore 16
      //   613: aload_0
      //   614: iload 16
      //   616: putfield 34	com/taobao/munion/base/download/DownloadingService$b:e	I
      //   619: iload 16
      //   621: iconst_3
      //   622: if_icmple +926 -> 1548
      //   625: aload_0
      //   626: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   629: getfield 225	com/taobao/munion/base/download/c$a:m	Z
      //   632: istore 17
      //   634: iload 17
      //   636: ifne +912 -> 1548
      //   639: ldc_w 475
      //   642: iconst_0
      //   643: anewarray 95	java/lang/Object
      //   646: invokestatic 100	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   649: invokestatic 323	com/taobao/munion/base/download/DownloadingService:b	()Ljava/util/Map;
      //   652: aload_0
      //   653: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   656: invokeinterface 328 2 0
      //   661: checkcast 330	android/os/Messenger
      //   664: aconst_null
      //   665: iconst_5
      //   666: iconst_0
      //   667: iconst_0
      //   668: invokestatic 337	android/os/Message:obtain	(Landroid/os/Handler;III)Landroid/os/Message;
      //   671: invokevirtual 334	android/os/Messenger:send	(Landroid/os/Message;)V
      //   674: aload_0
      //   675: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   678: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   681: aload_0
      //   682: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   685: aload_0
      //   686: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   689: invokevirtual 243	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
      //   692: aload_0
      //   693: aload 6
      //   695: invokespecial 237	com/taobao/munion/base/download/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   698: aload_0
      //   699: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   702: invokestatic 478	com/taobao/munion/base/download/DownloadingService:d	(Lcom/taobao/munion/base/download/DownloadingService;)Landroid/os/Handler;
      //   705: new 480	com/taobao/munion/base/download/DownloadingService$b$1
      //   708: dup
      //   709: aload_0
      //   710: invokespecial 483	com/taobao/munion/base/download/DownloadingService$b$1:<init>	(Lcom/taobao/munion/base/download/DownloadingService$b;)V
      //   713: invokevirtual 489	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   716: pop
      //   717: aload 7
      //   719: ifnull +8 -> 727
      //   722: aload 7
      //   724: invokevirtual 465	java/io/InputStream:close	()V
      //   727: aload 5
      //   729: ifnull -163 -> 566
      //   732: aload 5
      //   734: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   737: return
      //   738: astore 18
      //   740: aload 18
      //   742: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   745: return
      //   746: aload_0
      //   747: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   750: invokestatic 494	com/taobao/munion/base/download/f:e	(Landroid/content/Context;)Z
      //   753: ifne +34 -> 787
      //   756: aload_0
      //   757: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   760: getfield 497	com/taobao/munion/base/download/c$a:o	Z
      //   763: ifeq +24 -> 787
      //   766: ldc_w 499
      //   769: iconst_0
      //   770: anewarray 95	java/lang/Object
      //   773: invokestatic 377	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   776: new 148	java/io/IOException
      //   779: dup
      //   780: ldc_w 499
      //   783: invokespecial 500	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   786: athrow
      //   787: ldc_w 501
      //   790: aload_0
      //   791: getfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   794: l2f
      //   795: fmul
      //   796: aload_0
      //   797: getfield 40	com/taobao/munion/base/download/DownloadingService$b:g	J
      //   800: l2f
      //   801: fdiv
      //   802: f2i
      //   803: istore 67
      //   805: iload 67
      //   807: bipush 100
      //   809: if_icmple +7 -> 816
      //   812: bipush 99
      //   814: istore 67
      //   816: aload_0
      //   817: getfield 68	com/taobao/munion/base/download/DownloadingService$b:j	Lcom/taobao/munion/base/download/DownloadingService$a;
      //   820: ifnull +18 -> 838
      //   823: aload_0
      //   824: getfield 68	com/taobao/munion/base/download/DownloadingService$b:j	Lcom/taobao/munion/base/download/DownloadingService$a;
      //   827: aload_0
      //   828: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   831: iload 67
      //   833: invokeinterface 504 3 0
      //   838: aload_0
      //   839: iload 67
      //   841: invokespecial 506	com/taobao/munion/base/download/DownloadingService$b:b	(I)V
      //   844: aload_0
      //   845: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   848: invokestatic 448	com/taobao/munion/base/download/e:a	(Landroid/content/Context;)Lcom/taobao/munion/base/download/e;
      //   851: aload_0
      //   852: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   855: getfield 124	com/taobao/munion/base/download/c$a:b	Ljava/lang/String;
      //   858: aload_0
      //   859: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   862: getfield 142	com/taobao/munion/base/download/c$a:d	Ljava/lang/String;
      //   865: iload 67
      //   867: invokevirtual 509	com/taobao/munion/base/download/e:a	(Ljava/lang/String;Ljava/lang/String;I)V
      //   870: iload 65
      //   872: istore 43
      //   874: goto -474 -> 400
      //   877: astore 59
      //   879: aload 59
      //   881: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   884: return
      //   885: astore 62
      //   887: aload 62
      //   889: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   892: aload 9
      //   894: ifnull -328 -> 566
      //   897: aload 9
      //   899: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   902: return
      //   903: astore 63
      //   905: aload 63
      //   907: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   910: return
      //   911: astore 60
      //   913: aload 9
      //   915: ifnull +8 -> 923
      //   918: aload 9
      //   920: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   923: aload 60
      //   925: athrow
      //   926: astore 61
      //   928: aload 61
      //   930: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   933: goto -10 -> 923
      //   936: aload_0
      //   937: getfield 42	com/taobao/munion/base/download/DownloadingService$b:h	I
      //   940: iconst_2
      //   941: if_icmpne +124 -> 1065
      //   944: aload_0
      //   945: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   948: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   951: aload_0
      //   952: getfield 38	com/taobao/munion/base/download/DownloadingService$b:f	J
      //   955: aload_0
      //   956: getfield 40	com/taobao/munion/base/download/DownloadingService$b:g	J
      //   959: aload_0
      //   960: getfield 34	com/taobao/munion/base/download/DownloadingService$b:e	I
      //   963: i2l
      //   964: aload_0
      //   965: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   968: invokevirtual 511	com/taobao/munion/base/download/f:d	(JJJLcom/taobao/munion/base/download/c$a;)V
      //   971: aload_0
      //   972: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   975: invokestatic 288	com/taobao/munion/base/download/DownloadingService:c	(Lcom/taobao/munion/base/download/DownloadingService;)Landroid/app/NotificationManager;
      //   978: aload_0
      //   979: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   982: invokevirtual 293	android/app/NotificationManager:cancel	(I)V
      //   985: aload 10
      //   987: ifnull +8 -> 995
      //   990: aload 10
      //   992: invokevirtual 465	java/io/InputStream:close	()V
      //   995: aload 9
      //   997: ifnull -431 -> 566
      //   1000: aload 9
      //   1002: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1005: return
      //   1006: astore 53
      //   1008: aload 53
      //   1010: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1013: return
      //   1014: astore 56
      //   1016: aload 56
      //   1018: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1021: aload 9
      //   1023: ifnull -457 -> 566
      //   1026: aload 9
      //   1028: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1031: return
      //   1032: astore 57
      //   1034: aload 57
      //   1036: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1039: return
      //   1040: astore 54
      //   1042: aload 9
      //   1044: ifnull +8 -> 1052
      //   1047: aload 9
      //   1049: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1052: aload 54
      //   1054: athrow
      //   1055: astore 55
      //   1057: aload 55
      //   1059: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1062: goto -10 -> 1052
      //   1065: iload 44
      //   1067: ifne +139 -> 1206
      //   1070: new 112	java/lang/StringBuilder
      //   1073: dup
      //   1074: invokespecial 113	java/lang/StringBuilder:<init>	()V
      //   1077: ldc_w 513
      //   1080: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1083: aload_0
      //   1084: getfield 34	com/taobao/munion/base/download/DownloadingService$b:e	I
      //   1087: invokevirtual 222	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   1090: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1093: iconst_0
      //   1094: anewarray 95	java/lang/Object
      //   1097: invokestatic 377	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   1100: invokestatic 323	com/taobao/munion/base/download/DownloadingService:b	()Ljava/util/Map;
      //   1103: aload_0
      //   1104: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   1107: invokeinterface 328 2 0
      //   1112: checkcast 330	android/os/Messenger
      //   1115: aconst_null
      //   1116: iconst_5
      //   1117: iconst_0
      //   1118: iconst_0
      //   1119: invokestatic 337	android/os/Message:obtain	(Landroid/os/Handler;III)Landroid/os/Message;
      //   1122: invokevirtual 334	android/os/Messenger:send	(Landroid/os/Message;)V
      //   1125: aload_0
      //   1126: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1129: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   1132: aload_0
      //   1133: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   1136: aload_0
      //   1137: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   1140: invokevirtual 243	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
      //   1143: aload_0
      //   1144: new 27	java/lang/Exception
      //   1147: dup
      //   1148: new 112	java/lang/StringBuilder
      //   1151: dup
      //   1152: invokespecial 113	java/lang/StringBuilder:<init>	()V
      //   1155: ldc_w 513
      //   1158: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1161: aload_0
      //   1162: getfield 34	com/taobao/munion/base/download/DownloadingService$b:e	I
      //   1165: invokevirtual 222	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   1168: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1171: invokespecial 514	java/lang/Exception:<init>	(Ljava/lang/String;)V
      //   1174: invokespecial 237	com/taobao/munion/base/download/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   1177: aload 10
      //   1179: ifnull +8 -> 1187
      //   1182: aload 10
      //   1184: invokevirtual 465	java/io/InputStream:close	()V
      //   1187: aload 9
      //   1189: ifnull -623 -> 566
      //   1192: aload 9
      //   1194: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1197: return
      //   1198: astore 48
      //   1200: aload 48
      //   1202: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1205: return
      //   1206: aload_0
      //   1207: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1210: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   1213: aload_0
      //   1214: getfield 46	com/taobao/munion/base/download/DownloadingService$b:k	Lcom/taobao/munion/base/download/c$a;
      //   1217: invokevirtual 517	com/taobao/munion/base/download/f:d	(Lcom/taobao/munion/base/download/c$a;)V
      //   1220: new 86	java/io/File
      //   1223: dup
      //   1224: aload_0
      //   1225: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   1228: invokevirtual 520	java/io/File:getParent	()Ljava/lang/String;
      //   1231: aload_0
      //   1232: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   1235: invokevirtual 385	java/io/File:getName	()Ljava/lang/String;
      //   1238: ldc_w 522
      //   1241: ldc_w 524
      //   1244: invokevirtual 140	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   1247: invokespecial 526	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   1250: astore 45
      //   1252: aload_0
      //   1253: getfield 79	com/taobao/munion/base/download/DownloadingService$b:d	Ljava/io/File;
      //   1256: aload 45
      //   1258: invokevirtual 530	java/io/File:renameTo	(Ljava/io/File;)Z
      //   1261: pop
      //   1262: aload 45
      //   1264: invokevirtual 393	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   1267: astore 47
      //   1269: aload_0
      //   1270: aload 45
      //   1272: aload 47
      //   1274: invokespecial 532	com/taobao/munion/base/download/DownloadingService$b:a	(Ljava/io/File;Ljava/lang/String;)V
      //   1277: aload_0
      //   1278: getfield 68	com/taobao/munion/base/download/DownloadingService$b:j	Lcom/taobao/munion/base/download/DownloadingService$a;
      //   1281: ifnull -104 -> 1177
      //   1284: aload_0
      //   1285: getfield 68	com/taobao/munion/base/download/DownloadingService$b:j	Lcom/taobao/munion/base/download/DownloadingService$a;
      //   1288: aload_0
      //   1289: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   1292: aload 47
      //   1294: invokeinterface 535 3 0
      //   1299: goto -122 -> 1177
      //   1302: astore 28
      //   1304: aload_0
      //   1305: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1308: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   1311: aload_0
      //   1312: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   1315: aload_0
      //   1316: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   1319: invokevirtual 243	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
      //   1322: aload 28
      //   1324: ldc_w 537
      //   1327: iconst_0
      //   1328: anewarray 95	java/lang/Object
      //   1331: invokestatic 473	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
      //   1334: aload 10
      //   1336: ifnull +8 -> 1344
      //   1339: aload 10
      //   1341: invokevirtual 465	java/io/InputStream:close	()V
      //   1344: aload 9
      //   1346: ifnull -780 -> 566
      //   1349: aload 9
      //   1351: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1354: return
      //   1355: astore 29
      //   1357: aload 29
      //   1359: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1362: return
      //   1363: astore 51
      //   1365: aload 51
      //   1367: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1370: aload 9
      //   1372: ifnull -806 -> 566
      //   1375: aload 9
      //   1377: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1380: return
      //   1381: astore 52
      //   1383: aload 52
      //   1385: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1388: return
      //   1389: astore 49
      //   1391: aload 9
      //   1393: ifnull +8 -> 1401
      //   1396: aload 9
      //   1398: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1401: aload 49
      //   1403: athrow
      //   1404: astore 50
      //   1406: aload 50
      //   1408: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1411: goto -10 -> 1401
      //   1414: astore 25
      //   1416: aload 25
      //   1418: invokevirtual 538	android/os/RemoteException:printStackTrace	()V
      //   1421: aload_0
      //   1422: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1425: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   1428: aload_0
      //   1429: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   1432: aload_0
      //   1433: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   1436: invokevirtual 243	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
      //   1439: aload_0
      //   1440: aload 6
      //   1442: invokespecial 237	com/taobao/munion/base/download/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   1445: aload_0
      //   1446: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1449: invokestatic 478	com/taobao/munion/base/download/DownloadingService:d	(Lcom/taobao/munion/base/download/DownloadingService;)Landroid/os/Handler;
      //   1452: new 480	com/taobao/munion/base/download/DownloadingService$b$1
      //   1455: dup
      //   1456: aload_0
      //   1457: invokespecial 483	com/taobao/munion/base/download/DownloadingService$b$1:<init>	(Lcom/taobao/munion/base/download/DownloadingService$b;)V
      //   1460: invokevirtual 489	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   1463: pop
      //   1464: goto -747 -> 717
      //   1467: astore 8
      //   1469: aload 5
      //   1471: astore 9
      //   1473: aload 7
      //   1475: astore 10
      //   1477: aload 10
      //   1479: ifnull +8 -> 1487
      //   1482: aload 10
      //   1484: invokevirtual 465	java/io/InputStream:close	()V
      //   1487: aload 9
      //   1489: ifnull +8 -> 1497
      //   1492: aload 9
      //   1494: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1497: aload 8
      //   1499: athrow
      //   1500: astore 23
      //   1502: aload_0
      //   1503: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1506: invokestatic 240	com/taobao/munion/base/download/DownloadingService:a	(Lcom/taobao/munion/base/download/DownloadingService;)Lcom/taobao/munion/base/download/f;
      //   1509: aload_0
      //   1510: getfield 44	com/taobao/munion/base/download/DownloadingService$b:b	Landroid/content/Context;
      //   1513: aload_0
      //   1514: getfield 70	com/taobao/munion/base/download/DownloadingService$b:i	I
      //   1517: invokevirtual 243	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
      //   1520: aload_0
      //   1521: aload 6
      //   1523: invokespecial 237	com/taobao/munion/base/download/DownloadingService$b:a	(Ljava/lang/Exception;)V
      //   1526: aload_0
      //   1527: getfield 29	com/taobao/munion/base/download/DownloadingService$b:a	Lcom/taobao/munion/base/download/DownloadingService;
      //   1530: invokestatic 478	com/taobao/munion/base/download/DownloadingService:d	(Lcom/taobao/munion/base/download/DownloadingService;)Landroid/os/Handler;
      //   1533: new 480	com/taobao/munion/base/download/DownloadingService$b$1
      //   1536: dup
      //   1537: aload_0
      //   1538: invokespecial 483	com/taobao/munion/base/download/DownloadingService$b$1:<init>	(Lcom/taobao/munion/base/download/DownloadingService$b;)V
      //   1541: invokevirtual 489	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   1544: pop
      //   1545: aload 23
      //   1547: athrow
      //   1548: aload_0
      //   1549: invokespecial 540	com/taobao/munion/base/download/DownloadingService$b:a	()V
      //   1552: goto -835 -> 717
      //   1555: astore 21
      //   1557: aload 21
      //   1559: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1562: aload 5
      //   1564: ifnull -998 -> 566
      //   1567: aload 5
      //   1569: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1572: return
      //   1573: astore 22
      //   1575: aload 22
      //   1577: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1580: return
      //   1581: astore 19
      //   1583: aload 5
      //   1585: ifnull +8 -> 1593
      //   1588: aload 5
      //   1590: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1593: aload 19
      //   1595: athrow
      //   1596: astore 20
      //   1598: aload 20
      //   1600: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1603: goto -10 -> 1593
      //   1606: astore 32
      //   1608: aload 32
      //   1610: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1613: aload 9
      //   1615: ifnull -1049 -> 566
      //   1618: aload 9
      //   1620: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1623: return
      //   1624: astore 33
      //   1626: aload 33
      //   1628: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1631: return
      //   1632: astore 30
      //   1634: aload 9
      //   1636: ifnull +8 -> 1644
      //   1639: aload 9
      //   1641: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1644: aload 30
      //   1646: athrow
      //   1647: astore 31
      //   1649: aload 31
      //   1651: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1654: goto -10 -> 1644
      //   1657: astore 11
      //   1659: aload 11
      //   1661: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1664: goto -167 -> 1497
      //   1667: astore 14
      //   1669: aload 14
      //   1671: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1674: aload 9
      //   1676: ifnull -179 -> 1497
      //   1679: aload 9
      //   1681: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1684: goto -187 -> 1497
      //   1687: astore 15
      //   1689: aload 15
      //   1691: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1694: goto -197 -> 1497
      //   1697: astore 12
      //   1699: aload 9
      //   1701: ifnull +8 -> 1709
      //   1704: aload 9
      //   1706: invokevirtual 399	java/io/FileOutputStream:close	()V
      //   1709: aload 12
      //   1711: athrow
      //   1712: astore 13
      //   1714: aload 13
      //   1716: invokevirtual 492	java/io/IOException:printStackTrace	()V
      //   1719: goto -10 -> 1709
      //   1722: astore 8
      //   1724: aconst_null
      //   1725: astore 9
      //   1727: aconst_null
      //   1728: astore 10
      //   1730: goto -253 -> 1477
      //   1733: astore 8
      //   1735: aload_3
      //   1736: astore 9
      //   1738: aconst_null
      //   1739: astore 10
      //   1741: goto -264 -> 1477
      //   1744: astore 8
      //   1746: aload 73
      //   1748: astore 9
      //   1750: aconst_null
      //   1751: astore 10
      //   1753: goto -276 -> 1477
      //   1756: astore 8
      //   1758: aconst_null
      //   1759: astore 10
      //   1761: goto -284 -> 1477
      //   1764: astore 8
      //   1766: goto -289 -> 1477
      //   1769: astore 28
      //   1771: aconst_null
      //   1772: astore 9
      //   1774: aconst_null
      //   1775: astore 10
      //   1777: goto -473 -> 1304
      //   1780: astore 28
      //   1782: aload_3
      //   1783: astore 9
      //   1785: aconst_null
      //   1786: astore 10
      //   1788: goto -484 -> 1304
      //   1791: astore 28
      //   1793: aload 73
      //   1795: astore 9
      //   1797: aconst_null
      //   1798: astore 10
      //   1800: goto -496 -> 1304
      //   1803: astore 28
      //   1805: aconst_null
      //   1806: astore 10
      //   1808: goto -504 -> 1304
      //   1811: astore 75
      //   1813: aload 75
      //   1815: astore 6
      //   1817: aconst_null
      //   1818: astore 5
      //   1820: aconst_null
      //   1821: astore 7
      //   1823: goto -1230 -> 593
      //   1826: astore 4
      //   1828: aload_3
      //   1829: astore 5
      //   1831: aload 4
      //   1833: astore 6
      //   1835: aconst_null
      //   1836: astore 7
      //   1838: goto -1245 -> 593
      //   1841: astore 74
      //   1843: aload 73
      //   1845: astore 5
      //   1847: aload 74
      //   1849: astore 6
      //   1851: aconst_null
      //   1852: astore 7
      //   1854: goto -1261 -> 593
      //   1857: iload 65
      //   1859: istore 43
      //   1861: goto -1461 -> 400
      //   1864: iconst_1
      //   1865: istore 44
      //   1867: goto -1393 -> 474
      //   1870: aload_3
      //   1871: astore 9
      //   1873: goto -1798 -> 75
      //   1876: astore 40
      //   1878: aload 40
      //   1880: astore 6
      //   1882: aload 9
      //   1884: astore 5
      //   1886: aload 10
      //   1888: astore 7
      //   1890: goto -1297 -> 593
      //
      // Exception table:
      //   from	to	target	type
      //   149	154	567	java/io/FileNotFoundException
      //   75	149	580	java/io/IOException
      //   149	154	580	java/io/IOException
      //   154	161	580	java/io/IOException
      //   569	577	580	java/io/IOException
      //   732	737	738	java/io/IOException
      //   561	566	877	java/io/IOException
      //   551	556	885	java/io/IOException
      //   897	902	903	java/io/IOException
      //   551	556	911	finally
      //   887	892	911	finally
      //   918	923	926	java/io/IOException
      //   1000	1005	1006	java/io/IOException
      //   990	995	1014	java/io/IOException
      //   1026	1031	1032	java/io/IOException
      //   990	995	1040	finally
      //   1016	1021	1040	finally
      //   1047	1052	1055	java/io/IOException
      //   1192	1197	1198	java/io/IOException
      //   172	206	1302	java/lang/Exception
      //   206	322	1302	java/lang/Exception
      //   322	397	1302	java/lang/Exception
      //   400	416	1302	java/lang/Exception
      //   421	443	1302	java/lang/Exception
      //   457	466	1302	java/lang/Exception
      //   474	507	1302	java/lang/Exception
      //   512	546	1302	java/lang/Exception
      //   746	787	1302	java/lang/Exception
      //   787	805	1302	java/lang/Exception
      //   816	838	1302	java/lang/Exception
      //   838	870	1302	java/lang/Exception
      //   936	985	1302	java/lang/Exception
      //   1070	1177	1302	java/lang/Exception
      //   1206	1299	1302	java/lang/Exception
      //   1349	1354	1355	java/io/IOException
      //   1182	1187	1363	java/io/IOException
      //   1375	1380	1381	java/io/IOException
      //   1182	1187	1389	finally
      //   1365	1370	1389	finally
      //   1396	1401	1404	java/io/IOException
      //   639	674	1414	android/os/RemoteException
      //   593	619	1467	finally
      //   625	634	1467	finally
      //   674	717	1467	finally
      //   1421	1464	1467	finally
      //   1502	1548	1467	finally
      //   1548	1552	1467	finally
      //   639	674	1500	finally
      //   1416	1421	1500	finally
      //   722	727	1555	java/io/IOException
      //   1567	1572	1573	java/io/IOException
      //   722	727	1581	finally
      //   1557	1562	1581	finally
      //   1588	1593	1596	java/io/IOException
      //   1339	1344	1606	java/io/IOException
      //   1618	1623	1624	java/io/IOException
      //   1339	1344	1632	finally
      //   1608	1613	1632	finally
      //   1639	1644	1647	java/io/IOException
      //   1492	1497	1657	java/io/IOException
      //   1482	1487	1667	java/io/IOException
      //   1679	1684	1687	java/io/IOException
      //   1482	1487	1697	finally
      //   1669	1674	1697	finally
      //   1704	1709	1712	java/io/IOException
      //   8	21	1722	finally
      //   21	59	1733	finally
      //   59	71	1744	finally
      //   75	149	1756	finally
      //   149	154	1756	finally
      //   154	161	1756	finally
      //   569	577	1756	finally
      //   172	206	1764	finally
      //   206	322	1764	finally
      //   322	397	1764	finally
      //   400	416	1764	finally
      //   421	443	1764	finally
      //   457	466	1764	finally
      //   474	507	1764	finally
      //   512	546	1764	finally
      //   746	787	1764	finally
      //   787	805	1764	finally
      //   816	838	1764	finally
      //   838	870	1764	finally
      //   936	985	1764	finally
      //   1070	1177	1764	finally
      //   1206	1299	1764	finally
      //   1304	1334	1764	finally
      //   8	21	1769	java/lang/Exception
      //   21	59	1780	java/lang/Exception
      //   59	71	1791	java/lang/Exception
      //   75	149	1803	java/lang/Exception
      //   149	154	1803	java/lang/Exception
      //   154	161	1803	java/lang/Exception
      //   569	577	1803	java/lang/Exception
      //   8	21	1811	java/io/IOException
      //   21	59	1826	java/io/IOException
      //   59	71	1841	java/io/IOException
      //   172	206	1876	java/io/IOException
      //   206	322	1876	java/io/IOException
      //   322	397	1876	java/io/IOException
      //   400	416	1876	java/io/IOException
      //   421	443	1876	java/io/IOException
      //   457	466	1876	java/io/IOException
      //   474	507	1876	java/io/IOException
      //   512	546	1876	java/io/IOException
      //   746	787	1876	java/io/IOException
      //   787	805	1876	java/io/IOException
      //   816	838	1876	java/io/IOException
      //   838	870	1876	java/io/IOException
      //   936	985	1876	java/io/IOException
      //   1070	1177	1876	java/io/IOException
      //   1206	1299	1876	java/io/IOException
    }

    private void b(int paramInt)
      throws RemoteException
    {
      try
      {
        if (DownloadingService.b().get(this.k) != null)
          ((Messenger)DownloadingService.b().get(this.k)).send(Message.obtain(null, 3, paramInt, 0));
        return;
      }
      catch (DeadObjectException localDeadObjectException)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.k.c;
        Log.e(String.format("Service Client for downloading %1$15s is dead. Removing messenger from the service", arrayOfObject), new Object[0]);
        DownloadingService.b().put(this.k, null);
      }
    }

    public void a(int paramInt)
    {
      this.h = paramInt;
    }

    public void run()
    {
      this.e = 0;
      try
      {
        if (this.j != null)
          this.j.a(this.i);
        boolean bool1 = this.f < 0L;
        boolean bool2 = false;
        if (bool1)
          bool2 = true;
        a(bool2);
        if (DownloadingService.b().size() <= 0)
          DownloadingService.this.stopSelf();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  class c extends Handler
  {
    c()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      Log.d("IncomingHandler(msg.what:" + paramMessage.what + " msg.arg1:" + paramMessage.arg1 + " msg.arg2:" + paramMessage.arg2 + " msg.replyTo:" + paramMessage.replyTo, new Object[0]);
      switch (paramMessage.what)
      {
      default:
        super.handleMessage(paramMessage);
        return;
      case 4:
      }
      Bundle localBundle = paramMessage.getData();
      Log.d("IncomingHandler(msg.getData():" + localBundle, new Object[0]);
      c.a locala = c.a.a(localBundle);
      if (DownloadingService.a(DownloadingService.this).a(locala, DownloadingService.a, paramMessage.replyTo))
      {
        Log.i(locala.c + " is already in downloading list. ", new Object[0]);
        int i = DownloadingService.a(DownloadingService.this).b(locala);
        if ((i != -1) && (((f.c)DownloadingService.a().get(i)).a == null))
        {
          String str = j.a(i, "continue");
          Intent localIntent = new Intent(DownloadingService.b(DownloadingService.this), DownloadingService.class);
          localIntent.putExtra("com.taobao.broadcast.download.msg", str);
          DownloadingService.a(DownloadingService.this).a(DownloadingService.this, localIntent);
          return;
        }
        Toast.makeText(DownloadingService.b(DownloadingService.this), k.i, 0).show();
        Message localMessage3 = Message.obtain();
        localMessage3.what = 2;
        localMessage3.arg1 = 2;
        localMessage3.arg2 = 0;
        try
        {
          paramMessage.replyTo.send(localMessage3);
          return;
        }
        catch (RemoteException localRemoteException3)
        {
          localRemoteException3.printStackTrace();
          return;
        }
      }
      if (f.b(DownloadingService.b(DownloadingService.this)))
      {
        DownloadingService.b().put(locala, paramMessage.replyTo);
        Message localMessage2 = Message.obtain();
        localMessage2.what = 1;
        localMessage2.arg1 = 1;
        localMessage2.arg2 = 0;
        try
        {
          paramMessage.replyTo.send(localMessage2);
          DownloadingService.a(DownloadingService.this, locala);
          return;
        }
        catch (RemoteException localRemoteException2)
        {
          while (true)
            localRemoteException2.printStackTrace();
        }
      }
      Toast.makeText(DownloadingService.b(DownloadingService.this), k.d, 0).show();
      Message localMessage1 = Message.obtain();
      localMessage1.what = 2;
      localMessage1.arg1 = 4;
      localMessage1.arg2 = 0;
      try
      {
        paramMessage.replyTo.send(localMessage1);
        return;
      }
      catch (RemoteException localRemoteException1)
      {
        localRemoteException1.printStackTrace();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.download.DownloadingService
 * JD-Core Version:    0.6.2
 */