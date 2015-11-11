package cn.com.mma.mobile.tracking.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtil
{
  private static LocationUtil locationUtil = null;
  public LocationListener listener = new LocationListener()
  {
    public void onLocationChanged(Location paramAnonymousLocation)
    {
      double d1 = paramAnonymousLocation.getLongitude();
      double d2 = paramAnonymousLocation.getLatitude();
      Logger.d(d2 + "x" + d1);
      LocationUtil.this.locationBuilder = LocationUtil.this.locationBuilder.append(d2).append("x").append(d1);
    }

    public void onProviderDisabled(String paramAnonymousString)
    {
      Logger.d("onProviderDisabled:" + paramAnonymousString);
    }

    public void onProviderEnabled(String paramAnonymousString)
    {
      Logger.d("onProviderEnabled:" + paramAnonymousString);
    }

    public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      Logger.d("onStatusChanged:" + paramAnonymousString);
    }
  };
  private StringBuilder locationBuilder = new StringBuilder();
  private LocationManager locationManager;

  private LocationUtil(Context paramContext)
  {
    this.locationManager = ((LocationManager)paramContext.getSystemService("location"));
  }

  public static LocationUtil getInstance(Context paramContext)
  {
    if (locationUtil == null)
      locationUtil = new LocationUtil(paramContext);
    return locationUtil;
  }

  public String getLocation()
  {
    return this.locationBuilder.toString();
  }

  public void startLocationListener()
  {
    try
    {
      Criteria localCriteria = new Criteria();
      localCriteria.setAccuracy(2);
      localCriteria.setAltitudeRequired(false);
      localCriteria.setBearingRequired(false);
      localCriteria.setCostAllowed(true);
      localCriteria.setPowerRequirement(1);
      String str = this.locationManager.getBestProvider(localCriteria, true);
      this.locationManager.requestLocationUpdates(str, 3600000L, 0.0F, this.listener);
      return;
    }
    catch (Exception localException)
    {
      Logger.d("mma_Error data LBS" + localException);
      localException.printStackTrace();
    }
  }

  public void stopListenLocation()
  {
    if (this.locationManager != null)
    {
      this.locationManager.removeUpdates(this.listener);
      Logger.d("停止位置监听");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.util.LocationUtil
 * JD-Core Version:    0.6.2
 */