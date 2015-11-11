package com.umeng.analytics;

import java.util.Locale;
import u.aly.ap;

public enum Gender
{
  public int value;

  static
  {
    // Byte code:
    //   0: new 17	com/umeng/analytics/Gender$1
    //   3: dup
    //   4: ldc 18
    //   6: iconst_0
    //   7: iconst_1
    //   8: invokespecial 22	com/umeng/analytics/Gender$1:<init>	(Ljava/lang/String;II)V
    //   11: putstatic 24	com/umeng/analytics/Gender:Male	Lcom/umeng/analytics/Gender;
    //   14: new 26	com/umeng/analytics/Gender$2
    //   17: dup
    //   18: ldc 27
    //   20: iconst_1
    //   21: iconst_2
    //   22: invokespecial 28	com/umeng/analytics/Gender$2:<init>	(Ljava/lang/String;II)V
    //   25: putstatic 30	com/umeng/analytics/Gender:Female	Lcom/umeng/analytics/Gender;
    //   28: new 32	com/umeng/analytics/Gender$3
    //   31: dup
    //   32: ldc 33
    //   34: iconst_2
    //   35: iconst_0
    //   36: invokespecial 34	com/umeng/analytics/Gender$3:<init>	(Ljava/lang/String;II)V
    //   39: putstatic 36	com/umeng/analytics/Gender:Unknown	Lcom/umeng/analytics/Gender;
    //   42: iconst_3
    //   43: anewarray 2	com/umeng/analytics/Gender
    //   46: astore_0
    //   47: aload_0
    //   48: iconst_0
    //   49: getstatic 24	com/umeng/analytics/Gender:Male	Lcom/umeng/analytics/Gender;
    //   52: aastore
    //   53: aload_0
    //   54: iconst_1
    //   55: getstatic 30	com/umeng/analytics/Gender:Female	Lcom/umeng/analytics/Gender;
    //   58: aastore
    //   59: aload_0
    //   60: iconst_2
    //   61: getstatic 36	com/umeng/analytics/Gender:Unknown	Lcom/umeng/analytics/Gender;
    //   64: aastore
    //   65: aload_0
    //   66: putstatic 38	com/umeng/analytics/Gender:a	[Lcom/umeng/analytics/Gender;
    //   69: return
  }

  private Gender(int paramInt)
  {
    this.value = paramInt;
  }

  public static Gender getGender(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return Unknown;
    case 1:
      return Male;
    case 2:
    }
    return Female;
  }

  public static ap transGender(Gender paramGender)
  {
    switch (4.a[paramGender.ordinal()])
    {
    default:
      return ap.c;
    case 1:
      return ap.a;
    case 2:
    }
    return ap.b;
  }

  public int value()
  {
    return this.value;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.Gender
 * JD-Core Version:    0.6.2
 */