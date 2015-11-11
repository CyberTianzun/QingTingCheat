package com.miaozhen.mzmonitor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.util.Log;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

final class h
  implements ServiceConnection
{
  private static String f = null;
  private static boolean g = false;
  private final Context a;
  private List<ResolveInfo> b;
  private Map<String, Integer> c;
  private final SharedPreferences d;
  private final Random e;

  private h(Context paramContext)
  {
    this.d = paramContext.getSharedPreferences("openudid_prefs", 0);
    this.a = paramContext;
    this.e = new Random();
    this.c = new HashMap();
  }

  public static String a()
  {
    if (!g)
      Log.e("OpenUDID", "Initialisation isn't done");
    return f;
  }

  public static void a(Context paramContext)
  {
    h localh = new h(paramContext);
    String str = localh.d.getString("openudid", null);
    f = str;
    if (str == null)
    {
      localh.b = paramContext.getPackageManager().queryIntentServices(new Intent("org.OpenUDID.GETUDID"), 0);
      Log.d("OpenUDID", localh.b.size() + " services matches OpenUDID");
      if (localh.b != null)
        localh.c();
      return;
    }
    Log.d("OpenUDID", "OpenUDID: " + f);
    g = true;
  }

  public static boolean b()
  {
    return g;
  }

  private void c()
  {
    if (this.b.size() > 0)
    {
      Log.d("OpenUDID", "Trying service " + ((ResolveInfo)this.b.get(0)).loadLabel(this.a.getPackageManager()));
      ServiceInfo localServiceInfo = ((ResolveInfo)this.b.get(0)).serviceInfo;
      Intent localIntent = new Intent();
      localIntent.setComponent(new ComponentName(localServiceInfo.applicationInfo.packageName, localServiceInfo.name));
      this.a.bindService(localIntent, this, 1);
      this.b.remove(0);
      return;
    }
    if (!this.c.isEmpty())
    {
      TreeMap localTreeMap = new TreeMap(new a((byte)0));
      localTreeMap.putAll(this.c);
      f = (String)localTreeMap.firstKey();
    }
    if (f == null)
    {
      Log.d("OpenUDID", "Generating openUDID");
      String str = Settings.Secure.getString(this.a.getContentResolver(), "android_id");
      f = str;
      if ((str == null) || (f.equals("9774d56d682e549c")) || (f.length() < 15))
        f = new BigInteger(64, new SecureRandom()).toString(16);
    }
    Log.d("OpenUDID", "OpenUDID: " + f);
    SharedPreferences.Editor localEditor = this.d.edit();
    localEditor.putString("openudid", f);
    localEditor.commit();
    g = true;
  }

  public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    try
    {
      Parcel localParcel1 = Parcel.obtain();
      localParcel1.writeInt(this.e.nextInt());
      Parcel localParcel2 = Parcel.obtain();
      paramIBinder.transact(1, Parcel.obtain(), localParcel2, 0);
      String str;
      if (localParcel1.readInt() == localParcel2.readInt())
      {
        str = localParcel2.readString();
        if (str != null)
        {
          Log.d("OpenUDID", "Received " + str);
          if (!this.c.containsKey(str))
            break label146;
          this.c.put(str, Integer.valueOf(1 + ((Integer)this.c.get(str)).intValue()));
        }
      }
      while (true)
      {
        this.a.unbindService(this);
        c();
        return;
        label146: this.c.put(str, Integer.valueOf(1));
      }
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        Log.e("OpenUDID", "RemoteException: " + localRemoteException.getMessage());
    }
  }

  public final void onServiceDisconnected(ComponentName paramComponentName)
  {
  }

  final class a
    implements Comparator
  {
    private a()
    {
    }

    public final int compare(Object paramObject1, Object paramObject2)
    {
      if (((Integer)h.a(h.this).get(paramObject1)).intValue() < ((Integer)h.a(h.this).get(paramObject2)).intValue())
        return 1;
      if (h.a(h.this).get(paramObject1) == h.a(h.this).get(paramObject2))
        return 0;
      return -1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.h
 * JD-Core Version:    0.6.2
 */