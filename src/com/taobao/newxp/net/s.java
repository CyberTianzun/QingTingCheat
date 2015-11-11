package com.taobao.newxp.net;

import com.taobao.munion.base.volley.i;
import com.taobao.munion.base.volley.k;
import com.taobao.munion.base.volley.n;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.Log;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class s extends a
{
  public static String[] a;
  private boolean b = false;

  private s(String paramString, com.taobao.munion.base.f paramf)
  {
    super(paramString, paramf);
  }

  protected n<JSONObject> a(i parami)
  {
    try
    {
      n localn = n.a(new JSONObject(new String(parami.b, com.taobao.munion.base.volley.a.f.a(parami.c))), com.taobao.munion.base.volley.a.f.a(parami));
      return localn;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return n.a(new k(localUnsupportedEncodingException));
    }
    catch (JSONException localJSONException)
    {
      return n.a(new k(localJSONException));
    }
  }

  public void a()
  {
    if (!this.b)
    {
      AlimmContext.getAliContext().getReportQueue().a(this);
      this.b = true;
    }
  }

  public static class a
  {
    private static final Random k = new Random();
    private static final int l = 32767;
    List<Promoter> a = new ArrayList();
    private MMEntity b;
    private String c;
    private String d;
    private String e;
    private int f;
    private int g;
    private int h;
    private String i;
    private String j;
    private int m = 0;
    private String n = "";
    private int o = 1;

    public a(MMEntity paramMMEntity)
    {
      this.b = paramMMEntity;
    }

    private boolean a(Map<String, Object> paramMap)
    {
      boolean bool = true;
      if (s.a == null)
      {
        String[] arrayOfString2 = new String[12];
        arrayOfString2[0] = "category";
        arrayOfString2[bool] = "sid";
        arrayOfString2[2] = "device_id";
        arrayOfString2[3] = "idmd5";
        arrayOfString2[4] = "mc";
        arrayOfString2[5] = "action_type";
        arrayOfString2[6] = "action_index";
        arrayOfString2[7] = "layout_type";
        arrayOfString2[8] = "time";
        arrayOfString2[9] = "date";
        arrayOfString2[10] = "access";
        arrayOfString2[11] = "access_subtype";
        s.a = arrayOfString2;
      }
      if ((paramMap == null) || (paramMap.size() == 0))
        bool = false;
      while (true)
      {
        return bool;
        for (String str : s.a)
          if (!paramMap.containsKey(str))
          {
            Log.e(ExchangeConstants.LOG_TAG, "Report params has no required param [" + str + "]");
            bool = false;
          }
      }
    }

    // ERROR //
    private Map<String, Object> c()
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore_1
      //   2: invokestatic 133	com/taobao/newxp/common/AlimmContext:getAliContext	()Lcom/taobao/newxp/common/AlimmContext;
      //   5: astore_2
      //   6: aload_2
      //   7: invokevirtual 137	com/taobao/newxp/common/AlimmContext:getAppUtils	()Lcom/taobao/munion/base/a;
      //   10: astore_3
      //   11: new 139	java/util/HashMap
      //   14: dup
      //   15: invokespecial 140	java/util/HashMap:<init>	()V
      //   18: astore 4
      //   20: aload_0
      //   21: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   24: getfield 145	com/taobao/newxp/net/MMEntity:slotId	Ljava/lang/String;
      //   27: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   30: ifne +267 -> 297
      //   33: aload 4
      //   35: ldc 153
      //   37: aload_0
      //   38: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   41: getfield 145	com/taobao/newxp/net/MMEntity:slotId	Ljava/lang/String;
      //   44: invokeinterface 157 3 0
      //   49: pop
      //   50: aload_0
      //   51: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   54: invokevirtual 160	com/taobao/newxp/net/MMEntity:getTimeConsuming	()Ljava/lang/String;
      //   57: astore 7
      //   59: aload 7
      //   61: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   64: ifne +15 -> 79
      //   67: aload 4
      //   69: ldc 162
      //   71: aload 7
      //   73: invokeinterface 157 3 0
      //   78: pop
      //   79: aload_0
      //   80: getfield 164	com/taobao/newxp/net/s$a:i	Ljava/lang/String;
      //   83: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   86: ifne +17 -> 103
      //   89: aload 4
      //   91: ldc 166
      //   93: aload_0
      //   94: getfield 164	com/taobao/newxp/net/s$a:i	Ljava/lang/String;
      //   97: invokeinterface 157 3 0
      //   102: pop
      //   103: aload_0
      //   104: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   107: getfield 169	com/taobao/newxp/net/MMEntity:tabId	Ljava/lang/String;
      //   110: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   113: ifne +20 -> 133
      //   116: aload 4
      //   118: ldc 171
      //   120: aload_0
      //   121: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   124: getfield 169	com/taobao/newxp/net/MMEntity:tabId	Ljava/lang/String;
      //   127: invokeinterface 157 3 0
      //   132: pop
      //   133: aload_0
      //   134: getfield 173	com/taobao/newxp/net/s$a:j	Ljava/lang/String;
      //   137: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   140: ifne +17 -> 157
      //   143: aload 4
      //   145: ldc 175
      //   147: aload_0
      //   148: getfield 173	com/taobao/newxp/net/s$a:j	Ljava/lang/String;
      //   151: invokeinterface 157 3 0
      //   156: pop
      //   157: aload 4
      //   159: ldc 177
      //   161: getstatic 179	com/taobao/newxp/common/ExchangeConstants:sdk_version	Ljava/lang/String;
      //   164: invokeinterface 157 3 0
      //   169: pop
      //   170: aload 4
      //   172: ldc 181
      //   174: getstatic 183	com/taobao/newxp/common/ExchangeConstants:protocol_version	Ljava/lang/String;
      //   177: invokeinterface 157 3 0
      //   182: pop
      //   183: aload 4
      //   185: ldc 185
      //   187: invokestatic 191	java/lang/System:currentTimeMillis	()J
      //   190: invokestatic 197	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   193: invokeinterface 157 3 0
      //   198: pop
      //   199: aload 4
      //   201: ldc 199
      //   203: getstatic 204	android/os/Build:MODEL	Ljava/lang/String;
      //   206: invokeinterface 157 3 0
      //   211: pop
      //   212: aload_0
      //   213: getfield 43	com/taobao/newxp/net/s$a:n	Ljava/lang/String;
      //   216: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   219: istore 12
      //   221: iload 12
      //   223: ifne +189 -> 412
      //   226: aload_0
      //   227: getfield 43	com/taobao/newxp/net/s$a:n	Ljava/lang/String;
      //   230: ldc 206
      //   232: invokevirtual 210	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   235: astore 61
      //   237: new 139	java/util/HashMap
      //   240: dup
      //   241: invokespecial 140	java/util/HashMap:<init>	()V
      //   244: astore 62
      //   246: aload 61
      //   248: arraylength
      //   249: istore 63
      //   251: iload_1
      //   252: iload 63
      //   254: if_icmpge +98 -> 352
      //   257: aload 61
      //   259: iload_1
      //   260: aaload
      //   261: ldc 212
      //   263: invokevirtual 210	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   266: astore 64
      //   268: aload 64
      //   270: arraylength
      //   271: iconst_2
      //   272: if_icmpne +19 -> 291
      //   275: aload 62
      //   277: aload 64
      //   279: iconst_0
      //   280: aaload
      //   281: aload 64
      //   283: iconst_1
      //   284: aaload
      //   285: invokeinterface 157 3 0
      //   290: pop
      //   291: iinc 1 1
      //   294: goto -43 -> 251
      //   297: aload_0
      //   298: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   301: getfield 215	com/taobao/newxp/net/MMEntity:appkey	Ljava/lang/String;
      //   304: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   307: ifne +35 -> 342
      //   310: aload 4
      //   312: ldc 217
      //   314: aload_0
      //   315: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   318: getfield 215	com/taobao/newxp/net/MMEntity:appkey	Ljava/lang/String;
      //   321: invokeinterface 157 3 0
      //   326: pop
      //   327: goto -277 -> 50
      //   330: astore 5
      //   332: new 219	java/lang/RuntimeException
      //   335: dup
      //   336: aload 5
      //   338: invokespecial 222	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
      //   341: athrow
      //   342: getstatic 102	com/taobao/newxp/common/ExchangeConstants:LOG_TAG	Ljava/lang/String;
      //   345: ldc 224
      //   347: invokestatic 226	com/taobao/newxp/common/Log:b	(Ljava/lang/String;Ljava/lang/String;)V
      //   350: aconst_null
      //   351: areturn
      //   352: aload 62
      //   354: invokeinterface 230 1 0
      //   359: invokeinterface 236 1 0
      //   364: astore 66
      //   366: aload 66
      //   368: invokeinterface 242 1 0
      //   373: ifeq +39 -> 412
      //   376: aload 66
      //   378: invokeinterface 246 1 0
      //   383: checkcast 63	java/lang/String
      //   386: astore 67
      //   388: aload 4
      //   390: aload 67
      //   392: aload 62
      //   394: aload 67
      //   396: invokeinterface 250 2 0
      //   401: invokeinterface 157 3 0
      //   406: pop
      //   407: goto -41 -> 366
      //   410: astore 60
      //   412: aload_3
      //   413: invokeinterface 255 1 0
      //   418: astore 13
      //   420: aload 13
      //   422: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   425: ifne +15 -> 440
      //   428: aload 4
      //   430: ldc 73
      //   432: aload 13
      //   434: invokeinterface 157 3 0
      //   439: pop
      //   440: aload 4
      //   442: ldc_w 257
      //   445: aload_3
      //   446: invokeinterface 260 1 0
      //   451: invokeinterface 157 3 0
      //   456: pop
      //   457: aload_0
      //   458: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   461: getfield 264	com/taobao/newxp/net/MMEntity:module	Lcom/taobao/newxp/a;
      //   464: ifnull +21 -> 485
      //   467: aload 4
      //   469: ldc_w 265
      //   472: aload_0
      //   473: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   476: getfield 264	com/taobao/newxp/net/MMEntity:module	Lcom/taobao/newxp/a;
      //   479: invokeinterface 157 3 0
      //   484: pop
      //   485: aload 4
      //   487: ldc_w 267
      //   490: getstatic 272	android/os/Build$VERSION:RELEASE	Ljava/lang/String;
      //   493: invokeinterface 157 3 0
      //   498: pop
      //   499: aload 4
      //   501: ldc_w 274
      //   504: ldc_w 276
      //   507: invokeinterface 157 3 0
      //   512: pop
      //   513: aload 4
      //   515: ldc_w 278
      //   518: getstatic 35	com/taobao/newxp/net/s$a:k	Ljava/util/Random;
      //   521: sipush 32767
      //   524: invokevirtual 282	java/util/Random:nextInt	(I)I
      //   527: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   530: invokeinterface 157 3 0
      //   535: pop
      //   536: aload 4
      //   538: ldc_w 289
      //   541: aload_3
      //   542: invokeinterface 292 1 0
      //   547: invokeinterface 157 3 0
      //   552: pop
      //   553: aload_3
      //   554: invokeinterface 296 1 0
      //   559: astore 20
      //   561: aload 4
      //   563: ldc 85
      //   565: aload 20
      //   567: iconst_0
      //   568: aaload
      //   569: invokeinterface 157 3 0
      //   574: pop
      //   575: aload 4
      //   577: ldc 87
      //   579: aload 20
      //   581: iconst_1
      //   582: aaload
      //   583: invokeinterface 157 3 0
      //   588: pop
      //   589: aload_0
      //   590: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   593: getfield 298	com/taobao/newxp/net/MMEntity:sid	Ljava/lang/String;
      //   596: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   599: ifne +20 -> 619
      //   602: aload 4
      //   604: ldc 67
      //   606: aload_0
      //   607: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   610: getfield 298	com/taobao/newxp/net/MMEntity:sid	Ljava/lang/String;
      //   613: invokeinterface 157 3 0
      //   618: pop
      //   619: aload_0
      //   620: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   623: getfield 301	com/taobao/newxp/net/MMEntity:psid	Ljava/lang/String;
      //   626: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   629: ifne +21 -> 650
      //   632: aload 4
      //   634: ldc_w 302
      //   637: aload_0
      //   638: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   641: getfield 301	com/taobao/newxp/net/MMEntity:psid	Ljava/lang/String;
      //   644: invokeinterface 157 3 0
      //   649: pop
      //   650: aload 4
      //   652: ldc 69
      //   654: aload_3
      //   655: invokeinterface 305 1 0
      //   660: invokeinterface 157 3 0
      //   665: pop
      //   666: aload 4
      //   668: ldc 71
      //   670: aload_3
      //   671: invokeinterface 305 1 0
      //   676: invokestatic 310	com/taobao/newxp/common/b/b:a	(Ljava/lang/String;)Ljava/lang/String;
      //   679: invokeinterface 157 3 0
      //   684: pop
      //   685: aload_2
      //   686: invokevirtual 314	com/taobao/newxp/common/AlimmContext:getAppContext	()Landroid/content/Context;
      //   689: aload_3
      //   690: invokestatic 319	com/taobao/newxp/net/b:a	(Landroid/content/Context;Lcom/taobao/munion/base/a;)Landroid/location/Location;
      //   693: astore 49
      //   695: aload 49
      //   697: ifnull +95 -> 792
      //   700: aload 4
      //   702: ldc_w 321
      //   705: aload 49
      //   707: invokevirtual 327	android/location/Location:getLatitude	()D
      //   710: invokestatic 330	java/lang/String:valueOf	(D)Ljava/lang/String;
      //   713: invokeinterface 157 3 0
      //   718: pop
      //   719: aload 4
      //   721: ldc_w 332
      //   724: aload 49
      //   726: invokevirtual 335	android/location/Location:getLongitude	()D
      //   729: invokestatic 330	java/lang/String:valueOf	(D)Ljava/lang/String;
      //   732: invokeinterface 157 3 0
      //   737: pop
      //   738: aload 4
      //   740: ldc_w 337
      //   743: aload 49
      //   745: invokevirtual 340	android/location/Location:getProvider	()Ljava/lang/String;
      //   748: invokeinterface 157 3 0
      //   753: pop
      //   754: aload 4
      //   756: ldc_w 342
      //   759: aload 49
      //   761: invokevirtual 345	android/location/Location:getTime	()J
      //   764: invokestatic 348	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   767: invokeinterface 157 3 0
      //   772: pop
      //   773: aload 4
      //   775: ldc_w 350
      //   778: aload 49
      //   780: invokevirtual 354	android/location/Location:getAccuracy	()F
      //   783: invokestatic 357	java/lang/String:valueOf	(F)Ljava/lang/String;
      //   786: invokeinterface 157 3 0
      //   791: pop
      //   792: new 359	java/text/SimpleDateFormat
      //   795: dup
      //   796: ldc_w 361
      //   799: invokespecial 364	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
      //   802: new 366	java/util/Date
      //   805: dup
      //   806: invokespecial 367	java/util/Date:<init>	()V
      //   809: invokevirtual 373	java/text/DateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
      //   812: astore 26
      //   814: aload 26
      //   816: ldc_w 375
      //   819: invokevirtual 210	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   822: iconst_0
      //   823: aaload
      //   824: astore 27
      //   826: aload 26
      //   828: ldc_w 375
      //   831: invokevirtual 210	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   834: iconst_1
      //   835: aaload
      //   836: astore 28
      //   838: aload 4
      //   840: ldc 83
      //   842: aload 27
      //   844: invokeinterface 157 3 0
      //   849: pop
      //   850: aload 4
      //   852: ldc 81
      //   854: aload 28
      //   856: invokeinterface 157 3 0
      //   861: pop
      //   862: aload 4
      //   864: ldc_w 377
      //   867: aload_3
      //   868: invokeinterface 379 1 0
      //   873: invokeinterface 157 3 0
      //   878: pop
      //   879: aload 4
      //   881: ldc_w 381
      //   884: aload_0
      //   885: getfield 383	com/taobao/newxp/net/s$a:c	Ljava/lang/String;
      //   888: invokeinterface 157 3 0
      //   893: pop
      //   894: aload_0
      //   895: getfield 55	com/taobao/newxp/net/s$a:d	Ljava/lang/String;
      //   898: ifnull +321 -> 1219
      //   901: aload_0
      //   902: getfield 55	com/taobao/newxp/net/s$a:d	Ljava/lang/String;
      //   905: astore 33
      //   907: aload 4
      //   909: ldc_w 385
      //   912: aload 33
      //   914: invokeinterface 157 3 0
      //   919: pop
      //   920: aload 4
      //   922: ldc 65
      //   924: aload_0
      //   925: getfield 124	com/taobao/newxp/net/s$a:e	Ljava/lang/String;
      //   928: invokeinterface 157 3 0
      //   933: pop
      //   934: aload 4
      //   936: ldc 75
      //   938: aload_0
      //   939: getfield 387	com/taobao/newxp/net/s$a:f	I
      //   942: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   945: invokeinterface 157 3 0
      //   950: pop
      //   951: aload 4
      //   953: ldc 77
      //   955: aload_0
      //   956: getfield 389	com/taobao/newxp/net/s$a:g	I
      //   959: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   962: invokeinterface 157 3 0
      //   967: pop
      //   968: aload 4
      //   970: ldc 79
      //   972: aload_0
      //   973: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   976: getfield 392	com/taobao/newxp/net/MMEntity:layoutType	I
      //   979: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   982: invokeinterface 157 3 0
      //   987: pop
      //   988: aload 4
      //   990: ldc_w 394
      //   993: aload_0
      //   994: getfield 396	com/taobao/newxp/net/s$a:h	I
      //   997: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1000: invokeinterface 157 3 0
      //   1005: pop
      //   1006: getstatic 399	com/taobao/newxp/common/ExchangeConstants:CHANNEL	Ljava/lang/String;
      //   1009: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   1012: ifeq +167 -> 1179
      //   1015: aload_3
      //   1016: ldc_w 401
      //   1019: invokeinterface 403 2 0
      //   1024: astore 40
      //   1026: aload 40
      //   1028: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   1031: ifne +16 -> 1047
      //   1034: aload 4
      //   1036: ldc_w 405
      //   1039: aload 40
      //   1041: invokeinterface 157 3 0
      //   1046: pop
      //   1047: aload_0
      //   1048: getfield 39	com/taobao/newxp/net/s$a:m	I
      //   1051: ifeq +21 -> 1072
      //   1054: aload 4
      //   1056: ldc_w 407
      //   1059: aload_0
      //   1060: getfield 39	com/taobao/newxp/net/s$a:m	I
      //   1063: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1066: invokeinterface 157 3 0
      //   1071: pop
      //   1072: aload 4
      //   1074: ldc_w 409
      //   1077: aload_0
      //   1078: getfield 45	com/taobao/newxp/net/s$a:o	I
      //   1081: invokestatic 287	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1084: invokeinterface 157 3 0
      //   1089: pop
      //   1090: aload_0
      //   1091: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   1094: getfield 145	com/taobao/newxp/net/MMEntity:slotId	Ljava/lang/String;
      //   1097: invokestatic 151	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   1100: ifeq +87 -> 1187
      //   1103: aload_0
      //   1104: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   1107: getfield 215	com/taobao/newxp/net/MMEntity:appkey	Ljava/lang/String;
      //   1110: astore 42
      //   1112: new 411	com/taobao/newxp/controller/g
      //   1115: dup
      //   1116: aload_2
      //   1117: invokevirtual 314	com/taobao/newxp/common/AlimmContext:getAppContext	()Landroid/content/Context;
      //   1120: aload 42
      //   1122: invokespecial 414	com/taobao/newxp/controller/g:<init>	(Landroid/content/Context;Ljava/lang/String;)V
      //   1125: astore 43
      //   1127: aload 43
      //   1129: invokevirtual 416	com/taobao/newxp/controller/g:b	()Z
      //   1132: ifeq +28 -> 1160
      //   1135: aload 43
      //   1137: invokevirtual 418	com/taobao/newxp/controller/g:c	()Ljava/lang/String;
      //   1140: astore 45
      //   1142: aload 45
      //   1144: ifnull +16 -> 1160
      //   1147: aload 4
      //   1149: ldc_w 420
      //   1152: aload 45
      //   1154: invokeinterface 157 3 0
      //   1159: pop
      //   1160: aload 4
      //   1162: areturn
      //   1163: astore 18
      //   1165: getstatic 102	com/taobao/newxp/common/ExchangeConstants:LOG_TAG	Ljava/lang/String;
      //   1168: ldc 41
      //   1170: aload 18
      //   1172: invokestatic 426	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   1175: pop
      //   1176: goto -623 -> 553
      //   1179: getstatic 399	com/taobao/newxp/common/ExchangeConstants:CHANNEL	Ljava/lang/String;
      //   1182: astore 40
      //   1184: goto -158 -> 1026
      //   1187: aload_0
      //   1188: getfield 52	com/taobao/newxp/net/s$a:b	Lcom/taobao/newxp/net/MMEntity;
      //   1191: getfield 145	com/taobao/newxp/net/MMEntity:slotId	Ljava/lang/String;
      //   1194: astore 42
      //   1196: goto -84 -> 1112
      //   1199: astore 44
      //   1201: getstatic 102	com/taobao/newxp/common/ExchangeConstants:LOG_TAG	Ljava/lang/String;
      //   1204: ldc 41
      //   1206: aload 44
      //   1208: invokestatic 429	com/taobao/newxp/common/Log:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
      //   1211: goto -51 -> 1160
      //   1214: astore 25
      //   1216: goto -424 -> 792
      //   1219: ldc 41
      //   1221: astore 33
      //   1223: goto -316 -> 907
      //
      // Exception table:
      //   from	to	target	type
      //   20	50	330	java/lang/Exception
      //   50	79	330	java/lang/Exception
      //   79	103	330	java/lang/Exception
      //   103	133	330	java/lang/Exception
      //   133	157	330	java/lang/Exception
      //   157	221	330	java/lang/Exception
      //   297	327	330	java/lang/Exception
      //   342	350	330	java/lang/Exception
      //   412	440	330	java/lang/Exception
      //   440	485	330	java/lang/Exception
      //   485	536	330	java/lang/Exception
      //   553	619	330	java/lang/Exception
      //   619	650	330	java/lang/Exception
      //   650	685	330	java/lang/Exception
      //   792	907	330	java/lang/Exception
      //   907	1026	330	java/lang/Exception
      //   1026	1047	330	java/lang/Exception
      //   1047	1072	330	java/lang/Exception
      //   1072	1112	330	java/lang/Exception
      //   1165	1176	330	java/lang/Exception
      //   1179	1184	330	java/lang/Exception
      //   1187	1196	330	java/lang/Exception
      //   1201	1211	330	java/lang/Exception
      //   226	251	410	java/lang/Exception
      //   257	291	410	java/lang/Exception
      //   352	366	410	java/lang/Exception
      //   366	407	410	java/lang/Exception
      //   536	553	1163	java/lang/Exception
      //   1112	1142	1199	java/lang/Exception
      //   1147	1160	1199	java/lang/Exception
      //   685	695	1214	java/lang/Exception
      //   700	792	1214	java/lang/Exception
    }

    public a a(int paramInt)
    {
      this.f = paramInt;
      return this;
    }

    public a a(String paramString)
    {
      this.i = paramString;
      return this;
    }

    public a a(Promoter[] paramArrayOfPromoter)
    {
      int i1 = 0;
      int i2 = paramArrayOfPromoter.length;
      for (int i3 = 0; i3 < i2; i3++)
      {
        Promoter localPromoter3 = paramArrayOfPromoter[i3];
        this.a.add(localPromoter3);
      }
      if ((this.a != null) && (this.a.size() > 0))
      {
        int i4 = this.a.size();
        try
        {
          Promoter localPromoter2 = (Promoter)this.a.get(0);
          if (this.a.size() == 1)
          {
            this.n = localPromoter2.prom_act_pams;
            Log.a(ExchangeConstants.LOG_TAG, "set promoter act_pams to report act_params. [" + this.n + "]");
          }
          while (true)
          {
            label139: localStringBuffer1 = new StringBuffer();
            localStringBuffer2 = new StringBuffer();
            while (i1 < i4)
            {
              Promoter localPromoter1 = (Promoter)this.a.get(i1);
              localStringBuffer1.append(localPromoter1.promoter + ",");
              localStringBuffer2.append(localPromoter1.category + ",");
              i1++;
            }
            this.n = localPromoter2.slot_act_pams;
            Log.a(ExchangeConstants.LOG_TAG, "set slot act_pams to report act_params. [" + this.n + "]");
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          StringBuffer localStringBuffer1;
          StringBuffer localStringBuffer2;
          break label139;
          localStringBuffer1.deleteCharAt(-1 + localStringBuffer1.length());
          this.d = localStringBuffer1.toString();
          this.e = localStringBuffer2.toString();
        }
      }
      return this;
    }

    public s a()
    {
      Map localMap = b();
      return new s(b.a(n.f[0], localMap), null, null);
    }

    public a b(int paramInt)
    {
      this.g = paramInt;
      return this;
    }

    public a b(String paramString)
    {
      this.j = paramString;
      return this;
    }

    public Map<String, Object> b()
    {
      if (AlimmContext.getAliContext().getAppUtils().l());
      for (String str = "0"; ; str = "1")
      {
        this.c = str;
        Map localMap = c();
        if ((ExchangeConstants.DEBUG_MODE) && (!a(localMap)))
          Log.e(ExchangeConstants.LOG_TAG, "Report params verify failed...");
        return localMap;
      }
    }

    public a c(int paramInt)
    {
      this.h = paramInt;
      return this;
    }

    public a d(int paramInt)
    {
      this.o = paramInt;
      return this;
    }

    public a e(int paramInt)
    {
      this.m = paramInt;
      return this;
    }
  }

  public static class b extends s.a
  {
    public b(MMEntity paramMMEntity)
    {
      super();
      s.a.a(this, "custom");
      s.a.b(this, "-1");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.s
 * JD-Core Version:    0.6.2
 */