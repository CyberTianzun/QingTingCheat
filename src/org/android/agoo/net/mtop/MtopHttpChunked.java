package org.android.agoo.net.mtop;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.message.proguard.aA;
import com.umeng.message.proguard.ag;
import com.umeng.message.proguard.aq;
import com.umeng.message.proguard.az;
import org.android.agoo.helper.PhoneHelper;

public class MtopHttpChunked extends az
{
  private volatile String l;
  private volatile String m;
  private volatile String n;

  private String a(Context paramContext)
  {
    ag localag = new ag(paramContext);
    String str1 = localag.c();
    StringBuffer localStringBuffer = new StringBuffer();
    if (!TextUtils.isEmpty(str1))
      localStringBuffer.append("nt=" + str1);
    String str2 = localag.b();
    if (!TextUtils.isEmpty(str2))
      localStringBuffer.append("&apn=" + str2);
    return localStringBuffer.toString();
  }

  private String a(String paramString, aq paramaq)
  {
    if (paramaq != null)
    {
      String str = paramaq.c();
      paramString = paramString + "?" + str;
    }
    return paramString;
  }

  // ERROR //
  private String b(Context paramContext)
  {
    // Byte code:
    //   0: iconst_m1
    //   1: aload_1
    //   2: invokevirtual 67	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   5: ldc 69
    //   7: aload_1
    //   8: invokevirtual 72	android/content/Context:getPackageName	()Ljava/lang/String;
    //   11: invokevirtual 78	android/content/pm/PackageManager:checkPermission	(Ljava/lang/String;Ljava/lang/String;)I
    //   14: if_icmpne +5 -> 19
    //   17: aconst_null
    //   18: areturn
    //   19: aload_1
    //   20: ldc 80
    //   22: invokevirtual 84	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   25: checkcast 86	android/telephony/TelephonyManager
    //   28: astore_3
    //   29: aload_3
    //   30: invokevirtual 90	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
    //   33: checkcast 92	android/telephony/gsm/GsmCellLocation
    //   36: astore 4
    //   38: aload 4
    //   40: ifnull +151 -> 191
    //   43: aload 4
    //   45: invokevirtual 96	android/telephony/gsm/GsmCellLocation:getLac	()I
    //   48: istore 5
    //   50: aload 4
    //   52: invokevirtual 99	android/telephony/gsm/GsmCellLocation:getCid	()I
    //   55: istore 6
    //   57: iload 5
    //   59: iconst_m1
    //   60: if_icmpgt +9 -> 69
    //   63: iload 6
    //   65: iconst_m1
    //   66: if_icmple +125 -> 191
    //   69: new 34	java/lang/StringBuilder
    //   72: dup
    //   73: invokespecial 35	java/lang/StringBuilder:<init>	()V
    //   76: ldc 101
    //   78: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: iload 5
    //   83: invokevirtual 104	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   86: ldc 106
    //   88: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: iload 6
    //   93: invokevirtual 104	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   96: invokevirtual 44	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   99: astore 7
    //   101: aload 7
    //   103: astore 8
    //   105: aload_3
    //   106: invokevirtual 90	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
    //   109: checkcast 108	android/telephony/cdma/CdmaCellLocation
    //   112: astore 10
    //   114: aload 10
    //   116: ifnull +72 -> 188
    //   119: aload 10
    //   121: invokevirtual 111	android/telephony/cdma/CdmaCellLocation:getNetworkId	()I
    //   124: istore 11
    //   126: aload 10
    //   128: invokevirtual 114	android/telephony/cdma/CdmaCellLocation:getBaseStationId	()I
    //   131: istore 12
    //   133: iload 11
    //   135: iconst_m1
    //   136: if_icmpgt +9 -> 145
    //   139: iload 12
    //   141: iconst_m1
    //   142: if_icmple +46 -> 188
    //   145: new 34	java/lang/StringBuilder
    //   148: dup
    //   149: invokespecial 35	java/lang/StringBuilder:<init>	()V
    //   152: ldc 101
    //   154: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: iload 11
    //   159: invokevirtual 104	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   162: ldc 106
    //   164: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: iload 12
    //   169: invokevirtual 104	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   172: invokevirtual 44	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: astore 13
    //   177: aload 13
    //   179: areturn
    //   180: astore_2
    //   181: aconst_null
    //   182: areturn
    //   183: astore 9
    //   185: aload 8
    //   187: areturn
    //   188: aload 8
    //   190: areturn
    //   191: aconst_null
    //   192: astore 8
    //   194: goto -89 -> 105
    //
    // Exception table:
    //   from	to	target	type
    //   0	17	180	java/lang/Throwable
    //   19	38	180	java/lang/Throwable
    //   43	57	180	java/lang/Throwable
    //   69	101	180	java/lang/Throwable
    //   105	114	183	java/lang/Throwable
    //   119	133	183	java/lang/Throwable
    //   145	177	183	java/lang/Throwable
  }

  public final void connect(Context paramContext, MtopRequest paramMtopRequest, long paramLong, aA paramaA)
  {
    try
    {
      paramMtopRequest.putParams("c0", Build.BRAND);
      paramMtopRequest.putParams("c1", Build.MODEL);
      paramMtopRequest.putParams("c2", PhoneHelper.getOriginalImei(paramContext));
      paramMtopRequest.putParams("c3", PhoneHelper.getOriginalImsi(paramContext));
      paramMtopRequest.putParams("c4", PhoneHelper.getLocalMacAddress(paramContext));
      paramMtopRequest.putParams("c5", PhoneHelper.getSerialNum());
      paramMtopRequest.putParams("c6", PhoneHelper.getAndroidId(paramContext));
      MtopRequestHelper.checkAppKeyAndAppSecret(paramMtopRequest, this.l, this.m);
      aq localaq = MtopRequestHelper.getUrlWithRequestParams(paramContext, paramMtopRequest);
      String str1 = a(this.n, localaq);
      String str2 = a(paramContext);
      if (!TextUtils.isEmpty(str2))
        str1 = str1 + "&" + str2;
      String str3 = b(paramContext);
      if (!TextUtils.isEmpty(str3))
        str1 = str1 + "&" + str3;
      super.connect(paramContext, str1, paramLong, paramaA);
      return;
    }
    catch (Throwable localThrowable)
    {
      while (hasCallError());
      callError(true);
      paramaA.a(504, null, localThrowable);
    }
  }

  public void setBaseUrl(String paramString)
  {
    this.n = paramString;
  }

  public void setDefaultAppSecret(String paramString)
  {
    this.m = paramString;
  }

  public void setDefaultAppkey(String paramString)
  {
    this.l = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.net.mtop.MtopHttpChunked
 * JD-Core Version:    0.6.2
 */