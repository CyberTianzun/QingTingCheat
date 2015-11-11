package com.talkingdata.pingan.sdk;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import java.io.File;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class j
{
  private static final int a = 3600000;

  private static int a(String paramString)
  {
    String str = "";
    try
    {
      Matcher localMatcher = Pattern.compile("([0-9]+)").matcher(paramString);
      if (localMatcher.find())
        str = localMatcher.toMatchResult().group(0);
      int i = Integer.valueOf(str).intValue();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public static String a()
  {
    return "Android+" + Build.VERSION.RELEASE;
  }

  public static String a(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    if (localDisplayMetrics != null)
    {
      int i = localDisplayMetrics.widthPixels;
      int j = localDisplayMetrics.heightPixels;
      return Math.min(i, j) + "*" + Math.max(i, j) + "*" + localDisplayMetrics.densityDpi;
    }
    return "";
  }

  private static String a(String paramString1, String paramString2)
  {
    String str = paramString1.toLowerCase();
    if ((str.startsWith("unknown")) || (str.startsWith("alps")) || (str.startsWith("android")) || (str.startsWith("sprd")) || (str.startsWith("spreadtrum")) || (str.startsWith("rockchip")) || (str.startsWith("wondermedia")) || (str.startsWith("mtk")) || (str.startsWith("mt65")) || (str.startsWith("nvidia")) || (str.startsWith("brcm")) || (str.startsWith("marvell")) || (paramString2.toLowerCase().contains(str)))
      paramString1 = null;
    return paramString1;
  }

  public static int b()
  {
    return TimeZone.getDefault().getRawOffset() / 3600000;
  }

  public static String b(Context paramContext)
  {
    if (!aa.a(paramContext, "android.permission.READ_PHONE_STATE"))
      return "";
    return ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkOperatorName();
  }

  // ERROR //
  private static String b(String paramString)
  {
    // Byte code:
    //   0: ldc 16
    //   2: astore_1
    //   3: new 184	java/io/FileReader
    //   6: dup
    //   7: aload_0
    //   8: invokespecial 187	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   11: astore_2
    //   12: sipush 1024
    //   15: newarray char
    //   17: astore 5
    //   19: new 189	java/io/BufferedReader
    //   22: dup
    //   23: aload_2
    //   24: sipush 1024
    //   27: invokespecial 192	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   30: astore 6
    //   32: aload 6
    //   34: aload 5
    //   36: iconst_0
    //   37: sipush 1024
    //   40: invokevirtual 196	java/io/BufferedReader:read	([CII)I
    //   43: istore 7
    //   45: iconst_m1
    //   46: iload 7
    //   48: if_icmpeq +36 -> 84
    //   51: new 57	java/lang/StringBuilder
    //   54: dup
    //   55: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   58: aload_1
    //   59: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: new 114	java/lang/String
    //   65: dup
    //   66: aload 5
    //   68: iconst_0
    //   69: iload 7
    //   71: invokespecial 199	java/lang/String:<init>	([CII)V
    //   74: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: invokevirtual 73	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: astore_1
    //   81: goto -49 -> 32
    //   84: aload 6
    //   86: invokevirtual 202	java/io/BufferedReader:close	()V
    //   89: aload_2
    //   90: invokevirtual 203	java/io/FileReader:close	()V
    //   93: aload_1
    //   94: areturn
    //   95: astore 4
    //   97: aload_1
    //   98: areturn
    //   99: astore_3
    //   100: aload_1
    //   101: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   3	12	95	java/io/FileNotFoundException
    //   12	32	95	java/io/FileNotFoundException
    //   32	45	95	java/io/FileNotFoundException
    //   51	81	95	java/io/FileNotFoundException
    //   84	93	95	java/io/FileNotFoundException
    //   12	32	99	java/io/IOException
    //   32	45	99	java/io/IOException
    //   51	81	99	java/io/IOException
    //   84	93	99	java/io/IOException
  }

  public static String c()
  {
    String str1 = Build.MODEL.trim();
    String str2 = a(Build.MANUFACTURER.trim(), str1);
    if (TextUtils.isEmpty(str2))
      str2 = a(Build.BRAND.trim(), str1);
    if (str2 == null)
      str2 = "";
    return str2 + ":" + str1;
  }

  public static String c(Context paramContext)
  {
    if (!aa.a(paramContext, "android.permission.READ_PHONE_STATE"))
      return "";
    return ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkOperator();
  }

  public static int d()
  {
    return Build.VERSION.SDK_INT;
  }

  public static String d(Context paramContext)
  {
    if (!aa.a(paramContext, "android.permission.READ_PHONE_STATE"))
      return "";
    return ((TelephonyManager)paramContext.getSystemService("phone")).getSimOperator();
  }

  public static int e(Context paramContext)
  {
    if (!aa.a(paramContext, "android.permission.ACCESS_COARSE_LOCATION"))
      return -1;
    CellLocation localCellLocation = ((TelephonyManager)paramContext.getSystemService("phone")).getCellLocation();
    if ((localCellLocation instanceof GsmCellLocation))
      return ((GsmCellLocation)localCellLocation).getCid();
    return -1;
  }

  public static String e()
  {
    return Build.VERSION.RELEASE;
  }

  public static String f()
  {
    return Locale.getDefault().getLanguage();
  }

  public static String g()
  {
    return Locale.getDefault().getCountry();
  }

  // ERROR //
  public static String[] h()
  {
    // Byte code:
    //   0: iconst_4
    //   1: anewarray 114	java/lang/String
    //   4: astore_0
    //   5: iconst_0
    //   6: istore_1
    //   7: iload_1
    //   8: iconst_4
    //   9: if_icmpge +14 -> 23
    //   12: aload_0
    //   13: iload_1
    //   14: ldc 16
    //   16: aastore
    //   17: iinc 1 1
    //   20: goto -13 -> 7
    //   23: new 268	java/util/ArrayList
    //   26: dup
    //   27: invokespecial 269	java/util/ArrayList:<init>	()V
    //   30: astore_2
    //   31: new 184	java/io/FileReader
    //   34: dup
    //   35: ldc_w 271
    //   38: invokespecial 187	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   41: astore_3
    //   42: new 189	java/io/BufferedReader
    //   45: dup
    //   46: aload_3
    //   47: sipush 1024
    //   50: invokespecial 192	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   53: astore 4
    //   55: aload 4
    //   57: invokevirtual 274	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   60: astore 17
    //   62: aload 17
    //   64: ifnull +142 -> 206
    //   67: aload_2
    //   68: aload 17
    //   70: invokeinterface 280 2 0
    //   75: pop
    //   76: goto -21 -> 55
    //   79: astore 15
    //   81: aload 4
    //   83: invokevirtual 202	java/io/BufferedReader:close	()V
    //   86: aload_3
    //   87: invokevirtual 203	java/io/FileReader:close	()V
    //   90: iconst_0
    //   91: istore 8
    //   93: iconst_3
    //   94: anewarray 114	java/lang/String
    //   97: dup
    //   98: iconst_0
    //   99: ldc_w 282
    //   102: aastore
    //   103: dup
    //   104: iconst_1
    //   105: ldc_w 284
    //   108: aastore
    //   109: dup
    //   110: iconst_2
    //   111: ldc_w 286
    //   114: aastore
    //   115: astore 9
    //   117: iload 8
    //   119: ifeq +146 -> 265
    //   122: aload_2
    //   123: invokeinterface 289 1 0
    //   128: istore 10
    //   130: iconst_0
    //   131: istore 11
    //   133: iload 11
    //   135: iconst_3
    //   136: if_icmpge +129 -> 265
    //   139: aload 9
    //   141: iload 11
    //   143: aaload
    //   144: invokestatic 24	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   147: astore 12
    //   149: iconst_0
    //   150: istore 13
    //   152: iload 13
    //   154: iload 10
    //   156: if_icmpge +103 -> 259
    //   159: aload 12
    //   161: aload_2
    //   162: iload 13
    //   164: invokeinterface 293 2 0
    //   169: checkcast 114	java/lang/String
    //   172: invokevirtual 28	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   175: astore 14
    //   177: aload 14
    //   179: invokevirtual 34	java/util/regex/Matcher:find	()Z
    //   182: ifeq +18 -> 200
    //   185: aload_0
    //   186: iload 11
    //   188: aload 14
    //   190: invokevirtual 38	java/util/regex/Matcher:toMatchResult	()Ljava/util/regex/MatchResult;
    //   193: iconst_1
    //   194: invokeinterface 44 2 0
    //   199: aastore
    //   200: iinc 13 1
    //   203: goto -51 -> 152
    //   206: aload 4
    //   208: invokevirtual 202	java/io/BufferedReader:close	()V
    //   211: aload_3
    //   212: invokevirtual 203	java/io/FileReader:close	()V
    //   215: iconst_1
    //   216: istore 8
    //   218: goto -125 -> 93
    //   221: astore 20
    //   223: iconst_1
    //   224: istore 8
    //   226: goto -133 -> 93
    //   229: astore 16
    //   231: iconst_0
    //   232: istore 8
    //   234: goto -141 -> 93
    //   237: astore 5
    //   239: aload 4
    //   241: invokevirtual 202	java/io/BufferedReader:close	()V
    //   244: aload_3
    //   245: invokevirtual 203	java/io/FileReader:close	()V
    //   248: aload 5
    //   250: athrow
    //   251: astore 7
    //   253: iconst_0
    //   254: istore 8
    //   256: goto -163 -> 93
    //   259: iinc 11 1
    //   262: goto -129 -> 133
    //   265: aload_0
    //   266: iconst_3
    //   267: ldc_w 295
    //   270: invokestatic 297	com/talkingdata/pingan/sdk/j:b	(Ljava/lang/String;)Ljava/lang/String;
    //   273: aastore
    //   274: aload_0
    //   275: areturn
    //   276: astore 19
    //   278: iconst_1
    //   279: istore 8
    //   281: goto -188 -> 93
    //   284: astore 6
    //   286: goto -38 -> 248
    //
    // Exception table:
    //   from	to	target	type
    //   55	62	79	java/io/IOException
    //   67	76	79	java/io/IOException
    //   206	215	221	java/io/IOException
    //   81	90	229	java/io/IOException
    //   55	62	237	finally
    //   67	76	237	finally
    //   31	55	251	java/io/FileNotFoundException
    //   81	90	251	java/io/FileNotFoundException
    //   239	248	251	java/io/FileNotFoundException
    //   248	251	251	java/io/FileNotFoundException
    //   206	215	276	java/io/FileNotFoundException
    //   239	248	284	java/io/IOException
  }

  public static String[] i()
  {
    return new String[] { "vendor", "Renderder" };
  }

  // ERROR //
  public static int[] j()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_0
    //   2: iconst_2
    //   3: newarray int
    //   5: dup
    //   6: iconst_0
    //   7: iconst_0
    //   8: iastore
    //   9: dup
    //   10: iconst_1
    //   11: iconst_0
    //   12: iastore
    //   13: astore_1
    //   14: iconst_4
    //   15: newarray int
    //   17: astore_2
    //   18: iconst_0
    //   19: istore_3
    //   20: iload_3
    //   21: iconst_4
    //   22: if_icmpge +13 -> 35
    //   25: aload_2
    //   26: iload_3
    //   27: iconst_0
    //   28: iastore
    //   29: iinc 3 1
    //   32: goto -12 -> 20
    //   35: new 184	java/io/FileReader
    //   38: dup
    //   39: ldc_w 306
    //   42: invokespecial 187	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   45: astore 4
    //   47: new 189	java/io/BufferedReader
    //   50: dup
    //   51: aload 4
    //   53: sipush 1024
    //   56: invokespecial 192	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   59: astore 5
    //   61: iload_0
    //   62: iconst_4
    //   63: if_icmpge +20 -> 83
    //   66: aload_2
    //   67: iload_0
    //   68: aload 5
    //   70: invokevirtual 274	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   73: invokestatic 308	com/talkingdata/pingan/sdk/j:a	(Ljava/lang/String;)I
    //   76: iastore
    //   77: iinc 0 1
    //   80: goto -19 -> 61
    //   83: aload_1
    //   84: iconst_0
    //   85: aload_2
    //   86: iconst_0
    //   87: iaload
    //   88: iastore
    //   89: aload_1
    //   90: iconst_1
    //   91: aload_2
    //   92: iconst_1
    //   93: iaload
    //   94: aload_2
    //   95: iconst_2
    //   96: iaload
    //   97: iadd
    //   98: aload_2
    //   99: iconst_3
    //   100: iaload
    //   101: iadd
    //   102: iastore
    //   103: aload 5
    //   105: invokevirtual 202	java/io/BufferedReader:close	()V
    //   108: aload 4
    //   110: invokevirtual 203	java/io/FileReader:close	()V
    //   113: aload_1
    //   114: areturn
    //   115: astore 9
    //   117: aload 5
    //   119: invokevirtual 202	java/io/BufferedReader:close	()V
    //   122: aload 4
    //   124: invokevirtual 203	java/io/FileReader:close	()V
    //   127: aload_1
    //   128: areturn
    //   129: astore 10
    //   131: aload_1
    //   132: areturn
    //   133: astore 6
    //   135: aload 5
    //   137: invokevirtual 202	java/io/BufferedReader:close	()V
    //   140: aload 4
    //   142: invokevirtual 203	java/io/FileReader:close	()V
    //   145: aload 6
    //   147: athrow
    //   148: astore 8
    //   150: aload_1
    //   151: areturn
    //   152: astore 7
    //   154: goto -9 -> 145
    //   157: astore 11
    //   159: aload_1
    //   160: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   66	77	115	java/io/IOException
    //   83	103	115	java/io/IOException
    //   117	127	129	java/io/IOException
    //   66	77	133	finally
    //   83	103	133	finally
    //   35	61	148	java/io/FileNotFoundException
    //   103	113	148	java/io/FileNotFoundException
    //   117	127	148	java/io/FileNotFoundException
    //   135	145	148	java/io/FileNotFoundException
    //   145	148	148	java/io/FileNotFoundException
    //   135	145	152	java/io/IOException
    //   103	113	157	java/io/IOException
  }

  public static int[] k()
  {
    int[] arrayOfInt = new int[4];
    StatFs localStatFs1 = new StatFs(Environment.getDataDirectory().getAbsolutePath());
    arrayOfInt[0] = (localStatFs1.getBlockCount() * (localStatFs1.getBlockSize() / 512) / 2);
    arrayOfInt[1] = (localStatFs1.getAvailableBlocks() * (localStatFs1.getBlockSize() / 512) / 2);
    StatFs localStatFs2 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
    arrayOfInt[2] = (localStatFs2.getBlockCount() * (localStatFs2.getBlockSize() / 512) / 2);
    arrayOfInt[3] = (localStatFs2.getAvailableBlocks() * (localStatFs2.getBlockSize() / 512) / 2);
    return arrayOfInt;
  }

  public static int l()
  {
    String str1 = b("/sys/class/power_supply/battery/full_bat");
    Matcher localMatcher = Pattern.compile("\\s*([0-9]+)").matcher(str1);
    boolean bool = localMatcher.find();
    int i = 0;
    String str2;
    if (bool)
      str2 = localMatcher.toMatchResult().group(0);
    try
    {
      int j = Integer.valueOf(str2).intValue();
      i = j;
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.j
 * JD-Core Version:    0.6.2
 */