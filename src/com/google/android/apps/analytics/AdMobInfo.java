package com.google.android.apps.analytics;

import java.util.Random;

public class AdMobInfo
{
  private static final AdMobInfo INSTANCE = new AdMobInfo();
  private int adHitId;
  private Random random = new Random();

  public static AdMobInfo getInstance()
  {
    return INSTANCE;
  }

  public int generateAdHitId()
  {
    this.adHitId = this.random.nextInt();
    return this.adHitId;
  }

  public int getAdHitId()
  {
    return this.adHitId;
  }

  public String getJoinId()
  {
    if (this.adHitId == 0);
    String str1;
    String str2;
    do
    {
      return null;
      GoogleAnalyticsTracker localGoogleAnalyticsTracker = GoogleAnalyticsTracker.getInstance();
      str1 = localGoogleAnalyticsTracker.getVisitorIdForAds();
      str2 = localGoogleAnalyticsTracker.getSessionIdForAds();
    }
    while ((str1 == null) || (str2 == null));
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = str1;
    arrayOfObject[1] = str2;
    arrayOfObject[2] = Integer.valueOf(this.adHitId);
    return String.format("A,%s,%s,%d", arrayOfObject);
  }

  public void setAdHitId(int paramInt)
  {
    this.adHitId = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.analytics.AdMobInfo
 * JD-Core Version:    0.6.2
 */