package com.umeng.analytics.social;

import android.text.TextUtils;
import java.util.Locale;

public class UMPlatformData
{
  private UMedia a;
  private String b = "";
  private String c = "";
  private String d;
  private GENDER e;

  public UMPlatformData(UMedia paramUMedia, String paramString)
  {
    if ((paramUMedia == null) || (TextUtils.isEmpty(paramString)))
    {
      b.b("MobclickAgent", "parameter is not valid");
      return;
    }
    this.a = paramUMedia;
    this.b = paramString;
  }

  public GENDER getGender()
  {
    return this.e;
  }

  public UMedia getMeida()
  {
    return this.a;
  }

  public String getName()
  {
    return this.d;
  }

  public String getUsid()
  {
    return this.b;
  }

  public String getWeiboId()
  {
    return this.c;
  }

  public boolean isValid()
  {
    return (this.a != null) && (!TextUtils.isEmpty(this.b));
  }

  public void setGender(GENDER paramGENDER)
  {
    this.e = paramGENDER;
  }

  public void setName(String paramString)
  {
    this.d = paramString;
  }

  public void setWeiboId(String paramString)
  {
    this.c = paramString;
  }

  public String toString()
  {
    return "UMPlatformData [meida=" + this.a + ", usid=" + this.b + ", weiboId=" + this.c + ", name=" + this.d + ", gender=" + this.e + "]";
  }

  public static enum GENDER
  {
    public int value;

    static
    {
      // Byte code:
      //   0: new 16	com/umeng/analytics/social/UMPlatformData$GENDER$1
      //   3: dup
      //   4: ldc 17
      //   6: iconst_0
      //   7: iconst_0
      //   8: invokespecial 21	com/umeng/analytics/social/UMPlatformData$GENDER$1:<init>	(Ljava/lang/String;II)V
      //   11: putstatic 23	com/umeng/analytics/social/UMPlatformData$GENDER:MALE	Lcom/umeng/analytics/social/UMPlatformData$GENDER;
      //   14: new 25	com/umeng/analytics/social/UMPlatformData$GENDER$2
      //   17: dup
      //   18: ldc 26
      //   20: iconst_1
      //   21: iconst_1
      //   22: invokespecial 27	com/umeng/analytics/social/UMPlatformData$GENDER$2:<init>	(Ljava/lang/String;II)V
      //   25: putstatic 29	com/umeng/analytics/social/UMPlatformData$GENDER:FEMALE	Lcom/umeng/analytics/social/UMPlatformData$GENDER;
      //   28: iconst_2
      //   29: anewarray 2	com/umeng/analytics/social/UMPlatformData$GENDER
      //   32: astore_0
      //   33: aload_0
      //   34: iconst_0
      //   35: getstatic 23	com/umeng/analytics/social/UMPlatformData$GENDER:MALE	Lcom/umeng/analytics/social/UMPlatformData$GENDER;
      //   38: aastore
      //   39: aload_0
      //   40: iconst_1
      //   41: getstatic 29	com/umeng/analytics/social/UMPlatformData$GENDER:FEMALE	Lcom/umeng/analytics/social/UMPlatformData$GENDER;
      //   44: aastore
      //   45: aload_0
      //   46: putstatic 31	com/umeng/analytics/social/UMPlatformData$GENDER:a	[Lcom/umeng/analytics/social/UMPlatformData$GENDER;
      //   49: return
    }

    private GENDER(int paramInt)
    {
      this.value = paramInt;
    }
  }

  public static enum UMedia
  {
    static
    {
      // Byte code:
      //   0: new 20	com/umeng/analytics/social/UMPlatformData$UMedia$1
      //   3: dup
      //   4: ldc 21
      //   6: iconst_0
      //   7: invokespecial 25	com/umeng/analytics/social/UMPlatformData$UMedia$1:<init>	(Ljava/lang/String;I)V
      //   10: putstatic 27	com/umeng/analytics/social/UMPlatformData$UMedia:SINA_WEIBO	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   13: new 29	com/umeng/analytics/social/UMPlatformData$UMedia$2
      //   16: dup
      //   17: ldc 30
      //   19: iconst_1
      //   20: invokespecial 31	com/umeng/analytics/social/UMPlatformData$UMedia$2:<init>	(Ljava/lang/String;I)V
      //   23: putstatic 33	com/umeng/analytics/social/UMPlatformData$UMedia:TENCENT_WEIBO	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   26: new 35	com/umeng/analytics/social/UMPlatformData$UMedia$3
      //   29: dup
      //   30: ldc 36
      //   32: iconst_2
      //   33: invokespecial 37	com/umeng/analytics/social/UMPlatformData$UMedia$3:<init>	(Ljava/lang/String;I)V
      //   36: putstatic 39	com/umeng/analytics/social/UMPlatformData$UMedia:TENCENT_QZONE	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   39: new 41	com/umeng/analytics/social/UMPlatformData$UMedia$4
      //   42: dup
      //   43: ldc 42
      //   45: iconst_3
      //   46: invokespecial 43	com/umeng/analytics/social/UMPlatformData$UMedia$4:<init>	(Ljava/lang/String;I)V
      //   49: putstatic 45	com/umeng/analytics/social/UMPlatformData$UMedia:TENCENT_QQ	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   52: new 47	com/umeng/analytics/social/UMPlatformData$UMedia$5
      //   55: dup
      //   56: ldc 48
      //   58: iconst_4
      //   59: invokespecial 49	com/umeng/analytics/social/UMPlatformData$UMedia$5:<init>	(Ljava/lang/String;I)V
      //   62: putstatic 51	com/umeng/analytics/social/UMPlatformData$UMedia:WEIXIN_FRIENDS	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   65: new 53	com/umeng/analytics/social/UMPlatformData$UMedia$6
      //   68: dup
      //   69: ldc 54
      //   71: iconst_5
      //   72: invokespecial 55	com/umeng/analytics/social/UMPlatformData$UMedia$6:<init>	(Ljava/lang/String;I)V
      //   75: putstatic 57	com/umeng/analytics/social/UMPlatformData$UMedia:WEIXIN_CIRCLE	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   78: new 59	com/umeng/analytics/social/UMPlatformData$UMedia$7
      //   81: dup
      //   82: ldc 60
      //   84: bipush 6
      //   86: invokespecial 61	com/umeng/analytics/social/UMPlatformData$UMedia$7:<init>	(Ljava/lang/String;I)V
      //   89: putstatic 63	com/umeng/analytics/social/UMPlatformData$UMedia:RENREN	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   92: new 65	com/umeng/analytics/social/UMPlatformData$UMedia$8
      //   95: dup
      //   96: ldc 66
      //   98: bipush 7
      //   100: invokespecial 67	com/umeng/analytics/social/UMPlatformData$UMedia$8:<init>	(Ljava/lang/String;I)V
      //   103: putstatic 69	com/umeng/analytics/social/UMPlatformData$UMedia:DOUBAN	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   106: bipush 8
      //   108: anewarray 2	com/umeng/analytics/social/UMPlatformData$UMedia
      //   111: astore_0
      //   112: aload_0
      //   113: iconst_0
      //   114: getstatic 27	com/umeng/analytics/social/UMPlatformData$UMedia:SINA_WEIBO	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   117: aastore
      //   118: aload_0
      //   119: iconst_1
      //   120: getstatic 33	com/umeng/analytics/social/UMPlatformData$UMedia:TENCENT_WEIBO	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   123: aastore
      //   124: aload_0
      //   125: iconst_2
      //   126: getstatic 39	com/umeng/analytics/social/UMPlatformData$UMedia:TENCENT_QZONE	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   129: aastore
      //   130: aload_0
      //   131: iconst_3
      //   132: getstatic 45	com/umeng/analytics/social/UMPlatformData$UMedia:TENCENT_QQ	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   135: aastore
      //   136: aload_0
      //   137: iconst_4
      //   138: getstatic 51	com/umeng/analytics/social/UMPlatformData$UMedia:WEIXIN_FRIENDS	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   141: aastore
      //   142: aload_0
      //   143: iconst_5
      //   144: getstatic 57	com/umeng/analytics/social/UMPlatformData$UMedia:WEIXIN_CIRCLE	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   147: aastore
      //   148: aload_0
      //   149: bipush 6
      //   151: getstatic 63	com/umeng/analytics/social/UMPlatformData$UMedia:RENREN	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   154: aastore
      //   155: aload_0
      //   156: bipush 7
      //   158: getstatic 69	com/umeng/analytics/social/UMPlatformData$UMedia:DOUBAN	Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   161: aastore
      //   162: aload_0
      //   163: putstatic 71	com/umeng/analytics/social/UMPlatformData$UMedia:a	[Lcom/umeng/analytics/social/UMPlatformData$UMedia;
      //   166: return
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.social.UMPlatformData
 * JD-Core Version:    0.6.2
 */