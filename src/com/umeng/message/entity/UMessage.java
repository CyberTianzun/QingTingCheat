package com.umeng.message.entity;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class UMessage
{
  public static final String DISPLAY_TYPE_CUSTOM = "custom";
  public static final String DISPLAY_TYPE_NOTIFICATION = "notification";
  public static final String NOTIFICATION_GO_ACTIVITY = "go_activity";
  public static final String NOTIFICATION_GO_APP = "go_app";
  public static final String NOTIFICATION_GO_CUSTOM = "go_custom";
  public static final String NOTIFICATION_GO_URL = "go_url";
  private JSONObject a;
  public String activity;
  public String after_open;
  public String alias;
  public int builder_id;
  public String custom;
  public String display_type;
  public Map<String, String> extra;
  public String icon;
  public String img;
  public String largeIcon;
  public String msg_id;
  public boolean play_lights;
  public boolean play_sound;
  public boolean play_vibrate;
  public long random_min;
  public boolean screen_on;
  public String sound;
  public String text;
  public String ticker;
  public String title;
  public String url;

  public UMessage(JSONObject paramJSONObject)
    throws JSONException
  {
    this.a = paramJSONObject;
    this.msg_id = paramJSONObject.getString("msg_id");
    this.display_type = paramJSONObject.getString("display_type");
    this.alias = paramJSONObject.optString("alias");
    this.random_min = paramJSONObject.optLong("random_min");
    JSONObject localJSONObject1 = paramJSONObject.getJSONObject("body");
    this.ticker = localJSONObject1.optString("ticker");
    this.title = localJSONObject1.optString("title");
    this.text = localJSONObject1.optString("text");
    this.play_vibrate = localJSONObject1.optBoolean("play_vibrate", true);
    this.play_lights = localJSONObject1.optBoolean("play_lights", true);
    this.play_sound = localJSONObject1.optBoolean("play_sound", true);
    this.screen_on = localJSONObject1.optBoolean("screen_on", false);
    this.url = localJSONObject1.optString("url");
    this.img = localJSONObject1.optString("img");
    this.sound = localJSONObject1.optString("sound");
    this.icon = localJSONObject1.optString("icon");
    this.after_open = localJSONObject1.optString("after_open");
    this.largeIcon = localJSONObject1.optString("largeIcon");
    this.activity = localJSONObject1.optString("activity");
    this.custom = localJSONObject1.optString("custom");
    this.builder_id = localJSONObject1.optInt("builder_id", 0);
    JSONObject localJSONObject2 = paramJSONObject.optJSONObject("extra");
    if ((localJSONObject2 != null) && (localJSONObject2.keys() != null))
    {
      this.extra = new HashMap();
      Iterator localIterator = localJSONObject2.keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        this.extra.put(str, localJSONObject2.getString(str));
      }
    }
  }

  public JSONObject getRaw()
  {
    return this.a;
  }

  public boolean hasResourceFromInternet()
  {
    return (isLargeIconFromInternet()) || (isSoundFromInternet());
  }

  public boolean isLargeIconFromInternet()
  {
    return !TextUtils.isEmpty(this.img);
  }

  public boolean isSoundFromInternet()
  {
    return (!TextUtils.isEmpty(this.sound)) && ((this.sound.startsWith("http://")) || (this.sound.startsWith("https://")));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.entity.UMessage
 * JD-Core Version:    0.6.2
 */