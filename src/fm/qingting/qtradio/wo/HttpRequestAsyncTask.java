package fm.qingting.qtradio.wo;

import android.os.AsyncTask;
import com.alibaba.fastjson.JSONObject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONTokener;

public class HttpRequestAsyncTask extends AsyncTask<Object, Void, String>
{
  private Map<String, String> bodyParams;
  private String charset = "utf-8";
  private Object executeParams = null;
  private IHttpAsyncTaskListener handler;
  private Map<String, String> params;
  private RequestMethod requstMethod = RequestMethod.GET;
  private String url;

  public HttpRequestAsyncTask(String paramString, Map<String, String> paramMap, IHttpAsyncTaskListener paramIHttpAsyncTaskListener)
  {
    if (paramMap == null);
    for (this.params = createUrlParam(""); ; this.params = paramMap)
    {
      this.url = paramString;
      this.handler = paramIHttpAsyncTaskListener;
      return;
    }
  }

  public HttpRequestAsyncTask(String paramString, Map<String, String> paramMap1, Map<String, String> paramMap2, IHttpAsyncTaskListener paramIHttpAsyncTaskListener)
  {
    if (paramMap1 == null)
    {
      this.params = createUrlParam("");
      if (paramMap2 != null)
        break label75;
    }
    label75: for (this.bodyParams = createUrlParam(""); ; this.bodyParams = paramMap2)
    {
      this.url = paramString;
      this.requstMethod = RequestMethod.POST;
      this.handler = paramIHttpAsyncTaskListener;
      return;
      this.params = paramMap1;
      break;
    }
  }

  public static String byte2hex(byte[] paramArrayOfByte)
  {
    String str1 = "";
    int i = 0;
    if (i < paramArrayOfByte.length)
    {
      String str2 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      if (str2.length() == 1);
      for (str1 = str1 + "0" + str2; ; str1 = str1 + str2)
      {
        i++;
        break;
      }
    }
    return str1.toUpperCase();
  }

  public static Map<String, String> createParam(String paramString)
  {
    return createUrlParam(paramString);
  }

  public static Map<String, String> createUrlParam(String paramString)
  {
    HashMap localHashMap = new HashMap();
    if ((paramString != null) && (!paramString.trim().equals("")))
      for (String str : paramString.split(";"))
        localHashMap.put(str.split("=")[0], str.split("=")[1]);
    return localHashMap;
  }

  public static JSONObject parseJsonString(String paramString)
  {
    try
    {
      JSONObject localJSONObject = (JSONObject)new JSONTokener(paramString).nextValue();
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  // ERROR //
  protected String composeUrl(String paramString)
  {
    // Byte code:
    //   0: ldc 38
    //   2: astore_2
    //   3: new 134	java/util/Date
    //   6: dup
    //   7: invokespecial 135	java/util/Date:<init>	()V
    //   10: astore_3
    //   11: new 137	java/text/SimpleDateFormat
    //   14: dup
    //   15: ldc 139
    //   17: invokespecial 140	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   20: astore 4
    //   22: aload 4
    //   24: aload_3
    //   25: invokevirtual 146	java/text/DateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
    //   28: astore 32
    //   30: aload 32
    //   32: astore_2
    //   33: aload_0
    //   34: getfield 44	fm/qingting/qtradio/wo/HttpRequestAsyncTask:params	Ljava/util/Map;
    //   37: ldc 148
    //   39: aload_2
    //   40: invokeinterface 109 3 0
    //   45: pop
    //   46: aload_0
    //   47: getfield 44	fm/qingting/qtradio/wo/HttpRequestAsyncTask:params	Ljava/util/Map;
    //   50: ldc 150
    //   52: ldc 152
    //   54: invokeinterface 109 3 0
    //   59: pop
    //   60: new 154	java/util/ArrayList
    //   63: dup
    //   64: aload_0
    //   65: getfield 44	fm/qingting/qtradio/wo/HttpRequestAsyncTask:params	Ljava/util/Map;
    //   68: invokeinterface 158 1 0
    //   73: invokespecial 161	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   76: astore 8
    //   78: aload 8
    //   80: new 163	fm/qingting/qtradio/wo/HttpRequestAsyncTask$1
    //   83: dup
    //   84: aload_0
    //   85: invokespecial 166	fm/qingting/qtradio/wo/HttpRequestAsyncTask$1:<init>	(Lfm/qingting/qtradio/wo/HttpRequestAsyncTask;)V
    //   88: invokestatic 172	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   91: aload 8
    //   93: invokeinterface 178 1 0
    //   98: astore 29
    //   100: aload 29
    //   102: invokeinterface 184 1 0
    //   107: ifeq +48 -> 155
    //   110: aload 29
    //   112: invokeinterface 187 1 0
    //   117: checkcast 189	java/util/Map$Entry
    //   120: astore 30
    //   122: aload 30
    //   124: aload 30
    //   126: invokeinterface 192 1 0
    //   131: checkcast 64	java/lang/String
    //   134: ldc 25
    //   136: invokestatic 198	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   139: invokeinterface 202 2 0
    //   144: pop
    //   145: goto -45 -> 100
    //   148: astore 9
    //   150: aload 9
    //   152: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   155: ldc 38
    //   157: astore 10
    //   159: aload 8
    //   161: invokeinterface 178 1 0
    //   166: astore 26
    //   168: aload 26
    //   170: invokeinterface 184 1 0
    //   175: ifeq +75 -> 250
    //   178: aload 26
    //   180: invokeinterface 187 1 0
    //   185: checkcast 189	java/util/Map$Entry
    //   188: astore 27
    //   190: new 70	java/lang/StringBuilder
    //   193: dup
    //   194: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   197: aload 10
    //   199: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: aload 27
    //   204: invokeinterface 205 1 0
    //   209: checkcast 64	java/lang/String
    //   212: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: aload 27
    //   217: invokeinterface 192 1 0
    //   222: checkcast 64	java/lang/String
    //   225: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   231: astore 28
    //   233: aload 28
    //   235: astore 10
    //   237: goto -69 -> 168
    //   240: astore 5
    //   242: aload 5
    //   244: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   247: goto -214 -> 33
    //   250: aload 10
    //   252: astore 12
    //   254: new 70	java/lang/StringBuilder
    //   257: dup
    //   258: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   261: aload 12
    //   263: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   266: ldc 207
    //   268: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   274: astore 13
    //   276: ldc 38
    //   278: astore 14
    //   280: ldc 209
    //   282: invokestatic 215	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   285: astore 24
    //   287: aload 24
    //   289: aload 13
    //   291: invokevirtual 219	java/lang/String:getBytes	()[B
    //   294: invokevirtual 223	java/security/MessageDigest:update	([B)V
    //   297: aload 24
    //   299: invokevirtual 226	java/security/MessageDigest:digest	()[B
    //   302: invokestatic 228	fm/qingting/qtradio/wo/HttpRequestAsyncTask:byte2hex	([B)Ljava/lang/String;
    //   305: astore 25
    //   307: aload 25
    //   309: astore 14
    //   311: aload_0
    //   312: getfield 44	fm/qingting/qtradio/wo/HttpRequestAsyncTask:params	Ljava/util/Map;
    //   315: ldc 229
    //   317: aload 14
    //   319: invokeinterface 109 3 0
    //   324: pop
    //   325: ldc 38
    //   327: astore 17
    //   329: aload_0
    //   330: getfield 44	fm/qingting/qtradio/wo/HttpRequestAsyncTask:params	Ljava/util/Map;
    //   333: invokeinterface 158 1 0
    //   338: invokeinterface 232 1 0
    //   343: astore 21
    //   345: aload 21
    //   347: invokeinterface 184 1 0
    //   352: ifeq +93 -> 445
    //   355: aload 21
    //   357: invokeinterface 187 1 0
    //   362: checkcast 189	java/util/Map$Entry
    //   365: astore 22
    //   367: new 70	java/lang/StringBuilder
    //   370: dup
    //   371: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   374: aload 17
    //   376: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   379: aload 22
    //   381: invokeinterface 205 1 0
    //   386: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   389: ldc 103
    //   391: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: aload 22
    //   396: invokeinterface 192 1 0
    //   401: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   404: ldc 237
    //   406: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   412: astore 23
    //   414: aload 23
    //   416: astore 17
    //   418: goto -73 -> 345
    //   421: astore 11
    //   423: aload 10
    //   425: astore 12
    //   427: aload 11
    //   429: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   432: goto -178 -> 254
    //   435: astore 15
    //   437: aload 15
    //   439: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   442: goto -131 -> 311
    //   445: aload 17
    //   447: astore 19
    //   449: aload 19
    //   451: iconst_0
    //   452: iconst_m1
    //   453: aload 19
    //   455: invokevirtual 68	java/lang/String:length	()I
    //   458: iadd
    //   459: invokevirtual 241	java/lang/String:substring	(II)Ljava/lang/String;
    //   462: astore 20
    //   464: new 70	java/lang/StringBuilder
    //   467: dup
    //   468: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   471: aload_1
    //   472: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   475: ldc 243
    //   477: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   480: aload 20
    //   482: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   485: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   488: areturn
    //   489: astore 18
    //   491: aload 17
    //   493: astore 19
    //   495: aload 18
    //   497: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   500: goto -51 -> 449
    //
    // Exception table:
    //   from	to	target	type
    //   91	100	148	java/lang/Exception
    //   100	145	148	java/lang/Exception
    //   22	30	240	java/lang/Exception
    //   159	168	421	java/lang/Exception
    //   168	233	421	java/lang/Exception
    //   280	307	435	java/lang/Exception
    //   329	345	489	java/lang/Exception
    //   345	414	489	java/lang/Exception
  }

  protected String doInBackground(Object[] paramArrayOfObject)
  {
    this.executeParams = paramArrayOfObject[0];
    if (this.requstMethod == RequestMethod.GET)
      return doRequest();
    if (this.requstMethod == RequestMethod.POST)
      return doPostRequest();
    return null;
  }

  // ERROR //
  public String doPostRequest()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 266	org/apache/http/impl/client/DefaultHttpClient
    //   5: dup
    //   6: new 268	org/apache/http/params/BasicHttpParams
    //   9: dup
    //   10: invokespecial 269	org/apache/http/params/BasicHttpParams:<init>	()V
    //   13: invokespecial 272	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
    //   16: astore_2
    //   17: aload_2
    //   18: invokevirtual 276	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   21: ldc_w 278
    //   24: sipush 10000
    //   27: invokestatic 282	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   30: invokeinterface 288 3 0
    //   35: pop
    //   36: aload_2
    //   37: invokevirtual 276	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   40: ldc_w 290
    //   43: sipush 15000
    //   46: invokestatic 282	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   49: invokeinterface 288 3 0
    //   54: pop
    //   55: aload_0
    //   56: aload_0
    //   57: getfield 46	fm/qingting/qtradio/wo/HttpRequestAsyncTask:url	Ljava/lang/String;
    //   60: invokevirtual 292	fm/qingting/qtradio/wo/HttpRequestAsyncTask:composeUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   63: astore 5
    //   65: new 294	org/apache/http/client/methods/HttpPost
    //   68: dup
    //   69: aload 5
    //   71: invokespecial 295	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   74: astore 6
    //   76: aload 6
    //   78: ldc_w 297
    //   81: ldc_w 299
    //   84: invokevirtual 303	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   87: aload 6
    //   89: ldc_w 305
    //   92: ldc_w 307
    //   95: invokevirtual 303	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   98: aload 6
    //   100: ldc_w 309
    //   103: ldc_w 311
    //   106: invokevirtual 314	org/apache/http/client/methods/HttpPost:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   109: new 154	java/util/ArrayList
    //   112: dup
    //   113: invokespecial 315	java/util/ArrayList:<init>	()V
    //   116: astore 7
    //   118: aload_0
    //   119: getfield 51	fm/qingting/qtradio/wo/HttpRequestAsyncTask:bodyParams	Ljava/util/Map;
    //   122: invokeinterface 158 1 0
    //   127: invokeinterface 232 1 0
    //   132: astore 8
    //   134: aload 8
    //   136: invokeinterface 184 1 0
    //   141: ifeq +53 -> 194
    //   144: aload 8
    //   146: invokeinterface 187 1 0
    //   151: checkcast 189	java/util/Map$Entry
    //   154: astore 64
    //   156: aload 7
    //   158: new 317	org/apache/http/message/BasicNameValuePair
    //   161: dup
    //   162: aload 64
    //   164: invokeinterface 205 1 0
    //   169: checkcast 64	java/lang/String
    //   172: aload 64
    //   174: invokeinterface 192 1 0
    //   179: checkcast 64	java/lang/String
    //   182: invokespecial 319	org/apache/http/message/BasicNameValuePair:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   185: invokeinterface 322 2 0
    //   190: pop
    //   191: goto -57 -> 134
    //   194: new 324	org/apache/http/client/entity/UrlEncodedFormEntity
    //   197: dup
    //   198: aload 7
    //   200: ldc_w 326
    //   203: invokespecial 329	org/apache/http/client/entity/UrlEncodedFormEntity:<init>	(Ljava/util/List;Ljava/lang/String;)V
    //   206: astore 9
    //   208: aload 6
    //   210: aload 9
    //   212: invokevirtual 333	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   215: new 126	com/alibaba/fastjson/JSONObject
    //   218: dup
    //   219: invokespecial 334	com/alibaba/fastjson/JSONObject:<init>	()V
    //   222: astore 10
    //   224: new 137	java/text/SimpleDateFormat
    //   227: dup
    //   228: ldc_w 336
    //   231: invokespecial 140	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   234: new 134	java/util/Date
    //   237: dup
    //   238: invokespecial 135	java/util/Date:<init>	()V
    //   241: invokevirtual 146	java/text/DateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
    //   244: astore 11
    //   246: aload 10
    //   248: ldc_w 338
    //   251: aload 5
    //   253: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   256: pop
    //   257: aload 10
    //   259: ldc_w 343
    //   262: aload 11
    //   264: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   267: pop
    //   268: aload_2
    //   269: aload 6
    //   271: invokevirtual 347	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   274: astore 32
    //   276: aload 32
    //   278: ifnonnull +208 -> 486
    //   281: aload 10
    //   283: ldc_w 349
    //   286: ldc_w 351
    //   289: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   292: pop
    //   293: aload 10
    //   295: ldc_w 353
    //   298: aconst_null
    //   299: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   302: pop
    //   303: aload 10
    //   305: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   308: areturn
    //   309: astore 63
    //   311: aload 63
    //   313: invokevirtual 355	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   316: aconst_null
    //   317: astore 9
    //   319: goto -111 -> 208
    //   322: astore 27
    //   324: aload 10
    //   326: ldc_w 357
    //   329: ldc_w 359
    //   332: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   335: pop
    //   336: aload 10
    //   338: ldc_w 349
    //   341: aload 27
    //   343: invokevirtual 362	java/lang/OutOfMemoryError:getMessage	()Ljava/lang/String;
    //   346: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   349: pop
    //   350: aload 10
    //   352: ldc_w 353
    //   355: aconst_null
    //   356: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   359: pop
    //   360: aload 10
    //   362: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   365: areturn
    //   366: astore 22
    //   368: aload 10
    //   370: ldc_w 357
    //   373: ldc_w 364
    //   376: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   379: pop
    //   380: aload 10
    //   382: ldc_w 349
    //   385: aload 22
    //   387: invokevirtual 365	java/net/SocketTimeoutException:getMessage	()Ljava/lang/String;
    //   390: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   393: pop
    //   394: aload 10
    //   396: ldc_w 353
    //   399: aconst_null
    //   400: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   403: pop
    //   404: aload 10
    //   406: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   409: areturn
    //   410: astore 17
    //   412: aload 10
    //   414: ldc_w 357
    //   417: ldc_w 367
    //   420: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   423: pop
    //   424: aload 10
    //   426: ldc_w 349
    //   429: aload 17
    //   431: invokevirtual 368	org/apache/http/conn/ConnectTimeoutException:getMessage	()Ljava/lang/String;
    //   434: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   437: pop
    //   438: aload 10
    //   440: ldc_w 353
    //   443: aconst_null
    //   444: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   447: pop
    //   448: aload 10
    //   450: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   453: areturn
    //   454: astore 13
    //   456: aload 10
    //   458: ldc_w 349
    //   461: aload 13
    //   463: invokevirtual 369	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   466: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   469: pop
    //   470: aload 10
    //   472: ldc_w 353
    //   475: aconst_null
    //   476: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   479: pop
    //   480: aload 10
    //   482: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   485: areturn
    //   486: aload 32
    //   488: invokeinterface 375 1 0
    //   493: astore 33
    //   495: aload 33
    //   497: ifnull +322 -> 819
    //   500: aload 33
    //   502: invokeinterface 381 1 0
    //   507: astore_1
    //   508: aload 32
    //   510: ldc_w 383
    //   513: invokeinterface 387 2 0
    //   518: astore 47
    //   520: aload 47
    //   522: ifnull +28 -> 550
    //   525: aload 47
    //   527: invokeinterface 391 1 0
    //   532: ldc_w 393
    //   535: invokevirtual 397	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   538: ifeq +12 -> 550
    //   541: new 399	java/util/zip/GZIPInputStream
    //   544: dup
    //   545: aload_1
    //   546: invokespecial 402	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   549: astore_1
    //   550: new 404	java/io/ByteArrayOutputStream
    //   553: dup
    //   554: invokespecial 405	java/io/ByteArrayOutputStream:<init>	()V
    //   557: astore 48
    //   559: sipush 1024
    //   562: newarray byte
    //   564: astore 49
    //   566: aload_1
    //   567: aload 49
    //   569: iconst_0
    //   570: aload 49
    //   572: arraylength
    //   573: invokevirtual 411	java/io/InputStream:read	([BII)I
    //   576: istore 50
    //   578: iload 50
    //   580: iconst_m1
    //   581: if_icmpeq +77 -> 658
    //   584: aload 48
    //   586: aload 49
    //   588: iconst_0
    //   589: iload 50
    //   591: invokevirtual 415	java/io/ByteArrayOutputStream:write	([BII)V
    //   594: goto -28 -> 566
    //   597: astore 40
    //   599: aload 10
    //   601: ldc_w 417
    //   604: aload 40
    //   606: invokevirtual 369	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   609: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   612: pop
    //   613: aload 10
    //   615: ldc_w 353
    //   618: aconst_null
    //   619: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   622: pop
    //   623: aload 10
    //   625: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   628: astore 42
    //   630: aload_1
    //   631: ifnull +7 -> 638
    //   634: aload_1
    //   635: invokevirtual 420	java/io/InputStream:close	()V
    //   638: aload 33
    //   640: invokeinterface 423 1 0
    //   645: aload 42
    //   647: areturn
    //   648: astore 43
    //   650: aload 43
    //   652: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   655: aload 42
    //   657: areturn
    //   658: aload 48
    //   660: invokevirtual 427	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   663: astore 51
    //   665: aload_0
    //   666: getfield 27	fm/qingting/qtradio/wo/HttpRequestAsyncTask:charset	Ljava/lang/String;
    //   669: ifnull +76 -> 745
    //   672: aload_0
    //   673: getfield 27	fm/qingting/qtradio/wo/HttpRequestAsyncTask:charset	Ljava/lang/String;
    //   676: ldc 38
    //   678: invokevirtual 397	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   681: ifne +64 -> 745
    //   684: new 64	java/lang/String
    //   687: dup
    //   688: aload 51
    //   690: aload_0
    //   691: getfield 27	fm/qingting/qtradio/wo/HttpRequestAsyncTask:charset	Ljava/lang/String;
    //   694: invokespecial 430	java/lang/String:<init>	([BLjava/lang/String;)V
    //   697: astore 52
    //   699: aload 10
    //   701: ldc_w 353
    //   704: aload 52
    //   706: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   709: pop
    //   710: aload 10
    //   712: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   715: astore 54
    //   717: aload_1
    //   718: ifnull +7 -> 725
    //   721: aload_1
    //   722: invokevirtual 420	java/io/InputStream:close	()V
    //   725: aload 33
    //   727: invokeinterface 423 1 0
    //   732: aload 54
    //   734: areturn
    //   735: astore 55
    //   737: aload 55
    //   739: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   742: aload 54
    //   744: areturn
    //   745: new 64	java/lang/String
    //   748: dup
    //   749: aload 51
    //   751: invokespecial 432	java/lang/String:<init>	([B)V
    //   754: astore 52
    //   756: goto -57 -> 699
    //   759: astore 37
    //   761: aload_1
    //   762: ifnull +7 -> 769
    //   765: aload_1
    //   766: invokevirtual 420	java/io/InputStream:close	()V
    //   769: aload 33
    //   771: invokeinterface 423 1 0
    //   776: aload 37
    //   778: athrow
    //   779: astore 56
    //   781: aload 56
    //   783: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   786: goto -61 -> 725
    //   789: astore 44
    //   791: aload 44
    //   793: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   796: goto -158 -> 638
    //   799: astore 39
    //   801: aload 39
    //   803: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   806: goto -37 -> 769
    //   809: astore 38
    //   811: aload 38
    //   813: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   816: goto -40 -> 776
    //   819: aload 10
    //   821: ldc_w 417
    //   824: ldc_w 434
    //   827: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   830: pop
    //   831: aload 10
    //   833: ldc_w 353
    //   836: aconst_null
    //   837: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   840: pop
    //   841: aload 10
    //   843: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   846: areturn
    //   847: astore 34
    //   849: goto -8 -> 841
    //   852: astore 41
    //   854: goto -231 -> 623
    //   857: astore 53
    //   859: goto -149 -> 710
    //   862: astore 58
    //   864: goto -561 -> 303
    //   867: astore 14
    //   869: goto -389 -> 480
    //   872: astore 18
    //   874: goto -426 -> 448
    //   877: astore 23
    //   879: goto -475 -> 404
    //   882: astore 28
    //   884: goto -524 -> 360
    //   887: astore 12
    //   889: goto -621 -> 268
    //
    // Exception table:
    //   from	to	target	type
    //   194	208	309	java/io/UnsupportedEncodingException
    //   268	276	322	java/lang/OutOfMemoryError
    //   268	276	366	java/net/SocketTimeoutException
    //   268	276	410	org/apache/http/conn/ConnectTimeoutException
    //   268	276	454	java/lang/Exception
    //   500	520	597	java/lang/Exception
    //   525	550	597	java/lang/Exception
    //   550	566	597	java/lang/Exception
    //   566	578	597	java/lang/Exception
    //   584	594	597	java/lang/Exception
    //   658	699	597	java/lang/Exception
    //   710	717	597	java/lang/Exception
    //   745	756	597	java/lang/Exception
    //   638	645	648	java/io/IOException
    //   725	732	735	java/io/IOException
    //   500	520	759	finally
    //   525	550	759	finally
    //   550	566	759	finally
    //   566	578	759	finally
    //   584	594	759	finally
    //   599	623	759	finally
    //   623	630	759	finally
    //   658	699	759	finally
    //   699	710	759	finally
    //   710	717	759	finally
    //   745	756	759	finally
    //   721	725	779	java/io/IOException
    //   634	638	789	java/io/IOException
    //   765	769	799	java/io/IOException
    //   769	776	809	java/io/IOException
    //   819	841	847	java/lang/Exception
    //   599	623	852	java/lang/Exception
    //   699	710	857	java/lang/Exception
    //   281	303	862	java/lang/Exception
    //   456	480	867	java/lang/Exception
    //   412	448	872	java/lang/Exception
    //   368	404	877	java/lang/Exception
    //   324	360	882	java/lang/Exception
    //   246	268	887	java/lang/Exception
  }

  // ERROR //
  public String doRequest()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 266	org/apache/http/impl/client/DefaultHttpClient
    //   5: dup
    //   6: new 268	org/apache/http/params/BasicHttpParams
    //   9: dup
    //   10: invokespecial 269	org/apache/http/params/BasicHttpParams:<init>	()V
    //   13: invokespecial 272	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
    //   16: astore_2
    //   17: aload_2
    //   18: invokevirtual 276	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   21: ldc_w 278
    //   24: sipush 10000
    //   27: invokestatic 282	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   30: invokeinterface 288 3 0
    //   35: pop
    //   36: aload_2
    //   37: invokevirtual 276	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   40: ldc_w 290
    //   43: sipush 15000
    //   46: invokestatic 282	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   49: invokeinterface 288 3 0
    //   54: pop
    //   55: aload_0
    //   56: aload_0
    //   57: getfield 46	fm/qingting/qtradio/wo/HttpRequestAsyncTask:url	Ljava/lang/String;
    //   60: invokevirtual 292	fm/qingting/qtradio/wo/HttpRequestAsyncTask:composeUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   63: astore 6
    //   65: new 436	org/apache/http/client/methods/HttpGet
    //   68: dup
    //   69: aload 6
    //   71: invokespecial 437	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   74: astore 7
    //   76: aload 7
    //   78: ldc_w 305
    //   81: ldc_w 307
    //   84: invokeinterface 440 3 0
    //   89: aload 7
    //   91: ldc_w 309
    //   94: ldc_w 311
    //   97: invokeinterface 441 3 0
    //   102: new 126	com/alibaba/fastjson/JSONObject
    //   105: dup
    //   106: invokespecial 334	com/alibaba/fastjson/JSONObject:<init>	()V
    //   109: astore 8
    //   111: new 137	java/text/SimpleDateFormat
    //   114: dup
    //   115: ldc_w 336
    //   118: invokespecial 140	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   121: new 134	java/util/Date
    //   124: dup
    //   125: invokespecial 135	java/util/Date:<init>	()V
    //   128: invokevirtual 146	java/text/DateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
    //   131: astore 9
    //   133: aload 8
    //   135: ldc_w 338
    //   138: aload 6
    //   140: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   143: pop
    //   144: aload 8
    //   146: ldc_w 343
    //   149: aload 9
    //   151: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   154: pop
    //   155: aload_2
    //   156: aload 7
    //   158: invokevirtual 347	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   161: astore 27
    //   163: aload 27
    //   165: ifnonnull +163 -> 328
    //   168: aload 8
    //   170: ldc_w 349
    //   173: ldc_w 351
    //   176: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   179: pop
    //   180: aload 8
    //   182: ldc_w 353
    //   185: aconst_null
    //   186: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   189: pop
    //   190: aload 8
    //   192: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   195: astore 55
    //   197: aload 55
    //   199: areturn
    //   200: astore 21
    //   202: aload 8
    //   204: ldc_w 357
    //   207: ldc_w 364
    //   210: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   213: pop
    //   214: aload 8
    //   216: ldc_w 349
    //   219: aload 21
    //   221: invokevirtual 365	java/net/SocketTimeoutException:getMessage	()Ljava/lang/String;
    //   224: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   227: pop
    //   228: aload 8
    //   230: ldc_w 353
    //   233: aconst_null
    //   234: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   237: pop
    //   238: aload 8
    //   240: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   243: astore 23
    //   245: aload 23
    //   247: areturn
    //   248: astore 15
    //   250: aload 8
    //   252: ldc_w 357
    //   255: ldc_w 367
    //   258: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   261: pop
    //   262: aload 8
    //   264: ldc_w 349
    //   267: aload 15
    //   269: invokevirtual 368	org/apache/http/conn/ConnectTimeoutException:getMessage	()Ljava/lang/String;
    //   272: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   275: pop
    //   276: aload 8
    //   278: ldc_w 353
    //   281: aconst_null
    //   282: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   285: pop
    //   286: aload 8
    //   288: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   291: astore 17
    //   293: aload 17
    //   295: areturn
    //   296: astore 11
    //   298: aload 8
    //   300: ldc_w 349
    //   303: aload 11
    //   305: invokevirtual 369	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   308: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   311: pop
    //   312: aload 8
    //   314: ldc_w 353
    //   317: aconst_null
    //   318: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   321: pop
    //   322: aload 8
    //   324: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   327: areturn
    //   328: aload 27
    //   330: invokeinterface 375 1 0
    //   335: astore 28
    //   337: aload 28
    //   339: ifnull +326 -> 665
    //   342: aload 28
    //   344: invokeinterface 381 1 0
    //   349: astore_1
    //   350: aload 27
    //   352: ldc_w 383
    //   355: invokeinterface 387 2 0
    //   360: astore 43
    //   362: aload 43
    //   364: ifnull +28 -> 392
    //   367: aload 43
    //   369: invokeinterface 391 1 0
    //   374: ldc_w 393
    //   377: invokevirtual 397	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   380: ifeq +12 -> 392
    //   383: new 399	java/util/zip/GZIPInputStream
    //   386: dup
    //   387: aload_1
    //   388: invokespecial 402	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   391: astore_1
    //   392: new 404	java/io/ByteArrayOutputStream
    //   395: dup
    //   396: invokespecial 405	java/io/ByteArrayOutputStream:<init>	()V
    //   399: astore 44
    //   401: sipush 1024
    //   404: newarray byte
    //   406: astore 45
    //   408: aload_1
    //   409: aload 45
    //   411: iconst_0
    //   412: aload 45
    //   414: arraylength
    //   415: invokevirtual 411	java/io/InputStream:read	([BII)I
    //   418: istore 46
    //   420: iload 46
    //   422: iconst_m1
    //   423: if_icmpeq +81 -> 504
    //   426: aload 44
    //   428: aload 45
    //   430: iconst_0
    //   431: iload 46
    //   433: invokevirtual 415	java/io/ByteArrayOutputStream:write	([BII)V
    //   436: goto -28 -> 408
    //   439: astore 36
    //   441: aload 8
    //   443: ldc_w 417
    //   446: aload 36
    //   448: invokevirtual 369	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   451: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   454: pop
    //   455: aload 8
    //   457: ldc_w 353
    //   460: aconst_null
    //   461: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   464: pop
    //   465: aload 8
    //   467: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   470: astore 38
    //   472: aload_1
    //   473: ifnull +7 -> 480
    //   476: aload_1
    //   477: invokevirtual 420	java/io/InputStream:close	()V
    //   480: aload 28
    //   482: invokeinterface 423 1 0
    //   487: aload 38
    //   489: areturn
    //   490: astore 39
    //   492: aload 39
    //   494: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   497: aload 38
    //   499: areturn
    //   500: astore_3
    //   501: ldc 38
    //   503: areturn
    //   504: aload 44
    //   506: invokevirtual 427	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   509: astore 47
    //   511: aload_0
    //   512: getfield 27	fm/qingting/qtradio/wo/HttpRequestAsyncTask:charset	Ljava/lang/String;
    //   515: ifnull +76 -> 591
    //   518: aload_0
    //   519: getfield 27	fm/qingting/qtradio/wo/HttpRequestAsyncTask:charset	Ljava/lang/String;
    //   522: ldc 38
    //   524: invokevirtual 397	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   527: ifne +64 -> 591
    //   530: new 64	java/lang/String
    //   533: dup
    //   534: aload 47
    //   536: aload_0
    //   537: getfield 27	fm/qingting/qtradio/wo/HttpRequestAsyncTask:charset	Ljava/lang/String;
    //   540: invokespecial 430	java/lang/String:<init>	([BLjava/lang/String;)V
    //   543: astore 48
    //   545: aload 8
    //   547: ldc_w 353
    //   550: aload 48
    //   552: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   555: pop
    //   556: aload 8
    //   558: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   561: astore 50
    //   563: aload_1
    //   564: ifnull +7 -> 571
    //   567: aload_1
    //   568: invokevirtual 420	java/io/InputStream:close	()V
    //   571: aload 28
    //   573: invokeinterface 423 1 0
    //   578: aload 50
    //   580: areturn
    //   581: astore 51
    //   583: aload 51
    //   585: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   588: aload 50
    //   590: areturn
    //   591: new 64	java/lang/String
    //   594: dup
    //   595: aload 47
    //   597: invokespecial 432	java/lang/String:<init>	([B)V
    //   600: astore 48
    //   602: goto -57 -> 545
    //   605: astore 33
    //   607: aload_1
    //   608: ifnull +7 -> 615
    //   611: aload_1
    //   612: invokevirtual 420	java/io/InputStream:close	()V
    //   615: aload 28
    //   617: invokeinterface 423 1 0
    //   622: aload 33
    //   624: athrow
    //   625: astore 52
    //   627: aload 52
    //   629: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   632: goto -61 -> 571
    //   635: astore 40
    //   637: aload 40
    //   639: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   642: goto -162 -> 480
    //   645: astore 35
    //   647: aload 35
    //   649: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   652: goto -37 -> 615
    //   655: astore 34
    //   657: aload 34
    //   659: invokevirtual 424	java/io/IOException:printStackTrace	()V
    //   662: goto -40 -> 622
    //   665: aload 8
    //   667: ldc_w 417
    //   670: ldc_w 434
    //   673: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   676: pop
    //   677: aload 8
    //   679: ldc_w 353
    //   682: aconst_null
    //   683: invokevirtual 341	com/alibaba/fastjson/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   686: pop
    //   687: aload 8
    //   689: invokevirtual 354	com/alibaba/fastjson/JSONObject:toString	()Ljava/lang/String;
    //   692: astore 30
    //   694: aload 30
    //   696: areturn
    //   697: astore 29
    //   699: goto -12 -> 687
    //   702: astore 37
    //   704: goto -239 -> 465
    //   707: astore 49
    //   709: goto -153 -> 556
    //   712: astore 54
    //   714: goto -524 -> 190
    //   717: astore 12
    //   719: goto -397 -> 322
    //   722: astore 16
    //   724: goto -438 -> 286
    //   727: astore 22
    //   729: goto -491 -> 238
    //   732: astore 10
    //   734: goto -579 -> 155
    //
    // Exception table:
    //   from	to	target	type
    //   155	163	200	java/net/SocketTimeoutException
    //   155	163	248	org/apache/http/conn/ConnectTimeoutException
    //   155	163	296	java/lang/Exception
    //   342	362	439	java/lang/Exception
    //   367	392	439	java/lang/Exception
    //   392	408	439	java/lang/Exception
    //   408	420	439	java/lang/Exception
    //   426	436	439	java/lang/Exception
    //   504	545	439	java/lang/Exception
    //   556	563	439	java/lang/Exception
    //   591	602	439	java/lang/Exception
    //   480	487	490	java/io/IOException
    //   2	133	500	java/lang/Exception
    //   190	197	500	java/lang/Exception
    //   238	245	500	java/lang/Exception
    //   286	293	500	java/lang/Exception
    //   322	328	500	java/lang/Exception
    //   328	337	500	java/lang/Exception
    //   476	480	500	java/lang/Exception
    //   480	487	500	java/lang/Exception
    //   492	497	500	java/lang/Exception
    //   567	571	500	java/lang/Exception
    //   571	578	500	java/lang/Exception
    //   583	588	500	java/lang/Exception
    //   611	615	500	java/lang/Exception
    //   615	622	500	java/lang/Exception
    //   622	625	500	java/lang/Exception
    //   627	632	500	java/lang/Exception
    //   637	642	500	java/lang/Exception
    //   647	652	500	java/lang/Exception
    //   657	662	500	java/lang/Exception
    //   687	694	500	java/lang/Exception
    //   571	578	581	java/io/IOException
    //   342	362	605	finally
    //   367	392	605	finally
    //   392	408	605	finally
    //   408	420	605	finally
    //   426	436	605	finally
    //   441	465	605	finally
    //   465	472	605	finally
    //   504	545	605	finally
    //   545	556	605	finally
    //   556	563	605	finally
    //   591	602	605	finally
    //   567	571	625	java/io/IOException
    //   476	480	635	java/io/IOException
    //   611	615	645	java/io/IOException
    //   615	622	655	java/io/IOException
    //   665	687	697	java/lang/Exception
    //   441	465	702	java/lang/Exception
    //   545	556	707	java/lang/Exception
    //   168	190	712	java/lang/Exception
    //   298	322	717	java/lang/Exception
    //   250	286	722	java/lang/Exception
    //   202	238	727	java/lang/Exception
    //   133	155	732	java/lang/Exception
  }

  protected void onPostExecute(String paramString)
  {
    super.onPostExecute(paramString);
    if (this.handler != null)
      this.handler.onGetResult(this.executeParams, paramString);
  }

  public static enum RequestMethod
  {
    static
    {
      HEAD = new RequestMethod("HEAD", 2);
      RequestMethod[] arrayOfRequestMethod = new RequestMethod[3];
      arrayOfRequestMethod[0] = GET;
      arrayOfRequestMethod[1] = POST;
      arrayOfRequestMethod[2] = HEAD;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.wo.HttpRequestAsyncTask
 * JD-Core Version:    0.6.2
 */