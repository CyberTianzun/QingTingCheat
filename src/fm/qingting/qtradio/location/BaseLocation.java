package fm.qingting.qtradio.location;

import android.content.Context;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import fm.qingting.framework.event.IEventHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class BaseLocation
{
  private IEventHandler eventHandler;
  private LocationThread locationThread;
  private Context mContext;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        Location localLocation = (Location)paramAnonymousMessage.obj;
        BaseLocation.this.eventHandler.onEvent(BaseLocation.this, "betterlocation", localLocation);
        BaseLocation.this.stopLocation();
        return;
      case 2:
      }
      BaseLocation.this.stopLocation();
    }
  };

  public BaseLocation(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public long GetUTCTime()
  {
    Calendar localCalendar = Calendar.getInstance(Locale.CHINA);
    localCalendar.add(14, -(localCalendar.get(15) + localCalendar.get(16)));
    return localCalendar.getTimeInMillis();
  }

  public ArrayList<Cell> getTypeOfNetwork()
  {
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
      int i = localTelephonyManager.getNetworkType();
      ArrayList localArrayList = new ArrayList();
      if ((i == 6) || (i == 4) || (i == 7))
      {
        CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localTelephonyManager.getCellLocation();
        int j = localCdmaCellLocation.getBaseStationId();
        int k = localCdmaCellLocation.getNetworkId();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(localCdmaCellLocation.getSystemId());
        Cell localCell1 = new Cell();
        localCell1.cellId = j;
        localCell1.locationAreaCode = k;
        localCell1.mobileNetworkCode = localStringBuilder.toString();
        localCell1.mobileCountryCode = localTelephonyManager.getNetworkOperator().substring(0, 3);
        localCell1.radioType = "cdma";
        localArrayList.add(localCell1);
        return localArrayList;
      }
      if (i == 2)
      {
        GsmCellLocation localGsmCellLocation1 = (GsmCellLocation)localTelephonyManager.getCellLocation();
        int m = localGsmCellLocation1.getCid();
        int n = localGsmCellLocation1.getLac();
        Cell localCell2 = new Cell();
        localCell2.cellId = m;
        localCell2.locationAreaCode = n;
        localCell2.mobileNetworkCode = localTelephonyManager.getNetworkOperator().substring(3, 5);
        localCell2.mobileCountryCode = localTelephonyManager.getNetworkOperator().substring(0, 3);
        localCell2.radioType = "gsm";
        localArrayList.add(localCell2);
        return localArrayList;
      }
      if (i == 1)
      {
        GsmCellLocation localGsmCellLocation2 = (GsmCellLocation)localTelephonyManager.getCellLocation();
        int i1 = localGsmCellLocation2.getCid();
        int i2 = localGsmCellLocation2.getLac();
        Cell localCell3 = new Cell();
        localCell3.cellId = i1;
        localCell3.locationAreaCode = i2;
        localCell3.radioType = "gsm";
        localArrayList.add(localCell3);
        return localArrayList;
      }
      return null;
    }
    catch (Exception localException)
    {
      Message localMessage = new Message();
      localMessage.what = 2;
      this.mHandler.sendMessage(localMessage);
      localException.printStackTrace();
    }
    return null;
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

  public void requestData(ArrayList<Cell> paramArrayList)
  {
    if (paramArrayList == null)
    {
      Message localMessage1 = new Message();
      localMessage1.what = 2;
      this.mHandler.sendMessage(localMessage1);
      return;
    }
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpPost localHttpPost = new HttpPost("http://www.google.com/loc/json");
    JSONObject localJSONObject1 = new JSONObject();
    JSONArray localJSONArray;
    try
    {
      localJSONObject1.put("version", "1.1.0");
      localJSONObject1.put("host", "maps.google.com");
      localJSONObject1.put("home_mobile_country_code", ((Cell)paramArrayList.get(0)).mobileCountryCode);
      localJSONObject1.put("home_mobile_network_code", ((Cell)paramArrayList.get(0)).mobileNetworkCode);
      localJSONObject1.put("radio_type", ((Cell)paramArrayList.get(0)).radioType);
      localJSONObject1.put("request_address", true);
      if ("460".equals(((Cell)paramArrayList.get(0)).mobileCountryCode))
        localJSONObject1.put("address_language", "zh_CN");
      while (true)
      {
        localJSONArray = new JSONArray();
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject2.put("cell_id", ((Cell)paramArrayList.get(0)).cellId);
        localJSONObject2.put("location_area_code", ((Cell)paramArrayList.get(0)).locationAreaCode);
        localJSONObject2.put("mobile_country_code", ((Cell)paramArrayList.get(0)).mobileCountryCode);
        localJSONObject2.put("mobile_network_code", ((Cell)paramArrayList.get(0)).mobileNetworkCode);
        localJSONObject2.put("age", 0);
        localJSONArray.put(localJSONObject2);
        if (paramArrayList.size() <= 2)
          break;
        for (int i = 1; i < paramArrayList.size(); i++)
        {
          JSONObject localJSONObject3 = new JSONObject();
          localJSONObject3.put("cell_id", ((Cell)paramArrayList.get(i)).cellId);
          localJSONObject3.put("location_area_code", ((Cell)paramArrayList.get(i)).locationAreaCode);
          localJSONObject3.put("mobile_country_code", ((Cell)paramArrayList.get(i)).mobileCountryCode);
          localJSONObject3.put("mobile_network_code", ((Cell)paramArrayList.get(i)).mobileNetworkCode);
          localJSONObject3.put("age", 0);
          localJSONArray.put(localJSONObject3);
        }
        localJSONObject1.put("address_language", "en_US");
      }
    }
    catch (Exception localException)
    {
      Message localMessage2 = new Message();
      localMessage2.what = 2;
      this.mHandler.sendMessage(localMessage2);
      localException.printStackTrace();
      return;
    }
    localJSONObject1.put("cell_towers", localJSONArray);
    StringEntity localStringEntity = new StringEntity(localJSONObject1.toString());
    Log.e("Location send", localJSONObject1.toString());
    localHttpPost.setEntity(localStringEntity);
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localDefaultHttpClient.execute(localHttpPost).getEntity().getContent()));
    StringBuffer localStringBuffer = new StringBuffer();
    for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
    {
      Log.e("Locaiton receive", str);
      localStringBuffer.append(str);
    }
    if (localStringBuffer.length() <= 1)
    {
      Message localMessage3 = new Message();
      localMessage3.what = 2;
      this.mHandler.sendMessage(localMessage3);
      return;
    }
    JSONObject localJSONObject4 = (JSONObject)new JSONObject(localStringBuffer.toString()).get("location");
    Location localLocation = new Location("network");
    localLocation.setLatitude(((Double)localJSONObject4.get("latitude")).doubleValue());
    localLocation.setLongitude(((Double)localJSONObject4.get("longitude")).doubleValue());
    localLocation.setAccuracy(Float.parseFloat(localJSONObject4.get("accuracy").toString()));
    localLocation.setTime(GetUTCTime());
    Message localMessage4 = new Message();
    localMessage4.what = 1;
    localMessage4.obj = localLocation;
    this.mHandler.sendMessage(localMessage4);
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void startLocation()
  {
    this.locationThread = new LocationThread();
    this.locationThread.start();
  }

  public void stopLocation()
  {
    if (this.locationThread != null)
    {
      this.locationThread.interrupt();
      this.locationThread = null;
    }
  }

  public class Cell
  {
    public int cellId;
    public int locationAreaCode;
    public String mobileCountryCode;
    public String mobileNetworkCode;
    public String radioType;

    public Cell()
    {
    }
  }

  class LocationThread extends Thread
  {
    LocationThread()
    {
    }

    public void run()
    {
      BaseLocation.this.requestData(BaseLocation.this.getTypeOfNetwork());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.location.BaseLocation
 * JD-Core Version:    0.6.2
 */