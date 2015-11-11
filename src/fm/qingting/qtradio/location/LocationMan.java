package fm.qingting.qtradio.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import fm.qingting.framework.event.IEventHandler;

public class LocationMan
{
  private static final int CHECK_INTERVAL = 30000;
  private Location currentLocation;
  private IEventHandler eventHandler;
  private Context mContext;
  private MyLocationListner mGPSListener;
  private LocationData mLocation;
  private LocationManager mLocationManager;
  private MyLocationListner mNetworkListner;

  public LocationMan(Context paramContext)
  {
    this.mContext = paramContext;
    this.mLocationManager = ((LocationManager)this.mContext.getSystemService("location"));
  }

  private void checkLocation(Location paramLocation)
  {
    if (paramLocation.getProvider().equalsIgnoreCase("gps"))
      if (this.currentLocation != null)
        if (isBetterLocation(paramLocation, this.currentLocation))
        {
          stoplocation();
          this.currentLocation = paramLocation;
          this.eventHandler.onEvent(this, "betterLocation", this.currentLocation);
        }
    while (!paramLocation.getProvider().equalsIgnoreCase("network"))
    {
      return;
      this.currentLocation = paramLocation;
      return;
    }
    stoplocation();
    this.currentLocation = paramLocation;
    this.eventHandler.onEvent(this, "betterLocation", this.currentLocation);
  }

  private void doLocate(String paramString, LocationListener paramLocationListener)
  {
    try
    {
      this.mLocationManager.requestLocationUpdates(paramString, 5000L, 1.0F, paramLocationListener);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private boolean isSameProvider(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return paramString2 == null;
    return paramString1.equals(paramString2);
  }

  private void registerLocationListener()
  {
    try
    {
      if (isGPSEnabled())
      {
        this.mGPSListener = new MyLocationListner(null);
        doLocate("gps", this.mGPSListener);
      }
      if (isNetworkEnabled())
      {
        this.mNetworkListner = new MyLocationListner(null);
        doLocate("network", this.mNetworkListner);
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
  }

  protected boolean isBetterLocation(Location paramLocation1, Location paramLocation2)
  {
    if (paramLocation2 == null);
    label38: int k;
    label65: label71: label75: 
    while (true)
    {
      return true;
      long l = paramLocation1.getTime() - paramLocation2.getTime();
      int i;
      int j;
      if (l > 30000L)
      {
        i = 1;
        if (l >= -30000L)
          break label65;
        j = 1;
        if (l <= 0L)
          break label71;
      }
      for (k = 1; ; k = 0)
      {
        if (i != 0)
          break label75;
        if (j == 0)
          break label77;
        return false;
        i = 0;
        break;
        j = 0;
        break label38;
      }
    }
    label77: int m = (int)(paramLocation1.getAccuracy() - paramLocation2.getAccuracy());
    int n;
    label97: int i1;
    if (m > 0)
    {
      n = 1;
      if (m >= 0)
        break label168;
      i1 = 1;
      label105: if (m <= 200)
        break label174;
    }
    label168: label174: for (int i2 = 1; ; i2 = 0)
    {
      boolean bool = isSameProvider(paramLocation1.getProvider(), paramLocation2.getProvider());
      if ((i1 != 0) || ((k != 0) && (n == 0)) || ((k != 0) && (i2 == 0) && (bool)))
        break;
      return false;
      n = 0;
      break label97;
      i1 = 0;
      break label105;
    }
  }

  public boolean isGPSEnabled()
  {
    try
    {
      boolean bool1 = this.mLocationManager.isProviderEnabled("gps");
      boolean bool2 = false;
      if (bool1)
        bool2 = true;
      return bool2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public boolean isNetworkEnabled()
  {
    return (isWIFIEnabled()) || (isTelephonyEnabled());
  }

  public boolean isTelephonyEnabled()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
    return (localTelephonyManager != null) && (localTelephonyManager.getNetworkType() != 0);
  }

  public boolean isWIFIEnabled()
  {
    return ((WifiManager)this.mContext.getSystemService("wifi")).isWifiEnabled();
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void startLocation()
  {
    this.mLocation = null;
    registerLocationListener();
  }

  public void stoplocation()
  {
    try
    {
      if (this.mGPSListener != null)
      {
        this.mLocationManager.removeUpdates(this.mGPSListener);
        this.mGPSListener = null;
      }
      if (this.mNetworkListner != null)
      {
        this.mLocationManager.removeUpdates(this.mNetworkListner);
        this.mNetworkListner = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private class MyLocationListner
    implements LocationListener
  {
    private MyLocationListner()
    {
    }

    public void onLocationChanged(Location paramLocation)
    {
      LocationMan.this.checkLocation(paramLocation);
    }

    public void onProviderDisabled(String paramString)
    {
    }

    public void onProviderEnabled(String paramString)
    {
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.location.LocationMan
 * JD-Core Version:    0.6.2
 */