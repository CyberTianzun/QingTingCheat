package com.taobao.munion.base.utdid.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import com.taobao.munion.base.utdid.a.a.f;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class c
{
  private static final String a = "t";
  private static final String b = "t2";
  private String c;
  private String d;
  private boolean e;
  private boolean f;
  private boolean g;
  private SharedPreferences h;
  private b i;
  private SharedPreferences.Editor j;
  private b.a k;
  private Context l;
  private d m;
  private boolean n;

  // ERROR //
  public c(Context paramContext, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 37	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: ldc 39
    //   7: putfield 41	com/taobao/munion/base/utdid/b/a/c:c	Ljava/lang/String;
    //   10: aload_0
    //   11: ldc 39
    //   13: putfield 43	com/taobao/munion/base/utdid/b/a/c:d	Ljava/lang/String;
    //   16: aload_0
    //   17: iconst_0
    //   18: putfield 45	com/taobao/munion/base/utdid/b/a/c:e	Z
    //   21: aload_0
    //   22: iconst_0
    //   23: putfield 47	com/taobao/munion/base/utdid/b/a/c:f	Z
    //   26: aload_0
    //   27: iconst_0
    //   28: putfield 49	com/taobao/munion/base/utdid/b/a/c:g	Z
    //   31: aload_0
    //   32: aconst_null
    //   33: putfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   36: aload_0
    //   37: aconst_null
    //   38: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   41: aload_0
    //   42: aconst_null
    //   43: putfield 55	com/taobao/munion/base/utdid/b/a/c:j	Landroid/content/SharedPreferences$Editor;
    //   46: aload_0
    //   47: aconst_null
    //   48: putfield 57	com/taobao/munion/base/utdid/b/a/c:k	Lcom/taobao/munion/base/utdid/b/a/b$a;
    //   51: aload_0
    //   52: aconst_null
    //   53: putfield 59	com/taobao/munion/base/utdid/b/a/c:l	Landroid/content/Context;
    //   56: aload_0
    //   57: aconst_null
    //   58: putfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   61: aload_0
    //   62: iconst_0
    //   63: putfield 63	com/taobao/munion/base/utdid/b/a/c:n	Z
    //   66: aload_0
    //   67: iload 4
    //   69: putfield 45	com/taobao/munion/base/utdid/b/a/c:e	Z
    //   72: aload_0
    //   73: iload 5
    //   75: putfield 63	com/taobao/munion/base/utdid/b/a/c:n	Z
    //   78: aload_0
    //   79: aload_3
    //   80: putfield 41	com/taobao/munion/base/utdid/b/a/c:c	Ljava/lang/String;
    //   83: aload_0
    //   84: aload_2
    //   85: putfield 43	com/taobao/munion/base/utdid/b/a/c:d	Ljava/lang/String;
    //   88: aload_0
    //   89: aload_1
    //   90: putfield 59	com/taobao/munion/base/utdid/b/a/c:l	Landroid/content/Context;
    //   93: lconst_0
    //   94: lstore 6
    //   96: lconst_0
    //   97: lstore 8
    //   99: aload_1
    //   100: ifnull +27 -> 127
    //   103: aload_0
    //   104: aload_1
    //   105: aload_3
    //   106: iconst_0
    //   107: invokevirtual 69	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   110: putfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   113: aload_0
    //   114: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   117: ldc 8
    //   119: lconst_0
    //   120: invokeinterface 75 4 0
    //   125: lstore 6
    //   127: invokestatic 81	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
    //   130: astore 10
    //   132: aload 10
    //   134: invokestatic 86	com/taobao/munion/base/utdid/a/a/f:a	(Ljava/lang/String;)Z
    //   137: ifeq +275 -> 412
    //   140: aload_0
    //   141: iconst_0
    //   142: putfield 49	com/taobao/munion/base/utdid/b/a/c:g	Z
    //   145: aload_0
    //   146: iconst_0
    //   147: putfield 47	com/taobao/munion/base/utdid/b/a/c:f	Z
    //   150: aload_0
    //   151: getfield 47	com/taobao/munion/base/utdid/b/a/c:f	Z
    //   154: ifne +10 -> 164
    //   157: aload_0
    //   158: getfield 49	com/taobao/munion/base/utdid/b/a/c:g	Z
    //   161: ifeq +119 -> 280
    //   164: aload_1
    //   165: ifnull +115 -> 280
    //   168: aload_2
    //   169: invokestatic 86	com/taobao/munion/base/utdid/a/a/f:a	(Ljava/lang/String;)Z
    //   172: ifne +108 -> 280
    //   175: aload_0
    //   176: aload_0
    //   177: aload_2
    //   178: invokespecial 89	com/taobao/munion/base/utdid/b/a/c:g	(Ljava/lang/String;)Lcom/taobao/munion/base/utdid/b/a/d;
    //   181: putfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   184: aload_0
    //   185: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   188: ifnull +92 -> 280
    //   191: aload_0
    //   192: aload_0
    //   193: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   196: aload_3
    //   197: iconst_0
    //   198: invokevirtual 94	com/taobao/munion/base/utdid/b/a/d:a	(Ljava/lang/String;I)Lcom/taobao/munion/base/utdid/b/a/b;
    //   201: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   204: aload_0
    //   205: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   208: ldc 8
    //   210: lconst_0
    //   211: invokeinterface 98 4 0
    //   216: lstore 8
    //   218: iload 5
    //   220: ifne +344 -> 564
    //   223: lload 6
    //   225: lload 8
    //   227: lcmp
    //   228: ifle +243 -> 471
    //   231: aload_0
    //   232: aload_0
    //   233: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   236: aload_0
    //   237: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   240: invokespecial 101	com/taobao/munion/base/utdid/b/a/c:a	(Landroid/content/SharedPreferences;Lcom/taobao/munion/base/utdid/b/a/b;)V
    //   243: aload_0
    //   244: aload_0
    //   245: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   248: aload_3
    //   249: iconst_0
    //   250: invokevirtual 94	com/taobao/munion/base/utdid/b/a/d:a	(Ljava/lang/String;I)Lcom/taobao/munion/base/utdid/b/a/b;
    //   253: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   256: lload 8
    //   258: lstore 35
    //   260: lload 6
    //   262: lstore 31
    //   264: lload 35
    //   266: lstore 23
    //   268: lload 23
    //   270: lstore 33
    //   272: lload 31
    //   274: lstore 6
    //   276: lload 33
    //   278: lstore 8
    //   280: lload 6
    //   282: lload 8
    //   284: lcmp
    //   285: ifne +17 -> 302
    //   288: lload 6
    //   290: lconst_0
    //   291: lcmp
    //   292: ifne +119 -> 411
    //   295: lload 8
    //   297: lconst_0
    //   298: lcmp
    //   299: ifne +112 -> 411
    //   302: invokestatic 107	java/lang/System:currentTimeMillis	()J
    //   305: lstore 11
    //   307: aload_0
    //   308: getfield 63	com/taobao/munion/base/utdid/b/a/c:n	Z
    //   311: ifeq +24 -> 335
    //   314: aload_0
    //   315: getfield 63	com/taobao/munion/base/utdid/b/a/c:n	Z
    //   318: ifeq +93 -> 411
    //   321: lload 6
    //   323: lconst_0
    //   324: lcmp
    //   325: ifne +86 -> 411
    //   328: lload 8
    //   330: lconst_0
    //   331: lcmp
    //   332: ifne +79 -> 411
    //   335: aload_0
    //   336: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   339: ifnull +34 -> 373
    //   342: aload_0
    //   343: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   346: invokeinterface 111 1 0
    //   351: astore 17
    //   353: aload 17
    //   355: ldc 11
    //   357: lload 11
    //   359: invokeinterface 117 4 0
    //   364: pop
    //   365: aload 17
    //   367: invokeinterface 121 1 0
    //   372: pop
    //   373: aload_0
    //   374: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   377: ifnull +34 -> 411
    //   380: aload_0
    //   381: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   384: invokeinterface 124 1 0
    //   389: astore 14
    //   391: aload 14
    //   393: ldc 11
    //   395: lload 11
    //   397: invokeinterface 129 4 0
    //   402: pop
    //   403: aload 14
    //   405: invokeinterface 131 1 0
    //   410: pop
    //   411: return
    //   412: aload 10
    //   414: ldc 133
    //   416: invokevirtual 139	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   419: ifeq +16 -> 435
    //   422: aload_0
    //   423: iconst_1
    //   424: putfield 49	com/taobao/munion/base/utdid/b/a/c:g	Z
    //   427: aload_0
    //   428: iconst_1
    //   429: putfield 47	com/taobao/munion/base/utdid/b/a/c:f	Z
    //   432: goto -282 -> 150
    //   435: aload 10
    //   437: ldc 141
    //   439: invokevirtual 139	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   442: ifeq +16 -> 458
    //   445: aload_0
    //   446: iconst_1
    //   447: putfield 47	com/taobao/munion/base/utdid/b/a/c:f	Z
    //   450: aload_0
    //   451: iconst_0
    //   452: putfield 49	com/taobao/munion/base/utdid/b/a/c:g	Z
    //   455: goto -305 -> 150
    //   458: aload_0
    //   459: iconst_0
    //   460: putfield 49	com/taobao/munion/base/utdid/b/a/c:g	Z
    //   463: aload_0
    //   464: iconst_0
    //   465: putfield 47	com/taobao/munion/base/utdid/b/a/c:f	Z
    //   468: goto -318 -> 150
    //   471: lload 6
    //   473: lload 8
    //   475: lcmp
    //   476: ifge +40 -> 516
    //   479: aload_0
    //   480: aload_0
    //   481: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   484: aload_0
    //   485: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   488: invokespecial 144	com/taobao/munion/base/utdid/b/a/c:a	(Lcom/taobao/munion/base/utdid/b/a/b;Landroid/content/SharedPreferences;)V
    //   491: aload_0
    //   492: aload_1
    //   493: aload_3
    //   494: iconst_0
    //   495: invokevirtual 69	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   498: putfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   501: lload 8
    //   503: lstore 41
    //   505: lload 6
    //   507: lstore 31
    //   509: lload 41
    //   511: lstore 23
    //   513: goto -245 -> 268
    //   516: lload 6
    //   518: lload 8
    //   520: lcmp
    //   521: ifne +337 -> 858
    //   524: aload_0
    //   525: aload_0
    //   526: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   529: aload_0
    //   530: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   533: invokespecial 101	com/taobao/munion/base/utdid/b/a/c:a	(Landroid/content/SharedPreferences;Lcom/taobao/munion/base/utdid/b/a/b;)V
    //   536: aload_0
    //   537: aload_0
    //   538: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   541: aload_3
    //   542: iconst_0
    //   543: invokevirtual 94	com/taobao/munion/base/utdid/b/a/d:a	(Ljava/lang/String;I)Lcom/taobao/munion/base/utdid/b/a/b;
    //   546: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   549: lload 8
    //   551: lstore 37
    //   553: lload 6
    //   555: lstore 31
    //   557: lload 37
    //   559: lstore 23
    //   561: goto -293 -> 268
    //   564: aload_0
    //   565: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   568: ldc 11
    //   570: lconst_0
    //   571: invokeinterface 75 4 0
    //   576: lstore 25
    //   578: lload 25
    //   580: lstore 21
    //   582: aload_0
    //   583: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   586: ldc 11
    //   588: lconst_0
    //   589: invokeinterface 98 4 0
    //   594: lstore 28
    //   596: lload 28
    //   598: lstore 23
    //   600: lload 21
    //   602: lload 23
    //   604: lcmp
    //   605: ifge +42 -> 647
    //   608: lload 21
    //   610: lconst_0
    //   611: lcmp
    //   612: ifle +35 -> 647
    //   615: aload_0
    //   616: aload_0
    //   617: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   620: aload_0
    //   621: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   624: invokespecial 101	com/taobao/munion/base/utdid/b/a/c:a	(Landroid/content/SharedPreferences;Lcom/taobao/munion/base/utdid/b/a/b;)V
    //   627: aload_0
    //   628: aload_0
    //   629: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   632: aload_3
    //   633: iconst_0
    //   634: invokevirtual 94	com/taobao/munion/base/utdid/b/a/d:a	(Ljava/lang/String;I)Lcom/taobao/munion/base/utdid/b/a/b;
    //   637: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   640: lload 21
    //   642: lstore 31
    //   644: goto -376 -> 268
    //   647: lload 21
    //   649: lload 23
    //   651: lcmp
    //   652: ifle +39 -> 691
    //   655: lload 23
    //   657: lconst_0
    //   658: lcmp
    //   659: ifle +32 -> 691
    //   662: aload_0
    //   663: aload_0
    //   664: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   667: aload_0
    //   668: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   671: invokespecial 144	com/taobao/munion/base/utdid/b/a/c:a	(Lcom/taobao/munion/base/utdid/b/a/b;Landroid/content/SharedPreferences;)V
    //   674: aload_0
    //   675: aload_1
    //   676: aload_3
    //   677: iconst_0
    //   678: invokevirtual 69	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   681: putfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   684: lload 21
    //   686: lstore 31
    //   688: goto -420 -> 268
    //   691: lload 21
    //   693: lconst_0
    //   694: lcmp
    //   695: ifne +39 -> 734
    //   698: lload 23
    //   700: lconst_0
    //   701: lcmp
    //   702: ifle +32 -> 734
    //   705: aload_0
    //   706: aload_0
    //   707: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   710: aload_0
    //   711: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   714: invokespecial 144	com/taobao/munion/base/utdid/b/a/c:a	(Lcom/taobao/munion/base/utdid/b/a/b;Landroid/content/SharedPreferences;)V
    //   717: aload_0
    //   718: aload_1
    //   719: aload_3
    //   720: iconst_0
    //   721: invokevirtual 69	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   724: putfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   727: lload 21
    //   729: lstore 31
    //   731: goto -463 -> 268
    //   734: lload 23
    //   736: lconst_0
    //   737: lcmp
    //   738: ifne +42 -> 780
    //   741: lload 21
    //   743: lconst_0
    //   744: lcmp
    //   745: ifle +35 -> 780
    //   748: aload_0
    //   749: aload_0
    //   750: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   753: aload_0
    //   754: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   757: invokespecial 101	com/taobao/munion/base/utdid/b/a/c:a	(Landroid/content/SharedPreferences;Lcom/taobao/munion/base/utdid/b/a/b;)V
    //   760: aload_0
    //   761: aload_0
    //   762: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   765: aload_3
    //   766: iconst_0
    //   767: invokevirtual 94	com/taobao/munion/base/utdid/b/a/d:a	(Ljava/lang/String;I)Lcom/taobao/munion/base/utdid/b/a/b;
    //   770: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   773: lload 21
    //   775: lstore 31
    //   777: goto -509 -> 268
    //   780: lload 21
    //   782: lload 23
    //   784: lcmp
    //   785: ifne +28 -> 813
    //   788: aload_0
    //   789: aload_0
    //   790: getfield 51	com/taobao/munion/base/utdid/b/a/c:h	Landroid/content/SharedPreferences;
    //   793: aload_0
    //   794: getfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   797: invokespecial 101	com/taobao/munion/base/utdid/b/a/c:a	(Landroid/content/SharedPreferences;Lcom/taobao/munion/base/utdid/b/a/b;)V
    //   800: aload_0
    //   801: aload_0
    //   802: getfield 61	com/taobao/munion/base/utdid/b/a/c:m	Lcom/taobao/munion/base/utdid/b/a/d;
    //   805: aload_3
    //   806: iconst_0
    //   807: invokevirtual 94	com/taobao/munion/base/utdid/b/a/d:a	(Ljava/lang/String;I)Lcom/taobao/munion/base/utdid/b/a/b;
    //   810: putfield 53	com/taobao/munion/base/utdid/b/a/c:i	Lcom/taobao/munion/base/utdid/b/a/b;
    //   813: lload 21
    //   815: lstore 31
    //   817: goto -549 -> 268
    //   820: astore 20
    //   822: lload 6
    //   824: lstore 21
    //   826: lload 8
    //   828: lstore 23
    //   830: lload 23
    //   832: lstore 8
    //   834: lload 21
    //   836: lstore 6
    //   838: goto -558 -> 280
    //   841: astore 13
    //   843: return
    //   844: astore 27
    //   846: lload 8
    //   848: lstore 23
    //   850: goto -20 -> 830
    //   853: astore 30
    //   855: goto -25 -> 830
    //   858: lload 8
    //   860: lstore 39
    //   862: lload 6
    //   864: lstore 31
    //   866: lload 39
    //   868: lstore 23
    //   870: goto -602 -> 268
    //
    // Exception table:
    //   from	to	target	type
    //   191	218	820	java/lang/Exception
    //   231	256	820	java/lang/Exception
    //   479	501	820	java/lang/Exception
    //   524	549	820	java/lang/Exception
    //   564	578	820	java/lang/Exception
    //   373	411	841	java/lang/Exception
    //   582	596	844	java/lang/Exception
    //   615	640	853	java/lang/Exception
    //   662	684	853	java/lang/Exception
    //   705	727	853	java/lang/Exception
    //   748	773	853	java/lang/Exception
    //   788	813	853	java/lang/Exception
  }

  private void a(SharedPreferences paramSharedPreferences, b paramb)
  {
    if ((paramSharedPreferences != null) && (paramb != null))
    {
      b.a locala = paramb.c();
      if (locala != null)
      {
        locala.a();
        Iterator localIterator = paramSharedPreferences.getAll().entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          String str = (String)localEntry.getKey();
          Object localObject = localEntry.getValue();
          if ((localObject instanceof String))
            locala.a(str, (String)localObject);
          else if ((localObject instanceof Integer))
            locala.a(str, ((Integer)localObject).intValue());
          else if ((localObject instanceof Long))
            locala.a(str, ((Long)localObject).longValue());
          else if ((localObject instanceof Float))
            locala.a(str, ((Float)localObject).floatValue());
          else if ((localObject instanceof Boolean))
            locala.a(str, ((Boolean)localObject).booleanValue());
        }
        locala.b();
      }
    }
  }

  private void a(b paramb, SharedPreferences paramSharedPreferences)
  {
    if ((paramb != null) && (paramSharedPreferences != null))
    {
      SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
      if (localEditor != null)
      {
        localEditor.clear();
        Iterator localIterator = paramb.b().entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          String str = (String)localEntry.getKey();
          Object localObject = localEntry.getValue();
          if ((localObject instanceof String))
            localEditor.putString(str, (String)localObject);
          else if ((localObject instanceof Integer))
            localEditor.putInt(str, ((Integer)localObject).intValue());
          else if ((localObject instanceof Long))
            localEditor.putLong(str, ((Long)localObject).longValue());
          else if ((localObject instanceof Float))
            localEditor.putFloat(str, ((Float)localObject).floatValue());
          else if ((localObject instanceof Boolean))
            localEditor.putBoolean(str, ((Boolean)localObject).booleanValue());
        }
        localEditor.commit();
      }
    }
  }

  private boolean e()
  {
    if (this.i != null)
    {
      boolean bool = this.i.a();
      if (!bool)
        c();
      return bool;
    }
    return false;
  }

  private void f()
  {
    if ((this.j == null) && (this.h != null))
      this.j = this.h.edit();
    if ((this.g) && (this.k == null) && (this.i != null))
      this.k = this.i.c();
    e();
  }

  private d g(String paramString)
  {
    File localFile = h(paramString);
    if (localFile != null)
    {
      this.m = new d(localFile.getAbsolutePath());
      return this.m;
    }
    return null;
  }

  private File h(String paramString)
  {
    File localFile1 = Environment.getExternalStorageDirectory();
    if (localFile1 != null)
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = localFile1.getAbsolutePath();
      arrayOfObject[1] = File.separator;
      arrayOfObject[2] = paramString;
      File localFile2 = new File(String.format("%s%s%s", arrayOfObject));
      if ((localFile2 != null) && (!localFile2.exists()))
        localFile2.mkdirs();
      return localFile2;
    }
    return null;
  }

  public void a()
  {
    if ((this.h != null) && (this.l != null))
      this.h = this.l.getSharedPreferences(this.c, 0);
    String str = Environment.getExternalStorageState();
    if ((!f.a(str)) && ((str.equals("mounted")) || ((str.equals("mounted_ro")) && (this.i != null))));
    try
    {
      if (this.m != null)
        this.i = this.m.a(this.c, 0);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void a(String paramString)
  {
    if ((!f.a(paramString)) && (!paramString.equals("t")))
    {
      f();
      if (this.j != null)
        this.j.remove(paramString);
      if (this.k != null)
        this.k.a(paramString);
    }
  }

  public void a(String paramString, float paramFloat)
  {
    if ((!f.a(paramString)) && (!paramString.equals("t")))
    {
      f();
      if (this.j != null)
        this.j.putFloat(paramString, paramFloat);
      if (this.k != null)
        this.k.a(paramString, paramFloat);
    }
  }

  public void a(String paramString, int paramInt)
  {
    if ((!f.a(paramString)) && (!paramString.equals("t")))
    {
      f();
      if (this.j != null)
        this.j.putInt(paramString, paramInt);
      if (this.k != null)
        this.k.a(paramString, paramInt);
    }
  }

  public void a(String paramString, long paramLong)
  {
    if ((!f.a(paramString)) && (!paramString.equals("t")))
    {
      f();
      if (this.j != null)
        this.j.putLong(paramString, paramLong);
      if (this.k != null)
        this.k.a(paramString, paramLong);
    }
  }

  public void a(String paramString1, String paramString2)
  {
    if ((!f.a(paramString1)) && (!paramString1.equals("t")))
    {
      f();
      if (this.j != null)
        this.j.putString(paramString1, paramString2);
      if (this.k != null)
        this.k.a(paramString1, paramString2);
    }
  }

  public void a(String paramString, boolean paramBoolean)
  {
    if ((!f.a(paramString)) && (!paramString.equals("t")))
    {
      f();
      if (this.j != null)
        this.j.putBoolean(paramString, paramBoolean);
      if (this.k != null)
        this.k.a(paramString, paramBoolean);
    }
  }

  public String b(String paramString)
  {
    e();
    if (this.h != null)
    {
      String str = this.h.getString(paramString, "");
      if (!f.a(str))
        return str;
    }
    if (this.i != null)
      return this.i.a(paramString, "");
    return "";
  }

  public void b()
  {
    f();
    long l1 = System.currentTimeMillis();
    if (this.j != null)
    {
      this.j.clear();
      this.j.putLong("t", l1);
    }
    if (this.k != null)
    {
      this.k.a();
      this.k.a("t", l1);
    }
  }

  public int c(String paramString)
  {
    e();
    int i1;
    if (this.h != null)
      i1 = this.h.getInt(paramString, 0);
    b localb;
    do
    {
      return i1;
      localb = this.i;
      i1 = 0;
    }
    while (localb == null);
    return this.i.a(paramString, 0);
  }

  public boolean c()
  {
    boolean bool = true;
    long l1 = System.currentTimeMillis();
    if (this.j != null)
    {
      if ((!this.n) && (this.h != null))
        this.j.putLong("t", l1);
      if (!this.j.commit())
        bool = false;
    }
    if ((this.h != null) && (this.l != null))
      this.h = this.l.getSharedPreferences(this.c, 0);
    String str = Environment.getExternalStorageState();
    if (!f.a(str))
      if (str.equals("mounted"))
      {
        if (this.i != null)
          break label242;
        d locald = g(this.d);
        if (locald != null)
        {
          this.i = locald.a(this.c, 0);
          if (this.n)
            break label227;
          a(this.h, this.i);
        }
      }
    while (true)
    {
      this.k = this.i.c();
      label175: if ((str.equals("mounted")) || ((str.equals("mounted_ro")) && (this.i != null)));
      try
      {
        if (this.m != null)
          this.i = this.m.a(this.c, 0);
        return bool;
        label227: a(this.i, this.h);
        continue;
        label242: if ((this.k == null) || (this.k.b()))
          break label175;
        bool = false;
      }
      catch (Exception localException)
      {
      }
    }
    return bool;
  }

  public long d(String paramString)
  {
    long l1 = 0L;
    e();
    if (this.h != null)
      l1 = this.h.getLong(paramString, l1);
    while (this.i == null)
      return l1;
    return this.i.a(paramString, l1);
  }

  public Map<String, ?> d()
  {
    e();
    if (this.h != null)
      return this.h.getAll();
    if (this.i != null)
      return this.i.b();
    return null;
  }

  public float e(String paramString)
  {
    e();
    float f1;
    if (this.h != null)
      f1 = this.h.getFloat(paramString, 0.0F);
    b localb;
    do
    {
      return f1;
      localb = this.i;
      f1 = 0.0F;
    }
    while (localb == null);
    return this.i.a(paramString, 0.0F);
  }

  public boolean f(String paramString)
  {
    e();
    boolean bool;
    if (this.h != null)
      bool = this.h.getBoolean(paramString, false);
    b localb;
    do
    {
      return bool;
      localb = this.i;
      bool = false;
    }
    while (localb == null);
    return this.i.a(paramString, false);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.utdid.b.a.c
 * JD-Core Version:    0.6.2
 */