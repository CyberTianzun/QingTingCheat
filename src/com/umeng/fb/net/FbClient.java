package com.umeng.fb.net;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.Store;
import com.umeng.fb.model.UserInfo;
import com.umeng.fb.model.UserReply;
import com.umeng.fb.model.UserTitleReply;
import com.umeng.fb.util.Helper;
import com.umeng.fb.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FbClient
{
  public static final String FEEDBACK_BASE_URL = "http://feedback.umeng.com/feedback";
  public static final String FEEDBACK_Dev_Reply_URL = "http://feedback.umeng.com/feedback/reply";
  public static final String FEEDBACK_NewFeedback_URL = "http://feedback.umeng.com/feedback/feedbacks";
  public static final String FEEDBACK_UER_REPLY_URL = "http://feedback.umeng.com/feedback/reply";
  private static final int REGISTRATION_TIMEOUT_MS = 30000;
  private static final String TAG = FbClient.class.getName();
  private Context mContext;

  public FbClient(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private void addRequestHeader(JSONObject paramJSONObject)
  {
    try
    {
      JSONObject localJSONObject = Helper.getMessageHeader(this.mContext);
      Log.d(TAG, "addRequestHeader: " + localJSONObject.toString());
      Iterator localIterator = localJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramJSONObject.put(str, localJSONObject.get(str));
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  private void addUserInfoIfNotSynced(JSONObject paramJSONObject)
  {
    try
    {
      long l1 = Store.getInstance(this.mContext).getUserInfoLastSyncAt();
      long l2 = Store.getInstance(this.mContext).getUserInfoLastUpdateAt();
      Log.d(TAG, "addUserInfoIfNotSynced: last_sync_at=" + l1 + " last_update_at=" + l2);
      if (l1 < l2)
        paramJSONObject.put("userinfo", Store.getInstance(this.mContext).getUserInfo().toJson());
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  private boolean sendUserReply(UserReply paramUserReply)
  {
    try
    {
      JSONObject localJSONObject = paramUserReply.toJson();
      addRequestHeader(localJSONObject);
      addUserInfoIfNotSynced(localJSONObject);
      FbResponse localFbResponse = execute(new FbRequest("reply", localJSONObject, "http://feedback.umeng.com/feedback/reply"));
      if (localFbResponse != null)
      {
        boolean bool = "ok".equalsIgnoreCase(localFbResponse.getJson().get("state").toString());
        if (bool)
          return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private boolean sendUserTitleReply(UserTitleReply paramUserTitleReply)
  {
    try
    {
      JSONObject localJSONObject = paramUserTitleReply.toJson();
      addRequestHeader(localJSONObject);
      addUserInfoIfNotSynced(localJSONObject);
      FbResponse localFbResponse = execute(new FbRequest("feedback", localJSONObject, "http://feedback.umeng.com/feedback/feedbacks"));
      if (localFbResponse != null)
      {
        boolean bool = "ok".equalsIgnoreCase(localFbResponse.getJson().get("state").toString());
        if (bool)
          return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  // ERROR //
  public FbResponse execute(FbRequest paramFbRequest)
  {
    // Byte code:
    //   0: new 178	java/util/Random
    //   3: dup
    //   4: invokespecial 179	java/util/Random:<init>	()V
    //   7: sipush 1000
    //   10: invokevirtual 183	java/util/Random:nextInt	(I)I
    //   13: istore_2
    //   14: aload_1
    //   15: getfield 186	com/umeng/fb/net/FbRequest:mReportContent	Ljava/lang/String;
    //   18: astore_3
    //   19: aload_1
    //   20: getfield 189	com/umeng/fb/net/FbRequest:mKey	Ljava/lang/String;
    //   23: astore 4
    //   25: aload_1
    //   26: getfield 193	com/umeng/fb/net/FbRequest:mValue	Lorg/json/JSONObject;
    //   29: astore 5
    //   31: aload_1
    //   32: instanceof 141
    //   35: ifne +13 -> 48
    //   38: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   41: ldc 195
    //   43: invokestatic 198	com/umeng/fb/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   46: aconst_null
    //   47: areturn
    //   48: aload_3
    //   49: invokevirtual 202	java/lang/String:length	()I
    //   52: iconst_1
    //   53: if_icmpgt +30 -> 83
    //   56: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   59: new 49	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   66: iload_2
    //   67: invokevirtual 205	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   70: ldc 207
    //   72: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   78: invokestatic 198	com/umeng/fb/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   81: aconst_null
    //   82: areturn
    //   83: aload 4
    //   85: ifnull +306 -> 391
    //   88: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   91: new 49	java/lang/StringBuilder
    //   94: dup
    //   95: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   98: iload_2
    //   99: invokevirtual 205	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   102: ldc 209
    //   104: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload_3
    //   108: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: ldc 211
    //   113: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: aload 5
    //   118: invokevirtual 61	org/json/JSONObject:toString	()Ljava/lang/String;
    //   121: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   127: invokestatic 214	com/umeng/fb/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   130: new 216	java/util/ArrayList
    //   133: dup
    //   134: iconst_1
    //   135: invokespecial 219	java/util/ArrayList:<init>	(I)V
    //   138: astore 14
    //   140: aload 14
    //   142: new 221	org/apache/http/message/BasicNameValuePair
    //   145: dup
    //   146: aload 4
    //   148: aload 5
    //   150: invokevirtual 61	org/json/JSONObject:toString	()Ljava/lang/String;
    //   153: invokespecial 223	org/apache/http/message/BasicNameValuePair:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   156: invokeinterface 229 2 0
    //   161: pop
    //   162: new 231	org/apache/http/client/entity/UrlEncodedFormEntity
    //   165: dup
    //   166: aload 14
    //   168: ldc 233
    //   170: invokespecial 236	org/apache/http/client/entity/UrlEncodedFormEntity:<init>	(Ljava/util/List;Ljava/lang/String;)V
    //   173: astore 16
    //   175: new 238	org/apache/http/client/methods/HttpPost
    //   178: dup
    //   179: aload_3
    //   180: invokespecial 241	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   183: astore 6
    //   185: aload 6
    //   187: aload 16
    //   189: invokeinterface 247 1 0
    //   194: invokeinterface 253 2 0
    //   199: aload 6
    //   201: checkcast 238	org/apache/http/client/methods/HttpPost
    //   204: aload 16
    //   206: invokevirtual 257	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   209: new 259	org/apache/http/impl/client/DefaultHttpClient
    //   212: dup
    //   213: invokespecial 260	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   216: astore 7
    //   218: aload 7
    //   220: invokeinterface 266 1 0
    //   225: astore 8
    //   227: aload 8
    //   229: sipush 30000
    //   232: invokestatic 272	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   235: aload 8
    //   237: sipush 30000
    //   240: invokestatic 275	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   243: aload 8
    //   245: ldc2_w 276
    //   248: invokestatic 283	org/apache/http/conn/params/ConnManagerParams:setTimeout	(Lorg/apache/http/params/HttpParams;J)V
    //   251: aload 7
    //   253: aload 6
    //   255: checkcast 285	org/apache/http/client/methods/HttpUriRequest
    //   258: invokeinterface 288 2 0
    //   263: astore 11
    //   265: aload 11
    //   267: invokeinterface 294 1 0
    //   272: invokeinterface 299 1 0
    //   277: sipush 200
    //   280: if_icmpne +190 -> 470
    //   283: aload 11
    //   285: invokeinterface 303 1 0
    //   290: invokestatic 308	org/apache/http/util/EntityUtils:toString	(Lorg/apache/http/HttpEntity;)Ljava/lang/String;
    //   293: astore 12
    //   295: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   298: new 49	java/lang/StringBuilder
    //   301: dup
    //   302: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   305: ldc_w 310
    //   308: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: aload 12
    //   313: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   316: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   319: invokestatic 214	com/umeng/fb/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   322: new 154	com/umeng/fb/net/FbResponse
    //   325: dup
    //   326: new 58	org/json/JSONObject
    //   329: dup
    //   330: aload 12
    //   332: invokespecial 311	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   335: invokespecial 313	com/umeng/fb/net/FbResponse:<init>	(Lorg/json/JSONObject;)V
    //   338: astore 13
    //   340: aload 13
    //   342: areturn
    //   343: astore 10
    //   345: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   348: new 49	java/lang/StringBuilder
    //   351: dup
    //   352: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   355: iload_2
    //   356: invokevirtual 205	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   359: ldc_w 315
    //   362: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   365: aload_3
    //   366: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   372: aload 10
    //   374: invokestatic 318	com/umeng/fb/util/Log:d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   377: aconst_null
    //   378: areturn
    //   379: astore 17
    //   381: new 320	java/lang/AssertionError
    //   384: dup
    //   385: aload 17
    //   387: invokespecial 323	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   390: athrow
    //   391: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   394: new 49	java/lang/StringBuilder
    //   397: dup
    //   398: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   401: iload_2
    //   402: invokevirtual 205	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   405: ldc_w 325
    //   408: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   411: aload_3
    //   412: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   415: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   418: invokestatic 214	com/umeng/fb/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   421: new 327	org/apache/http/client/methods/HttpGet
    //   424: dup
    //   425: aload_3
    //   426: invokespecial 328	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   429: astore 6
    //   431: goto -222 -> 209
    //   434: astore 9
    //   436: getstatic 31	com/umeng/fb/net/FbClient:TAG	Ljava/lang/String;
    //   439: new 49	java/lang/StringBuilder
    //   442: dup
    //   443: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   446: iload_2
    //   447: invokevirtual 205	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   450: ldc_w 330
    //   453: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: aload_3
    //   457: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   463: aload 9
    //   465: invokestatic 318	com/umeng/fb/util/Log:d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   468: aconst_null
    //   469: areturn
    //   470: aconst_null
    //   471: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   251	340	343	org/apache/http/client/ClientProtocolException
    //   162	175	379	java/io/UnsupportedEncodingException
    //   251	340	434	java/lang/Exception
  }

  public List<DevReply> getDevReply(List<String> paramList, String paramString1, String paramString2)
  {
    Object localObject;
    if ((paramList == null) || (paramList.size() == 0) || (TextUtils.isEmpty(paramString2)))
      localObject = null;
    while (true)
    {
      return localObject;
      StringBuilder localStringBuilder1 = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if (!TextUtils.isEmpty(str2))
        {
          localStringBuilder1.append(str2);
          localStringBuilder1.append(",");
        }
      }
      if (localStringBuilder1.length() > 1)
        localStringBuilder1.replace(-1 + localStringBuilder1.length(), localStringBuilder1.length(), "");
      StringBuilder localStringBuilder2 = new StringBuilder("http://feedback.umeng.com/feedback/reply");
      localStringBuilder2.append("?appkey=" + paramString2);
      localStringBuilder2.append("&feedback_id=" + localStringBuilder1);
      if (!TextUtils.isEmpty(paramString1))
        localStringBuilder2.append("&startkey=" + paramString1);
      Log.d(TAG, "getDevReply url: " + localStringBuilder2);
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpParams localHttpParams = localDefaultHttpClient.getParams();
      HttpConnectionParams.setConnectionTimeout(localHttpParams, 30000);
      HttpConnectionParams.setSoTimeout(localHttpParams, 30000);
      ConnManagerParams.setTimeout(localHttpParams, 30000L);
      HttpGet localHttpGet = new HttpGet(localStringBuilder2.toString());
      try
      {
        HttpResponse localHttpResponse = localDefaultHttpClient.execute((HttpUriRequest)localHttpGet);
        if (localHttpResponse.getStatusLine().getStatusCode() == 200)
        {
          String str1 = EntityUtils.toString(localHttpResponse.getEntity());
          Log.d(TAG, "getDevReply resp: " + str1);
          JSONArray localJSONArray1 = new JSONArray(str1);
          localObject = new ArrayList();
          int i = 0;
          while (true)
          {
            int j = localJSONArray1.length();
            if (i >= j)
              break;
            try
            {
              JSONArray localJSONArray2 = localJSONArray1.getJSONArray(i);
              int k = 0;
              while (true)
              {
                int m = localJSONArray2.length();
                if (k < m)
                  try
                  {
                    ((List)localObject).add(new DevReply(localJSONArray2.getJSONObject(k)));
                    k++;
                  }
                  catch (JSONException localJSONException2)
                  {
                    while (true)
                      localJSONException2.printStackTrace();
                  }
              }
            }
            catch (JSONException localJSONException1)
            {
              localJSONException1.printStackTrace();
              i++;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return null;
  }

  public boolean sendReply(Reply paramReply)
    throws IllegalArgumentException
  {
    if (paramReply == null)
      return true;
    if ((paramReply instanceof UserReply))
      return sendUserReply((UserReply)paramReply);
    if ((paramReply instanceof UserTitleReply))
      return sendUserTitleReply((UserTitleReply)paramReply);
    throw new IllegalArgumentException("Illegal argument: " + paramReply.getClass().getName() + ". reply must be " + UserReply.class.getName() + " or " + UserTitleReply.class.getName() + ".");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.net.FbClient
 * JD-Core Version:    0.6.2
 */