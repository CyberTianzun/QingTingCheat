package com.tencent.connect.dataprovider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;
import java.lang.ref.WeakReference;

public final class CallbackManager
{
  private WeakReference<Context> a;
  private Uri b;
  private String c;
  private String d;
  private String e;
  private String f;
  private boolean g = false;
  private int h;

  public CallbackManager(Activity paramActivity)
  {
    this.a = new WeakReference(paramActivity.getApplicationContext());
    Intent localIntent = paramActivity.getIntent();
    if (localIntent != null)
    {
      this.b = localIntent.getData();
      this.c = localIntent.getStringExtra("srcPackageName");
      this.d = localIntent.getStringExtra("srcClassName");
      this.e = localIntent.getStringExtra("srcAction");
      this.h = localIntent.getIntExtra("requestDataTypeFlag", 0);
      this.f = localIntent.getStringExtra("params_appid");
      if ((this.b != null) && (this.d != null))
        this.g = true;
    }
  }

  private int a(Bundle paramBundle)
  {
    if (!this.g)
      return -2;
    Intent localIntent = new Intent();
    localIntent.setClassName(this.c, this.d);
    localIntent.setAction(this.e);
    paramBundle.putString("params_appid", this.f);
    localIntent.putExtras(paramBundle);
    localIntent.setFlags(268435456);
    ((Context)this.a.get()).startActivity(localIntent);
    return 0;
  }

  private int a(String paramString)
  {
    int i;
    if (paramString == null)
      i = -7;
    boolean bool2;
    do
    {
      String str;
      boolean bool1;
      do
      {
        return i;
        str = paramString.toLowerCase();
        bool1 = str.startsWith("http://");
        i = 0;
      }
      while (bool1);
      if (Environment.getExternalStorageState().equals("mounted"))
      {
        if (!str.startsWith(Environment.getExternalStorageDirectory().toString().toLowerCase()))
          return -5;
      }
      else
        return -10;
      File localFile = new File(paramString);
      if ((!localFile.exists()) || (localFile.isDirectory()))
        return -8;
      long l = localFile.length();
      if (l == 0L)
        return -9;
      bool2 = l < 1073741824L;
      i = 0;
    }
    while (!bool2);
    return -6;
  }

  public int getRequestDateTypeFlag()
  {
    return this.h;
  }

  public boolean isCallFromTencentApp()
  {
    return this.g;
  }

  public boolean isSupportType(int paramInt)
  {
    return (paramInt & getRequestDateTypeFlag()) != 0;
  }

  public int sendTextAndImagePath(String paramString1, String paramString2)
  {
    int i;
    if (!isSupportType(1))
      i = -1;
    do
    {
      return i;
      i = a(paramString2);
    }
    while (i != 0);
    DataType.TextAndMediaPath localTextAndMediaPath = new DataType.TextAndMediaPath(paramString1, paramString2);
    Bundle localBundle = new Bundle();
    localBundle.putInt("contentDataType", 1);
    localBundle.putParcelable("contentData", localTextAndMediaPath);
    return a(localBundle);
  }

  public int sendTextAndVideoPath(String paramString1, String paramString2)
  {
    int i;
    if (!isSupportType(2))
      i = -1;
    do
    {
      return i;
      i = a(paramString2);
    }
    while (i != 0);
    DataType.TextAndMediaPath localTextAndMediaPath = new DataType.TextAndMediaPath(paramString1, paramString2);
    Bundle localBundle = new Bundle();
    localBundle.putInt("contentDataType", 2);
    localBundle.putParcelable("contentData", localTextAndMediaPath);
    return a(localBundle);
  }

  public int sendTextOnly(String paramString)
  {
    if (!isSupportType(4))
      return -1;
    DataType.TextOnly localTextOnly = new DataType.TextOnly(paramString);
    Bundle localBundle = new Bundle();
    localBundle.putInt("contentDataType", 4);
    localBundle.putParcelable("contentData", localTextOnly);
    return a(localBundle);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.dataprovider.CallbackManager
 * JD-Core Version:    0.6.2
 */