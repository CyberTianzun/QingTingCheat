package com.taobao.newxp.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import org.json.JSONObject;

public class a
{
  private static final String a = "TB_LOCATION";
  private static final String b = "location";

  public static Location a(Context paramContext)
  {
    return a(paramContext.getSharedPreferences("TB_LOCATION", 0).getString("location", ""));
  }

  private static Location a(String paramString)
  {
    Location localLocation = new Location("");
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      localLocation.setLatitude(localJSONObject.optDouble("lat"));
      localLocation.setLongitude(localJSONObject.optDouble("lon"));
      localLocation.setTime(localJSONObject.optLong("time"));
      localLocation.setAccuracy(localJSONObject.optInt("accuracy"));
      localLocation.setProvider(localJSONObject.optString("provider"));
      return localLocation;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private static String a(Location paramLocation)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("lat", paramLocation.getLatitude());
      localJSONObject.put("lon", paramLocation.getLongitude());
      localJSONObject.put("time", paramLocation.getTime());
      localJSONObject.put("accuracy", paramLocation.getAccuracy());
      localJSONObject.put("provider", paramLocation.getProvider());
      String str = localJSONObject.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static void a(Context paramContext, Location paramLocation)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("TB_LOCATION", 0);
    String str = a(paramLocation);
    try
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString("location", str);
      localEditor.commit();
      return;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.location.a
 * JD-Core Version:    0.6.2
 */