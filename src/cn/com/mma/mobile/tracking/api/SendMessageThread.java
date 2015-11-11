package cn.com.mma.mobile.tracking.api;

import android.content.Context;
import android.content.SharedPreferences;
import cn.com.mma.mobile.tracking.util.DeviceInfoUtil;
import cn.com.mma.mobile.tracking.util.Logger;
import cn.com.mma.mobile.tracking.util.SharedPreferencedUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

public class SendMessageThread extends Thread
{
  private Context context;
  private boolean isInterruptThread = false;
  private boolean isNormalList;
  private String spName;
  public int totalSize = 0;

  SendMessageThread(String paramString, Context paramContext, boolean paramBoolean)
  {
    this.spName = paramString;
    this.context = paramContext;
    this.isNormalList = paramBoolean;
  }

  private void handleFailedResult(String paramString, long paramLong)
  {
    if (this.isNormalList)
    {
      SharedPreferencedUtil.removeFromSharedPreferences(this.context, "cn.com.mma.mobile.tracking.normal", paramString);
      SharedPreferencedUtil.putLong(this.context, "cn.com.mma.mobile.tracking.falied", paramString, paramLong);
      SharedPreferencedUtil.putLong(this.context, "cn.com.mma.mobile.tracking.other", paramString, 1L);
      return;
    }
    long l = 1L + SharedPreferencedUtil.getLong(this.context, "cn.com.mma.mobile.tracking.other", paramString);
    if (l > 3L)
    {
      SharedPreferencedUtil.removeFromSharedPreferences(this.context, "cn.com.mma.mobile.tracking.falied", paramString);
      boolean bool = SharedPreferencedUtil.removeFromSharedPreferences(this.context, "cn.com.mma.mobile.tracking.other", paramString);
      Logger.d("mma_failed发送失败超过三次，删除other中记录" + bool);
      return;
    }
    SharedPreferencedUtil.putLong(this.context, "cn.com.mma.mobile.tracking.other", paramString, l);
  }

  private void handleSuccessResult(String paramString1, String paramString2)
  {
    SharedPreferencedUtil.removeFromSharedPreferences(this.context, paramString1, paramString2);
    if (!this.isNormalList)
    {
      boolean bool = SharedPreferencedUtil.removeFromSharedPreferences(this.context, "cn.com.mma.mobile.tracking.other", paramString2);
      Logger.d("mma_failed数据发送成功，删除other中记录" + bool);
    }
  }

  private void sendData()
  {
    Iterator localIterator = SharedPreferencedUtil.getSharedPreferences(this.context, this.spName).getAll().keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext());
      String str;
      do
      {
        return;
        str = (String)localIterator.next();
      }
      while ((this.isInterruptThread) || (!DeviceInfoUtil.isNetworkAvailable(this.context)));
      Long localLong;
      HttpResponse localHttpResponse;
      try
      {
        localLong = Long.valueOf(SharedPreferencedUtil.getLong(this.context, this.spName, str));
        if ((str == null) || ("".equals(str)))
          continue;
        if (localLong.longValue() <= System.currentTimeMillis())
          break label253;
        localHttpResponse = sendMessage(str);
        Logger.d("isNormal:" + this.isNormalList + " mma_request_sendUrl:" + str);
        if (localHttpResponse != null)
          break label170;
        handleFailedResult(str, localLong.longValue());
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      continue;
      label170: int i = localHttpResponse.getStatusLine().getStatusCode();
      Logger.d("mma_result_code:" + i);
      if ((i == 200) || (i == 301) || (i == 302))
      {
        handleSuccessResult(this.spName, str);
      }
      else
      {
        handleFailedResult(str, localLong.longValue());
        continue;
        label253: SharedPreferencedUtil.removeFromSharedPreferences(this.context, this.spName, str);
      }
    }
  }

  // ERROR //
  private HttpResponse sendMessage(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 177	org/apache/http/impl/client/DefaultHttpClient
    //   5: dup
    //   6: invokespecial 178	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   9: astore_3
    //   10: aload_3
    //   11: invokevirtual 182	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   14: ldc 184
    //   16: sipush 1000
    //   19: getstatic 189	cn/com/mma/mobile/tracking/api/Global:OFFLINECACHE_TIMEOUT	I
    //   22: imul
    //   23: invokestatic 194	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   26: invokeinterface 200 3 0
    //   31: pop
    //   32: aload_3
    //   33: invokevirtual 182	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   36: ldc 202
    //   38: sipush 1000
    //   41: getstatic 189	cn/com/mma/mobile/tracking/api/Global:OFFLINECACHE_TIMEOUT	I
    //   44: imul
    //   45: invokestatic 194	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   48: invokeinterface 200 3 0
    //   53: pop
    //   54: new 204	org/apache/http/client/methods/HttpGet
    //   57: dup
    //   58: new 206	java/net/URI
    //   61: dup
    //   62: aload_1
    //   63: invokespecial 207	java/net/URI:<init>	(Ljava/lang/String;)V
    //   66: invokespecial 210	org/apache/http/client/methods/HttpGet:<init>	(Ljava/net/URI;)V
    //   69: astore 10
    //   71: aload_3
    //   72: aload 10
    //   74: invokevirtual 214	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   77: astore 11
    //   79: aload 11
    //   81: astore 6
    //   83: aload_3
    //   84: ifnull +19 -> 103
    //   87: aload_3
    //   88: invokevirtual 218	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   91: ifnull +12 -> 103
    //   94: aload_3
    //   95: invokevirtual 218	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   98: invokeinterface 223 1 0
    //   103: aload 6
    //   105: areturn
    //   106: astore 4
    //   108: aload 4
    //   110: invokevirtual 156	java/lang/Exception:printStackTrace	()V
    //   113: ldc 225
    //   115: invokestatic 72	cn/com/mma/mobile/tracking/util/Logger:d	(Ljava/lang/String;)V
    //   118: aconst_null
    //   119: astore 6
    //   121: aload_2
    //   122: ifnull -19 -> 103
    //   125: aload_2
    //   126: invokevirtual 218	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   129: astore 7
    //   131: aconst_null
    //   132: astore 6
    //   134: aload 7
    //   136: ifnull -33 -> 103
    //   139: aload_2
    //   140: invokevirtual 218	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   143: invokeinterface 223 1 0
    //   148: aconst_null
    //   149: areturn
    //   150: astore 5
    //   152: aload_2
    //   153: ifnull +19 -> 172
    //   156: aload_2
    //   157: invokevirtual 218	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   160: ifnull +12 -> 172
    //   163: aload_2
    //   164: invokevirtual 218	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   167: invokeinterface 223 1 0
    //   172: aload 5
    //   174: athrow
    //   175: astore 5
    //   177: aload_3
    //   178: astore_2
    //   179: goto -27 -> 152
    //   182: astore 5
    //   184: aload_3
    //   185: astore_2
    //   186: goto -34 -> 152
    //   189: astore 4
    //   191: aload_3
    //   192: astore_2
    //   193: goto -85 -> 108
    //   196: astore 4
    //   198: aload_3
    //   199: astore_2
    //   200: goto -92 -> 108
    //
    // Exception table:
    //   from	to	target	type
    //   2	10	106	java/lang/Exception
    //   2	10	150	finally
    //   108	118	150	finally
    //   10	71	175	finally
    //   71	79	182	finally
    //   10	71	189	java/lang/Exception
    //   71	79	196	java/lang/Exception
  }

  public void interrupt()
  {
    this.isInterruptThread = true;
    super.interrupt();
  }

  public void run()
  {
    sendData();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.api.SendMessageThread
 * JD-Core Version:    0.6.2
 */