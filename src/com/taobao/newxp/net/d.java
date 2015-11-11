package com.taobao.newxp.net;

import android.os.Parcel;
import android.os.Parcelable;
import com.taobao.munion.base.Log;
import com.taobao.newxp.UFPResType;
import com.taobao.newxp.b;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  implements Parcelable
{
  public String appkey;
  public int autofill = 1;
  public String filterPromoter = "";
  public String keywords = "";
  public int landing_type = 0;
  public int layoutType = -1;
  public int page_index = -1;
  public String psid = "";
  public UFPResType resType;
  public String sid = "";
  public String slotId;
  public String slot_act_params = "";
  public String tabId = "";
  public String tag = "";
  public b template;
  public String templateAttrs;
  public String urlParams;

  public d()
  {
  }

  protected d(Parcel paramParcel)
  {
    this.slotId = paramParcel.readString();
    this.appkey = paramParcel.readString();
    this.autofill = paramParcel.readInt();
    this.layoutType = paramParcel.readInt();
    this.keywords = paramParcel.readString();
    this.tabId = paramParcel.readString();
    this.filterPromoter = paramParcel.readString();
    this.tag = paramParcel.readString();
    this.landing_type = paramParcel.readInt();
    this.sid = paramParcel.readString();
    this.psid = paramParcel.readString();
    int i = paramParcel.readInt();
    UFPResType localUFPResType;
    int j;
    b localb;
    if (i == -1)
    {
      localUFPResType = null;
      this.resType = localUFPResType;
      j = paramParcel.readInt();
      localb = null;
      if (j != -1)
        break label234;
    }
    while (true)
    {
      this.template = localb;
      this.templateAttrs = paramParcel.readString();
      this.urlParams = paramParcel.readString();
      this.slot_act_params = paramParcel.readString();
      this.page_index = paramParcel.readInt();
      return;
      localUFPResType = UFPResType.values()[i];
      break;
      label234: localb = b.values()[j];
    }
  }

  public void clear()
  {
    this.psid = "";
    this.sid = "";
    this.resType = null;
    this.template = null;
    this.slot_act_params = "";
    this.urlParams = "";
    this.templateAttrs = "";
  }

  public int describeContents()
  {
    return 0;
  }

  public void warp(JSONObject paramJSONObject)
  {
    try
    {
      this.sid = paramJSONObject.optString("sid", "");
      this.psid = paramJSONObject.optString("psid", "");
      this.urlParams = paramJSONObject.optString("url_params", this.urlParams);
      if (paramJSONObject.has("template"))
        this.template = b.a(paramJSONObject.getString("template"));
      UFPResType localUFPResType;
      if (paramJSONObject.has("resource_type"))
      {
        localUFPResType = UFPResType.fromString(paramJSONObject.optString("resource_type", UFPResType.APP.toString()));
        if (localUFPResType == null)
          break label107;
      }
      while (true)
      {
        this.resType = localUFPResType;
        this.slot_act_params = paramJSONObject.optString("act_pams", "");
        return;
        label107: localUFPResType = UFPResType.APP;
      }
    }
    catch (JSONException localJSONException)
    {
      Log.e(localJSONException, "Parse json error", new Object[0]);
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = -1;
    paramParcel.writeString(this.slotId);
    paramParcel.writeString(this.appkey);
    paramParcel.writeInt(this.autofill);
    paramParcel.writeInt(this.layoutType);
    paramParcel.writeString(this.keywords);
    paramParcel.writeString(this.tabId);
    paramParcel.writeString(this.filterPromoter);
    paramParcel.writeString(this.tag);
    paramParcel.writeInt(this.landing_type);
    paramParcel.writeString(this.sid);
    paramParcel.writeString(this.psid);
    int j;
    if (this.resType == null)
    {
      j = i;
      paramParcel.writeInt(j);
      if (this.template != null)
        break label163;
    }
    while (true)
    {
      paramParcel.writeInt(i);
      paramParcel.writeString(this.templateAttrs);
      paramParcel.writeString(this.urlParams);
      paramParcel.writeString(this.slot_act_params);
      paramParcel.writeInt(this.page_index);
      return;
      j = this.resType.ordinal();
      break;
      label163: i = this.template.ordinal();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.d
 * JD-Core Version:    0.6.2
 */