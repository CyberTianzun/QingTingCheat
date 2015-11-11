package com.sina.weibo.sdk;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import java.util.Iterator;
import java.util.List;

public class WeiboAppManager
{
  private static final String SDK_INT_FILE_NAME = "weibo_for_sdk.json";
  private static final String TAG = WeiboAppManager.class.getName();
  private static final String WEIBO_IDENTITY_ACTION = "com.sina.weibo.action.sdkidentity";
  private static final Uri WEIBO_NAME_URI = Uri.parse("content://com.sina.weibo.sdkProvider/query/package");
  private static WeiboAppManager sInstance;
  private Context mContext;

  private WeiboAppManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }

  public static WeiboAppManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new WeiboAppManager(paramContext);
      WeiboAppManager localWeiboAppManager = sInstance;
      return localWeiboAppManager;
    }
    finally
    {
    }
  }

  private WeiboInfo queryWeiboInfoByAsset(Context paramContext)
  {
    Intent localIntent = new Intent("com.sina.weibo.action.sdkidentity");
    localIntent.addCategory("android.intent.category.DEFAULT");
    List localList = paramContext.getPackageManager().queryIntentServices(localIntent, 0);
    Object localObject;
    if ((localList == null) || (localList.isEmpty()))
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = null;
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
        if ((localResolveInfo.serviceInfo != null) && (localResolveInfo.serviceInfo.applicationInfo != null) && (!TextUtils.isEmpty(localResolveInfo.serviceInfo.applicationInfo.packageName)))
        {
          WeiboInfo localWeiboInfo = parseWeiboInfoByAsset(localResolveInfo.serviceInfo.applicationInfo.packageName);
          if (localWeiboInfo != null)
            if (localObject == null)
              localObject = localWeiboInfo;
            else if (((WeiboInfo)localObject).getSupportApi() < localWeiboInfo.getSupportApi())
              localObject = localWeiboInfo;
        }
      }
    }
  }

  private WeiboInfo queryWeiboInfoByProvider(Context paramContext)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Object localObject1 = null;
    while (true)
    {
      try
      {
        Cursor localCursor = localContentResolver.query(WEIBO_NAME_URI, null, null, null, null);
        localObject1 = localCursor;
        WeiboInfo localWeiboInfo;
        if (localObject1 == null)
        {
          if (localObject1 != null)
            localObject1.close();
          localWeiboInfo = null;
          return localWeiboInfo;
        }
        int i = localObject1.getColumnIndex("support_api");
        int j = localObject1.getColumnIndex("package");
        if (localObject1.moveToFirst())
        {
          int k = -1;
          String str1 = localObject1.getString(i);
          try
          {
            int m = Integer.parseInt(str1);
            k = m;
            String str2 = localObject1.getString(j);
            if ((!TextUtils.isEmpty(str2)) && (ApiUtils.validateWeiboSign(paramContext, str2)))
            {
              localWeiboInfo = new WeiboInfo();
              localWeiboInfo.setPackageName(str2);
              localWeiboInfo.setSupportApi(k);
              return localWeiboInfo;
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            localNumberFormatException.printStackTrace();
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        LogUtil.e(TAG, localException.getMessage());
        return null;
      }
      finally
      {
        if (localObject1 != null)
          localObject1.close();
      }
      if (localObject1 != null)
        localObject1.close();
    }
  }

  private WeiboInfo queryWeiboInfoInternal(Context paramContext)
  {
    int i = 1;
    WeiboInfo localWeiboInfo1 = queryWeiboInfoByProvider(paramContext);
    WeiboInfo localWeiboInfo2 = queryWeiboInfoByAsset(paramContext);
    int j;
    if (localWeiboInfo1 != null)
    {
      j = i;
      if (localWeiboInfo2 == null)
        break label56;
      label27: if ((j == 0) || (i == 0))
        break label64;
      if (localWeiboInfo1.getSupportApi() < localWeiboInfo2.getSupportApi())
        break label61;
    }
    label56: label61: label64: 
    while (j != 0)
    {
      return localWeiboInfo1;
      j = 0;
      break;
      i = 0;
      break label27;
      return localWeiboInfo2;
    }
    if (i != 0)
      return localWeiboInfo2;
    return null;
  }

  public WeiboInfo getWeiboInfo()
  {
    try
    {
      WeiboInfo localWeiboInfo = queryWeiboInfoInternal(this.mContext);
      return localWeiboInfo;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  public WeiboInfo parseWeiboInfoByAsset(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +9 -> 13
    //   7: aconst_null
    //   8: astore 20
    //   10: aload 20
    //   12: areturn
    //   13: aconst_null
    //   14: astore_2
    //   15: aload_0
    //   16: getfield 50	com/sina/weibo/sdk/WeiboAppManager:mContext	Landroid/content/Context;
    //   19: aload_1
    //   20: iconst_2
    //   21: invokevirtual 216	android/content/Context:createPackageContext	(Ljava/lang/String;I)Landroid/content/Context;
    //   24: astore 13
    //   26: sipush 4096
    //   29: newarray byte
    //   31: astore 14
    //   33: aload 13
    //   35: invokevirtual 220	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   38: ldc 8
    //   40: invokevirtual 226	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   43: astore_2
    //   44: new 228	java/lang/StringBuilder
    //   47: dup
    //   48: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   51: astore 15
    //   53: aload_2
    //   54: aload 14
    //   56: iconst_0
    //   57: sipush 4096
    //   60: invokevirtual 235	java/io/InputStream:read	([BII)I
    //   63: istore 16
    //   65: iload 16
    //   67: iconst_m1
    //   68: if_icmpne +39 -> 107
    //   71: aload 15
    //   73: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   76: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   79: ifne +18 -> 97
    //   82: aload_0
    //   83: getfield 50	com/sina/weibo/sdk/WeiboAppManager:mContext	Landroid/content/Context;
    //   86: aload_1
    //   87: invokestatic 176	com/sina/weibo/sdk/ApiUtils:validateWeiboSign	(Landroid/content/Context;Ljava/lang/String;)Z
    //   90: istore 18
    //   92: iload 18
    //   94: ifne +73 -> 167
    //   97: aload_2
    //   98: ifnull +7 -> 105
    //   101: aload_2
    //   102: invokevirtual 239	java/io/InputStream:close	()V
    //   105: aconst_null
    //   106: areturn
    //   107: aload 15
    //   109: new 241	java/lang/String
    //   112: dup
    //   113: aload 14
    //   115: iconst_0
    //   116: iload 16
    //   118: invokespecial 244	java/lang/String:<init>	([BII)V
    //   121: invokevirtual 248	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: goto -72 -> 53
    //   128: astore 11
    //   130: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   133: aload 11
    //   135: invokevirtual 249	android/content/pm/PackageManager$NameNotFoundException:getMessage	()Ljava/lang/String;
    //   138: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   141: aload_2
    //   142: ifnull +7 -> 149
    //   145: aload_2
    //   146: invokevirtual 239	java/io/InputStream:close	()V
    //   149: aconst_null
    //   150: areturn
    //   151: astore 17
    //   153: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   156: aload 17
    //   158: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   161: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   164: goto -59 -> 105
    //   167: new 252	org/json/JSONObject
    //   170: dup
    //   171: aload 15
    //   173: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   176: invokespecial 253	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   179: ldc 152
    //   181: iconst_m1
    //   182: invokevirtual 257	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   185: istore 19
    //   187: new 126	com/sina/weibo/sdk/WeiboAppManager$WeiboInfo
    //   190: dup
    //   191: invokespecial 177	com/sina/weibo/sdk/WeiboAppManager$WeiboInfo:<init>	()V
    //   194: astore 20
    //   196: aload 20
    //   198: aload_1
    //   199: invokestatic 181	com/sina/weibo/sdk/WeiboAppManager$WeiboInfo:access$0	(Lcom/sina/weibo/sdk/WeiboAppManager$WeiboInfo;Ljava/lang/String;)V
    //   202: aload 20
    //   204: iload 19
    //   206: invokestatic 185	com/sina/weibo/sdk/WeiboAppManager$WeiboInfo:access$1	(Lcom/sina/weibo/sdk/WeiboAppManager$WeiboInfo;I)V
    //   209: aload_2
    //   210: ifnull -200 -> 10
    //   213: aload_2
    //   214: invokevirtual 239	java/io/InputStream:close	()V
    //   217: aload 20
    //   219: areturn
    //   220: astore 21
    //   222: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   225: aload 21
    //   227: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   230: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   233: aload 20
    //   235: areturn
    //   236: astore 12
    //   238: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   241: aload 12
    //   243: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   246: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   249: goto -100 -> 149
    //   252: astore 9
    //   254: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   257: aload 9
    //   259: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   262: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   265: aload_2
    //   266: ifnull -117 -> 149
    //   269: aload_2
    //   270: invokevirtual 239	java/io/InputStream:close	()V
    //   273: goto -124 -> 149
    //   276: astore 10
    //   278: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   281: aload 10
    //   283: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   286: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   289: goto -140 -> 149
    //   292: astore 7
    //   294: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   297: aload 7
    //   299: invokevirtual 258	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   302: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   305: aload_2
    //   306: ifnull -157 -> 149
    //   309: aload_2
    //   310: invokevirtual 239	java/io/InputStream:close	()V
    //   313: goto -164 -> 149
    //   316: astore 8
    //   318: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   321: aload 8
    //   323: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   326: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   329: goto -180 -> 149
    //   332: astore 5
    //   334: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   337: aload 5
    //   339: invokevirtual 191	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   342: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   345: aload_2
    //   346: ifnull -197 -> 149
    //   349: aload_2
    //   350: invokevirtual 239	java/io/InputStream:close	()V
    //   353: goto -204 -> 149
    //   356: astore 6
    //   358: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   361: aload 6
    //   363: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   366: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   369: goto -220 -> 149
    //   372: astore_3
    //   373: aload_2
    //   374: ifnull +7 -> 381
    //   377: aload_2
    //   378: invokevirtual 239	java/io/InputStream:close	()V
    //   381: aload_3
    //   382: athrow
    //   383: astore 4
    //   385: getstatic 28	com/sina/weibo/sdk/WeiboAppManager:TAG	Ljava/lang/String;
    //   388: aload 4
    //   390: invokevirtual 250	java/io/IOException:getMessage	()Ljava/lang/String;
    //   393: invokestatic 197	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   396: goto -15 -> 381
    //
    // Exception table:
    //   from	to	target	type
    //   15	53	128	android/content/pm/PackageManager$NameNotFoundException
    //   53	65	128	android/content/pm/PackageManager$NameNotFoundException
    //   71	92	128	android/content/pm/PackageManager$NameNotFoundException
    //   107	125	128	android/content/pm/PackageManager$NameNotFoundException
    //   167	209	128	android/content/pm/PackageManager$NameNotFoundException
    //   101	105	151	java/io/IOException
    //   213	217	220	java/io/IOException
    //   145	149	236	java/io/IOException
    //   15	53	252	java/io/IOException
    //   53	65	252	java/io/IOException
    //   71	92	252	java/io/IOException
    //   107	125	252	java/io/IOException
    //   167	209	252	java/io/IOException
    //   269	273	276	java/io/IOException
    //   15	53	292	org/json/JSONException
    //   53	65	292	org/json/JSONException
    //   71	92	292	org/json/JSONException
    //   107	125	292	org/json/JSONException
    //   167	209	292	org/json/JSONException
    //   309	313	316	java/io/IOException
    //   15	53	332	java/lang/Exception
    //   53	65	332	java/lang/Exception
    //   71	92	332	java/lang/Exception
    //   107	125	332	java/lang/Exception
    //   167	209	332	java/lang/Exception
    //   349	353	356	java/io/IOException
    //   15	53	372	finally
    //   53	65	372	finally
    //   71	92	372	finally
    //   107	125	372	finally
    //   130	141	372	finally
    //   167	209	372	finally
    //   254	265	372	finally
    //   294	305	372	finally
    //   334	345	372	finally
    //   377	381	383	java/io/IOException
  }

  public static class WeiboInfo
  {
    private String mPackageName;
    private int mSupportApi;

    private void setPackageName(String paramString)
    {
      this.mPackageName = paramString;
    }

    private void setSupportApi(int paramInt)
    {
      this.mSupportApi = paramInt;
    }

    public String getPackageName()
    {
      return this.mPackageName;
    }

    public int getSupportApi()
    {
      return this.mSupportApi;
    }

    public boolean isLegal()
    {
      return (!TextUtils.isEmpty(this.mPackageName)) && (this.mSupportApi > 0);
    }

    public String toString()
    {
      return "WeiboInfo: PackageName = " + this.mPackageName + ", supportApi = " + this.mSupportApi;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.WeiboAppManager
 * JD-Core Version:    0.6.2
 */