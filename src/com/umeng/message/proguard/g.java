package com.umeng.message.proguard;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

public class g
{
  public static File a(String paramString)
  {
    File localFile1 = Environment.getExternalStorageDirectory();
    if (localFile1 != null)
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = localFile1.getAbsolutePath();
      arrayOfObject[1] = File.separator;
      arrayOfObject[2] = paramString;
      File localFile2 = new File(String.format("%s%s%s", arrayOfObject));
      if ((localFile2 != null) && (!localFile2.exists()))
        localFile2.mkdirs();
      return localFile2;
    }
    return null;
  }

  public static String a()
  {
    try
    {
      FileReader localFileReader = new FileReader("/proc/cpuinfo");
      Object localObject1 = null;
      if (localFileReader != null);
      while (true)
      {
        try
        {
          localBufferedReader = new BufferedReader(localFileReader, 1024);
          String str = localBufferedReader.readLine();
          localObject1 = str;
        }
        catch (IOException localIOException)
        {
          try
          {
            BufferedReader localBufferedReader;
            localBufferedReader.close();
            localFileReader.close();
            if (localObject1 == null)
              break;
            return localObject1.substring(1 + localObject1.indexOf(':')).trim();
            localIOException = localIOException;
            Log.e("Could not read from file /proc/cpuinfo", localIOException.toString());
            continue;
          }
          catch (FileNotFoundException localFileNotFoundException2)
          {
            localObject3 = localObject1;
            localObject2 = localFileNotFoundException2;
          }
        }
        Log.e("BaseParameter-Could not open file /proc/cpuinfo", ((FileNotFoundException)localObject2).toString());
        localObject1 = localObject3;
      }
      return "";
    }
    catch (FileNotFoundException localFileNotFoundException1)
    {
      while (true)
      {
        Object localObject2 = localFileNotFoundException1;
        Object localObject3 = null;
      }
    }
  }

  public static String a(Context paramContext)
  {
    try
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      String str1 = paramContext.getPackageName();
      if ((localPackageManager != null) && (str1 != null))
      {
        String str2 = localPackageManager.getApplicationLabel(localPackageManager.getPackageInfo(str1, 1).applicationInfo).toString();
        return str2;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return null;
  }

  public static int b()
  {
    try
    {
      int j = Build.VERSION.class.getField("SDK_INT").getInt(null);
      return j;
    }
    catch (Exception localException1)
    {
      try
      {
        int i = Integer.parseInt((String)Build.VERSION.class.getField("SDK").get(null));
        return i;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.g
 * JD-Core Version:    0.6.2
 */