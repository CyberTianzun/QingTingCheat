package fm.qingting.utils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.umeng.analytics.MobclickAgent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WifiSettingsUtil
{
  private static final String GOOGLE_DNS = "8.8.8.8";
  public static WifiSettingsUtil _instance;
  private Context mContext = null;
  private boolean mHasDoneWifiSetting = false;
  private ArrayList<InetAddress> mLstDnses;
  private WifiConfiguration mWifiConf = null;
  private WifiManager mWifiManager;

  private void buildWifiConf()
  {
    if (this.mContext == null);
    WifiInfo localWifiInfo;
    WifiConfiguration localWifiConfiguration;
    do
    {
      return;
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        List localList;
        do
        {
          do
            this.mWifiManager = ((WifiManager)this.mContext.getSystemService("wifi"));
          while (this.mWifiManager == null);
          localWifiInfo = this.mWifiManager.getConnectionInfo();
          localList = this.mWifiManager.getConfiguredNetworks();
        }
        while ((localWifiInfo == null) || (localList == null));
        localIterator = localList.iterator();
      }
      localWifiConfiguration = (WifiConfiguration)localIterator.next();
    }
    while (localWifiConfiguration.networkId != localWifiInfo.getNetworkId());
    this.mWifiConf = localWifiConfiguration;
  }

  public static Object getDeclaredField(Object paramObject, String paramString)
  {
    try
    {
      Field localField = paramObject.getClass().getDeclaredField(paramString);
      localField.setAccessible(true);
      Object localObject = localField.get(paramObject);
      return localObject;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static Object getField(Object paramObject, String paramString)
  {
    try
    {
      Object localObject = paramObject.getClass().getField(paramString).get(paramObject);
      return localObject;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static WifiSettingsUtil getInstance()
  {
    if (_instance == null)
      _instance = new WifiSettingsUtil();
    return _instance;
  }

  private boolean isValidDNS(InetAddress paramInetAddress)
  {
    if (paramInetAddress == null);
    String str;
    do
    {
      return false;
      str = paramInetAddress.getHostAddress();
    }
    while ((str == null) || (str.equalsIgnoreCase("")) || (str.startsWith("192")) || (str.startsWith("127")));
    return true;
  }

  private void log(String paramString)
  {
    Log.e("wifisettingsutil", paramString);
  }

  private void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private boolean setDNS(String paramString)
  {
    if ((this.mWifiConf == null) || (paramString == null))
      return false;
    try
    {
      InetAddress localInetAddress1 = InetAddress.getByName(paramString);
      Field localField1 = this.mWifiConf.getClass().getField("linkProperties");
      if (localField1 == null)
        return false;
      localField1.setAccessible(true);
      Object localObject = localField1.get(this.mWifiConf);
      Field localField2 = localObject.getClass().getDeclaredField("mDnses");
      if (localField2 != null)
      {
        localField2.setAccessible(true);
        this.mLstDnses = ((ArrayList)localField2.get(localObject));
      }
      if (this.mLstDnses != null)
      {
        if (this.mLstDnses.size() == 0)
        {
          this.mLstDnses.add(localInetAddress1);
          return true;
        }
        InetAddress localInetAddress2 = (InetAddress)this.mLstDnses.get(0);
        if (localInetAddress2 != null)
        {
          String str = localInetAddress2.getHostAddress();
          if ((str != null) && (!str.equalsIgnoreCase(paramString)))
          {
            this.mLstDnses.clear();
            this.mLstDnses.add(localInetAddress1);
            return true;
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static void setEnumField(Object paramObject, String paramString1, String paramString2)
  {
    try
    {
      Field localField = paramObject.getClass().getField(paramString2);
      localField.set(paramObject, Enum.valueOf(localField.getType(), paramString1));
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void setGateway(InetAddress paramInetAddress)
  {
    Object localObject = getField(this.mWifiConf, "linkProperties");
    if (localObject == null)
      return;
    ((InetAddress)getDeclaredField(localObject, "mGateway"));
  }

  private void setIpAssignment(String paramString)
  {
    setEnumField(this.mWifiConf, paramString, "ipAssignment");
  }

  public void init(Context paramContext)
  {
    setContext(paramContext);
    buildWifiConf();
  }

  public boolean isValidDNS()
  {
    if (this.mWifiConf == null)
      return false;
    try
    {
      Field localField1 = this.mWifiConf.getClass().getField("linkProperties");
      if (localField1 == null)
        return false;
      localField1.setAccessible(true);
      Object localObject = localField1.get(this.mWifiConf);
      Field localField2 = localObject.getClass().getDeclaredField("mDnses");
      if (localField2 != null)
      {
        localField2.setAccessible(true);
        this.mLstDnses = ((ArrayList)localField2.get(localObject));
      }
      if (this.mLstDnses == null)
        return false;
      if (this.mLstDnses.size() == 0)
        return false;
      boolean bool = isValidDNS((InetAddress)this.mLstDnses.get(0));
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public void setDNS()
  {
    try
    {
      if (this.mHasDoneWifiSetting)
        return;
      setIpAssignment("STATIC");
      if (setDNS("8.8.8.8"))
      {
        MobclickAgent.onEvent(this.mContext, "AutoSetDNS");
        this.mWifiManager.updateNetwork(this.mWifiConf);
        this.mWifiManager.saveConfiguration();
        this.mWifiManager.reassociate();
      }
      this.mHasDoneWifiSetting = true;
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setIpAddress(InetAddress paramInetAddress, int paramInt)
  {
    try
    {
      Object localObject1 = getField(this.mWifiConf, "linkProperties");
      if (localObject1 == null)
        return;
      Class localClass = Class.forName("android.net.LinkAddress");
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = InetAddress.class;
      arrayOfClass[1] = Integer.TYPE;
      Constructor localConstructor = localClass.getConstructor(arrayOfClass);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramInetAddress;
      arrayOfObject[1] = Integer.valueOf(paramInt);
      Object localObject2 = localConstructor.newInstance(arrayOfObject);
      ArrayList localArrayList = (ArrayList)getDeclaredField(localObject1, "mLinkAddresses");
      localArrayList.clear();
      localArrayList.add(localObject2);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.WifiSettingsUtil
 * JD-Core Version:    0.6.2
 */