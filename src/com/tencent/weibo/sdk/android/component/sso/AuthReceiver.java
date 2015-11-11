package com.tencent.weibo.sdk.android.component.sso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tencent.weibo.sdk.android.component.sso.tools.Cryptor;

public class AuthReceiver extends BroadcastReceiver
{
  static final String ACTION = "com.tencent.sso.AUTH";

  // ERROR //
  private WeiboToken revert(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 20	com/tencent/weibo/sdk/android/component/sso/WeiboToken
    //   3: dup
    //   4: invokespecial 21	com/tencent/weibo/sdk/android/component/sso/WeiboToken:<init>	()V
    //   7: astore_2
    //   8: new 23	java/io/ByteArrayInputStream
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 26	java/io/ByteArrayInputStream:<init>	([B)V
    //   16: astore_3
    //   17: new 28	java/io/DataInputStream
    //   20: dup
    //   21: aload_3
    //   22: invokespecial 31	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   25: astore 4
    //   27: aload_2
    //   28: aload 4
    //   30: invokevirtual 35	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   33: putfield 38	com/tencent/weibo/sdk/android/component/sso/WeiboToken:accessToken	Ljava/lang/String;
    //   36: aload_2
    //   37: aload 4
    //   39: invokevirtual 42	java/io/DataInputStream:readLong	()J
    //   42: putfield 46	com/tencent/weibo/sdk/android/component/sso/WeiboToken:expiresIn	J
    //   45: aload_2
    //   46: aload 4
    //   48: invokevirtual 35	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   51: putfield 49	com/tencent/weibo/sdk/android/component/sso/WeiboToken:refreshToken	Ljava/lang/String;
    //   54: aload_2
    //   55: aload 4
    //   57: invokevirtual 35	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   60: putfield 52	com/tencent/weibo/sdk/android/component/sso/WeiboToken:openID	Ljava/lang/String;
    //   63: aload_2
    //   64: aload 4
    //   66: invokevirtual 35	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   69: putfield 55	com/tencent/weibo/sdk/android/component/sso/WeiboToken:omasToken	Ljava/lang/String;
    //   72: aload_2
    //   73: aload 4
    //   75: invokevirtual 35	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   78: putfield 58	com/tencent/weibo/sdk/android/component/sso/WeiboToken:omasKey	Ljava/lang/String;
    //   81: aload_3
    //   82: ifnull +7 -> 89
    //   85: aload_3
    //   86: invokevirtual 61	java/io/ByteArrayInputStream:close	()V
    //   89: aload 4
    //   91: ifnull +8 -> 99
    //   94: aload 4
    //   96: invokevirtual 62	java/io/DataInputStream:close	()V
    //   99: aload_2
    //   100: areturn
    //   101: astore 8
    //   103: aload 8
    //   105: invokevirtual 65	java/lang/Exception:printStackTrace	()V
    //   108: aload_3
    //   109: ifnull +7 -> 116
    //   112: aload_3
    //   113: invokevirtual 61	java/io/ByteArrayInputStream:close	()V
    //   116: aload 4
    //   118: ifnull +8 -> 126
    //   121: aload 4
    //   123: invokevirtual 62	java/io/DataInputStream:close	()V
    //   126: aconst_null
    //   127: areturn
    //   128: astore 5
    //   130: aload_3
    //   131: ifnull +7 -> 138
    //   134: aload_3
    //   135: invokevirtual 61	java/io/ByteArrayInputStream:close	()V
    //   138: aload 4
    //   140: ifnull +8 -> 148
    //   143: aload 4
    //   145: invokevirtual 62	java/io/DataInputStream:close	()V
    //   148: aload 5
    //   150: athrow
    //   151: astore 12
    //   153: goto -64 -> 89
    //   156: astore 11
    //   158: aload_2
    //   159: areturn
    //   160: astore 10
    //   162: goto -46 -> 116
    //   165: astore 9
    //   167: goto -41 -> 126
    //   170: astore 7
    //   172: goto -34 -> 138
    //   175: astore 6
    //   177: goto -29 -> 148
    //
    // Exception table:
    //   from	to	target	type
    //   27	81	101	java/lang/Exception
    //   27	81	128	finally
    //   103	108	128	finally
    //   85	89	151	java/io/IOException
    //   94	99	156	java/io/IOException
    //   112	116	160	java/io/IOException
    //   121	126	165	java/io/IOException
    //   134	138	170	java/io/IOException
    //   143	148	175	java/io/IOException
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent.getAction().equals("com.tencent.sso.AUTH")) && (paramIntent.getStringExtra("com.tencent.sso.PACKAGE_NAME").equals(paramContext.getPackageName())))
    {
      if (!paramIntent.getBooleanExtra("com.tencent.sso.AUTH_RESULT", false))
        break label98;
      String str2 = paramIntent.getStringExtra("com.tencent.sso.WEIBO_NICK");
      byte[] arrayOfByte = paramIntent.getByteArrayExtra("com.tencent.sso.ACCESS_TOKEN");
      WeiboToken localWeiboToken = revert(new Cryptor().decrypt(arrayOfByte, "&-*)Wb5_U,[^!9'+".getBytes(), 10));
      if (AuthHelper.listener != null)
        AuthHelper.listener.onAuthPassed(str2, localWeiboToken);
    }
    label98: int i;
    String str1;
    do
    {
      return;
      i = paramIntent.getIntExtra("com.tencent.sso.RESULT", 1);
      str1 = paramIntent.getStringExtra("com.tencent.sso.WEIBO_NICK");
    }
    while (AuthHelper.listener == null);
    AuthHelper.listener.onAuthFail(i, str1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.sso.AuthReceiver
 * JD-Core Version:    0.6.2
 */