package com.tencent.stat.event;

import android.content.Context;
import java.util.Arrays;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomEvent extends Event
{
  private long duration = -1L;
  protected Key key = new Key();

  public CustomEvent(Context paramContext, int paramInt, String paramString)
  {
    super(paramContext, paramInt);
    this.key.id = paramString;
  }

  public Key getKey()
  {
    return this.key;
  }

  public EventType getType()
  {
    return EventType.CUSTOM;
  }

  public boolean onEncode(JSONObject paramJSONObject)
    throws JSONException
  {
    paramJSONObject.put("ei", this.key.id);
    if (this.duration > 0L)
      paramJSONObject.put("du", this.duration);
    if (this.key.args != null)
    {
      JSONArray localJSONArray = new JSONArray();
      String[] arrayOfString = this.key.args;
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++)
        localJSONArray.put(arrayOfString[j]);
      paramJSONObject.put("ar", localJSONArray);
    }
    if (this.key.prop != null)
      paramJSONObject.put("kv", new JSONObject(this.key.prop));
    return true;
  }

  public void setArgs(String[] paramArrayOfString)
  {
    this.key.args = paramArrayOfString;
  }

  public void setDuration(long paramLong)
  {
    this.duration = paramLong;
  }

  public void setProperties(Properties paramProperties)
  {
    this.key.prop = paramProperties;
  }

  public static class Key
  {
    String[] args;
    String id;
    Properties prop = null;

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      Key localKey;
      int i;
      label81: 
      do
      {
        return true;
        if (!(paramObject instanceof Key))
          break;
        localKey = (Key)paramObject;
        if ((this.id.equals(localKey.id)) && (Arrays.equals(this.args, localKey.args)));
        for (i = 1; ; i = 0)
        {
          if (this.prop == null)
            break label81;
          if ((i != 0) && (this.prop.equals(localKey.prop)))
            break;
          return false;
        }
      }
      while ((i != 0) && (localKey.prop == null));
      return false;
      return false;
    }

    public int hashCode()
    {
      String str = this.id;
      int i = 0;
      if (str != null)
        i = this.id.hashCode();
      if (this.args != null)
        i ^= Arrays.hashCode(this.args);
      if (this.prop != null)
        i ^= this.prop.hashCode();
      return i;
    }

    public String toString()
    {
      String str1 = this.id;
      String str2 = "";
      if (this.args != null)
      {
        String str3 = this.args[0];
        for (int i = 1; i < this.args.length; i++)
          str3 = str3 + "," + this.args[i];
        str2 = "[" + str3 + "]";
      }
      if (this.prop != null)
        str2 = str2 + this.prop.toString();
      return str1 + str2;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.event.CustomEvent
 * JD-Core Version:    0.6.2
 */