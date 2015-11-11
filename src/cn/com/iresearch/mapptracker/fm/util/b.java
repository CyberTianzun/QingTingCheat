package cn.com.iresearch.mapptracker.fm.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.IRMonitor;
import cn.com.iresearch.mapptracker.fm.a.a;
import cn.com.iresearch.mapptracker.fm.dao.SessionInfo;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public final class b
{
  private a a;
  private Context b;

  public b(a parama, Context paramContext)
  {
    this.a = parama;
    this.b = paramContext;
  }

  private static String a()
  {
    return 5L * (63529L + Long.parseLong(b())) + (int)Math.round(10000.0D + 89999.0D * Math.random());
  }

  private static StringBuilder a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
    localStringBuilder.append(File.separator);
    localStringBuilder.append("ickeck");
    localStringBuilder.append(File.separator);
    localStringBuilder.append(paramString);
    return localStringBuilder;
  }

  private static List<String> a(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    PackageManager localPackageManager = paramContext.getPackageManager();
    List localList = b(paramContext);
    try
    {
      Iterator localIterator = localActivityManager.getRecentTasks(100, 0).iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return localArrayList;
        ResolveInfo localResolveInfo = localPackageManager.resolveActivity(((ActivityManager.RecentTaskInfo)localIterator.next()).baseIntent, 0);
        if (localResolveInfo != null)
        {
          String str = localResolveInfo.activityInfo.packageName;
          if ((!localList.contains(str)) && (!localArrayList.contains(str)))
            localArrayList.add(str);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localArrayList;
  }

  private static List<String> a(List<String> paramList, int paramInt)
  {
    int i = paramList.size();
    Object localObject;
    if ((paramList == null) || (i == 0))
      localObject = null;
    do
    {
      return localObject;
      localObject = new ArrayList();
    }
    while (paramInt > i);
    return paramList.subList(0, paramInt);
  }

  private static void a(String paramString, List<String> paramList)
  {
    if ((paramList == null) || (!paramList.contains(paramString)))
    {
      Log.i("ACHECKTAG", "alist:" + paramString);
      a(paramString, a("alist").toString(), true);
    }
  }

  private static void a(List<String> paramList)
  {
    int i;
    if (paramList != null)
    {
      a("", a("atemp").toString(), false);
      i = 0;
      if (i < paramList.size());
    }
    else
    {
      return;
    }
    Object localObject = (String)paramList.get(i);
    String str = localObject + "\n";
    if (i == -1 + paramList.size());
    while (true)
    {
      a((String)localObject, a("atemp").toString(), true);
      i++;
      break;
      localObject = str;
    }
  }

  private void a(List<String> paramList1, List<String> paramList2)
  {
    SessionInfo localSessionInfo;
    int i;
    if (paramList1 != null)
    {
      localSessionInfo = new SessionInfo();
      localSessionInfo.setEnd_time(Long.valueOf(Long.parseLong(c(this.b))).longValue());
      localSessionInfo.setInapp(3L);
      i = 0;
      if (i < paramList1.size());
    }
    else
    {
      return;
    }
    Object localObject = (String)paramList1.get(i);
    String str2;
    if (!paramList2.contains(localObject))
    {
      String str1 = "inapp=3 and page_name='" + (String)localObject + "'";
      if ((this.a.a(SessionInfo.class, str1) == null) || (paramList2.isEmpty()))
      {
        long l = (int)Math.round(10.0D + 90.0D * Math.random());
        localSessionInfo.setPage_name((String)localObject);
        localSessionInfo.setSessionid(a());
        localSessionInfo.setDuration(l);
        localSessionInfo.setStart_time(l + System.currentTimeMillis() / 1000L);
        this.a.a(localSessionInfo);
        str2 = localObject + "\n";
        if (i != -1 + paramList1.size())
          break label231;
      }
    }
    while (true)
    {
      a((String)localObject, paramList2);
      i++;
      break;
      label231: localObject = str2;
    }
  }

  // ERROR //
  private static boolean a(String paramString1, String paramString2, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: iconst_1
    //   3: istore 4
    //   5: new 72	java/io/File
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 267	java/io/File:<init>	(Ljava/lang/String;)V
    //   13: astore 5
    //   15: aload 5
    //   17: invokevirtual 270	java/io/File:getParentFile	()Ljava/io/File;
    //   20: invokevirtual 273	java/io/File:exists	()Z
    //   23: ifne +12 -> 35
    //   26: aload 5
    //   28: invokevirtual 270	java/io/File:getParentFile	()Ljava/io/File;
    //   31: invokevirtual 276	java/io/File:mkdirs	()Z
    //   34: pop
    //   35: aload 5
    //   37: invokevirtual 270	java/io/File:getParentFile	()Ljava/io/File;
    //   40: invokevirtual 273	java/io/File:exists	()Z
    //   43: ifne +5 -> 48
    //   46: iconst_0
    //   47: ireturn
    //   48: new 278	java/io/BufferedReader
    //   51: dup
    //   52: new 280	java/io/StringReader
    //   55: dup
    //   56: aload_0
    //   57: invokespecial 281	java/io/StringReader:<init>	(Ljava/lang/String;)V
    //   60: invokespecial 284	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   63: astore 9
    //   65: new 286	java/io/BufferedWriter
    //   68: dup
    //   69: new 288	java/io/FileWriter
    //   72: dup
    //   73: aload 5
    //   75: iload_2
    //   76: invokespecial 291	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
    //   79: invokespecial 294	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   82: astore 15
    //   84: sipush 1024
    //   87: newarray char
    //   89: astore 16
    //   91: aload 9
    //   93: aload 16
    //   95: invokevirtual 298	java/io/BufferedReader:read	([C)I
    //   98: istore 17
    //   100: iload 17
    //   102: iconst_m1
    //   103: if_icmpne +47 -> 150
    //   106: aload 15
    //   108: invokevirtual 301	java/io/BufferedWriter:flush	()V
    //   111: aload 9
    //   113: invokevirtual 304	java/io/BufferedReader:close	()V
    //   116: aload 15
    //   118: invokevirtual 305	java/io/BufferedWriter:close	()V
    //   121: new 20	java/lang/StringBuilder
    //   124: dup
    //   125: iload 4
    //   127: invokestatic 308	java/lang/String:valueOf	(Z)Ljava/lang/String;
    //   130: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   133: ldc_w 310
    //   136: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: aload_1
    //   140: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   146: pop
    //   147: iload 4
    //   149: ireturn
    //   150: aload 15
    //   152: aload 16
    //   154: iconst_0
    //   155: iload 17
    //   157: invokevirtual 314	java/io/BufferedWriter:write	([CII)V
    //   160: goto -69 -> 91
    //   163: astore 6
    //   165: aload 15
    //   167: astore_3
    //   168: aload 9
    //   170: astore 7
    //   172: aload 6
    //   174: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   177: aload 7
    //   179: ifnull +8 -> 187
    //   182: aload 7
    //   184: invokevirtual 304	java/io/BufferedReader:close	()V
    //   187: aload_3
    //   188: ifnull +132 -> 320
    //   191: aload_3
    //   192: invokevirtual 305	java/io/BufferedWriter:close	()V
    //   195: iconst_0
    //   196: istore 4
    //   198: goto -77 -> 121
    //   201: astore 13
    //   203: aload 13
    //   205: invokevirtual 315	java/io/IOException:printStackTrace	()V
    //   208: iconst_0
    //   209: istore 4
    //   211: goto -90 -> 121
    //   214: astore 8
    //   216: aconst_null
    //   217: astore 9
    //   219: aload 9
    //   221: ifnull +8 -> 229
    //   224: aload 9
    //   226: invokevirtual 304	java/io/BufferedReader:close	()V
    //   229: aload_3
    //   230: ifnull +7 -> 237
    //   233: aload_3
    //   234: invokevirtual 305	java/io/BufferedWriter:close	()V
    //   237: aload 8
    //   239: athrow
    //   240: astore 10
    //   242: aload 10
    //   244: invokevirtual 315	java/io/IOException:printStackTrace	()V
    //   247: goto -10 -> 237
    //   250: astore 19
    //   252: aload 19
    //   254: invokevirtual 315	java/io/IOException:printStackTrace	()V
    //   257: goto -136 -> 121
    //   260: astore 14
    //   262: goto -75 -> 187
    //   265: astore 11
    //   267: goto -38 -> 229
    //   270: astore 18
    //   272: goto -156 -> 116
    //   275: astore 8
    //   277: aconst_null
    //   278: astore_3
    //   279: goto -60 -> 219
    //   282: astore 8
    //   284: aload 15
    //   286: astore_3
    //   287: goto -68 -> 219
    //   290: astore 8
    //   292: aload 7
    //   294: astore 9
    //   296: goto -77 -> 219
    //   299: astore 6
    //   301: aconst_null
    //   302: astore_3
    //   303: aconst_null
    //   304: astore 7
    //   306: goto -134 -> 172
    //   309: astore 6
    //   311: aload 9
    //   313: astore 7
    //   315: aconst_null
    //   316: astore_3
    //   317: goto -145 -> 172
    //   320: iconst_0
    //   321: istore 4
    //   323: goto -202 -> 121
    //
    // Exception table:
    //   from	to	target	type
    //   84	91	163	java/lang/Exception
    //   91	100	163	java/lang/Exception
    //   106	111	163	java/lang/Exception
    //   150	160	163	java/lang/Exception
    //   191	195	201	java/io/IOException
    //   5	35	214	finally
    //   35	46	214	finally
    //   48	65	214	finally
    //   233	237	240	java/io/IOException
    //   116	121	250	java/io/IOException
    //   182	187	260	java/io/IOException
    //   224	229	265	java/io/IOException
    //   111	116	270	java/io/IOException
    //   65	84	275	finally
    //   84	91	282	finally
    //   91	100	282	finally
    //   106	111	282	finally
    //   150	160	282	finally
    //   172	177	290	finally
    //   5	35	299	java/lang/Exception
    //   35	46	299	java/lang/Exception
    //   48	65	299	java/lang/Exception
    //   65	84	309	java/lang/Exception
  }

  @SuppressLint({"SimpleDateFormat"})
  private static String b()
  {
    try
    {
      Date localDate = new Date(System.currentTimeMillis());
      String str = new SimpleDateFormat("yyyyMMdd").format(localDate);
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private static List<String> b(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    PackageManager localPackageManager = paramContext.getPackageManager();
    Intent localIntent = new Intent("android.intent.action.MAIN");
    localIntent.addCategory("android.intent.category.HOME");
    Iterator localIterator = localPackageManager.queryIntentActivities(localIntent, 65536).iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localArrayList;
      localArrayList.add(((ResolveInfo)localIterator.next()).activityInfo.packageName);
    }
  }

  private static String c(Context paramContext)
  {
    label129: 
    while (true)
    {
      int i;
      String str;
      int k;
      try
      {
        if (paramContext.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", paramContext.getPackageName()) != 0)
          break label124;
        i = 1;
        if (i != 0)
        {
          str = "";
          int j = paramContext.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", paramContext.getPackageName());
          k = 0;
          if (j == 0)
            break label129;
          if (k != 0)
          {
            str = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
            break label116;
            if (IRMonitor.c)
              Log.e("MAT_SESSION", "deviceId is null");
            return "";
          }
        }
        else
        {
          if (IRMonitor.c)
            Log.e("MAT_SESSION", "lost----->android.permission.READ_PHONE_STATE");
          return "";
        }
      }
      catch (Exception localException)
      {
        return "";
      }
      label116: if (str != null)
      {
        return str;
        label124: i = 0;
        continue;
        k = 1;
      }
    }
  }

  // ERROR //
  private static List<String> c()
  {
    // Byte code:
    //   0: new 72	java/io/File
    //   3: dup
    //   4: ldc 189
    //   6: invokestatic 181	cn/com/iresearch/mapptracker/fm/util/b:a	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   9: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   12: invokespecial 267	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: astore_0
    //   16: aconst_null
    //   17: astore_1
    //   18: new 88	java/util/ArrayList
    //   21: dup
    //   22: invokespecial 89	java/util/ArrayList:<init>	()V
    //   25: astore_2
    //   26: new 278	java/io/BufferedReader
    //   29: dup
    //   30: new 379	java/io/FileReader
    //   33: dup
    //   34: aload_0
    //   35: invokespecial 382	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   38: invokespecial 284	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   41: astore_3
    //   42: aload_3
    //   43: invokevirtual 385	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   46: astore 10
    //   48: aload 10
    //   50: ifnonnull +31 -> 81
    //   53: aload_3
    //   54: invokevirtual 304	java/io/BufferedReader:close	()V
    //   57: aload_3
    //   58: invokevirtual 304	java/io/BufferedReader:close	()V
    //   61: new 20	java/lang/StringBuilder
    //   64: dup
    //   65: ldc_w 387
    //   68: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   71: aload_2
    //   72: invokevirtual 390	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   75: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   78: pop
    //   79: aload_2
    //   80: areturn
    //   81: aload_2
    //   82: aload 10
    //   84: invokeinterface 152 2 0
    //   89: ifne -47 -> 42
    //   92: aload_2
    //   93: aload 10
    //   95: invokeinterface 155 2 0
    //   100: pop
    //   101: goto -59 -> 42
    //   104: astore 7
    //   106: aload_3
    //   107: ifnull -46 -> 61
    //   110: aload_3
    //   111: invokevirtual 304	java/io/BufferedReader:close	()V
    //   114: goto -53 -> 61
    //   117: astore 8
    //   119: goto -58 -> 61
    //   122: astore 5
    //   124: aload_1
    //   125: ifnull +7 -> 132
    //   128: aload_1
    //   129: invokevirtual 304	java/io/BufferedReader:close	()V
    //   132: aload 5
    //   134: athrow
    //   135: astore 6
    //   137: goto -5 -> 132
    //   140: astore 11
    //   142: goto -81 -> 61
    //   145: astore 4
    //   147: aload_3
    //   148: astore_1
    //   149: aload 4
    //   151: astore 5
    //   153: goto -29 -> 124
    //   156: astore 13
    //   158: aconst_null
    //   159: astore_3
    //   160: goto -54 -> 106
    //
    // Exception table:
    //   from	to	target	type
    //   42	48	104	java/io/IOException
    //   53	57	104	java/io/IOException
    //   81	101	104	java/io/IOException
    //   110	114	117	java/io/IOException
    //   26	42	122	finally
    //   128	132	135	java/io/IOException
    //   57	61	140	java/io/IOException
    //   42	48	145	finally
    //   53	57	145	finally
    //   81	101	145	finally
    //   26	42	156	java/io/IOException
  }

  // ERROR //
  private static List<String> d()
  {
    // Byte code:
    //   0: new 72	java/io/File
    //   3: dup
    //   4: ldc 179
    //   6: invokestatic 181	cn/com/iresearch/mapptracker/fm/util/b:a	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   9: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   12: invokespecial 267	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: astore_0
    //   16: aconst_null
    //   17: astore_1
    //   18: new 88	java/util/ArrayList
    //   21: dup
    //   22: invokespecial 89	java/util/ArrayList:<init>	()V
    //   25: astore_2
    //   26: new 278	java/io/BufferedReader
    //   29: dup
    //   30: new 379	java/io/FileReader
    //   33: dup
    //   34: aload_0
    //   35: invokespecial 382	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   38: invokespecial 284	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   41: astore_3
    //   42: aload_3
    //   43: invokevirtual 385	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   46: astore 9
    //   48: aload 9
    //   50: ifnonnull +13 -> 63
    //   53: aload_3
    //   54: invokevirtual 304	java/io/BufferedReader:close	()V
    //   57: aload_3
    //   58: invokevirtual 304	java/io/BufferedReader:close	()V
    //   61: aload_2
    //   62: areturn
    //   63: aload_2
    //   64: aload 9
    //   66: invokeinterface 152 2 0
    //   71: ifne -29 -> 42
    //   74: aload_2
    //   75: aload 9
    //   77: invokeinterface 155 2 0
    //   82: pop
    //   83: goto -41 -> 42
    //   86: astore 7
    //   88: aload_3
    //   89: ifnull -28 -> 61
    //   92: aload_3
    //   93: invokevirtual 304	java/io/BufferedReader:close	()V
    //   96: aload_2
    //   97: areturn
    //   98: astore 8
    //   100: aload_2
    //   101: areturn
    //   102: astore 5
    //   104: aload_1
    //   105: ifnull +7 -> 112
    //   108: aload_1
    //   109: invokevirtual 304	java/io/BufferedReader:close	()V
    //   112: aload 5
    //   114: athrow
    //   115: astore 6
    //   117: goto -5 -> 112
    //   120: astore 10
    //   122: aload_2
    //   123: areturn
    //   124: astore 4
    //   126: aload_3
    //   127: astore_1
    //   128: aload 4
    //   130: astore 5
    //   132: goto -28 -> 104
    //   135: astore 12
    //   137: aconst_null
    //   138: astore_3
    //   139: goto -51 -> 88
    //
    // Exception table:
    //   from	to	target	type
    //   42	48	86	java/io/IOException
    //   53	57	86	java/io/IOException
    //   63	83	86	java/io/IOException
    //   92	96	98	java/io/IOException
    //   26	42	102	finally
    //   108	112	115	java/io/IOException
    //   57	61	120	java/io/IOException
    //   42	48	124	finally
    //   53	57	124	finally
    //   63	83	124	finally
    //   26	42	135	java/io/IOException
  }

  public final void a(SessionInfo paramSessionInfo)
  {
    int j;
    Object localObject1;
    int k;
    Object localObject2;
    int m;
    try
    {
      List localList1 = c();
      List localList2 = d();
      List localList3 = a(this.b);
      paramSessionInfo.setEnd_time(Long.valueOf(Long.parseLong(c(this.b))).longValue());
      paramSessionInfo.setInapp(3L);
      int i = localList3.size();
      j = 0;
      localObject1 = new ArrayList();
      boolean bool = localList1.isEmpty();
      k = 0;
      if (k >= i)
      {
        a((List)localObject1, localList2);
        a(localList3);
        return;
      }
      String str1 = (String)localList3.get(k);
      if (bool)
      {
        long l1 = (int)Math.round(10.0D + 90.0D * Math.random());
        paramSessionInfo.setPage_name(str1);
        paramSessionInfo.setSessionid(a());
        paramSessionInfo.setDuration(l1);
        paramSessionInfo.setStart_time(l1 + System.currentTimeMillis() / 1000L);
        this.a.a(paramSessionInfo);
        a(str1 + "\n", localList2);
        new StringBuilder("no savetemp save:").append(str1).toString();
        localObject2 = localObject1;
        m = j;
      }
      else if ((!localList1.contains(str1)) && (!localList2.contains(str1)))
      {
        new StringBuilder("find new open app:").append(str1).toString();
        long l2 = (int)Math.round(10.0D + 90.0D * Math.random());
        paramSessionInfo.setPage_name(str1);
        paramSessionInfo.setSessionid(a());
        paramSessionInfo.setDuration(l2);
        paramSessionInfo.setStart_time(l2 + System.currentTimeMillis() / 1000L);
        this.a.a(paramSessionInfo);
        a(str1 + "\n", localList2);
        localObject2 = localObject1;
        m = j;
      }
      else
      {
        int n = localList1.indexOf(str1);
        if (n <= k)
        {
          localObject2 = localObject1;
          m = 0;
        }
        else
        {
          List localList4 = a(localList3, k);
          if ((j != 0) && (k - 1 >= 0))
          {
            String str2 = (String)localList3.get(k - 1);
            if (localList1.indexOf(str2) > n)
            {
              new StringBuilder("n(").append(str1).append(")...n-1(").append(str2).append(") = ba").toString();
              localObject2 = a(localList3, k);
            }
            else
            {
              new StringBuilder("n(").append(str1).append(")...n-1(").append(str2).append(") = ab").toString();
            }
          }
          else
          {
            localObject2 = localList4;
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    while (true)
    {
      k++;
      j = m;
      localObject1 = localObject2;
      break;
      m = 1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.util.b
 * JD-Core Version:    0.6.2
 */