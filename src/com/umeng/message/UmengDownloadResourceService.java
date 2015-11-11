package com.umeng.message;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.IBinder;
import com.umeng.common.message.Log;
import com.umeng.message.entity.UMessage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.json.JSONObject;

public class UmengDownloadResourceService extends Service
{
  public static final String TAG = UmengDownloadResourceService.class.getSimpleName();
  private static final String d = ".tmp";
  private static final String e = "RETRY_TIME";
  private static final String f = "OPERATIOIN";
  private static final int g = 1;
  private static final int h = 2;
  private static final long i = 1048576L;
  private static final long j = 86400000L;
  private static final int k = 300000;
  private static final int l = 3;
  private static Thread m;
  ScheduledThreadPoolExecutor a;
  Context b;
  ArrayList<String> c;

  private static long a(File paramFile)
  {
    long l1;
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.isDirectory()))
      l1 = 0L;
    while (true)
    {
      return l1;
      Stack localStack = new Stack();
      localStack.clear();
      localStack.push(paramFile);
      long l2;
      for (l1 = 0L; !localStack.isEmpty(); l1 = l2)
      {
        File[] arrayOfFile = ((File)localStack.pop()).listFiles();
        int n = arrayOfFile.length;
        l2 = l1;
        for (int i1 = 0; i1 < n; i1++)
        {
          File localFile = arrayOfFile[i1];
          if (!localFile.isDirectory())
            l2 += localFile.length();
        }
      }
    }
  }

  private PendingIntent a(UMessage paramUMessage, int paramInt)
  {
    String str = paramUMessage.getRaw().toString();
    int n = paramUMessage.msg_id.hashCode();
    Intent localIntent = new Intent(this.b, UmengDownloadResourceService.class);
    localIntent.putExtra("body", str);
    localIntent.putExtra("OPERATIOIN", 2);
    localIntent.putExtra("RETRY_TIME", paramInt);
    PendingIntent localPendingIntent = PendingIntent.getService(this.b, n, localIntent, 134217728);
    Log.a(TAG, "PendingIntent: msgId:" + paramUMessage.msg_id + ",requestCode:" + n + ",retryTime:" + paramInt);
    return localPendingIntent;
  }

  private static void b(File paramFile, long paramLong)
  {
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.canWrite()) || (!paramFile.isDirectory()));
    while (true)
    {
      return;
      for (File localFile : paramFile.listFiles())
        if ((!localFile.isDirectory()) && (System.currentTimeMillis() - localFile.lastModified() > paramLong))
          localFile.delete();
    }
  }

  public static void checkDir(File paramFile, long paramLong1, long paramLong2)
    throws IOException
  {
    if ((paramFile.exists()) && (a(paramFile.getCanonicalFile()) > paramLong1))
    {
      if (m == null)
        m = new Thread(new UmengDownloadResourceService.1(paramFile, paramLong2));
      synchronized (m)
      {
        m.start();
        return;
      }
    }
  }

  public static String getMessageResourceFolder(Context paramContext, UMessage paramUMessage)
  {
    String str = paramContext.getCacheDir() + "/umeng_push/";
    if ((paramUMessage != null) && (paramUMessage.msg_id != null))
      str = str + paramUMessage.msg_id + "/";
    return str;
  }

  public void checkCache()
  {
    try
    {
      checkDir(new File(getMessageResourceFolder(this.b, null)), 1048576L, 86400000L);
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  public void close(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void deleteAlarm(UMessage paramUMessage, int paramInt)
  {
    Log.a(TAG, "deleteAlarm");
    PendingIntent localPendingIntent = a(paramUMessage, paramInt);
    ((AlarmManager)getSystemService("alarm")).cancel(localPendingIntent);
  }

  public void downloadResource(UMessage paramUMessage, int paramInt)
  {
    DownloadResourceTask localDownloadResourceTask = new DownloadResourceTask(paramUMessage, paramInt);
    if (Build.VERSION.SDK_INT >= 11)
    {
      localDownloadResourceTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
      return;
    }
    localDownloadResourceTask.execute(new Void[0]);
  }

  public void notification(UMessage paramUMessage)
  {
    UHandler localUHandler = PushAgent.getInstance(this).getMessageHandler();
    if (localUHandler != null)
      localUHandler.handleMessage(this, paramUMessage);
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    this.a = new ScheduledThreadPoolExecutor(4 * Runtime.getRuntime().availableProcessors());
    this.b = this;
    this.c = new ArrayList();
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (paramIntent == null)
      return super.onStartCommand(paramIntent, paramInt1, paramInt2);
    int n = paramIntent.getIntExtra("OPERATIOIN", 2);
    int i1 = paramIntent.getIntExtra("RETRY_TIME", 3);
    String str = paramIntent.getStringExtra("body");
    try
    {
      localUMessage = new UMessage(new JSONObject(str));
      if (this.c.contains(localUMessage.msg_id))
        return super.onStartCommand(paramIntent, paramInt1, paramInt2);
      this.c.add(localUMessage.msg_id);
      switch (n)
      {
      default:
      case 2:
        while (true)
        {
          return super.onStartCommand(paramIntent, paramInt1, paramInt2);
          Log.a(TAG, "Start Download Resource");
          int i2 = i1 - 1;
          setAlarm(localUMessage, i2);
          checkCache();
          downloadResource(localUMessage, i2);
        }
      case 1:
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        UMessage localUMessage;
        localException.printStackTrace();
        continue;
        deleteAlarm(localUMessage, i1);
        Log.a(TAG, "Show Notification After Downloaded Resource");
        notification(localUMessage);
        this.c.remove(localUMessage.msg_id);
        if (this.c.size() == 0)
          stopSelf();
      }
    }
  }

  public void setAlarm(UMessage paramUMessage, int paramInt)
  {
    Log.a(TAG, "setAlarm");
    PendingIntent localPendingIntent = a(paramUMessage, paramInt);
    ((AlarmManager)getSystemService("alarm")).set(1, 300000L + System.currentTimeMillis(), localPendingIntent);
  }

  public class DownloadResourceTask extends AsyncTask<Void, Void, Boolean>
  {
    UMessage a;
    ArrayList<String> b;
    int c;

    public DownloadResourceTask(UMessage paramInt, int arg3)
    {
      this.a = paramInt;
      this.b = new ArrayList();
      if (paramInt.isLargeIconFromInternet())
        this.b.add(paramInt.img);
      if (paramInt.isSoundFromInternet())
        this.b.add(paramInt.sound);
      int i;
      this.c = i;
    }

    protected Boolean a(Void[] paramArrayOfVoid)
    {
      Iterator localIterator = this.b.iterator();
      boolean bool = true;
      while (localIterator.hasNext())
        bool &= download((String)localIterator.next());
      return Boolean.valueOf(bool);
    }

    protected void a(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      UmengDownloadResourceService.this.c.remove(this.a.msg_id);
      if ((paramBoolean.booleanValue()) || (this.c <= 0))
      {
        MessageSharedPrefs.getInstance(UmengDownloadResourceService.this.b).c(this.a.msg_id);
        str = this.a.getRaw().toString();
        localIntent = new Intent(UmengDownloadResourceService.this.b, UmengDownloadResourceService.class);
        localIntent.putExtra("body", str);
        localIntent.putExtra("OPERATIOIN", 1);
        localIntent.putExtra("RETRY_TIME", this.c);
        UmengDownloadResourceService.this.startService(localIntent);
      }
      while (UmengDownloadResourceService.this.c.size() != 0)
      {
        String str;
        Intent localIntent;
        return;
      }
      UmengDownloadResourceService.this.stopSelf();
    }

    // ERROR //
    public boolean download(String paramString)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_2
      //   2: aload_1
      //   3: invokestatic 159	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   6: ifeq +5 -> 11
      //   9: iconst_1
      //   10: ireturn
      //   11: new 161	java/lang/StringBuilder
      //   14: dup
      //   15: invokespecial 162	java/lang/StringBuilder:<init>	()V
      //   18: aload_1
      //   19: invokevirtual 165	java/lang/String:hashCode	()I
      //   22: invokevirtual 169	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   25: ldc 171
      //   27: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   30: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   33: astore 7
      //   35: aload_0
      //   36: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   39: getfield 97	com/umeng/message/UmengDownloadResourceService:b	Landroid/content/Context;
      //   42: aload_0
      //   43: getfield 23	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:a	Lcom/umeng/message/entity/UMessage;
      //   46: invokestatic 179	com/umeng/message/UmengDownloadResourceService:getMessageResourceFolder	(Landroid/content/Context;Lcom/umeng/message/entity/UMessage;)Ljava/lang/String;
      //   49: astore 8
      //   51: new 181	java/io/File
      //   54: dup
      //   55: aload 8
      //   57: new 161	java/lang/StringBuilder
      //   60: dup
      //   61: invokespecial 162	java/lang/StringBuilder:<init>	()V
      //   64: aload 7
      //   66: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   69: ldc 183
      //   71: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   74: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   77: invokespecial 186	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   80: astore 9
      //   82: new 181	java/io/File
      //   85: dup
      //   86: aload 8
      //   88: aload 7
      //   90: invokespecial 186	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   93: astore 10
      //   95: aload 10
      //   97: invokevirtual 189	java/io/File:exists	()Z
      //   100: istore 11
      //   102: iload 11
      //   104: ifeq +21 -> 125
      //   107: aload_0
      //   108: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   111: aconst_null
      //   112: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   115: aload_0
      //   116: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   119: aconst_null
      //   120: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   123: iconst_1
      //   124: ireturn
      //   125: new 181	java/io/File
      //   128: dup
      //   129: aload 8
      //   131: invokespecial 195	java/io/File:<init>	(Ljava/lang/String;)V
      //   134: astore 12
      //   136: aload 12
      //   138: invokevirtual 189	java/io/File:exists	()Z
      //   141: ifne +9 -> 150
      //   144: aload 12
      //   146: invokevirtual 198	java/io/File:mkdirs	()Z
      //   149: pop
      //   150: aload 9
      //   152: invokevirtual 189	java/io/File:exists	()Z
      //   155: ifeq +9 -> 164
      //   158: aload 9
      //   160: invokevirtual 201	java/io/File:delete	()Z
      //   163: pop
      //   164: new 203	java/net/URL
      //   167: dup
      //   168: new 205	java/net/URI
      //   171: dup
      //   172: aload_1
      //   173: invokespecial 206	java/net/URI:<init>	(Ljava/lang/String;)V
      //   176: invokevirtual 209	java/net/URI:toASCIIString	()Ljava/lang/String;
      //   179: invokespecial 210	java/net/URL:<init>	(Ljava/lang/String;)V
      //   182: invokevirtual 214	java/net/URL:openStream	()Ljava/io/InputStream;
      //   185: astore 13
      //   187: aload 13
      //   189: astore 6
      //   191: new 216	java/io/FileOutputStream
      //   194: dup
      //   195: aload 9
      //   197: invokespecial 219	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   200: astore 14
      //   202: sipush 10240
      //   205: newarray byte
      //   207: astore 15
      //   209: aload 6
      //   211: aload 15
      //   213: invokevirtual 225	java/io/InputStream:read	([B)I
      //   216: istore 16
      //   218: iload 16
      //   220: ifle +47 -> 267
      //   223: aload 14
      //   225: aload 15
      //   227: iconst_0
      //   228: iload 16
      //   230: invokevirtual 229	java/io/FileOutputStream:write	([BII)V
      //   233: goto -24 -> 209
      //   236: astore_3
      //   237: aload 14
      //   239: astore_2
      //   240: aload 6
      //   242: astore 4
      //   244: aload_3
      //   245: invokevirtual 232	java/lang/Exception:printStackTrace	()V
      //   248: aload_0
      //   249: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   252: aload 4
      //   254: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   257: aload_0
      //   258: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   261: aload_2
      //   262: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   265: iconst_0
      //   266: ireturn
      //   267: aload 9
      //   269: aload 10
      //   271: invokevirtual 236	java/io/File:renameTo	(Ljava/io/File;)Z
      //   274: pop
      //   275: aload_0
      //   276: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   279: aload 6
      //   281: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   284: aload_0
      //   285: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   288: aload 14
      //   290: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   293: iconst_1
      //   294: ireturn
      //   295: astore 5
      //   297: aconst_null
      //   298: astore 6
      //   300: aload_0
      //   301: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   304: aload 6
      //   306: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   309: aload_0
      //   310: getfield 18	com/umeng/message/UmengDownloadResourceService$DownloadResourceTask:d	Lcom/umeng/message/UmengDownloadResourceService;
      //   313: aload_2
      //   314: invokevirtual 193	com/umeng/message/UmengDownloadResourceService:close	(Ljava/io/Closeable;)V
      //   317: aload 5
      //   319: athrow
      //   320: astore 5
      //   322: aconst_null
      //   323: astore_2
      //   324: goto -24 -> 300
      //   327: astore 5
      //   329: aload 14
      //   331: astore_2
      //   332: goto -32 -> 300
      //   335: astore 5
      //   337: aload 4
      //   339: astore 6
      //   341: goto -41 -> 300
      //   344: astore_3
      //   345: aconst_null
      //   346: astore_2
      //   347: aconst_null
      //   348: astore 4
      //   350: goto -106 -> 244
      //   353: astore_3
      //   354: aload 6
      //   356: astore 4
      //   358: aconst_null
      //   359: astore_2
      //   360: goto -116 -> 244
      //
      // Exception table:
      //   from	to	target	type
      //   202	209	236	java/lang/Exception
      //   209	218	236	java/lang/Exception
      //   223	233	236	java/lang/Exception
      //   267	275	236	java/lang/Exception
      //   11	102	295	finally
      //   125	150	295	finally
      //   150	164	295	finally
      //   164	187	295	finally
      //   191	202	320	finally
      //   202	209	327	finally
      //   209	218	327	finally
      //   223	233	327	finally
      //   267	275	327	finally
      //   244	248	335	finally
      //   11	102	344	java/lang/Exception
      //   125	150	344	java/lang/Exception
      //   150	164	344	java/lang/Exception
      //   164	187	344	java/lang/Exception
      //   191	202	353	java/lang/Exception
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UmengDownloadResourceService
 * JD-Core Version:    0.6.2
 */