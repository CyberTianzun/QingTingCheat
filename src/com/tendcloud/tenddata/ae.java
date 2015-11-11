package com.tendcloud.tenddata;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ae
{
  private static long a(String paramString)
  {
    long l = 1125899906842597L;
    int i = paramString.length();
    for (int j = 0; j < i; j++)
      l = l * 131L + paramString.charAt(j);
    return l;
  }

  public static List a(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      if ((x.a(paramContext, "android.permission.ACCESS_FINE_LOCATION")) || (x.a(paramContext, "android.permission.ACCESS_COARSE_LOCATION")))
      {
        LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
        Iterator localIterator = localLocationManager.getProviders(true).iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          Location localLocation = localLocationManager.getLastKnownLocation(str);
          if (localLocation != null)
            localArrayList.add(localLocation);
          try
          {
            PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0);
            localLocationManager.requestLocationUpdates(str, 300000L, 0.0F, localPendingIntent);
            localLocationManager.removeUpdates(localPendingIntent);
          }
          catch (Exception localException2)
          {
          }
        }
      }
    }
    catch (Exception localException1)
    {
    }
    return localArrayList;
  }

  public static String b(Context paramContext)
  {
    List localList = a(paramContext);
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Location localLocation = (Location)localIterator.next();
      localStringBuffer.append(localLocation.getLatitude()).append(',').append(localLocation.getLongitude()).append(',').append(localLocation.getAltitude()).append(',').append(localLocation.getTime()).append(',').append(localLocation.getAccuracy()).append(',').append(localLocation.getBearing()).append(',').append(localLocation.getSpeed()).append(',').append((short)localLocation.getProvider().hashCode()).append(':');
    }
    return localStringBuffer.toString();
  }

  public static Long[][] c(Context paramContext)
  {
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    PackageManager localPackageManager = paramContext.getPackageManager();
    HashSet localHashSet1 = new HashSet();
    localHashSet1.add(paramContext.getPackageName());
    HashSet localHashSet2 = new HashSet();
    if (x.a(paramContext, "android.permission.GET_TASKS"))
    {
      List localList3 = localActivityManager.getRecentTasks(10, 1);
      if (localList3 != null)
      {
        Iterator localIterator3 = localList3.iterator();
        while (localIterator3.hasNext())
        {
          ComponentName localComponentName = ((ActivityManager.RecentTaskInfo)localIterator3.next()).baseIntent.getComponent();
          if (localComponentName != null)
          {
            String str2 = localComponentName.getPackageName();
            if (localHashSet1.add(str2))
              localHashSet2.add(Long.valueOf(a(str2)));
          }
        }
      }
    }
    List localList1 = localActivityManager.getRunningAppProcesses();
    HashSet localHashSet3 = new HashSet();
    if (localList1 != null)
    {
      Iterator localIterator2 = localList1.iterator();
      while (localIterator2.hasNext())
      {
        String str1 = ((ActivityManager.RunningAppProcessInfo)localIterator2.next()).processName;
        if (localPackageManager.getLaunchIntentForPackage(str1) != null)
          localHashSet3.add(Long.valueOf(a(str1)));
      }
    }
    List localList2 = localPackageManager.getInstalledApplications(0);
    HashSet localHashSet4 = new HashSet();
    if (localList2 != null)
    {
      Iterator localIterator1 = localList2.iterator();
      while (localIterator1.hasNext())
      {
        ApplicationInfo localApplicationInfo = (ApplicationInfo)localIterator1.next();
        if (((0x1 & localApplicationInfo.flags) <= 0) && (!localHashSet1.contains(localApplicationInfo.packageName)))
          localHashSet4.add(Long.valueOf(a(localApplicationInfo.packageName)));
      }
    }
    Long[][] arrayOfLong; = new Long[3][];
    arrayOfLong;[0] = new Long[localHashSet2.size()];
    arrayOfLong;[0] = ((Long[])localHashSet2.toArray(arrayOfLong;[0]));
    arrayOfLong;[1] = new Long[localHashSet3.size()];
    arrayOfLong;[1] = ((Long[])localHashSet3.toArray(arrayOfLong;[1]));
    arrayOfLong;[2] = new Long[localHashSet4.size()];
    arrayOfLong;[2] = ((Long[])localHashSet4.toArray(arrayOfLong;[2]));
    return arrayOfLong;;
  }

  public static List d(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    ArrayList localArrayList = new ArrayList();
    List localList = localPackageManager.getInstalledApplications(0);
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ApplicationInfo localApplicationInfo = (ApplicationInfo)localIterator.next();
        localArrayList.add(localApplicationInfo.packageName);
        localArrayList.add(x.b(localPackageManager.getApplicationLabel(localApplicationInfo).toString().getBytes()));
        if ((0x1 & localApplicationInfo.flags) > 0)
          localArrayList.add("1");
        else
          localArrayList.add("0");
      }
    }
    return localArrayList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.ae
 * JD-Core Version:    0.6.2
 */