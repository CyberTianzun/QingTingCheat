package com.miaozhen.mzmonitor;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;

class c
{
  private static c a;
  private Context b;
  private c c;
  private String d;

  private c(Context paramContext)
  {
    this.b = paramContext;
  }

  // ERROR //
  private c a(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: new 26	com/miaozhen/mzmonitor/c$c
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 29	com/miaozhen/mzmonitor/c$c:<init>	(Lcom/miaozhen/mzmonitor/c;)V
    //   8: astore_2
    //   9: iconst_0
    //   10: istore_3
    //   11: invokestatic 35	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   14: astore 10
    //   16: aload 10
    //   18: aload_1
    //   19: ldc 37
    //   21: invokeinterface 43 3 0
    //   26: aload 10
    //   28: invokeinterface 47 1 0
    //   33: istore 11
    //   35: iload 11
    //   37: istore 12
    //   39: aconst_null
    //   40: astore 13
    //   42: aconst_null
    //   43: astore 14
    //   45: aconst_null
    //   46: astore 15
    //   48: iload 12
    //   50: iconst_1
    //   51: if_icmpne +27 -> 78
    //   54: aload_0
    //   55: aconst_null
    //   56: putfield 49	com/miaozhen/mzmonitor/c:d	Ljava/lang/String;
    //   59: aload_1
    //   60: ifnull +1098 -> 1158
    //   63: aload_1
    //   64: invokevirtual 54	java/io/InputStream:close	()V
    //   67: aload 13
    //   69: pop
    //   70: aload 14
    //   72: pop
    //   73: aload 15
    //   75: pop
    //   76: aload_2
    //   77: areturn
    //   78: iload 12
    //   80: tableswitch	default:+32 -> 112, 0:+72->152, 1:+32->112, 2:+87->167, 3:+866->946
    //   113: fconst_2
    //   114: astore 36
    //   116: aload 14
    //   118: astore 32
    //   120: aload 15
    //   122: astore 25
    //   124: aload 10
    //   126: invokeinterface 57 1 0
    //   131: istore 37
    //   133: iload 37
    //   135: istore 12
    //   137: aload 36
    //   139: astore 13
    //   141: aload 32
    //   143: astore 14
    //   145: aload 25
    //   147: astore 15
    //   149: goto -101 -> 48
    //   152: aload 13
    //   154: astore 36
    //   156: aload 14
    //   158: astore 32
    //   160: aload 15
    //   162: astore 25
    //   164: goto -40 -> 124
    //   167: aload_0
    //   168: aload 10
    //   170: invokeinterface 61 1 0
    //   175: putfield 49	com/miaozhen/mzmonitor/c:d	Ljava/lang/String;
    //   178: aload_0
    //   179: ldc 63
    //   181: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   184: ifeq +14 -> 198
    //   187: aload_2
    //   188: new 68	java/util/ArrayList
    //   191: dup
    //   192: invokespecial 69	java/util/ArrayList:<init>	()V
    //   195: putfield 72	com/miaozhen/mzmonitor/c$c:a	Ljava/util/List;
    //   198: aload_2
    //   199: getfield 72	com/miaozhen/mzmonitor/c$c:a	Ljava/util/List;
    //   202: ifnull +1098 -> 1300
    //   205: aload_0
    //   206: ldc 74
    //   208: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   211: ifeq +1089 -> 1300
    //   214: new 76	com/miaozhen/mzmonitor/c$b
    //   217: dup
    //   218: aload_0
    //   219: invokespecial 77	com/miaozhen/mzmonitor/c$b:<init>	(Lcom/miaozhen/mzmonitor/c;)V
    //   222: astore 25
    //   224: aload 25
    //   226: ifnull +1063 -> 1289
    //   229: aload_0
    //   230: ldc 79
    //   232: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   235: ifeq +35 -> 270
    //   238: iload_3
    //   239: ifne +31 -> 270
    //   242: aload 13
    //   244: ifnonnull +26 -> 270
    //   247: aload 25
    //   249: aload 10
    //   251: invokeinterface 82 1 0
    //   256: putfield 84	com/miaozhen/mzmonitor/c$b:a	Ljava/lang/String;
    //   259: aload 13
    //   261: astore 36
    //   263: aload 14
    //   265: astore 32
    //   267: goto -143 -> 124
    //   270: aload_0
    //   271: ldc 86
    //   273: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   276: ifeq +52 -> 328
    //   279: aload 25
    //   281: getfield 88	com/miaozhen/mzmonitor/c$b:b	Ljava/util/List;
    //   284: ifnonnull +15 -> 299
    //   287: aload 25
    //   289: new 68	java/util/ArrayList
    //   292: dup
    //   293: invokespecial 69	java/util/ArrayList:<init>	()V
    //   296: putfield 88	com/miaozhen/mzmonitor/c$b:b	Ljava/util/List;
    //   299: aload 25
    //   301: getfield 88	com/miaozhen/mzmonitor/c$b:b	Ljava/util/List;
    //   304: aload 10
    //   306: invokeinterface 82 1 0
    //   311: invokeinterface 94 2 0
    //   316: pop
    //   317: aload 13
    //   319: astore 36
    //   321: aload 14
    //   323: astore 32
    //   325: goto -201 -> 124
    //   328: aload_0
    //   329: ldc 96
    //   331: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   334: ifeq +26 -> 360
    //   337: aload 25
    //   339: aload 10
    //   341: invokeinterface 82 1 0
    //   346: putfield 98	com/miaozhen/mzmonitor/c$b:c	Ljava/lang/String;
    //   349: aload 13
    //   351: astore 36
    //   353: aload 14
    //   355: astore 32
    //   357: goto -233 -> 124
    //   360: aload_0
    //   361: ldc 100
    //   363: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   366: ifeq +26 -> 392
    //   369: aload 25
    //   371: aload 10
    //   373: invokeinterface 82 1 0
    //   378: putfield 101	com/miaozhen/mzmonitor/c$b:d	Ljava/lang/String;
    //   381: aload 13
    //   383: astore 36
    //   385: aload 14
    //   387: astore 32
    //   389: goto -265 -> 124
    //   392: aload_0
    //   393: ldc 103
    //   395: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   398: ifeq +26 -> 424
    //   401: aload 25
    //   403: aload 10
    //   405: invokeinterface 82 1 0
    //   410: putfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   413: aload 13
    //   415: astore 36
    //   417: aload 14
    //   419: astore 32
    //   421: goto -297 -> 124
    //   424: aload_0
    //   425: ldc 108
    //   427: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   430: ifeq +52 -> 482
    //   433: aload 10
    //   435: invokeinterface 82 1 0
    //   440: invokevirtual 113	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   443: astore 44
    //   445: aload 44
    //   447: ldc 115
    //   449: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   452: ifne +13 -> 465
    //   455: aload 44
    //   457: ldc 120
    //   459: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   462: ifeq +827 -> 1289
    //   465: aload 25
    //   467: iconst_0
    //   468: putfield 124	com/miaozhen/mzmonitor/c$b:j	Z
    //   471: aload 13
    //   473: astore 36
    //   475: aload 14
    //   477: astore 32
    //   479: goto -355 -> 124
    //   482: aload_0
    //   483: ldc 126
    //   485: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   488: ifeq +52 -> 540
    //   491: aload 10
    //   493: invokeinterface 82 1 0
    //   498: invokevirtual 113	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   501: astore 43
    //   503: aload 43
    //   505: ldc 128
    //   507: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   510: ifne +13 -> 523
    //   513: aload 43
    //   515: ldc 130
    //   517: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   520: ifeq +769 -> 1289
    //   523: aload 25
    //   525: iconst_1
    //   526: putfield 133	com/miaozhen/mzmonitor/c$b:i	Z
    //   529: aload 13
    //   531: astore 36
    //   533: aload 14
    //   535: astore 32
    //   537: goto -413 -> 124
    //   540: aload_0
    //   541: ldc 135
    //   543: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   546: ifeq +28 -> 574
    //   549: aload 25
    //   551: new 68	java/util/ArrayList
    //   554: dup
    //   555: invokespecial 69	java/util/ArrayList:<init>	()V
    //   558: putfield 138	com/miaozhen/mzmonitor/c$b:g	Ljava/util/List;
    //   561: iconst_1
    //   562: istore_3
    //   563: aload 13
    //   565: astore 36
    //   567: aload 14
    //   569: astore 32
    //   571: goto -447 -> 124
    //   574: iload_3
    //   575: ifeq +154 -> 729
    //   578: aload_0
    //   579: ldc 140
    //   581: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   584: ifeq +26 -> 610
    //   587: aload 14
    //   589: aload 10
    //   591: invokeinterface 82 1 0
    //   596: putfield 143	com/miaozhen/mzmonitor/c$a:c	Ljava/lang/String;
    //   599: aload 13
    //   601: astore 36
    //   603: aload 14
    //   605: astore 32
    //   607: goto -483 -> 124
    //   610: aload_0
    //   611: ldc 79
    //   613: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   616: ifeq +26 -> 642
    //   619: aload 14
    //   621: aload 10
    //   623: invokeinterface 82 1 0
    //   628: putfield 145	com/miaozhen/mzmonitor/c$a:b	Ljava/lang/String;
    //   631: aload 13
    //   633: astore 36
    //   635: aload 14
    //   637: astore 32
    //   639: goto -515 -> 124
    //   642: aload_0
    //   643: ldc 147
    //   645: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   648: ifeq +52 -> 700
    //   651: aload 10
    //   653: invokeinterface 82 1 0
    //   658: invokevirtual 113	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   661: astore 41
    //   663: aload 41
    //   665: ldc 115
    //   667: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   670: ifne +13 -> 683
    //   673: aload 41
    //   675: ldc 120
    //   677: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   680: ifeq +609 -> 1289
    //   683: aload 14
    //   685: iconst_0
    //   686: putfield 149	com/miaozhen/mzmonitor/c$a:d	Z
    //   689: aload 13
    //   691: astore 36
    //   693: aload 14
    //   695: astore 32
    //   697: goto -573 -> 124
    //   700: new 142	com/miaozhen/mzmonitor/c$a
    //   703: dup
    //   704: aload_0
    //   705: invokespecial 150	com/miaozhen/mzmonitor/c$a:<init>	(Lcom/miaozhen/mzmonitor/c;)V
    //   708: astore 32
    //   710: aload 32
    //   712: aload 10
    //   714: invokeinterface 61 1 0
    //   719: putfield 151	com/miaozhen/mzmonitor/c$a:a	Ljava/lang/String;
    //   722: aload 13
    //   724: astore 36
    //   726: goto -602 -> 124
    //   729: aload_0
    //   730: ldc 153
    //   732: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   735: ifeq +26 -> 761
    //   738: aload 25
    //   740: new 68	java/util/ArrayList
    //   743: dup
    //   744: invokespecial 69	java/util/ArrayList:<init>	()V
    //   747: putfield 156	com/miaozhen/mzmonitor/c$b:h	Ljava/util/List;
    //   750: aload 13
    //   752: astore 36
    //   754: aload 14
    //   756: astore 32
    //   758: goto -634 -> 124
    //   761: aload_0
    //   762: ldc 158
    //   764: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   767: ifeq +20 -> 787
    //   770: new 160	com/miaozhen/mzmonitor/c$d
    //   773: dup
    //   774: aload_0
    //   775: invokespecial 161	com/miaozhen/mzmonitor/c$d:<init>	(Lcom/miaozhen/mzmonitor/c;)V
    //   778: astore 36
    //   780: aload 14
    //   782: astore 32
    //   784: goto -660 -> 124
    //   787: aload 13
    //   789: ifnull +500 -> 1289
    //   792: aload_0
    //   793: ldc 163
    //   795: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   798: ifeq +26 -> 824
    //   801: aload 13
    //   803: aload 10
    //   805: invokeinterface 82 1 0
    //   810: putfield 164	com/miaozhen/mzmonitor/c$d:a	Ljava/lang/String;
    //   813: aload 13
    //   815: astore 36
    //   817: aload 14
    //   819: astore 32
    //   821: goto -697 -> 124
    //   824: aload_0
    //   825: ldc 79
    //   827: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   830: ifeq +26 -> 856
    //   833: aload 13
    //   835: aload 10
    //   837: invokeinterface 82 1 0
    //   842: putfield 165	com/miaozhen/mzmonitor/c$d:b	Ljava/lang/String;
    //   845: aload 13
    //   847: astore 36
    //   849: aload 14
    //   851: astore 32
    //   853: goto -729 -> 124
    //   856: aload_0
    //   857: ldc 167
    //   859: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   862: ifeq +26 -> 888
    //   865: aload 13
    //   867: aload 10
    //   869: invokeinterface 82 1 0
    //   874: putfield 168	com/miaozhen/mzmonitor/c$d:c	Ljava/lang/String;
    //   877: aload 13
    //   879: astore 36
    //   881: aload 14
    //   883: astore 32
    //   885: goto -761 -> 124
    //   888: aload_0
    //   889: ldc 147
    //   891: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   894: ifeq +395 -> 1289
    //   897: aload 10
    //   899: invokeinterface 82 1 0
    //   904: invokevirtual 113	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   907: astore 42
    //   909: aload 42
    //   911: ldc 115
    //   913: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   916: ifne +13 -> 929
    //   919: aload 42
    //   921: ldc 120
    //   923: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   926: ifeq +363 -> 1289
    //   929: aload 13
    //   931: iconst_0
    //   932: putfield 169	com/miaozhen/mzmonitor/c$d:d	Z
    //   935: aload 13
    //   937: astore 36
    //   939: aload 14
    //   941: astore 32
    //   943: goto -819 -> 124
    //   946: aload_0
    //   947: aload 10
    //   949: invokeinterface 61 1 0
    //   954: putfield 49	com/miaozhen/mzmonitor/c:d	Ljava/lang/String;
    //   957: aload_0
    //   958: ldc 74
    //   960: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   963: ifeq +319 -> 1282
    //   966: aload_2
    //   967: getfield 72	com/miaozhen/mzmonitor/c$c:a	Ljava/util/List;
    //   970: aload 15
    //   972: invokeinterface 94 2 0
    //   977: pop
    //   978: aconst_null
    //   979: astore 25
    //   981: aload_0
    //   982: ldc 135
    //   984: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   987: ifeq +5 -> 992
    //   990: iconst_0
    //   991: istore_3
    //   992: iload_3
    //   993: ifeq +282 -> 1275
    //   996: aload_0
    //   997: ldc 140
    //   999: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   1002: ifne +273 -> 1275
    //   1005: aload_0
    //   1006: ldc 79
    //   1008: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   1011: ifne +264 -> 1275
    //   1014: aload_0
    //   1015: ldc 147
    //   1017: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   1020: ifne +255 -> 1275
    //   1023: aload_0
    //   1024: ldc 108
    //   1026: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   1029: ifne +246 -> 1275
    //   1032: aload 25
    //   1034: getfield 138	com/miaozhen/mzmonitor/c$b:g	Ljava/util/List;
    //   1037: aload 14
    //   1039: invokeinterface 94 2 0
    //   1044: pop
    //   1045: aconst_null
    //   1046: astore 32
    //   1048: aload_0
    //   1049: ldc 158
    //   1051: invokespecial 66	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;)Z
    //   1054: ifeq +214 -> 1268
    //   1057: aload 25
    //   1059: getfield 156	com/miaozhen/mzmonitor/c$b:h	Ljava/util/List;
    //   1062: aload 13
    //   1064: invokeinterface 94 2 0
    //   1069: pop
    //   1070: aconst_null
    //   1071: astore 36
    //   1073: goto -949 -> 124
    //   1076: astore 8
    //   1078: aload 8
    //   1080: invokevirtual 172	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   1083: aload_1
    //   1084: ifnull -1008 -> 76
    //   1087: aload_1
    //   1088: invokevirtual 54	java/io/InputStream:close	()V
    //   1091: aload_2
    //   1092: areturn
    //   1093: astore 9
    //   1095: aload 9
    //   1097: invokevirtual 173	java/io/IOException:printStackTrace	()V
    //   1100: aload_2
    //   1101: areturn
    //   1102: astore 6
    //   1104: aload 6
    //   1106: invokevirtual 173	java/io/IOException:printStackTrace	()V
    //   1109: aload_1
    //   1110: ifnull -1034 -> 76
    //   1113: aload_1
    //   1114: invokevirtual 54	java/io/InputStream:close	()V
    //   1117: aload_2
    //   1118: areturn
    //   1119: astore 7
    //   1121: aload 7
    //   1123: invokevirtual 173	java/io/IOException:printStackTrace	()V
    //   1126: aload_2
    //   1127: areturn
    //   1128: astore 4
    //   1130: aload_1
    //   1131: ifnull +7 -> 1138
    //   1134: aload_1
    //   1135: invokevirtual 54	java/io/InputStream:close	()V
    //   1138: aload 4
    //   1140: athrow
    //   1141: astore 5
    //   1143: aload 5
    //   1145: invokevirtual 173	java/io/IOException:printStackTrace	()V
    //   1148: goto -10 -> 1138
    //   1151: astore 49
    //   1153: aload 49
    //   1155: invokevirtual 173	java/io/IOException:printStackTrace	()V
    //   1158: aload 13
    //   1160: pop
    //   1161: aload 14
    //   1163: pop
    //   1164: aload 15
    //   1166: pop
    //   1167: aload_2
    //   1168: areturn
    //   1169: astore 4
    //   1171: aload 13
    //   1173: pop
    //   1174: aload 14
    //   1176: pop
    //   1177: aload 15
    //   1179: pop
    //   1180: goto -50 -> 1130
    //   1183: astore 4
    //   1185: aload 13
    //   1187: pop
    //   1188: aload 14
    //   1190: pop
    //   1191: goto -61 -> 1130
    //   1194: astore 4
    //   1196: aload 13
    //   1198: pop
    //   1199: goto -69 -> 1130
    //   1202: astore 6
    //   1204: aload 13
    //   1206: pop
    //   1207: aload 14
    //   1209: pop
    //   1210: aload 15
    //   1212: pop
    //   1213: goto -109 -> 1104
    //   1216: astore 6
    //   1218: aload 13
    //   1220: pop
    //   1221: aload 14
    //   1223: pop
    //   1224: goto -120 -> 1104
    //   1227: astore 6
    //   1229: aload 13
    //   1231: pop
    //   1232: goto -128 -> 1104
    //   1235: astore 8
    //   1237: aload 13
    //   1239: pop
    //   1240: aload 14
    //   1242: pop
    //   1243: aload 15
    //   1245: pop
    //   1246: goto -168 -> 1078
    //   1249: astore 8
    //   1251: aload 13
    //   1253: pop
    //   1254: aload 14
    //   1256: pop
    //   1257: goto -179 -> 1078
    //   1260: astore 8
    //   1262: aload 13
    //   1264: pop
    //   1265: goto -187 -> 1078
    //   1268: aload 13
    //   1270: astore 36
    //   1272: goto -1148 -> 124
    //   1275: aload 14
    //   1277: astore 32
    //   1279: goto -231 -> 1048
    //   1282: aload 15
    //   1284: astore 25
    //   1286: goto -305 -> 981
    //   1289: aload 13
    //   1291: astore 36
    //   1293: aload 14
    //   1295: astore 32
    //   1297: goto -1173 -> 124
    //   1300: aload 15
    //   1302: astore 25
    //   1304: goto -1080 -> 224
    //
    // Exception table:
    //   from	to	target	type
    //   11	35	1076	org/xmlpull/v1/XmlPullParserException
    //   124	133	1076	org/xmlpull/v1/XmlPullParserException
    //   1087	1091	1093	java/io/IOException
    //   11	35	1102	java/io/IOException
    //   124	133	1102	java/io/IOException
    //   1113	1117	1119	java/io/IOException
    //   11	35	1128	finally
    //   124	133	1128	finally
    //   1078	1083	1128	finally
    //   1104	1109	1128	finally
    //   1134	1138	1141	java/io/IOException
    //   63	67	1151	java/io/IOException
    //   54	59	1169	finally
    //   167	198	1169	finally
    //   198	224	1169	finally
    //   946	978	1169	finally
    //   229	238	1183	finally
    //   247	259	1183	finally
    //   270	299	1183	finally
    //   299	317	1183	finally
    //   328	349	1183	finally
    //   360	381	1183	finally
    //   392	413	1183	finally
    //   424	465	1183	finally
    //   465	471	1183	finally
    //   482	523	1183	finally
    //   523	529	1183	finally
    //   540	561	1183	finally
    //   578	599	1183	finally
    //   610	631	1183	finally
    //   642	683	1183	finally
    //   683	689	1183	finally
    //   700	710	1183	finally
    //   729	750	1183	finally
    //   761	780	1183	finally
    //   792	813	1183	finally
    //   824	845	1183	finally
    //   856	877	1183	finally
    //   888	929	1183	finally
    //   929	935	1183	finally
    //   981	990	1183	finally
    //   996	1045	1183	finally
    //   710	722	1194	finally
    //   1048	1070	1194	finally
    //   54	59	1202	java/io/IOException
    //   167	198	1202	java/io/IOException
    //   198	224	1202	java/io/IOException
    //   946	978	1202	java/io/IOException
    //   229	238	1216	java/io/IOException
    //   247	259	1216	java/io/IOException
    //   270	299	1216	java/io/IOException
    //   299	317	1216	java/io/IOException
    //   328	349	1216	java/io/IOException
    //   360	381	1216	java/io/IOException
    //   392	413	1216	java/io/IOException
    //   424	465	1216	java/io/IOException
    //   465	471	1216	java/io/IOException
    //   482	523	1216	java/io/IOException
    //   523	529	1216	java/io/IOException
    //   540	561	1216	java/io/IOException
    //   578	599	1216	java/io/IOException
    //   610	631	1216	java/io/IOException
    //   642	683	1216	java/io/IOException
    //   683	689	1216	java/io/IOException
    //   700	710	1216	java/io/IOException
    //   729	750	1216	java/io/IOException
    //   761	780	1216	java/io/IOException
    //   792	813	1216	java/io/IOException
    //   824	845	1216	java/io/IOException
    //   856	877	1216	java/io/IOException
    //   888	929	1216	java/io/IOException
    //   929	935	1216	java/io/IOException
    //   981	990	1216	java/io/IOException
    //   996	1045	1216	java/io/IOException
    //   710	722	1227	java/io/IOException
    //   1048	1070	1227	java/io/IOException
    //   54	59	1235	org/xmlpull/v1/XmlPullParserException
    //   167	198	1235	org/xmlpull/v1/XmlPullParserException
    //   198	224	1235	org/xmlpull/v1/XmlPullParserException
    //   946	978	1235	org/xmlpull/v1/XmlPullParserException
    //   229	238	1249	org/xmlpull/v1/XmlPullParserException
    //   247	259	1249	org/xmlpull/v1/XmlPullParserException
    //   270	299	1249	org/xmlpull/v1/XmlPullParserException
    //   299	317	1249	org/xmlpull/v1/XmlPullParserException
    //   328	349	1249	org/xmlpull/v1/XmlPullParserException
    //   360	381	1249	org/xmlpull/v1/XmlPullParserException
    //   392	413	1249	org/xmlpull/v1/XmlPullParserException
    //   424	465	1249	org/xmlpull/v1/XmlPullParserException
    //   465	471	1249	org/xmlpull/v1/XmlPullParserException
    //   482	523	1249	org/xmlpull/v1/XmlPullParserException
    //   523	529	1249	org/xmlpull/v1/XmlPullParserException
    //   540	561	1249	org/xmlpull/v1/XmlPullParserException
    //   578	599	1249	org/xmlpull/v1/XmlPullParserException
    //   610	631	1249	org/xmlpull/v1/XmlPullParserException
    //   642	683	1249	org/xmlpull/v1/XmlPullParserException
    //   683	689	1249	org/xmlpull/v1/XmlPullParserException
    //   700	710	1249	org/xmlpull/v1/XmlPullParserException
    //   729	750	1249	org/xmlpull/v1/XmlPullParserException
    //   761	780	1249	org/xmlpull/v1/XmlPullParserException
    //   792	813	1249	org/xmlpull/v1/XmlPullParserException
    //   824	845	1249	org/xmlpull/v1/XmlPullParserException
    //   856	877	1249	org/xmlpull/v1/XmlPullParserException
    //   888	929	1249	org/xmlpull/v1/XmlPullParserException
    //   929	935	1249	org/xmlpull/v1/XmlPullParserException
    //   981	990	1249	org/xmlpull/v1/XmlPullParserException
    //   996	1045	1249	org/xmlpull/v1/XmlPullParserException
    //   710	722	1260	org/xmlpull/v1/XmlPullParserException
    //   1048	1070	1260	org/xmlpull/v1/XmlPullParserException
  }

  private static d a(String paramString, b paramb)
  {
    Iterator localIterator;
    if ((paramString != null) && (paramb.h != null))
      localIterator = paramb.h.iterator();
    d locald;
    do
    {
      if (!localIterator.hasNext())
        return null;
      locald = (d)localIterator.next();
    }
    while (!paramString.equals(locald.a));
    return locald;
  }

  public static c a(Context paramContext)
  {
    try
    {
      if (a == null)
        a = new c(paramContext.getApplicationContext());
      return a;
    }
    finally
    {
    }
  }

  private String a(a parama, b paramb)
  {
    f localf = f.a(this.b);
    StringBuilder localStringBuilder = new StringBuilder();
    String str1 = paramb.c;
    String str2 = paramb.d;
    try
    {
      localStringBuilder.append(str1 + "mv" + str2 + URLEncoder.encode("a3.2", "UTF-8"));
      localStringBuilder.append(str1 + "mr" + str2 + URLEncoder.encode(new StringBuilder().append(parama.h()).toString(), "UTF-8"));
      localStringBuilder.append(str1 + "mc" + str2 + URLEncoder.encode(new StringBuilder().append(j.c(this.b)).toString(), "UTF-8"));
      localStringBuilder.append(str1 + "mw" + str2 + URLEncoder.encode(localf.l(), "UTF-8"));
      String str3 = localf.m();
      if (str3 != null)
        localStringBuilder.append(paramb.c + "mj" + paramb.d + URLEncoder.encode(str3, "UTF-8"));
      if (parama.h() > 0)
      {
        String str6 = a.a() - parama.g();
        localStringBuilder.append(str1 + "mu" + str2 + URLEncoder.encode(str6, "UTF-8"));
      }
      while (true)
      {
        String str4 = j.g(this.b);
        if ((str4 != null) && (!str4.contains("UNKNOWN")))
          localStringBuilder.append(str1 + "mg" + str2 + URLEncoder.encode(str4, "UTF-8"));
        String str5 = localf.h();
        if ((str5 != null) && (!str5.equals("")))
          localStringBuilder.append(str1 + "m6" + str2 + URLEncoder.encode(a.a(str5).toUpperCase(), "UTF-8"));
        return localStringBuilder.toString();
        localStringBuilder.append(str1 + "mu" + str2 + URLEncoder.encode("0", "UTF-8"));
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  private String a(String paramString, a parama, b paramb)
  {
    List localList = paramb.g;
    String str1 = paramb.c;
    String str2 = paramb.d;
    Iterator localIterator = localList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        d locald = a(parama.d(), paramb);
        if (locald != null)
          paramString = paramString.replaceAll(str1 + locald.b + str2 + "[^" + str1 + "]*", "");
        return paramString;
      }
      a locala = (a)localIterator.next();
      if (((!locala.a.equals("PANELID")) || (parama.b() != null)) && ((!locala.a.equals("MUID")) || (parama.c() != null)) && ((!locala.a.equals("IESID")) || (parama.i() != null)) && (paramString.contains(str1 + locala.b + str2)))
        paramString = paramString.replaceAll(str1 + locala.b + str2 + "[^" + str1 + "]*", "");
    }
  }

  private static String a(String paramString, a parama)
  {
    if ((paramString != null) && (!paramString.equals("")))
    {
      if (parama.c.equals("md5"))
        paramString = a.a(paramString);
      while (true)
      {
        if (parama.d);
        try
        {
          String str = URLEncoder.encode(paramString, "UTF-8");
          paramString = str;
          return paramString;
          if (parama.c.equals("sha1"))
            paramString = a.b(paramString);
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
          return paramString;
        }
      }
    }
    return "";
  }

  private boolean a(String paramString)
  {
    return paramString.equals(this.d);
  }

  public final b a(URL paramURL)
  {
    String str;
    if ((this.c != null) && (this.c.a != null))
      str = paramURL.getHost();
    b localb;
    Iterator localIterator2;
    do
    {
      Iterator localIterator1 = this.c.a.iterator();
      while (!localIterator2.hasNext())
      {
        if (!localIterator1.hasNext())
          return null;
        localb = (b)localIterator1.next();
        localIterator2 = localb.b.iterator();
      }
    }
    while (!str.endsWith((String)localIterator2.next()));
    return localb;
  }

  final c a()
  {
    try
    {
      c localc = this.c;
      return localc;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  public final URL a(a parama)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 19	com/miaozhen/mzmonitor/c:b	Landroid/content/Context;
    //   6: invokestatic 206	com/miaozhen/mzmonitor/f:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/f;
    //   9: astore_3
    //   10: new 208	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   17: astore 4
    //   19: aload_1
    //   20: invokevirtual 340	com/miaozhen/mzmonitor/a:a	()Ljava/lang/String;
    //   23: astore 5
    //   25: aload 5
    //   27: astore 6
    //   29: new 328	java/net/URL
    //   32: dup
    //   33: aload 6
    //   35: invokespecial 341	java/net/URL:<init>	(Ljava/lang/String;)V
    //   38: astore 7
    //   40: aload_0
    //   41: aload 7
    //   43: invokevirtual 343	com/miaozhen/mzmonitor/c:a	(Ljava/net/URL;)Lcom/miaozhen/mzmonitor/c$b;
    //   46: astore 11
    //   48: aload 11
    //   50: ifnull +1292 -> 1342
    //   53: aload 11
    //   55: getfield 133	com/miaozhen/mzmonitor/c$b:i	Z
    //   58: ifeq +1284 -> 1342
    //   61: aload 11
    //   63: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   66: ifnull +456 -> 522
    //   69: aload 11
    //   71: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   74: ldc_w 284
    //   77: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   80: ifne +442 -> 522
    //   83: aload 6
    //   85: aload 11
    //   87: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   90: invokevirtual 278	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   93: ifeq +429 -> 522
    //   96: aload 6
    //   98: aload 6
    //   100: aload 11
    //   102: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   105: invokevirtual 347	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   108: invokevirtual 351	java/lang/String:substring	(I)Ljava/lang/String;
    //   111: astore 12
    //   113: aload 11
    //   115: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   118: ifnull +48 -> 166
    //   121: aload 11
    //   123: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   126: ldc_w 284
    //   129: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   132: ifne +34 -> 166
    //   135: aload 6
    //   137: aload 11
    //   139: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   142: invokevirtual 278	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   145: ifeq +21 -> 166
    //   148: aload 6
    //   150: iconst_0
    //   151: aload 6
    //   153: aload 11
    //   155: getfield 106	com/miaozhen/mzmonitor/c$b:e	Ljava/lang/String;
    //   158: invokevirtual 347	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   161: invokevirtual 354	java/lang/String:substring	(II)Ljava/lang/String;
    //   164: astore 6
    //   166: aload_0
    //   167: aload 6
    //   169: aload_1
    //   170: aload 11
    //   172: invokespecial 356	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/a;Lcom/miaozhen/mzmonitor/c$b;)Ljava/lang/String;
    //   175: astore 13
    //   177: aload 4
    //   179: aload 13
    //   181: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: aload 11
    //   187: getfield 84	com/miaozhen/mzmonitor/c$b:a	Ljava/lang/String;
    //   190: ldc_w 358
    //   193: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   196: ifeq +16 -> 212
    //   199: aload 4
    //   201: aload_0
    //   202: aload_1
    //   203: aload 11
    //   205: invokespecial 360	com/miaozhen/mzmonitor/c:a	(Lcom/miaozhen/mzmonitor/a;Lcom/miaozhen/mzmonitor/c$b;)Ljava/lang/String;
    //   208: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: pop
    //   212: aload 11
    //   214: getfield 138	com/miaozhen/mzmonitor/c$b:g	Ljava/util/List;
    //   217: invokeinterface 178 1 0
    //   222: astore 15
    //   224: aload 15
    //   226: invokeinterface 184 1 0
    //   231: ifne +299 -> 530
    //   234: aload_1
    //   235: invokevirtual 295	com/miaozhen/mzmonitor/a:d	()Ljava/lang/String;
    //   238: ifnull +143 -> 381
    //   241: aload 11
    //   243: getfield 98	com/miaozhen/mzmonitor/c$b:c	Ljava/lang/String;
    //   246: astore 29
    //   248: aload_1
    //   249: invokevirtual 295	com/miaozhen/mzmonitor/a:d	()Ljava/lang/String;
    //   252: ldc 37
    //   254: invokestatic 230	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   257: astore 30
    //   259: aload_1
    //   260: invokevirtual 295	com/miaozhen/mzmonitor/a:d	()Ljava/lang/String;
    //   263: aload 11
    //   265: invokestatic 297	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/c$b;)Lcom/miaozhen/mzmonitor/c$d;
    //   268: astore 31
    //   270: aload 31
    //   272: ifnull +56 -> 328
    //   275: new 208	java/lang/StringBuilder
    //   278: dup
    //   279: aload 29
    //   281: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   284: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   287: aload 31
    //   289: getfield 165	com/miaozhen/mzmonitor/c$d:b	Ljava/lang/String;
    //   292: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: aload 11
    //   297: getfield 101	com/miaozhen/mzmonitor/c$b:d	Ljava/lang/String;
    //   300: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   306: astore 29
    //   308: aload 31
    //   310: getfield 169	com/miaozhen/mzmonitor/c$d:d	Z
    //   313: ifeq +1011 -> 1324
    //   316: aload 31
    //   318: getfield 168	com/miaozhen/mzmonitor/c$d:c	Ljava/lang/String;
    //   321: ldc 37
    //   323: invokestatic 230	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   326: astore 30
    //   328: aload 4
    //   330: new 208	java/lang/StringBuilder
    //   333: dup
    //   334: aload 29
    //   336: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   339: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   342: aload 30
    //   344: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   347: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   350: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   353: pop
    //   354: new 208	java/lang/StringBuilder
    //   357: dup
    //   358: aload 13
    //   360: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   363: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   366: aload 29
    //   368: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: aload 30
    //   373: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   376: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   379: astore 13
    //   381: aload 11
    //   383: getfield 363	com/miaozhen/mzmonitor/c$b:f	Ljava/lang/String;
    //   386: ifnull +58 -> 444
    //   389: aload 4
    //   391: new 208	java/lang/StringBuilder
    //   394: dup
    //   395: aload 11
    //   397: getfield 98	com/miaozhen/mzmonitor/c$b:c	Ljava/lang/String;
    //   400: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   403: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   406: aload 11
    //   408: getfield 363	com/miaozhen/mzmonitor/c$b:f	Ljava/lang/String;
    //   411: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: aload 11
    //   416: getfield 101	com/miaozhen/mzmonitor/c$b:d	Ljava/lang/String;
    //   419: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   422: aload_0
    //   423: getfield 19	com/miaozhen/mzmonitor/c:b	Landroid/content/Context;
    //   426: aload 4
    //   428: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   431: invokestatic 368	com/miaozhen/mzmonitor/k:a	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   434: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   440: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   443: pop
    //   444: aload_1
    //   445: new 208	java/lang/StringBuilder
    //   448: dup
    //   449: aload 13
    //   451: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   454: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   457: aload 12
    //   459: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   462: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   465: invokevirtual 370	com/miaozhen/mzmonitor/a:a	(Ljava/lang/String;)V
    //   468: ldc_w 372
    //   471: new 208	java/lang/StringBuilder
    //   474: dup
    //   475: ldc_w 374
    //   478: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   481: aload 13
    //   483: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   486: aload 12
    //   488: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   494: invokestatic 379	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   497: pop
    //   498: new 328	java/net/URL
    //   501: dup
    //   502: aload 4
    //   504: aload 12
    //   506: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   509: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   512: invokespecial 341	java/net/URL:<init>	(Ljava/lang/String;)V
    //   515: astore 9
    //   517: aload_0
    //   518: monitorexit
    //   519: aload 9
    //   521: areturn
    //   522: ldc_w 284
    //   525: astore 12
    //   527: goto -414 -> 113
    //   530: aload 15
    //   532: invokeinterface 187 1 0
    //   537: checkcast 142	com/miaozhen/mzmonitor/c$a
    //   540: astore 16
    //   542: new 208	java/lang/StringBuilder
    //   545: dup
    //   546: aload 11
    //   548: getfield 98	com/miaozhen/mzmonitor/c$b:c	Ljava/lang/String;
    //   551: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   554: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   557: aload 16
    //   559: getfield 145	com/miaozhen/mzmonitor/c$a:b	Ljava/lang/String;
    //   562: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   565: aload 11
    //   567: getfield 101	com/miaozhen/mzmonitor/c$b:d	Ljava/lang/String;
    //   570: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   573: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   576: astore 17
    //   578: aload 16
    //   580: getfield 151	com/miaozhen/mzmonitor/c$a:a	Ljava/lang/String;
    //   583: astore 18
    //   585: ldc_w 284
    //   588: astore 19
    //   590: aload 18
    //   592: ldc_w 381
    //   595: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   598: ifeq +206 -> 804
    //   601: aload_3
    //   602: invokevirtual 382	com/miaozhen/mzmonitor/f:d	()Ljava/lang/String;
    //   605: astore 19
    //   607: aload 19
    //   609: aload 16
    //   611: invokestatic 384	com/miaozhen/mzmonitor/c:a	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/c$a;)Ljava/lang/String;
    //   614: astore 20
    //   616: aload 18
    //   618: ldc_w 306
    //   621: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   624: ifne +25 -> 649
    //   627: aload 18
    //   629: ldc_w 310
    //   632: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   635: ifne +14 -> 649
    //   638: aload 18
    //   640: ldc_w 314
    //   643: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   646: ifeq +65 -> 711
    //   649: aload 13
    //   651: aload 17
    //   653: invokevirtual 278	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   656: ifne -432 -> 224
    //   659: aload 20
    //   661: ldc_w 284
    //   664: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   667: ifeq +17 -> 684
    //   670: aload 11
    //   672: getfield 84	com/miaozhen/mzmonitor/c$b:a	Ljava/lang/String;
    //   675: ldc_w 358
    //   678: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   681: ifne -457 -> 224
    //   684: new 208	java/lang/StringBuilder
    //   687: dup
    //   688: aload 13
    //   690: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   693: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   696: aload 17
    //   698: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   701: aload 20
    //   703: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   706: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   709: astore 13
    //   711: aload 20
    //   713: ldc_w 284
    //   716: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   719: ifeq +17 -> 736
    //   722: aload 11
    //   724: getfield 84	com/miaozhen/mzmonitor/c$b:a	Ljava/lang/String;
    //   727: ldc_w 358
    //   730: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   733: ifne -509 -> 224
    //   736: aload 4
    //   738: new 208	java/lang/StringBuilder
    //   741: dup
    //   742: aload 17
    //   744: invokestatic 213	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   747: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   750: aload 20
    //   752: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   755: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   758: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   761: pop
    //   762: goto -538 -> 224
    //   765: astore 8
    //   767: aload 7
    //   769: astore 9
    //   771: ldc_w 372
    //   774: new 208	java/lang/StringBuilder
    //   777: dup
    //   778: ldc_w 386
    //   781: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   784: aload 8
    //   786: invokevirtual 389	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   789: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   792: invokestatic 379	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   795: pop
    //   796: goto -279 -> 517
    //   799: astore_2
    //   800: aload_0
    //   801: monitorexit
    //   802: aload_2
    //   803: athrow
    //   804: aload 18
    //   806: ldc_w 391
    //   809: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   812: ifeq +12 -> 824
    //   815: aload_3
    //   816: invokevirtual 393	com/miaozhen/mzmonitor/f:e	()Ljava/lang/String;
    //   819: astore 19
    //   821: goto -214 -> 607
    //   824: aload 18
    //   826: ldc_w 395
    //   829: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   832: ifeq +12 -> 844
    //   835: aload_3
    //   836: invokevirtual 396	com/miaozhen/mzmonitor/f:c	()Ljava/lang/String;
    //   839: astore 19
    //   841: goto -234 -> 607
    //   844: aload 18
    //   846: ldc_w 398
    //   849: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   852: ifeq +44 -> 896
    //   855: aload_3
    //   856: invokevirtual 282	com/miaozhen/mzmonitor/f:h	()Ljava/lang/String;
    //   859: astore 26
    //   861: aload 26
    //   863: ifnull -256 -> 607
    //   866: aload 26
    //   868: ldc_w 284
    //   871: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   874: ifne -267 -> 607
    //   877: aload 26
    //   879: ldc_w 400
    //   882: ldc_w 284
    //   885: invokevirtual 304	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   888: invokevirtual 113	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   891: astore 19
    //   893: goto -286 -> 607
    //   896: aload 18
    //   898: ldc_w 402
    //   901: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   904: ifeq +12 -> 916
    //   907: aload_3
    //   908: invokevirtual 403	com/miaozhen/mzmonitor/f:b	()Ljava/lang/String;
    //   911: astore 19
    //   913: goto -306 -> 607
    //   916: aload 18
    //   918: ldc_w 405
    //   921: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   924: ifeq +18 -> 942
    //   927: aload_3
    //   928: invokevirtual 408	com/miaozhen/mzmonitor/f:k	()Z
    //   931: ifeq +421 -> 1352
    //   934: ldc_w 410
    //   937: astore 19
    //   939: goto +410 -> 1349
    //   942: aload 18
    //   944: ldc_w 412
    //   947: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   950: ifeq +11 -> 961
    //   953: invokestatic 413	com/miaozhen/mzmonitor/f:a	()Ljava/lang/String;
    //   956: astore 19
    //   958: goto -351 -> 607
    //   961: aload 18
    //   963: ldc_w 415
    //   966: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   969: ifeq +61 -> 1030
    //   972: aload_1
    //   973: invokevirtual 417	com/miaozhen/mzmonitor/a:f	()J
    //   976: lstore 24
    //   978: aload 11
    //   980: getfield 124	com/miaozhen/mzmonitor/c$b:j	Z
    //   983: ifeq +27 -> 1010
    //   986: new 208	java/lang/StringBuilder
    //   989: dup
    //   990: lload 24
    //   992: ldc2_w 418
    //   995: ldiv
    //   996: invokestatic 267	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   999: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1002: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1005: astore 19
    //   1007: goto +353 -> 1360
    //   1010: new 208	java/lang/StringBuilder
    //   1013: dup
    //   1014: lload 24
    //   1016: invokestatic 267	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   1019: invokespecial 216	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1022: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1025: astore 19
    //   1027: goto +333 -> 1360
    //   1030: aload 18
    //   1032: ldc_w 421
    //   1035: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1038: ifeq +30 -> 1068
    //   1041: aload_0
    //   1042: getfield 19	com/miaozhen/mzmonitor/c:b	Landroid/content/Context;
    //   1045: invokestatic 423	com/miaozhen/mzmonitor/j:h	(Landroid/content/Context;)Ljava/lang/String;
    //   1048: astore 23
    //   1050: aload 23
    //   1052: ldc_w 274
    //   1055: invokevirtual 278	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1058: ifne -451 -> 607
    //   1061: aload 23
    //   1063: astore 19
    //   1065: goto -458 -> 607
    //   1068: aload 18
    //   1070: ldc_w 425
    //   1073: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1076: ifeq +12 -> 1088
    //   1079: aload_3
    //   1080: invokevirtual 427	com/miaozhen/mzmonitor/f:f	()Ljava/lang/String;
    //   1083: astore 19
    //   1085: goto -478 -> 607
    //   1088: aload 18
    //   1090: ldc_w 429
    //   1093: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1096: ifeq +11 -> 1107
    //   1099: ldc_w 291
    //   1102: astore 19
    //   1104: goto -497 -> 607
    //   1107: aload 18
    //   1109: ldc_w 431
    //   1112: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1115: ifeq +11 -> 1126
    //   1118: invokestatic 433	com/miaozhen/mzmonitor/f:g	()Ljava/lang/String;
    //   1121: astore 19
    //   1123: goto -516 -> 607
    //   1126: aload 18
    //   1128: ldc_w 435
    //   1131: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1134: ifeq +12 -> 1146
    //   1137: aload_3
    //   1138: invokevirtual 436	com/miaozhen/mzmonitor/f:i	()Ljava/lang/String;
    //   1141: astore 19
    //   1143: goto -536 -> 607
    //   1146: aload 18
    //   1148: ldc_w 438
    //   1151: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1154: ifeq +12 -> 1166
    //   1157: aload_3
    //   1158: invokevirtual 440	com/miaozhen/mzmonitor/f:j	()Ljava/lang/String;
    //   1161: astore 19
    //   1163: goto -556 -> 607
    //   1166: aload 18
    //   1168: ldc_w 306
    //   1171: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1174: ifeq +12 -> 1186
    //   1177: aload_1
    //   1178: invokevirtual 308	com/miaozhen/mzmonitor/a:b	()Ljava/lang/String;
    //   1181: astore 19
    //   1183: goto -576 -> 607
    //   1186: aload 18
    //   1188: ldc_w 310
    //   1191: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1194: ifeq +12 -> 1206
    //   1197: aload_1
    //   1198: invokevirtual 312	com/miaozhen/mzmonitor/a:c	()Ljava/lang/String;
    //   1201: astore 19
    //   1203: goto -596 -> 607
    //   1206: aload 18
    //   1208: ldc_w 314
    //   1211: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1214: ifeq +12 -> 1226
    //   1217: aload_1
    //   1218: invokevirtual 316	com/miaozhen/mzmonitor/a:i	()Ljava/lang/String;
    //   1221: astore 19
    //   1223: goto -616 -> 607
    //   1226: aload 18
    //   1228: ldc_w 442
    //   1231: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1234: ifeq +15 -> 1249
    //   1237: aload_0
    //   1238: getfield 19	com/miaozhen/mzmonitor/c:b	Landroid/content/Context;
    //   1241: invokestatic 444	com/miaozhen/mzmonitor/f:b	(Landroid/content/Context;)Ljava/lang/String;
    //   1244: astore 19
    //   1246: goto -639 -> 607
    //   1249: aload 18
    //   1251: ldc_w 446
    //   1254: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1257: ifeq +11 -> 1268
    //   1260: invokestatic 449	com/miaozhen/mzmonitor/f:n	()Ljava/lang/String;
    //   1263: astore 19
    //   1265: goto -658 -> 607
    //   1268: aload 18
    //   1270: ldc_w 451
    //   1273: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1276: ifeq +16 -> 1292
    //   1279: aload_0
    //   1280: getfield 19	com/miaozhen/mzmonitor/c:b	Landroid/content/Context;
    //   1283: pop
    //   1284: invokestatic 454	com/miaozhen/mzmonitor/f:o	()Ljava/lang/String;
    //   1287: astore 19
    //   1289: goto -682 -> 607
    //   1292: aload 18
    //   1294: ldc_w 456
    //   1297: invokevirtual 118	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1300: ifeq -693 -> 607
    //   1303: aload 11
    //   1305: getfield 363	com/miaozhen/mzmonitor/c$b:f	Ljava/lang/String;
    //   1308: ifnonnull -1084 -> 224
    //   1311: aload 11
    //   1313: aload 16
    //   1315: getfield 145	com/miaozhen/mzmonitor/c$a:b	Ljava/lang/String;
    //   1318: putfield 363	com/miaozhen/mzmonitor/c$b:f	Ljava/lang/String;
    //   1321: goto -1097 -> 224
    //   1324: aload 31
    //   1326: getfield 168	com/miaozhen/mzmonitor/c$d:c	Ljava/lang/String;
    //   1329: astore 30
    //   1331: goto -1003 -> 328
    //   1334: astore 8
    //   1336: aconst_null
    //   1337: astore 9
    //   1339: goto -568 -> 771
    //   1342: aload 7
    //   1344: astore 9
    //   1346: goto -829 -> 517
    //   1349: goto -742 -> 607
    //   1352: ldc_w 291
    //   1355: astore 19
    //   1357: goto -8 -> 1349
    //   1360: goto -753 -> 607
    //   1363: astore_2
    //   1364: goto -564 -> 800
    //
    // Exception table:
    //   from	to	target	type
    //   40	48	765	java/lang/Exception
    //   53	113	765	java/lang/Exception
    //   113	166	765	java/lang/Exception
    //   166	212	765	java/lang/Exception
    //   212	224	765	java/lang/Exception
    //   224	270	765	java/lang/Exception
    //   275	328	765	java/lang/Exception
    //   328	381	765	java/lang/Exception
    //   381	444	765	java/lang/Exception
    //   444	517	765	java/lang/Exception
    //   530	585	765	java/lang/Exception
    //   590	607	765	java/lang/Exception
    //   607	649	765	java/lang/Exception
    //   649	684	765	java/lang/Exception
    //   684	711	765	java/lang/Exception
    //   711	736	765	java/lang/Exception
    //   736	762	765	java/lang/Exception
    //   804	821	765	java/lang/Exception
    //   824	841	765	java/lang/Exception
    //   844	861	765	java/lang/Exception
    //   866	893	765	java/lang/Exception
    //   896	913	765	java/lang/Exception
    //   916	934	765	java/lang/Exception
    //   942	958	765	java/lang/Exception
    //   961	1007	765	java/lang/Exception
    //   1010	1027	765	java/lang/Exception
    //   1030	1061	765	java/lang/Exception
    //   1068	1085	765	java/lang/Exception
    //   1088	1099	765	java/lang/Exception
    //   1107	1123	765	java/lang/Exception
    //   1126	1143	765	java/lang/Exception
    //   1146	1163	765	java/lang/Exception
    //   1166	1183	765	java/lang/Exception
    //   1186	1203	765	java/lang/Exception
    //   1206	1223	765	java/lang/Exception
    //   1226	1246	765	java/lang/Exception
    //   1249	1265	765	java/lang/Exception
    //   1268	1289	765	java/lang/Exception
    //   1292	1321	765	java/lang/Exception
    //   1324	1331	765	java/lang/Exception
    //   2	25	799	finally
    //   29	40	799	finally
    //   771	796	799	finally
    //   29	40	1334	java/lang/Exception
    //   40	48	1363	finally
    //   53	113	1363	finally
    //   113	166	1363	finally
    //   166	212	1363	finally
    //   212	224	1363	finally
    //   224	270	1363	finally
    //   275	328	1363	finally
    //   328	381	1363	finally
    //   381	444	1363	finally
    //   444	517	1363	finally
    //   530	585	1363	finally
    //   590	607	1363	finally
    //   607	649	1363	finally
    //   649	684	1363	finally
    //   684	711	1363	finally
    //   711	736	1363	finally
    //   736	762	1363	finally
    //   804	821	1363	finally
    //   824	841	1363	finally
    //   844	861	1363	finally
    //   866	893	1363	finally
    //   896	913	1363	finally
    //   916	934	1363	finally
    //   942	958	1363	finally
    //   961	1007	1363	finally
    //   1010	1027	1363	finally
    //   1030	1061	1363	finally
    //   1068	1085	1363	finally
    //   1088	1099	1363	finally
    //   1107	1123	1363	finally
    //   1126	1143	1363	finally
    //   1146	1163	1363	finally
    //   1166	1183	1363	finally
    //   1186	1203	1363	finally
    //   1206	1223	1363	finally
    //   1226	1246	1363	finally
    //   1249	1265	1363	finally
    //   1268	1289	1363	finally
    //   1292	1321	1363	finally
    //   1324	1331	1363	finally
  }

  public final void b()
  {
    try
    {
      this.c = a(new ByteArrayInputStream(j.k(this.b).getBytes("UTF-8")));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
    finally
    {
    }
  }

  public class a
  {
    String a;
    String b;
    String c = "raw";
    boolean d = true;

    a()
    {
    }

    public static long a()
    {
      return System.currentTimeMillis() / 1000L;
    }

    public static String a(String paramString)
    {
      if ((paramString == null) || (paramString.length() == 0))
        return "";
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.reset();
        localMessageDigest.update(paramString.getBytes("UTF-8"));
        String str = a(localMessageDigest.digest());
        return str;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return "";
    }

    private static String a(byte[] paramArrayOfByte)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = paramArrayOfByte.length;
      int j = 0;
      if (j >= i)
        return localStringBuilder.toString();
      String str = Integer.toHexString(0xFF & paramArrayOfByte[j]);
      if (str.length() == 1)
        localStringBuilder.append("0").append(str);
      while (true)
      {
        j++;
        break;
        localStringBuilder.append(str);
      }
    }

    public static String b(String paramString)
    {
      if ((paramString == null) || (paramString.length() == 0))
        return "";
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
        localMessageDigest.update(paramString.getBytes("UTF-8"), 0, paramString.length());
        String str = a(localMessageDigest.digest());
        return str;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return "";
    }
  }

  final class b
  {
    String a;
    List<String> b;
    String c;
    String d;
    String e;
    String f;
    List<c.a> g;
    List<c.d> h;
    boolean i = false;
    boolean j = true;

    b()
    {
    }
  }

  final class c
  {
    List<c.b> a;

    c()
    {
    }
  }

  final class d
  {
    String a;
    String b;
    String c;
    boolean d = true;

    d()
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.c
 * JD-Core Version:    0.6.2
 */