package com.tendcloud.tenddata;

import android.content.Context;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.net.URL;

public class ag
{
  private static final String c = "td-cache";
  private static final String d = "td-%.zip";
  private static final String e = "td.tmp-";
  private static final String f = "pref_longtime";
  private static final String g = "td.sdk.digest.";
  private static final byte[] m = { -57, 9, -109, 31, 108, -13, -36, 34, -71, 6, -27, -77, -64, -39, 104, 24, -69, -86, -101, -20, -45, 83, 117, 85, 104, -59, -39, -93, 93, 44, 6, 66, 55, 71, -104, 118, -115, 88, 97, 27, 127, -83, -61, 50, 3, -34, -37, -110, 56, -78, 72, 28, 55, 98, -64, 87, 52, -75, 93, -27, 64, -94, 78, 80, -96, 77, 62, 42, -86, -83, 24, -97, 90, 24, -92, 94, -34, 124, -75, -111, 13, 35, 84, 69, -24, -73, -60, 116, 35, -71, 90, 61, -54, 62, -85, 71, -13, 108, -1, -53, -57, -102, 78, 78, 93, -122, -27, -97, -92, -38, -114, -69, -119, 61, 119, 116, -95, 39, 78, -52, -68, 66, 105, -104, 27, -91, 72, -50, 18, -110, 9, 7, -73, 11, -10, -81, 88, 85, 61, -34, -6, 52, -123, 76, 75, -81, -111, -121, 8, 32, 73, 18, 32, 83, 120, -58, -110, -30, 47, 120, 1, -113, -74, 73, -23, 33, -70, -110, -46, -105, -70, -94, 77, -118, 1, 111, 112, 23, 0, 55, 25, -49, -71, 17, 48, -10, 74, 101, 75, 107, 72, -19, 104, 48, -106, -12, 53, 63, -38, 116, -5, -104, 99, -81, 86, -93, 80, -103, 13, -6, -110, -83, -97, 107, 51, -125, 46, 124, -51, -111, 2, 52, 96, 116, -80, -76, 107, 24, -109, 100, 34, -98, 37, -112, -93, 35, 57, 68, 55, -114, -36, -111, -57, -26, 32, 93, 117, 67, -51, 115, 2, -105, -40, 111, 59, 36, 1, 100, -11, -17, 8, 64, 44, -42, 50, -44, 57, 11, 91, -5, 64, -92, 102, 35, -83, -5, -4, 71, 16, 53, 87, -89, 32, 46, 91, -5, -54, 15, -29, 43, 36, -22, 122, 99, -10, -68, -115, 52, 91, -42, 6, -110, 31, 115, 112, 14, -77, 120, -94, 126, 119, -86, 0, -123, -107, 82, -13, 34, 95, 87, 18, -23, -8, -43, -111, -41, 50, -47, 55, -3, -19, -12, -42, -124, -100, -29, -113, 26, 84, -74, -110, -51, -30, -8, -114, -42, 115, 76, -87, 92, 82, 47, 102, 33, 123, 125, 24, 4, -26, -69, -46, 31, -89, -114, -69, 50, -88, -64, -32, 64, 40, 11, 83, -43, 93, -101, -84, -104, 47, -117, 83, 98, 79, -30, 107, -3, -88, 82, -39, 118, -94, -51, 80, 55, -90, 64, -124, -111, -108, 31, -32, -60, 19, -48, -59, 59, -111, 53, 25, 41, 72, 15, -117, 80, 91, -119, -120, -93, 119, 124, 38, 24, -113, 83, -94, 122, 127, 58, 67, 11, 28, -55, -68, -108, -75, -52, 60, 80, -41, -105, 96, -77, 90, -30, -20, -20, -101, -119, 114, -85, 115, -7, -97, -7, -76, -53, 116, 85, -98, -54, -126, 36, 60, 104, -71, -94, -66, -128, -88, -10, -5, 76, -6, 68, 36, 13, 44, -91, 86, -5, 23, 101, -125, 13, -65, -76, 72, -6, -26, 107, -118, 4, 80, -27, 103, 38, 45, 38, 36, -10, 119, 124, -61, 73, 47, 5, 35, -47, -113, 114, 0, 45, 99, -99, 120, 83, 75, 56, -102, 22, -113, 19, -35, 113, 112, 33, 77, -32, 105, -66, -52, 124, -90, -61, -55, 54, -103, -26, 126, -92, -19, 101, -124, -90, 82, 68, 91, 13, -51, 91, -111, -40, 67, 38, -13, 59, 114, -113, -46, -71, -119, -74, 112, 78, -23, 7, -112, -65, 58, -3, -35, 115, 20, -113, 55, 92, -63, -105, 59, -100, 38, 108, 75, -114, -79, -8, 95, -69, -33, -108, -88, -16, -2, -91, -32, -52, -56, -41, -7, -53, -34, -72, -76, -94, 60, 39, -52, 14, 114, -113, -79, 51, -37, 66, 46, -107, -24, -128, 44, 77, 98, -23, 90, -101, 104, -26, -84, 2, -80, 68, -98, 98, -87, -113, -58, 75, 52, 74, 50, 78, -78, -91, 117, 29, -3, -18, 21, -38, 104, 7, 123, 66, 76, -72, -118, -73, -7, 40, -23, -125, -85, 53, 45, 4, 42, -99, 116, 29, -43, -58, 81, 21, -122, 99, 12, 42, -3, 50, 72, -103, 65, 59, -112, 98, 116, -4, 37, 119, 80, -58, 93, -77, 27, -92, -90, 126, -63, -126, 45, 96, -35, 25, -120, 20, -23, 49, 10, 119, 54, 2, 90, 106, 101, 21, -125, -26, 26, 103, 28, -73, -110, 44, -109, 4, 27, -73, -74, 44, 111, -71, 97, -16, -120, -69, 69, -98, 125, 84, -125, -122, 56, -55, -101, 18, -102, -87, 41, -103, 63, -62, 52, -3, 53, 28, 37, 49, -88, 104, 104, -113, 125, -41, -70, -50, -12, 12, 84, -3, -88, -31, -48, 55, 2, 97, 19, 123, 102, 71, 118, -65, 13, -68, -103, 23, -102, 66, -62, -26, 20, 95, 12, -128, 115, 9, 43, 116, -106, -44, -48, 92, 8, 119, 98, -35, 43, 24, -1, -20, 1, 80, 39, -102, -118, 57, 100, 88, -95, -113, -75, -16, 9, 91, 67, -118, -4, -98, -62, 37, 14, 112, -66, -28, 59, 119, 0, 33, -50, -58, 98, 78, -2, -113, -73, -97, -110, -37, 51, -51, -58, 25, 58, 36, -10, 112, 21, 76, -39, 1, -64, -48, 92, 37, -39, 1, -22, -51, -121, 124, 13, 3, 33, 76, 93, 82, 110, 95, -105, 69, 79, 10, 112, 60, 50, -60, 70, -18, -75, 85, -9, -3, 36, -66, -90, 107, 71, 67, -26, -108, -101, -54, -81, 58, -69, -63, 99, 53, -51, 58, 86, -10, 39, -112, -15, 37, -56, 79, 86, -92, 102, -88, -70, -26, -11, -35, 20, 47, 69, -27, 58, -37, -116, -35, -47, 96, 81, 89, -63, -12, -44, -117, 63, 105, 24, 53, -25, 123, -45, 48, -92, -126, -83, -49, 35, -64, 125, -96, 98, -5, -51, 78, -48, -91, 127, -119, -79, 2, 104, -70, -27, -61, -25, 55, 82, -107, -8, 9, -28, -110, 30, -1, -9, 17, 7, 104, 60, -61, 84, 92, 53, -30, -74, -75, 88, -34, -109, 63, -71, -12, -56, 96, -37, -25, -28, 60, 91, 37, 14, -4, 17, 63, 116, -96, 117, 91, -84, 118, -58, -21, 88, 9, -49, 47, -99, 6, -25, -75, 127, -20, 49, -41, 117, 10, 54, 86, 92, -57, 97, -7, -25, -77 };
  private static final int[] n = { 48, 174, 179, 57, 117, 158, 111, 77, 5, 190, 185, 241, 57, 21, 216, 178, 37, 2, 95, 166 };
  private static final int[] o = { 104, 169, 91, 102, 66, 239, 22, 20, 116, 6, 95, 8, 9, 28, 206, 160, 123, 152, 33, 126 };
  u a;
  Context b;
  String h;
  String i;
  String j;
  String k;
  volatile boolean l = false;

  private u a(String paramString)
  {
    try
    {
      this.i = "td-%.zip".replace("%", this.h);
      this.j = ("td.tmp-" + this.h);
      this.k = ("td.sdk.digest." + this.h);
      File localFile1 = this.b.getDir("td-cache", 0);
      File localFile2 = new File(localFile1, this.i);
      if (a(localFile2))
      {
        u localu = (u)new DexClassLoader(localFile2.getAbsolutePath(), localFile1.getAbsolutePath(), null, ag.class.getClassLoader()).loadClass(paramString).newInstance();
        return localu;
      }
    }
    catch (Throwable localThrowable)
    {
    }
    return null;
  }

  private void a(u paramu)
  {
    URL localURL = new URL(paramu.c());
    String str1 = localURL.getHost();
    int i1 = localURL.getPort();
    if (i1 == -1)
      i1 = localURL.getDefaultPort();
    ad localad = new ad(new String(x.a(x.a(n, o), m)), str1, i1);
    String str2 = localad.a(paramu.c());
    File localFile1;
    File localFile2;
    String str4;
    if (!x.a(str2))
    {
      String str3 = str2.trim();
      paramu.a(str3);
      if (!str3.equalsIgnoreCase(paramu.a()))
      {
        localFile1 = this.b.getDir("td-cache", 0);
        localFile2 = new File(localFile1, this.j);
        str4 = localad.a(paramu.d(), localFile2);
        if (str4 == null);
      }
    }
    try
    {
      File localFile3 = new File(localFile1, this.i);
      localFile3.delete();
      localFile2.renameTo(localFile3);
      aa.a(this.b, "pref_longtime", this.k, str4);
      paramu.e();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  // ERROR //
  private boolean a(File paramFile)
  {
    // Byte code:
    //   0: new 451	java/io/FileInputStream
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 454	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   8: astore_2
    //   9: ldc_w 456
    //   12: invokestatic 462	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   15: astore 7
    //   17: sipush 1024
    //   20: newarray byte
    //   22: astore 8
    //   24: aload_2
    //   25: aload 8
    //   27: invokevirtual 466	java/io/FileInputStream:read	([B)I
    //   30: istore 9
    //   32: iload 9
    //   34: iconst_m1
    //   35: if_icmpeq +24 -> 59
    //   38: aload 7
    //   40: aload 8
    //   42: iconst_0
    //   43: iload 9
    //   45: invokevirtual 470	java/security/MessageDigest:update	([BII)V
    //   48: goto -24 -> 24
    //   51: astore 5
    //   53: aload_2
    //   54: invokevirtual 473	java/io/FileInputStream:close	()V
    //   57: iconst_0
    //   58: ireturn
    //   59: aload 7
    //   61: invokevirtual 477	java/security/MessageDigest:digest	()[B
    //   64: invokestatic 480	com/tendcloud/tenddata/x:a	([B)Ljava/lang/String;
    //   67: astore 10
    //   69: aload_0
    //   70: getfield 333	com/tendcloud/tenddata/ag:b	Landroid/content/Context;
    //   73: ldc 17
    //   75: aload_0
    //   76: getfield 331	com/tendcloud/tenddata/ag:k	Ljava/lang/String;
    //   79: ldc_w 482
    //   82: invokestatic 485	com/tendcloud/tenddata/aa:b	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   85: aload 10
    //   87: invokevirtual 489	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   90: istore 11
    //   92: aload_2
    //   93: invokevirtual 473	java/io/FileInputStream:close	()V
    //   96: iload 11
    //   98: ireturn
    //   99: astore 12
    //   101: iload 11
    //   103: ireturn
    //   104: astore_3
    //   105: aconst_null
    //   106: astore_2
    //   107: aload_2
    //   108: invokevirtual 473	java/io/FileInputStream:close	()V
    //   111: aload_3
    //   112: athrow
    //   113: astore 6
    //   115: iconst_0
    //   116: ireturn
    //   117: astore 4
    //   119: goto -8 -> 111
    //   122: astore_3
    //   123: goto -16 -> 107
    //   126: astore 13
    //   128: aconst_null
    //   129: astore_2
    //   130: goto -77 -> 53
    //
    // Exception table:
    //   from	to	target	type
    //   9	24	51	java/lang/Exception
    //   24	32	51	java/lang/Exception
    //   38	48	51	java/lang/Exception
    //   59	92	51	java/lang/Exception
    //   92	96	99	java/io/IOException
    //   0	9	104	finally
    //   53	57	113	java/io/IOException
    //   107	111	117	java/io/IOException
    //   9	24	122	finally
    //   24	32	122	finally
    //   38	48	122	finally
    //   59	92	122	finally
    //   0	9	126	java/lang/Exception
  }

  // ERROR //
  public u a(Context paramContext, String paramString1, String paramString2, Class paramClass1, Class paramClass2, String paramString3)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 494	com/tendcloud/tenddata/ag:a	Lcom/tendcloud/tenddata/u;
    //   6: ifnonnull +82 -> 88
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual 498	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   14: putfield 333	com/tendcloud/tenddata/ag:b	Landroid/content/Context;
    //   17: aload_0
    //   18: aload_3
    //   19: putfield 308	com/tendcloud/tenddata/ag:h	Ljava/lang/String;
    //   22: aload_0
    //   23: monitorenter
    //   24: new 500	java/lang/Thread
    //   27: dup
    //   28: new 502	com/tendcloud/tenddata/w
    //   31: dup
    //   32: aload_0
    //   33: aload 6
    //   35: invokespecial 505	com/tendcloud/tenddata/w:<init>	(Lcom/tendcloud/tenddata/ag;Ljava/lang/String;)V
    //   38: invokespecial 508	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   41: invokevirtual 511	java/lang/Thread:start	()V
    //   44: aload_0
    //   45: ldc2_w 512
    //   48: invokevirtual 517	java/lang/Object:wait	(J)V
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_0
    //   54: getfield 494	com/tendcloud/tenddata/ag:a	Lcom/tendcloud/tenddata/u;
    //   57: ifnonnull +20 -> 77
    //   60: aload_0
    //   61: iconst_1
    //   62: putfield 298	com/tendcloud/tenddata/ag:l	Z
    //   65: aload_0
    //   66: aload 5
    //   68: invokevirtual 371	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   71: checkcast 373	com/tendcloud/tenddata/u
    //   74: putfield 494	com/tendcloud/tenddata/ag:a	Lcom/tendcloud/tenddata/u;
    //   77: aload_0
    //   78: getfield 494	com/tendcloud/tenddata/ag:a	Lcom/tendcloud/tenddata/u;
    //   81: aload_1
    //   82: aload_2
    //   83: invokeinterface 520 3 0
    //   88: aload_0
    //   89: getfield 494	com/tendcloud/tenddata/ag:a	Lcom/tendcloud/tenddata/u;
    //   92: astore 8
    //   94: aload_0
    //   95: monitorexit
    //   96: aload 8
    //   98: areturn
    //   99: astore 10
    //   101: aload_0
    //   102: monitorexit
    //   103: aload 10
    //   105: athrow
    //   106: astore 7
    //   108: aload_0
    //   109: monitorexit
    //   110: aload 7
    //   112: athrow
    //   113: astore 9
    //   115: goto -64 -> 51
    //
    // Exception table:
    //   from	to	target	type
    //   24	51	99	finally
    //   51	53	99	finally
    //   101	103	99	finally
    //   2	24	106	finally
    //   53	77	106	finally
    //   77	88	106	finally
    //   88	94	106	finally
    //   103	106	106	finally
    //   24	51	113	java/lang/InterruptedException
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.ag
 * JD-Core Version:    0.6.2
 */