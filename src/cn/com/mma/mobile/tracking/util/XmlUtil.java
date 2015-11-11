package cn.com.mma.mobile.tracking.util;

public class XmlUtil
{
  public static final String XMLFILE = "sdkconfig.xml";

  // ERROR //
  public static cn.com.mma.mobile.tracking.bean.SDK doParser(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: invokestatic 22	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   5: astore_3
    //   6: aload_3
    //   7: aload_0
    //   8: ldc 24
    //   10: invokeinterface 30 3 0
    //   15: aload_3
    //   16: invokeinterface 34 1 0
    //   21: istore 4
    //   23: aconst_null
    //   24: astore 5
    //   26: aconst_null
    //   27: astore 6
    //   29: aconst_null
    //   30: astore 7
    //   32: aconst_null
    //   33: astore 8
    //   35: goto +1165 -> 1200
    //   38: aload_3
    //   39: invokeinterface 37 1 0
    //   44: istore 19
    //   46: iload 19
    //   48: istore 4
    //   50: aload 18
    //   52: astore 7
    //   54: aload 13
    //   56: astore 6
    //   58: aload 16
    //   60: astore 5
    //   62: aload_1
    //   63: astore 8
    //   65: goto +1135 -> 1200
    //   68: new 39	cn/com/mma/mobile/tracking/bean/SDK
    //   71: dup
    //   72: invokespecial 40	cn/com/mma/mobile/tracking/bean/SDK:<init>	()V
    //   75: astore_1
    //   76: aload 7
    //   78: astore 18
    //   80: aload 6
    //   82: astore 13
    //   84: aload 5
    //   86: astore 16
    //   88: goto -50 -> 38
    //   91: aload_3
    //   92: invokeinterface 44 1 0
    //   97: astore 23
    //   99: ldc 46
    //   101: aload 23
    //   103: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   106: ifeq +15 -> 121
    //   109: aload 8
    //   111: new 54	cn/com/mma/mobile/tracking/bean/OfflineCache
    //   114: dup
    //   115: invokespecial 55	cn/com/mma/mobile/tracking/bean/OfflineCache:<init>	()V
    //   118: putfield 58	cn/com/mma/mobile/tracking/bean/SDK:offlineCache	Lcn/com/mma/mobile/tracking/bean/OfflineCache;
    //   121: aload 8
    //   123: getfield 58	cn/com/mma/mobile/tracking/bean/SDK:offlineCache	Lcn/com/mma/mobile/tracking/bean/OfflineCache;
    //   126: ifnull +75 -> 201
    //   129: ldc 60
    //   131: aload 23
    //   133: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   136: ifeq +17 -> 153
    //   139: aload 8
    //   141: getfield 58	cn/com/mma/mobile/tracking/bean/SDK:offlineCache	Lcn/com/mma/mobile/tracking/bean/OfflineCache;
    //   144: aload_3
    //   145: invokeinterface 63 1 0
    //   150: putfield 65	cn/com/mma/mobile/tracking/bean/OfflineCache:length	Ljava/lang/String;
    //   153: ldc 67
    //   155: aload 23
    //   157: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   160: ifeq +17 -> 177
    //   163: aload 8
    //   165: getfield 58	cn/com/mma/mobile/tracking/bean/SDK:offlineCache	Lcn/com/mma/mobile/tracking/bean/OfflineCache;
    //   168: aload_3
    //   169: invokeinterface 63 1 0
    //   174: putfield 69	cn/com/mma/mobile/tracking/bean/OfflineCache:queueExpirationSecs	Ljava/lang/String;
    //   177: ldc 71
    //   179: aload 23
    //   181: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   184: ifeq +17 -> 201
    //   187: aload 8
    //   189: getfield 58	cn/com/mma/mobile/tracking/bean/SDK:offlineCache	Lcn/com/mma/mobile/tracking/bean/OfflineCache;
    //   192: aload_3
    //   193: invokeinterface 63 1 0
    //   198: putfield 73	cn/com/mma/mobile/tracking/bean/OfflineCache:timeout	Ljava/lang/String;
    //   201: ldc 75
    //   203: aload 23
    //   205: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   208: ifeq +15 -> 223
    //   211: aload 8
    //   213: new 77	java/util/ArrayList
    //   216: dup
    //   217: invokespecial 78	java/util/ArrayList:<init>	()V
    //   220: putfield 81	cn/com/mma/mobile/tracking/bean/SDK:companies	Ljava/util/List;
    //   223: aload 8
    //   225: getfield 81	cn/com/mma/mobile/tracking/bean/SDK:companies	Ljava/util/List;
    //   228: ifnull +965 -> 1193
    //   231: ldc 83
    //   233: aload 23
    //   235: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   238: ifeq +955 -> 1193
    //   241: new 85	cn/com/mma/mobile/tracking/bean/Company
    //   244: dup
    //   245: invokespecial 86	cn/com/mma/mobile/tracking/bean/Company:<init>	()V
    //   248: astore 13
    //   250: aload 13
    //   252: ifnull +927 -> 1179
    //   255: ldc 88
    //   257: aload 23
    //   259: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   262: ifeq +22 -> 284
    //   265: aload 13
    //   267: getfield 90	cn/com/mma/mobile/tracking/bean/Company:name	Ljava/lang/String;
    //   270: ifnonnull +14 -> 284
    //   273: aload 13
    //   275: aload_3
    //   276: invokeinterface 63 1 0
    //   281: putfield 90	cn/com/mma/mobile/tracking/bean/Company:name	Ljava/lang/String;
    //   284: ldc 92
    //   286: aload 23
    //   288: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   291: ifeq +15 -> 306
    //   294: aload 13
    //   296: new 94	cn/com/mma/mobile/tracking/bean/Domain
    //   299: dup
    //   300: invokespecial 95	cn/com/mma/mobile/tracking/bean/Domain:<init>	()V
    //   303: putfield 98	cn/com/mma/mobile/tracking/bean/Company:domain	Lcn/com/mma/mobile/tracking/bean/Domain;
    //   306: aload 13
    //   308: getfield 98	cn/com/mma/mobile/tracking/bean/Company:domain	Lcn/com/mma/mobile/tracking/bean/Domain;
    //   311: ifnull +27 -> 338
    //   314: ldc 100
    //   316: aload 23
    //   318: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   321: ifeq +17 -> 338
    //   324: aload 13
    //   326: getfield 98	cn/com/mma/mobile/tracking/bean/Company:domain	Lcn/com/mma/mobile/tracking/bean/Domain;
    //   329: aload_3
    //   330: invokeinterface 63 1 0
    //   335: putfield 102	cn/com/mma/mobile/tracking/bean/Domain:url	Ljava/lang/String;
    //   338: ldc 104
    //   340: aload 23
    //   342: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   345: ifeq +15 -> 360
    //   348: aload 13
    //   350: new 106	cn/com/mma/mobile/tracking/bean/Signature
    //   353: dup
    //   354: invokespecial 107	cn/com/mma/mobile/tracking/bean/Signature:<init>	()V
    //   357: putfield 110	cn/com/mma/mobile/tracking/bean/Company:signature	Lcn/com/mma/mobile/tracking/bean/Signature;
    //   360: aload 13
    //   362: getfield 110	cn/com/mma/mobile/tracking/bean/Company:signature	Lcn/com/mma/mobile/tracking/bean/Signature;
    //   365: ifnull +51 -> 416
    //   368: ldc 112
    //   370: aload 23
    //   372: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   375: ifeq +17 -> 392
    //   378: aload 13
    //   380: getfield 110	cn/com/mma/mobile/tracking/bean/Company:signature	Lcn/com/mma/mobile/tracking/bean/Signature;
    //   383: aload_3
    //   384: invokeinterface 63 1 0
    //   389: putfield 114	cn/com/mma/mobile/tracking/bean/Signature:publicKey	Ljava/lang/String;
    //   392: ldc 116
    //   394: aload 23
    //   396: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   399: ifeq +17 -> 416
    //   402: aload 13
    //   404: getfield 110	cn/com/mma/mobile/tracking/bean/Company:signature	Lcn/com/mma/mobile/tracking/bean/Signature;
    //   407: aload_3
    //   408: invokeinterface 63 1 0
    //   413: putfield 118	cn/com/mma/mobile/tracking/bean/Signature:paramKey	Ljava/lang/String;
    //   416: ldc 120
    //   418: aload 23
    //   420: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   423: ifeq +15 -> 438
    //   426: aload 13
    //   428: new 122	cn/com/mma/mobile/tracking/bean/Switch
    //   431: dup
    //   432: invokespecial 123	cn/com/mma/mobile/tracking/bean/Switch:<init>	()V
    //   435: putfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   438: aload 13
    //   440: getfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   443: ifnull +152 -> 595
    //   446: ldc 129
    //   448: aload 23
    //   450: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   453: ifeq +20 -> 473
    //   456: aload 13
    //   458: getfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   461: aload_3
    //   462: invokeinterface 63 1 0
    //   467: invokestatic 135	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   470: putfield 138	cn/com/mma/mobile/tracking/bean/Switch:isTrackLocation	Z
    //   473: ldc 140
    //   475: aload 23
    //   477: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   480: ifeq +17 -> 497
    //   483: aload 13
    //   485: getfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   488: aload_3
    //   489: invokeinterface 63 1 0
    //   494: putfield 142	cn/com/mma/mobile/tracking/bean/Switch:offlineCacheExpiration	Ljava/lang/String;
    //   497: ldc 144
    //   499: aload 23
    //   501: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   504: ifeq +18 -> 522
    //   507: aload 13
    //   509: getfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   512: new 146	java/util/HashMap
    //   515: dup
    //   516: invokespecial 147	java/util/HashMap:<init>	()V
    //   519: putfield 150	cn/com/mma/mobile/tracking/bean/Switch:encrypt	Ljava/util/Map;
    //   522: aload 13
    //   524: getfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   527: getfield 150	cn/com/mma/mobile/tracking/bean/Switch:encrypt	Ljava/util/Map;
    //   530: ifnull +65 -> 595
    //   533: ldc 152
    //   535: aload 23
    //   537: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   540: ifne +33 -> 573
    //   543: ldc 154
    //   545: aload 23
    //   547: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   550: ifne +23 -> 573
    //   553: ldc 156
    //   555: aload 23
    //   557: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   560: ifne +13 -> 573
    //   563: ldc 158
    //   565: aload 23
    //   567: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   570: ifeq +25 -> 595
    //   573: aload 13
    //   575: getfield 127	cn/com/mma/mobile/tracking/bean/Company:sswitch	Lcn/com/mma/mobile/tracking/bean/Switch;
    //   578: getfield 150	cn/com/mma/mobile/tracking/bean/Switch:encrypt	Ljava/util/Map;
    //   581: aload 23
    //   583: aload_3
    //   584: invokeinterface 63 1 0
    //   589: invokeinterface 164 3 0
    //   594: pop
    //   595: ldc 166
    //   597: aload 23
    //   599: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   602: ifeq +15 -> 617
    //   605: aload 13
    //   607: new 168	cn/com/mma/mobile/tracking/bean/Config
    //   610: dup
    //   611: invokespecial 169	cn/com/mma/mobile/tracking/bean/Config:<init>	()V
    //   614: putfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   617: aload 13
    //   619: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   622: ifnull +546 -> 1168
    //   625: ldc 174
    //   627: aload 23
    //   629: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   632: ifeq +18 -> 650
    //   635: aload 13
    //   637: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   640: new 77	java/util/ArrayList
    //   643: dup
    //   644: invokespecial 78	java/util/ArrayList:<init>	()V
    //   647: putfield 176	cn/com/mma/mobile/tracking/bean/Config:arguments	Ljava/util/List;
    //   650: aload 13
    //   652: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   655: getfield 176	cn/com/mma/mobile/tracking/bean/Config:arguments	Ljava/util/List;
    //   658: ifnull +503 -> 1161
    //   661: ldc 178
    //   663: aload 23
    //   665: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   668: ifeq +493 -> 1161
    //   671: new 180	cn/com/mma/mobile/tracking/bean/Argument
    //   674: dup
    //   675: invokespecial 181	cn/com/mma/mobile/tracking/bean/Argument:<init>	()V
    //   678: astore 16
    //   680: aload 16
    //   682: ifnull +93 -> 775
    //   685: ldc 183
    //   687: aload 23
    //   689: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   692: ifeq +14 -> 706
    //   695: aload 16
    //   697: aload_3
    //   698: invokeinterface 63 1 0
    //   703: putfield 185	cn/com/mma/mobile/tracking/bean/Argument:key	Ljava/lang/String;
    //   706: ldc 187
    //   708: aload 23
    //   710: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   713: ifeq +14 -> 727
    //   716: aload 16
    //   718: aload_3
    //   719: invokeinterface 63 1 0
    //   724: putfield 189	cn/com/mma/mobile/tracking/bean/Argument:value	Ljava/lang/String;
    //   727: ldc 191
    //   729: aload 23
    //   731: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   734: ifeq +17 -> 751
    //   737: aload 16
    //   739: aload_3
    //   740: invokeinterface 63 1 0
    //   745: invokestatic 135	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   748: putfield 193	cn/com/mma/mobile/tracking/bean/Argument:urlEncode	Z
    //   751: ldc 195
    //   753: aload 23
    //   755: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   758: ifeq +17 -> 775
    //   761: aload 16
    //   763: aload_3
    //   764: invokeinterface 63 1 0
    //   769: invokestatic 135	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   772: putfield 197	cn/com/mma/mobile/tracking/bean/Argument:isRequired	Z
    //   775: ldc 199
    //   777: aload 23
    //   779: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   782: ifeq +18 -> 800
    //   785: aload 13
    //   787: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   790: new 77	java/util/ArrayList
    //   793: dup
    //   794: invokespecial 78	java/util/ArrayList:<init>	()V
    //   797: putfield 201	cn/com/mma/mobile/tracking/bean/Config:events	Ljava/util/List;
    //   800: aload 13
    //   802: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   805: getfield 201	cn/com/mma/mobile/tracking/bean/Config:events	Ljava/util/List;
    //   808: ifnull +346 -> 1154
    //   811: ldc 203
    //   813: aload 23
    //   815: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   818: ifeq +336 -> 1154
    //   821: new 205	cn/com/mma/mobile/tracking/bean/Event
    //   824: dup
    //   825: invokespecial 206	cn/com/mma/mobile/tracking/bean/Event:<init>	()V
    //   828: astore 18
    //   830: aload 18
    //   832: ifnull +69 -> 901
    //   835: ldc 183
    //   837: aload 23
    //   839: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   842: ifeq +14 -> 856
    //   845: aload 18
    //   847: aload_3
    //   848: invokeinterface 63 1 0
    //   853: putfield 207	cn/com/mma/mobile/tracking/bean/Event:key	Ljava/lang/String;
    //   856: ldc 187
    //   858: aload 23
    //   860: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   863: ifeq +14 -> 877
    //   866: aload 18
    //   868: aload_3
    //   869: invokeinterface 63 1 0
    //   874: putfield 208	cn/com/mma/mobile/tracking/bean/Event:value	Ljava/lang/String;
    //   877: ldc 191
    //   879: aload 23
    //   881: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   884: ifeq +17 -> 901
    //   887: aload 18
    //   889: aload_3
    //   890: invokeinterface 63 1 0
    //   895: invokestatic 135	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   898: putfield 209	cn/com/mma/mobile/tracking/bean/Event:urlEncode	Z
    //   901: ldc 211
    //   903: aload 23
    //   905: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   908: ifeq +14 -> 922
    //   911: aload 13
    //   913: aload_3
    //   914: invokeinterface 63 1 0
    //   919: putfield 213	cn/com/mma/mobile/tracking/bean/Company:separator	Ljava/lang/String;
    //   922: ldc 215
    //   924: aload 23
    //   926: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   929: ifeq +14 -> 943
    //   932: aload 13
    //   934: aload_3
    //   935: invokeinterface 63 1 0
    //   940: putfield 217	cn/com/mma/mobile/tracking/bean/Company:equalizer	Ljava/lang/String;
    //   943: ldc 219
    //   945: aload 23
    //   947: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   950: ifeq +198 -> 1148
    //   953: aload 13
    //   955: aload_3
    //   956: invokeinterface 63 1 0
    //   961: invokestatic 135	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   964: putfield 221	cn/com/mma/mobile/tracking/bean/Company:timeStampUseSecond	Z
    //   967: aload 8
    //   969: astore_1
    //   970: goto -932 -> 38
    //   973: aload_3
    //   974: invokeinterface 44 1 0
    //   979: astore 12
    //   981: ldc 83
    //   983: aload 12
    //   985: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   988: ifeq +153 -> 1141
    //   991: aload 8
    //   993: getfield 81	cn/com/mma/mobile/tracking/bean/SDK:companies	Ljava/util/List;
    //   996: aload 6
    //   998: invokeinterface 226 2 0
    //   1003: pop
    //   1004: aconst_null
    //   1005: astore 13
    //   1007: ldc 178
    //   1009: aload 12
    //   1011: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1014: ifeq +120 -> 1134
    //   1017: aload 13
    //   1019: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   1022: getfield 176	cn/com/mma/mobile/tracking/bean/Config:arguments	Ljava/util/List;
    //   1025: aload 5
    //   1027: invokeinterface 226 2 0
    //   1032: pop
    //   1033: aconst_null
    //   1034: astore 16
    //   1036: ldc 203
    //   1038: aload 12
    //   1040: invokevirtual 52	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1043: ifeq +81 -> 1124
    //   1046: aload 13
    //   1048: getfield 172	cn/com/mma/mobile/tracking/bean/Company:config	Lcn/com/mma/mobile/tracking/bean/Config;
    //   1051: getfield 201	cn/com/mma/mobile/tracking/bean/Config:events	Ljava/util/List;
    //   1054: aload 7
    //   1056: invokeinterface 226 2 0
    //   1061: pop
    //   1062: aload 8
    //   1064: astore_1
    //   1065: aconst_null
    //   1066: astore 18
    //   1068: goto -1030 -> 38
    //   1071: astore_2
    //   1072: aload_2
    //   1073: invokevirtual 229	java/lang/Exception:printStackTrace	()V
    //   1076: aload_1
    //   1077: areturn
    //   1078: astore_2
    //   1079: aload 7
    //   1081: pop
    //   1082: aload 6
    //   1084: pop
    //   1085: aload 5
    //   1087: pop
    //   1088: aload 8
    //   1090: astore_1
    //   1091: goto -19 -> 1072
    //   1094: astore_2
    //   1095: aload 7
    //   1097: pop
    //   1098: aload 5
    //   1100: pop
    //   1101: aload 8
    //   1103: astore_1
    //   1104: goto -32 -> 1072
    //   1107: astore_2
    //   1108: aload 7
    //   1110: pop
    //   1111: aload 8
    //   1113: astore_1
    //   1114: goto -42 -> 1072
    //   1117: astore_2
    //   1118: aload 8
    //   1120: astore_1
    //   1121: goto -49 -> 1072
    //   1124: aload 7
    //   1126: astore 18
    //   1128: aload 8
    //   1130: astore_1
    //   1131: goto -1093 -> 38
    //   1134: aload 5
    //   1136: astore 16
    //   1138: goto -102 -> 1036
    //   1141: aload 6
    //   1143: astore 13
    //   1145: goto -138 -> 1007
    //   1148: aload 8
    //   1150: astore_1
    //   1151: goto -1113 -> 38
    //   1154: aload 7
    //   1156: astore 18
    //   1158: goto -328 -> 830
    //   1161: aload 5
    //   1163: astore 16
    //   1165: goto -485 -> 680
    //   1168: aload 7
    //   1170: astore 18
    //   1172: aload 5
    //   1174: astore 16
    //   1176: goto -275 -> 901
    //   1179: aload 7
    //   1181: astore 18
    //   1183: aload 5
    //   1185: astore 16
    //   1187: aload 8
    //   1189: astore_1
    //   1190: goto -1152 -> 38
    //   1193: aload 6
    //   1195: astore 13
    //   1197: goto -947 -> 250
    //   1200: iload 4
    //   1202: iconst_1
    //   1203: if_icmpne +15 -> 1218
    //   1206: aload 7
    //   1208: pop
    //   1209: aload 6
    //   1211: pop
    //   1212: aload 5
    //   1214: pop
    //   1215: aload 8
    //   1217: areturn
    //   1218: iload 4
    //   1220: tableswitch	default:+32 -> 1252, 0:+-1152->68, 1:+32->1252, 2:+-1129->91, 3:+-247->973
    //   1253: iconst_4
    //   1254: astore 18
    //   1256: aload 6
    //   1258: astore 13
    //   1260: aload 5
    //   1262: astore 16
    //   1264: aload 8
    //   1266: astore_1
    //   1267: goto -1229 -> 38
    //
    // Exception table:
    //   from	to	target	type
    //   2	23	1071	java/lang/Exception
    //   38	46	1071	java/lang/Exception
    //   68	76	1078	java/lang/Exception
    //   91	121	1078	java/lang/Exception
    //   121	153	1078	java/lang/Exception
    //   153	177	1078	java/lang/Exception
    //   177	201	1078	java/lang/Exception
    //   201	223	1078	java/lang/Exception
    //   223	250	1078	java/lang/Exception
    //   973	1004	1078	java/lang/Exception
    //   255	284	1094	java/lang/Exception
    //   284	306	1094	java/lang/Exception
    //   306	338	1094	java/lang/Exception
    //   338	360	1094	java/lang/Exception
    //   360	392	1094	java/lang/Exception
    //   392	416	1094	java/lang/Exception
    //   416	438	1094	java/lang/Exception
    //   438	473	1094	java/lang/Exception
    //   473	497	1094	java/lang/Exception
    //   497	522	1094	java/lang/Exception
    //   522	573	1094	java/lang/Exception
    //   573	595	1094	java/lang/Exception
    //   595	617	1094	java/lang/Exception
    //   617	650	1094	java/lang/Exception
    //   650	680	1094	java/lang/Exception
    //   1007	1033	1094	java/lang/Exception
    //   685	706	1107	java/lang/Exception
    //   706	727	1107	java/lang/Exception
    //   727	751	1107	java/lang/Exception
    //   751	775	1107	java/lang/Exception
    //   775	800	1107	java/lang/Exception
    //   800	830	1107	java/lang/Exception
    //   1036	1062	1107	java/lang/Exception
    //   835	856	1117	java/lang/Exception
    //   856	877	1117	java/lang/Exception
    //   877	901	1117	java/lang/Exception
    //   901	922	1117	java/lang/Exception
    //   922	943	1117	java/lang/Exception
    //   943	967	1117	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.util.XmlUtil
 * JD-Core Version:    0.6.2
 */