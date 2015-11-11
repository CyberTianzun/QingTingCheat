package com.taobao.newxp.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.taobao.locate.LocateManager;
import android.taobao.locate.LocateManagerImpl;
import android.taobao.locate.LocationInfo;

public class LocateActivity extends FragmentActivity
{
  BroadcastReceiver locationInfoReceiver;
  LocateManager mLocateManager;

  private boolean isExistTBLocateModel()
  {
    try
    {
      Class.forName("android.taobao.locate.LocateManager");
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void cancelReqLocation()
  {
    if (this.mLocateManager != null)
      this.mLocateManager.cancelAll();
  }

  protected void onCreate(Bundle paramBundle)
  {
    try
    {
      super.onCreate(paramBundle);
      if (isExistTBLocateModel())
      {
        this.locationInfoReceiver = new BroadcastReceiver()
        {
          public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            switch (paramAnonymousIntent.getIntExtra("location_status", 1))
            {
            case -1:
            case 0:
            default:
              return;
            case 1:
            case 2:
            }
            LocationInfo localLocationInfo = (LocationInfo)paramAnonymousIntent.getParcelableExtra("location_info");
            LocateActivity.this.onGetLoationInfo(localLocationInfo);
          }
        };
        this.mLocateManager = LocateManagerImpl.getInstance(this);
      }
      return;
    }
    catch (Exception localException)
    {
      finish();
    }
  }

  protected void onGetLoationInfo(LocationInfo paramLocationInfo)
  {
    double d1 = paramLocationInfo.getOffsetLatitude();
    double d2 = paramLocationInfo.getOffsetLongitude();
    long l = paramLocationInfo.getTime();
    float f = (float)paramLocationInfo.getAccuracy();
    if ((d1 != 0.0D) && (d2 != 0.0D))
    {
      Location localLocation = new Location("tb_locate_sdk");
      localLocation.setLatitude(d1);
      localLocation.setLongitude(d2);
      localLocation.setTime(l);
      localLocation.setAccuracy(f);
      a.a(getApplicationContext(), localLocation);
      cancelReqLocation();
    }
  }

  protected void onPause()
  {
    super.onPause();
    if (this.locationInfoReceiver != null)
      unregisterReceiver(this.locationInfoReceiver);
    cancelReqLocation();
  }

  protected void onResume()
  {
    super.onResume();
    if (this.locationInfoReceiver != null)
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction(LocateManager.LOCATION_NOTIFY_URI);
      registerReceiver(this.locationInfoReceiver, localIntentFilter);
    }
    if ((this.mLocateManager != null) && (this.locationInfoReceiver != null))
      requestLocation();
  }

  public void requestLocation()
  {
    if (this.mLocateManager != null)
      this.mLocateManager.requestLocationUpdates();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.location.LocateActivity
 * JD-Core Version:    0.6.2
 */